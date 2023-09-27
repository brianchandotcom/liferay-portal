/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectFieldSetting;
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
 * The cache model class for representing ObjectFieldSetting in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectFieldSettingCacheModel
	implements CacheModel<ObjectFieldSetting>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectFieldSettingCacheModel)) {
			return false;
		}

		ObjectFieldSettingCacheModel objectFieldSettingCacheModel =
			(ObjectFieldSettingCacheModel)object;

		if ((objectFieldSettingId ==
				objectFieldSettingCacheModel.objectFieldSettingId) &&
			(mvccVersion == objectFieldSettingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectFieldSettingId);

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
		sb.append(", objectFieldSettingId=");
		sb.append(objectFieldSettingId);
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
		sb.append(", objectFieldId=");
		sb.append(objectFieldId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectFieldSetting toEntityModel() {
		ObjectFieldSettingImpl objectFieldSettingImpl =
			new ObjectFieldSettingImpl();

		objectFieldSettingImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectFieldSettingImpl.setUuid("");
		}
		else {
			objectFieldSettingImpl.setUuid(uuid);
		}

		objectFieldSettingImpl.setObjectFieldSettingId(objectFieldSettingId);
		objectFieldSettingImpl.setCompanyId(companyId);
		objectFieldSettingImpl.setUserId(userId);

		if (userName == null) {
			objectFieldSettingImpl.setUserName("");
		}
		else {
			objectFieldSettingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectFieldSettingImpl.setCreateDate(null);
		}
		else {
			objectFieldSettingImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectFieldSettingImpl.setModifiedDate(null);
		}
		else {
			objectFieldSettingImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectFieldSettingImpl.setObjectFieldId(objectFieldId);

		if (name == null) {
			objectFieldSettingImpl.setName("");
		}
		else {
			objectFieldSettingImpl.setName(name);
		}

		if (value == null) {
			objectFieldSettingImpl.setValue("");
		}
		else {
			objectFieldSettingImpl.setValue(value);
		}

		objectFieldSettingImpl.resetOriginalValues();

		return objectFieldSettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectFieldSettingId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectFieldId = objectInput.readLong();
		name = objectInput.readUTF();
		value = objectInput.readUTF();
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

		objectOutput.writeLong(objectFieldSettingId);

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

		objectOutput.writeLong(objectFieldId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long objectFieldSettingId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectFieldId;
	public String name;
	public String value;

}