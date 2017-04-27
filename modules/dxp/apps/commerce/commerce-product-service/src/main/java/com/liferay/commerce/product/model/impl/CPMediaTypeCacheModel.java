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

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPMediaType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPMediaType in entity cache.
 *
 * @author Marco Leo
 * @see CPMediaType
 * @generated
 */
@ProviderType
public class CPMediaTypeCacheModel implements CacheModel<CPMediaType>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPMediaTypeCacheModel)) {
			return false;
		}

		CPMediaTypeCacheModel cpMediaTypeCacheModel = (CPMediaTypeCacheModel)obj;

		if (CPMediaTypeId == cpMediaTypeCacheModel.CPMediaTypeId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPMediaTypeId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPMediaTypeId=");
		sb.append(CPMediaTypeId);
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
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPMediaType toEntityModel() {
		CPMediaTypeImpl cpMediaTypeImpl = new CPMediaTypeImpl();

		if (uuid == null) {
			cpMediaTypeImpl.setUuid(StringPool.BLANK);
		}
		else {
			cpMediaTypeImpl.setUuid(uuid);
		}

		cpMediaTypeImpl.setCPMediaTypeId(CPMediaTypeId);
		cpMediaTypeImpl.setGroupId(groupId);
		cpMediaTypeImpl.setCompanyId(companyId);
		cpMediaTypeImpl.setUserId(userId);

		if (userName == null) {
			cpMediaTypeImpl.setUserName(StringPool.BLANK);
		}
		else {
			cpMediaTypeImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpMediaTypeImpl.setCreateDate(null);
		}
		else {
			cpMediaTypeImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpMediaTypeImpl.setModifiedDate(null);
		}
		else {
			cpMediaTypeImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (title == null) {
			cpMediaTypeImpl.setTitle(StringPool.BLANK);
		}
		else {
			cpMediaTypeImpl.setTitle(title);
		}

		if (description == null) {
			cpMediaTypeImpl.setDescription(StringPool.BLANK);
		}
		else {
			cpMediaTypeImpl.setDescription(description);
		}

		cpMediaTypeImpl.setPriority(priority);

		cpMediaTypeImpl.resetOriginalValues();

		return cpMediaTypeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPMediaTypeId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		title = objectInput.readUTF();
		description = objectInput.readUTF();

		priority = objectInput.readInt();
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

		objectOutput.writeLong(CPMediaTypeId);

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

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeInt(priority);
	}

	public String uuid;
	public long CPMediaTypeId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String title;
	public String description;
	public int priority;
}