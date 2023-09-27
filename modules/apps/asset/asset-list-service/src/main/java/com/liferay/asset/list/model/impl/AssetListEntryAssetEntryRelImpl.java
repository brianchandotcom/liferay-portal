/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.model.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetListEntryAssetEntryRelImpl
	extends AssetListEntryAssetEntryRelBaseImpl {

	@Override
	public String getAssetEntryUuid() {
		if (Validator.isNotNull(_assetEntryUuid)) {
			return _assetEntryUuid;
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchAssetEntry(
			getAssetEntryId());

		if (assetEntry != null) {
			_assetEntryUuid = assetEntry.getClassUuid();
		}

		return _assetEntryUuid;
	}

	@Override
	public void setAssetEntryUuid(String assetEntryUuid) {
		_assetEntryUuid = assetEntryUuid;
	}

	private String _assetEntryUuid;

}