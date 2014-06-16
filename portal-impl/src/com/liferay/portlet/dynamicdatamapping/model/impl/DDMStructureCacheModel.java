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

package com.liferay.portlet.dynamicdatamapping.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DDMStructure in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructure
 * @generated
 */
public class DDMStructureCacheModel implements CacheModel<DDMStructure>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{uuid=");
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", xsd=");
		sb.append(xsd);
		sb.append(", storageType=");
		sb.append(storageType);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMStructure toEntityModel() {
		DDMStructureImpl ddmStructureImpl = new DDMStructureImpl();

		if (uuid == null) {
			ddmStructureImpl.setUuid(StringPool.BLANK);
		}
		else {
			ddmStructureImpl.setUuid(uuid);
		}

		ddmStructureImpl.setStructureId(structureId);
		ddmStructureImpl.setGroupId(groupId);
		ddmStructureImpl.setCompanyId(companyId);
		ddmStructureImpl.setUserId(userId);

		if (userName == null) {
			ddmStructureImpl.setUserName(StringPool.BLANK);
		}
		else {
			ddmStructureImpl.setUserName(userName);
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
			ddmStructureImpl.setStructureKey(StringPool.BLANK);
		}
		else {
			ddmStructureImpl.setStructureKey(structureKey);
		}

		if (name == null) {
			ddmStructureImpl.setName(StringPool.BLANK);
		}
		else {
			ddmStructureImpl.setName(name);
		}

		if (description == null) {
			ddmStructureImpl.setDescription(StringPool.BLANK);
		}
		else {
			ddmStructureImpl.setDescription(description);
		}

		if (xsd == null) {
			ddmStructureImpl.setXsd(StringPool.BLANK);
		}
		else {
			ddmStructureImpl.setXsd(xsd);
		}

		if (storageType == null) {
			ddmStructureImpl.setStorageType(StringPool.BLANK);
		}
		else {
			ddmStructureImpl.setStorageType(storageType);
		}

		ddmStructureImpl.setType(type);

		ddmStructureImpl.resetOriginalValues();

		ddmStructureImpl.setDDMForm(_DDMForm);

		ddmStructureImpl.setLocalizedFieldsMap(_localizedFieldsMap);

		ddmStructureImpl.setLocalizedPersistentFieldsMap(_localizedPersistentFieldsMap);

		ddmStructureImpl.setLocalizedTransientFieldsMap(_localizedTransientFieldsMap);

		return ddmStructureImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		uuid = objectInput.readUTF();
		structureId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		parentStructureId = objectInput.readLong();
		classNameId = objectInput.readLong();
		structureKey = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		xsd = objectInput.readUTF();
		storageType = objectInput.readUTF();
		type = objectInput.readInt();

		_DDMForm = (com.liferay.portlet.dynamicdatamapping.model.DDMForm)objectInput.readObject();
		_localizedFieldsMap = (java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>>)objectInput.readObject();
		_localizedPersistentFieldsMap = (java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>>)objectInput.readObject();
		_localizedTransientFieldsMap = (java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>>)objectInput.readObject();
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

		objectOutput.writeLong(structureId);
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
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(parentStructureId);
		objectOutput.writeLong(classNameId);

		if (structureKey == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(structureKey);
		}

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

		if (xsd == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(xsd);
		}

		if (storageType == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(storageType);
		}

		objectOutput.writeInt(type);

		objectOutput.writeObject(_DDMForm);
		objectOutput.writeObject(_localizedFieldsMap);
		objectOutput.writeObject(_localizedPersistentFieldsMap);
		objectOutput.writeObject(_localizedTransientFieldsMap);
	}

	public String uuid;
	public long structureId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentStructureId;
	public long classNameId;
	public String structureKey;
	public String name;
	public String description;
	public String xsd;
	public String storageType;
	public int type;
	public com.liferay.portlet.dynamicdatamapping.model.DDMForm _DDMForm;
	public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>> _localizedFieldsMap;
	public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>> _localizedPersistentFieldsMap;
	public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>> _localizedTransientFieldsMap;
}