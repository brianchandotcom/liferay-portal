/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

	public AssetListEntryAssetEntryRelImpl() {
	}

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