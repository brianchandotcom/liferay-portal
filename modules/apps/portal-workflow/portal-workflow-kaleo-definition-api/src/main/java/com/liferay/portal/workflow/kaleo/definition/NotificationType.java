/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public enum NotificationType {

	EMAIL("email"),
	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	IM("im"),
	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	PRIVATE_MESSAGE("private-message"),
	PUSH_NOTIFICATION("push-notification"),
	USER_NOTIFICATION("user-notification");

	public static NotificationType parse(String value)
		throws KaleoDefinitionValidationException {

		if (Objects.equals(EMAIL.getValue(), value)) {
			return EMAIL;
		}
		else if (Objects.equals(PUSH_NOTIFICATION.getValue(), value)) {
			return PUSH_NOTIFICATION;
		}
		else if (Objects.equals(USER_NOTIFICATION.getValue(), value)) {
			return USER_NOTIFICATION;
		}

		throw new KaleoDefinitionValidationException.InvalidNotificationType(
			value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private NotificationType(String value) {
		_value = value;
	}

	private final String _value;

}