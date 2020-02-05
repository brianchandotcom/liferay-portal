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

import com.liferay.asset.list.model.AssetListEntryUsage;
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
 * The cache model class for representing AssetListEntryUsage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetListEntryUsageCacheModel
	implements CacheModel<AssetListEntryUsage>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetListEntryUsageCacheModel)) {
			return false;
		}

		AssetListEntryUsageCacheModel assetListEntryUsageCacheModel =
			(AssetListEntryUsageCacheModel)obj;

		if ((assetListEntryUsageId ==
				assetListEntryUsageCacheModel.assetListEntryUsageId) &&
			(mvccVersion == assetListEntryUsageCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetListEntryUsageId);

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
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", assetListEntryUsageId=");
		sb.append(assetListEntryUsageId);
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
	public AssetListEntryUsage toEntityModel() {
		AssetListEntryUsageImpl assetListEntryUsageImpl =
			new AssetListEntryUsageImpl();

		assetListEntryUsageImpl.setMvccVersion(mvccVersion);
		assetListEntryUsageImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			assetListEntryUsageImpl.setUuid("");
		}
		else {
			assetListEntryUsageImpl.setUuid(uuid);
		}

		assetListEntryUsageImpl.setAssetListEntryUsageId(assetListEntryUsageId);
		assetListEntryUsageImpl.setGroupId(groupId);
		assetListEntryUsageImpl.setCompanyId(companyId);
		assetListEntryUsageImpl.setUserId(userId);

		if (userName == null) {
			assetListEntryUsageImpl.setUserName("");
		}
		else {
			assetListEntryUsageImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetListEntryUsageImpl.setCreateDate(null);
		}
		else {
			assetListEntryUsageImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetListEntryUsageImpl.setModifiedDate(null);
		}
		else {
			assetListEntryUsageImpl.setModifiedDate(new Date(modifiedDate));
		}

		assetListEntryUsageImpl.setAssetListEntryId(assetListEntryId);
		assetListEntryUsageImpl.setClassNameId(classNameId);
		assetListEntryUsageImpl.setClassPK(classPK);

		if (portletId == null) {
			assetListEntryUsageImpl.setPortletId("");
		}
		else {
			assetListEntryUsageImpl.setPortletId(portletId);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			assetListEntryUsageImpl.setLastPublishDate(null);
		}
		else {
			assetListEntryUsageImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		assetListEntryUsageImpl.resetOriginalValues();

		return assetListEntryUsageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		assetListEntryUsageId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		assetListEntryId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		portletId = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(assetListEntryUsageId);

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

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long assetListEntryUsageId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long assetListEntryId;
	public long classNameId;
	public long classPK;
	public String portletId;
	public long lastPublishDate;

}