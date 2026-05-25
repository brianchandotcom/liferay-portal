/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.graphql.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Leslie Wong
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetWorkspaceGroupChannelAssetSummariesPageResponse {

	public AssetSummaryMetricBag getAssetSummaryMetricBag() {
		return _assetSummaryMetricBag;
	}

	public void setAssetSummaryMetricBag(
		AssetSummaryMetricBag assetSummaryMetricBag) {

		_assetSummaryMetricBag = assetSummaryMetricBag;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class AssetSummaryMetric {

		public String getAssetId() {
			return _assetId;
		}

		public String getAssetTitle() {
			return _assetTitle;
		}

		public String getAssetType() {
			return _assetType;
		}

		public Metric getDownloadsMetric() {
			return _downloadsMetric;
		}

		public Metric getImpressionsMetric() {
			return _impressionsMetric;
		}

		public Metric getReadsMetric() {
			return _readsMetric;
		}

		public Metric getViewsMetric() {
			return _viewsMetric;
		}

		public void setAssetId(String assetId) {
			_assetId = assetId;
		}

		public void setAssetTitle(String assetTitle) {
			_assetTitle = assetTitle;
		}

		public void setAssetType(String assetType) {
			_assetType = assetType;
		}

		public void setDownloadsMetric(Metric downloadsMetric) {
			_downloadsMetric = downloadsMetric;
		}

		public void setImpressionsMetric(Metric impressionsMetric) {
			_impressionsMetric = impressionsMetric;
		}

		public void setReadsMetric(Metric readsMetric) {
			_readsMetric = readsMetric;
		}

		public void setViewsMetric(Metric viewsMetric) {
			_viewsMetric = viewsMetric;
		}

		private String _assetId;
		private String _assetTitle;
		private String _assetType;
		private Metric _downloadsMetric;
		private Metric _impressionsMetric;
		private Metric _readsMetric;
		private Metric _viewsMetric;

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class AssetSummaryMetricBag {

		public List<AssetSummaryMetric> getAssetSummaryMetrics() {
			return _assetSummaryMetrics;
		}

		public Integer getTotal() {
			return _total;
		}

		public void setAssetSummaryMetrics(
			List<AssetSummaryMetric> assetSummaryMetrics) {

			_assetSummaryMetrics = assetSummaryMetrics;
		}

		public void setTotal(Integer total) {
			_total = total;
		}

		private List<AssetSummaryMetric> _assetSummaryMetrics;
		private Integer _total;

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Metric {

		public Trend getTrend() {
			return _trend;
		}

		public Double getValue() {
			return _value;
		}

		public void setTrend(Trend trend) {
			_trend = trend;
		}

		public void setValue(Double value) {
			_value = value;
		}

		private Trend _trend;
		private Double _value;

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Trend {

		public Double getPercentage() {
			return _percentage;
		}

		public void setPercentage(Double percentage) {
			_percentage = percentage;
		}

		private Double _percentage;

	}

	private AssetSummaryMetricBag _assetSummaryMetricBag;

}