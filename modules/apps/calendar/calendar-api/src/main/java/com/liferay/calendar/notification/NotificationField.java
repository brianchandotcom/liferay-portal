/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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