/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.client.dto.v1_0;

import com.liferay.analytics.cms.rest.client.function.UnsafeSupplier;
import com.liferay.analytics.cms.rest.client.serdes.v1_0.PerformanceTopAssetItemSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
public class PerformanceTopAssetItem implements Cloneable, Serializable {

	public static PerformanceTopAssetItem toDTO(String json) {
		return PerformanceTopAssetItemSerDes.toDTO(json);
	}

	public Double getDownloads() {
		return downloads;
	}

	public void setDownloads(Double downloads) {
		this.downloads = downloads;
	}

	public void setDownloads(
		UnsafeSupplier<Double, Exception> downloadsUnsafeSupplier) {

		try {
			downloads = downloadsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double downloads;

	public Double getEngagement() {
		return engagement;
	}

	public void setEngagement(Double engagement) {
		this.engagement = engagement;
	}

	public void setEngagement(
		UnsafeSupplier<Double, Exception> engagementUnsafeSupplier) {

		try {
			engagement = engagementUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double engagement;

	public Double getImpressions() {
		return impressions;
	}

	public void setImpressions(Double impressions) {
		this.impressions = impressions;
	}

	public void setImpressions(
		UnsafeSupplier<Double, Exception> impressionsUnsafeSupplier) {

		try {
			impressions = impressionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double impressions;

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public void setMimeType(
		UnsafeSupplier<String, Exception> mimeTypeUnsafeSupplier) {

		try {
			mimeType = mimeTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String mimeType;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String title;

	public Trend getTrend() {
		return trend;
	}

	public void setTrend(Trend trend) {
		this.trend = trend;
	}

	public void setTrend(UnsafeSupplier<Trend, Exception> trendUnsafeSupplier) {
		try {
			trend = trendUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Trend trend;

	public Double getViews() {
		return views;
	}

	public void setViews(Double views) {
		this.views = views;
	}

	public void setViews(
		UnsafeSupplier<Double, Exception> viewsUnsafeSupplier) {

		try {
			views = viewsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double views;

	@Override
	public PerformanceTopAssetItem clone() throws CloneNotSupportedException {
		return (PerformanceTopAssetItem)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PerformanceTopAssetItem)) {
			return false;
		}

		PerformanceTopAssetItem performanceTopAssetItem =
			(PerformanceTopAssetItem)object;

		return Objects.equals(toString(), performanceTopAssetItem.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PerformanceTopAssetItemSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:867979568