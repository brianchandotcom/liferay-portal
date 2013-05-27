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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.documentlibrary.model.DLFileEntryType;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DLFileEntryType in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryType
 * @generated
 */
public class DLFileEntryTypeCacheModel implements CacheModel<DLFileEntryType>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", fileEntryTypeId=");
		sb.append(fileEntryTypeId);
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
		sb.append(", fileEntryTypeKey=");
		sb.append(fileEntryTypeKey);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DLFileEntryType toEntityModel() {
		DLFileEntryTypeImpl dlFileEntryTypeImpl = new DLFileEntryTypeImpl();

		if (uuid == null) {
			dlFileEntryTypeImpl.setUuid(StringPool.BLANK);
		}
		else {
			dlFileEntryTypeImpl.setUuid(uuid);
		}

		dlFileEntryTypeImpl.setFileEntryTypeId(fileEntryTypeId);
		dlFileEntryTypeImpl.setGroupId(groupId);
		dlFileEntryTypeImpl.setCompanyId(companyId);
		dlFileEntryTypeImpl.setUserId(userId);

		if (userName == null) {
			dlFileEntryTypeImpl.setUserName(StringPool.BLANK);
		}
		else {
			dlFileEntryTypeImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			dlFileEntryTypeImpl.setCreateDate(null);
		}
		else {
			dlFileEntryTypeImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			dlFileEntryTypeImpl.setModifiedDate(null);
		}
		else {
			dlFileEntryTypeImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (fileEntryTypeKey == null) {
			dlFileEntryTypeImpl.setFileEntryTypeKey(StringPool.BLANK);
		}
		else {
			dlFileEntryTypeImpl.setFileEntryTypeKey(fileEntryTypeKey);
		}

		if (name == null) {
			dlFileEntryTypeImpl.setName(StringPool.BLANK);
		}
		else {
			dlFileEntryTypeImpl.setName(name);
		}

		if (description == null) {
			dlFileEntryTypeImpl.setDescription(StringPool.BLANK);
		}
		else {
			dlFileEntryTypeImpl.setDescription(description);
		}

		dlFileEntryTypeImpl.resetOriginalValues();

		return dlFileEntryTypeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		fileEntryTypeId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		fileEntryTypeKey = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
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

		objectOutput.writeLong(fileEntryTypeId);
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

		if (fileEntryTypeKey == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(fileEntryTypeKey);
		}

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
	}

	public String uuid;
	public long fileEntryTypeId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String fileEntryTypeKey;
	public String name;
	public String description;
}