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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;

import java.util.Calendar;

import org.junit.Test;

/**
 * @author Douglas Wong
 */
public class RecurrenceWeeklyByDayTest extends RecurrenceTestCase {

	@Test
	public void testRecurrence() {
		Recurrence recurrence = getRecurrence(dtStart, durationOneHour, 1);

		Calendar beforeRecurrenceFirstDay = getCalendar(
			2008, FEBRUARY, 8, 22, 9);
		Calendar beforeRecurrenceSecondDay = getCalendar(
			2008, FEBRUARY, 9, 22, 9);

		assertRecurrenceEquals(false, recurrence, beforeRecurrenceFirstDay);
		assertRecurrenceEquals(false, recurrence, beforeRecurrenceSecondDay);

		Calendar duringRecurrenceFirstDay1 = getCalendar(
			2008, FEBRUARY, 8, 22, 10);
		Calendar duringRecurrenceFirstDay2 = getCalendar(
			2008, FEBRUARY, 15, 22, 15);
		Calendar duringRecurrenceSecondDay1 = getCalendar(
			2008, FEBRUARY, 9, 22, 10);
		Calendar duringRecurrenceSecondDay2 = getCalendar(
			2008, FEBRUARY, 16, 22, 15);

		assertRecurrenceEquals(true, recurrence, duringRecurrenceFirstDay1);
		assertRecurrenceEquals(true, recurrence, duringRecurrenceFirstDay2);
		assertRecurrenceEquals(true, recurrence, duringRecurrenceSecondDay1);
		assertRecurrenceEquals(true, recurrence, duringRecurrenceSecondDay2);

		Calendar afterRecurrenceFirstDay = getCalendar(
			2008, FEBRUARY, 8, 23, 10);
		Calendar afterRecurrenceSecondDay = getCalendar(
			2008, FEBRUARY, 9, 23, 10);

		assertRecurrenceEquals(false, recurrence, afterRecurrenceFirstDay);
		assertRecurrenceEquals(false, recurrence, afterRecurrenceSecondDay);
	}

	@Test
	public void testRecurrenceCrossDates() {
		Recurrence recurrence = getRecurrence(dtStart, durationTwoHours, 1);

		Calendar duringRecurrence = getCalendar(2008, FEBRUARY, 9, 0, 9);

		assertRecurrenceEquals(true, recurrence, duringRecurrence);

		Calendar afterRecurrence = getCalendar(2008, FEBRUARY, 9, 0, 10);

		assertRecurrenceEquals(false, recurrence, afterRecurrence);
	}

	@Test
	public void testRecurrenceWithInterval() {
		Recurrence recurrence = getRecurrence(dtStart, durationOneHour, 2);

		Calendar duringRecurrenceFirstDay1 = getCalendar(
			2008, FEBRUARY, 8, 22, 15);
		Calendar duringRecurrenceFirstDay2 = getCalendar(
			2008, FEBRUARY, 15, 22, 15);
		Calendar duringRecurrenceFirstDay3 = getCalendar(
			2008, FEBRUARY, 22, 22, 15);
		Calendar duringRecurrenceFirstDay4 = getCalendar(
			2008, FEBRUARY, 29, 22, 15);
		Calendar duringRecurrenceSecondDay1 = getCalendar(
			2008, FEBRUARY, 9, 22, 15);
		Calendar duringRecurrenceSecondDay2 = getCalendar(
			2008, FEBRUARY, 16, 22, 15);
		Calendar duringRecurrenceSecondDay3 = getCalendar(
			2008, FEBRUARY, 23, 22, 15);
		Calendar duringRecurrenceSecondDay4 = getCalendar(
			2008, MARCH, 1, 22, 15);

		assertRecurrenceEquals(true, recurrence, duringRecurrenceFirstDay1);
		assertRecurrenceEquals(false, recurrence, duringRecurrenceFirstDay2);
		assertRecurrenceEquals(true, recurrence, duringRecurrenceFirstDay3);
		assertRecurrenceEquals(false, recurrence, duringRecurrenceFirstDay4);
		assertRecurrenceEquals(true, recurrence, duringRecurrenceSecondDay1);
		assertRecurrenceEquals(false, recurrence, duringRecurrenceSecondDay2);
		assertRecurrenceEquals(true, recurrence, duringRecurrenceSecondDay3);
		assertRecurrenceEquals(false, recurrence, duringRecurrenceSecondDay4);
	}

	@Test
	public void testRecurrenceWithUntilDate() {
		Recurrence recurrence = getRecurrence(dtStart, durationOneHour, 1);

		recurrence.setUntil(getCalendar(2008, FEBRUARY, 15, 22, 0));

		Calendar beforeUntil = getCalendar(2008, FEBRUARY, 8, 22, 15);

		assertRecurrenceEquals(true, recurrence, beforeUntil);

		Calendar afterUntil = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(false, recurrence, afterUntil);
	}

	protected Recurrence getRecurrence(
		Calendar dtStart, Duration duration, int interval) {

		Recurrence recurrence = new Recurrence(
			dtStart, duration, Recurrence.WEEKLY);

		DayAndPosition[] days = {
			new DayAndPosition(FRIDAY, 0), new DayAndPosition(SATURDAY, 0)
		};

		recurrence.setByDay(days);

		recurrence.setInterval(interval);

		return recurrence;
	}

}