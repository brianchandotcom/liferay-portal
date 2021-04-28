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

package com.liferay.portal.search.web.internal.modified.facet.builder;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.DateFormatFactoryImpl;

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
public class DateRangeFactoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBoundedRange() {
		Assert.assertEquals(
			"[20180131000000 TO 20180228235959]",
			_dateRangeFactory.getRangeString("2018-01-31", "2018-02-28"));
	}

	@Test(expected = ParseException.class)
	public void testDateFormatShouldBeyyyyMMddHHmmss() throws Exception {
		_dateRangeFactory.validateRange(
			"[{\"label\":\"past-hour\", \"range\":\"20190908 TO *\"}]");
	}

	@Test(expected = ParseException.class)
	public void testInvalidRangeAliases() throws Exception {
		_dateRangeFactory.validateRange(
			"[{\"label\":\"past-hour\", \"range\":\"[past-test TO *]\"}]");
	}

	@Test(expected = ParseException.class)
	public void testInvalidRangeWithoutBrackets() throws Exception {
		_dateRangeFactory.validateRange(
			"[{\"label\":\"past-hour\", \"range\":\"past-hour TO *\"}]");
	}

	@Test
	public void testNamedRange() {
		Calendar calendar = new GregorianCalendar(
			2018, Calendar.MARCH, 1, 16, 30, 42);

		Assert.assertEquals(
			"[20180301153042 TO 20180301163042]",
			_dateRangeFactory.getRangeString("past-hour", calendar));
		Assert.assertEquals(
			"[20180228163042 TO 20180301163042]",
			_dateRangeFactory.getRangeString("past-24-hours", calendar));
	}

	@Test
	public void testReplaceAliasPast24Hours() {
		Calendar calendar = new GregorianCalendar(
			2018, Calendar.MAY, 15, 23, 59, 59);

		Assert.assertEquals(
			"[20180514235959 TO 20180515225959]",
			_dateRangeFactory.replaceAliases(
				"[past-24-hours TO past-hour]", calendar));
	}

	@Test
	public void testReplaceAliasPastHour() {
		Calendar calendar = new GregorianCalendar(
			2018, Calendar.MAY, 15, 23, 59, 59);

		Assert.assertEquals(
			"[20180515225959 TO 20180515235959]",
			_dateRangeFactory.replaceAliases("[past-hour TO *]", calendar));
	}

	@Test
	public void testReplaceAliasPastMonth() {
		Calendar calendar = new GregorianCalendar(
			2018, Calendar.MAY, 15, 23, 59, 59);

		Assert.assertEquals(
			"[20180415235959 TO 20180508235959]",
			_dateRangeFactory.replaceAliases(
				"[past-month TO past-week]", calendar));
	}

	@Test
	public void testReplaceAliasPastWeek() {
		Calendar calendar = new GregorianCalendar(
			2018, Calendar.MAY, 15, 23, 59, 59);

		Assert.assertEquals(
			"[20180508235959 TO 20180514235959]",
			_dateRangeFactory.replaceAliases(
				"[past-week TO past-24-hours]", calendar));
	}

	@Test
	public void testReplaceAliasPastYear() {
		Calendar calendar = new GregorianCalendar(
			2018, Calendar.MAY, 15, 23, 59, 59);

		Assert.assertEquals(
			"[20170515235959 TO 20180415235959]",
			_dateRangeFactory.replaceAliases(
				"[past-year TO past-month]", calendar));
	}

	private final DateRangeFactory _dateRangeFactory = new DateRangeFactory(
		new DateFormatFactoryImpl());

}