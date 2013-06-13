/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.RecurrenceTestCase;
import com.liferay.portal.kernel.util.Time;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Angelo Jefferson
 */
public class TZSRecurrenceTest extends RecurrenceTestCase {

	@Test
	public void testCompleteTimeZoneWithinTSZRecurrence() {
		checkWithinTSZRecurrence(_timeZone);
	}

	@Test
	public void testIncompleteTimeZoneWithinTSZRecurrence() {
		checkWithinTSZRecurrence(getIncompleteTimeZone());
	}

	@Test
	public void testInsideDSTTZSRecurrence() {

		// Event starting inside DST matched by first Sunday of the month

		TZSRecurrence firstSunOfMonth = getMonthByDayTSZRecurrence(
			_insideDSTCalendar, _durationHour, SUNDAY, 1, 1, _timeZone);

		checkWithinTSZRecurrence(
			getInsideDSTCalendar(AUGUST, 7), _durationHour, firstSunOfMonth);
		checkWithinTSZRecurrence(
			getInsideDSTCalendar(DECEMBER, 4), _durationHour, firstSunOfMonth);

		// Events starting inside DST matched second day of the month

		TZSRecurrence secondDayOfMonthTSZRecurrence =
			getMonthByMonthDayTSZRecurrence(
				_insideDSTCalendar, _durationHour, 2, 1, _timeZone);

		checkWithinTSZRecurrence(
			getInsideDSTCalendar(AUGUST, 2), _durationHour,
			secondDayOfMonthTSZRecurrence);
		checkWithinTSZRecurrence(
			getInsideDSTCalendar(DECEMBER, 2), _durationHour,
			secondDayOfMonthTSZRecurrence);
	}

	@Test
	public void testOutsideDSTTZSRecurrence() {

		// Event starting outside of DST matched by the first Monday of the
		// month

		TZSRecurrence firstMondayOfMonthTSZRecurrence =
			getMonthByDayTSZRecurrence(
				_outsideDSTCalendar, _durationHour, MONDAY, 1, 1, _timeZone);

		checkWithinTSZRecurrence(
			getOutsideDSTCalendar(AUGUST, 1), _durationHour,
			firstMondayOfMonthTSZRecurrence);
		checkWithinTSZRecurrence(
			getOutsideDSTCalendar(DECEMBER, 5), _durationHour,
			firstMondayOfMonthTSZRecurrence);

		// Event starting outside of DST matched by the sixth day of the month

		TZSRecurrence sixthDayOfMonthTSZRecurrence =
			getMonthByMonthDayTSZRecurrence(
				_outsideDSTCalendar, _durationHour, 6, 1, _timeZone);

		checkWithinTSZRecurrence(
			getOutsideDSTCalendar(AUGUST, 6), _durationHour,
			sixthDayOfMonthTSZRecurrence);
		checkWithinTSZRecurrence(
			getOutsideDSTCalendar(DECEMBER, 6), _durationHour,
			sixthDayOfMonthTSZRecurrence);
	}

	protected void assertTZSRecurrenceEquals(
		boolean expected, TZSRecurrence recurrence, Calendar calendar) {

		Assert.assertEquals(expected, recurrence.isInRecurrence(calendar));
	}

	protected void checkWithinTSZRecurrence(
		Calendar calendar, Duration duration, TZSRecurrence tszRecurrence) {

		Calendar afterTSZRecurrenceCalendar = Calendar.getInstance();

		afterTSZRecurrenceCalendar.setTimeInMillis(
			calendar.getTimeInMillis() + duration.getInterval() + Time.MINUTE);

		assertTZSRecurrenceEquals(
			false, tszRecurrence, afterTSZRecurrenceCalendar);

		Calendar beforeTSZRecurrenceCalendar = Calendar.getInstance();

		beforeTSZRecurrenceCalendar.setTimeInMillis(
			calendar.getTimeInMillis() - Time.MINUTE);

		assertTZSRecurrenceEquals(
			false, tszRecurrence, beforeTSZRecurrenceCalendar);

		Calendar endOfTSZRecurrenceCalendar = Calendar.getInstance();

		endOfTSZRecurrenceCalendar.setTimeInMillis(
			calendar.getTimeInMillis() + duration.getInterval() - Time.MINUTE);

		assertTZSRecurrenceEquals(
			true, tszRecurrence, endOfTSZRecurrenceCalendar);

		Calendar startOfTSZRecurrenceCalendar = Calendar.getInstance();

		startOfTSZRecurrenceCalendar.setTimeInMillis(
			calendar.getTimeInMillis() + Time.MINUTE);

		assertTZSRecurrenceEquals(
			true, tszRecurrence, startOfTSZRecurrenceCalendar);
	}

	protected void checkWithinTSZRecurrence(TimeZone timeZone) {
		TZSRecurrence insideDSTTSZRecurrence = getMonthByDayTSZRecurrence(
			_insideDSTCalendar, _durationHour, SUNDAY, 1, 1, timeZone);

		Calendar insideDSTCalendar = getCalendar(2013, JULY, 7, 4, 0);

		checkWithinTSZRecurrence(
			insideDSTCalendar, _durationHour, insideDSTTSZRecurrence);

		TZSRecurrence outsideDSTTSZRecurrence = getMonthByDayTSZRecurrence(
			_outsideDSTCalendar, _durationHour, SUNDAY, 1, 1, timeZone);

		Calendar outsideDSTCalendar = getCalendar(2013, JANUARY, 6, 5, 0);

		checkWithinTSZRecurrence(
			outsideDSTCalendar, _durationHour, outsideDSTTSZRecurrence);
	}

	protected TimeZone getIncompleteTimeZone() {

		// An incomplete time zone is missing the daylight savings settings and
		// other information

		TimeZone timeZone = TimeZone.getTimeZone("UTC");

		timeZone = (TimeZone)timeZone.clone();

		timeZone.setID("America/New_York");
		timeZone.setRawOffset((int)Time.HOUR * -5);

		return timeZone;
	}

	protected Calendar getInsideDSTCalendar(int month, int date) {

		// Returns a candidate calendar for an event that has a start date
		// inside DST. Set the hour to 4 because it will be midnight in New York
		// (EST) but 4 am at Greenwich (UTC).

		return getCalendar(2011, month, date, 4, 0);
	}

	protected TZSRecurrence getMonthByDayTSZRecurrence(
		Calendar dtStart, Duration duration, int day, int position,
		int interval, TimeZone timeZone) {

		// Returns a month by day (e.g. first Monday, second Tuesday of every
		// month) recurrence object based on the UTC start date of the event

		TZSRecurrence tszRecurrence = new TZSRecurrence(
			dtStart, duration, Recurrence.MONTHLY);

		DayAndPosition[] dayAndPositions = {new DayAndPosition(day, position)};

		tszRecurrence.setByDay(dayAndPositions);

		tszRecurrence.setInterval(interval);
		tszRecurrence.setTimeZone(timeZone);

		return tszRecurrence;
	}

	protected TZSRecurrence getMonthByMonthDayTSZRecurrence(
		Calendar dtStart, Duration duration, int monthDay, int interval,
		TimeZone timeZone) {

		// Returns a month by month day (e.g. the 5th, or the 6th of every
		// month) recurrence object based on the UTC start date of the event

		TZSRecurrence tszRecurrence = new TZSRecurrence(
			dtStart, duration, Recurrence.MONTHLY);

		tszRecurrence.setByMonthDay(new int[] {monthDay});
		tszRecurrence.setInterval(interval);
		tszRecurrence.setTimeZone(timeZone);

		return tszRecurrence;
	}

	protected Calendar getOutsideDSTCalendar(int month, int date) {

		// Returns a candidate calendar for an event that has a start date
		// outside DST. Set the hour to 5 because it will be midnight in New
		// York (EST) but 5 am at Greenwich (UTC).

		return getCalendar(2011, month, date, 5, 0);
	}

	private Duration _durationHour = getDuration(0, 0, 1, 0, 0);
	private Calendar _insideDSTCalendar = getCalendar(2011, JULY, 3, 4, 0);
	private Calendar _outsideDSTCalendar = getCalendar(2011, FEBRUARY, 7, 5, 0);
	private TimeZone _timeZone = TimeZone.getTimeZone("America/New_York");

}