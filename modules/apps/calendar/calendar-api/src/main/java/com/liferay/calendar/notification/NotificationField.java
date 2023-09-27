/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.notification;

import java.util.Objects;

/**
 * @author Adam Brandizzi
 */
public enum NotificationField {

	BODY("body"), SUBJECT("subject");

	public static NotificationField parse(String value) {
		if (Objects.equals(BODY.getValue(), value)) {
			return BODY;
		}
		else if (Objects.equals(SUBJECT.getValue(), value)) {
			return SUBJECT;
		}

		throw new IllegalArgumentException("Invalid value " + value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private NotificationField(String value) {
		_value = value;
	}

	private final String _value;

}