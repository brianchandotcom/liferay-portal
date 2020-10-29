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

package com.liferay.commerce.machine.learning.internal.forecast;

import com.liferay.commerce.machine.learning.forecast.CommerceMLForecast;

import java.util.Date;

/**
 * @author Riccardo Ferrari
 */
public class BaseCommerceMLForecastImpl implements CommerceMLForecast {

	@Override
	public float getActual() {
		return _actual;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public float getForecast() {
		return _forecast;
	}

	@Override
	public long getForecastId() {
		return _forecastId;
	}

	@Override
	public float getForecastLowerBound() {
		return _forecastLowerBound;
	}

	@Override
	public float getForecastUpperBound() {
		return _forecastUpperBound;
	}

	@Override
	public String getJobId() {
		return _jobId;
	}

	@Override
	public String getPeriod() {
		return _period;
	}

	@Override
	public String getScope() {
		return _scope;
	}

	@Override
	public String getTarget() {
		return _target;
	}

	@Override
	public Date getTimestamp() {
		return _timestamp;
	}

	@Override
	public void setActual(float actual) {
		_actual = actual;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public void setForecast(float forecast) {
		_forecast = forecast;
	}

	@Override
	public void setForecastId(long forecastId) {
		_forecastId = forecastId;
	}

	@Override
	public void setForecastLowerBound(float forecastLowerBound) {
		_forecastLowerBound = forecastLowerBound;
	}

	@Override
	public void setForecastUpperBound(float forecastUpperBound) {
		_forecastUpperBound = forecastUpperBound;
	}

	@Override
	public void setJobId(String jobId) {
		_jobId = jobId;
	}

	@Override
	public void setPeriod(String period) {
		_period = period;
	}

	@Override
	public void setScope(String scope) {
		_scope = scope;
	}

	@Override
	public void setTarget(String target) {
		_target = target;
	}

	@Override
	public void setTimestamp(Date timestamp) {
		_timestamp = timestamp;
	}

	private float _actual;
	private long _companyId;
	private float _forecast;
	private long _forecastId;
	private float _forecastLowerBound;
	private float _forecastUpperBound;
	private String _jobId;
	private String _period;
	private String _scope;
	private String _target;
	private Date _timestamp;

}