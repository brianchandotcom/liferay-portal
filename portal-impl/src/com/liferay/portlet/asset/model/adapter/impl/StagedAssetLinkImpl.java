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

package com.liferay.portlet.asset.model.adapter.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.adapter.StagedAssetLink;
import com.liferay.portlet.asset.model.impl.AssetLinkImpl;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;

/**
 * @author Mate Thurzo
 */
public class StagedAssetLinkImpl extends AssetLinkImpl
	implements StagedAssetLink {

	public StagedAssetLinkImpl(AssetLink assetLink) {
		_assetLink = assetLink;
	}

	@Override
	public Date getLastPublishDate() {
		return null;
	}

	@Override
	public Date getModifiedDate() {
		return _assetLink.getCreateDate();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedAssetLink.class);
	}

	@Override
	public String getUuid() {
		AssetEntry assetEntry1 = AssetEntryLocalServiceUtil.fetchAssetEntry(
			_assetLink.getEntryId1());
		AssetEntry assetEntry2 = AssetEntryLocalServiceUtil.fetchAssetEntry(
			_assetLink.getEntryId2());

		if ((assetEntry1 == null) || (assetEntry2 == null)) {
			return null;
		}

		return assetEntry1.getClassUuid() + StringPool.POUND +
			assetEntry2.getClassUuid();
	}

	@Override
	public void setLastPublishDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setModifiedDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	private final AssetLink _assetLink;

}