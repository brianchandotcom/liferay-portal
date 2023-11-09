/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class ObjectValidationRuleSettingValueException extends PortalException {

	public String getMessageKey() {
		return _messageKey;
	}

	public static class CompositeKeyMustHaveMaxObjectFields
		extends ObjectValidationRuleSettingValueException {

		public CompositeKeyMustHaveMaxObjectFields() {
			super(
				"Add a maximum of five object fields to create unique " +
					"composite keys",
				"add-a-maximum-of-five-object-fields-to-create-unique-" +
					"composite-keys");
		}

	}

	public static class CompositeKeyMustHaveMinObjectFields
		extends ObjectValidationRuleSettingValueException {

		public CompositeKeyMustHaveMinObjectFields() {
			super(
				"Add a minimum of two object fields to create unique " +
					"composite keys",
				"add-a-minimum-of-two-object-fields-to-create-unique-" +
					"composite-keys");
		}

	}

	public static class InvalidValue
		extends ObjectValidationRuleSettingValueException {

		public InvalidValue(
			String objectValidationRuleSettingName,
			String objectValidationRuleSettingValue) {

			super(
				String.format(
					"The value \"%s\" of the object validation rule setting " +
						"\"%s\" is invalid",
					objectValidationRuleSettingValue,
					objectValidationRuleSettingName));
		}

	}

	private ObjectValidationRuleSettingValueException(String message) {
		super(message);
	}

	private ObjectValidationRuleSettingValueException(
		String message, String messageKey) {

		super(message);

		_messageKey = messageKey;
	}

	private String _messageKey;

}