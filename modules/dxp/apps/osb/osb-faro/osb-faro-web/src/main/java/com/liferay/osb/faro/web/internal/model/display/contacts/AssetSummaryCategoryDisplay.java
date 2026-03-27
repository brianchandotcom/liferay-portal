/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.osb.faro.engine.client.model.AssetSummaryCategory;

/**
 * @author Ivica Cardic
 */
public class AssetSummaryCategoryDisplay {

	public AssetSummaryCategoryDisplay(
		AssetSummaryCategory assetSummaryCategory) {

		_id = assetSummaryCategory.getId();
		_name = assetSummaryCategory.getName();
	}

	@JsonProperty("id")
	private final String _id;

	private final String _name;

}