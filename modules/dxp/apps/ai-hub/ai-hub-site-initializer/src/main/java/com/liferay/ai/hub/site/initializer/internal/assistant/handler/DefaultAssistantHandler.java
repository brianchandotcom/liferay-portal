/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal.assistant.handler;

import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;

/**
 * @author Feliphe Marinho
 */
public class DefaultAssistantHandler implements AssistantHandler {

	public static final String KEY = "default";

	@Override
	public void handle(AssistantHandlerContext assistantHandlerContext) {
		DefaultAssistant defaultAssistant = AiServices.builder(
			DefaultAssistant.class
		).systemMessageProvider(
			assistantHandlerContext.getSystemMessageProvider()
		).streamingChatModel(
			assistantHandlerContext.getVertexAiGeminiStreamingChatModel()
		).build();

		defaultAssistant.assist(
			assistantHandlerContext.getUserMessage()
		).onCompleteResponse(
			assistantHandlerContext.getOnCompleteResponse()
		).onError(
			assistantHandlerContext.getOnError()
		).start();
	}

	public interface DefaultAssistant {

		public TokenStream assist(String userMessage);

	}

}