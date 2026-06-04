/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.langchain4j.observability.api.listener;

import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import dev.langchain4j.guardrail.OutputGuardrailResult;
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

		completeExceptionally(
			null, outputGuardrailExecutedEvent.duration(),
			outputGuardrailResult, "output");
	}

}