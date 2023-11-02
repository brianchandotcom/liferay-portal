/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.util;

import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.text.ParseException;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class DateRangeFactoryUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBoundedRange() {
		Assert.assertEquals(
			"[20180131000000 TO 20180228235959]",
			DateRangeFactoryUtil.getRangeString(
				"2018-01-31", "2018-02-28", TimeZoneUtil.GMT));
	}

	@Test(expected = ParseException.class)
	public void testDateFormatShouldBeyyyyMMddHHmmss() throws Exception {
		DateRangeFactoryUtil.validateRanges(
			"[{\"label\":\"past-hour\", \"range\":\"[20190908 TO *]\"}]");
	}

	@Test(expected = ParseException.class)
	public void testInvalidRangeAliases() throws Exception {
		DateRangeFactoryUtil.validateRanges(
			"[{\"label\":\"past-hour\", \"range\":\"[past-test TO *]\"}]");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidRangeWithoutBrackets() throws Exception {
		DateRangeFactoryUtil.validateRanges(
			"[{\"label\":\"past-hour\", \"range\":\"past-hour TO *\"}]");
	}

	@Test
	public void testNamedRange() {
		Calendar calendar = new GregorianCalendar(
			2018, Calendar.MARCH, 1, 16, 30, 42);

		Assert.assertEquals(
			"[20180301153042 TO 20180301163042]",
			DateRangeFactoryUtil.getRangeString("past-hour", calendar));
		Assert.assertEquals(
			"[20180228163042 TO 20180301163042]",
			DateRangeFactoryUtil.getRangeString("past-24-hours", calendar));
	}

	@Test
	public void testReplaceAliasPast24Hours() {
		Calendar calendar = new GregorianCalendar(
			2018, Calendar.MAY, 15, 23, 59, 59);

		Assert.assertEquals(
			"[20180514235959 TO 20180515225959]",
			DateRangeFactoryUtil.replaceAliases(
				"[past-24-hours TO past-hour]", calendar));
	}

	@Test
	public void testReplaceAliasPastHour() {
		Calendar calendar = new GregorianCalendar(
			2018, Calendar.MAY, 15, 23, 59, 59);

		Assert.assertEquals(
			"[20180515225959 TO 20180515235959]",
			DateRangeFactoryUtil.replaceAliases("[past-hour TO *]", calendar));
	}

	@Test
	public void testReplaceAliasPastMonth() {
		Calendar calendar = new GregorianCalendar(
			2018, Calendar.MAY, 15, 23, 59, 59);

		Assert.assertEquals(
			"[20180415235959 TO 20180508235959]",
			DateRangeFactoryUtil.replaceAliases(
				"[past-month TO past-week]", calendar));
	}

	@Test
	public void testReplaceAliasPastWeek() {
		Calendar calendar = new GregorianCalendar(
			2018, Calendar.MAY, 15, 23, 59, 59);

		Assert.assertEquals(
			"[20180508235959 TO 20180514235959]",
			DateRangeFactoryUtil.replaceAliases(
				"[past-week TO past-24-hours]", calendar));
	}

	@Test
	public void testReplaceAliasPastYear() {
		Calendar calendar = new GregorianCalendar(
			2018, Calendar.MAY, 15, 23, 59, 59);

		Assert.assertEquals(
			"[20170515235959 TO 20180415235959]",
			DateRangeFactoryUtil.replaceAliases(
				"[past-year TO past-month]", calendar));
	}

}