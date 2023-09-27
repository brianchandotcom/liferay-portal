/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class ObjectValidationRuleSettingNameException extends PortalException {

	public static class MissingRequiredName
		extends ObjectValidationRuleSettingNameException {

		public MissingRequiredName(String objectValidationRuleSettingName) {
			super(
				String.format(
					"The object validation rule setting \"%s\" is required",
					objectValidationRuleSettingName));
		}

	}

	public static class NotAllowedName
		extends ObjectValidationRuleSettingNameException {

		public NotAllowedName(String objectValidationRuleSettingName) {
			super(
				String.format(
					"The object validation rule setting \"%s\" is not allowed",
					objectValidationRuleSettingName));
		}

	}

	private ObjectValidationRuleSettingNameException(String msg) {
		super(msg);
	}

}