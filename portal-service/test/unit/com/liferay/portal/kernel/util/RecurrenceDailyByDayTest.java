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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;

import java.util.Calendar;

import org.junit.Test;

/**
 * @author Douglas Wong
 */
public class RecurrenceDailyByDayTest extends RecurrenceTestCase {

	@Test
	public void testDtStart() {
		Recurrence recurrence = getRecurrence(dtStart, durationOneHour);

		Calendar beforeDtStart = getCalendar(2008, FEBRUARY, 5, 22, 9);

		assertRecurrenceEquals(false, recurrence, beforeDtStart);

		Calendar duringDtStart1 = getCalendar(2008, FEBRUARY, 5, 22, 10);
		Calendar duringDtStart2 = getCalendar(2008, FEBRUARY, 5, 23, 9);

		assertRecurrenceEquals(true, recurrence, duringDtStart1);
		assertRecurrenceEquals(true, recurrence, duringDtStart2);

		Calendar afterDtStart = getCalendar(2008, FEBRUARY, 5, 23, 10);

		assertRecurrenceEquals(false, recurrence, afterDtStart);
	}

	@Test
	public void testRecurrence() {
		Recurrence recurrence = getRecurrence(dtStart, durationOneHour);

		Calendar beforeRecurrence = getCalendar(2008, FEBRUARY, 5, 22, 9);

		assertRecurrenceEquals(false, recurrence, beforeRecurrence);

		Calendar duringRecurrence1 = getCalendar(2008, FEBRUARY, 5, 22, 10);
		Calendar duringRecurrence2 = getCalendar(2008, FEBRUARY, 6, 22, 10);
		Calendar duringRecurrence3 = getCalendar(2008, FEBRUARY, 8, 22, 10);
		Calendar duringRecurrence4 = getCalendar(2008, FEBRUARY, 11, 22, 10);
		Calendar duringRecurrence5 = getCalendar(2008, FEBRUARY, 12, 22, 10);
		Calendar duringRecurrence6 = getCalendar(2008, FEBRUARY, 15, 22, 10);

		assertRecurrenceEquals(true, recurrence, duringRecurrence1);
		assertRecurrenceEquals(true, recurrence, duringRecurrence2);
		assertRecurrenceEquals(true, recurrence, duringRecurrence3);
		assertRecurrenceEquals(true, recurrence, duringRecurrence4);
		assertRecurrenceEquals(true, recurrence, duringRecurrence5);
		assertRecurrenceEquals(true, recurrence, duringRecurrence6);

		Calendar afterRecurrence = getCalendar(2008, FEBRUARY, 5, 23, 10);
		Calendar duringWeekend1 = getCalendar(2008, FEBRUARY, 9, 22, 10);
		Calendar duringWeekend2 = getCalendar(2008, FEBRUARY, 10, 22, 10);
		Calendar duringWeekend3 = getCalendar(2008, FEBRUARY, 16, 22, 10);
		Calendar duringWeekend4 = getCalendar(2008, FEBRUARY, 17, 22, 10);

		assertRecurrenceEquals(false, recurrence, afterRecurrence);
		assertRecurrenceEquals(false, recurrence, duringWeekend1);
		assertRecurrenceEquals(false, recurrence, duringWeekend2);
		assertRecurrenceEquals(false, recurrence, duringWeekend3);
		assertRecurrenceEquals(false, recurrence, duringWeekend4);
	}

	@Test
	public void testRecurrenceCrossDates() {
		Recurrence recurrence = getRecurrence(dtStart, durationTwoHours);

		Calendar duringRecurrence = getCalendar(2008, FEBRUARY, 6, 0, 9);

		assertRecurrenceEquals(true, recurrence, duringRecurrence);

		Calendar afterRecurrence = getCalendar(2008, FEBRUARY, 6, 0, 10);

		assertRecurrenceEquals(false, recurrence, afterRecurrence);
	}

	@Test
	public void testRecurrenceWithUntilDate() {
		Recurrence recurrence = getRecurrence(dtStart, durationOneHour);

		recurrence.setUntil(getCalendar(2008, FEBRUARY, 6, 22, 0));

		Calendar beforeUntil = getCalendar(2008, FEBRUARY, 5, 22, 15);

		assertRecurrenceEquals(true, recurrence, beforeUntil);

		Calendar afterUntil = getCalendar(2008, FEBRUARY, 6, 22, 15);

		assertRecurrenceEquals(false, recurrence, afterUntil);
	}

	protected Recurrence getRecurrence(Calendar dtStart, Duration duration) {
		Recurrence recurrence = new Recurrence(
			dtStart, duration, Recurrence.DAILY);

		DayAndPosition[] days = {
			new DayAndPosition(MONDAY, 0), new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0), new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		recurrence.setByDay(days);

		return recurrence;
	}

}