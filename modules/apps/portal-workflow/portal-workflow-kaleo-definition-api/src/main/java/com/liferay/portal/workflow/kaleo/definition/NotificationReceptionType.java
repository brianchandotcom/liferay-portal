/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public enum NotificationReceptionType {

	BCC("bcc"), CC("cc"), TO("to");

	public static NotificationReceptionType parse(String value) {
		if (Objects.equals(BCC.getValue(), value)) {
			return BCC;
		}
		else if (Objects.equals(CC.getValue(), value)) {
			return CC;
		}

		return TO;
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private NotificationReceptionType(String value) {
		_value = value;
	}

	private final String _value;

}