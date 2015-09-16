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

package com.liferay.portlet.asset.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.asset.model.AssetTagStats;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetTagStats in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagStats
 * @generated
 */
@ProviderType
public class AssetTagStatsCacheModel implements CacheModel<AssetTagStats>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetTagStatsCacheModel)) {
			return false;
		}

		AssetTagStatsCacheModel assetTagStatsCacheModel = (AssetTagStatsCacheModel)obj;

		if (tagStatsId == assetTagStatsCacheModel.tagStatsId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, tagStatsId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{tagStatsId=");
		sb.append(tagStatsId);
		sb.append(", tagId=");
		sb.append(tagId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", assetCount=");
		sb.append(assetCount);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetTagStats toEntityModel() {
		AssetTagStatsImpl assetTagStatsImpl = new AssetTagStatsImpl();

		assetTagStatsImpl.setTagStatsId(tagStatsId);
		assetTagStatsImpl.setTagId(tagId);
		assetTagStatsImpl.setClassNameId(classNameId);
		assetTagStatsImpl.setAssetCount(assetCount);
		assetTagStatsImpl.setCompanyId(companyId);

		assetTagStatsImpl.resetOriginalValues();

		return assetTagStatsImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		tagStatsId = objectInput.readLong();
		tagId = objectInput.readLong();
		classNameId = objectInput.readLong();
		assetCount = objectInput.readInt();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(tagStatsId);
		objectOutput.writeLong(tagId);
		objectOutput.writeLong(classNameId);
		objectOutput.writeInt(assetCount);
		objectOutput.writeLong(companyId);
	}

	public long tagStatsId;
	public long tagId;
	public long classNameId;
	public int assetCount;
	public long companyId;
}