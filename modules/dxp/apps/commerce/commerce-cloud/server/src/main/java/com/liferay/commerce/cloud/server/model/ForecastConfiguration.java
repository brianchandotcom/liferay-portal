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

import java.util.Set;

/**
 * @author Andrea Di Giorgi
 */
@DataObject(generateConverter = true, publicConverter = false)
public class ForecastConfiguration implements JsonSerializable {

	public ForecastConfiguration(JsonObject jsonObject) {
		ForecastConfigurationConverter.fromJson(jsonObject, this);
	}

	public int getAhead() {
		return _ahead;
	}

	public Frequency getFrequency() {
		return _frequency;
	}

	public Set<Level> getLevels() {
		return _levels;
	}

	public Set<ForecastPeriod> getPeriods() {
		return _periods;
	}

	public Set<ForecastTarget> getTargets() {
		return _targets;
	}

	public String getTimeZoneOffset() {
		return _timeZoneOffset;
	}

	public void setAhead(int ahead) {
		_ahead = ahead;
	}

	public void setFrequency(Frequency frequency) {
		_frequency = frequency;
	}

	public void setLevels(Set<Level> levels) {
		_levels = levels;
	}

	public void setPeriods(Set<ForecastPeriod> periods) {
		_periods = periods;
	}

	public void setTargets(Set<ForecastTarget> targets) {
		_targets = targets;
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

	public enum Frequency {

		DAILY, MONTHLY, WEEKLY

	}

	public enum Level {

		COMPANY, CUSTOMER, CUSTOMER_SKU, SKU

	}

	private int _ahead;
	private Frequency _frequency;
	private Set<Level> _levels;
	private Set<ForecastPeriod> _periods;
	private Set<ForecastTarget> _targets;
	private String _timeZoneOffset;

}