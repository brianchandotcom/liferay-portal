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
 * Executes a split operation and returns a {@link RecurrenceSplit} instance as
 * a result.
 *
 * @author Adam Brandizzi
 */
public interface RecurrenceSplitter {

	/**
	 * Generates a {@link RecurrenceSplit} instance representing the result.
	 *
	 * @param  recurrence the <code>Recurrence</code> (in the
	 *         <code>com.liferay.calendar.api</code> module) to be split into
	 *         two new recurrences
	 * @param  startTimeJCalendar the starting date for the original recurrence
	 * @param  splitTimeJCalendar the date to split the recurrence
	 * @return a {@link RecurrenceSplit} representing the operation result
	 */
	public RecurrenceSplit split(
		Recurrence recurrence, Calendar startTimeJCalendar,
		Calendar splitTimeJCalendar);

}