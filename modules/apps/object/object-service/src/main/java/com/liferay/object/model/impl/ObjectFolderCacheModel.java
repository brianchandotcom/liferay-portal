/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectFolder;
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
 * The cache model class for representing ObjectFolder in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectFolderCacheModel
	implements CacheModel<ObjectFolder>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectFolderCacheModel)) {
			return false;
		}

		ObjectFolderCacheModel objectFolderCacheModel =
			(ObjectFolderCacheModel)object;

		if ((objectFolderId == objectFolderCacheModel.objectFolderId) &&
			(mvccVersion == objectFolderCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectFolderId);

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
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", objectFolderId=");
		sb.append(objectFolderId);
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
		sb.append(", label=");
		sb.append(label);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectFolder toEntityModel() {
		ObjectFolderImpl objectFolderImpl = new ObjectFolderImpl();

		objectFolderImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectFolderImpl.setUuid("");
		}
		else {
			objectFolderImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			objectFolderImpl.setExternalReferenceCode("");
		}
		else {
			objectFolderImpl.setExternalReferenceCode(externalReferenceCode);
		}

		objectFolderImpl.setObjectFolderId(objectFolderId);
		objectFolderImpl.setCompanyId(companyId);
		objectFolderImpl.setUserId(userId);

		if (userName == null) {
			objectFolderImpl.setUserName("");
		}
		else {
			objectFolderImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectFolderImpl.setCreateDate(null);
		}
		else {
			objectFolderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectFolderImpl.setModifiedDate(null);
		}
		else {
			objectFolderImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (label == null) {
			objectFolderImpl.setLabel("");
		}
		else {
			objectFolderImpl.setLabel(label);
		}

		if (name == null) {
			objectFolderImpl.setName("");
		}
		else {
			objectFolderImpl.setName(name);
		}

		objectFolderImpl.resetOriginalValues();

		return objectFolderImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		objectFolderId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		label = objectInput.readUTF();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(objectFolderId);

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

		if (label == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(label);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long objectFolderId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String label;
	public String name;

}