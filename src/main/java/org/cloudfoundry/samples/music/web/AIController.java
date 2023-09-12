package org.cloudfoundry.samples.music.web;

import org.cloudfoundry.samples.music.config.ai.MessageRetriever;
import org.cloudfoundry.samples.music.domain.Album;
import org.cloudfoundry.samples.music.domain.MessageRequest;
import org.cloudfoundry.samples.music.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ai.client.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

import io.pivotal.cfenv.core.CfEnv;
import jakarta.validation.Valid;

@RestController
@Profile("llm")
public class AIController {
    private static final Logger logger = LoggerFactory.getLogger(AIController.class);
    private MessageRetriever messageRetriever;

    @Autowired
    public AIController(MessageRetriever messageRetriever) {
        this.messageRetriever = messageRetriever;
    }
    @RequestMapping(value = "/ai/rag", method = RequestMethod.POST)

    public Generation generate(@RequestBody MessageRequest messageRequest) {
        Message[] messages = messageRequest.getMessages();
        logger.info("Getting Messages " + messages);

        return messageRetriever.retrieve(messages[messages.length - 1].getText());
    }

}