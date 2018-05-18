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

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
@DataObject(generateConverter = true, publicConverter = false)
public class ForecastConfiguration implements JsonSerializable {

	public ForecastConfiguration() {
	}

	public ForecastConfiguration(JsonObject jsonObject) {
		ForecastConfigurationConverter.fromJson(jsonObject, this);
	}

	public ForecastFrequency getFrequency() {
		return _frequency;
	}

	public List<ForecastItemConfiguration> getItems() {
		return _items;
	}

	public String getTimeZoneOffset() {
		return _timeZoneOffset;
	}

	public void setFrequency(ForecastFrequency frequency) {
		_frequency = frequency;
	}

	public void setItems(List<ForecastItemConfiguration> items) {
		_items = items;
	}

	public void setTimeZoneOffset(String timeZoneOffset) {
		_timeZoneOffset = timeZoneOffset;
	}

	@Override
	public JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();

		ForecastConfigurationConverter.toJson(this, jsonObject);

		return jsonObject;
	}

	private ForecastFrequency _frequency;
	private List<ForecastItemConfiguration> _items;
	private String _timeZoneOffset;

}