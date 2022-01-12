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

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectField;
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
 * The cache model class for representing ObjectField in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectFieldCacheModel
	implements CacheModel<ObjectField>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectFieldCacheModel)) {
			return false;
		}

		ObjectFieldCacheModel objectFieldCacheModel =
			(ObjectFieldCacheModel)object;

		if ((objectFieldId == objectFieldCacheModel.objectFieldId) &&
			(mvccVersion == objectFieldCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectFieldId);

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
		sb.append(", objectFieldId=");
		sb.append(objectFieldId);
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
		sb.append(", listTypeDefinitionId=");
		sb.append(listTypeDefinitionId);
		sb.append(", objectDefinitionId=");
		sb.append(objectDefinitionId);
		sb.append(", businessType=");
		sb.append(businessType);
		sb.append(", dbColumnName=");
		sb.append(dbColumnName);
		sb.append(", dbTableName=");
		sb.append(dbTableName);
		sb.append(", dbType=");
		sb.append(dbType);
		sb.append(", indexed=");
		sb.append(indexed);
		sb.append(", indexedAsKeyword=");
		sb.append(indexedAsKeyword);
		sb.append(", indexedLanguageId=");
		sb.append(indexedLanguageId);
		sb.append(", label=");
		sb.append(label);
		sb.append(", name=");
		sb.append(name);
		sb.append(", relationshipType=");
		sb.append(relationshipType);
		sb.append(", required=");
		sb.append(required);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectField toEntityModel() {
		ObjectFieldImpl objectFieldImpl = new ObjectFieldImpl();

		objectFieldImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectFieldImpl.setUuid("");
		}
		else {
			objectFieldImpl.setUuid(uuid);
		}

		objectFieldImpl.setObjectFieldId(objectFieldId);
		objectFieldImpl.setCompanyId(companyId);
		objectFieldImpl.setUserId(userId);

		if (userName == null) {
			objectFieldImpl.setUserName("");
		}
		else {
			objectFieldImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectFieldImpl.setCreateDate(null);
		}
		else {
			objectFieldImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectFieldImpl.setModifiedDate(null);
		}
		else {
			objectFieldImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectFieldImpl.setListTypeDefinitionId(listTypeDefinitionId);
		objectFieldImpl.setObjectDefinitionId(objectDefinitionId);

		if (businessType == null) {
			objectFieldImpl.setBusinessType("");
		}
		else {
			objectFieldImpl.setBusinessType(businessType);
		}

		if (dbColumnName == null) {
			objectFieldImpl.setDBColumnName("");
		}
		else {
			objectFieldImpl.setDBColumnName(dbColumnName);
		}

		if (dbTableName == null) {
			objectFieldImpl.setDBTableName("");
		}
		else {
			objectFieldImpl.setDBTableName(dbTableName);
		}

		if (dbType == null) {
			objectFieldImpl.setDBType("");
		}
		else {
			objectFieldImpl.setDBType(dbType);
		}

		objectFieldImpl.setIndexed(indexed);
		objectFieldImpl.setIndexedAsKeyword(indexedAsKeyword);

		if (indexedLanguageId == null) {
			objectFieldImpl.setIndexedLanguageId("");
		}
		else {
			objectFieldImpl.setIndexedLanguageId(indexedLanguageId);
		}

		if (label == null) {
			objectFieldImpl.setLabel("");
		}
		else {
			objectFieldImpl.setLabel(label);
		}

		if (name == null) {
			objectFieldImpl.setName("");
		}
		else {
			objectFieldImpl.setName(name);
		}

		if (relationshipType == null) {
			objectFieldImpl.setRelationshipType("");
		}
		else {
			objectFieldImpl.setRelationshipType(relationshipType);
		}

		objectFieldImpl.setRequired(required);

		objectFieldImpl.resetOriginalValues();

		return objectFieldImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectFieldId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		listTypeDefinitionId = objectInput.readLong();

		objectDefinitionId = objectInput.readLong();
		businessType = objectInput.readUTF();
		dbColumnName = objectInput.readUTF();
		dbTableName = objectInput.readUTF();
		dbType = objectInput.readUTF();

		indexed = objectInput.readBoolean();

		indexedAsKeyword = objectInput.readBoolean();
		indexedLanguageId = objectInput.readUTF();
		label = objectInput.readUTF();
		name = objectInput.readUTF();
		relationshipType = objectInput.readUTF();

		required = objectInput.readBoolean();
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

		objectOutput.writeLong(objectFieldId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(listTypeDefinitionId);

		objectOutput.writeLong(objectDefinitionId);

		if (businessType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(businessType);
		}

		if (dbColumnName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(dbColumnName);
		}

		if (dbTableName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(dbTableName);
		}

		if (dbType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(dbType);
		}

		objectOutput.writeBoolean(indexed);

		objectOutput.writeBoolean(indexedAsKeyword);

		if (indexedLanguageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(indexedLanguageId);
		}

		if (label == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(label);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (relationshipType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(relationshipType);
		}

		objectOutput.writeBoolean(required);
	}

	public long mvccVersion;
	public String uuid;
	public long objectFieldId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long listTypeDefinitionId;
	public long objectDefinitionId;
	public String businessType;
	public String dbColumnName;
	public String dbTableName;
	public String dbType;
	public boolean indexed;
	public boolean indexedAsKeyword;
	public String indexedLanguageId;
	public String label;
	public String name;
	public String relationshipType;
	public boolean required;

}