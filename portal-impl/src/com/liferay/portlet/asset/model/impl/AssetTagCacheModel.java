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

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetTag in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetTagCacheModel
	implements CacheModel<AssetTag>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetTagCacheModel)) {
			return false;
		}

		AssetTagCacheModel assetTagCacheModel = (AssetTagCacheModel)obj;

		if (tagId == assetTagCacheModel.tagId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, tagId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", tagId=");
		sb.append(tagId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", assetCount=");
		sb.append(assetCount);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetTag toEntityModel() {
		AssetTagImpl assetTagImpl = new AssetTagImpl();

		if (uuid == null) {
			assetTagImpl.setUuid("");
		}
		else {
			assetTagImpl.setUuid(uuid);
		}

		assetTagImpl.setTagId(tagId);
		assetTagImpl.setGroupId(groupId);
		assetTagImpl.setCompanyId(companyId);
		assetTagImpl.setUserId(userId);

		if (userName == null) {
			assetTagImpl.setUserName("");
		}
		else {
			assetTagImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetTagImpl.setCreateDate(null);
		}
		else {
			assetTagImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetTagImpl.setModifiedDate(null);
		}
		else {
			assetTagImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			assetTagImpl.setName("");
		}
		else {
			assetTagImpl.setName(name);
		}

		assetTagImpl.setAssetCount(assetCount);

		if (lastPublishDate == Long.MIN_VALUE) {
			assetTagImpl.setLastPublishDate(null);
		}
		else {
			assetTagImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		assetTagImpl.resetOriginalValues();

		return assetTagImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		tagId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		assetCount = objectInput.readInt();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(tagId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(assetCount);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long tagId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public int assetCount;
	public long lastPublishDate;

}