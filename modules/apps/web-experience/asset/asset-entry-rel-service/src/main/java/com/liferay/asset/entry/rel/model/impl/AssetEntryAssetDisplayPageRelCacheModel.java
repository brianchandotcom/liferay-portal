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

package com.liferay.asset.entry.rel.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.entry.rel.model.AssetEntryAssetDisplayPageRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetEntryAssetDisplayPageRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetDisplayPageRel
 * @generated
 */
@ProviderType
public class AssetEntryAssetDisplayPageRelCacheModel implements CacheModel<AssetEntryAssetDisplayPageRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryAssetDisplayPageRelCacheModel)) {
			return false;
		}

		AssetEntryAssetDisplayPageRelCacheModel assetEntryAssetDisplayPageRelCacheModel =
			(AssetEntryAssetDisplayPageRelCacheModel)obj;

		if (assetEntryAssetDisplayPageId == assetEntryAssetDisplayPageRelCacheModel.assetEntryAssetDisplayPageId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, assetEntryAssetDisplayPageId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{assetEntryAssetDisplayPageId=");
		sb.append(assetEntryAssetDisplayPageId);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", assetDisplayPageId=");
		sb.append(assetDisplayPageId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetEntryAssetDisplayPageRel toEntityModel() {
		AssetEntryAssetDisplayPageRelImpl assetEntryAssetDisplayPageRelImpl = new AssetEntryAssetDisplayPageRelImpl();

		assetEntryAssetDisplayPageRelImpl.setAssetEntryAssetDisplayPageId(assetEntryAssetDisplayPageId);
		assetEntryAssetDisplayPageRelImpl.setAssetEntryId(assetEntryId);
		assetEntryAssetDisplayPageRelImpl.setAssetDisplayPageId(assetDisplayPageId);

		assetEntryAssetDisplayPageRelImpl.resetOriginalValues();

		return assetEntryAssetDisplayPageRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		assetEntryAssetDisplayPageId = objectInput.readLong();

		assetEntryId = objectInput.readLong();

		assetDisplayPageId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(assetEntryAssetDisplayPageId);

		objectOutput.writeLong(assetEntryId);

		objectOutput.writeLong(assetDisplayPageId);
	}

	public long assetEntryAssetDisplayPageId;
	public long assetEntryId;
	public long assetDisplayPageId;
}