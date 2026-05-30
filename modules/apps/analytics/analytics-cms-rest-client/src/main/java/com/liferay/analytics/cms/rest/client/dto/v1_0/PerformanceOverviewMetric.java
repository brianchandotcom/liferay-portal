/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.client.dto.v1_0;

import com.liferay.analytics.cms.rest.client.function.UnsafeSupplier;
import com.liferay.analytics.cms.rest.client.serdes.v1_0.PerformanceOverviewMetricSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
public class PerformanceOverviewMetric implements Cloneable, Serializable {

	public static PerformanceOverviewMetric toDTO(String json) {
		return PerformanceOverviewMetricSerDes.toDTO(json);
	}

	public Metric getDownloadsMetric() {
		return downloadsMetric;
	}

	public void setDownloadsMetric(Metric downloadsMetric) {
		this.downloadsMetric = downloadsMetric;
	}

	public void setDownloadsMetric(
		UnsafeSupplier<Metric, Exception> downloadsMetricUnsafeSupplier) {

		try {
			downloadsMetric = downloadsMetricUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Metric downloadsMetric;

	public Metric getImpressionsMetric() {
		return impressionsMetric;
	}

	public void setImpressionsMetric(Metric impressionsMetric) {
		this.impressionsMetric = impressionsMetric;
	}

	public void setImpressionsMetric(
		UnsafeSupplier<Metric, Exception> impressionsMetricUnsafeSupplier) {

		try {
			impressionsMetric = impressionsMetricUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Metric impressionsMetric;

	public Metric getReadsMetric() {
		return readsMetric;
	}

	public void setReadsMetric(Metric readsMetric) {
		this.readsMetric = readsMetric;
	}

	public void setReadsMetric(
		UnsafeSupplier<Metric, Exception> readsMetricUnsafeSupplier) {

		try {
			readsMetric = readsMetricUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Metric readsMetric;

	public Metric getViewsMetric() {
		return viewsMetric;
	}

	public void setViewsMetric(Metric viewsMetric) {
		this.viewsMetric = viewsMetric;
	}

	public void setViewsMetric(
		UnsafeSupplier<Metric, Exception> viewsMetricUnsafeSupplier) {

		try {
			viewsMetric = viewsMetricUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Metric viewsMetric;

	@Override
	public PerformanceOverviewMetric clone() throws CloneNotSupportedException {
		return (PerformanceOverviewMetric)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PerformanceOverviewMetric)) {
			return false;
		}

		PerformanceOverviewMetric performanceOverviewMetric =
			(PerformanceOverviewMetric)object;

		return Objects.equals(toString(), performanceOverviewMetric.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PerformanceOverviewMetricSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1656600776