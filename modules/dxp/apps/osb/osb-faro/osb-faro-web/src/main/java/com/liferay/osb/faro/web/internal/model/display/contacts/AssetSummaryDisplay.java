/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.osb.faro.engine.client.model.AssetSummary;
import com.liferay.osb.faro.engine.client.model.Metric;

/**
 * @author Marcos Martins
 */
public class AssetSummaryDisplay {

	public AssetSummaryDisplay(AssetSummary assetSummary) {
		_assetId = assetSummary.getAssetId();
		_assetTitle = assetSummary.getAssetTitle();
		_assetType = assetSummary.getAssetType();
		_downloadsMetric = assetSummary.getDownloadsMetric();
		_impressionsMetric = assetSummary.getImpressionsMetric();
		_viewsMetric = assetSummary.getViewsMetric();
	}

	@JsonProperty("id")
	private final String _assetId;

	private final String _assetTitle;
	private final String _assetType;
	private final Metric _downloadsMetric;
	private final Metric _impressionsMetric;
	private final Metric _viewsMetric;

}