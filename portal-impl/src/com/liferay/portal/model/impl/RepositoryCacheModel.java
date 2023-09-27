/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.Repository;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Repository in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RepositoryCacheModel
	implements CacheModel<Repository>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RepositoryCacheModel)) {
			return false;
		}

		RepositoryCacheModel repositoryCacheModel =
			(RepositoryCacheModel)object;

		if ((repositoryId == repositoryCacheModel.repositoryId) &&
			(mvccVersion == repositoryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, repositoryId);

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
		StringBundler sb = new StringBundler(35);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", repositoryId=");
		sb.append(repositoryId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", dlFolderId=");
		sb.append(dlFolderId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Repository toEntityModel() {
		RepositoryImpl repositoryImpl = new RepositoryImpl();

		repositoryImpl.setMvccVersion(mvccVersion);
		repositoryImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			repositoryImpl.setUuid("");
		}
		else {
			repositoryImpl.setUuid(uuid);
		}

		repositoryImpl.setRepositoryId(repositoryId);
		repositoryImpl.setGroupId(groupId);
		repositoryImpl.setCompanyId(companyId);
		repositoryImpl.setUserId(userId);

		if (userName == null) {
			repositoryImpl.setUserName("");
		}
		else {
			repositoryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			repositoryImpl.setCreateDate(null);
		}
		else {
			repositoryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			repositoryImpl.setModifiedDate(null);
		}
		else {
			repositoryImpl.setModifiedDate(new Date(modifiedDate));
		}

		repositoryImpl.setClassNameId(classNameId);

		if (name == null) {
			repositoryImpl.setName("");
		}
		else {
			repositoryImpl.setName(name);
		}

		if (description == null) {
			repositoryImpl.setDescription("");
		}
		else {
			repositoryImpl.setDescription(description);
		}

		if (portletId == null) {
			repositoryImpl.setPortletId("");
		}
		else {
			repositoryImpl.setPortletId(portletId);
		}

		if (typeSettings == null) {
			repositoryImpl.setTypeSettings("");
		}
		else {
			repositoryImpl.setTypeSettings(typeSettings);
		}

		repositoryImpl.setDlFolderId(dlFolderId);

		if (lastPublishDate == Long.MIN_VALUE) {
			repositoryImpl.setLastPublishDate(null);
		}
		else {
			repositoryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		repositoryImpl.resetOriginalValues();

		return repositoryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		repositoryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		portletId = objectInput.readUTF();
		typeSettings = (String)objectInput.readObject();

		dlFolderId = objectInput.readLong();
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

		objectOutput.writeLong(repositoryId);

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

		objectOutput.writeLong(classNameId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (portletId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		if (typeSettings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(typeSettings);
		}

		objectOutput.writeLong(dlFolderId);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long repositoryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public String name;
	public String description;
	public String portletId;
	public String typeSettings;
	public long dlFolderId;
	public long lastPublishDate;

}