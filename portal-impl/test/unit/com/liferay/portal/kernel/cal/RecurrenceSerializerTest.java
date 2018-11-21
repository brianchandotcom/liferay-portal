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

package com.liferay.portal.kernel.cal;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.util.CalendarFactoryImpl;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Steven Smith
 */
public class RecurrenceSerializerTest extends RecurrenceSerializer {

	@Before
	public void setUp() {
		setUpCalendarFactoryUtil();
		setUpFastDateFormatFactoryUtil();
	}

	@Test
	public void testDailyRecurrence() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.DAILY);

		Assert.assertEquals("0 4 3 1/1 * ? *", toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 1/3 * ? *", toCronText(recurrence));
	}

	@Test
	public void testDailyRecurrenceByDay() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.DAILY);

		recurrence.setByDay(_firstMondayDayAndPositions);

		Assert.assertEquals("0 4 3 ? * MON *", toCronText(recurrence));

		recurrence.setByDay(_firstMondayDayAndFirstFridayAndPositions);

		Assert.assertEquals("0 4 3 ? * MON,FRI *", toCronText(recurrence));
	}

	@Test
	public void testMonthlyRecurrence() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.MONTHLY);

		Assert.assertEquals("0 4 3 ? 1/1 ? *", toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? 1/3 ? *", toCronText(recurrence));
	}

	@Test
	public void testMonthlyRecurrenceByFirstDay() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.MONTHLY);

		recurrence.setByDay(_firstMondayDayAndPositions);

		Assert.assertEquals("0 4 3 ? 1/1 MON#0 *", toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? 1/3 MON#0 *", toCronText(recurrence));
	}

	@Test
	public void testMonthlyRecurrenceByLastDay() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.MONTHLY);

		recurrence.setByDay(_lastMondayDayAndPositions);

		Assert.assertEquals("0 4 3 ? 1/1 MONL *", toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? 1/3 MONL *", toCronText(recurrence));
	}

	@Test
	public void testMonthlyRecurrenceByMonthDay() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.MONTHLY);

		recurrence.setByMonthDay(new int[] {15});

		Assert.assertEquals("0 4 3 15 1/1 ? *", toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 15 1/3 ? *", toCronText(recurrence));
	}

	@Test
	public void testNoRecurrence() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.NO_RECURRENCE);

		Assert.assertEquals("0 4 3 2 1 ? 2010", toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 2 1 ? 2010", toCronText(recurrence));
	}

	@Test
	public void testWeeklyNoRecurrence() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.WEEKLY);

		recurrence.setInterval(0);

		Assert.assertEquals("0 4 3 ? * 7 *", toCronText(recurrence));
	}

	@Test
	public void testWeeklyRecurrence() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.WEEKLY);

		Assert.assertEquals("0 4 3 ? * 7/1 *", toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? * 7/3 *", toCronText(recurrence));
	}

	@Test
	public void testWeeklyRecurrenceByDay() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.WEEKLY);

		recurrence.setByDay(_firstMondayDayAndPositions);

		Assert.assertEquals("0 4 3 ? * MON/1 *", toCronText(recurrence));

		recurrence.setByDay(_firstMondayDayAndFirstFridayAndPositions);

		Assert.assertEquals("0 4 3 ? * MON,FRI/1 *", toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? * MON,FRI/3 *", toCronText(recurrence));
	}

	@Test
	public void testYearlyRecurrence() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.YEARLY);

		Assert.assertEquals("0 4 3 ? 1 ? 2010/1", toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? 1 ? 2010/3", toCronText(recurrence));
	}

	@Test
	public void testYearlyRecurrenceByFirstDay() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.YEARLY);

		recurrence.setByMonth(new int[] {0});
		recurrence.setByDay(_firstMondayDayAndPositions);

		Assert.assertEquals("0 4 3 ? 1 MON#0 2010/1", toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? 1 MON#0 2010/3", toCronText(recurrence));
	}

	@Test
	public void testYearlyRecurrenceByLastDay() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.YEARLY);

		recurrence.setByMonth(new int[] {0});
		recurrence.setByDay(_lastMondayDayAndPositions);

		Assert.assertEquals("0 4 3 ? 1 MONL 2010/1", toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? 1 MONL 2010/3", toCronText(recurrence));
	}

	@Test
	public void testYearlyRecurrenceByMonthDay() throws Exception {
		Recurrence recurrence = getRecurrence(
			_recurrenceCalendar, Recurrence.YEARLY);

		recurrence.setByMonth(new int[] {0});
		recurrence.setByMonthDay(new int[] {15});

		Assert.assertEquals("0 4 3 15 1 ? 2010/1", toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 15 1 ? 2010/3", toCronText(recurrence));
	}

	protected Recurrence getRecurrence(Calendar calendar, int recurrenceType) {
		return new Recurrence(
			calendar, new Duration(1, 0, 0, 0), recurrenceType);
	}

	protected void setUpCalendarFactoryUtil() {
		CalendarFactoryUtil calendarFactoryUtil = new CalendarFactoryUtil();

		calendarFactoryUtil.setCalendarFactory(new CalendarFactoryImpl());
	}

	protected void setUpFastDateFormatFactoryUtil() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());
	}

	private static final DayAndPosition[]
		_firstMondayDayAndFirstFridayAndPositions = {
			new DayAndPosition(Calendar.MONDAY, 0),
			new DayAndPosition(Calendar.FRIDAY, 0)
		};
	private static final DayAndPosition[] _firstMondayDayAndPositions = {
		new DayAndPosition(Calendar.MONDAY, 0)
	};
	private static final DayAndPosition[] _lastMondayDayAndPositions = {
		new DayAndPosition(Calendar.MONDAY, -1)
	};
	private static final Calendar _recurrenceCalendar = new GregorianCalendar(
		2010, 0, 2, 3, 4, 5);

}