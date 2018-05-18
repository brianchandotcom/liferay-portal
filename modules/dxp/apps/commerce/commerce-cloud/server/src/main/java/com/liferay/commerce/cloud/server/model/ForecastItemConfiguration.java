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
public class ForecastItemConfiguration implements JsonSerializable {

	public ForecastItemConfiguration() {
	}

	public ForecastItemConfiguration(JsonObject jsonObject) {
		ForecastItemConfigurationConverter.fromJson(jsonObject, this);
	}

	public int getAhead() {
		return _ahead;
	}

	public List<String> getIds() {
		return _ids;
	}

	public ForecastLevel getLevel() {
		return _level;
	}

	public ForecastPeriod getPeriod() {
		return _period;
	}

	public ForecastTarget getTarget() {
		return _target;
	}

	public void setAhead(int ahead) {
		_ahead = ahead;
	}

	public void setIds(List<String> ids) {
		_ids = ids;
	}

	public void setLevel(ForecastLevel level) {
		_level = level;
	}

	public void setPeriod(ForecastPeriod period) {
		_period = period;
	}

	public void setTarget(ForecastTarget target) {
		_target = target;
	}

	@Override
	public JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();

		ForecastItemConfigurationConverter.toJson(this, jsonObject);

		return jsonObject;
	}

	private int _ahead;
	private List<String> _ids;
	private ForecastLevel _level;
	private ForecastPeriod _period;
	private ForecastTarget _target;

}