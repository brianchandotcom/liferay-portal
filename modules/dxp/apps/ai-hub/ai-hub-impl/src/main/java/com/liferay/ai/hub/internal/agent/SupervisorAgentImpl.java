/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.agent;

import com.liferay.ai.hub.agent.AgentContext;
import com.liferay.ai.hub.agent.SupervisorAgent;
import com.liferay.ai.hub.internal.memory.ChatMemoryProviderUtil;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.concurrent.NoticeableExecutorService;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyInheritableThreadLocalCallable;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.internal.InternalAgent;
import dev.langchain4j.agentic.supervisor.SupervisorContextStrategy;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiChatModel;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
@Component(service = SupervisorAgent.class)
public class SupervisorAgentImpl implements SupervisorAgent {

	@Override
	public void invoke(AgentContext agentContext) {
		InternalAgent[] internalAgents = _createInternalAgents(agentContext);

		_noticeableExecutorService.submit(
			new CompanyInheritableThreadLocalCallable<>(
				() -> {
					try (VertexAiGeminiChatModel vertexAiGeminiChatModel =
							VertexAiGeminiChatModel.builder(
							).location(
								"europe-central2"
							).modelName(
								"gemini-2.5-flash-lite"
							).project(
								"ai-hub-liferay"
							).build()) {

						_invoke(
							agentContext, internalAgents,
							vertexAiGeminiChatModel);
					}
					catch (Exception exception) {
						_log.error(exception);

						SseUtil.send(
							"I cannot fulfill this request.",
							"Chat Message Sent", null,
							agentContext.getSseEventSinkKey());
					}

					return null;
				}));
	}

	@Activate
	protected void activate() {
		_noticeableExecutorService = _portalExecutorManager.getPortalExecutor(
			SupervisorAgentImpl.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_noticeableExecutorService.shutdown();
	}

	private InternalAgent[] _createInternalAgents(AgentContext agentContext) {
		try {
			Page<ObjectEntry> page = _objectEntryManager.getObjectEntries(
				agentContext.getCompanyId(),
				_objectDefinitionLocalService.getObjectDefinition(
					agentContext.getCompanyId(), "AIHubAgentDefinition"),
				null, null, agentContext.getDTOConverterContext(),
				"(active eq true)", Pagination.of(1, 20), null, null);

			InternalAgentFactory internalAgentFactory =
				new InternalAgentFactory(
					agentContext, _workflowDefinitionManager,
					_workflowInstanceManager);

			return TransformUtil.transformToArray(
				page.getItems(), internalAgentFactory::create,
				InternalAgent.class);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return new InternalAgent[0];
	}

	private void _invoke(
		AgentContext agentContext, InternalAgent[] internalAgents,
		VertexAiGeminiChatModel vertexAiGeminiChatModel) {

		dev.langchain4j.agentic.supervisor.SupervisorAgent supervisorAgent =
			AgenticServices.supervisorBuilder(
			).chatMemoryProvider(
				memoryId -> ChatMemoryProviderUtil.provide(
					agentContext.getSseEventSinkKey())
			).chatModel(
				vertexAiGeminiChatModel
			).contextGenerationStrategy(
				SupervisorContextStrategy.CHAT_MEMORY_AND_SUMMARIZATION
			).maxAgentsInvocations(
				5
			).subAgents(
				(Object[])internalAgents
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

	private NoticeableExecutorService _noticeableExecutorService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

}