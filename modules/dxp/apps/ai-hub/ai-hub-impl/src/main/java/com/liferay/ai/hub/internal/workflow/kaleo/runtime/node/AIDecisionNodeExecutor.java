/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node;

import com.liferay.ai.hub.internal.assistant.handler.AssistantHandlerContext;
import com.liferay.ai.hub.internal.assistant.handler.AssistantHandlerUtil;
import com.liferay.ai.hub.internal.mcp.tool.provider.MCPToolProviderUtil;
import com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util.ContentRetrieverUtil;
import com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util.ToolsUtil;
import com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util.VariablesUtil;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowNodeManager;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeSetting;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.graph.PathElement;
import com.liferay.portal.workflow.kaleo.runtime.node.BaseNodeExecutor;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeSettingLocalService;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.invocation.InvocationParameters;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiStreamingChatModel;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author João Victor Alves
 */
@Component(service = NodeExecutor.class)
public class AIDecisionNodeExecutor extends BaseNodeExecutor {

	@Override
	public NodeType getNodeType() {
		return NodeType.AI_DECISION;
	}

	public class Tools {

		@Tool(
			"Complete the workflow node by proceeding to the chosen transition"
		)
		public void completeWorkflowNode(
				InvocationParameters invocationParameters,
				@P(
					"A brief, one-sentence justification for the chosen transition."
				)
				String reason,
				@P("Transition name") String transitionName)
			throws PortalException {

			ExecutionContext executionContext = invocationParameters.get(
				"executionContext");

			Map<String, Serializable> workflowContext =
				executionContext.getWorkflowContext();

			workflowContext.put("reason", reason);

			PermissionThreadLocal.setPermissionChecker(
				invocationParameters.get("permissionChecker"));

			KaleoInstanceToken kaleoInstanceToken =
				executionContext.getKaleoInstanceToken();

			_workflowNodeManager.completeWorkflowNode(
				kaleoInstanceToken.getCompanyId(),
				kaleoInstanceToken.getUserId(),
				kaleoInstanceToken.getKaleoInstanceTokenId(), transitionName,
				workflowContext, false);
		}

	}

	@Override
	protected boolean doEnter(
		KaleoNode currentKaleoNode, ExecutionContext executionContext) {

		return true;
	}

	@Override
	protected void doExecute(
			KaleoNode currentKaleoNode, ExecutionContext executionContext,
			List<PathElement> remainingPathElements)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		Map<String, String> kaleoNodeSettingValues = new HashMap<>();

		List<KaleoNodeSetting> kaleoNodeSettings =
			_kaleoNodeSettingLocalService.getKaleoNodeSettings(
				currentKaleoNode.getKaleoNodeId());

		for (KaleoNodeSetting kaleoNodeSetting : kaleoNodeSettings) {
			kaleoNodeSettingValues.put(
				kaleoNodeSetting.getName(), kaleoNodeSetting.getValue());
		}

		ServiceContext serviceContext = executionContext.getServiceContext();
		VertexAiGeminiStreamingChatModel vertexAiGeminiStreamingChatModel =
			VertexAiGeminiStreamingChatModel.builder(
			).project(
				"ai-hub-liferay"
			).location(
				"us-central1"
			).modelName(
				"gemini-2.5-flash-lite"
			).build();
		Map<String, Serializable> workflowContext =
			executionContext.getWorkflowContext();

		AssistantHandlerUtil.handle(
			AssistantHandlerContext.builder(
			).contentRetriever(
				ContentRetrieverUtil.createContentRetriever(
					GetterUtil.getString(workflowContext.get("accessToken")),
					kaleoNodeSettingValues,
					GetterUtil.getString(workflowContext.get("userToken")))
			).invocationParameters(
				InvocationParameters.from(
					Map.of(
						"executionContext", executionContext,
						"permissionChecker",
						PermissionThreadLocal.getPermissionChecker()))
			).onCompleteResponse(
				response -> vertexAiGeminiStreamingChatModel.close()
			).onError(
				throwable -> vertexAiGeminiStreamingChatModel.close()
			).systemMessageProvider(
				object -> VariablesUtil.applyInputVariables(
					executionContext, "prompt", kaleoNodeSettingValues)
			).tools(
				new Tools()
			).toolProvider(
				MCPToolProviderUtil.create(
					kaleoInstanceToken.getCompanyId(), _dtoConverterRegistry,
					kaleoInstanceToken.getGroupId(), serviceContext.getLocale(),
					ToolsUtil.getMCPServerExternalReferenceCodes(
						_jsonFactory, kaleoNodeSettingValues),
					_objectEntryManager, serviceContext.getUserId())
			).userMessage(
				VariablesUtil.applyInputVariables(
					executionContext, "userMessage", kaleoNodeSettingValues)
			).vertexAiGeminiStreamingChatModel(
				vertexAiGeminiStreamingChatModel
			).build(),
			"default");
	}

	@Override
	protected void doExit(
			KaleoNode currentKaleoNode, ExecutionContext executionContext,
			List<PathElement> remainingPathElements)
		throws PortalException {

		KaleoTransition kaleoTransition = null;

		if (Validator.isNull(executionContext.getTransitionName())) {
			kaleoTransition = currentKaleoNode.getDefaultKaleoTransition();
		}
		else {
			kaleoTransition = currentKaleoNode.getKaleoTransition(
				executionContext.getTransitionName());
		}

		remainingPathElements.add(
			new PathElement(
				null, kaleoTransition.getTargetKaleoNode(),
				new ExecutionContext(
					executionContext.getKaleoInstanceToken(),
					executionContext.getWorkflowContext(),
					executionContext.getServiceContext())));
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private KaleoNodeSettingLocalService _kaleoNodeSettingLocalService;

	@Reference(
		target = "(object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private WorkflowNodeManager _workflowNodeManager;

}