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

import com.liferay.dynamic.data.lists.model.DDLRecordVersion;

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
 * The cache model class for representing DDLRecordVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersion
 * @generated
 */
@ProviderType
public class DDLRecordVersionCacheModel implements CacheModel<DDLRecordVersion>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDLRecordVersionCacheModel)) {
			return false;
		}

		DDLRecordVersionCacheModel ddlRecordVersionCacheModel = (DDLRecordVersionCacheModel)obj;

		if (recordVersionId == ddlRecordVersionCacheModel.recordVersionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, recordVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{recordVersionId=");
		sb.append(recordVersionId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", DDMStorageId=");
		sb.append(DDMStorageId);
		sb.append(", recordSetId=");
		sb.append(recordSetId);
		sb.append(", recordId=");
		sb.append(recordId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", displayIndex=");
		sb.append(displayIndex);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDLRecordVersion toEntityModel() {
		DDLRecordVersionImpl ddlRecordVersionImpl = new DDLRecordVersionImpl();

		ddlRecordVersionImpl.setRecordVersionId(recordVersionId);
		ddlRecordVersionImpl.setGroupId(groupId);
		ddlRecordVersionImpl.setUserId(userId);

		if (userName == null) {
			ddlRecordVersionImpl.setUserName(StringPool.BLANK);
		}
		else {
			ddlRecordVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddlRecordVersionImpl.setCreateDate(null);
		}
		else {
			ddlRecordVersionImpl.setCreateDate(new Date(createDate));
		}

		ddlRecordVersionImpl.setDDMStorageId(DDMStorageId);
		ddlRecordVersionImpl.setRecordSetId(recordSetId);
		ddlRecordVersionImpl.setRecordId(recordId);

		if (version == null) {
			ddlRecordVersionImpl.setVersion(StringPool.BLANK);
		}
		else {
			ddlRecordVersionImpl.setVersion(version);
		}

		ddlRecordVersionImpl.setDisplayIndex(displayIndex);
		ddlRecordVersionImpl.setStatus(status);
		ddlRecordVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			ddlRecordVersionImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			ddlRecordVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			ddlRecordVersionImpl.setStatusDate(null);
		}
		else {
			ddlRecordVersionImpl.setStatusDate(new Date(statusDate));
		}

		ddlRecordVersionImpl.setCompanyId(companyId);

		ddlRecordVersionImpl.resetOriginalValues();

		return ddlRecordVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		recordVersionId = objectInput.readLong();
		groupId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		DDMStorageId = objectInput.readLong();
		recordSetId = objectInput.readLong();
		recordId = objectInput.readLong();
		version = objectInput.readUTF();
		displayIndex = objectInput.readInt();
		status = objectInput.readInt();
		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(recordVersionId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(DDMStorageId);
		objectOutput.writeLong(recordSetId);
		objectOutput.writeLong(recordId);

		if (version == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeInt(displayIndex);
		objectOutput.writeInt(status);
		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
		objectOutput.writeLong(companyId);
	}

	public long recordVersionId;
	public long groupId;
	public long userId;
	public String userName;
	public long createDate;
	public long DDMStorageId;
	public long recordSetId;
	public long recordId;
	public String version;
	public int displayIndex;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public long companyId;
}