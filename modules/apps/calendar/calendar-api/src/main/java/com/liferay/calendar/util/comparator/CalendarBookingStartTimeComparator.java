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