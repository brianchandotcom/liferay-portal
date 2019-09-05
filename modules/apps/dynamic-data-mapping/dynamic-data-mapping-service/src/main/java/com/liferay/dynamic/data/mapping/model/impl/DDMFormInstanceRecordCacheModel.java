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

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
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
 * The cache model class for representing DDMFormInstanceRecord in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMFormInstanceRecordCacheModel
	implements CacheModel<DDMFormInstanceRecord>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormInstanceRecordCacheModel)) {
			return false;
		}

		DDMFormInstanceRecordCacheModel ddmFormInstanceRecordCacheModel =
			(DDMFormInstanceRecordCacheModel)obj;

		if ((formInstanceRecordId ==
				ddmFormInstanceRecordCacheModel.formInstanceRecordId) &&
			(mvccVersion == ddmFormInstanceRecordCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, formInstanceRecordId);

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
		StringBundler sb = new StringBundler(33);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", formInstanceRecordId=");
		sb.append(formInstanceRecordId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
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
		sb.append(", formInstanceId=");
		sb.append(formInstanceId);
		sb.append(", formInstanceVersion=");
		sb.append(formInstanceVersion);
		sb.append(", storageId=");
		sb.append(storageId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMFormInstanceRecord toEntityModel() {
		DDMFormInstanceRecordImpl ddmFormInstanceRecordImpl =
			new DDMFormInstanceRecordImpl();

		ddmFormInstanceRecordImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			ddmFormInstanceRecordImpl.setUuid("");
		}
		else {
			ddmFormInstanceRecordImpl.setUuid(uuid);
		}

		ddmFormInstanceRecordImpl.setFormInstanceRecordId(formInstanceRecordId);
		ddmFormInstanceRecordImpl.setGroupId(groupId);
		ddmFormInstanceRecordImpl.setCompanyId(companyId);
		ddmFormInstanceRecordImpl.setUserId(userId);

		if (userName == null) {
			ddmFormInstanceRecordImpl.setUserName("");
		}
		else {
			ddmFormInstanceRecordImpl.setUserName(userName);
		}

		ddmFormInstanceRecordImpl.setVersionUserId(versionUserId);

		if (versionUserName == null) {
			ddmFormInstanceRecordImpl.setVersionUserName("");
		}
		else {
			ddmFormInstanceRecordImpl.setVersionUserName(versionUserName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmFormInstanceRecordImpl.setCreateDate(null);
		}
		else {
			ddmFormInstanceRecordImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddmFormInstanceRecordImpl.setModifiedDate(null);
		}
		else {
			ddmFormInstanceRecordImpl.setModifiedDate(new Date(modifiedDate));
		}

		ddmFormInstanceRecordImpl.setFormInstanceId(formInstanceId);

		if (formInstanceVersion == null) {
			ddmFormInstanceRecordImpl.setFormInstanceVersion("");
		}
		else {
			ddmFormInstanceRecordImpl.setFormInstanceVersion(
				formInstanceVersion);
		}

		ddmFormInstanceRecordImpl.setStorageId(storageId);

		if (version == null) {
			ddmFormInstanceRecordImpl.setVersion("");
		}
		else {
			ddmFormInstanceRecordImpl.setVersion(version);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			ddmFormInstanceRecordImpl.setLastPublishDate(null);
		}
		else {
			ddmFormInstanceRecordImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		ddmFormInstanceRecordImpl.resetOriginalValues();

		return ddmFormInstanceRecordImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		formInstanceRecordId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		versionUserId = objectInput.readLong();
		versionUserName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		formInstanceId = objectInput.readLong();
		formInstanceVersion = objectInput.readUTF();

		storageId = objectInput.readLong();
		version = objectInput.readUTF();
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

		objectOutput.writeLong(formInstanceRecordId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(versionUserId);

		if (versionUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(versionUserName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(formInstanceId);

		if (formInstanceVersion == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(formInstanceVersion);
		}

		objectOutput.writeLong(storageId);

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long formInstanceRecordId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long versionUserId;
	public String versionUserName;
	public long createDate;
	public long modifiedDate;
	public long formInstanceId;
	public String formInstanceVersion;
	public long storageId;
	public String version;
	public long lastPublishDate;

}