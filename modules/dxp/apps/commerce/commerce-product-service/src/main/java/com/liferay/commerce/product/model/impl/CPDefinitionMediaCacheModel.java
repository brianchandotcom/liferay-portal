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

import com.liferay.commerce.product.model.CPDefinitionMedia;

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
 * The cache model class for representing CPDefinitionMedia in entity cache.
 *
 * @author Marco Leo
 * @see CPDefinitionMedia
 * @generated
 */
@ProviderType
public class CPDefinitionMediaCacheModel implements CacheModel<CPDefinitionMedia>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionMediaCacheModel)) {
			return false;
		}

		CPDefinitionMediaCacheModel cpDefinitionMediaCacheModel = (CPDefinitionMediaCacheModel)obj;

		if (CPDefinitionMediaId == cpDefinitionMediaCacheModel.CPDefinitionMediaId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionMediaId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionMediaId=");
		sb.append(CPDefinitionMediaId);
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
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", DDMContent=");
		sb.append(DDMContent);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", CPMediaTypeId=");
		sb.append(CPMediaTypeId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionMedia toEntityModel() {
		CPDefinitionMediaImpl cpDefinitionMediaImpl = new CPDefinitionMediaImpl();

		if (uuid == null) {
			cpDefinitionMediaImpl.setUuid(StringPool.BLANK);
		}
		else {
			cpDefinitionMediaImpl.setUuid(uuid);
		}

		cpDefinitionMediaImpl.setCPDefinitionMediaId(CPDefinitionMediaId);
		cpDefinitionMediaImpl.setGroupId(groupId);
		cpDefinitionMediaImpl.setCompanyId(companyId);
		cpDefinitionMediaImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionMediaImpl.setUserName(StringPool.BLANK);
		}
		else {
			cpDefinitionMediaImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionMediaImpl.setCreateDate(null);
		}
		else {
			cpDefinitionMediaImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionMediaImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionMediaImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpDefinitionMediaImpl.setCPDefinitionId(CPDefinitionId);
		cpDefinitionMediaImpl.setFileEntryId(fileEntryId);

		if (DDMContent == null) {
			cpDefinitionMediaImpl.setDDMContent(StringPool.BLANK);
		}
		else {
			cpDefinitionMediaImpl.setDDMContent(DDMContent);
		}

		cpDefinitionMediaImpl.setPriority(priority);
		cpDefinitionMediaImpl.setCPMediaTypeId(CPMediaTypeId);

		cpDefinitionMediaImpl.resetOriginalValues();

		return cpDefinitionMediaImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPDefinitionMediaId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		fileEntryId = objectInput.readLong();
		DDMContent = objectInput.readUTF();

		priority = objectInput.readInt();

		CPMediaTypeId = objectInput.readLong();
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

		objectOutput.writeLong(CPDefinitionMediaId);

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

		objectOutput.writeLong(CPDefinitionId);

		objectOutput.writeLong(fileEntryId);

		if (DDMContent == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(DDMContent);
		}

		objectOutput.writeInt(priority);

		objectOutput.writeLong(CPMediaTypeId);
	}

	public String uuid;
	public long CPDefinitionMediaId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public long fileEntryId;
	public String DDMContent;
	public int priority;
	public long CPMediaTypeId;
}