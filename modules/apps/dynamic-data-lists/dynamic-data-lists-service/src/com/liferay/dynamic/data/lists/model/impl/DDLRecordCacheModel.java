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

package com.liferay.dynamic.data.lists.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.model.DDLRecord;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DDLRecord in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecord
 * @generated
 */
@ProviderType
public class DDLRecordCacheModel implements CacheModel<DDLRecord>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDLRecordCacheModel)) {
			return false;
		}

		DDLRecordCacheModel ddlRecordCacheModel = (DDLRecordCacheModel)obj;

		if (recordId == ddlRecordCacheModel.recordId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, recordId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", recordId=");
		sb.append(recordId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", versionUserId=");
		sb.append(versionUserId);
		sb.append(", versionUserName=");
		sb.append(versionUserName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", DDMStorageId=");
		sb.append(DDMStorageId);
		sb.append(", recordSetId=");
		sb.append(recordSetId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", displayIndex=");
		sb.append(displayIndex);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDLRecord toEntityModel() {
		DDLRecordImpl ddlRecordImpl = new DDLRecordImpl();

		if (uuid == null) {
			ddlRecordImpl.setUuid(StringPool.BLANK);
		}
		else {
			ddlRecordImpl.setUuid(uuid);
		}

		ddlRecordImpl.setRecordId(recordId);
		ddlRecordImpl.setGroupId(groupId);
		ddlRecordImpl.setUserId(userId);

		if (userName == null) {
			ddlRecordImpl.setUserName(StringPool.BLANK);
		}
		else {
			ddlRecordImpl.setUserName(userName);
		}

		ddlRecordImpl.setVersionUserId(versionUserId);

		if (versionUserName == null) {
			ddlRecordImpl.setVersionUserName(StringPool.BLANK);
		}
		else {
			ddlRecordImpl.setVersionUserName(versionUserName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddlRecordImpl.setCreateDate(null);
		}
		else {
			ddlRecordImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddlRecordImpl.setModifiedDate(null);
		}
		else {
			ddlRecordImpl.setModifiedDate(new Date(modifiedDate));
		}

		ddlRecordImpl.setDDMStorageId(DDMStorageId);
		ddlRecordImpl.setRecordSetId(recordSetId);

		if (version == null) {
			ddlRecordImpl.setVersion(StringPool.BLANK);
		}
		else {
			ddlRecordImpl.setVersion(version);
		}

		ddlRecordImpl.setDisplayIndex(displayIndex);

		if (lastPublishDate == Long.MIN_VALUE) {
			ddlRecordImpl.setLastPublishDate(null);
		}
		else {
			ddlRecordImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		ddlRecordImpl.setCompanyId(companyId);

		ddlRecordImpl.resetOriginalValues();

		return ddlRecordImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		recordId = objectInput.readLong();
		groupId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		versionUserId = objectInput.readLong();
		versionUserName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		DDMStorageId = objectInput.readLong();
		recordSetId = objectInput.readLong();
		version = objectInput.readUTF();
		displayIndex = objectInput.readInt();
		lastPublishDate = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(recordId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(versionUserId);

		if (versionUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(versionUserName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(DDMStorageId);
		objectOutput.writeLong(recordSetId);

		if (version == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeInt(displayIndex);
		objectOutput.writeLong(lastPublishDate);
		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public long recordId;
	public long groupId;
	public long userId;
	public String userName;
	public long versionUserId;
	public String versionUserName;
	public long createDate;
	public long modifiedDate;
	public long DDMStorageId;
	public long recordSetId;
	public String version;
	public int displayIndex;
	public long lastPublishDate;
	public long companyId;
}