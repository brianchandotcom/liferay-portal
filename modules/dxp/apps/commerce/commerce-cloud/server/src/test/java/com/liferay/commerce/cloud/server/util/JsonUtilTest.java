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

package com.liferay.commerce.cloud.server.util;

import com.liferay.commerce.cloud.server.model.Forecast;
import com.liferay.commerce.cloud.server.model.ForecastPeriod;
import com.liferay.commerce.cloud.server.model.ForecastTarget;
import com.liferay.commerce.cloud.server.model.JsonSerializable;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class JsonUtilTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testToJsonArray() {
		List<Forecast> forecasts = new ArrayList<>(2);

		forecasts.add(_createForecast(0));
		forecasts.add(_createForecast(1));

		Object object = forecasts;

		JsonArray jsonArray = JsonUtil.toJsonArray(
			(Iterable<JsonSerializable>)object);

		Assert.assertEquals(jsonArray.toString(), 2, jsonArray.size());

		for (int i = 0; i < jsonArray.size(); i++) {
			_assertForecastJsonObject(jsonArray, i);
		}
	}

	private static void _assertForecastJsonObject(
		JsonArray jsonArray, int index) {

		JsonObject jsonObject = jsonArray.getJsonObject(index);

		Assert.assertEquals(
			jsonObject.getString("assertivity"), String.valueOf(index));
		Assert.assertEquals(
			jsonObject.getLong("companyId"), Long.valueOf(index));
		Assert.assertEquals(
			jsonObject.getLong("customerId"), Long.valueOf(index));
		Assert.assertEquals(jsonObject.getString("sku"), "product-" + index);
		Assert.assertEquals(jsonObject.getLong("time"), Long.valueOf(index));
	}

	private static Forecast _createForecast(int index) {
		Forecast forecast = new Forecast();

		forecast.setAssertivity(String.valueOf(index));
		forecast.setCompanyId(index);
		forecast.setCustomerId(index);
		forecast.setPeriod(ForecastPeriod.MONTHLY);
		forecast.setSKU("product-" + index);
		forecast.setTarget(ForecastTarget.QUANTITY);
		forecast.setTime(index);

		return forecast;
	}

}