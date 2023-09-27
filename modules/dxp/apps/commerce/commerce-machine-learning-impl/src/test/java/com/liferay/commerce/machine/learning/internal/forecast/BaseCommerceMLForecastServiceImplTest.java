/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.forecast;

import com.liferay.commerce.machine.learning.internal.forecast.constants.CommerceMLForecastPeriod;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Riccardo Ferrari
 */
public class BaseCommerceMLForecastServiceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_baseCommerceMLForecastServiceImpl = Mockito.mock(
			BaseCommerceMLForecastServiceImpl.class,
			Mockito.CALLS_REAL_METHODS);
	}

	@Test
	public void testGetEndDateMonth() {
		LocalDateTime testLocalDateTime = LocalDateTime.of(
			2019, 9, 21, 13, 2, 45);

		Date testDate = _toDate(testLocalDateTime);

		Date actualEndDate = _baseCommerceMLForecastServiceImpl.getEndDate(
			testDate, CommerceMLForecastPeriod.MONTH, 3);

		LocalDateTime expectedDateTime = testLocalDateTime.truncatedTo(
			ChronoUnit.DAYS);

		expectedDateTime = expectedDateTime.withDayOfMonth(1);

		expectedDateTime = expectedDateTime.plusMonths(3);

		Date expectedEndDate = _toDate(expectedDateTime);

		Assert.assertEquals(expectedEndDate, actualEndDate);
	}

	@Test
	public void testGetEndDateWeek() {
		LocalDateTime testLocalDateTime = LocalDateTime.of(
			2019, 9, 21, 13, 2, 45);

		Date testDate = _toDate(testLocalDateTime);

		Date actualEndDate = _baseCommerceMLForecastServiceImpl.getEndDate(
			testDate, CommerceMLForecastPeriod.WEEK, 3);

		LocalDateTime expectedDateTime = testLocalDateTime.truncatedTo(
			ChronoUnit.DAYS);

		expectedDateTime = expectedDateTime.with(ChronoField.DAY_OF_WEEK, 1);

		expectedDateTime = expectedDateTime.plusWeeks(3);

		Date expectedEndDate = _toDate(expectedDateTime);

		Assert.assertEquals(expectedEndDate, actualEndDate);
	}

	@Test
	public void testGetEndDateZeroStepMonth() {
		LocalDateTime testLocalDateTime = LocalDateTime.of(
			2019, 9, 21, 13, 2, 45);

		Date testDate = _toDate(testLocalDateTime);

		Date actualEndDate = _baseCommerceMLForecastServiceImpl.getEndDate(
			testDate, CommerceMLForecastPeriod.MONTH, 0);

		LocalDateTime expectedDateTime = testLocalDateTime.truncatedTo(
			ChronoUnit.DAYS);

		expectedDateTime = expectedDateTime.withDayOfMonth(1);

		Date expectedEndDate = _toDate(expectedDateTime);

		Assert.assertEquals(expectedEndDate, actualEndDate);
	}

	@Test
	public void testGetEndDateZeroStepWeek() {
		LocalDateTime testLocalDateTime = LocalDateTime.of(
			2019, 9, 21, 13, 2, 45);

		Date testDate = _toDate(testLocalDateTime);

		Date actualEndDate = _baseCommerceMLForecastServiceImpl.getEndDate(
			testDate, CommerceMLForecastPeriod.WEEK, 0);

		LocalDateTime expectedDateTime = testLocalDateTime.truncatedTo(
			ChronoUnit.DAYS);

		expectedDateTime = expectedDateTime.with(ChronoField.DAY_OF_WEEK, 1);

		Date expectedEndDate = _toDate(expectedDateTime);

		Assert.assertEquals(expectedEndDate, actualEndDate);
	}

	@Test
	public void testGetStartDateMonth() {
		LocalDateTime testLocalDateTime = LocalDateTime.of(
			2019, 9, 21, 13, 2, 45);

		Date testDate = _toDate(testLocalDateTime);

		Date actualStartDate = _baseCommerceMLForecastServiceImpl.getStartDate(
			testDate, CommerceMLForecastPeriod.MONTH, 3);

		LocalDateTime expectedDateTime = testLocalDateTime.truncatedTo(
			ChronoUnit.DAYS);

		expectedDateTime = expectedDateTime.withDayOfMonth(1);

		expectedDateTime = expectedDateTime.minusMonths(3);

		Date expectedStartDate = _toDate(expectedDateTime);

		Assert.assertEquals(expectedStartDate, actualStartDate);
	}

	@Test
	public void testGetStartDateWeek() {
		LocalDateTime testLocalDateTime = LocalDateTime.of(
			2019, 9, 21, 13, 2, 45);

		Date testDate = _toDate(testLocalDateTime);

		Date actualStartDate = _baseCommerceMLForecastServiceImpl.getStartDate(
			testDate, CommerceMLForecastPeriod.WEEK, 3);

		LocalDateTime expectedDateTime = testLocalDateTime.truncatedTo(
			ChronoUnit.DAYS);

		expectedDateTime = expectedDateTime.with(ChronoField.DAY_OF_WEEK, 1);

		expectedDateTime = expectedDateTime.minusWeeks(3);

		Date expectedStartDate = _toDate(expectedDateTime);

		Assert.assertEquals(expectedStartDate, actualStartDate);
	}

	@Test
	public void testGetStartDateZeroStepMonth() {
		LocalDateTime testLocalDateTime = LocalDateTime.of(
			2019, 9, 21, 13, 2, 45);

		Date testDate = _toDate(testLocalDateTime);

		Date actualStartDate = _baseCommerceMLForecastServiceImpl.getStartDate(
			testDate, CommerceMLForecastPeriod.MONTH, 0);

		LocalDateTime expectedDateTime = testLocalDateTime.truncatedTo(
			ChronoUnit.DAYS);

		expectedDateTime = expectedDateTime.withDayOfMonth(1);

		Date expectedStartDate = _toDate(expectedDateTime);

		Assert.assertEquals(expectedStartDate, actualStartDate);
	}

	@Test
	public void testGetStartDateZeroStepWeek() {
		LocalDateTime testLocalDateTime = LocalDateTime.of(
			2019, 9, 21, 13, 2, 45);

		Date testDate = _toDate(testLocalDateTime);

		Date actualStartDate = _baseCommerceMLForecastServiceImpl.getStartDate(
			testDate, CommerceMLForecastPeriod.WEEK, 0);

		LocalDateTime expectedDateTime = testLocalDateTime.truncatedTo(
			ChronoUnit.DAYS);

		expectedDateTime = expectedDateTime.with(ChronoField.DAY_OF_WEEK, 1);

		Date expectedStartDate = _toDate(expectedDateTime);

		Assert.assertEquals(expectedStartDate, actualStartDate);
	}

	private Date _toDate(LocalDateTime localDateTime) {
		ZonedDateTime zonedDateTime = localDateTime.atZone(
			BaseCommerceMLForecastServiceImpl.DEFAULT_ZONE_OFFSET);

		return Date.from(zonedDateTime.toInstant());
	}

	private static BaseCommerceMLForecastServiceImpl
		_baseCommerceMLForecastServiceImpl;

}