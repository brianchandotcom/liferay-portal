/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.guardrail;

import com.liferay.ai.hub.guardrail.ModelArmorHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailResult;

/**
 * @author João Victor Alves
 */
public class InputGuardrailImpl implements InputGuardrail {

	public InputGuardrailImpl(
		long companyId, String externalReferenceCode, String location,
		ModelArmorHandler modelArmorHandler) {

		_companyId = companyId;
		_externalReferenceCode = externalReferenceCode;
		_location = location;
		_modelArmorHandler = modelArmorHandler;
	}

	@Override
	public InputGuardrailResult validate(UserMessage userMessage) {
		try {
			if (_modelArmorHandler.hasUserPromptViolation(
					_companyId, _externalReferenceCode, _location,
					userMessage.singleText())) {

				return fatal(
					"Input rejected: Security policy violation detected.");
			}

			return success();
		}
		catch (Exception exception) {
			_log.error(exception);

			return fatal(
				"Input rejected: Unable to validate against security policy.");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InputGuardrailImpl.class);

	private final long _companyId;
	private final String _externalReferenceCode;
	private final String _location;
	private final ModelArmorHandler _modelArmorHandler;

}