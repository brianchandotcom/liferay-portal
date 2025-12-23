/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.assistant.handler;

import dev.langchain4j.invocation.InvocationParameters;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

/**
 * @author Feliphe Marinho
 */
public class DefaultAssistantHandler implements AssistantHandler {

	public static final String KEY = "default";

	@Override
	public void handle(AssistantHandlerContext assistantHandlerContext) {
		AiServices<DefaultAssistant> aiServices = AiServices.builder(
			DefaultAssistant.class);

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
		).tools(
			assistantHandlerContext.getTools()
		).build();

		DefaultAssistant defaultAssistant = aiServices.build();

		defaultAssistant.assist(
			assistantHandlerContext.getInvocationParameters(),
			assistantHandlerContext.getUserMessage()
		).onCompleteResponse(
			assistantHandlerContext.getOnCompleteResponse()
		).onError(
			assistantHandlerContext.getOnError()
		).start();
	}

	public interface DefaultAssistant {

		public TokenStream assist(
			InvocationParameters invocationParameters,
			@UserMessage String userMessage);

	}

}