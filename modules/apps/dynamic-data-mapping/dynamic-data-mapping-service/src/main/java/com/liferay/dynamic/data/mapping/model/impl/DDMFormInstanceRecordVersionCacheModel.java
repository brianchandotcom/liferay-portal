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

package com.liferay.dynamic.data.mapping.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DDMFormInstanceRecordVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordVersion
 * @generated
 */
@ProviderType
public class DDMFormInstanceRecordVersionCacheModel implements CacheModel<DDMFormInstanceRecordVersion>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormInstanceRecordVersionCacheModel)) {
			return false;
		}

		DDMFormInstanceRecordVersionCacheModel ddmFormInstanceRecordVersionCacheModel =
			(DDMFormInstanceRecordVersionCacheModel)obj;

		if (formInstanceRecordVersionId == ddmFormInstanceRecordVersionCacheModel.formInstanceRecordVersionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, formInstanceRecordVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{formInstanceRecordVersionId=");
		sb.append(formInstanceRecordVersionId);
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
		sb.append(", formInstanceId=");
		sb.append(formInstanceId);
		sb.append(", formInstanceVersion=");
		sb.append(formInstanceVersion);
		sb.append(", formInstanceRecordId=");
		sb.append(formInstanceRecordId);
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
		sb.append(", storageId=");
		sb.append(storageId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMFormInstanceRecordVersion toEntityModel() {
		DDMFormInstanceRecordVersionImpl ddmFormInstanceRecordVersionImpl = new DDMFormInstanceRecordVersionImpl();

		ddmFormInstanceRecordVersionImpl.setFormInstanceRecordVersionId(formInstanceRecordVersionId);
		ddmFormInstanceRecordVersionImpl.setGroupId(groupId);
		ddmFormInstanceRecordVersionImpl.setCompanyId(companyId);
		ddmFormInstanceRecordVersionImpl.setUserId(userId);

		if (userName == null) {
			ddmFormInstanceRecordVersionImpl.setUserName("");
		}
		else {
			ddmFormInstanceRecordVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmFormInstanceRecordVersionImpl.setCreateDate(null);
		}
		else {
			ddmFormInstanceRecordVersionImpl.setCreateDate(new Date(createDate));
		}

		ddmFormInstanceRecordVersionImpl.setFormInstanceId(formInstanceId);

		if (formInstanceVersion == null) {
			ddmFormInstanceRecordVersionImpl.setFormInstanceVersion("");
		}
		else {
			ddmFormInstanceRecordVersionImpl.setFormInstanceVersion(formInstanceVersion);
		}

		ddmFormInstanceRecordVersionImpl.setFormInstanceRecordId(formInstanceRecordId);

		if (version == null) {
			ddmFormInstanceRecordVersionImpl.setVersion("");
		}
		else {
			ddmFormInstanceRecordVersionImpl.setVersion(version);
		}

		ddmFormInstanceRecordVersionImpl.setStatus(status);
		ddmFormInstanceRecordVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			ddmFormInstanceRecordVersionImpl.setStatusByUserName("");
		}
		else {
			ddmFormInstanceRecordVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			ddmFormInstanceRecordVersionImpl.setStatusDate(null);
		}
		else {
			ddmFormInstanceRecordVersionImpl.setStatusDate(new Date(statusDate));
		}

		ddmFormInstanceRecordVersionImpl.setStorageId(storageId);

		ddmFormInstanceRecordVersionImpl.resetOriginalValues();

		return ddmFormInstanceRecordVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		formInstanceRecordVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();

		formInstanceId = objectInput.readLong();
		formInstanceVersion = objectInput.readUTF();

		formInstanceRecordId = objectInput.readLong();
		version = objectInput.readUTF();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();

		storageId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(formInstanceRecordVersionId);

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

		objectOutput.writeLong(formInstanceId);

		if (formInstanceVersion == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(formInstanceVersion);
		}

		objectOutput.writeLong(formInstanceRecordId);

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);

		objectOutput.writeLong(storageId);
	}

	public long formInstanceRecordVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long formInstanceId;
	public String formInstanceVersion;
	public long formInstanceRecordId;
	public String version;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public long storageId;
}