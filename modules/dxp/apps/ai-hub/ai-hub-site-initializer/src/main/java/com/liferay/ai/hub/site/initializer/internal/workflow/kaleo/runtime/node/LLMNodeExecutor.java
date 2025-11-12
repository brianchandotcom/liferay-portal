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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeSetting;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.graph.PathElement;
import com.liferay.portal.workflow.kaleo.runtime.node.BaseNodeExecutor;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeSettingLocalService;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = NodeExecutor.class)
public class LLMNodeExecutor extends BaseNodeExecutor {

	@Override
	public NodeType getNodeType() {
		return NodeType.LLM;
	}

	public interface WritingAssistant {

		public TokenStream rewrite(String userMessage);

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

		WritingAssistant writingAssistant = AiServices.builder(
			WritingAssistant.class
		).systemMessageProvider(
			object -> _applyVariables(
				kaleoNodeSettingsMap, "prompt", executionContext)
		).streamingChatModel(
			vertexAiGeminiStreamingChatModel
		).build();

		writingAssistant.rewrite(
			_applyVariables(
				kaleoNodeSettingsMap, "userMessage", executionContext)
		).onCompleteResponse(
			response -> _completeResponse(
				response, executionContext, vertexAiGeminiStreamingChatModel)
		).onError(
			throwable -> vertexAiGeminiStreamingChatModel.close()
		).start();
	}

	@Override
	protected void doExit(
		KaleoNode currentKaleoNode, ExecutionContext executionContext,
		List<PathElement> remainingPathElements) {
	}

	@Reference
	protected WorkflowInstanceManager workflowInstanceManager;

	@Reference
	protected WorkflowTaskManager workflowTaskManager;

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

	private void _completeResponse(
		ChatResponse chatResponse, ExecutionContext executionContext,
		VertexAiGeminiStreamingChatModel vertexAiGeminiStreamingChatModel) {

		Map<String, Serializable> workflowContext =
			executionContext.getWorkflowContext();

		AiMessage aiMessage = chatResponse.aiMessage();

		workflowContext.put("rewrittenText", aiMessage.text());

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		try {
			BiConsumer<String, String> biConsumer =
				(BiConsumer)workflowContext.get("broadcast");

			biConsumer.accept(
				aiMessage.text(),
				String.valueOf(kaleoInstanceToken.getKaleoInstanceId()));

			workflowInstanceManager.updateWorkflowContext(
				kaleoInstanceToken.getCompanyId(),
				kaleoInstanceToken.getKaleoInstanceId(), workflowContext);

			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				executionContext.getKaleoTaskInstanceToken();

			workflowTaskManager.completeWorkflowTask(
				kaleoTaskInstanceToken.getCompanyId(),
				kaleoTaskInstanceToken.getUserId(),
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(), "end", "",
				executionContext.getWorkflowContext());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
		finally {
			vertexAiGeminiStreamingChatModel.close();
		}
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

}