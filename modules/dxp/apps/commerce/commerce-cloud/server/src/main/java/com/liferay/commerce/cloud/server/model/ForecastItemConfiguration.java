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