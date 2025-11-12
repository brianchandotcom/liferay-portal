/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal.workflow.kaleo.runtime.node;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeSetting;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.KaleoSignaler;
import com.liferay.portal.workflow.kaleo.runtime.graph.PathElement;
import com.liferay.portal.workflow.kaleo.runtime.node.BaseNodeExecutor;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeSettingLocalService;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.invocation.InvocationParameters;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Iterator;
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

	public interface DecisionAssistant {

		public TokenStream decide(
			@UserMessage String userMessage,
			InvocationParameters invocationParameters);

	}

	public class Tools {

		@Tool(
			"Complete the workflow node by proceeding to the chosen transition"
		)
		public void completeWorkflowNode(
				@P(
					"A brief, one-sentence justification for the chosen transition."
				)
				String reason,
				@P("Transition name") String transitionName,
				InvocationParameters parameters)
			throws WorkflowException {

			ExecutionContext executionContext = parameters.get(
				"executionContext");

			Map<String, Serializable> workflowContext =
				executionContext.getWorkflowContext();

			workflowContext.put("reason", reason);

			PermissionThreadLocal.setPermissionChecker(
				parameters.get("permissionChecker"));

			try {
				_kaleoSignaler.signalExit(
					transitionName, executionContext, false);
			}
			catch (Exception exception) {
				throw new WorkflowException(
					"Unable to signal next transition", exception);
			}
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
		List<PathElement> remainingPathElements) {

		VertexAiGeminiStreamingChatModel vertexAiGeminiStreamingChatModel =
			VertexAiGeminiStreamingChatModel.builder(
			).project(
				"ai-hub-liferay"
			).location(
				"us-central1"
			).modelName(
				"gemini-2.5-flash-lite"
			).build();

		Map<String, String> kaleoNodeSettingsMap = new HashMap<>();

		List<KaleoNodeSetting> kaleoNodeSettings =
			_kaleoNodeSettingLocalService.getKaleoNodeSettings(
				currentKaleoNode.getKaleoNodeId());

		for (KaleoNodeSetting kaleoNodeSetting : kaleoNodeSettings) {
			kaleoNodeSettingsMap.put(
				kaleoNodeSetting.getName(), kaleoNodeSetting.getValue());
		}

		DecisionAssistant decisionAssistant = AiServices.builder(
			DecisionAssistant.class
		).systemMessageProvider(
			object -> _applyVariables(
				kaleoNodeSettingsMap, "prompt", executionContext)
		).streamingChatModel(
			vertexAiGeminiStreamingChatModel
		).tools(
			new Tools()
		).build();

		decisionAssistant.decide(
			_applyVariables(
				kaleoNodeSettingsMap, "userMessage", executionContext),
			InvocationParameters.from(
				Map.of(
					"executionContext", executionContext, "permissionChecker",
					PermissionThreadLocal.getPermissionChecker()))
		).onCompleteResponse(
			response -> vertexAiGeminiStreamingChatModel.close()
		).onError(
			throwable -> vertexAiGeminiStreamingChatModel.close()
		).start();
	}

	@Override
	protected void doExit(
			KaleoNode currentKaleoNode, ExecutionContext executionContext,
			List<PathElement> remainingPathElements)
		throws PortalException {

		String transitionName = executionContext.getTransitionName();

		KaleoTransition kaleoTransition = null;

		if (Validator.isNull(transitionName)) {
			kaleoTransition = currentKaleoNode.getDefaultKaleoTransition();
		}
		else {
			kaleoTransition = currentKaleoNode.getKaleoTransition(
				transitionName);
		}

		ExecutionContext newExecutionContext = new ExecutionContext(
			executionContext.getKaleoInstanceToken(),
			executionContext.getWorkflowContext(),
			executionContext.getServiceContext());

		PathElement pathElement = new PathElement(
			null, kaleoTransition.getTargetKaleoNode(), newExecutionContext);

		remainingPathElements.add(pathElement);
	}

	private String _applyVariables(
		Map<String, String> kaleoNodeSettingsMap, String kaleoNodeSettingName,
		ExecutionContext executionContext) {

		Map<String, String> inputVariables = _getInputVariables(
			kaleoNodeSettingsMap, executionContext.getWorkflowContext());

		String message = kaleoNodeSettingsMap.get(kaleoNodeSettingName);

		for (Map.Entry<String, String> entry : inputVariables.entrySet()) {
			message = StringUtil.replace(
				message, "{{" + entry.getKey() + "}}", entry.getValue());
		}

		return message;
	}

	private Map<String, String> _getInputVariables(
		Map<String, String> kaleoNodeSettingsMap,
		Map<String, Serializable> workflowContext) {

		String inputVariablesString = kaleoNodeSettingsMap.get(
			"inputVariables");

		if (inputVariablesString == null) {
			return Map.of();
		}

		Map<String, String> inputVariables = new HashMap<>();

		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(
				inputVariablesString);

			Iterator<JSONObject> iterator = jsonArray.iterator();

			iterator.forEachRemaining(
				jsonObject -> {
					String name = jsonObject.getString("name");

					inputVariables.put(
						name, GetterUtil.getString(workflowContext.get(name)));
				});
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}

		return inputVariables;
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private KaleoNodeSettingLocalService _kaleoNodeSettingLocalService;

	@Reference
	private KaleoSignaler _kaleoSignaler;

}