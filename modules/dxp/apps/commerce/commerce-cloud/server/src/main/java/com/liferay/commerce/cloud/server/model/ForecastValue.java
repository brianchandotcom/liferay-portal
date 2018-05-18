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

/**
 * @author Andrea Di Giorgi
 */
@DataObject(generateConverter = true, publicConverter = false)
public class ForecastValue implements JsonSerializable {

	public ForecastValue() {
	}

	public ForecastValue(JsonObject jsonObject) {
		ForecastValueConverter.fromJson(jsonObject, this);
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

		ForecastValueConverter.toJson(this, jsonObject);

		return jsonObject;
	}

	private String _lowerValue;
	private long _time;
	private String _upperValue;
	private String _value;

}