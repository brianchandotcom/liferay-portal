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

package com.liferay.portal.workflow.kaleo.runtime.internal.calendar;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.workflow.kaleo.definition.DelayDuration;
import com.liferay.portal.workflow.kaleo.definition.DurationScale;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

/**
 * @author Feliphe Marinho
 */
@PowerMockRunnerDelegate(Parameterized.class)
@PrepareForTest(CalendarFactoryUtil.class)
@RunWith(PowerMockRunner.class)
public class DefaultDueDateCalculatorTest {

	@Parameterized.Parameters(name = "Testing scale in {3}s")
	public static List<Object[]> durationScales() {
		return Arrays.asList(
			new Object[][] {
				{Calendar.SECOND, 1.5, 2, DurationScale.SECOND},
				{Calendar.MINUTE, 1.5, 2, DurationScale.MINUTE},
				{Calendar.HOUR, 1.5, 2, DurationScale.HOUR},
				{Calendar.DAY_OF_YEAR, 1.5, 2, DurationScale.DAY},
				{Calendar.MONTH, 1.5, 2, DurationScale.MONTH},
				{Calendar.YEAR, 1.5, 2, DurationScale.YEAR}
			});
	}

	public DefaultDueDateCalculatorTest(
		int calendarTimeUnit, double duration, int durationExpected,
		DurationScale durationScale) {

		_calendarTimeUnit = calendarTimeUnit;
		_duration = duration;
		_durationExpected = durationExpected;
		_durationScale = durationScale;
	}

	@Before
	public void setUp() {
		PowerMockito.mockStatic(CalendarFactoryUtil.class);

		PowerMockito.when(
			CalendarFactoryUtil.getCalendar()
		).thenReturn(
			new GregorianCalendar()
		);

		PowerMockito.when(
			CalendarFactoryUtil.getCalendar(2021, Calendar.JANUARY, 1, 1, 1, 1)
		).thenReturn(
			new GregorianCalendar(2021, Calendar.JANUARY, 1, 1, 1, 1)
		);
	}

	@Test
	public void testGetDueDate() {
		Calendar defaultCalendar = CalendarFactoryUtil.getCalendar(
			2021, Calendar.JANUARY, 1, 1, 1, 1);

		Date startDate = defaultCalendar.getTime();

		DelayDuration delayDuration = new DelayDuration(
			_duration, _durationScale);

		Date dueDate = _defaultDueDateCalculator.getDueDate(
			startDate, delayDuration);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			2021, Calendar.JANUARY, 1, 1, 1, 1);

		expectedCalendar.add(_calendarTimeUnit, _durationExpected);

		Assert.assertEquals(expectedCalendar.getTime(), dueDate);
	}

	private final int _calendarTimeUnit;
	private final DefaultDueDateCalculator _defaultDueDateCalculator =
		new DefaultDueDateCalculator();
	private final double _duration;
	private final int _durationExpected;
	private final DurationScale _durationScale;

}