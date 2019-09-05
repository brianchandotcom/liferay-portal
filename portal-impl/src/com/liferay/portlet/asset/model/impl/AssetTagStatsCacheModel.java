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

import com.liferay.asset.kernel.model.AssetTagStats;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetTagStats in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Judson (7.1.x), replaced by {@link
 com.liferay.asset.tag.stats.model.impl.AssetTagStatsImpl}
 * @generated
 */
@Deprecated
public class AssetTagStatsCacheModel
	implements CacheModel<AssetTagStats>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetTagStatsCacheModel)) {
			return false;
		}

		AssetTagStatsCacheModel assetTagStatsCacheModel =
			(AssetTagStatsCacheModel)obj;

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
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", tagId=");
		sb.append(tagId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", assetCount=");
		sb.append(assetCount);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetTagStats toEntityModel() {
		AssetTagStatsImpl assetTagStatsImpl = new AssetTagStatsImpl();

		assetTagStatsImpl.setTagStatsId(tagStatsId);
		assetTagStatsImpl.setCompanyId(companyId);
		assetTagStatsImpl.setTagId(tagId);
		assetTagStatsImpl.setClassNameId(classNameId);
		assetTagStatsImpl.setAssetCount(assetCount);

		assetTagStatsImpl.resetOriginalValues();

		return assetTagStatsImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		tagStatsId = objectInput.readLong();

		companyId = objectInput.readLong();

		tagId = objectInput.readLong();

		classNameId = objectInput.readLong();

		assetCount = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(tagStatsId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(tagId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeInt(assetCount);
	}

	public long tagStatsId;
	public long companyId;
	public long tagId;
	public long classNameId;
	public int assetCount;

}