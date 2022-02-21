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

import com.liferay.object.model.ObjectViewSortColumn;
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
 * The cache model class for representing ObjectViewSortColumn in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectViewSortColumnCacheModel
	implements CacheModel<ObjectViewSortColumn>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectViewSortColumnCacheModel)) {
			return false;
		}

		ObjectViewSortColumnCacheModel objectViewSortColumnCacheModel =
			(ObjectViewSortColumnCacheModel)object;

		if ((objectViewSortColumnId ==
				objectViewSortColumnCacheModel.objectViewSortColumnId) &&
			(mvccVersion == objectViewSortColumnCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectViewSortColumnId);

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
		sb.append(", objectViewSortColumnId=");
		sb.append(objectViewSortColumnId);
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
		sb.append(", objectViewId=");
		sb.append(objectViewId);
		sb.append(", objectFieldName=");
		sb.append(objectFieldName);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", sortOrder=");
		sb.append(sortOrder);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectViewSortColumn toEntityModel() {
		ObjectViewSortColumnImpl objectViewSortColumnImpl =
			new ObjectViewSortColumnImpl();

		objectViewSortColumnImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectViewSortColumnImpl.setUuid("");
		}
		else {
			objectViewSortColumnImpl.setUuid(uuid);
		}

		objectViewSortColumnImpl.setObjectViewSortColumnId(
			objectViewSortColumnId);
		objectViewSortColumnImpl.setCompanyId(companyId);
		objectViewSortColumnImpl.setUserId(userId);

		if (userName == null) {
			objectViewSortColumnImpl.setUserName("");
		}
		else {
			objectViewSortColumnImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectViewSortColumnImpl.setCreateDate(null);
		}
		else {
			objectViewSortColumnImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectViewSortColumnImpl.setModifiedDate(null);
		}
		else {
			objectViewSortColumnImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectViewSortColumnImpl.setObjectViewId(objectViewId);

		if (objectFieldName == null) {
			objectViewSortColumnImpl.setObjectFieldName("");
		}
		else {
			objectViewSortColumnImpl.setObjectFieldName(objectFieldName);
		}

		objectViewSortColumnImpl.setPriority(priority);

		if (sortOrder == null) {
			objectViewSortColumnImpl.setSortOrder("");
		}
		else {
			objectViewSortColumnImpl.setSortOrder(sortOrder);
		}

		objectViewSortColumnImpl.resetOriginalValues();

		return objectViewSortColumnImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectViewSortColumnId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectViewId = objectInput.readLong();
		objectFieldName = objectInput.readUTF();

		priority = objectInput.readInt();
		sortOrder = objectInput.readUTF();
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

		objectOutput.writeLong(objectViewSortColumnId);

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

		objectOutput.writeLong(objectViewId);

		if (objectFieldName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(objectFieldName);
		}

		objectOutput.writeInt(priority);

		if (sortOrder == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sortOrder);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long objectViewSortColumnId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectViewId;
	public String objectFieldName;
	public int priority;
	public String sortOrder;

}