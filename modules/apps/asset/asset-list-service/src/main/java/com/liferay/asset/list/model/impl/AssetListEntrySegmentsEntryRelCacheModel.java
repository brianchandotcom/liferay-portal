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

import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
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
 * The cache model class for representing AssetListEntrySegmentsEntryRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetListEntrySegmentsEntryRelCacheModel
	implements CacheModel<AssetListEntrySegmentsEntryRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetListEntrySegmentsEntryRelCacheModel)) {
			return false;
		}

		AssetListEntrySegmentsEntryRelCacheModel
			assetListEntrySegmentsEntryRelCacheModel =
				(AssetListEntrySegmentsEntryRelCacheModel)obj;

		if ((assetListEntrySegmentsEntryRelId ==
				assetListEntrySegmentsEntryRelCacheModel.
					assetListEntrySegmentsEntryRelId) &&
			(mvccVersion ==
				assetListEntrySegmentsEntryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetListEntrySegmentsEntryRelId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", assetListEntrySegmentsEntryRelId=");
		sb.append(assetListEntrySegmentsEntryRelId);
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
		sb.append(", segmentsEntryId=");
		sb.append(segmentsEntryId);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetListEntrySegmentsEntryRel toEntityModel() {
		AssetListEntrySegmentsEntryRelImpl assetListEntrySegmentsEntryRelImpl =
			new AssetListEntrySegmentsEntryRelImpl();

		assetListEntrySegmentsEntryRelImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			assetListEntrySegmentsEntryRelImpl.setUuid("");
		}
		else {
			assetListEntrySegmentsEntryRelImpl.setUuid(uuid);
		}

		assetListEntrySegmentsEntryRelImpl.setAssetListEntrySegmentsEntryRelId(
			assetListEntrySegmentsEntryRelId);
		assetListEntrySegmentsEntryRelImpl.setGroupId(groupId);
		assetListEntrySegmentsEntryRelImpl.setCompanyId(companyId);
		assetListEntrySegmentsEntryRelImpl.setUserId(userId);

		if (userName == null) {
			assetListEntrySegmentsEntryRelImpl.setUserName("");
		}
		else {
			assetListEntrySegmentsEntryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetListEntrySegmentsEntryRelImpl.setCreateDate(null);
		}
		else {
			assetListEntrySegmentsEntryRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetListEntrySegmentsEntryRelImpl.setModifiedDate(null);
		}
		else {
			assetListEntrySegmentsEntryRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		assetListEntrySegmentsEntryRelImpl.setAssetListEntryId(
			assetListEntryId);
		assetListEntrySegmentsEntryRelImpl.setSegmentsEntryId(segmentsEntryId);

		if (typeSettings == null) {
			assetListEntrySegmentsEntryRelImpl.setTypeSettings("");
		}
		else {
			assetListEntrySegmentsEntryRelImpl.setTypeSettings(typeSettings);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			assetListEntrySegmentsEntryRelImpl.setLastPublishDate(null);
		}
		else {
			assetListEntrySegmentsEntryRelImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		assetListEntrySegmentsEntryRelImpl.resetOriginalValues();

		return assetListEntrySegmentsEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		assetListEntrySegmentsEntryRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		assetListEntryId = objectInput.readLong();

		segmentsEntryId = objectInput.readLong();
		typeSettings = objectInput.readUTF();
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

		objectOutput.writeLong(assetListEntrySegmentsEntryRelId);

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

		objectOutput.writeLong(segmentsEntryId);

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long assetListEntrySegmentsEntryRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long assetListEntryId;
	public long segmentsEntryId;
	public String typeSettings;
	public long lastPublishDate;

}