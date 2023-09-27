/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.recurrence;

import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.util.JCalendarUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class RecurrenceSplitterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_recurrenceSplitter = new RecurrenceSplitterImpl();
	}

	@Test
	public void testSplitRecurrence() {
		Recurrence recurrence = _getRecurrence("RRULE:FREQ=DAILY;INTERVAL=1");

		Calendar startTimeJCalendar = _getJCalendar(1);

		Calendar splitTimeJCalendar = _getJCalendar(10);

		RecurrenceSplit recurrenceSplit = _recurrenceSplitter.split(
			recurrence, startTimeJCalendar, splitTimeJCalendar);

		_assertSplit(recurrenceSplit);

		_assertFirstRecurrenceEquals(
			recurrenceSplit, "RRULE:FREQ=DAILY;UNTIL=20170109;INTERVAL=1");

		_assertSecondRecurrenceEquals(
			recurrenceSplit, "RRULE:FREQ=DAILY;INTERVAL=1");
	}

	@Test
	public void testSplitRecurrenceWithCount() {
		Recurrence recurrence = _getRecurrence(
			"RRULE:FREQ=DAILY;INTERVAL=1;COUNT=20");

		Calendar startTimeJCalendar = _getJCalendar(1);

		Calendar splitTimeJCalendar = _getJCalendar(10);

		RecurrenceSplit recurrenceSplit = _recurrenceSplitter.split(
			recurrence, startTimeJCalendar, splitTimeJCalendar);

		_assertSplit(recurrenceSplit);

		_assertFirstRecurrenceEquals(
			recurrenceSplit, "RRULE:FREQ=DAILY;COUNT=9;INTERVAL=1");

		_assertSecondRecurrenceEquals(
			recurrenceSplit, "RRULE:FREQ=DAILY;COUNT=11;INTERVAL=1");
	}

	@Test
	public void testSplitRecurrenceWithCountAndSplitDateBeforeStartDate() {
		Recurrence recurrence = _getRecurrence(
			"RRULE:FREQ=DAILY;COUNT=5;INTERVAL=1");

		Calendar startTimeJCalendar = _getJCalendar(1);

		Calendar splitTimeJCalendar = _getJCalendar(10);

		RecurrenceSplit recurrenceSplit = _recurrenceSplitter.split(
			recurrence, startTimeJCalendar, splitTimeJCalendar);

		_assertNotSplit(recurrenceSplit);

		_assertFirstRecurrenceEquals(
			recurrenceSplit, "RRULE:FREQ=DAILY;COUNT=5;INTERVAL=1");
	}

	@Test
	public void testSplitRecurrenceWithCountBeforeSplitDate() {
		Recurrence recurrence = _getRecurrence(
			"RRULE:FREQ=DAILY;INTERVAL=1;COUNT=5");

		Calendar startTimeJCalendar = _getJCalendar(1);

		Calendar splitTimeJCalendar = _getJCalendar(10);

		RecurrenceSplit recurrenceSplit = _recurrenceSplitter.split(
			recurrence, startTimeJCalendar, splitTimeJCalendar);

		_assertNotSplit(recurrenceSplit);

		_assertFirstRecurrenceEquals(
			recurrenceSplit, "RRULE:FREQ=DAILY;COUNT=5;INTERVAL=1");
	}

	@Test
	public void testSplitRecurrenceWithExDate() {
		Recurrence recurrence = _getRecurrence(
			"RRULE:FREQ=DAILY;INTERVAL=1\n" +
				"EXDATE;TZID=\"UTC\";VALUE=DATE:20170108,20170112");

		Calendar startTimeJCalendar = _getJCalendar(1);

		Calendar splitTimeJCalendar = _getJCalendar(10);

		RecurrenceSplit recurrenceSplit = _recurrenceSplitter.split(
			recurrence, startTimeJCalendar, splitTimeJCalendar);

		_assertSplit(recurrenceSplit);

		Assert.assertEquals(
			"RRULE:FREQ=DAILY;UNTIL=20170109;INTERVAL=1\n" +
				"EXDATE;TZID=\"UTC\";VALUE=DATE:20170108",
			RecurrenceSerializer.serialize(
				recurrenceSplit.getFirstRecurrence()));

		Assert.assertEquals(
			"RRULE:FREQ=DAILY;INTERVAL=1\n" +
				"EXDATE;TZID=\"UTC\";VALUE=DATE:20170112",
			RecurrenceSerializer.serialize(
				recurrenceSplit.getSecondRecurrence()));
	}

	@Test
	public void testSplitRecurrenceWithSplitDateAfterUntilDate() {
		Recurrence recurrence = _getRecurrence(
			"RRULE:FREQ=DAILY;INTERVAL=1;UNTIL=20170108");

		Calendar startTimeJCalendar = _getJCalendar(1);

		Calendar splitTimeJCalendar = _getJCalendar(10);

		RecurrenceSplit recurrenceSplit = _recurrenceSplitter.split(
			recurrence, startTimeJCalendar, splitTimeJCalendar);

		_assertNotSplit(recurrenceSplit);

		_assertFirstRecurrenceEquals(
			recurrenceSplit, "RRULE:FREQ=DAILY;UNTIL=20170108;INTERVAL=1");
	}

	@Test
	public void testSplitRecurrenceWithSplitDateBeforeStartDate() {
		Recurrence recurrence = _getRecurrence("RRULE:FREQ=DAILY;INTERVAL=1");

		Calendar startTimeJCalendar = _getJCalendar(4);

		Calendar splitTimeJCalendar = _getJCalendar(1);

		RecurrenceSplit recurrenceSplit = _recurrenceSplitter.split(
			recurrence, startTimeJCalendar, splitTimeJCalendar);

		_assertNotSplit(recurrenceSplit);

		_assertFirstRecurrenceEquals(
			recurrenceSplit, "RRULE:FREQ=DAILY;INTERVAL=1");
	}

	@Test
	public void testSplitRecurrenceWithUntilDate() {
		Recurrence recurrence = _getRecurrence(
			"RRULE:FREQ=DAILY;INTERVAL=1;UNTIL=20170131");

		Calendar startTimeJCalendar = _getJCalendar(1);

		Calendar splitTimeJCalendar = _getJCalendar(10);

		RecurrenceSplit recurrenceSplit = _recurrenceSplitter.split(
			recurrence, startTimeJCalendar, splitTimeJCalendar);

		_assertSplit(recurrenceSplit);

		_assertFirstRecurrenceEquals(
			recurrenceSplit, "RRULE:FREQ=DAILY;UNTIL=20170109;INTERVAL=1");

		_assertSecondRecurrenceEquals(
			recurrenceSplit, "RRULE:FREQ=DAILY;UNTIL=20170131;INTERVAL=1");
	}

	private void _assertFirstRecurrenceEquals(
		RecurrenceSplit recurrenceSplit, String expectedRecurrence) {

		_assertRecurrenceEquals(
			recurrenceSplit.getFirstRecurrence(), expectedRecurrence);
	}

	private void _assertNotSplit(RecurrenceSplit recurrenceSplit) {
		Assert.assertFalse(recurrenceSplit.isSplit());
	}

	private void _assertRecurrenceEquals(
		Recurrence recurrenceObj, String expectedRecurrence) {

		Assert.assertEquals(
			expectedRecurrence, RecurrenceSerializer.serialize(recurrenceObj));
	}

	private void _assertSecondRecurrenceEquals(
		RecurrenceSplit recurrenceSplit, String expectedRecurrence) {

		_assertRecurrenceEquals(
			recurrenceSplit.getSecondRecurrence(), expectedRecurrence);
	}

	private void _assertSplit(RecurrenceSplit recurrenceSplit) {
		Assert.assertTrue(recurrenceSplit.isSplit());
	}

	private Calendar _getJCalendar(int dayOfMonth) {
		return JCalendarUtil.getJCalendar(
			2017, Calendar.JANUARY, dayOfMonth, 1, 0, 0, 0, _utcTimeZone);
	}

	private Recurrence _getRecurrence(String recurrence) {
		return RecurrenceSerializer.deserialize(recurrence, _utcTimeZone);
	}

	private static final TimeZone _utcTimeZone = TimeZone.getTimeZone(
		StringPool.UTC);

	private RecurrenceSplitter _recurrenceSplitter;

}