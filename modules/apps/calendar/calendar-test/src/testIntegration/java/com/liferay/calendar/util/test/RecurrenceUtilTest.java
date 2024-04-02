/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.recurrence.Frequency;
import com.liferay.calendar.recurrence.PositionalWeekday;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.Weekday;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.calendar.util.RecurrenceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class RecurrenceUtilTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Calendar calendar = CalendarTestUtil.addCalendar(group);

		_calendarBooking = CalendarBookingTestUtil.addRegularCalendarBooking(
			calendar);

		_calendarBooking.setStartTime(
			new Date(
				0
			).getTime());
	}

	@Test
	public void testGetSummaryWithDailyRecurrence() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setInterval(1);

		Assert.assertEquals(
			"Daily", RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithDailyRecurrenceAndInterval() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setInterval(2);

		Assert.assertEquals(
			"Every 2 Days",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithDailyRecurrenceAndIntervalAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setInterval(2);

		Assert.assertEquals(
			"Every 2 Days, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithDailyRecurrenceAndIntervalUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setInterval(2);

		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Every 2 Days, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithDailyRecurrenceAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setInterval(1);

		Assert.assertEquals(
			"Daily, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithDailyRecurrenceUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setInterval(1);

		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Daily, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithMonthlyRecurrence() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.MONTHLY);
		recurrence.setInterval(1);

		Assert.assertEquals(
			"Monthly", RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithMonthlyRecurrenceAndDayOfWeek() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.MONTHLY);
		recurrence.setInterval(1);

		_setPositionalWeekdays(recurrence);

		Assert.assertEquals(
			"Monthly, on First Sunday",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithMonthlyRecurrenceAndDayOfWeekAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.MONTHLY);
		recurrence.setInterval(1);

		_setPositionalWeekdays(recurrence);

		Assert.assertEquals(
			"Monthly, on First Sunday, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithMonthlyRecurrenceAndDayOfWeekUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.MONTHLY);
		recurrence.setInterval(1);

		_setPositionalWeekdays(recurrence);
		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Monthly, on First Sunday, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithMonthlyRecurrenceAndInterval() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.MONTHLY);
		recurrence.setInterval(2);

		Assert.assertEquals(
			"Every 2 Months",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithMonthlyRecurrenceAndIntervalAndDayOfWeek() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.MONTHLY);
		recurrence.setInterval(2);

		_setPositionalWeekdays(recurrence);

		Assert.assertEquals(
			"Every 2 Months, on First Sunday",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithMonthlyRecurrenceAndIntervalAndDayOfWeekAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.MONTHLY);
		recurrence.setInterval(2);

		_setPositionalWeekdays(recurrence);

		Assert.assertEquals(
			"Every 2 Months, on First Sunday, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithMonthlyRecurrenceAndIntervalAndDayOfWeekUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.MONTHLY);
		recurrence.setInterval(2);

		_setPositionalWeekdays(recurrence);
		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Every 2 Months, on First Sunday, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithMonthlyRecurrenceAndIntervalAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.MONTHLY);
		recurrence.setInterval(2);

		Assert.assertEquals(
			"Every 2 Months, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithMonthlyRecurrenceAndIntervalUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.MONTHLY);
		recurrence.setInterval(2);

		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Every 2 Months, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithMonthlyRecurrenceAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.MONTHLY);
		recurrence.setInterval(1);

		Assert.assertEquals(
			"Monthly, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithMonthlyRecurrenceUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.MONTHLY);
		recurrence.setInterval(1);

		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Monthly, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithNoRecurrence() {
		Assert.assertEquals(
			"False", RecurrenceUtil.getSummary(_calendarBooking, null));
	}

	@Test
	public void testGetSummaryWithWeeklyRecurrence() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.WEEKLY);
		recurrence.setInterval(1);

		Assert.assertEquals(
			"Weekly", RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithWeeklyRecurrenceAndDayOfWeek() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.WEEKLY);
		recurrence.setInterval(1);

		_setPositionalWeekdays(recurrence);

		Assert.assertEquals(
			"Weekly, on Sunday",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithWeeklyRecurrenceAndDayOfWeekAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.WEEKLY);
		recurrence.setInterval(1);

		_setPositionalWeekdays(recurrence);

		Assert.assertEquals(
			"Weekly, on Sunday, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithWeeklyRecurrenceAndDayOfWeekUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.WEEKLY);
		recurrence.setInterval(1);

		_setPositionalWeekdays(recurrence);
		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Weekly, on Sunday, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithWeeklyRecurrenceAndInterval() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.WEEKLY);
		recurrence.setInterval(2);

		Assert.assertEquals(
			"Every 2 Weeks",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithWeeklyRecurrenceAndIntervalAndDayOfWeek() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.WEEKLY);
		recurrence.setInterval(2);

		_setPositionalWeekdays(recurrence);

		Assert.assertEquals(
			"Every 2 Weeks, on Sunday",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithWeeklyRecurrenceAndIntervalAndDayOfWeekAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.WEEKLY);
		recurrence.setInterval(2);

		_setPositionalWeekdays(recurrence);

		Assert.assertEquals(
			"Every 2 Weeks, on Sunday, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithWeeklyRecurrenceAndIntervalAndDayOfWeekUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.WEEKLY);
		recurrence.setInterval(2);

		_setPositionalWeekdays(recurrence);
		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Every 2 Weeks, on Sunday, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithWeeklyRecurrenceAndIntervalAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.WEEKLY);
		recurrence.setInterval(2);

		Assert.assertEquals(
			"Every 2 Weeks, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithWeeklyRecurrenceAndIntervalUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.WEEKLY);
		recurrence.setInterval(2);

		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Every 2 Weeks, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithWeeklyRecurrenceAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.WEEKLY);
		recurrence.setInterval(1);

		Assert.assertEquals(
			"Weekly, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithWeeklyRecurrenceUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.WEEKLY);
		recurrence.setInterval(1);

		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Weekly, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithYearlyRecurrence() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.YEARLY);
		recurrence.setInterval(1);

		Assert.assertEquals(
			"Yearly", RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithYearlyRecurrenceAndDayOfWeekAndMonth() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.YEARLY);
		recurrence.setInterval(1);

		_setPositionalWeekdays(recurrence);

		Assert.assertEquals(
			"Yearly, on the First Sunday of January",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithYearlyRecurrenceAndDayOfWeekAndMonthAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.YEARLY);
		recurrence.setInterval(1);

		_setPositionalWeekdays(recurrence);

		Assert.assertEquals(
			"Yearly, on the First Sunday of January, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithYearlyRecurrenceAndDayOfWeekAndMonthUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.YEARLY);
		recurrence.setInterval(1);

		_setPositionalWeekdays(recurrence);
		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Yearly, on the First Sunday of January, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithYearlyRecurrenceAndInterval() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.YEARLY);
		recurrence.setInterval(2);

		Assert.assertEquals(
			"Every 2 Years",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithYearlyRecurrenceAndIntervalAndDayOfWeekAndMonth() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.YEARLY);
		recurrence.setInterval(2);

		_setPositionalWeekdays(recurrence);

		Assert.assertEquals(
			"Every 2 Years, on First Sunday of January",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithYearlyRecurrenceAndIntervalAndDayOfWeekAndMonthAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.YEARLY);
		recurrence.setInterval(2);

		_setPositionalWeekdays(recurrence);

		Assert.assertEquals(
			"Every 2 Years, on First Sunday of January, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithYearlyRecurrenceAndIntervalAndDayOfWeekAndMonthUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.YEARLY);
		recurrence.setInterval(2);

		_setPositionalWeekdays(recurrence);
		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Every 2 Years, on First Sunday of January, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithYearlyRecurrenceAndIntervalAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.YEARLY);
		recurrence.setInterval(2);

		Assert.assertEquals(
			"Every 2 Years, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithYearlyRecurrenceAndIntervalUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.YEARLY);
		recurrence.setInterval(2);

		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Every 2 Years, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithYearlyRecurrenceAndOccurrences() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.YEARLY);
		recurrence.setInterval(1);

		Assert.assertEquals(
			"Yearly, 2 Times",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	@Test
	public void testGetSummaryWithYearlyRecurrenceUntilDate() {
		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.YEARLY);
		recurrence.setInterval(1);

		_setUntilJCalendar(recurrence);

		Assert.assertEquals(
			"Yearly, Until January 1, 1970",
			RecurrenceUtil.getSummary(_calendarBooking, recurrence));
	}

	private void _setPositionalWeekdays(Recurrence recurrence) {
		List<PositionalWeekday> positionalWeekdays = new ArrayList<>();

		positionalWeekdays.add(new PositionalWeekday(Weekday.SUNDAY, 1));

		recurrence.setPositionalWeekdays(positionalWeekdays);
	}

	private void _setUntilJCalendar(Recurrence recurrence) {
		java.util.Calendar untilJCalendar = java.util.Calendar.getInstance();

		untilJCalendar.setTime(new Date(0));

		recurrence.setUntilJCalendar(untilJCalendar);
	}

	private static CalendarBooking _calendarBooking;

}