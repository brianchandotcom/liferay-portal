/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scheduler;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Peter Borkuti
 */
public class CronTextTest {

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidFrequency() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.set(2010, 0, 2, 3, 4, 5);

		CronText cronText = new CronText(cal, 100, 10);
	}

	@Test
	public void testValidFrequencies() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.set(2010, 0, 2, 3, 4, 5);

		CronText cronText = new CronText(cal, CronText.NO_FREQUENCY, 10);
		Assert.assertEquals("5 4 3 2 1 ? 2010", cronText.toString());

		cronText = new CronText(cal, CronText.SECONDLY_FREQUENCY, 11);
		Assert.assertEquals("*/11 * * * * ? *", cronText.toString());

		cronText = new CronText(cal, CronText.MINUTELY_FREQUENCY, 12);
		Assert.assertEquals("5 */12 * * * ? *", cronText.toString());

		cronText = new CronText(cal, CronText.HOURLY_FREQUENCY, 13);
		Assert.assertEquals("5 * */13 * * ? *", cronText.toString());

		cronText = new CronText(cal, CronText.DAILY_FREQUENCY, 14);
		Assert.assertEquals("5 4 3 2/14 * ? *", cronText.toString());

		cronText = new CronText(cal, CronText.WEEKLY_FREQUENCY, 15);
		Assert.assertEquals("5 4 3 2/105 * ? *", cronText.toString());

		cronText = new CronText(cal, CronText.MONTHLY_FREQUENCY, 6);
		Assert.assertEquals("5 4 3 2 1/6 ? *", cronText.toString());

		cronText = new CronText(cal, CronText.YEARLY_FREQUENCY, 7);
		Assert.assertEquals("5 4 3 2 1 ? 2010/7", cronText.toString());
	}

}