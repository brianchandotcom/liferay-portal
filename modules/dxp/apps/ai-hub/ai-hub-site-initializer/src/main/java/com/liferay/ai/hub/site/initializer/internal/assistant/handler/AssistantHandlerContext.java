/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal.assistant.handler;

import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiStreamingChatModel;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Feliphe Marinho
 */
public class AssistantHandlerContext {

	public static AssistantHandlerContext.Builder builder() {
		return new AssistantHandlerContext.Builder();
	}

	public AssistantHandlerContext(AssistantHandlerContext.Builder builder) {
		_memoryId = builder._memoryId;
		_onCompleteResponse = builder._onCompleteResponse;
		_onError = builder._onError;
		_systemMessageProvider = builder._systemMessageProvider;
		_userMessage = builder._userMessage;
		_vertexAiGeminiStreamingChatModel =
			builder._vertexAiGeminiStreamingChatModel;
	}

	public String getMemoryId() {
		return _memoryId;
	}

	public Consumer<ChatResponse> getOnCompleteResponse() {
		return _onCompleteResponse;
	}

	public Consumer<Throwable> getOnError() {
		return _onError;
	}

	public Function<Object, String> getSystemMessageProvider() {
		return _systemMessageProvider;
	}

	public String getUserMessage() {
		return _userMessage;
	}

	public VertexAiGeminiStreamingChatModel
		getVertexAiGeminiStreamingChatModel() {

		return _vertexAiGeminiStreamingChatModel;
	}

	public static class Builder {

		public AssistantHandlerContext build() {
			return new AssistantHandlerContext(this);
		}

		public Builder memoryId(String memoryId) {
			_memoryId = memoryId;

			return this;
		}

		public Builder onCompleteResponse(
			Consumer<ChatResponse> onCompleteResponse) {

			_onCompleteResponse = onCompleteResponse;

			return this;
		}

		public Builder onError(Consumer<Throwable> onError) {
			_onError = onError;

			return this;
		}

		public Builder systemMessageProvider(
			Function<Object, String> systemMessageProvider) {

			_systemMessageProvider = systemMessageProvider;

			return this;
		}

		public Builder userMessage(String userMessage) {
			_userMessage = userMessage;

			return this;
		}

		public Builder vertexAiGeminiStreamingChatModel(
			VertexAiGeminiStreamingChatModel vertexAiGeminiStreamingChatModel) {

			_vertexAiGeminiStreamingChatModel =
				vertexAiGeminiStreamingChatModel;

			return this;
		}

		private String _memoryId;
		private Consumer<ChatResponse> _onCompleteResponse;
		private Consumer<Throwable> _onError;
		private Function<Object, String> _systemMessageProvider;
		private String _userMessage;
		private VertexAiGeminiStreamingChatModel
			_vertexAiGeminiStreamingChatModel;

	}

	private final String _memoryId;
	private final Consumer<ChatResponse> _onCompleteResponse;
	private final Consumer<Throwable> _onError;
	private final Function<Object, String> _systemMessageProvider;
	private final String _userMessage;
	private final VertexAiGeminiStreamingChatModel
		_vertexAiGeminiStreamingChatModel;

}