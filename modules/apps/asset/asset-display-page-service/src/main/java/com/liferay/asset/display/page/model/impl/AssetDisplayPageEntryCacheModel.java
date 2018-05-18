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

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetDisplayPageEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageEntry
 * @generated
 */
@ProviderType
public class AssetDisplayPageEntryCacheModel implements CacheModel<AssetDisplayPageEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetDisplayPageEntryCacheModel)) {
			return false;
		}

		AssetDisplayPageEntryCacheModel assetDisplayPageEntryCacheModel = (AssetDisplayPageEntryCacheModel)obj;

		if (assetDisplayPageEntryId == assetDisplayPageEntryCacheModel.assetDisplayPageEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, assetDisplayPageEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{assetDisplayPageEntryId=");
		sb.append(assetDisplayPageEntryId);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", layoutPageTemplateEntryId=");
		sb.append(layoutPageTemplateEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetDisplayPageEntry toEntityModel() {
		AssetDisplayPageEntryImpl assetDisplayPageEntryImpl = new AssetDisplayPageEntryImpl();

		assetDisplayPageEntryImpl.setAssetDisplayPageEntryId(assetDisplayPageEntryId);
		assetDisplayPageEntryImpl.setAssetEntryId(assetEntryId);
		assetDisplayPageEntryImpl.setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);

		assetDisplayPageEntryImpl.resetOriginalValues();

		return assetDisplayPageEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		assetDisplayPageEntryId = objectInput.readLong();

		assetEntryId = objectInput.readLong();

		layoutPageTemplateEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(assetDisplayPageEntryId);

		objectOutput.writeLong(assetEntryId);

		objectOutput.writeLong(layoutPageTemplateEntryId);
	}

	public long assetDisplayPageEntryId;
	public long assetEntryId;
	public long layoutPageTemplateEntryId;
}