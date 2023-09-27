/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectViewColumn;
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
 * The cache model class for representing ObjectViewColumn in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectViewColumnCacheModel
	implements CacheModel<ObjectViewColumn>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectViewColumnCacheModel)) {
			return false;
		}

		ObjectViewColumnCacheModel objectViewColumnCacheModel =
			(ObjectViewColumnCacheModel)object;

		if ((objectViewColumnId ==
				objectViewColumnCacheModel.objectViewColumnId) &&
			(mvccVersion == objectViewColumnCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectViewColumnId);

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
		sb.append(", objectViewColumnId=");
		sb.append(objectViewColumnId);
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
		sb.append(", label=");
		sb.append(label);
		sb.append(", objectFieldName=");
		sb.append(objectFieldName);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectViewColumn toEntityModel() {
		ObjectViewColumnImpl objectViewColumnImpl = new ObjectViewColumnImpl();

		objectViewColumnImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectViewColumnImpl.setUuid("");
		}
		else {
			objectViewColumnImpl.setUuid(uuid);
		}

		objectViewColumnImpl.setObjectViewColumnId(objectViewColumnId);
		objectViewColumnImpl.setCompanyId(companyId);
		objectViewColumnImpl.setUserId(userId);

		if (userName == null) {
			objectViewColumnImpl.setUserName("");
		}
		else {
			objectViewColumnImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectViewColumnImpl.setCreateDate(null);
		}
		else {
			objectViewColumnImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectViewColumnImpl.setModifiedDate(null);
		}
		else {
			objectViewColumnImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectViewColumnImpl.setObjectViewId(objectViewId);

		if (label == null) {
			objectViewColumnImpl.setLabel("");
		}
		else {
			objectViewColumnImpl.setLabel(label);
		}

		if (objectFieldName == null) {
			objectViewColumnImpl.setObjectFieldName("");
		}
		else {
			objectViewColumnImpl.setObjectFieldName(objectFieldName);
		}

		objectViewColumnImpl.setPriority(priority);

		objectViewColumnImpl.resetOriginalValues();

		return objectViewColumnImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectViewColumnId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectViewId = objectInput.readLong();
		label = objectInput.readUTF();
		objectFieldName = objectInput.readUTF();

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

		objectOutput.writeLong(objectViewColumnId);

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

		if (label == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(label);
		}

		if (objectFieldName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(objectFieldName);
		}

		objectOutput.writeInt(priority);
	}

	public long mvccVersion;
	public String uuid;
	public long objectViewColumnId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectViewId;
	public String label;
	public String objectFieldName;
	public int priority;

}