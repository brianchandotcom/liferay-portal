/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.util;

import com.liferay.asset.kernel.model.AssetEntry;

import java.util.List;

/**
 * Provides a wrapper class to group asset entries by asset category title.
 *
 * @author Eudaldo Alonso
 */
public class AssetEntryResult {

	public AssetEntryResult(List<AssetEntry> assetEntries) {
		_assetEntries = assetEntries;
	}

	public AssetEntryResult(String title, List<AssetEntry> assetEntries) {
		_title = title;
		_assetEntries = assetEntries;
	}

	public List<AssetEntry> getAssetEntries() {
		return _assetEntries;
	}

	public String getTitle() {
		return _title;
	}

	public void setAssetEntries(List<AssetEntry> assetEntries) {
		_assetEntries = assetEntries;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private List<AssetEntry> _assetEntries;
	private String _title;

}