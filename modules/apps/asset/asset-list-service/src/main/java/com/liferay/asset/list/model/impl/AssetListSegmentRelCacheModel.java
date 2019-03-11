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

import com.liferay.asset.list.model.AssetListSegmentRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetListSegmentRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AssetListSegmentRelCacheModel
	implements CacheModel<AssetListSegmentRel>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetListSegmentRelCacheModel)) {
			return false;
		}

		AssetListSegmentRelCacheModel assetListSegmentRelCacheModel =
			(AssetListSegmentRelCacheModel)obj;

		if (assetListSegmentRelId ==
				assetListSegmentRelCacheModel.assetListSegmentRelId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, assetListSegmentRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", assetListSegmentRelId=");
		sb.append(assetListSegmentRelId);
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
	public AssetListSegmentRel toEntityModel() {
		AssetListSegmentRelImpl assetListSegmentRelImpl =
			new AssetListSegmentRelImpl();

		if (uuid == null) {
			assetListSegmentRelImpl.setUuid("");
		}
		else {
			assetListSegmentRelImpl.setUuid(uuid);
		}

		assetListSegmentRelImpl.setAssetListSegmentRelId(assetListSegmentRelId);
		assetListSegmentRelImpl.setGroupId(groupId);
		assetListSegmentRelImpl.setCompanyId(companyId);
		assetListSegmentRelImpl.setUserId(userId);

		if (userName == null) {
			assetListSegmentRelImpl.setUserName("");
		}
		else {
			assetListSegmentRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetListSegmentRelImpl.setCreateDate(null);
		}
		else {
			assetListSegmentRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetListSegmentRelImpl.setModifiedDate(null);
		}
		else {
			assetListSegmentRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		assetListSegmentRelImpl.setAssetListEntryId(assetListEntryId);
		assetListSegmentRelImpl.setSegmentsEntryId(segmentsEntryId);

		if (typeSettings == null) {
			assetListSegmentRelImpl.setTypeSettings("");
		}
		else {
			assetListSegmentRelImpl.setTypeSettings(typeSettings);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			assetListSegmentRelImpl.setLastPublishDate(null);
		}
		else {
			assetListSegmentRelImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		assetListSegmentRelImpl.resetOriginalValues();

		return assetListSegmentRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		assetListSegmentRelId = objectInput.readLong();

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
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(assetListSegmentRelId);

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

	public String uuid;
	public long assetListSegmentRelId;
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