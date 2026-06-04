/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.langchain4j.observability.api.listener;

import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrailRequest;
import dev.langchain4j.guardrail.InputGuardrailResult;
import dev.langchain4j.observability.api.event.InputGuardrailExecutedEvent;
import dev.langchain4j.observability.api.listener.InputGuardrailExecutedListener;

/**
 * @author Pedro Leite
 */
public class InputGuardrailExecutedListenerImpl
	extends BaseGuardrailExecutedListener
	implements InputGuardrailExecutedListener {

	public InputGuardrailExecutedListenerImpl(
		ExecutionContext executionContext) {

		super(executionContext);
	}

	@Override
	public void onEvent(
		InputGuardrailExecutedEvent inputGuardrailExecutedEvent) {

		InputGuardrailResult inputGuardrailResult =
			inputGuardrailExecutedEvent.result();

		if (inputGuardrailResult.isSuccess()) {
			return;
		}

		InputGuardrailRequest inputGuardrailRequest =
			inputGuardrailExecutedEvent.request();

		UserMessage userMessage = inputGuardrailRequest.userMessage();

		completeExceptionally(
			userMessage.singleText(), inputGuardrailExecutedEvent.duration(),
			inputGuardrailResult, "input");
	}

}