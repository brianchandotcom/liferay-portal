/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.client.dto.v1_0;

import com.liferay.analytics.cms.rest.client.function.UnsafeSupplier;
import com.liferay.analytics.cms.rest.client.serdes.v1_0.PerformanceMetricSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
public class PerformanceMetric implements Cloneable, Serializable {

	public static PerformanceMetric toDTO(String json) {
		return PerformanceMetricSerDes.toDTO(json);
	}

	public String getMetricType() {
		return metricType;
	}

	public void setMetricType(String metricType) {
		this.metricType = metricType;
	}

	public void setMetricType(
		UnsafeSupplier<String, Exception> metricTypeUnsafeSupplier) {

		try {
			metricType = metricTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String metricType;

	public Metric[] getMetrics() {
		return metrics;
	}

	public void setMetrics(Metric[] metrics) {
		this.metrics = metrics;
	}

	public void setMetrics(
		UnsafeSupplier<Metric[], Exception> metricsUnsafeSupplier) {

		try {
			metrics = metricsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Metric[] metrics;

	@Override
	public PerformanceMetric clone() throws CloneNotSupportedException {
		return (PerformanceMetric)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PerformanceMetric)) {
			return false;
		}

		PerformanceMetric performanceMetric = (PerformanceMetric)object;

		return Objects.equals(toString(), performanceMetric.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PerformanceMetricSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1222794928