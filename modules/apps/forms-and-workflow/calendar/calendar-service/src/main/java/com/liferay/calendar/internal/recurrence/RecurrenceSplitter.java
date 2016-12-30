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

package com.liferay.calendar.internal.recurrence;

import com.liferay.calendar.recurrence.Recurrence;

import java.util.Calendar;

/**
 * {@link RecurrenceSplitter} executes a split operation and returns a
 * {@link RecurrenceSplit} instance as result.
 *
 * @author Adam Brandizzi
 */
public interface RecurrenceSplitter {

	/**
	 * Given a {@link Recurrence} instance, a date to be used as the recurrence
	 * start date, and a date on which the recurrence should be split,
	 * generates a {@link RecurrenceSplit} instance representing the
	 * result.
	 *
	 * @param  recurrence the {@link Recurrence} to be split into two.
	 * @param  startTimeJCalendar the date to be considered for starting the
	 *         original recurrence.
	 * @param  splitTimeJCalendar the date where we expect to split the
	 *         recurrence into two.
	 * @return a {@link RecurrenceSplit} representing the operation result.
	 */
	public RecurrenceSplit split(
		Recurrence recurrence, Calendar startTimeJCalendar,
		Calendar splitTimeJCalendar);

}