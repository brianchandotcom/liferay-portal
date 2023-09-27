/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Objects;

/**
 * @author Cistina González
 */
public class HistogramMetric {

	public HistogramMetric() {
	}

	public HistogramMetric(Date key, double value) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}

		_key = key;
		_value = value;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof HistogramMetric)) {
			return false;
		}

		HistogramMetric histogramMetric = (HistogramMetric)object;

		if (Objects.equals(_key, histogramMetric._key) &&
			Objects.equals(_value, histogramMetric._value)) {

			return true;
		}

		return false;
	}

	public Date getKey() {
		return _key;
	}

	public Double getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_key, _value);
	}

	public void setKey(Date key) {
		_key = key;
	}

	public void setValue(double value) {
		_value = value;
	}

	public JSONObject toJSONObject() {
		LocalDateTime localDateTime = _toLocalDateTime(_key);

		return JSONUtil.put(
			"key", localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
		).put(
			"value", _value
		);
	}

	private LocalDateTime _toLocalDateTime(Date date) {
		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		return zonedDateTime.toLocalDateTime();
	}

	@JsonFormat(
		pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
		shape = JsonFormat.Shape.STRING, timezone = "UTC"
	)
	private Date _key;

	private double _value;

}