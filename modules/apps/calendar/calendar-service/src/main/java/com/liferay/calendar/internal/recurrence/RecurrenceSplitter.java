/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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