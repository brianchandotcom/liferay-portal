/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Mateus Santana
 */
public class RequiredObjectValidationRuleSettingException
	extends PortalException {

	public RequiredObjectValidationRuleSettingException(
		String message, String messageKey) {

		super(message);

		_messageKey = messageKey;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public static class MustNotDeleteObjectValidationRuleSettingCompositeKey
		extends RequiredObjectValidationRuleSettingException {

		public MustNotDeleteObjectValidationRuleSettingCompositeKey() {
			super(
				"Fields cannot be deleted from unique composite keys after " +
					"the definition is published",
				"fields-cannot-be-deleted-from-unique-composite-keys-after-" +
					"the-definition-is-published");
		}

	}

	private final String _messageKey;

}