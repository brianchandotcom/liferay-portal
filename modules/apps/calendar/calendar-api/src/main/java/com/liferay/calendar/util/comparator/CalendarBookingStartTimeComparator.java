/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.util.comparator;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Inácio Nery
 */
public class CalendarBookingStartTimeComparator
	extends OrderByComparator<CalendarBooking> {

	public static final String ORDER_BY_ASC =
		"CalendarBooking.startTime, CalendarBooking.createDate ASC";

	public static final String ORDER_BY_DESC =
		"CalendarBooking.startTime, CalendarBooking.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"startTime", "createDate"};

	public CalendarBookingStartTimeComparator() {
		this(false);
	}

	public CalendarBookingStartTimeComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CalendarBooking calendarBooking1, CalendarBooking calendarBooking2) {

		long startTime1 = calendarBooking1.getStartTime();
		long startTime2 = calendarBooking2.getStartTime();

		int value = 0;

		if (startTime1 < startTime2) {
			value = -1;
		}
		else if (startTime1 > startTime2) {
			value = 1;
		}

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

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}