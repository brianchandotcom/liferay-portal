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

import com.liferay.object.model.ObjectLayoutBoxRow;
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
 * The cache model class for representing ObjectLayoutBoxRow in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectLayoutBoxRowCacheModel
	implements CacheModel<ObjectLayoutBoxRow>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectLayoutBoxRowCacheModel)) {
			return false;
		}

		ObjectLayoutBoxRowCacheModel objectLayoutBoxRowCacheModel =
			(ObjectLayoutBoxRowCacheModel)object;

		if ((objectLayoutBoxRowId ==
				objectLayoutBoxRowCacheModel.objectLayoutBoxRowId) &&
			(mvccVersion == objectLayoutBoxRowCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectLayoutBoxRowId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", objectLayoutBoxRowId=");
		sb.append(objectLayoutBoxRowId);
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
		sb.append(", objectLayoutBoxId=");
		sb.append(objectLayoutBoxId);
		sb.append(", order=");
		sb.append(order);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectLayoutBoxRow toEntityModel() {
		ObjectLayoutBoxRowImpl objectLayoutBoxRowImpl =
			new ObjectLayoutBoxRowImpl();

		objectLayoutBoxRowImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectLayoutBoxRowImpl.setUuid("");
		}
		else {
			objectLayoutBoxRowImpl.setUuid(uuid);
		}

		objectLayoutBoxRowImpl.setObjectLayoutBoxRowId(objectLayoutBoxRowId);
		objectLayoutBoxRowImpl.setCompanyId(companyId);
		objectLayoutBoxRowImpl.setUserId(userId);

		if (userName == null) {
			objectLayoutBoxRowImpl.setUserName("");
		}
		else {
			objectLayoutBoxRowImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectLayoutBoxRowImpl.setCreateDate(null);
		}
		else {
			objectLayoutBoxRowImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectLayoutBoxRowImpl.setModifiedDate(null);
		}
		else {
			objectLayoutBoxRowImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectLayoutBoxRowImpl.setObjectLayoutBoxId(objectLayoutBoxId);
		objectLayoutBoxRowImpl.setOrder(order);

		objectLayoutBoxRowImpl.resetOriginalValues();

		return objectLayoutBoxRowImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectLayoutBoxRowId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectLayoutBoxId = objectInput.readLong();

		order = objectInput.readInt();
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

		objectOutput.writeLong(objectLayoutBoxRowId);

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

		objectOutput.writeLong(objectLayoutBoxId);

		objectOutput.writeInt(order);
	}

	public long mvccVersion;
	public String uuid;
	public long objectLayoutBoxRowId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectLayoutBoxId;
	public int order;

}