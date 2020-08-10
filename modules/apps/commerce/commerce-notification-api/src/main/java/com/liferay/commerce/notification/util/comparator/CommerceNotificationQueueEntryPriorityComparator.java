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

package com.liferay.commerce.notification.util.comparator;

import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationQueueEntryPriorityComparator
	extends OrderByComparator<CommerceNotificationQueueEntry> {

	public static final String ORDER_BY_ASC =
		"CommerceNotificationQueueEntry.priority ASC";

	public static final String ORDER_BY_DESC =
		"CommerceNotificationQueueEntry.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public CommerceNotificationQueueEntryPriorityComparator() {
		this(false);
	}

	public CommerceNotificationQueueEntryPriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry1,
		CommerceNotificationQueueEntry commerceNotificationQueueEntry2) {

		int value = Double.compare(
			commerceNotificationQueueEntry1.getPriority(),
			commerceNotificationQueueEntry2.getPriority());

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
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

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}