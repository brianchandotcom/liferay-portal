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

package com.liferay.asset.model.impl;

import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetEntryUsage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetEntryUsageCacheModel
	implements CacheModel<AssetEntryUsage>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryUsageCacheModel)) {
			return false;
		}

		AssetEntryUsageCacheModel assetEntryUsageCacheModel =
			(AssetEntryUsageCacheModel)obj;

		if ((assetEntryUsageId ==
				assetEntryUsageCacheModel.assetEntryUsageId) &&
			(mvccVersion == assetEntryUsageCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetEntryUsageId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", assetEntryUsageId=");
		sb.append(assetEntryUsageId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", containerType=");
		sb.append(containerType);
		sb.append(", containerKey=");
		sb.append(containerKey);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", type=");
		sb.append(type);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetEntryUsage toEntityModel() {
		AssetEntryUsageImpl assetEntryUsageImpl = new AssetEntryUsageImpl();

		assetEntryUsageImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			assetEntryUsageImpl.setUuid("");
		}
		else {
			assetEntryUsageImpl.setUuid(uuid);
		}

		assetEntryUsageImpl.setAssetEntryUsageId(assetEntryUsageId);
		assetEntryUsageImpl.setGroupId(groupId);

		if (createDate == Long.MIN_VALUE) {
			assetEntryUsageImpl.setCreateDate(null);
		}
		else {
			assetEntryUsageImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetEntryUsageImpl.setModifiedDate(null);
		}
		else {
			assetEntryUsageImpl.setModifiedDate(new Date(modifiedDate));
		}

		assetEntryUsageImpl.setAssetEntryId(assetEntryId);
		assetEntryUsageImpl.setContainerType(containerType);

		if (containerKey == null) {
			assetEntryUsageImpl.setContainerKey("");
		}
		else {
			assetEntryUsageImpl.setContainerKey(containerKey);
		}

		assetEntryUsageImpl.setPlid(plid);
		assetEntryUsageImpl.setType(type);

		if (lastPublishDate == Long.MIN_VALUE) {
			assetEntryUsageImpl.setLastPublishDate(null);
		}
		else {
			assetEntryUsageImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		assetEntryUsageImpl.resetOriginalValues();

		return assetEntryUsageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		assetEntryUsageId = objectInput.readLong();

		groupId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		assetEntryId = objectInput.readLong();

		containerType = objectInput.readLong();
		containerKey = objectInput.readUTF();

		plid = objectInput.readLong();

		type = objectInput.readInt();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(assetEntryUsageId);

		objectOutput.writeLong(groupId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(assetEntryId);

		objectOutput.writeLong(containerType);

		if (containerKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(containerKey);
		}

		objectOutput.writeLong(plid);

		objectOutput.writeInt(type);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long assetEntryUsageId;
	public long groupId;
	public long createDate;
	public long modifiedDate;
	public long assetEntryId;
	public long containerType;
	public String containerKey;
	public long plid;
	public int type;
	public long lastPublishDate;

}