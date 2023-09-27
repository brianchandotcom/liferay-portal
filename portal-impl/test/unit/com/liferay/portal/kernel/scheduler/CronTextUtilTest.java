/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class CronTextUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());
	}

	@Test
	public void testDailyRecurrence() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.DAILY);

		Assert.assertEquals("0 4 3 1/1 * ? *", _toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 1/3 * ? *", _toCronText(recurrence));
	}

	@Test
	public void testDailyRecurrenceByDay() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.DAILY);

		recurrence.setByDay(_firstMondayDayAndPositions);

		Assert.assertEquals("0 4 3 ? * MON *", _toCronText(recurrence));

		recurrence.setByDay(_firstMondayDayAndFirstFridayAndPositions);

		Assert.assertEquals("0 4 3 ? * MON,FRI *", _toCronText(recurrence));
	}

	@Test
	public void testMonthlyRecurrence() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.MONTHLY);

		Assert.assertEquals("0 4 3 ? 1/1 ? *", _toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? 1/3 ? *", _toCronText(recurrence));
	}

	@Test
	public void testMonthlyRecurrenceByFirstDay() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.MONTHLY);

		recurrence.setByDay(_firstMondayDayAndPositions);

		Assert.assertEquals("0 4 3 ? 1/1 MON#0 *", _toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? 1/3 MON#0 *", _toCronText(recurrence));
	}

	@Test
	public void testMonthlyRecurrenceByLastDay() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.MONTHLY);

		recurrence.setByDay(_lastMondayDayAndPositions);

		Assert.assertEquals("0 4 3 ? 1/1 MONL *", _toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? 1/3 MONL *", _toCronText(recurrence));
	}

	@Test
	public void testMonthlyRecurrenceByMonthDay() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.MONTHLY);

		recurrence.setByMonthDay(new int[] {15});

		Assert.assertEquals("0 4 3 15 1/1 ? *", _toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 15 1/3 ? *", _toCronText(recurrence));
	}

	@Test
	public void testNoRecurrence() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.NO_RECURRENCE);

		Assert.assertEquals("0 4 3 2 1 ? 2010", _toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 2 1 ? 2010", _toCronText(recurrence));
	}

	@Test
	public void testWeeklyNoRecurrence() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.WEEKLY);

		recurrence.setInterval(0);

		Assert.assertEquals("0 4 3 ? * 7 *", _toCronText(recurrence));
	}

	@Test
	public void testWeeklyRecurrence() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.WEEKLY);

		Assert.assertEquals("0 4 3 ? * 7/1 *", _toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? * 7/3 *", _toCronText(recurrence));
	}

	@Test
	public void testWeeklyRecurrenceByDay() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.WEEKLY);

		recurrence.setByDay(_firstMondayDayAndPositions);

		Assert.assertEquals("0 4 3 ? * MON/1 *", _toCronText(recurrence));

		recurrence.setByDay(_firstMondayDayAndFirstFridayAndPositions);

		Assert.assertEquals("0 4 3 ? * MON,FRI/1 *", _toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? * MON,FRI/3 *", _toCronText(recurrence));
	}

	@Test
	public void testYearlyRecurrence() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.YEARLY);

		Assert.assertEquals("0 4 3 ? 1 ? 2010/1", _toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? 1 ? 2010/3", _toCronText(recurrence));
	}

	@Test
	public void testYearlyRecurrenceByFirstDay() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.YEARLY);

		recurrence.setByMonth(new int[] {0});
		recurrence.setByDay(_firstMondayDayAndPositions);

		Assert.assertEquals("0 4 3 ? 1 MON#0 2010/1", _toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? 1 MON#0 2010/3", _toCronText(recurrence));
	}

	@Test
	public void testYearlyRecurrenceByLastDay() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.YEARLY);

		recurrence.setByMonth(new int[] {0});
		recurrence.setByDay(_lastMondayDayAndPositions);

		Assert.assertEquals("0 4 3 ? 1 MONL 2010/1", _toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 ? 1 MONL 2010/3", _toCronText(recurrence));
	}

	@Test
	public void testYearlyRecurrenceByMonthDay() {
		Recurrence recurrence = _getRecurrence(
			_recurrenceCalendar, Recurrence.YEARLY);

		recurrence.setByMonth(new int[] {0});
		recurrence.setByMonthDay(new int[] {15});

		Assert.assertEquals("0 4 3 15 1 ? 2010/1", _toCronText(recurrence));

		recurrence.setInterval(3);

		Assert.assertEquals("0 4 3 15 1 ? 2010/3", _toCronText(recurrence));
	}

	private Recurrence _getRecurrence(Calendar calendar, int recurrenceType) {
		return new Recurrence(
			calendar, new Duration(1, 0, 0, 0), recurrenceType);
	}

	private String _toCronText(Recurrence recurrence) {
		return ReflectionTestUtil.invoke(
			CronTextUtil.class, "_toCronText",
			new Class<?>[] {Recurrence.class}, recurrence);
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