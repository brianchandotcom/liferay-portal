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

import com.liferay.commerce.product.model.CPAttachmentFileEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPAttachmentFileEntry in entity cache.
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntry
 * @generated
 */
@ProviderType
public class CPAttachmentFileEntryCacheModel implements CacheModel<CPAttachmentFileEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPAttachmentFileEntryCacheModel)) {
			return false;
		}

		CPAttachmentFileEntryCacheModel cpAttachmentFileEntryCacheModel = (CPAttachmentFileEntryCacheModel)obj;

		if (CPAttachmentFileEntryId == cpAttachmentFileEntryCacheModel.CPAttachmentFileEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPAttachmentFileEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(45);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPAttachmentFileEntryId=");
		sb.append(CPAttachmentFileEntryId);
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
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", title=");
		sb.append(title);
		sb.append(", json=");
		sb.append(json);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", type=");
		sb.append(type);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPAttachmentFileEntry toEntityModel() {
		CPAttachmentFileEntryImpl cpAttachmentFileEntryImpl = new CPAttachmentFileEntryImpl();

		if (uuid == null) {
			cpAttachmentFileEntryImpl.setUuid("");
		}
		else {
			cpAttachmentFileEntryImpl.setUuid(uuid);
		}

		cpAttachmentFileEntryImpl.setCPAttachmentFileEntryId(CPAttachmentFileEntryId);
		cpAttachmentFileEntryImpl.setGroupId(groupId);
		cpAttachmentFileEntryImpl.setCompanyId(companyId);
		cpAttachmentFileEntryImpl.setUserId(userId);

		if (userName == null) {
			cpAttachmentFileEntryImpl.setUserName("");
		}
		else {
			cpAttachmentFileEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpAttachmentFileEntryImpl.setCreateDate(null);
		}
		else {
			cpAttachmentFileEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpAttachmentFileEntryImpl.setModifiedDate(null);
		}
		else {
			cpAttachmentFileEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpAttachmentFileEntryImpl.setClassNameId(classNameId);
		cpAttachmentFileEntryImpl.setClassPK(classPK);
		cpAttachmentFileEntryImpl.setFileEntryId(fileEntryId);

		if (displayDate == Long.MIN_VALUE) {
			cpAttachmentFileEntryImpl.setDisplayDate(null);
		}
		else {
			cpAttachmentFileEntryImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			cpAttachmentFileEntryImpl.setExpirationDate(null);
		}
		else {
			cpAttachmentFileEntryImpl.setExpirationDate(new Date(expirationDate));
		}

		if (title == null) {
			cpAttachmentFileEntryImpl.setTitle("");
		}
		else {
			cpAttachmentFileEntryImpl.setTitle(title);
		}

		if (json == null) {
			cpAttachmentFileEntryImpl.setJson("");
		}
		else {
			cpAttachmentFileEntryImpl.setJson(json);
		}

		cpAttachmentFileEntryImpl.setPriority(priority);
		cpAttachmentFileEntryImpl.setType(type);

		if (lastPublishDate == Long.MIN_VALUE) {
			cpAttachmentFileEntryImpl.setLastPublishDate(null);
		}
		else {
			cpAttachmentFileEntryImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		cpAttachmentFileEntryImpl.setStatus(status);
		cpAttachmentFileEntryImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			cpAttachmentFileEntryImpl.setStatusByUserName("");
		}
		else {
			cpAttachmentFileEntryImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			cpAttachmentFileEntryImpl.setStatusDate(null);
		}
		else {
			cpAttachmentFileEntryImpl.setStatusDate(new Date(statusDate));
		}

		cpAttachmentFileEntryImpl.resetOriginalValues();

		return cpAttachmentFileEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPAttachmentFileEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		fileEntryId = objectInput.readLong();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		title = objectInput.readUTF();
		json = objectInput.readUTF();

		priority = objectInput.readDouble();

		type = objectInput.readInt();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPAttachmentFileEntryId);

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

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(fileEntryId);
		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (json == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(json);
		}

		objectOutput.writeDouble(priority);

		objectOutput.writeInt(type);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public String uuid;
	public long CPAttachmentFileEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long fileEntryId;
	public long displayDate;
	public long expirationDate;
	public String title;
	public String json;
	public double priority;
	public int type;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}