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

import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
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
 * The cache model class for representing AssetListEntryAssetEntryRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetListEntryAssetEntryRelCacheModel
	implements CacheModel<AssetListEntryAssetEntryRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetListEntryAssetEntryRelCacheModel)) {
			return false;
		}

		AssetListEntryAssetEntryRelCacheModel
			assetListEntryAssetEntryRelCacheModel =
				(AssetListEntryAssetEntryRelCacheModel)obj;

		if ((assetListEntryAssetEntryRelId ==
				assetListEntryAssetEntryRelCacheModel.
					assetListEntryAssetEntryRelId) &&
			(mvccVersion ==
				assetListEntryAssetEntryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetListEntryAssetEntryRelId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", assetListEntryAssetEntryRelId=");
		sb.append(assetListEntryAssetEntryRelId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", assetListEntryId=");
		sb.append(assetListEntryId);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", segmentsEntryId=");
		sb.append(segmentsEntryId);
		sb.append(", position=");
		sb.append(position);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetListEntryAssetEntryRel toEntityModel() {
		AssetListEntryAssetEntryRelImpl assetListEntryAssetEntryRelImpl =
			new AssetListEntryAssetEntryRelImpl();

		assetListEntryAssetEntryRelImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			assetListEntryAssetEntryRelImpl.setUuid("");
		}
		else {
			assetListEntryAssetEntryRelImpl.setUuid(uuid);
		}

		assetListEntryAssetEntryRelImpl.setAssetListEntryAssetEntryRelId(
			assetListEntryAssetEntryRelId);
		assetListEntryAssetEntryRelImpl.setGroupId(groupId);
		assetListEntryAssetEntryRelImpl.setCompanyId(companyId);
		assetListEntryAssetEntryRelImpl.setUserId(userId);

		if (userName == null) {
			assetListEntryAssetEntryRelImpl.setUserName("");
		}
		else {
			assetListEntryAssetEntryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetListEntryAssetEntryRelImpl.setCreateDate(null);
		}
		else {
			assetListEntryAssetEntryRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetListEntryAssetEntryRelImpl.setModifiedDate(null);
		}
		else {
			assetListEntryAssetEntryRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		assetListEntryAssetEntryRelImpl.setAssetListEntryId(assetListEntryId);
		assetListEntryAssetEntryRelImpl.setAssetEntryId(assetEntryId);
		assetListEntryAssetEntryRelImpl.setSegmentsEntryId(segmentsEntryId);
		assetListEntryAssetEntryRelImpl.setPosition(position);

		if (lastPublishDate == Long.MIN_VALUE) {
			assetListEntryAssetEntryRelImpl.setLastPublishDate(null);
		}
		else {
			assetListEntryAssetEntryRelImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		assetListEntryAssetEntryRelImpl.resetOriginalValues();

		return assetListEntryAssetEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		assetListEntryAssetEntryRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		assetListEntryId = objectInput.readLong();

		assetEntryId = objectInput.readLong();

		segmentsEntryId = objectInput.readLong();

		position = objectInput.readInt();
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

		objectOutput.writeLong(assetListEntryAssetEntryRelId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(assetListEntryId);

		objectOutput.writeLong(assetEntryId);

		objectOutput.writeLong(segmentsEntryId);

		objectOutput.writeInt(position);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long assetListEntryAssetEntryRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long assetListEntryId;
	public long assetEntryId;
	public long segmentsEntryId;
	public int position;
	public long lastPublishDate;

}