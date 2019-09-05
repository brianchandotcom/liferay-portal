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

import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
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
 * The cache model class for representing DDMStructureVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMStructureVersionCacheModel
	implements CacheModel<DDMStructureVersion>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMStructureVersionCacheModel)) {
			return false;
		}

		DDMStructureVersionCacheModel ddmStructureVersionCacheModel =
			(DDMStructureVersionCacheModel)obj;

		if ((structureVersionId ==
				ddmStructureVersionCacheModel.structureVersionId) &&
			(mvccVersion == ddmStructureVersionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, structureVersionId);

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
		StringBundler sb = new StringBundler(39);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", structureVersionId=");
		sb.append(structureVersionId);
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
		sb.append(", structureId=");
		sb.append(structureId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", parentStructureId=");
		sb.append(parentStructureId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", definition=");
		sb.append(definition);
		sb.append(", storageType=");
		sb.append(storageType);
		sb.append(", type=");
		sb.append(type);
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
	public DDMStructureVersion toEntityModel() {
		DDMStructureVersionImpl ddmStructureVersionImpl =
			new DDMStructureVersionImpl();

		ddmStructureVersionImpl.setMvccVersion(mvccVersion);
		ddmStructureVersionImpl.setStructureVersionId(structureVersionId);
		ddmStructureVersionImpl.setGroupId(groupId);
		ddmStructureVersionImpl.setCompanyId(companyId);
		ddmStructureVersionImpl.setUserId(userId);

		if (userName == null) {
			ddmStructureVersionImpl.setUserName("");
		}
		else {
			ddmStructureVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmStructureVersionImpl.setCreateDate(null);
		}
		else {
			ddmStructureVersionImpl.setCreateDate(new Date(createDate));
		}

		ddmStructureVersionImpl.setStructureId(structureId);

		if (version == null) {
			ddmStructureVersionImpl.setVersion("");
		}
		else {
			ddmStructureVersionImpl.setVersion(version);
		}

		ddmStructureVersionImpl.setParentStructureId(parentStructureId);

		if (name == null) {
			ddmStructureVersionImpl.setName("");
		}
		else {
			ddmStructureVersionImpl.setName(name);
		}

		if (description == null) {
			ddmStructureVersionImpl.setDescription("");
		}
		else {
			ddmStructureVersionImpl.setDescription(description);
		}

		if (definition == null) {
			ddmStructureVersionImpl.setDefinition("");
		}
		else {
			ddmStructureVersionImpl.setDefinition(definition);
		}

		if (storageType == null) {
			ddmStructureVersionImpl.setStorageType("");
		}
		else {
			ddmStructureVersionImpl.setStorageType(storageType);
		}

		ddmStructureVersionImpl.setType(type);
		ddmStructureVersionImpl.setStatus(status);
		ddmStructureVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			ddmStructureVersionImpl.setStatusByUserName("");
		}
		else {
			ddmStructureVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			ddmStructureVersionImpl.setStatusDate(null);
		}
		else {
			ddmStructureVersionImpl.setStatusDate(new Date(statusDate));
		}

		ddmStructureVersionImpl.resetOriginalValues();

		ddmStructureVersionImpl.setDDMForm(_ddmForm);

		return ddmStructureVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		structureVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();

		structureId = objectInput.readLong();
		version = objectInput.readUTF();

		parentStructureId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		definition = objectInput.readUTF();
		storageType = objectInput.readUTF();

		type = objectInput.readInt();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();

		_ddmForm =
			(com.liferay.dynamic.data.mapping.model.DDMForm)
				objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(structureVersionId);

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

		objectOutput.writeLong(structureId);

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeLong(parentStructureId);

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

		if (definition == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(definition);
		}

		if (storageType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(storageType);
		}

		objectOutput.writeInt(type);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);

		objectOutput.writeObject(_ddmForm);
	}

	public long mvccVersion;
	public long structureVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long structureId;
	public String version;
	public long parentStructureId;
	public String name;
	public String description;
	public String definition;
	public String storageType;
	public int type;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public com.liferay.dynamic.data.mapping.model.DDMForm _ddmForm;

}