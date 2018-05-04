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

import com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetEntryDisplayPageRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryDisplayPageRel
 * @generated
 */
@ProviderType
public class AssetEntryDisplayPageRelCacheModel implements CacheModel<AssetEntryDisplayPageRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryDisplayPageRelCacheModel)) {
			return false;
		}

		AssetEntryDisplayPageRelCacheModel assetEntryDisplayPageRelCacheModel = (AssetEntryDisplayPageRelCacheModel)obj;

		if (assetEntryDisplayPageRelId == assetEntryDisplayPageRelCacheModel.assetEntryDisplayPageRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, assetEntryDisplayPageRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{assetEntryDisplayPageRelId=");
		sb.append(assetEntryDisplayPageRelId);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", displayPageId=");
		sb.append(displayPageId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetEntryDisplayPageRel toEntityModel() {
		AssetEntryDisplayPageRelImpl assetEntryDisplayPageRelImpl = new AssetEntryDisplayPageRelImpl();

		assetEntryDisplayPageRelImpl.setAssetEntryDisplayPageRelId(assetEntryDisplayPageRelId);
		assetEntryDisplayPageRelImpl.setAssetEntryId(assetEntryId);
		assetEntryDisplayPageRelImpl.setDisplayPageId(displayPageId);

		assetEntryDisplayPageRelImpl.resetOriginalValues();

		return assetEntryDisplayPageRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		assetEntryDisplayPageRelId = objectInput.readLong();

		assetEntryId = objectInput.readLong();

		displayPageId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(assetEntryDisplayPageRelId);

		objectOutput.writeLong(assetEntryId);

		objectOutput.writeLong(displayPageId);
	}

	public long assetEntryDisplayPageRelId;
	public long assetEntryId;
	public long displayPageId;
}