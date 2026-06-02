/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.guardrail.listener;

import com.liferay.ai.hub.internal.audit.AuditRouterUtil;
import com.liferay.ai.hub.internal.audit.constants.AIHubEventTypes;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import dev.langchain4j.guardrail.GuardrailResult;

import java.time.Duration;

import java.util.Date;

/**
 * @author Pedro Leite
 */
public abstract class BaseGuardrailExecutedListener {

	public BaseGuardrailExecutedListener(ExecutionContext executionContext) {
		_executionContext = executionContext;
	}

	protected void route(
		String content, Duration duration, GuardrailResult<?> guardrailResult,
		String guardrailType) {

		try {
			KaleoInstanceToken kaleoInstanceToken =
				_executionContext.getKaleoInstanceToken();

			AuditRouterUtil.route(
				WorkflowInstance.class.getName(),
				kaleoInstanceToken.getKaleoInstanceId(),
				AIHubEventTypes.AI_HUB_GUARDRAIL_VIOLATION,
				JSONUtil.put(
					"agentDefinitionExternalReferenceCode",
					MapUtil.getString(
						_executionContext.getWorkflowContext(),
						"agentDefinitionExternalReferenceCode")
				).put(
					"content", content
				).put(
					"duration", duration.toMillis()
				).put(
					"errors",
					JSONUtil.toJSONArray(
						guardrailResult.failures(),
						GuardrailResult.Failure::message)
				).put(
					"guardrailType", guardrailType
				).put(
					"sseEventSinkKey",
					MapUtil.getString(
						_executionContext.getWorkflowContext(),
						"sseEventSinkKey")
				).put(
					"workflowInstanceId",
					kaleoInstanceToken.getKaleoInstanceId()
				),
				new Date(), kaleoInstanceToken.getUserId());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseGuardrailExecutedListener.class);

	private final ExecutionContext _executionContext;

}