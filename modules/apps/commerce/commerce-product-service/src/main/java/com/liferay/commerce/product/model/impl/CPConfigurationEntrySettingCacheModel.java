/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPConfigurationEntrySetting;
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
 * The cache model class for representing CPConfigurationEntrySetting in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPConfigurationEntrySettingCacheModel
	implements CacheModel<CPConfigurationEntrySetting>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPConfigurationEntrySettingCacheModel)) {
			return false;
		}

		CPConfigurationEntrySettingCacheModel
			cpConfigurationEntrySettingCacheModel =
				(CPConfigurationEntrySettingCacheModel)object;

		if ((CPConfigurationEntrySettingId ==
				cpConfigurationEntrySettingCacheModel.
					CPConfigurationEntrySettingId) &&
			(mvccVersion ==
				cpConfigurationEntrySettingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPConfigurationEntrySettingId);

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", CPConfigurationEntrySettingId=");
		sb.append(CPConfigurationEntrySettingId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", CPConfigurationEntryId=");
		sb.append(CPConfigurationEntryId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPConfigurationEntrySetting toEntityModel() {
		CPConfigurationEntrySettingImpl cpConfigurationEntrySettingImpl =
			new CPConfigurationEntrySettingImpl();

		cpConfigurationEntrySettingImpl.setMvccVersion(mvccVersion);
		cpConfigurationEntrySettingImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			cpConfigurationEntrySettingImpl.setUuid("");
		}
		else {
			cpConfigurationEntrySettingImpl.setUuid(uuid);
		}

		cpConfigurationEntrySettingImpl.setCPConfigurationEntrySettingId(
			CPConfigurationEntrySettingId);
		cpConfigurationEntrySettingImpl.setGroupId(groupId);
		cpConfigurationEntrySettingImpl.setCompanyId(companyId);
		cpConfigurationEntrySettingImpl.setUserId(userId);

		if (userName == null) {
			cpConfigurationEntrySettingImpl.setUserName("");
		}
		else {
			cpConfigurationEntrySettingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpConfigurationEntrySettingImpl.setCreateDate(null);
		}
		else {
			cpConfigurationEntrySettingImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpConfigurationEntrySettingImpl.setModifiedDate(null);
		}
		else {
			cpConfigurationEntrySettingImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		cpConfigurationEntrySettingImpl.setCPConfigurationEntryId(
			CPConfigurationEntryId);
		cpConfigurationEntrySettingImpl.setType(type);

		if (value == null) {
			cpConfigurationEntrySettingImpl.setValue("");
		}
		else {
			cpConfigurationEntrySettingImpl.setValue(value);
		}

		cpConfigurationEntrySettingImpl.resetOriginalValues();

		return cpConfigurationEntrySettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		CPConfigurationEntrySettingId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPConfigurationEntryId = objectInput.readLong();

		type = objectInput.readInt();
		value = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPConfigurationEntrySettingId);

		objectOutput.writeLong(groupId);

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

		objectOutput.writeLong(CPConfigurationEntryId);

		objectOutput.writeInt(type);

		if (value == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(value);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long CPConfigurationEntrySettingId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPConfigurationEntryId;
	public int type;
	public String value;

}
// LIFERAY-SERVICE-BUILDER-HASH:57223790