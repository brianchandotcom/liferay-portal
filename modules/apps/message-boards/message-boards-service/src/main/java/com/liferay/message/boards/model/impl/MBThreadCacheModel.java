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

package com.liferay.message.boards.model.impl;

import com.liferay.message.boards.model.MBThread;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MBThread in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBThreadCacheModel
	implements CacheModel<MBThread>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MBThreadCacheModel)) {
			return false;
		}

		MBThreadCacheModel mbThreadCacheModel = (MBThreadCacheModel)obj;

		if (threadId == mbThreadCacheModel.threadId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, threadId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(47);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", threadId=");
		sb.append(threadId);
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
		sb.append(", categoryId=");
		sb.append(categoryId);
		sb.append(", rootMessageId=");
		sb.append(rootMessageId);
		sb.append(", rootMessageUserId=");
		sb.append(rootMessageUserId);
		sb.append(", title=");
		sb.append(title);
		sb.append(", messageCount=");
		sb.append(messageCount);
		sb.append(", viewCount=");
		sb.append(viewCount);
		sb.append(", lastPostByUserId=");
		sb.append(lastPostByUserId);
		sb.append(", lastPostDate=");
		sb.append(lastPostDate);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", question=");
		sb.append(question);
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
	public MBThread toEntityModel() {
		MBThreadImpl mbThreadImpl = new MBThreadImpl();

		if (uuid == null) {
			mbThreadImpl.setUuid("");
		}
		else {
			mbThreadImpl.setUuid(uuid);
		}

		mbThreadImpl.setThreadId(threadId);
		mbThreadImpl.setGroupId(groupId);
		mbThreadImpl.setCompanyId(companyId);
		mbThreadImpl.setUserId(userId);

		if (userName == null) {
			mbThreadImpl.setUserName("");
		}
		else {
			mbThreadImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mbThreadImpl.setCreateDate(null);
		}
		else {
			mbThreadImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mbThreadImpl.setModifiedDate(null);
		}
		else {
			mbThreadImpl.setModifiedDate(new Date(modifiedDate));
		}

		mbThreadImpl.setCategoryId(categoryId);
		mbThreadImpl.setRootMessageId(rootMessageId);
		mbThreadImpl.setRootMessageUserId(rootMessageUserId);

		if (title == null) {
			mbThreadImpl.setTitle("");
		}
		else {
			mbThreadImpl.setTitle(title);
		}

		mbThreadImpl.setMessageCount(messageCount);
		mbThreadImpl.setViewCount(viewCount);
		mbThreadImpl.setLastPostByUserId(lastPostByUserId);

		if (lastPostDate == Long.MIN_VALUE) {
			mbThreadImpl.setLastPostDate(null);
		}
		else {
			mbThreadImpl.setLastPostDate(new Date(lastPostDate));
		}

		mbThreadImpl.setPriority(priority);
		mbThreadImpl.setQuestion(question);

		if (lastPublishDate == Long.MIN_VALUE) {
			mbThreadImpl.setLastPublishDate(null);
		}
		else {
			mbThreadImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		mbThreadImpl.setStatus(status);
		mbThreadImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			mbThreadImpl.setStatusByUserName("");
		}
		else {
			mbThreadImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			mbThreadImpl.setStatusDate(null);
		}
		else {
			mbThreadImpl.setStatusDate(new Date(statusDate));
		}

		mbThreadImpl.resetOriginalValues();

		return mbThreadImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		threadId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		categoryId = objectInput.readLong();

		rootMessageId = objectInput.readLong();

		rootMessageUserId = objectInput.readLong();
		title = objectInput.readUTF();

		messageCount = objectInput.readInt();

		viewCount = objectInput.readInt();

		lastPostByUserId = objectInput.readLong();
		lastPostDate = objectInput.readLong();

		priority = objectInput.readDouble();

		question = objectInput.readBoolean();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(threadId);

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

		objectOutput.writeLong(categoryId);

		objectOutput.writeLong(rootMessageId);

		objectOutput.writeLong(rootMessageUserId);

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		objectOutput.writeInt(messageCount);

		objectOutput.writeInt(viewCount);

		objectOutput.writeLong(lastPostByUserId);
		objectOutput.writeLong(lastPostDate);

		objectOutput.writeDouble(priority);

		objectOutput.writeBoolean(question);
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
	public long threadId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long categoryId;
	public long rootMessageId;
	public long rootMessageUserId;
	public String title;
	public int messageCount;
	public int viewCount;
	public long lastPostByUserId;
	public long lastPostDate;
	public double priority;
	public boolean question;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}