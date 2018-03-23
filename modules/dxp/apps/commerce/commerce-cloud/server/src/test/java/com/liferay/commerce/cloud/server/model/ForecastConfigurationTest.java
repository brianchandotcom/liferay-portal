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

package com.liferay.commerce.cloud.server.model;

import com.liferay.commerce.cloud.server.test.util.FileTestUtil;

import io.vertx.core.json.JsonObject;

import java.io.IOException;

import java.util.Collections;
import java.util.EnumSet;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class ForecastConfigurationTest {

	@Test
	public void testFromJson() throws IOException {
		String json = FileTestUtil.read(
			getClass(), "dependencies/forecast_configuration.json");

		JsonObject jsonObject = new JsonObject(json);

		ForecastConfiguration forecastScheduler = new ForecastConfiguration(
			jsonObject);

		Assert.assertEquals(123, forecastScheduler.getAhead());
		Assert.assertEquals(
			ForecastConfiguration.Frequency.DAILY,
			forecastScheduler.getFrequency());
		Assert.assertEquals(
			Collections.singleton(ForecastConfiguration.Level.COMPANY),
			forecastScheduler.getLevels());
		Assert.assertEquals(
			EnumSet.of(
				ForecastConfiguration.Period.MONTHLY,
				ForecastConfiguration.Period.WEEKLY),
			forecastScheduler.getPeriods());
		Assert.assertEquals(
			EnumSet.of(ForecastConfiguration.Target.REVENUE),
			forecastScheduler.getTargets());
		Assert.assertEquals("+03:00", forecastScheduler.getTimeZoneId());
	}

}