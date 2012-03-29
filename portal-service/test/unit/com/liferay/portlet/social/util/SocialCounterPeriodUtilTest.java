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
package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@PrepareForTest(PropsUtil.class)
@RunWith(PowerMockRunner.class)
public class SocialCounterPeriodUtilTest extends PowerMockito {

	@Test
	public void testGetActivityDay() {
		_initMocks();

		Calendar calendar = new GregorianCalendar(2011, Calendar.JANUARY, 1);

		int activityDay = SocialCounterPeriodUtil.getActivityDay(calendar);

		Assert.assertEquals(0, activityDay);

		activityDay = SocialCounterPeriodUtil.getActivityDay(
			calendar.getTimeInMillis());

		Assert.assertEquals(0, activityDay);

		calendar = new GregorianCalendar(2011, Calendar.FEBRUARY, 1);

		activityDay = SocialCounterPeriodUtil.getActivityDay(calendar);

		Assert.assertEquals(31, activityDay);

		activityDay = SocialCounterPeriodUtil.getActivityDay(
			calendar.getTimeInMillis());

		Assert.assertEquals(31, activityDay);
	}

	@Test
	public void testGetDate() {
		_initMocks();

		Date activityDate = SocialCounterPeriodUtil.getDate(0);

		Calendar calendar = new GregorianCalendar(2011, Calendar.JANUARY, 1);

		Assert.assertEquals(calendar.getTime(), activityDate);

		calendar = new GregorianCalendar(2011, Calendar.FEBRUARY, 1);

		activityDate = SocialCounterPeriodUtil.getDate(31);

		Assert.assertEquals(calendar.getTime(), activityDate);
	}

	@Test
	public void testGetPeriodLength() {
		_initMocks();

		int periodLength = SocialCounterPeriodUtil.getPeriodLength();

		Assert.assertEquals(1, periodLength);

		periodLength = SocialCounterPeriodUtil.getPeriodLength(-1);

		Assert.assertEquals(1, periodLength);
	}

	@Test
	public void testGetStartPeriod() {
		_initMocks();

		Calendar calendar = new GregorianCalendar(2011, Calendar.JANUARY, 15);

		int offset = SocialCounterPeriodUtil.getStartPeriod(
			calendar.getTimeInMillis());

		Assert.assertEquals(14, offset);

		int currentStartPeriod = SocialCounterPeriodUtil.getStartPeriod();

		Assert.assertEquals(
			currentStartPeriod, SocialCounterPeriodUtil.getStartPeriod(0));

		Assert.assertEquals(
			currentStartPeriod - 1, SocialCounterPeriodUtil.getStartPeriod(-1));
	}

	private void _initMocks() {
		mockStatic(PropsUtil.class);

		when(
			PropsUtil.get(PropsKeys.SOCIAL_ACTIVITY_COUNTER_PERIOD_LENGTH)
		).thenReturn("1");
	}

}