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
public class Forecast implements JsonSerializable {

	public Forecast() {
	}

	public Forecast(JsonObject jsonObject) {
		ForecastConverter.fromJson(jsonObject, this);
	}

	public String getAssertivity() {
		return _assertivity;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getCustomerId() {
		return _customerId;
	}

	public ForecastPeriod getPeriod() {
		return _period;
	}

	public String getSKU() {
		return _sku;
	}

	public ForecastTarget getTarget() {
		return _target;
	}

	public long getTime() {
		return _time;
	}

	public List<ForecastTimeSeries> getTimeSeries() {
		return _timeSeries;
	}

	public void setAssertivity(String assertivity) {
		_assertivity = assertivity;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCustomerId(long customerId) {
		_customerId = customerId;
	}

	public void setPeriod(ForecastPeriod period) {
		_period = period;
	}

	public void setSKU(String sku) {
		_sku = sku;
	}

	public void setTarget(ForecastTarget target) {
		_target = target;
	}

	public void setTime(long time) {
		_time = time;
	}

	public void setTimeSeries(List<ForecastTimeSeries> timeSeries) {
		_timeSeries = timeSeries;
	}

	@Override
	public JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();

		ForecastConverter.toJson(this, jsonObject);

		return jsonObject;
	}

	private String _assertivity;
	private long _companyId;
	private long _customerId;
	private ForecastPeriod _period;
	private String _sku;
	private ForecastTarget _target;
	private long _time;
	private List<ForecastTimeSeries> _timeSeries;

}