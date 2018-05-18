/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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