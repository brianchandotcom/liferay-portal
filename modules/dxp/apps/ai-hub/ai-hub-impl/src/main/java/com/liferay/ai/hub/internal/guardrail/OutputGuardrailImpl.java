/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.guardrail;

import com.liferay.ai.hub.guardrail.ModelArmorHandler;
import com.liferay.ai.hub.quota.QuotaManager;
import com.liferay.ai.hub.quota.Source;
import com.liferay.ai.hub.quota.Usage;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailResult;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
public class OutputGuardrailImpl implements OutputGuardrail {

	public OutputGuardrailImpl(
		long companyId, String externalReferenceCode, String location,
		ModelArmorHandler modelArmorHandler, QuotaManager quotaManager,
		long userId, Map<String, Serializable> workflowContext) {

		_companyId = companyId;
		_externalReferenceCode = externalReferenceCode;
		_location = location;
		_modelArmorHandler = modelArmorHandler;
		_quotaManager = quotaManager;
		_userId = userId;
		_workflowContext = workflowContext;
	}

	@Override
	public OutputGuardrailResult fatal(String message) {
		SseUtil.send(
			"Model response violates security policy",
			GetterUtil.getString(
				_workflowContext.get("outBoundEventName"), "Chat Message Sent"),
			null,
			GetterUtil.getString(_workflowContext.get("sseEventSinkKey")));

		return OutputGuardrail.super.fatal(message);
	}

	@Override
	public OutputGuardrailResult validate(AiMessage aiMessage) {
		String text = aiMessage.text();

		try {
			String violations = _modelArmorHandler.sanitizeModelResponse(
				_companyId, _externalReferenceCode, _location, text);

			if (Validator.isNotNull(violations)) {
				return fatal(
					JSONUtil.put(
						"modelArmorTemplateExternalReferenceCode",
						_externalReferenceCode
					).put(
						"violations", violations
					).toString());
			}

			return success();
		}
		catch (Exception exception) {
			_log.error(exception);

			return fatal("Unable to validate against security policy");
		}
		finally {
			try {
				_quotaManager.updateUsage(
					_companyId,
					Usage.builder(
					).source(
						Source.MODEL_ARMOR
					).text(
						text
					).build(),
					_userId);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OutputGuardrailImpl.class);

	private final long _companyId;
	private final String _externalReferenceCode;
	private final String _location;
	private final ModelArmorHandler _modelArmorHandler;
	private final QuotaManager _quotaManager;
	private final long _userId;
	private final Map<String, Serializable> _workflowContext;

}