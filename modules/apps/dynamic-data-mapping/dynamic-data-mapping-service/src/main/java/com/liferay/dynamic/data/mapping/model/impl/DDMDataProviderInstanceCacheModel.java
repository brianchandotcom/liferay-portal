/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
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
 * The cache model class for representing DDMDataProviderInstance in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMDataProviderInstanceCacheModel
	implements CacheModel<DDMDataProviderInstance>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMDataProviderInstanceCacheModel)) {
			return false;
		}

		DDMDataProviderInstanceCacheModel ddmDataProviderInstanceCacheModel =
			(DDMDataProviderInstanceCacheModel)object;

		if ((dataProviderInstanceId ==
				ddmDataProviderInstanceCacheModel.dataProviderInstanceId) &&
			(mvccVersion == ddmDataProviderInstanceCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, dataProviderInstanceId);

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
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", dataProviderInstanceId=");
		sb.append(dataProviderInstanceId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", definition=");
		sb.append(definition);
		sb.append(", type=");
		sb.append(type);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMDataProviderInstance toEntityModel() {
		DDMDataProviderInstanceImpl ddmDataProviderInstanceImpl =
			new DDMDataProviderInstanceImpl();

		ddmDataProviderInstanceImpl.setMvccVersion(mvccVersion);
		ddmDataProviderInstanceImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			ddmDataProviderInstanceImpl.setUuid("");
		}
		else {
			ddmDataProviderInstanceImpl.setUuid(uuid);
		}

		ddmDataProviderInstanceImpl.setDataProviderInstanceId(
			dataProviderInstanceId);
		ddmDataProviderInstanceImpl.setGroupId(groupId);
		ddmDataProviderInstanceImpl.setCompanyId(companyId);
		ddmDataProviderInstanceImpl.setUserId(userId);

		if (userName == null) {
			ddmDataProviderInstanceImpl.setUserName("");
		}
		else {
			ddmDataProviderInstanceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmDataProviderInstanceImpl.setCreateDate(null);
		}
		else {
			ddmDataProviderInstanceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddmDataProviderInstanceImpl.setModifiedDate(null);
		}
		else {
			ddmDataProviderInstanceImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			ddmDataProviderInstanceImpl.setName("");
		}
		else {
			ddmDataProviderInstanceImpl.setName(name);
		}

		if (description == null) {
			ddmDataProviderInstanceImpl.setDescription("");
		}
		else {
			ddmDataProviderInstanceImpl.setDescription(description);
		}

		if (definition == null) {
			ddmDataProviderInstanceImpl.setDefinition("");
		}
		else {
			ddmDataProviderInstanceImpl.setDefinition(definition);
		}

		if (type == null) {
			ddmDataProviderInstanceImpl.setType("");
		}
		else {
			ddmDataProviderInstanceImpl.setType(type);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			ddmDataProviderInstanceImpl.setLastPublishDate(null);
		}
		else {
			ddmDataProviderInstanceImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		ddmDataProviderInstanceImpl.resetOriginalValues();

		return ddmDataProviderInstanceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		dataProviderInstanceId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = (String)objectInput.readObject();
		definition = (String)objectInput.readObject();
		type = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(dataProviderInstanceId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(description);
		}

		if (definition == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(definition);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long dataProviderInstanceId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String definition;
	public String type;
	public long lastPublishDate;

}