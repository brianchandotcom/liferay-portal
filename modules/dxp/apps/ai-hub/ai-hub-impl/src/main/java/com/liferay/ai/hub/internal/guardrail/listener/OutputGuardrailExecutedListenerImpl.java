/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.guardrail.listener;

import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrailRequest;
import dev.langchain4j.guardrail.OutputGuardrailResult;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.observability.api.event.OutputGuardrailExecutedEvent;
import dev.langchain4j.observability.api.listener.OutputGuardrailExecutedListener;

/**
 * @author Pedro Leite
 */
public class OutputGuardrailExecutedListenerImpl
	extends BaseGuardrailExecutedListener
	implements OutputGuardrailExecutedListener {

	public OutputGuardrailExecutedListenerImpl(
		ExecutionContext executionContext) {

		super(executionContext);
	}

	@Override
	public void onEvent(
		OutputGuardrailExecutedEvent outputGuardrailExecutedEvent) {

		OutputGuardrailResult outputGuardrailResult =
			outputGuardrailExecutedEvent.result();

		if (outputGuardrailResult.isSuccess()) {
			return;
		}

		OutputGuardrailRequest outputGuardrailRequest =
			outputGuardrailExecutedEvent.request();

		ChatResponse chatResponse = outputGuardrailRequest.responseFromLLM();

		AiMessage aiMessage = chatResponse.aiMessage();

		route(
			aiMessage.text(), outputGuardrailExecutedEvent.duration(),
			outputGuardrailResult, "output");
	}

}