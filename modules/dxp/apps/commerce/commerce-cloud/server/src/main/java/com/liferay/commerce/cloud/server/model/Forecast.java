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

	public List<ForecastValue> getValues() {
		return _values;
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

	public void setValues(List<ForecastValue> values) {
		_values = values;
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
	private List<ForecastValue> _values;

}