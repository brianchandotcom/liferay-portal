/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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