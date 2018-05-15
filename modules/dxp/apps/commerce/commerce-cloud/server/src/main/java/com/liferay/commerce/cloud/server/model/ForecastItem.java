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

/**
 * @author Andrea Di Giorgi
 */
@DataObject(generateConverter = true, publicConverter = false)
public class ForecastItem implements JsonSerializable {

	public ForecastItem() {
	}

	public ForecastItem(JsonObject jsonObject) {
		ForecastItemConverter.fromJson(jsonObject, this);
	}

	public String getLowerValue() {
		return _lowerValue;
	}

	public long getTime() {
		return _time;
	}

	public String getUpperValue() {
		return _upperValue;
	}

	public String getValue() {
		return _value;
	}

	public void setLowerValue(String lowerValue) {
		_lowerValue = lowerValue;
	}

	public void setTime(long time) {
		_time = time;
	}

	public void setUpperValue(String upperValue) {
		_upperValue = upperValue;
	}

	public void setValue(String value) {
		_value = value;
	}

	@Override
	public JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();

		ForecastItemConverter.toJson(this, jsonObject);

		return jsonObject;
	}

	private String _lowerValue;
	private long _time;
	private String _upperValue;
	private String _value;

}