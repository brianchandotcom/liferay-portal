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

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.list.model.AssetEntryAssetListEntryRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetEntryAssetListEntryRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AssetEntryAssetListEntryRelCacheModel
	implements CacheModel<AssetEntryAssetListEntryRel>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryAssetListEntryRelCacheModel)) {
			return false;
		}

		AssetEntryAssetListEntryRelCacheModel
			assetEntryAssetListEntryRelCacheModel =
				(AssetEntryAssetListEntryRelCacheModel)obj;

		if (assetEntryAssetListEntryRelId ==
				assetEntryAssetListEntryRelCacheModel.
					assetEntryAssetListEntryRelId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, assetEntryAssetListEntryRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", assetEntryAssetListEntryRelId=");
		sb.append(assetEntryAssetListEntryRelId);
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
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", assetListEntryId=");
		sb.append(assetListEntryId);
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
	public AssetEntryAssetListEntryRel toEntityModel() {
		AssetEntryAssetListEntryRelImpl assetEntryAssetListEntryRelImpl =
			new AssetEntryAssetListEntryRelImpl();

		if (uuid == null) {
			assetEntryAssetListEntryRelImpl.setUuid("");
		}
		else {
			assetEntryAssetListEntryRelImpl.setUuid(uuid);
		}

		assetEntryAssetListEntryRelImpl.setAssetEntryAssetListEntryRelId(
			assetEntryAssetListEntryRelId);
		assetEntryAssetListEntryRelImpl.setGroupId(groupId);
		assetEntryAssetListEntryRelImpl.setCompanyId(companyId);
		assetEntryAssetListEntryRelImpl.setUserId(userId);

		if (userName == null) {
			assetEntryAssetListEntryRelImpl.setUserName("");
		}
		else {
			assetEntryAssetListEntryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetEntryAssetListEntryRelImpl.setCreateDate(null);
		}
		else {
			assetEntryAssetListEntryRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetEntryAssetListEntryRelImpl.setModifiedDate(null);
		}
		else {
			assetEntryAssetListEntryRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		assetEntryAssetListEntryRelImpl.setAssetEntryId(assetEntryId);
		assetEntryAssetListEntryRelImpl.setAssetListEntryId(assetListEntryId);
		assetEntryAssetListEntryRelImpl.setSegmentsEntryId(segmentsEntryId);
		assetEntryAssetListEntryRelImpl.setPosition(position);

		if (lastPublishDate == Long.MIN_VALUE) {
			assetEntryAssetListEntryRelImpl.setLastPublishDate(null);
		}
		else {
			assetEntryAssetListEntryRelImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		assetEntryAssetListEntryRelImpl.resetOriginalValues();

		return assetEntryAssetListEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		assetEntryAssetListEntryRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		assetEntryId = objectInput.readLong();

		assetListEntryId = objectInput.readLong();

		segmentsEntryId = objectInput.readLong();

		position = objectInput.readInt();
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

		objectOutput.writeLong(assetEntryAssetListEntryRelId);

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

		objectOutput.writeLong(assetEntryId);

		objectOutput.writeLong(assetListEntryId);

		objectOutput.writeLong(segmentsEntryId);

		objectOutput.writeInt(position);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long assetEntryAssetListEntryRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long assetEntryId;
	public long assetListEntryId;
	public long segmentsEntryId;
	public int position;
	public long lastPublishDate;

}