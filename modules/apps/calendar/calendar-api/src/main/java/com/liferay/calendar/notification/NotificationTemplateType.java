/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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