/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.messageboards.model.MBCategory;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MBCategory in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see MBCategory
 * @generated
 */
public class MBCategoryCacheModel implements CacheModel<MBCategory>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(39);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", categoryId=");
		sb.append(categoryId);
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
		sb.append(", parentCategoryId=");
		sb.append(parentCategoryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", displayStyle=");
		sb.append(displayStyle);
		sb.append(", threadCount=");
		sb.append(threadCount);
		sb.append(", messageCount=");
		sb.append(messageCount);
		sb.append(", lastPostDate=");
		sb.append(lastPostDate);
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

	public MBCategory toEntityModel() {
		MBCategoryImpl mbCategoryImpl = new MBCategoryImpl();

		if (uuid == null) {
			mbCategoryImpl.setUuid(StringPool.BLANK);
		}
		else {
			mbCategoryImpl.setUuid(uuid);
		}

		mbCategoryImpl.setCategoryId(categoryId);
		mbCategoryImpl.setGroupId(groupId);
		mbCategoryImpl.setCompanyId(companyId);
		mbCategoryImpl.setUserId(userId);

		if (userName == null) {
			mbCategoryImpl.setUserName(StringPool.BLANK);
		}
		else {
			mbCategoryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mbCategoryImpl.setCreateDate(null);
		}
		else {
			mbCategoryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mbCategoryImpl.setModifiedDate(null);
		}
		else {
			mbCategoryImpl.setModifiedDate(new Date(modifiedDate));
		}

		mbCategoryImpl.setParentCategoryId(parentCategoryId);

		if (name == null) {
			mbCategoryImpl.setName(StringPool.BLANK);
		}
		else {
			mbCategoryImpl.setName(name);
		}

		if (description == null) {
			mbCategoryImpl.setDescription(StringPool.BLANK);
		}
		else {
			mbCategoryImpl.setDescription(description);
		}

		if (displayStyle == null) {
			mbCategoryImpl.setDisplayStyle(StringPool.BLANK);
		}
		else {
			mbCategoryImpl.setDisplayStyle(displayStyle);
		}

		mbCategoryImpl.setThreadCount(threadCount);
		mbCategoryImpl.setMessageCount(messageCount);

		if (lastPostDate == Long.MIN_VALUE) {
			mbCategoryImpl.setLastPostDate(null);
		}
		else {
			mbCategoryImpl.setLastPostDate(new Date(lastPostDate));
		}

		mbCategoryImpl.setStatus(status);
		mbCategoryImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			mbCategoryImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			mbCategoryImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			mbCategoryImpl.setStatusDate(null);
		}
		else {
			mbCategoryImpl.setStatusDate(new Date(statusDate));
		}

		mbCategoryImpl.resetOriginalValues();

		return mbCategoryImpl;
	}

	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		categoryId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		parentCategoryId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		displayStyle = objectInput.readUTF();
		threadCount = objectInput.readInt();
		messageCount = objectInput.readInt();
		lastPostDate = objectInput.readLong();
		status = objectInput.readInt();
		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(categoryId);
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
		objectOutput.writeLong(parentCategoryId);

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

		if (displayStyle == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(displayStyle);
		}

		objectOutput.writeInt(threadCount);
		objectOutput.writeInt(messageCount);
		objectOutput.writeLong(lastPostDate);
		objectOutput.writeInt(status);
		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public String uuid;
	public long categoryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentCategoryId;
	public String name;
	public String description;
	public String displayStyle;
	public int threadCount;
	public int messageCount;
	public long lastPostDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}