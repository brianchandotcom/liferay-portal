/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.liferay.osb.faro.engine.client.model.AssetSummaryCategory;
import com.liferay.osb.faro.engine.client.model.Metric;

/**
 * @author Ivica Cardic
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetSummaryCategoryDisplay {

	public AssetSummaryCategoryDisplay(
		AssetSummaryCategory assetSummaryCategory) {

		_downloadsMetric = assetSummaryCategory.getDownloadsMetric();
		_id = assetSummaryCategory.getId();
		_impressionsMetric = assetSummaryCategory.getImpressionsMetric();
		_name = assetSummaryCategory.getName();
		_viewsMetric = assetSummaryCategory.getViewsMetric();
		_vocabularyId = assetSummaryCategory.getVocabularyId();
		_vocabularyName = assetSummaryCategory.getVocabularyName();
	}

	private final Metric _downloadsMetric;
	private final String _id;
	private final Metric _impressionsMetric;
	private final String _name;
	private final Metric _viewsMetric;
	private final String _vocabularyId;
	private final String _vocabularyName;

}