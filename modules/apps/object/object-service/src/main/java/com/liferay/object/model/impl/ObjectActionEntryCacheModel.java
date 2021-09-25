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

import com.liferay.object.model.ObjectActionEntry;
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
 * The cache model class for representing ObjectActionEntry in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectActionEntryCacheModel
	implements CacheModel<ObjectActionEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectActionEntryCacheModel)) {
			return false;
		}

		ObjectActionEntryCacheModel objectActionEntryCacheModel =
			(ObjectActionEntryCacheModel)object;

		if ((objectActionEntryId ==
				objectActionEntryCacheModel.objectActionEntryId) &&
			(mvccVersion == objectActionEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectActionEntryId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", objectActionEntryId=");
		sb.append(objectActionEntryId);
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
		sb.append(", objectDefinitionId=");
		sb.append(objectDefinitionId);
		sb.append(", active=");
		sb.append(active);
		sb.append(", triggerName=");
		sb.append(triggerName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", settings=");
		sb.append(settings);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectActionEntry toEntityModel() {
		ObjectActionEntryImpl objectActionEntryImpl =
			new ObjectActionEntryImpl();

		objectActionEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectActionEntryImpl.setUuid("");
		}
		else {
			objectActionEntryImpl.setUuid(uuid);
		}

		objectActionEntryImpl.setObjectActionEntryId(objectActionEntryId);
		objectActionEntryImpl.setCompanyId(companyId);
		objectActionEntryImpl.setUserId(userId);

		if (userName == null) {
			objectActionEntryImpl.setUserName("");
		}
		else {
			objectActionEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectActionEntryImpl.setCreateDate(null);
		}
		else {
			objectActionEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectActionEntryImpl.setModifiedDate(null);
		}
		else {
			objectActionEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectActionEntryImpl.setObjectDefinitionId(objectDefinitionId);
		objectActionEntryImpl.setActive(active);

		if (triggerName == null) {
			objectActionEntryImpl.setTriggerName("");
		}
		else {
			objectActionEntryImpl.setTriggerName(triggerName);
		}

		if (name == null) {
			objectActionEntryImpl.setName("");
		}
		else {
			objectActionEntryImpl.setName(name);
		}

		if (settings == null) {
			objectActionEntryImpl.setSettings("");
		}
		else {
			objectActionEntryImpl.setSettings(settings);
		}

		objectActionEntryImpl.resetOriginalValues();

		return objectActionEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectActionEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectDefinitionId = objectInput.readLong();

		active = objectInput.readBoolean();
		triggerName = objectInput.readUTF();
		name = objectInput.readUTF();
		settings = objectInput.readUTF();
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

		objectOutput.writeLong(objectActionEntryId);

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

		objectOutput.writeLong(objectDefinitionId);

		objectOutput.writeBoolean(active);

		if (triggerName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(triggerName);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (settings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(settings);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long objectActionEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectDefinitionId;
	public boolean active;
	public String triggerName;
	public String name;
	public String settings;

}