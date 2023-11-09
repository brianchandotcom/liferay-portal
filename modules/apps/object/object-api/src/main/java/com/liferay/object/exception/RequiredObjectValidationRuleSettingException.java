/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Collections;
import java.util.List;

/**
 * @author Mateus Santana
 */
public class RequiredObjectValidationRuleSettingException
	extends PortalException {

	public List<Object> getArguments() {
		return _arguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public static class
		MustNotDeleteObjectValidationRuleSettingPublishedObjectDefinition
			extends RequiredObjectValidationRuleSettingException {

		public MustNotDeleteObjectValidationRuleSettingPublishedObjectDefinition(
			String objectValidationRuleSettingName) {

			super(
				Collections.singletonList(objectValidationRuleSettingName),
				String.format(
					"The object validation rule setting \"%s\" cannot be " +
						"deleted after the object definition is published",
					objectValidationRuleSettingName),
				"the-object-validation-rule-setting-x-cannot-be-deleted-" +
					"after-the-object-definition-is-published");
		}

	}

	private RequiredObjectValidationRuleSettingException(
		List<Object> arguments, String message, String messageKey) {

		super(message);

		_arguments = arguments;
		_messageKey = messageKey;
	}

	private final List<Object> _arguments;
	private final String _messageKey;

}