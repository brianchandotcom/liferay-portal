/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.model.impl;

import com.liferay.dynamic.data.lists.model.DDLRecord;
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
 * The cache model class for representing DDLRecord in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDLRecordCacheModel
	implements CacheModel<DDLRecord>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDLRecordCacheModel)) {
			return false;
		}

		DDLRecordCacheModel ddlRecordCacheModel = (DDLRecordCacheModel)object;

		if ((recordId == ddlRecordCacheModel.recordId) &&
			(mvccVersion == ddlRecordCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, recordId);

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
		StringBundler sb = new StringBundler(41);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", recordId=");
		sb.append(recordId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", versionUserId=");
		sb.append(versionUserId);
		sb.append(", versionUserName=");
		sb.append(versionUserName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", DDMStorageId=");
		sb.append(DDMStorageId);
		sb.append(", recordSetId=");
		sb.append(recordSetId);
		sb.append(", recordSetVersion=");
		sb.append(recordSetVersion);
		sb.append(", className=");
		sb.append(className);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", version=");
		sb.append(version);
		sb.append(", displayIndex=");
		sb.append(displayIndex);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDLRecord toEntityModel() {
		DDLRecordImpl ddlRecordImpl = new DDLRecordImpl();

		ddlRecordImpl.setMvccVersion(mvccVersion);
		ddlRecordImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			ddlRecordImpl.setUuid("");
		}
		else {
			ddlRecordImpl.setUuid(uuid);
		}

		ddlRecordImpl.setRecordId(recordId);
		ddlRecordImpl.setGroupId(groupId);
		ddlRecordImpl.setCompanyId(companyId);
		ddlRecordImpl.setUserId(userId);

		if (userName == null) {
			ddlRecordImpl.setUserName("");
		}
		else {
			ddlRecordImpl.setUserName(userName);
		}

		ddlRecordImpl.setVersionUserId(versionUserId);

		if (versionUserName == null) {
			ddlRecordImpl.setVersionUserName("");
		}
		else {
			ddlRecordImpl.setVersionUserName(versionUserName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddlRecordImpl.setCreateDate(null);
		}
		else {
			ddlRecordImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddlRecordImpl.setModifiedDate(null);
		}
		else {
			ddlRecordImpl.setModifiedDate(new Date(modifiedDate));
		}

		ddlRecordImpl.setDDMStorageId(DDMStorageId);
		ddlRecordImpl.setRecordSetId(recordSetId);

		if (recordSetVersion == null) {
			ddlRecordImpl.setRecordSetVersion("");
		}
		else {
			ddlRecordImpl.setRecordSetVersion(recordSetVersion);
		}

		if (className == null) {
			ddlRecordImpl.setClassName("");
		}
		else {
			ddlRecordImpl.setClassName(className);
		}

		ddlRecordImpl.setClassPK(classPK);

		if (version == null) {
			ddlRecordImpl.setVersion("");
		}
		else {
			ddlRecordImpl.setVersion(version);
		}

		ddlRecordImpl.setDisplayIndex(displayIndex);

		if (lastPublishDate == Long.MIN_VALUE) {
			ddlRecordImpl.setLastPublishDate(null);
		}
		else {
			ddlRecordImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		ddlRecordImpl.resetOriginalValues();

		return ddlRecordImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		recordId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		versionUserId = objectInput.readLong();
		versionUserName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		DDMStorageId = objectInput.readLong();

		recordSetId = objectInput.readLong();
		recordSetVersion = objectInput.readUTF();
		className = objectInput.readUTF();

		classPK = objectInput.readLong();
		version = objectInput.readUTF();

		displayIndex = objectInput.readInt();
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

		objectOutput.writeLong(recordId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(versionUserId);

		if (versionUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(versionUserName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(DDMStorageId);

		objectOutput.writeLong(recordSetId);

		if (recordSetVersion == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(recordSetVersion);
		}

		if (className == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(className);
		}

		objectOutput.writeLong(classPK);

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeInt(displayIndex);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long recordId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long versionUserId;
	public String versionUserName;
	public long createDate;
	public long modifiedDate;
	public long DDMStorageId;
	public long recordSetId;
	public String recordSetVersion;
	public String className;
	public long classPK;
	public String version;
	public int displayIndex;
	public long lastPublishDate;

}