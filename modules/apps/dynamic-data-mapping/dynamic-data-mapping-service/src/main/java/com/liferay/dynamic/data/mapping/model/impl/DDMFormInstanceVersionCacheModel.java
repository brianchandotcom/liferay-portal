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

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
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
 * The cache model class for representing DDMFormInstanceVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMFormInstanceVersionCacheModel
	implements CacheModel<DDMFormInstanceVersion>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormInstanceVersionCacheModel)) {
			return false;
		}

		DDMFormInstanceVersionCacheModel ddmFormInstanceVersionCacheModel =
			(DDMFormInstanceVersionCacheModel)obj;

		if ((formInstanceVersionId ==
				ddmFormInstanceVersionCacheModel.formInstanceVersionId) &&
			(mvccVersion == ddmFormInstanceVersionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, formInstanceVersionId);

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
		StringBundler sb = new StringBundler(35);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", formInstanceVersionId=");
		sb.append(formInstanceVersionId);
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
		sb.append(", structureVersionId=");
		sb.append(structureVersionId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", settings=");
		sb.append(settings);
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
	public DDMFormInstanceVersion toEntityModel() {
		DDMFormInstanceVersionImpl ddmFormInstanceVersionImpl =
			new DDMFormInstanceVersionImpl();

		ddmFormInstanceVersionImpl.setMvccVersion(mvccVersion);
		ddmFormInstanceVersionImpl.setFormInstanceVersionId(
			formInstanceVersionId);
		ddmFormInstanceVersionImpl.setGroupId(groupId);
		ddmFormInstanceVersionImpl.setCompanyId(companyId);
		ddmFormInstanceVersionImpl.setUserId(userId);

		if (userName == null) {
			ddmFormInstanceVersionImpl.setUserName("");
		}
		else {
			ddmFormInstanceVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmFormInstanceVersionImpl.setCreateDate(null);
		}
		else {
			ddmFormInstanceVersionImpl.setCreateDate(new Date(createDate));
		}

		ddmFormInstanceVersionImpl.setFormInstanceId(formInstanceId);
		ddmFormInstanceVersionImpl.setStructureVersionId(structureVersionId);

		if (name == null) {
			ddmFormInstanceVersionImpl.setName("");
		}
		else {
			ddmFormInstanceVersionImpl.setName(name);
		}

		if (description == null) {
			ddmFormInstanceVersionImpl.setDescription("");
		}
		else {
			ddmFormInstanceVersionImpl.setDescription(description);
		}

		if (settings == null) {
			ddmFormInstanceVersionImpl.setSettings("");
		}
		else {
			ddmFormInstanceVersionImpl.setSettings(settings);
		}

		if (version == null) {
			ddmFormInstanceVersionImpl.setVersion("");
		}
		else {
			ddmFormInstanceVersionImpl.setVersion(version);
		}

		ddmFormInstanceVersionImpl.setStatus(status);
		ddmFormInstanceVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			ddmFormInstanceVersionImpl.setStatusByUserName("");
		}
		else {
			ddmFormInstanceVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			ddmFormInstanceVersionImpl.setStatusDate(null);
		}
		else {
			ddmFormInstanceVersionImpl.setStatusDate(new Date(statusDate));
		}

		ddmFormInstanceVersionImpl.resetOriginalValues();

		return ddmFormInstanceVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		formInstanceVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();

		formInstanceId = objectInput.readLong();

		structureVersionId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		settings = objectInput.readUTF();
		version = objectInput.readUTF();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(formInstanceVersionId);

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

		objectOutput.writeLong(structureVersionId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (settings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(settings);
		}

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
	}

	public long mvccVersion;
	public long formInstanceVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long formInstanceId;
	public long structureVersionId;
	public String name;
	public String description;
	public String settings;
	public String version;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}