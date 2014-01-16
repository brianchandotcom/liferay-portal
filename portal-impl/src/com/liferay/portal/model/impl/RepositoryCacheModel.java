/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Repository;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Repository in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Repository
 * @generated
 */
public class RepositoryCacheModel implements CacheModel<Repository>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{uuid=");
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
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Repository toEntityModel() {
		RepositoryImpl repositoryImpl = new RepositoryImpl();

		if (uuid == null) {
			repositoryImpl.setUuid(StringPool.BLANK);
		}
		else {
			repositoryImpl.setUuid(uuid);
		}

		repositoryImpl.setRepositoryId(repositoryId);
		repositoryImpl.setGroupId(groupId);
		repositoryImpl.setCompanyId(companyId);
		repositoryImpl.setUserId(userId);

		if (userName == null) {
			repositoryImpl.setUserName(StringPool.BLANK);
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
			repositoryImpl.setName(StringPool.BLANK);
		}
		else {
			repositoryImpl.setName(name);
		}

		if (description == null) {
			repositoryImpl.setDescription(StringPool.BLANK);
		}
		else {
			repositoryImpl.setDescription(description);
		}

		if (portletId == null) {
			repositoryImpl.setPortletId(StringPool.BLANK);
		}
		else {
			repositoryImpl.setPortletId(portletId);
		}

		if (typeSettings == null) {
			repositoryImpl.setTypeSettings(StringPool.BLANK);
		}
		else {
			repositoryImpl.setTypeSettings(typeSettings);
		}

		repositoryImpl.setDlFolderId(dlFolderId);
		repositoryImpl.setMvccVersion(mvccVersion);

		repositoryImpl.resetOriginalValues();

		return repositoryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
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
		typeSettings = objectInput.readUTF();
		dlFolderId = objectInput.readLong();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(repositoryId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(classNameId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (portletId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeLong(dlFolderId);
		objectOutput.writeLong(mvccVersion);
	}

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
	public long mvccVersion;
}