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

package com.liferay.notifications.web.internal.util.comparator;

import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alejandro Tardín
 */
public class UserNotificationEventTimestampComparator
	extends OrderByComparator<UserNotificationEvent> {

	public static final String ORDER_BY_ASC =
		"UserNotificationEvent.timestamp ASC";

	public static final String ORDER_BY_DESC =
		"UserNotificationEvent.timestamp DESC";

	public static final String[] ORDER_BY_FIELDS = {"timestamp"};

	public UserNotificationEventTimestampComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		UserNotificationEvent userNotificationEvent1,
		UserNotificationEvent userNotificationEvent2) {

		int value = Long.compare(
			userNotificationEvent1.getTimestamp(),
			userNotificationEvent2.getTimestamp());

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