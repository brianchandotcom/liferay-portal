/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectViewFilterColumn;
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
 * The cache model class for representing ObjectViewFilterColumn in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectViewFilterColumnCacheModel
	implements CacheModel<ObjectViewFilterColumn>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectViewFilterColumnCacheModel)) {
			return false;
		}

		ObjectViewFilterColumnCacheModel objectViewFilterColumnCacheModel =
			(ObjectViewFilterColumnCacheModel)object;

		if ((objectViewFilterColumnId ==
				objectViewFilterColumnCacheModel.objectViewFilterColumnId) &&
			(mvccVersion == objectViewFilterColumnCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectViewFilterColumnId);

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
		sb.append(", objectViewFilterColumnId=");
		sb.append(objectViewFilterColumnId);
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
		sb.append(", filterType=");
		sb.append(filterType);
		sb.append(", json=");
		sb.append(json);
		sb.append(", objectFieldName=");
		sb.append(objectFieldName);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectViewFilterColumn toEntityModel() {
		ObjectViewFilterColumnImpl objectViewFilterColumnImpl =
			new ObjectViewFilterColumnImpl();

		objectViewFilterColumnImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectViewFilterColumnImpl.setUuid("");
		}
		else {
			objectViewFilterColumnImpl.setUuid(uuid);
		}

		objectViewFilterColumnImpl.setObjectViewFilterColumnId(
			objectViewFilterColumnId);
		objectViewFilterColumnImpl.setCompanyId(companyId);
		objectViewFilterColumnImpl.setUserId(userId);

		if (userName == null) {
			objectViewFilterColumnImpl.setUserName("");
		}
		else {
			objectViewFilterColumnImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectViewFilterColumnImpl.setCreateDate(null);
		}
		else {
			objectViewFilterColumnImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectViewFilterColumnImpl.setModifiedDate(null);
		}
		else {
			objectViewFilterColumnImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectViewFilterColumnImpl.setObjectViewId(objectViewId);

		if (filterType == null) {
			objectViewFilterColumnImpl.setFilterType("");
		}
		else {
			objectViewFilterColumnImpl.setFilterType(filterType);
		}

		if (json == null) {
			objectViewFilterColumnImpl.setJSON("");
		}
		else {
			objectViewFilterColumnImpl.setJSON(json);
		}

		if (objectFieldName == null) {
			objectViewFilterColumnImpl.setObjectFieldName("");
		}
		else {
			objectViewFilterColumnImpl.setObjectFieldName(objectFieldName);
		}

		objectViewFilterColumnImpl.resetOriginalValues();

		return objectViewFilterColumnImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectViewFilterColumnId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectViewId = objectInput.readLong();
		filterType = objectInput.readUTF();
		json = (String)objectInput.readObject();
		objectFieldName = objectInput.readUTF();
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

		objectOutput.writeLong(objectViewFilterColumnId);

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

		if (filterType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(filterType);
		}

		if (json == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(json);
		}

		if (objectFieldName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(objectFieldName);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long objectViewFilterColumnId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectViewId;
	public String filterType;
	public String json;
	public String objectFieldName;

}