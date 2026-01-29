/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.agent;

import com.liferay.ai.hub.agent.AgentContext;
import com.liferay.ai.hub.agent.AgentsFactory;
import com.liferay.ai.hub.agent.SupervisorAgent;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiChatModel;

import java.util.concurrent.ExecutorService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
@Component(service = SupervisorAgent.class)
public class SupervisorAgentImpl implements SupervisorAgent {

	@Override
	public void invoke(AgentContext agentContext) {
		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				SupervisorAgentImpl.class.getName());

		Object[] agents = _agentsFactory.create(agentContext);

		executorService.submit(
			() -> {
				try (VertexAiGeminiChatModel vertexAiGeminiChatModel =
						VertexAiGeminiChatModel.builder(
						).location(
							"us-central1"
						).modelName(
							"gemini-2.5-flash-lite"
						).project(
							"ai-hub-liferay"
						).build()) {

					_invoke(agentContext, agents, vertexAiGeminiChatModel);
				}
				catch (Exception exception) {
					_log.error(exception);

					SseUtil.send(
						"I cannot fulfill this request.", "Chat Message Sent",
						null, agentContext.getSseEventSinkKey());
				}
			});
	}

	private void _invoke(
		AgentContext agentContext, Object[] agents,
		VertexAiGeminiChatModel vertexAiGeminiChatModel) {

		dev.langchain4j.agentic.supervisor.SupervisorAgent supervisorAgent =
			AgenticServices.supervisorBuilder(
			).chatModel(
				vertexAiGeminiChatModel
			).maxAgentsInvocations(
				5
			).subAgents(
				agents
			).responseStrategy(
				SupervisorResponseStrategy.SUMMARY
			).build();

		SseUtil.send(
			supervisorAgent.invoke(
				MapUtil.getString(agentContext.getInput(), "message")),
			"Chat Message Sent", null, agentContext.getSseEventSinkKey());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SupervisorAgentImpl.class);

	@Reference
	private AgentsFactory _agentsFactory;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

}