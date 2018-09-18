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

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetListEntryAssetEntryRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryAssetEntryRel
 * @generated
 */
@ProviderType
public class AssetListEntryAssetEntryRelCacheModel implements CacheModel<AssetListEntryAssetEntryRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetListEntryAssetEntryRelCacheModel)) {
			return false;
		}

		AssetListEntryAssetEntryRelCacheModel assetListEntryAssetEntryRelCacheModel =
			(AssetListEntryAssetEntryRelCacheModel)obj;

		if (assetListEntryAssetEntryRelId == assetListEntryAssetEntryRelCacheModel.assetListEntryAssetEntryRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, assetListEntryAssetEntryRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{assetListEntryAssetEntryRelId=");
		sb.append(assetListEntryAssetEntryRelId);
		sb.append(", assetListEntryId=");
		sb.append(assetListEntryId);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", position=");
		sb.append(position);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetListEntryAssetEntryRel toEntityModel() {
		AssetListEntryAssetEntryRelImpl assetListEntryAssetEntryRelImpl = new AssetListEntryAssetEntryRelImpl();

		assetListEntryAssetEntryRelImpl.setAssetListEntryAssetEntryRelId(assetListEntryAssetEntryRelId);
		assetListEntryAssetEntryRelImpl.setAssetListEntryId(assetListEntryId);
		assetListEntryAssetEntryRelImpl.setAssetEntryId(assetEntryId);
		assetListEntryAssetEntryRelImpl.setPosition(position);

		assetListEntryAssetEntryRelImpl.resetOriginalValues();

		return assetListEntryAssetEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		assetListEntryAssetEntryRelId = objectInput.readLong();

		assetListEntryId = objectInput.readLong();

		assetEntryId = objectInput.readLong();

		position = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(assetListEntryAssetEntryRelId);

		objectOutput.writeLong(assetListEntryId);

		objectOutput.writeLong(assetEntryId);

		objectOutput.writeInt(position);
	}

	public long assetListEntryAssetEntryRelId;
	public long assetListEntryId;
	public long assetEntryId;
	public int position;
}