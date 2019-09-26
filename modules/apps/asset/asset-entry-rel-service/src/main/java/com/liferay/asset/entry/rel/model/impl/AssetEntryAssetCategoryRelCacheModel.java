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

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetEntryAssetCategoryRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetEntryAssetCategoryRelCacheModel
	implements CacheModel<AssetEntryAssetCategoryRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryAssetCategoryRelCacheModel)) {
			return false;
		}

		AssetEntryAssetCategoryRelCacheModel
			assetEntryAssetCategoryRelCacheModel =
				(AssetEntryAssetCategoryRelCacheModel)obj;

		if ((assetEntryAssetCategoryRelId ==
				assetEntryAssetCategoryRelCacheModel.
					assetEntryAssetCategoryRelId) &&
			(mvccVersion == assetEntryAssetCategoryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetEntryAssetCategoryRelId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", assetEntryAssetCategoryRelId=");
		sb.append(assetEntryAssetCategoryRelId);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", assetCategoryId=");
		sb.append(assetCategoryId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetEntryAssetCategoryRel toEntityModel() {
		AssetEntryAssetCategoryRelImpl assetEntryAssetCategoryRelImpl =
			new AssetEntryAssetCategoryRelImpl();

		assetEntryAssetCategoryRelImpl.setMvccVersion(mvccVersion);
		assetEntryAssetCategoryRelImpl.setAssetEntryAssetCategoryRelId(
			assetEntryAssetCategoryRelId);
		assetEntryAssetCategoryRelImpl.setAssetEntryId(assetEntryId);
		assetEntryAssetCategoryRelImpl.setAssetCategoryId(assetCategoryId);
		assetEntryAssetCategoryRelImpl.setPriority(priority);

		assetEntryAssetCategoryRelImpl.resetOriginalValues();

		return assetEntryAssetCategoryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		assetEntryAssetCategoryRelId = objectInput.readLong();

		assetEntryId = objectInput.readLong();

		assetCategoryId = objectInput.readLong();

		priority = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(assetEntryAssetCategoryRelId);

		objectOutput.writeLong(assetEntryId);

		objectOutput.writeLong(assetCategoryId);

		objectOutput.writeInt(priority);
	}

	public long mvccVersion;
	public long assetEntryAssetCategoryRelId;
	public long assetEntryId;
	public long assetCategoryId;
	public int priority;

}