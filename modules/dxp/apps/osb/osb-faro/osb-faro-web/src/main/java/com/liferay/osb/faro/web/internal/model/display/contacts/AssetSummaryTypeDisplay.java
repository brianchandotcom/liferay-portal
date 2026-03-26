/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.osb.faro.engine.client.model.AssetSummaryType;

/**
 * @author Ivica Cardic
 */
public class AssetSummaryTypeDisplay {

	public AssetSummaryTypeDisplay(AssetSummaryType assetSummaryType) {
		_id = assetSummaryType.getId();
		_name = assetSummaryType.getName();
	}

	@JsonProperty("id")
	private final String _id;

	private final String _name;

}