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

import com.liferay.dynamic.data.mapping.model.DDMStructure;
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
 * The cache model class for representing DDMStructure in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMStructureCacheModel
	implements CacheModel<DDMStructure>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMStructureCacheModel)) {
			return false;
		}

		DDMStructureCacheModel ddmStructureCacheModel =
			(DDMStructureCacheModel)obj;

		if ((structureId == ddmStructureCacheModel.structureId) &&
			(mvccVersion == ddmStructureCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, structureId);

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
		StringBundler sb = new StringBundler(43);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", structureId=");
		sb.append(structureId);
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
		sb.append(", parentStructureId=");
		sb.append(parentStructureId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", structureKey=");
		sb.append(structureKey);
		sb.append(", version=");
		sb.append(version);
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
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMStructure toEntityModel() {
		DDMStructureImpl ddmStructureImpl = new DDMStructureImpl();

		ddmStructureImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			ddmStructureImpl.setUuid("");
		}
		else {
			ddmStructureImpl.setUuid(uuid);
		}

		ddmStructureImpl.setStructureId(structureId);
		ddmStructureImpl.setGroupId(groupId);
		ddmStructureImpl.setCompanyId(companyId);
		ddmStructureImpl.setUserId(userId);

		if (userName == null) {
			ddmStructureImpl.setUserName("");
		}
		else {
			ddmStructureImpl.setUserName(userName);
		}

		ddmStructureImpl.setVersionUserId(versionUserId);

		if (versionUserName == null) {
			ddmStructureImpl.setVersionUserName("");
		}
		else {
			ddmStructureImpl.setVersionUserName(versionUserName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmStructureImpl.setCreateDate(null);
		}
		else {
			ddmStructureImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddmStructureImpl.setModifiedDate(null);
		}
		else {
			ddmStructureImpl.setModifiedDate(new Date(modifiedDate));
		}

		ddmStructureImpl.setParentStructureId(parentStructureId);
		ddmStructureImpl.setClassNameId(classNameId);

		if (structureKey == null) {
			ddmStructureImpl.setStructureKey("");
		}
		else {
			ddmStructureImpl.setStructureKey(structureKey);
		}

		if (version == null) {
			ddmStructureImpl.setVersion("");
		}
		else {
			ddmStructureImpl.setVersion(version);
		}

		if (name == null) {
			ddmStructureImpl.setName("");
		}
		else {
			ddmStructureImpl.setName(name);
		}

		if (description == null) {
			ddmStructureImpl.setDescription("");
		}
		else {
			ddmStructureImpl.setDescription(description);
		}

		if (definition == null) {
			ddmStructureImpl.setDefinition("");
		}
		else {
			ddmStructureImpl.setDefinition(definition);
		}

		if (storageType == null) {
			ddmStructureImpl.setStorageType("");
		}
		else {
			ddmStructureImpl.setStorageType(storageType);
		}

		ddmStructureImpl.setType(type);

		if (lastPublishDate == Long.MIN_VALUE) {
			ddmStructureImpl.setLastPublishDate(null);
		}
		else {
			ddmStructureImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		ddmStructureImpl.resetOriginalValues();

		ddmStructureImpl.setClassName(_className);

		ddmStructureImpl.setDDMForm(_ddmForm);

		return ddmStructureImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		structureId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		versionUserId = objectInput.readLong();
		versionUserName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		parentStructureId = objectInput.readLong();

		classNameId = objectInput.readLong();
		structureKey = objectInput.readUTF();
		version = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		definition = objectInput.readUTF();
		storageType = objectInput.readUTF();

		type = objectInput.readInt();
		lastPublishDate = objectInput.readLong();

		_className = (String)objectInput.readObject();
		_ddmForm =
			(com.liferay.dynamic.data.mapping.model.DDMForm)
				objectInput.readObject();
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

		objectOutput.writeLong(structureId);

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

		objectOutput.writeLong(parentStructureId);

		objectOutput.writeLong(classNameId);

		if (structureKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(structureKey);
		}

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

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
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeObject(_className);
		objectOutput.writeObject(_ddmForm);
	}

	public long mvccVersion;
	public String uuid;
	public long structureId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long versionUserId;
	public String versionUserName;
	public long createDate;
	public long modifiedDate;
	public long parentStructureId;
	public long classNameId;
	public String structureKey;
	public String version;
	public String name;
	public String description;
	public String definition;
	public String storageType;
	public int type;
	public long lastPublishDate;
	public String _className;
	public com.liferay.dynamic.data.mapping.model.DDMForm _ddmForm;

}