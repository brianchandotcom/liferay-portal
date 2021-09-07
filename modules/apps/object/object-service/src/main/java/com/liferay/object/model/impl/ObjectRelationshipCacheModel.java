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

import com.liferay.object.model.ObjectRelationship;
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
 * The cache model class for representing ObjectRelationship in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectRelationshipCacheModel
	implements CacheModel<ObjectRelationship>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectRelationshipCacheModel)) {
			return false;
		}

		ObjectRelationshipCacheModel objectRelationshipCacheModel =
			(ObjectRelationshipCacheModel)object;

		if ((objectRelationshipId ==
				objectRelationshipCacheModel.objectRelationshipId) &&
			(mvccVersion == objectRelationshipCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectRelationshipId);

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
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", objectRelationshipId=");
		sb.append(objectRelationshipId);
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
		sb.append(", label=");
		sb.append(label);
		sb.append(", name=");
		sb.append(name);
		sb.append(", objectDefinitionId1=");
		sb.append(objectDefinitionId1);
		sb.append(", objectDefinitionId1FieldId=");
		sb.append(objectDefinitionId1FieldId);
		sb.append(", objectDefinitionId2=");
		sb.append(objectDefinitionId2);
		sb.append(", objectDefinitionId2FieldId=");
		sb.append(objectDefinitionId2FieldId);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectRelationship toEntityModel() {
		ObjectRelationshipImpl objectRelationshipImpl =
			new ObjectRelationshipImpl();

		objectRelationshipImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectRelationshipImpl.setUuid("");
		}
		else {
			objectRelationshipImpl.setUuid(uuid);
		}

		objectRelationshipImpl.setObjectRelationshipId(objectRelationshipId);
		objectRelationshipImpl.setCompanyId(companyId);
		objectRelationshipImpl.setUserId(userId);

		if (userName == null) {
			objectRelationshipImpl.setUserName("");
		}
		else {
			objectRelationshipImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectRelationshipImpl.setCreateDate(null);
		}
		else {
			objectRelationshipImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectRelationshipImpl.setModifiedDate(null);
		}
		else {
			objectRelationshipImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (label == null) {
			objectRelationshipImpl.setLabel("");
		}
		else {
			objectRelationshipImpl.setLabel(label);
		}

		if (name == null) {
			objectRelationshipImpl.setName("");
		}
		else {
			objectRelationshipImpl.setName(name);
		}

		objectRelationshipImpl.setObjectDefinitionId1(objectDefinitionId1);
		objectRelationshipImpl.setObjectDefinitionId1FieldId(
			objectDefinitionId1FieldId);
		objectRelationshipImpl.setObjectDefinitionId2(objectDefinitionId2);
		objectRelationshipImpl.setObjectDefinitionId2FieldId(
			objectDefinitionId2FieldId);

		if (type == null) {
			objectRelationshipImpl.setType("");
		}
		else {
			objectRelationshipImpl.setType(type);
		}

		objectRelationshipImpl.resetOriginalValues();

		return objectRelationshipImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectRelationshipId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		label = objectInput.readUTF();
		name = objectInput.readUTF();

		objectDefinitionId1 = objectInput.readLong();

		objectDefinitionId1FieldId = objectInput.readLong();

		objectDefinitionId2 = objectInput.readLong();

		objectDefinitionId2FieldId = objectInput.readLong();
		type = objectInput.readUTF();
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

		objectOutput.writeLong(objectRelationshipId);

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

		objectOutput.writeLong(objectDefinitionId1);

		objectOutput.writeLong(objectDefinitionId1FieldId);

		objectOutput.writeLong(objectDefinitionId2);

		objectOutput.writeLong(objectDefinitionId2FieldId);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long objectRelationshipId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String label;
	public String name;
	public long objectDefinitionId1;
	public long objectDefinitionId1FieldId;
	public long objectDefinitionId2;
	public long objectDefinitionId2FieldId;
	public String type;

}