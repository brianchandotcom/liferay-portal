/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.test.util;

import com.liferay.calendar.recurrence.Frequency;
import com.liferay.calendar.recurrence.PositionalWeekday;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Adam Brandizzi
 */
public class RecurrenceTestUtil {

	public static Recurrence getDailyRecurrence() {
		return getDailyRecurrence(0, TimeZoneUtil.GMT, null);
	}

	public static Recurrence getDailyRecurrence(Calendar untilJCalendar) {
		return getDailyRecurrence(0, TimeZoneUtil.GMT, untilJCalendar);
	}

	public static Recurrence getDailyRecurrence(int count) {
		return getDailyRecurrence(count, TimeZoneUtil.GMT, null);
	}

	public static Recurrence getDailyRecurrence(int count, TimeZone timeZone) {
		return getDailyRecurrence(count, timeZone, null);
	}

	public static Recurrence getDailyRecurrence(
		int count, TimeZone timeZone, Calendar untilJCalendar) {

		return getRecurrence(count, Frequency.DAILY, timeZone, untilJCalendar);
	}

	public static Recurrence getDailyRecurrence(TimeZone timeZone) {
		return getDailyRecurrence(0, timeZone, null);
	}

	public static Recurrence getDailyRecurrence(
		TimeZone timeZone, Calendar untilJCalendar) {

		return getDailyRecurrence(0, timeZone, untilJCalendar);
	}

	public static Recurrence getRecurrence(
		int count, Frequency frequency, TimeZone timeZone,
		Calendar untilJCalendar) {

		Recurrence recurrence = new Recurrence();

		recurrence.setCount(count);
		recurrence.setFrequency(frequency);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());
		recurrence.setTimeZone(timeZone);
		recurrence.setUntilJCalendar(untilJCalendar);

		return recurrence;
	}

}