/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.guardrail;

import com.liferay.ai.hub.guardrail.ModelArmorHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailResult;

/**
 * @author João Victor Alves
 */
public class OutputGuardrailImpl implements OutputGuardrail {

	public OutputGuardrailImpl(
		long companyId, String externalReferenceCode, String location,
		ModelArmorHandler modelArmorHandler) {

		_companyId = companyId;
		_externalReferenceCode = externalReferenceCode;
		_location = location;
		_modelArmorHandler = modelArmorHandler;
	}

	@Override
	public OutputGuardrailResult validate(AiMessage aiMessage) {
		try {
			if (_modelArmorHandler.hasModelResponseViolation(
					_companyId, _externalReferenceCode, _location,
					aiMessage.text())) {

				return fatal("Response blocked: Contains restricted content.");
			}

			return success();
		}
		catch (Exception exception) {
			_log.error(exception);

			return fatal(
				"Response blocked: Unable to validate against security " +
					"policy.");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OutputGuardrailImpl.class);

	private final long _companyId;
	private final String _externalReferenceCode;
	private final String _location;
	private final ModelArmorHandler _modelArmorHandler;

}