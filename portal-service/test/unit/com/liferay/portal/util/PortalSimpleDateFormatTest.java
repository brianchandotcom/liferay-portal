/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Mate Thurzo
 */
public class PortalSimpleDateFormatTest {

	@BeforeClass
	public static void init() {
		_testCalendar = Calendar.getInstance();

		_testCalendar.set(Calendar.YEAR, 1984);
		_testCalendar.set(Calendar.MONTH, Calendar.MARCH);
		_testCalendar.set(Calendar.DAY_OF_MONTH, 9);

		_testCalendar.set(Calendar.HOUR_OF_DAY, 22);
		_testCalendar.set(Calendar.MINUTE, 40);
		_testCalendar.set(Calendar.SECOND, 0);
		_testCalendar.set(Calendar.MILLISECOND, 0);

		_testCalendar.setTimeZone(TimeZoneUtil.getDefault());
	}

	@Test
	public void testDateOnlyIsoFormat() {
		DateFormat dateFormat = new PortalSimpleDateFormat(
			"yyyyMMdd", Locale.getDefault());

		dateFormat.setTimeZone(TimeZoneUtil.getDefault());

		String expextedFormat = "19840309";

		String dateOnlyIsoFormat = dateFormat.format(_testCalendar.getTime());

		Assert.assertEquals(expextedFormat, dateOnlyIsoFormat);
	}

	@Test
	public void testIso8601Format() {
		DateFormat dateFormat = new PortalSimpleDateFormat(
			DateUtil.ISO_8601_PATTERN, Locale.getDefault());

		dateFormat.setTimeZone(TimeZoneUtil.getDefault());

		String expectedFormat = "1984-03-09T22:40:00+00:00";

		String iso8601FormatDate = dateFormat.format(_testCalendar.getTime());

		Assert.assertEquals(expectedFormat, iso8601FormatDate);
	}

	@Test
	public void testTimeOnlyIsoFormat() {
		DateFormat dateFormat = new PortalSimpleDateFormat(
			"HHmmss", Locale.getDefault());

		dateFormat.setTimeZone(TimeZoneUtil.getDefault());

		String expectedFormat = "224000";

		String timeOnlyIsoFormat = dateFormat.format(_testCalendar.getTime());

		Assert.assertEquals(expectedFormat, timeOnlyIsoFormat);
	}

	private static Calendar _testCalendar;

}