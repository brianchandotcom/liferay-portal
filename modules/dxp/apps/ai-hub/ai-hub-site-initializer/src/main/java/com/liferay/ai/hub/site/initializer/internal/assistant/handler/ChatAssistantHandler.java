/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal.assistant.handler;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.memory.ChatMemoryAccess;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;

/**
 * @author Feliphe Marinho
 */
public class ChatAssistantHandler implements AssistantHandler {

	public static final String KEY = "chat";

	@Override
	public void handle(AssistantHandlerContext assistantHandlerContext) {
		AiServices<ChatAssistant> aiServices = AiServices.builder(
			ChatAssistant.class
		).chatMemoryProvider(
			id -> MessageWindowChatMemory.builder(
			).chatMemoryStore(
				_inMemoryChatMemoryStore
			).id(
				id
			).maxMessages(
				30
			).build()
		);

		if (assistantHandlerContext.getContentRetriever() != null) {
			aiServices.contentRetriever(
				assistantHandlerContext.getContentRetriever());
		}

		aiServices.streamingChatModel(
			assistantHandlerContext.getVertexAiGeminiStreamingChatModel()
		).systemMessageProvider(
			assistantHandlerContext.getSystemMessageProvider()
		).toolProvider(
			assistantHandlerContext.getToolProvider()
		).build();

		ChatAssistant chatAssistant = aiServices.build();

		chatAssistant.chat(
			assistantHandlerContext.getMemoryId(),
			assistantHandlerContext.getUserMessage()
		).onCompleteResponse(
			assistantHandlerContext.getOnCompleteResponse()
		).onError(
			assistantHandlerContext.getOnError()
		).start();
	}

	public interface ChatAssistant extends ChatMemoryAccess {

		public TokenStream chat(
			@MemoryId String memoryId, @UserMessage String userMessage);

	}

	private final InMemoryChatMemoryStore _inMemoryChatMemoryStore =
		new InMemoryChatMemoryStore();

}