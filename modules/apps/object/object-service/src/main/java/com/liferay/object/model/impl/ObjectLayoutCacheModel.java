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

import com.liferay.object.model.ObjectLayout;
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
 * The cache model class for representing ObjectLayout in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectLayoutCacheModel
	implements CacheModel<ObjectLayout>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectLayoutCacheModel)) {
			return false;
		}

		ObjectLayoutCacheModel objectLayoutCacheModel =
			(ObjectLayoutCacheModel)object;

		if ((objectLayoutId == objectLayoutCacheModel.objectLayoutId) &&
			(mvccVersion == objectLayoutCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectLayoutId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", objectLayoutId=");
		sb.append(objectLayoutId);
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
		sb.append(", defaultObjectLayout=");
		sb.append(defaultObjectLayout);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectLayout toEntityModel() {
		ObjectLayoutImpl objectLayoutImpl = new ObjectLayoutImpl();

		objectLayoutImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectLayoutImpl.setUuid("");
		}
		else {
			objectLayoutImpl.setUuid(uuid);
		}

		objectLayoutImpl.setObjectLayoutId(objectLayoutId);
		objectLayoutImpl.setCompanyId(companyId);
		objectLayoutImpl.setUserId(userId);

		if (userName == null) {
			objectLayoutImpl.setUserName("");
		}
		else {
			objectLayoutImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectLayoutImpl.setCreateDate(null);
		}
		else {
			objectLayoutImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectLayoutImpl.setModifiedDate(null);
		}
		else {
			objectLayoutImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectLayoutImpl.setObjectDefinitionId(objectDefinitionId);
		objectLayoutImpl.setDefaultObjectLayout(defaultObjectLayout);

		if (name == null) {
			objectLayoutImpl.setName("");
		}
		else {
			objectLayoutImpl.setName(name);
		}

		objectLayoutImpl.resetOriginalValues();

		return objectLayoutImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectLayoutId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectDefinitionId = objectInput.readLong();

		defaultObjectLayout = objectInput.readBoolean();
		name = objectInput.readUTF();
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

		objectOutput.writeLong(objectLayoutId);

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

		objectOutput.writeBoolean(defaultObjectLayout);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long objectLayoutId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectDefinitionId;
	public boolean defaultObjectLayout;
	public String name;

}