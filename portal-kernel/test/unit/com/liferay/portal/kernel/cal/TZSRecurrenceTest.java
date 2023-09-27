/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void testCompleteTimeZoneWithinTZSRecurrence() {
		checkWithinTZSRecurrence(_timeZone);
	}

	@Test
	public void testIncompleteTimeZoneWithinTZSRecurrence() {
		checkWithinTZSRecurrence(getIncompleteTimeZone());
	}

	@Test
	public void testInsideDSTTZSRecurrence() {

		// Event starting inside DST matched by first Sunday of the month

		TZSRecurrence firstSunOfMonth = getMonthByDayTZSRecurrence(
			_insideDSTCalendar, _durationHour, SUNDAY, 1, 1, _timeZone);

		checkWithinTZSRecurrence(
			getInsideDSTCalendar(AUGUST, 7), _durationHour, firstSunOfMonth);
		checkWithinTZSRecurrence(
			getInsideDSTCalendar(DECEMBER, 4), _durationHour, firstSunOfMonth);

		// Events starting inside DST matched second day of the month

		TZSRecurrence secondDayOfMonthTZSRecurrence =
			getMonthByMonthDayTZSRecurrence(
				_insideDSTCalendar, _durationHour, 2, 1, _timeZone);

		checkWithinTZSRecurrence(
			getInsideDSTCalendar(AUGUST, 2), _durationHour,
			secondDayOfMonthTZSRecurrence);
		checkWithinTZSRecurrence(
			getInsideDSTCalendar(DECEMBER, 2), _durationHour,
			secondDayOfMonthTZSRecurrence);
	}

	@Test
	public void testOutsideDSTTZSRecurrence() {

		// Event starting outside of DST matched by the first Monday of the
		// month

		TZSRecurrence firstMondayOfMonthTZSRecurrence =
			getMonthByDayTZSRecurrence(
				_outsideDSTCalendar, _durationHour, MONDAY, 1, 1, _timeZone);

		checkWithinTZSRecurrence(
			getOutsideDSTCalendar(AUGUST, 1), _durationHour,
			firstMondayOfMonthTZSRecurrence);
		checkWithinTZSRecurrence(
			getOutsideDSTCalendar(DECEMBER, 5), _durationHour,
			firstMondayOfMonthTZSRecurrence);

		// Event starting outside of DST matched by the sixth day of the month

		TZSRecurrence sixthDayOfMonthTZSRecurrence =
			getMonthByMonthDayTZSRecurrence(
				_outsideDSTCalendar, _durationHour, 6, 1, _timeZone);

		checkWithinTZSRecurrence(
			getOutsideDSTCalendar(AUGUST, 6), _durationHour,
			sixthDayOfMonthTZSRecurrence);
		checkWithinTZSRecurrence(
			getOutsideDSTCalendar(DECEMBER, 6), _durationHour,
			sixthDayOfMonthTZSRecurrence);
	}

	protected void assertTZSRecurrenceEquals(
		boolean expected, TZSRecurrence recurrence, Calendar calendar) {

		Assert.assertEquals(expected, recurrence.isInRecurrence(calendar));
	}

	protected void checkWithinTZSRecurrence(
		Calendar calendar, Duration duration, TZSRecurrence tzsRecurrence) {

		Calendar afterTZSRecurrenceCalendar = Calendar.getInstance();

		afterTZSRecurrenceCalendar.setTimeInMillis(
			calendar.getTimeInMillis() + duration.getInterval() + Time.MINUTE);

		assertTZSRecurrenceEquals(
			false, tzsRecurrence, afterTZSRecurrenceCalendar);

		Calendar beforeTZSRecurrenceCalendar = Calendar.getInstance();

		beforeTZSRecurrenceCalendar.setTimeInMillis(
			calendar.getTimeInMillis() - Time.MINUTE);

		assertTZSRecurrenceEquals(
			false, tzsRecurrence, beforeTZSRecurrenceCalendar);

		Calendar endOfTZSRecurrenceCalendar = Calendar.getInstance();

		endOfTZSRecurrenceCalendar.setTimeInMillis(
			calendar.getTimeInMillis() + duration.getInterval() - Time.MINUTE);

		assertTZSRecurrenceEquals(
			true, tzsRecurrence, endOfTZSRecurrenceCalendar);

		Calendar startOfTZSRecurrenceCalendar = Calendar.getInstance();

		startOfTZSRecurrenceCalendar.setTimeInMillis(
			calendar.getTimeInMillis() + Time.MINUTE);

		assertTZSRecurrenceEquals(
			true, tzsRecurrence, startOfTZSRecurrenceCalendar);
	}

	protected void checkWithinTZSRecurrence(TimeZone timeZone) {
		TZSRecurrence insideDSTTZSRecurrence = getMonthByDayTZSRecurrence(
			_insideDSTCalendar, _durationHour, SUNDAY, 1, 1, timeZone);

		Calendar insideDSTCalendar = getCalendar(2013, JULY, 7, 4, 0);

		checkWithinTZSRecurrence(
			insideDSTCalendar, _durationHour, insideDSTTZSRecurrence);

		TZSRecurrence outsideDSTTZSRecurrence = getMonthByDayTZSRecurrence(
			_outsideDSTCalendar, _durationHour, SUNDAY, 1, 1, timeZone);

		Calendar outsideDSTCalendar = getCalendar(2013, JANUARY, 6, 5, 0);

		checkWithinTZSRecurrence(
			outsideDSTCalendar, _durationHour, outsideDSTTZSRecurrence);
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

	protected TZSRecurrence getMonthByDayTZSRecurrence(
		Calendar dtStart, Duration duration, int day, int position,
		int interval, TimeZone timeZone) {

		// Returns a month by day (e.g. first Monday, second Tuesday of every
		// month) recurrence object based on the UTC start date of the event

		TZSRecurrence tzsRecurrence = new TZSRecurrence(
			dtStart, duration, Recurrence.MONTHLY);

		tzsRecurrence.setByDay(
			new DayAndPosition[] {new DayAndPosition(day, position)});
		tzsRecurrence.setInterval(interval);
		tzsRecurrence.setTimeZone(timeZone);

		return tzsRecurrence;
	}

	protected TZSRecurrence getMonthByMonthDayTZSRecurrence(
		Calendar dtStart, Duration duration, int monthDay, int interval,
		TimeZone timeZone) {

		// Returns a month by month day (e.g. the 5th, or the 6th of every
		// month) recurrence object based on the UTC start date of the event

		TZSRecurrence tzsRecurrence = new TZSRecurrence(
			dtStart, duration, Recurrence.MONTHLY);

		tzsRecurrence.setByMonthDay(new int[] {monthDay});
		tzsRecurrence.setInterval(interval);
		tzsRecurrence.setTimeZone(timeZone);

		return tzsRecurrence;
	}

	protected Calendar getOutsideDSTCalendar(int month, int date) {

		// Returns a candidate calendar for an event that has a start date
		// outside DST. Set the hour to 5 because it will be midnight in New
		// York (EST) but 5 am at Greenwich (UTC).

		return getCalendar(2011, month, date, 5, 0);
	}

	private final Duration _durationHour = getDuration(0, 0, 1, 0, 0);
	private final Calendar _insideDSTCalendar = getCalendar(
		2011, JULY, 3, 4, 0);
	private final Calendar _outsideDSTCalendar = getCalendar(
		2011, FEBRUARY, 7, 5, 0);
	private final TimeZone _timeZone = TimeZone.getTimeZone("America/New_York");

}