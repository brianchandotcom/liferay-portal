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
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.definition.DelayDuration;
import com.liferay.portal.workflow.kaleo.definition.DurationScale;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Feliphe Marinho
 */
@RunWith(Parameterized.class)
public class DefaultDueDateCalculatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(
		name = "calendarTimeUnit={0}, duration={1}, durationScale={2}, expectedDuration={3}"
	)
	public static List<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{Calendar.DAY_OF_YEAR, 1.5, DurationScale.DAY, 2},
				{Calendar.HOUR, 1.5, DurationScale.HOUR, 2},
				{Calendar.MINUTE, 1.5, DurationScale.MINUTE, 2},
				{Calendar.MONTH, 1.5, DurationScale.MONTH, 2},
				{Calendar.SECOND, 1.5, DurationScale.SECOND, 2},
				{Calendar.YEAR, 1.5, DurationScale.YEAR, 2}
			});
	}

	@Test
	public void testGetDueDate() {
		Calendar defaultCalendar = CalendarFactoryUtil.getCalendar(
			2021, Calendar.JANUARY, 1, 1, 1, 1);

		Date startDate = defaultCalendar.getTime();

		DelayDuration delayDuration = new DelayDuration(
			duration, durationScale);

		Date dueDate = _defaultDueDateCalculator.getDueDate(
			startDate, delayDuration);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			2021, Calendar.JANUARY, 1, 1, 1, 1);

		expectedCalendar.add(calendarTimeUnit, expectedDuration);

		Assert.assertEquals(expectedCalendar.getTime(), dueDate);
	}

	@Parameterized.Parameter
	public int calendarTimeUnit;

	@Parameterized.Parameter(1)
	public double duration;

	@Parameterized.Parameter(2)
	public DurationScale durationScale;

	@Parameterized.Parameter(3)
	public int expectedDuration;

	private final DefaultDueDateCalculator _defaultDueDateCalculator =
		new DefaultDueDateCalculator();

}