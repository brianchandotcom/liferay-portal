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

	public final Duration durationHour = getDuration(0,0,1,0,0);

	// EET is +2:00 UTC, during DST +3:00 UTC
	public final TimeZone EET = TimeZone.getTimeZone("Europe/Istanbul");

	// EST is -5:00 UTC, during DST -4:00 UTC
	public final TimeZone EST = TimeZone.getTimeZone("America/New_York");

	public void testBadTimeZoneTZSRecurrence() {

		// Sets up a time zone that does not have a daylight savings component.
		// Liferay passes this version of time zone to TZSRecurrence.

		TimeZone incompleteTimeZone =
			(TimeZone)TimeZone.getTimeZone("UTC").clone();

		incompleteTimeZone.setID("Europe/Istanbul");
		incompleteTimeZone.setRawOffset(2 * 60 * 60 * 1000);

		testTimeZone(incompleteTimeZone);
	}

	public void testGoodTimeZoneTZSRecurrence() {
		testTimeZone(EET);
	}

	public void testAcrossDaylightSavingsTZSRecurrence() {

		// Event starting within DST match by month day
		// UTC Time for midnight with given for timeZone of recurrence

		Calendar firstSundayJulStart = getCalendar(2011, JULY, 3, 4, 0);

		//Creates a recurrence object based on the UTC start date of the event.

		TZSRecurrence recurFirstSunOfMonth = getMonthByDayRecurrence(
			firstSundayJulStart, durationHour, SUNDAY, 1, 1, EST);

		// Liferay creates a matching candidate that has a start time based on
		// the start time of the event.

		Calendar candidateAugSun = getCalendar(2011, AUGUST, 7, 4, 0);
		Calendar candidateDecSun = getCalendar(2011, DECEMBER, 4, 4, 0);

		limitTest(
			candidateAugSun, firstSundayJulStart, durationHour,
			recurFirstSunOfMonth);
		limitTest(
			candidateDecSun, firstSundayJulStart, durationHour,
			recurFirstSunOfMonth);

		// Events starting within DST match By date

		TZSRecurrence recurSecondOfMonth = getMonthByMonthDayRecurrence(
			firstSundayJulStart, durationHour, 2, 1, EST);

		Calendar candidateAugSecond = getCalendar(2011, AUGUST, 2, 4, 0);
		Calendar candidateDecSecond = getCalendar(2011, DECEMBER, 2, 4, 0);

		limitTest(
			candidateAugSecond, firstSundayJulStart, durationHour,
			recurSecondOfMonth);
		limitTest(
			candidateDecSecond, firstSundayJulStart, durationHour,
			recurSecondOfMonth);

		// Event starting outside of DST match by month day

		Calendar firstSundayFebStart = getCalendar(2011, FEBRUARY, 7, 5, 0);

		TZSRecurrence recurFirstMonOfMonth = getMonthByDayRecurrence(
			firstSundayFebStart, durationHour, MONDAY, 1, 1, EST);

		Calendar candidateAugMon = getCalendar(2011, AUGUST, 1, 5, 0);
		Calendar candidateDecMon = getCalendar(2011, DECEMBER, 5, 5, 0);

		limitTest(
			candidateAugMon, firstSundayFebStart, durationHour,
			recurFirstMonOfMonth);
		limitTest(
			candidateDecMon, firstSundayFebStart, durationHour,
			recurFirstMonOfMonth);

		// Event starting outside of DST match by month day

		TZSRecurrence recurSixthOfMonth = getMonthByMonthDayRecurrence(
			firstSundayFebStart, durationHour, 6, 1, EET);

		Calendar candidateAugSixth = getCalendar(2011, AUGUST, 6, 5, 0);
		Calendar candidateDecSixth = getCalendar(2011, DECEMBER, 6, 5, 0);

		limitTest(
			candidateAugSixth, firstSundayFebStart, durationHour,
			recurSixthOfMonth);
		limitTest(
			candidateDecSixth, firstSundayFebStart, durationHour,
			recurSixthOfMonth);
	}

	protected void testTimeZone(TimeZone timeZone) {

		// First Sunday in January 2011

		Calendar candidateJan = getCalendar(2013, JANUARY, 5, 22, 0);
		Calendar firstSundayJanStart = getCalendar(2011, JANUARY, 1, 22, 0);

		TZSRecurrence recurJan = getMonthByDayRecurrence(
			firstSundayJanStart, durationHour, SUNDAY, 1, 1, timeZone);

		limitTest(candidateJan, firstSundayJanStart, durationHour, recurJan);

		// First Sunday in July 2011

		Calendar candidateJuly = getCalendar(2013, JULY, 6, 21, 0);
		Calendar firstSundayJulStart = getCalendar(2011, JULY, 2, 21, 0);

		TZSRecurrence recurJuly = getMonthByDayRecurrence(
			firstSundayJulStart, durationHour, SUNDAY, 1, 1, timeZone);

		limitTest(candidateJuly, firstSundayJulStart, durationHour, recurJuly);
	}

	protected void limitTest(
		Calendar candidate, Calendar startDate, Duration duration,
		TZSRecurrence recur) {

		long candidateTimeInMills = candidate.getTimeInMillis();
		long durationInMills = duration.getInterval();

		Calendar afterRecurPeriod = Calendar.getInstance();
		Calendar beforeRecurPeriod = Calendar.getInstance();
		Calendar endOfRecurPeriod = Calendar.getInstance();
		Calendar startOfRecurPeriod = Calendar.getInstance();

		afterRecurPeriod.setTimeInMillis(
			candidateTimeInMills + durationInMills + Time.MINUTE);
		beforeRecurPeriod.setTimeInMillis(candidateTimeInMills - Time.MINUTE);
		endOfRecurPeriod.setTimeInMillis(
			candidateTimeInMills + durationInMills - Time.MINUTE);
		startOfRecurPeriod.setTimeInMillis(candidateTimeInMills + Time.MINUTE);

		assertRecurrenceEquals(false, recur, afterRecurPeriod);
		assertRecurrenceEquals(false, recur, beforeRecurPeriod);
		assertRecurrenceEquals(true, recur, endOfRecurPeriod);
		assertRecurrenceEquals(true, recur, startOfRecurPeriod);
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

}