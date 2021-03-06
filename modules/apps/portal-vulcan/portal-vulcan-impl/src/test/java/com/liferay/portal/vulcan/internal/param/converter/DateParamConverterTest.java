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

package com.liferay.portal.vulcan.internal.param.converter;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class DateParamConverterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testFromString() {
		String dateString = "2019-02-18";

		Date date = _dateParamConverter.fromString(dateString);

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		Assert.assertEquals(18, calendar.get(Calendar.DATE));
		Assert.assertEquals(1, calendar.get(Calendar.MONTH));
		Assert.assertEquals(2019, calendar.get(Calendar.YEAR));

		String dateTimeString = "2019-01-27T08:56:45.236Z";

		date = _dateParamConverter.fromString(dateTimeString);

		calendar = Calendar.getInstance();

		calendar.setTime(date);

		Assert.assertEquals(27, calendar.get(Calendar.DATE));
		Assert.assertEquals(8, calendar.get(Calendar.HOUR));
		Assert.assertEquals(56, calendar.get(Calendar.MINUTE));
		Assert.assertEquals(0, calendar.get(Calendar.MONTH));
		Assert.assertEquals(45, calendar.get(Calendar.SECOND));
		Assert.assertEquals(2019, calendar.get(Calendar.YEAR));
	}

	@Test
	public void testToString() {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.DATE, 12);
		calendar.set(Calendar.HOUR_OF_DAY, 15);
		calendar.set(Calendar.MILLISECOND, 871);
		calendar.set(Calendar.MINUTE, 1);
		calendar.set(Calendar.MONTH, 7);
		calendar.set(Calendar.SECOND, 58);
		calendar.set(Calendar.YEAR, 2019);

		String dateString = _dateParamConverter.toString(calendar.getTime());

		Assert.assertEquals("2019-08-12T15:01:58.871Z", dateString);
	}

	private final DateParamConverter _dateParamConverter =
		new DateParamConverter();

}