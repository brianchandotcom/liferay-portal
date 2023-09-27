/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.model;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetEntryWrapper;

/**
 * @author Jorge Ferrer
 */
public class VersionedAssetEntry extends AssetEntryWrapper {

	public VersionedAssetEntry(AssetEntry assetEntry, int versionType) {
		super(assetEntry);

		_versionType = versionType;
	}

	public int getVersionType() {
		return _versionType;
	}

	private final int _versionType;

}