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

import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DDLRecordSetVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetVersion
 * @generated
 */
@ProviderType
public class DDLRecordSetVersionCacheModel implements CacheModel<DDLRecordSetVersion>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDLRecordSetVersionCacheModel)) {
			return false;
		}

		DDLRecordSetVersionCacheModel ddlRecordSetVersionCacheModel = (DDLRecordSetVersionCacheModel)obj;

		if (recordSetVersionId == ddlRecordSetVersionCacheModel.recordSetVersionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, recordSetVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{recordSetVersionId=");
		sb.append(recordSetVersionId);
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
		sb.append(", recordSetId=");
		sb.append(recordSetId);
		sb.append(", DDMStructureVersionId=");
		sb.append(DDMStructureVersionId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", version=");
		sb.append(version);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDLRecordSetVersion toEntityModel() {
		DDLRecordSetVersionImpl ddlRecordSetVersionImpl = new DDLRecordSetVersionImpl();

		ddlRecordSetVersionImpl.setRecordSetVersionId(recordSetVersionId);
		ddlRecordSetVersionImpl.setGroupId(groupId);
		ddlRecordSetVersionImpl.setCompanyId(companyId);
		ddlRecordSetVersionImpl.setUserId(userId);

		if (userName == null) {
			ddlRecordSetVersionImpl.setUserName(StringPool.BLANK);
		}
		else {
			ddlRecordSetVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddlRecordSetVersionImpl.setCreateDate(null);
		}
		else {
			ddlRecordSetVersionImpl.setCreateDate(new Date(createDate));
		}

		ddlRecordSetVersionImpl.setRecordSetId(recordSetId);
		ddlRecordSetVersionImpl.setDDMStructureVersionId(DDMStructureVersionId);

		if (name == null) {
			ddlRecordSetVersionImpl.setName(StringPool.BLANK);
		}
		else {
			ddlRecordSetVersionImpl.setName(name);
		}

		if (description == null) {
			ddlRecordSetVersionImpl.setDescription(StringPool.BLANK);
		}
		else {
			ddlRecordSetVersionImpl.setDescription(description);
		}

		if (version == null) {
			ddlRecordSetVersionImpl.setVersion(StringPool.BLANK);
		}
		else {
			ddlRecordSetVersionImpl.setVersion(version);
		}

		ddlRecordSetVersionImpl.setStatus(status);
		ddlRecordSetVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			ddlRecordSetVersionImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			ddlRecordSetVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			ddlRecordSetVersionImpl.setStatusDate(null);
		}
		else {
			ddlRecordSetVersionImpl.setStatusDate(new Date(statusDate));
		}

		ddlRecordSetVersionImpl.resetOriginalValues();

		return ddlRecordSetVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		recordSetVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();

		recordSetId = objectInput.readLong();

		DDMStructureVersionId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		version = objectInput.readUTF();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(recordSetVersionId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);

		objectOutput.writeLong(recordSetId);

		objectOutput.writeLong(DDMStructureVersionId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (version == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long recordSetVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long recordSetId;
	public long DDMStructureVersionId;
	public String name;
	public String description;
	public String version;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}