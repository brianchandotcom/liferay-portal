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

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
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
 * The cache model class for representing DDMFormInstanceRecordVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMFormInstanceRecordVersionCacheModel
	implements CacheModel<DDMFormInstanceRecordVersion>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormInstanceRecordVersionCacheModel)) {
			return false;
		}

		DDMFormInstanceRecordVersionCacheModel
			ddmFormInstanceRecordVersionCacheModel =
				(DDMFormInstanceRecordVersionCacheModel)obj;

		if ((formInstanceRecordVersionId ==
				ddmFormInstanceRecordVersionCacheModel.
					formInstanceRecordVersionId) &&
			(mvccVersion ==
				ddmFormInstanceRecordVersionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, formInstanceRecordVersionId);

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
		sb.append(", formInstanceRecordVersionId=");
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
		sb.append(", storageId=");
		sb.append(storageId);
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
	public DDMFormInstanceRecordVersion toEntityModel() {
		DDMFormInstanceRecordVersionImpl ddmFormInstanceRecordVersionImpl =
			new DDMFormInstanceRecordVersionImpl();

		ddmFormInstanceRecordVersionImpl.setMvccVersion(mvccVersion);
		ddmFormInstanceRecordVersionImpl.setFormInstanceRecordVersionId(
			formInstanceRecordVersionId);
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
			ddmFormInstanceRecordVersionImpl.setCreateDate(
				new Date(createDate));
		}

		ddmFormInstanceRecordVersionImpl.setFormInstanceId(formInstanceId);

		if (formInstanceVersion == null) {
			ddmFormInstanceRecordVersionImpl.setFormInstanceVersion("");
		}
		else {
			ddmFormInstanceRecordVersionImpl.setFormInstanceVersion(
				formInstanceVersion);
		}

		ddmFormInstanceRecordVersionImpl.setFormInstanceRecordId(
			formInstanceRecordId);

		if (version == null) {
			ddmFormInstanceRecordVersionImpl.setVersion("");
		}
		else {
			ddmFormInstanceRecordVersionImpl.setVersion(version);
		}

		ddmFormInstanceRecordVersionImpl.setStorageId(storageId);
		ddmFormInstanceRecordVersionImpl.setStatus(status);
		ddmFormInstanceRecordVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			ddmFormInstanceRecordVersionImpl.setStatusByUserName("");
		}
		else {
			ddmFormInstanceRecordVersionImpl.setStatusByUserName(
				statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			ddmFormInstanceRecordVersionImpl.setStatusDate(null);
		}
		else {
			ddmFormInstanceRecordVersionImpl.setStatusDate(
				new Date(statusDate));
		}

		ddmFormInstanceRecordVersionImpl.resetOriginalValues();

		return ddmFormInstanceRecordVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

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

		storageId = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

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

		objectOutput.writeLong(storageId);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long mvccVersion;
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
	public long storageId;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}