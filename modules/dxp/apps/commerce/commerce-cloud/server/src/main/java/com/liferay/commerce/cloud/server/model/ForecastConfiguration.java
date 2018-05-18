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