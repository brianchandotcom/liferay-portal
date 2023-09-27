/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.util;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.impl.CalendarBookingImpl;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.recurrence.Weekday;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class RecurrenceUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetLastCalendarBookingInstance() {
		Calendar lastInstanceStartTimeJCalendar = getJan2016Calendar(23);

		List<CalendarBooking> calendarBookings = getRecurringCalendarBookings(
			getJan2016Calendar(1),
			"RRULE:FREQ=DAILY;INTERVAL=1;UNTIL=20160116\n" +
				"EXDATE;TZID=\"UTC\";VALUE=DATE:20160105",
			getJan2016Calendar(5), null, getJan2016Calendar(17),
			"RRULE:FREQ=DAILY;INTERVAL=1;COUNT=5",
			lastInstanceStartTimeJCalendar,
			"RRULE:FREQ=DAILY;INTERVAL=1;COUNT=3");

		CalendarBooking calendarBooking =
			RecurrenceUtil.getLastInstanceCalendarBooking(calendarBookings);

		Assert.assertEquals(
			calendarBooking.getStartTime(),
			lastInstanceStartTimeJCalendar.getTimeInMillis());

		Recurrence recurrence = calendarBooking.getRecurrenceObj();

		Assert.assertNull(recurrence.getUntilJCalendar());

		Assert.assertTrue(recurrence.getCount() == 3);
	}

	@Test
	public void testGetLastCalendarBookingInstanceBetweenCalendarBookingsWithSameRecurringCalendarBookingIdAndExceptionOnFirst() {
		Calendar lastInstanceStartTimeJCalendar = getJan2016Calendar(2);

		List<CalendarBooking> calendarBookings = getRecurringCalendarBookings(
			getJan2016Calendar(1),
			"RRULE:FREQ=DAILY;INTERVAL=1;UNTIL=20160101\n" +
				"EXDATE;TZID=\"UTC\";VALUE=DATE:20160101",
			lastInstanceStartTimeJCalendar,
			"RRULE:FREQ=DAILY;INTERVAL=1;UNTIL=20160103\n" +
				"EXDATE;TZID=\"UTC\";VALUE=DATE:20160101");

		CalendarBooking calendarBooking20160101 = calendarBookings.get(0);
		CalendarBooking calendarBooking20160102 = calendarBookings.get(1);

		calendarBooking20160102.setRecurringCalendarBookingId(
			calendarBooking20160101.getRecurringCalendarBookingId());

		CalendarBooking lastInstanceCalendarBooking =
			RecurrenceUtil.getLastInstanceCalendarBooking(calendarBookings);

		Assert.assertEquals(
			lastInstanceCalendarBooking.getStartTime(),
			lastInstanceStartTimeJCalendar.getTimeInMillis());

		Recurrence recurrence20160101 =
			calendarBooking20160101.getRecurrenceObj();

		assertSameDay(
			getJan2016Calendar(1), recurrence20160101.getUntilJCalendar());

		Recurrence recurrence20160102 =
			calendarBooking20160102.getRecurrenceObj();

		assertSameDay(
			getJan2016Calendar(3), recurrence20160102.getUntilJCalendar());
	}

	@Test
	public void testGetLastCalendarBookingInstanceReturnsUnboundRecurring() {
		Calendar lastInstanceStartTimeJCalendar = getJan2016Calendar(23);

		List<CalendarBooking> calendarBookings = getRecurringCalendarBookings(
			getJan2016Calendar(1), "RRULE:FREQ=DAILY;INTERVAL=1;UNTIL=20160116",
			getJan2016Calendar(17), "RRULE:FREQ=DAILY;INTERVAL=1;COUNT=5",
			lastInstanceStartTimeJCalendar, "RRULE:FREQ=DAILY;INTERVAL=1");

		CalendarBooking calendarBooking =
			RecurrenceUtil.getLastInstanceCalendarBooking(calendarBookings);

		Assert.assertEquals(
			calendarBooking.getStartTime(),
			lastInstanceStartTimeJCalendar.getTimeInMillis());

		Recurrence recurrence = calendarBooking.getRecurrenceObj();

		Assert.assertNull(recurrence.getUntilJCalendar());

		Assert.assertTrue(recurrence.getCount() == 0);
	}

	@Test
	public void testGetLastCalendarBookingInstanceWithExceptionOnLast() {
		Calendar lastInstanceStartTimeJCalendar = getJan2016Calendar(1);

		List<CalendarBooking> calendarBookings = getRecurringCalendarBookings(
			lastInstanceStartTimeJCalendar,
			"RRULE:FREQ=DAILY;INTERVAL=1;UNTIL=20160116\n" +
				"EXDATE;TZID=\"UTC\";VALUE=DATE:20160105",
			getJan2016Calendar(5), null);

		CalendarBooking calendarBooking =
			RecurrenceUtil.getLastInstanceCalendarBooking(calendarBookings);

		Assert.assertEquals(
			calendarBooking.getStartTime(),
			lastInstanceStartTimeJCalendar.getTimeInMillis());

		Recurrence recurrence = calendarBooking.getRecurrenceObj();

		assertSameDay(getJan2016Calendar(16), recurrence.getUntilJCalendar());

		Assert.assertTrue(recurrence.getCount() == 0);
	}

	@Test
	public void testGetLastCalendarBookingInstanceWithExceptionOnLastDay() {
		Calendar lastInstanceStartTimeJCalendar = getJan2016Calendar(1);

		List<CalendarBooking> calendarBookings = getRecurringCalendarBookings(
			lastInstanceStartTimeJCalendar,
			"RRULE:FREQ=DAILY;INTERVAL=1;UNTIL=20160116\n" +
				"EXDATE;TZID=\"UTC\";VALUE=DATE:20160116",
			getJan2016Calendar(16), null);

		CalendarBooking calendarBooking =
			RecurrenceUtil.getLastInstanceCalendarBooking(calendarBookings);

		Assert.assertEquals(
			calendarBooking.getStartTime(),
			lastInstanceStartTimeJCalendar.getTimeInMillis());

		Recurrence recurrence = calendarBooking.getRecurrenceObj();

		assertSameDay(getJan2016Calendar(16), recurrence.getUntilJCalendar());

		Assert.assertTrue(recurrence.getCount() == 0);
	}

	@Test
	public void testInTimeZoneDoesNotUpdateExceptionJCalendarsInSameDay() {
		Recurrence recurrence = RecurrenceSerializer.deserialize(
			"RRULE:FREQ=DAILY;INTERVAL=1\n" +
				"EXDATE;TZID=\"UTC\";VALUE=DATE:20151225,20151231",
			_utcTimeZone);

		List<Calendar> exceptionJCalendars =
			recurrence.getExceptionJCalendars();

		Calendar exceptionJCalendar = exceptionJCalendars.get(0);

		Assert.assertEquals(2015, exceptionJCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			Calendar.DECEMBER, exceptionJCalendar.get(Calendar.MONTH));
		Assert.assertEquals(25, exceptionJCalendar.get(Calendar.DAY_OF_MONTH));

		exceptionJCalendar = exceptionJCalendars.get(1);

		Assert.assertEquals(2015, exceptionJCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			Calendar.DECEMBER, exceptionJCalendar.get(Calendar.MONTH));
		Assert.assertEquals(31, exceptionJCalendar.get(Calendar.DAY_OF_MONTH));

		Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			2015, Calendar.DECEMBER, 11, 10, 0, 0, 0, _utcTimeZone);

		recurrence = RecurrenceUtil.inTimeZone(
			recurrence, startTimeJCalendar, _losAngelesTimeZone);

		exceptionJCalendars = recurrence.getExceptionJCalendars();

		exceptionJCalendar = exceptionJCalendars.get(0);

		Assert.assertEquals(2015, exceptionJCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			Calendar.DECEMBER, exceptionJCalendar.get(Calendar.MONTH));
		Assert.assertEquals(25, exceptionJCalendar.get(Calendar.DAY_OF_MONTH));

		exceptionJCalendar = exceptionJCalendars.get(1);

		Assert.assertEquals(2015, exceptionJCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			Calendar.DECEMBER, exceptionJCalendar.get(Calendar.MONTH));
		Assert.assertEquals(31, exceptionJCalendar.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testInTimeZoneDoesNotUpdateUntilJCalendarInSameDay() {
		Recurrence recurrence = RecurrenceSerializer.deserialize(
			"RRULE:FREQ=DAILY;INTERVAL=1;UNTIL=20160116", _utcTimeZone);

		Calendar untilJCalendar = recurrence.getUntilJCalendar();

		Assert.assertEquals(2016, untilJCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			Calendar.JANUARY, untilJCalendar.get(Calendar.MONTH));
		Assert.assertEquals(16, untilJCalendar.get(Calendar.DAY_OF_MONTH));

		Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			2015, Calendar.DECEMBER, 11, 10, 0, 0, 0, _utcTimeZone);

		recurrence = RecurrenceUtil.inTimeZone(
			recurrence, startTimeJCalendar, _losAngelesTimeZone);

		untilJCalendar = recurrence.getUntilJCalendar();

		Assert.assertEquals(2016, untilJCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			Calendar.JANUARY, untilJCalendar.get(Calendar.MONTH));
		Assert.assertEquals(16, untilJCalendar.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testInTimeZoneNullRecurrence() {
		Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			2015, Calendar.DECEMBER, 11, 1, 0, 0, 0, _utcTimeZone);

		Recurrence recurrence = RecurrenceUtil.inTimeZone(
			null, startTimeJCalendar, _losAngelesTimeZone);

		Assert.assertNull(recurrence);
	}

	@Test
	public void testInTimeZoneUpdatesExceptionJCalendars() {
		Recurrence recurrence = RecurrenceSerializer.deserialize(
			"RRULE:FREQ=DAILY;INTERVAL=1\n" +
				"EXDATE;TZID=\"UTC\";VALUE=DATE:20151225,20151231",
			_utcTimeZone);

		List<Calendar> exceptionJCalendars =
			recurrence.getExceptionJCalendars();

		Calendar exceptionJCalendar = exceptionJCalendars.get(0);

		Assert.assertEquals(2015, exceptionJCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			Calendar.DECEMBER, exceptionJCalendar.get(Calendar.MONTH));
		Assert.assertEquals(25, exceptionJCalendar.get(Calendar.DAY_OF_MONTH));

		exceptionJCalendar = exceptionJCalendars.get(1);

		Assert.assertEquals(2015, exceptionJCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			Calendar.DECEMBER, exceptionJCalendar.get(Calendar.MONTH));
		Assert.assertEquals(31, exceptionJCalendar.get(Calendar.DAY_OF_MONTH));

		Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			2015, Calendar.DECEMBER, 11, 1, 0, 0, 0, _utcTimeZone);

		recurrence = RecurrenceUtil.inTimeZone(
			recurrence, startTimeJCalendar, _losAngelesTimeZone);

		exceptionJCalendars = recurrence.getExceptionJCalendars();

		exceptionJCalendar = exceptionJCalendars.get(0);

		Assert.assertEquals(2015, exceptionJCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			Calendar.DECEMBER, exceptionJCalendar.get(Calendar.MONTH));
		Assert.assertEquals(24, exceptionJCalendar.get(Calendar.DAY_OF_MONTH));

		exceptionJCalendar = exceptionJCalendars.get(1);

		Assert.assertEquals(2015, exceptionJCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			Calendar.DECEMBER, exceptionJCalendar.get(Calendar.MONTH));
		Assert.assertEquals(30, exceptionJCalendar.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testInTimeZoneUpdatesUntilJCalendar() {
		Recurrence recurrence = RecurrenceSerializer.deserialize(
			"RRULE:FREQ=DAILY;INTERVAL=1;UNTIL=20160116", _utcTimeZone);

		Calendar untilJCalendar = recurrence.getUntilJCalendar();

		Assert.assertEquals(2016, untilJCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			Calendar.JANUARY, untilJCalendar.get(Calendar.MONTH));
		Assert.assertEquals(16, untilJCalendar.get(Calendar.DAY_OF_MONTH));

		Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			2015, Calendar.DECEMBER, 11, 1, 0, 0, 0, _utcTimeZone);

		recurrence = RecurrenceUtil.inTimeZone(
			recurrence, startTimeJCalendar, _losAngelesTimeZone);

		untilJCalendar = recurrence.getUntilJCalendar();

		Assert.assertEquals(2016, untilJCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			Calendar.JANUARY, untilJCalendar.get(Calendar.MONTH));
		Assert.assertEquals(15, untilJCalendar.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testInTimeZoneUpdatesWeekdays() {
		Recurrence recurrence = RecurrenceSerializer.deserialize(
			"RRULE:FREQ=WEEKLY;INTERVAL=1;BYDAY=MO,WE,FR", _utcTimeZone);

		List<Weekday> weekdays = recurrence.getWeekdays();

		Assert.assertTrue(
			weekdays.toString(), weekdays.contains(Weekday.MONDAY));
		Assert.assertTrue(
			weekdays.toString(), weekdays.contains(Weekday.WEDNESDAY));
		Assert.assertTrue(
			weekdays.toString(), weekdays.contains(Weekday.FRIDAY));

		Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			2015, Calendar.DECEMBER, 11, 1, 0, 0, 0, _utcTimeZone);

		recurrence = RecurrenceUtil.inTimeZone(
			recurrence, startTimeJCalendar, _losAngelesTimeZone);

		weekdays = recurrence.getWeekdays();

		Assert.assertTrue(
			weekdays.toString(), weekdays.contains(Weekday.SUNDAY));
		Assert.assertTrue(
			weekdays.toString(), weekdays.contains(Weekday.TUESDAY));
		Assert.assertTrue(
			weekdays.toString(), weekdays.contains(Weekday.THURSDAY));
	}

	protected void assertSameDay(
		Calendar expectedJCalendar, Calendar actualJCalendar) {

		Assert.assertEquals(
			expectedJCalendar.get(Calendar.YEAR),
			actualJCalendar.get(Calendar.YEAR));

		Assert.assertEquals(
			expectedJCalendar.get(Calendar.MONTH),
			actualJCalendar.get(Calendar.MONTH));

		Assert.assertEquals(
			expectedJCalendar.get(Calendar.DAY_OF_MONTH),
			actualJCalendar.get(Calendar.DAY_OF_MONTH));
	}

	protected Calendar getJan2016Calendar(int dayOfMonth) {
		return CalendarFactoryUtil.getCalendar(
			2016, Calendar.JANUARY, dayOfMonth, 0, 0, 0, 0, _utcTimeZone);
	}

	protected List<CalendarBooking> getRecurringCalendarBookings(
		Object... objects) {

		List<CalendarBooking> calendarBookings = new ArrayList<>();

		for (int i = 0; i < objects.length; i += 2) {
			Calendar startTimeJCalendar = (Calendar)objects[i];
			String recurrence = (String)objects[i + 1];

			CalendarBooking calendarBooking = mockCalendarBooking(
				startTimeJCalendar, recurrence);

			calendarBookings.add(calendarBooking);
		}

		return calendarBookings;
	}

	protected CalendarBooking mockCalendarBooking(
		Calendar startTimeJCalendar, String recurrence) {

		CalendarBooking calendarBooking = Mockito.mock(
			CalendarBookingImpl.class, Mockito.CALLS_REAL_METHODS);

		calendarBooking.setStartTime(startTimeJCalendar.getTimeInMillis());
		calendarBooking.setEndTime(
			startTimeJCalendar.getTimeInMillis() + Time.HOUR);
		calendarBooking.setRecurrence(recurrence);

		Mockito.doReturn(
			_utcTimeZone
		).when(
			calendarBooking
		).getTimeZone();

		return calendarBooking;
	}

	private static final TimeZone _losAngelesTimeZone = TimeZone.getTimeZone(
		"America/Los_Angeles");
	private static final TimeZone _utcTimeZone = TimeZone.getTimeZone(
		StringPool.UTC);

}