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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.social.kernel.util.SocialCounterPeriodUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Zsolt Berentey
 */
public class SocialCounterPeriodUtilTest {

	@Before
	public void setUp() throws Exception {
		PropsTestUtil.setProps(
			PropsKeys.SOCIAL_ACTIVITY_COUNTER_PERIOD_LENGTH, "1");
	}

	@Test
	public void testGetActivityDay() {
		Calendar calendar = new GregorianCalendar(2011, Calendar.JANUARY, 1);

		Assert.assertEquals(
			0, SocialCounterPeriodUtil.getActivityDay(calendar));
		Assert.assertEquals(
			0,
			SocialCounterPeriodUtil.getActivityDay(calendar.getTimeInMillis()));

		calendar = new GregorianCalendar(2011, Calendar.FEBRUARY, 1);

		Assert.assertEquals(
			31, SocialCounterPeriodUtil.getActivityDay(calendar));
		Assert.assertEquals(
			31,
			SocialCounterPeriodUtil.getActivityDay(calendar.getTimeInMillis()));
	}

	@Test
	public void testGetDate() {
		Calendar calendar = new GregorianCalendar(2011, Calendar.JANUARY, 1);

		Assert.assertEquals(
			calendar.getTime(), SocialCounterPeriodUtil.getDate(0));

		calendar = new GregorianCalendar(2011, Calendar.FEBRUARY, 1);

		Assert.assertEquals(
			calendar.getTime(), SocialCounterPeriodUtil.getDate(31));
	}

	@Test
	public void testGetPeriodLength() {
		Assert.assertEquals(1, SocialCounterPeriodUtil.getPeriodLength());
		Assert.assertEquals(1, SocialCounterPeriodUtil.getPeriodLength(-1));
	}

	@Test
	public void testGetStartPeriod() {
		Calendar calendar = new GregorianCalendar(2011, Calendar.JANUARY, 15);

		Assert.assertEquals(
			14,
			SocialCounterPeriodUtil.getStartPeriod(calendar.getTimeInMillis()));

		int startPeriod = SocialCounterPeriodUtil.getStartPeriod();

		Assert.assertEquals(
			startPeriod, SocialCounterPeriodUtil.getStartPeriod(0));
		Assert.assertEquals(
			startPeriod - 1, SocialCounterPeriodUtil.getStartPeriod(-1));
	}

}