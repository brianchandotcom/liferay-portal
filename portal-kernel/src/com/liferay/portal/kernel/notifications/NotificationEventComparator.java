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

package com.liferay.portal.kernel.notifications;

import java.util.Comparator;

/**
 * @author Edward Han
 */
public class NotificationEventComparator
	implements Comparator<NotificationEvent> {

	public NotificationEventComparator() {
		this(true);
	}

	public NotificationEventComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		NotificationEvent notificationEvent1,
		NotificationEvent notificationEvent2) {

		if (notificationEvent1.equals(notificationEvent2)) {
			return 0;
		}

		long value =
			notificationEvent1.getDeliverBy() -
				notificationEvent2.getDeliverBy();

		if (value == 0) {
			value =
				notificationEvent1.getTimestamp() -
					notificationEvent2.getTimestamp();
		}

		if (value == 0) {
			value =
				notificationEvent1.hashCode() - notificationEvent2.hashCode();
		}

		if (_ascending) {
			return (int)value;
		}

		return (int)-value;
	}

	private final boolean _ascending;

}