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
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Peter Borkuti
 */
public class CronTextTest {

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidFrequency() throws Exception {
		Calendar startDate = new GregorianCalendar(2010, 0, 2, 3, 4, 5);

		new CronText(startDate, 100, 10);
	}

	@Test
	public void testValidFrequencies() throws Exception {
		Calendar startDate = new GregorianCalendar(2010, 0, 2, 3, 4, 5);

		Assert.assertEquals(
			"5 4 3 2 1 ? 2010",
			getCronText(startDate, CronText.NO_FREQUENCY, 10));

		Assert.assertEquals(
			"*/11 * * * * ? *",
			getCronText(startDate, CronText.SECONDLY_FREQUENCY, 11));

		Assert.assertEquals(
			"5 */12 * * * ? *",
			getCronText(startDate, CronText.MINUTELY_FREQUENCY, 12));

		Assert.assertEquals(
			"5 * */13 * * ? *",
			getCronText(startDate, CronText.HOURLY_FREQUENCY, 13));

		Assert.assertEquals(
			"5 4 3 2/14 * ? *",
			getCronText(startDate, CronText.DAILY_FREQUENCY, 14));

		Assert.assertEquals(
			"5 4 3 2/105 * ? *",
			getCronText(startDate, CronText.WEEKLY_FREQUENCY, 15));

		Assert.assertEquals(
			"5 4 3 2 1/6 ? *",
			getCronText(startDate, CronText.MONTHLY_FREQUENCY, 6));

		Assert.assertEquals(
			"5 4 3 2 1 ? 2010/7",
			getCronText(startDate, CronText.YEARLY_FREQUENCY, 7));
	}

	protected String getCronText(
		Calendar startDate, int frequency, int interval) {

		CronText cronText = new CronText(startDate, frequency, interval);

		return cronText.toString();
	}

}