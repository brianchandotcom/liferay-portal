/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.asset.library.internal.util;

import com.liferay.headless.asset.library.dto.v1_0.AssetLibrary;

/**
 * @author Adolfo Pérez
 */
public class AssetLibraryUtil {

	public static AssetLibrary.Type getAssetLibraryType(int depotEntryType) {
		return _assetLibraryTypes[depotEntryType];
	}

	public static int getDepotEntryType(AssetLibrary.Type assetLibraryType) {
		return assetLibraryType.ordinal();
	}

	private static final AssetLibrary.Type[] _assetLibraryTypes =
		new AssetLibrary.Type[] {
			AssetLibrary.Type.ASSET_LIBRARY, AssetLibrary.Type.SPACE
		};

}