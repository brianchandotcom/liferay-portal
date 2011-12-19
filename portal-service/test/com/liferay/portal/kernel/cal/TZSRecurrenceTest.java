/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.cal;

import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.util.RecurrenceTestCase;
import com.liferay.portal.kernel.util.Time;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Angelo Jefferson
 */
public class TZSRecurrenceTest extends RecurrenceTestCase {

	public final Duration durationHour = getDuration(0, 0, 1, 0, 0);

	// EST is -5:00 UTC, during DST -4:00 UTC

	public final TimeZone EST = TimeZone.getTimeZone("America/New_York");

	public void testTimeZone() {
		completeTimeZoneTest(true);
		completeTimeZoneTest(false);
	}

	public void testTZSRecurrenceAcrossDaylightSavings() {

		// Event starting within DST match by first Sunday of month

		// Creates a recurrence object based on the UTC start date of the event

		TZSRecurrence firstSunOfMonth = getMonthByDayRecurrence(
			insideDSTStart, durationHour, SUNDAY, 1, 1, EST);

		// UTC Time for midnight with given timeZone of recurrence

		Calendar insideDST1 = getCalendar(2011, AUGUST, 7, 4, 0);
		Calendar outsideDST1 = getCalendar(2011, DECEMBER, 4, 4, 0);

		limitTest(insideDST1, durationHour, firstSunOfMonth);
		limitTest(outsideDST1, durationHour, firstSunOfMonth);

		// Events starting within DST match By date of the second

		TZSRecurrence secondOfMonth = getMonthByMonthDayRecurrence(
			insideDSTStart, durationHour, 2, 1, EST);

		Calendar insideDST2 = getCalendar(2011, AUGUST, 2, 4, 0);
		Calendar outsideDST2 = getCalendar(2011, DECEMBER, 2, 4, 0);

		limitTest(insideDST2, durationHour, secondOfMonth);
		limitTest(outsideDST2, durationHour, secondOfMonth);

		// Event starting outside of DST matched by first Monday of month

		TZSRecurrence firstMonOfMonth = getMonthByDayRecurrence(
			outsideDSTStart, durationHour, MONDAY, 1, 1, EST);

		Calendar insideDST3 = getCalendar(2011, AUGUST, 1, 5, 0);
		Calendar outsideDST3 = getCalendar(2011, DECEMBER, 5, 5, 0);

		limitTest(insideDST3, durationHour, firstMonOfMonth);
		limitTest(outsideDST3, durationHour, firstMonOfMonth);

		// Event starting outside of DST matched by the date of the sixth

		TZSRecurrence sixthOfMonth = getMonthByMonthDayRecurrence(
			outsideDSTStart, durationHour, 6, 1, EST);

		Calendar insideDST4 = getCalendar(2011, AUGUST, 6, 5, 0);
		Calendar outsideDST4 = getCalendar(2011, DECEMBER, 6, 5, 0);

		limitTest(insideDST4, durationHour, sixthOfMonth);
		limitTest(outsideDST4, durationHour, sixthOfMonth);
	}

	protected void assertTZSRecurrenceEquals(
		boolean expected, TZSRecurrence recurrence, Calendar calendar) {

		assertEquals(expected, recurrence.isInRecurrence(calendar));
	}

	protected void completeTimeZoneTest(boolean completeTimeZone) {
		TimeZone timeZone = EST;

		if (!completeTimeZone) {
			timeZone = (TimeZone) TimeZone.getTimeZone("UTC").clone();
			timeZone.setID("America/New_York");
			timeZone.setRawOffset(-5 * 60 * 60 * 1000);
		}

		TZSRecurrence recurJuly = getMonthByDayRecurrence(
			insideDSTStart, durationHour, SUNDAY, 1, 1, timeZone);

		Calendar insideDST = getCalendar(2013, JULY, 7, 4, 0);

		limitTest(insideDST, durationHour, recurJuly);

		TZSRecurrence firstSunOfMonth = getMonthByDayRecurrence(
			outsideDSTStart, durationHour, SUNDAY, 1, 1, timeZone);

		Calendar outsideDST = getCalendar(2013, JANUARY, 6, 5, 0);

		limitTest(outsideDST, durationHour, firstSunOfMonth);
	}

	protected void limitTest(
		Calendar candidate, Duration duration, TZSRecurrence recur) {

		long candidateTimeInMills = candidate.getTimeInMillis();
		long durationInMills = duration.getInterval();

		Calendar beforeRecurPeriod = Calendar.getInstance();
		Calendar startOfRecurPeriod = Calendar.getInstance();
		Calendar endOfRecurPeriod = Calendar.getInstance();
		Calendar afterRecurPeriod = Calendar.getInstance();

		beforeRecurPeriod.setTimeInMillis(candidateTimeInMills - Time.MINUTE);
		startOfRecurPeriod.setTimeInMillis(candidateTimeInMills + Time.MINUTE);
		endOfRecurPeriod.setTimeInMillis(
			candidateTimeInMills + durationInMills - Time.MINUTE);
		afterRecurPeriod.setTimeInMillis(
			candidateTimeInMills + durationInMills + Time.MINUTE);

		assertTZSRecurrenceEquals(false, recur, beforeRecurPeriod);
		assertTZSRecurrenceEquals(true, recur, startOfRecurPeriod);
		assertTZSRecurrenceEquals(true, recur, endOfRecurPeriod);
		assertTZSRecurrenceEquals(false, recur, afterRecurPeriod);
	}

	protected TZSRecurrence getMonthByDayRecurrence(
		Calendar dtStart, Duration duration, int day, int position,
		int interval, TimeZone tz) {

		TZSRecurrence recurrence = new TZSRecurrence(
			dtStart, duration, Recurrence.MONTHLY);

		DayAndPosition[] days = {new DayAndPosition(day, position)};

		recurrence.setByDay(days);
		recurrence.setInterval(interval);
		recurrence.setTimeZone(tz);

		return recurrence;
	}

	protected TZSRecurrence getMonthByMonthDayRecurrence(
		Calendar dtStart, Duration duration, int monthDay, int interval,
		TimeZone tz) {

		TZSRecurrence recurrence = new TZSRecurrence(
			dtStart, duration, Recurrence.MONTHLY);

		int[] monthDays = {monthDay};

		recurrence.setByMonthDay(monthDays);
		recurrence.setInterval(interval);
		recurrence.setTimeZone(tz);

		return recurrence;
	}

	protected Calendar insideDSTStart = getCalendar(2011, JULY, 3, 4, 0);
	protected Calendar outsideDSTStart = getCalendar(2011, FEBRUARY, 7, 5, 0);

}