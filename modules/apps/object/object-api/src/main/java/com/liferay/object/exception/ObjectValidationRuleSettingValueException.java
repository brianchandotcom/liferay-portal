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

	public ObjectValidationRuleSettingValueException(String msg) {
		super(msg);
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

}