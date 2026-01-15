/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.agent;

import com.liferay.ai.hub.agent.Agent;
import com.liferay.ai.hub.agent.AgentContext;
import com.liferay.ai.hub.internal.web.search.LiferayWebSearchEngine;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiChatModel;
import dev.langchain4j.rag.content.retriever.WebSearchContentRetriever;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author João Victor Alves
 */
@Component(service = Agent.class)
public class AgentImpl implements Agent {

	@Override
	public void invoke(AgentContext agentContext) {
		VertexAiGeminiChatModel vertexAiGeminiChatModel =
			VertexAiGeminiChatModel.builder(
			).location(
				"us-central1"
			).modelName(
				"gemini-2.5-flash-lite"
			).project(
				"ai-hub-liferay"
			).build();

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(AgentImpl.class.getName());

		executorService.submit(
			() -> {
				try {
					LiferayKnowledgeAgent liferayKnowledgeAgent =
						AgenticServices.agentBuilder(
							LiferayKnowledgeAgent.class
						).chatMemoryProvider(
							id -> MessageWindowChatMemory.builder(
							).chatMemoryStore(
								_inMemoryChatMemoryStore
							).id(
								id
							).maxMessages(
								30
							).build()
						).chatModel(
							vertexAiGeminiChatModel
						).contentRetriever(
							WebSearchContentRetriever.builder(
							).webSearchEngine(
								new LiferayWebSearchEngine(null)
							).build()
						).build();

					Map<String, Object> input = agentContext.getInput();

					SseUtil.send(
						liferayKnowledgeAgent.invoke(
							GetterUtil.getString(input.get("message"))),
						"Chat Message Sent", agentContext.getSseEventSinkKey());
				}
				catch (Exception exception) {
					_log.error(exception);
				}
				finally {
					vertexAiGeminiChatModel.close();
				}
			});
	}

	public interface LiferayKnowledgeAgent {

		@dev.langchain4j.agentic.Agent(
			description = "This agent provides targeted support by searching the Liferay DXP instance for the most relevant data, ensuring every response is grounded in your specific environment.",
			name = "Liferay Knowledge Agent", outputKey = "response"
		)
		@SystemMessage(
			"You are a Liferay Knowledge Agent, a specialized AI agent designed to assist users by retrieving accurate information specifically from the Liferay Digital Experience Platform (DXP) instance or using the chat history. Your tone is professional, helpful, and concise."
		)
		@UserMessage("{{request}}")
		public String invoke(@V("request") String request);

	}

	private static final Log _log = LogFactoryUtil.getLog(AgentImpl.class);

	private final InMemoryChatMemoryStore _inMemoryChatMemoryStore =
		new InMemoryChatMemoryStore();

	@Reference
	private PortalExecutorManager _portalExecutorManager;

}