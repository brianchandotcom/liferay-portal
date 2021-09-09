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

import com.liferay.object.model.ObjectLayoutTab;
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
 * The cache model class for representing ObjectLayoutTab in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectLayoutTabCacheModel
	implements CacheModel<ObjectLayoutTab>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectLayoutTabCacheModel)) {
			return false;
		}

		ObjectLayoutTabCacheModel objectLayoutTabCacheModel =
			(ObjectLayoutTabCacheModel)object;

		if ((objectLayoutTabId ==
				objectLayoutTabCacheModel.objectLayoutTabId) &&
			(mvccVersion == objectLayoutTabCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectLayoutTabId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", objectLayoutTabId=");
		sb.append(objectLayoutTabId);
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
		sb.append(", objectLayoutId=");
		sb.append(objectLayoutId);
		sb.append(", objectRelationshipId=");
		sb.append(objectRelationshipId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectLayoutTab toEntityModel() {
		ObjectLayoutTabImpl objectLayoutTabImpl = new ObjectLayoutTabImpl();

		objectLayoutTabImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectLayoutTabImpl.setUuid("");
		}
		else {
			objectLayoutTabImpl.setUuid(uuid);
		}

		objectLayoutTabImpl.setObjectLayoutTabId(objectLayoutTabId);
		objectLayoutTabImpl.setCompanyId(companyId);
		objectLayoutTabImpl.setUserId(userId);

		if (userName == null) {
			objectLayoutTabImpl.setUserName("");
		}
		else {
			objectLayoutTabImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectLayoutTabImpl.setCreateDate(null);
		}
		else {
			objectLayoutTabImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectLayoutTabImpl.setModifiedDate(null);
		}
		else {
			objectLayoutTabImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectLayoutTabImpl.setObjectLayoutId(objectLayoutId);
		objectLayoutTabImpl.setObjectRelationshipId(objectRelationshipId);

		if (name == null) {
			objectLayoutTabImpl.setName("");
		}
		else {
			objectLayoutTabImpl.setName(name);
		}

		objectLayoutTabImpl.setPriority(priority);

		objectLayoutTabImpl.resetOriginalValues();

		return objectLayoutTabImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectLayoutTabId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectLayoutId = objectInput.readLong();

		objectRelationshipId = objectInput.readLong();
		name = objectInput.readUTF();

		priority = objectInput.readInt();
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

		objectOutput.writeLong(objectLayoutTabId);

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

		objectOutput.writeLong(objectLayoutId);

		objectOutput.writeLong(objectRelationshipId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(priority);
	}

	public long mvccVersion;
	public String uuid;
	public long objectLayoutTabId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectLayoutId;
	public long objectRelationshipId;
	public String name;
	public int priority;

}