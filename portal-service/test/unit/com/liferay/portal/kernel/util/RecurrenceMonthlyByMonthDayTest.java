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

import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;

import java.util.Calendar;

import org.junit.Test;

/**
 * @author Douglas Wong
 */
public class RecurrenceMonthlyByMonthDayTest extends RecurrenceTestCase {

	@Test
	public void testRecurrence() {
		Recurrence recurrence = getRecurrence(dtStart, durationOneHour, 15, 1);

		Calendar beforeRecurrence = getCalendar(2008, FEBRUARY, 15, 22, 9);

		assertRecurrenceEquals(false, recurrence, beforeRecurrence);

		Calendar duringRecurrence1 = getCalendar(2008, FEBRUARY, 15, 22, 10);
		Calendar duringRecurrence2 = getCalendar(2008, MARCH, 15, 22, 10);

		assertRecurrenceEquals(true, recurrence, duringRecurrence1);
		assertRecurrenceEquals(true, recurrence, duringRecurrence2);

		Calendar afterRecurrence = getCalendar(2008, FEBRUARY, 15, 23, 10);

		assertRecurrenceEquals(false, recurrence, afterRecurrence);
	}

	@Test
	public void testRecurrenceCrossDates() {
		Recurrence recurrence = getRecurrence(dtStart, durationTwoHours, 15, 1);

		Calendar duringRecurrence = getCalendar(2008, FEBRUARY, 16, 0, 9);

		assertRecurrenceEquals(true, recurrence, duringRecurrence);

		Calendar afterRecurrence = getCalendar(2008, FEBRUARY, 16, 0, 10);

		assertRecurrenceEquals(false, recurrence, afterRecurrence);
	}

	@Test
	public void testRecurrenceCrossWeeks() {
		Recurrence recurrence = getRecurrence(
			dtStart, durationCrossWeek, 15, 1);

		Calendar duringRecurrence = getCalendar(2008, FEBRUARY, 23, 22, 9);

		assertRecurrenceEquals(true, recurrence, duringRecurrence);

		Calendar afterRecurrence = getCalendar(2008, FEBRUARY, 23, 22, 10);

		assertRecurrenceEquals(false, recurrence, afterRecurrence);
	}

	@Test
	public void testRecurrenceCrossYears() {
		Recurrence recurrence = getRecurrence(
			dtStart, durationCrossWeek, 29, 1);

		Calendar duringRecurrence = getCalendar(2009, JANUARY, 6, 22, 9);

		assertRecurrenceEquals(true, recurrence, duringRecurrence);

		Calendar afterRecurrence = getCalendar(2009, JANUARY, 6, 22, 10);

		assertRecurrenceEquals(false, recurrence, afterRecurrence);
	}

	@Test
	public void testRecurrenceWithInterval() {
		Recurrence recurrence = getRecurrence(dtStart, durationOneHour, 15, 2);

		Calendar duringRecurrence1 = getCalendar(2008, FEBRUARY, 15, 22, 15);
		Calendar duringRecurrence2 = getCalendar(2008, MARCH, 15, 22, 15);
		Calendar duringRecurrence3 = getCalendar(2008, APRIL, 15, 22, 15);
		Calendar duringRecurrence4 = getCalendar(2008, MAY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, duringRecurrence1);
		assertRecurrenceEquals(false, recurrence, duringRecurrence2);
		assertRecurrenceEquals(true, recurrence, duringRecurrence3);
		assertRecurrenceEquals(false, recurrence, duringRecurrence4);
	}

	@Test
	public void testRecurrenceWithLeapYear() {
		Recurrence recurrence = getRecurrence(dtStart, durationTwoHours, 29, 1);

		Calendar duringRecurrence1 = getCalendar(2008, FEBRUARY, 29, 22, 10);
		Calendar duringRecurrence2 = getCalendar(2008, MARCH, 1, 0, 9);
		Calendar duringRecurrence3 = getCalendar(2012, FEBRUARY, 29, 22, 15);

		assertRecurrenceEquals(true, recurrence, duringRecurrence1);
		assertRecurrenceEquals(true, recurrence, duringRecurrence2);
		assertRecurrenceEquals(true, recurrence, duringRecurrence3);

		Calendar afterRecurrence1 = getCalendar(2008, MARCH, 1, 0, 10);
		Calendar afterRecurrence2 = getCalendar(2009, FEBRUARY, 28, 22, 15);
		Calendar afterRecurrence3 = getCalendar(2009, MARCH, 1, 0, 0);

		assertRecurrenceEquals(false, recurrence, afterRecurrence1);
		assertRecurrenceEquals(false, recurrence, afterRecurrence2);
		assertRecurrenceEquals(false, recurrence, afterRecurrence3);
	}

	@Test
	public void testRecurrenceWithUntilDate() {
		Recurrence recurrence = getRecurrence(dtStart, durationOneHour, 15, 1);

		recurrence.setUntil(getCalendar(2008, MARCH, 15, 22, 0));

		Calendar beforeUntil = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, beforeUntil);

		Calendar afterUntil = getCalendar(2008, MARCH, 15, 22, 15);

		assertRecurrenceEquals(false, recurrence, afterUntil);
	}

	protected Recurrence getRecurrence(
		Calendar dtStart, Duration duration, int monthDay, int interval) {

		Recurrence recurrence = new Recurrence(
			dtStart, duration, Recurrence.MONTHLY);

		int[] monthDays = {monthDay};

		recurrence.setByMonthDay(monthDays);
		recurrence.setInterval(interval);

		return recurrence;
	}

}