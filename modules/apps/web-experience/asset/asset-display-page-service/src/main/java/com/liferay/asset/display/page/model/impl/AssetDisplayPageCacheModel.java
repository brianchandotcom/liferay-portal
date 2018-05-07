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

package com.liferay.asset.display.page.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.display.page.model.AssetDisplayPage;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetDisplayPage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPage
 * @generated
 */
@ProviderType
public class AssetDisplayPageCacheModel implements CacheModel<AssetDisplayPage>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetDisplayPageCacheModel)) {
			return false;
		}

		AssetDisplayPageCacheModel assetDisplayPageCacheModel = (AssetDisplayPageCacheModel)obj;

		if (assetDisplayPageId == assetDisplayPageCacheModel.assetDisplayPageId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, assetDisplayPageId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{assetDisplayPageId=");
		sb.append(assetDisplayPageId);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", layoutId=");
		sb.append(layoutId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetDisplayPage toEntityModel() {
		AssetDisplayPageImpl assetDisplayPageImpl = new AssetDisplayPageImpl();

		assetDisplayPageImpl.setAssetDisplayPageId(assetDisplayPageId);
		assetDisplayPageImpl.setAssetEntryId(assetEntryId);
		assetDisplayPageImpl.setLayoutId(layoutId);

		assetDisplayPageImpl.resetOriginalValues();

		return assetDisplayPageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		assetDisplayPageId = objectInput.readLong();

		assetEntryId = objectInput.readLong();

		layoutId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(assetDisplayPageId);

		objectOutput.writeLong(assetEntryId);

		objectOutput.writeLong(layoutId);
	}

	public long assetDisplayPageId;
	public long assetEntryId;
	public long layoutId;
}