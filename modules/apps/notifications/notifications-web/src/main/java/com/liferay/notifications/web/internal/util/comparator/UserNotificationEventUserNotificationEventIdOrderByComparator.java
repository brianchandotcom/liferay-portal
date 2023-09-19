/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.web.internal.util.comparator;

import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Roberto Díaz
 */
public class UserNotificationEventUserNotificationEventIdOrderByComparator
	extends OrderByComparator<UserNotificationEvent> {

	public static final String ORDER_BY_ASC =
		"UserNotificationEvent.userNotificationEventId ASC";

	public static final String ORDER_BY_DESC =
		"UserNotificationEvent.userNotificationEventId DESC";

	public static final String[] ORDER_BY_FIELDS = {"userNotificationEventId"};

	public UserNotificationEventUserNotificationEventIdOrderByComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	@Override
	public int compare(
		UserNotificationEvent userNotificationEvent1,
		UserNotificationEvent userNotificationEvent2) {

		int value = Long.compare(
			userNotificationEvent1.getUserNotificationEventId(),
			userNotificationEvent2.getUserNotificationEventId());

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	private final boolean _ascending;

}