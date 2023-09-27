/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectValidationRuleSetting;
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
 * The cache model class for representing ObjectValidationRuleSetting in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectValidationRuleSettingCacheModel
	implements CacheModel<ObjectValidationRuleSetting>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectValidationRuleSettingCacheModel)) {
			return false;
		}

		ObjectValidationRuleSettingCacheModel
			objectValidationRuleSettingCacheModel =
				(ObjectValidationRuleSettingCacheModel)object;

		if ((objectValidationRuleSettingId ==
				objectValidationRuleSettingCacheModel.
					objectValidationRuleSettingId) &&
			(mvccVersion ==
				objectValidationRuleSettingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectValidationRuleSettingId);

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
		sb.append(", objectValidationRuleSettingId=");
		sb.append(objectValidationRuleSettingId);
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
		sb.append(", objectValidationRuleId=");
		sb.append(objectValidationRuleId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectValidationRuleSetting toEntityModel() {
		ObjectValidationRuleSettingImpl objectValidationRuleSettingImpl =
			new ObjectValidationRuleSettingImpl();

		objectValidationRuleSettingImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectValidationRuleSettingImpl.setUuid("");
		}
		else {
			objectValidationRuleSettingImpl.setUuid(uuid);
		}

		objectValidationRuleSettingImpl.setObjectValidationRuleSettingId(
			objectValidationRuleSettingId);
		objectValidationRuleSettingImpl.setCompanyId(companyId);
		objectValidationRuleSettingImpl.setUserId(userId);

		if (userName == null) {
			objectValidationRuleSettingImpl.setUserName("");
		}
		else {
			objectValidationRuleSettingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectValidationRuleSettingImpl.setCreateDate(null);
		}
		else {
			objectValidationRuleSettingImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectValidationRuleSettingImpl.setModifiedDate(null);
		}
		else {
			objectValidationRuleSettingImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		objectValidationRuleSettingImpl.setObjectValidationRuleId(
			objectValidationRuleId);

		if (name == null) {
			objectValidationRuleSettingImpl.setName("");
		}
		else {
			objectValidationRuleSettingImpl.setName(name);
		}

		if (value == null) {
			objectValidationRuleSettingImpl.setValue("");
		}
		else {
			objectValidationRuleSettingImpl.setValue(value);
		}

		objectValidationRuleSettingImpl.resetOriginalValues();

		return objectValidationRuleSettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectValidationRuleSettingId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectValidationRuleId = objectInput.readLong();
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

		objectOutput.writeLong(objectValidationRuleSettingId);

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

		objectOutput.writeLong(objectValidationRuleId);

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
	public long objectValidationRuleSettingId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectValidationRuleId;
	public String name;
	public String value;

}