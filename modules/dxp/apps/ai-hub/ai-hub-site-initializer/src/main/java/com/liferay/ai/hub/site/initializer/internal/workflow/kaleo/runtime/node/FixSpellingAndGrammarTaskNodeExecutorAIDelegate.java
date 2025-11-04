/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal.workflow.kaleo.runtime.node;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.node.TaskNodeExecutorAIDelegate;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;

import java.io.Serializable;

import java.util.Map;
import java.util.function.BiConsumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(service = TaskNodeExecutorAIDelegate.class)
public class FixSpellingAndGrammarTaskNodeExecutorAIDelegate
	implements TaskNodeExecutorAIDelegate {

	@Override
	public void execute(
		ExecutionContext executionContext, String taskNodeName) {

		Map<String, Serializable> workflowContext =
			executionContext.getWorkflowContext();

		VertexAiGeminiStreamingChatModel vertexAiGeminiStreamingChatModel =
			VertexAiGeminiStreamingChatModel.builder(
			).project(
				""
			).location(
				"us-central1"
			).modelName(
				"gemini-2.5-flash-lite"
			).build();

		Assistant assistant = AiServices.builder(
			Assistant.class
		).systemMessageProvider(
			object -> StringBundler.concat(
				"You are an expert linguistic editor. Your sole task is to ",
				"correct all grammatical, spelling, and punctuation errors in ",
				"the provided text while preserving its meaning, tone, and ",
				"style. Do not alter structure or wording beyond what is ",
				"necessary for grammatical precision and natural fluency. ",
				"Output only the corrected text, with no explanations or ",
				"commentary. If the text is already correct, return it ",
				"unchanged.")
		).streamingChatModel(
			vertexAiGeminiStreamingChatModel
		).build();

		assistant.rewrite(
			"This is the text to be fixed: " +
				GetterUtil.getString(workflowContext.get("text"))
		).onCompleteResponse(
			response -> _completeResponse(
				response, executionContext, vertexAiGeminiStreamingChatModel)
		).onError(
			throwable -> vertexAiGeminiStreamingChatModel.close()
		).start();
	}

	@Override
	public String getKey() {
		return "fixSpellingAndGrammar";
	}

	public interface Assistant {

		public TokenStream rewrite(String text);

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
			BiConsumer<String, String> consumer =
				(BiConsumer)workflowContext.get("broadcast");

			consumer.accept(
				aiMessage.text(),
				String.valueOf(kaleoInstanceToken.getKaleoInstanceId()));

			_workflowInstanceManager.updateWorkflowContext(
				kaleoInstanceToken.getCompanyId(),
				kaleoInstanceToken.getKaleoInstanceId(), workflowContext);

			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				executionContext.getKaleoTaskInstanceToken();

			_workflowTaskManager.completeWorkflowTask(
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

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}