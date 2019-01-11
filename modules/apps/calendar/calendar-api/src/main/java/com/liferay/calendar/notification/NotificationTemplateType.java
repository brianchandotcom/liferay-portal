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
 * @author Eduardo Lundgren
 * @author Pier Paolo Ramon
 */
public enum NotificationTemplateType {

	DECLINE("decline"), INVITE("invite"), INSTANCE_DELETED("instance-deleted"),
	MOVED_TO_TRASH("moved-to-trash"), REMINDER("reminder"), UPDATE("update");

	public static NotificationTemplateType parse(String value) {
		if (Objects.equals(DECLINE.getValue(), value)) {
			return DECLINE;
		}
		else if (Objects.equals(INVITE.getValue(), value)) {
			return INVITE;
		}
		else if (Objects.equals(INSTANCE_DELETED.getValue(), value)) {
			return INSTANCE_DELETED;
		}
		else if (Objects.equals(MOVED_TO_TRASH.getValue(), value)) {
			return MOVED_TO_TRASH;
		}
		else if (Objects.equals(REMINDER.getValue(), value)) {
			return REMINDER;
		}
		else if (Objects.equals(UPDATE.getValue(), value)) {
			return UPDATE;
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

	private NotificationTemplateType(String value) {
		_value = value;
	}

	private final String _value;

}