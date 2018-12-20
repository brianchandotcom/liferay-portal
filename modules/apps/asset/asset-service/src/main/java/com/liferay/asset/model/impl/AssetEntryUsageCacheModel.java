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

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.model.AssetEntryUsage;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetEntryUsage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryUsage
 * @generated
 */
@ProviderType
public class AssetEntryUsageCacheModel implements CacheModel<AssetEntryUsage>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryUsageCacheModel)) {
			return false;
		}

		AssetEntryUsageCacheModel assetEntryUsageCacheModel = (AssetEntryUsageCacheModel)obj;

		if (assetEntryUsageId == assetEntryUsageCacheModel.assetEntryUsageId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, assetEntryUsageId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", assetEntryUsageId=");
		sb.append(assetEntryUsageId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetEntryUsage toEntityModel() {
		AssetEntryUsageImpl assetEntryUsageImpl = new AssetEntryUsageImpl();

		if (uuid == null) {
			assetEntryUsageImpl.setUuid("");
		}
		else {
			assetEntryUsageImpl.setUuid(uuid);
		}

		assetEntryUsageImpl.setAssetEntryUsageId(assetEntryUsageId);
		assetEntryUsageImpl.setGroupId(groupId);
		assetEntryUsageImpl.setCompanyId(companyId);
		assetEntryUsageImpl.setUserId(userId);

		if (userName == null) {
			assetEntryUsageImpl.setUserName("");
		}
		else {
			assetEntryUsageImpl.setUserName(userName);
		}

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
		assetEntryUsageImpl.setClassNameId(classNameId);
		assetEntryUsageImpl.setClassPK(classPK);

		if (portletId == null) {
			assetEntryUsageImpl.setPortletId("");
		}
		else {
			assetEntryUsageImpl.setPortletId(portletId);
		}

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
		uuid = objectInput.readUTF();

		assetEntryUsageId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		assetEntryId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		portletId = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(assetEntryUsageId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (portletId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long assetEntryUsageId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long assetEntryId;
	public long classNameId;
	public long classPK;
	public String portletId;
	public long lastPublishDate;
}