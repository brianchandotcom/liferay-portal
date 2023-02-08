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

package com.liferay.analytics.reports.web.internal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Cistina González
 */
public class HistoricalMetric {

	public HistoricalMetric() {
	}

	public HistoricalMetric(
		List<HistogramMetric> histogramMetrics, double value) {

		if (histogramMetrics == null) {
			throw new IllegalArgumentException("Histogram metrics are null");
		}

		_histogramMetrics = Collections.unmodifiableList(histogramMetrics);
		_value = value;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof HistoricalMetric)) {
			return false;
		}

		HistoricalMetric historicalMetric = (HistoricalMetric)object;

		if (Objects.equals(
				_histogramMetrics, historicalMetric._histogramMetrics) &&
			Objects.equals(_value, historicalMetric._value)) {

			return true;
		}

		return false;
	}

	public List<HistogramMetric> getHistogramMetrics() {
		return _histogramMetrics;
	}

	public Double getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_histogramMetrics, _value);
	}

	public void setHistogramMetrics(List<HistogramMetric> histogramMetrics) {
		_histogramMetrics = histogramMetrics;
	}

	public void setValue(double value) {
		_value = value;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"histogram",
			JSONUtil.toJSONArray(
				_histogramMetrics,
				histogramMetric -> histogramMetric.toJSONObject(), _log)
		).put(
			"value", _value
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HistoricalMetric.class.getName());

	@JsonProperty("histogram")
	private List<HistogramMetric> _histogramMetrics;

	private double _value;

}