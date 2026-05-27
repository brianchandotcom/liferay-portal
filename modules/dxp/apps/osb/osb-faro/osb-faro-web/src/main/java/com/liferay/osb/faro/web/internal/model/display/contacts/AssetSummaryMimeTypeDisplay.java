/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.AssetSummaryMimeType;

/**
 * @author Nilton Vieira
 */
public class AssetSummaryMimeTypeDisplay {

	public AssetSummaryMimeTypeDisplay(
		AssetSummaryMimeType assetSummaryMimeType) {

		_id = assetSummaryMimeType.getId();
		_name = assetSummaryMimeType.getName();
	}

	private final String _id;
	private final String _name;

}