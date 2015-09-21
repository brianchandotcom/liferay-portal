/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.dynamic.data.mapping.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMContent;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DDMContent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDMContent
 * @generated
 */
@ProviderType
public class DDMContentCacheModel implements CacheModel<DDMContent>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMContentCacheModel)) {
			return false;
		}

		DDMContentCacheModel ddmContentCacheModel = (DDMContentCacheModel)obj;

		if (contentId == ddmContentCacheModel.contentId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, contentId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", contentId=");
		sb.append(contentId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", data=");
		sb.append(data);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMContent toEntityModel() {
		DDMContentImpl ddmContentImpl = new DDMContentImpl();

		if (uuid == null) {
			ddmContentImpl.setUuid(StringPool.BLANK);
		}
		else {
			ddmContentImpl.setUuid(uuid);
		}

		ddmContentImpl.setContentId(contentId);
		ddmContentImpl.setGroupId(groupId);
		ddmContentImpl.setUserId(userId);

		if (userName == null) {
			ddmContentImpl.setUserName(StringPool.BLANK);
		}
		else {
			ddmContentImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmContentImpl.setCreateDate(null);
		}
		else {
			ddmContentImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddmContentImpl.setModifiedDate(null);
		}
		else {
			ddmContentImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			ddmContentImpl.setName(StringPool.BLANK);
		}
		else {
			ddmContentImpl.setName(name);
		}

		if (description == null) {
			ddmContentImpl.setDescription(StringPool.BLANK);
		}
		else {
			ddmContentImpl.setDescription(description);
		}

		if (data == null) {
			ddmContentImpl.setData(StringPool.BLANK);
		}
		else {
			ddmContentImpl.setData(data);
		}

		ddmContentImpl.setCompanyId(companyId);

		ddmContentImpl.resetOriginalValues();

		return ddmContentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		contentId = objectInput.readLong();
		groupId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		data = objectInput.readUTF();
		companyId = objectInput.readLong();
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

		objectOutput.writeLong(contentId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

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

		if (data == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(data);
		}

		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public long contentId;
	public long groupId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String data;
	public long companyId;
}