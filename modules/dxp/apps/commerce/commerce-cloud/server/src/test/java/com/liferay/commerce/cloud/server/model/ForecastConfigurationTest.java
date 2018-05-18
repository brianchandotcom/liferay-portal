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

import java.util.Arrays;
import java.util.List;

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

		Assert.assertEquals(
			ForecastFrequency.DAILY, forecastScheduler.getFrequency());

		List<ForecastItemConfiguration> forecastItemConfigurations =
			forecastScheduler.getItems();

		Assert.assertEquals(
			forecastItemConfigurations.toString(), 2,
			forecastItemConfigurations.size());

		_assertForecastItemConfiguration(
			forecastItemConfigurations.get(0), 123,
			Arrays.asList("foo", "bar", "baz"), ForecastLevel.COMPANY,
			ForecastPeriod.MONTHLY, ForecastTarget.REVENUE);
		_assertForecastItemConfiguration(
			forecastItemConfigurations.get(1), 456, null,
			ForecastLevel.CUSTOMER_SKU, ForecastPeriod.WEEKLY,
			ForecastTarget.QUANTITY);

		Assert.assertEquals("+03:00", forecastScheduler.getTimeZoneOffset());
	}

	private void _assertForecastItemConfiguration(
		ForecastItemConfiguration forecastItemConfiguration, int ahead,
		List<String> ids, ForecastLevel forecastLevel,
		ForecastPeriod forecastPeriod, ForecastTarget forecastTarget) {

		Assert.assertEquals(ahead, forecastItemConfiguration.getAhead());
		Assert.assertEquals(ids, forecastItemConfiguration.getIds());
		Assert.assertEquals(
			forecastLevel, forecastItemConfiguration.getLevel());
		Assert.assertEquals(
			forecastPeriod, forecastItemConfiguration.getPeriod());
		Assert.assertEquals(
			forecastTarget, forecastItemConfiguration.getTarget());
	}

}