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

import com.liferay.message.boards.model.MBMessage;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MBMessage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBMessageCacheModel
	implements CacheModel<MBMessage>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MBMessageCacheModel)) {
			return false;
		}

		MBMessageCacheModel mbMessageCacheModel = (MBMessageCacheModel)obj;

		if (messageId == mbMessageCacheModel.messageId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, messageId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(55);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", messageId=");
		sb.append(messageId);
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
		sb.append(", categoryId=");
		sb.append(categoryId);
		sb.append(", threadId=");
		sb.append(threadId);
		sb.append(", rootMessageId=");
		sb.append(rootMessageId);
		sb.append(", parentMessageId=");
		sb.append(parentMessageId);
		sb.append(", treePath=");
		sb.append(treePath);
		sb.append(", subject=");
		sb.append(subject);
		sb.append(", body=");
		sb.append(body);
		sb.append(", format=");
		sb.append(format);
		sb.append(", anonymous=");
		sb.append(anonymous);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", allowPingbacks=");
		sb.append(allowPingbacks);
		sb.append(", answer=");
		sb.append(answer);
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
	public MBMessage toEntityModel() {
		MBMessageImpl mbMessageImpl = new MBMessageImpl();

		if (uuid == null) {
			mbMessageImpl.setUuid("");
		}
		else {
			mbMessageImpl.setUuid(uuid);
		}

		mbMessageImpl.setMessageId(messageId);
		mbMessageImpl.setGroupId(groupId);
		mbMessageImpl.setCompanyId(companyId);
		mbMessageImpl.setUserId(userId);

		if (userName == null) {
			mbMessageImpl.setUserName("");
		}
		else {
			mbMessageImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mbMessageImpl.setCreateDate(null);
		}
		else {
			mbMessageImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mbMessageImpl.setModifiedDate(null);
		}
		else {
			mbMessageImpl.setModifiedDate(new Date(modifiedDate));
		}

		mbMessageImpl.setClassNameId(classNameId);
		mbMessageImpl.setClassPK(classPK);
		mbMessageImpl.setCategoryId(categoryId);
		mbMessageImpl.setThreadId(threadId);
		mbMessageImpl.setRootMessageId(rootMessageId);
		mbMessageImpl.setParentMessageId(parentMessageId);

		if (treePath == null) {
			mbMessageImpl.setTreePath("");
		}
		else {
			mbMessageImpl.setTreePath(treePath);
		}

		if (subject == null) {
			mbMessageImpl.setSubject("");
		}
		else {
			mbMessageImpl.setSubject(subject);
		}

		if (body == null) {
			mbMessageImpl.setBody("");
		}
		else {
			mbMessageImpl.setBody(body);
		}

		if (format == null) {
			mbMessageImpl.setFormat("");
		}
		else {
			mbMessageImpl.setFormat(format);
		}

		mbMessageImpl.setAnonymous(anonymous);
		mbMessageImpl.setPriority(priority);
		mbMessageImpl.setAllowPingbacks(allowPingbacks);
		mbMessageImpl.setAnswer(answer);

		if (lastPublishDate == Long.MIN_VALUE) {
			mbMessageImpl.setLastPublishDate(null);
		}
		else {
			mbMessageImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		mbMessageImpl.setStatus(status);
		mbMessageImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			mbMessageImpl.setStatusByUserName("");
		}
		else {
			mbMessageImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			mbMessageImpl.setStatusDate(null);
		}
		else {
			mbMessageImpl.setStatusDate(new Date(statusDate));
		}

		mbMessageImpl.resetOriginalValues();

		return mbMessageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		messageId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		categoryId = objectInput.readLong();

		threadId = objectInput.readLong();

		rootMessageId = objectInput.readLong();

		parentMessageId = objectInput.readLong();
		treePath = objectInput.readUTF();
		subject = objectInput.readUTF();
		body = objectInput.readUTF();
		format = objectInput.readUTF();

		anonymous = objectInput.readBoolean();

		priority = objectInput.readDouble();

		allowPingbacks = objectInput.readBoolean();

		answer = objectInput.readBoolean();
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

		objectOutput.writeLong(messageId);

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

		objectOutput.writeLong(categoryId);

		objectOutput.writeLong(threadId);

		objectOutput.writeLong(rootMessageId);

		objectOutput.writeLong(parentMessageId);

		if (treePath == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(treePath);
		}

		if (subject == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(subject);
		}

		if (body == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(body);
		}

		if (format == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(format);
		}

		objectOutput.writeBoolean(anonymous);

		objectOutput.writeDouble(priority);

		objectOutput.writeBoolean(allowPingbacks);

		objectOutput.writeBoolean(answer);
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
	public long messageId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long categoryId;
	public long threadId;
	public long rootMessageId;
	public long parentMessageId;
	public String treePath;
	public String subject;
	public String body;
	public String format;
	public boolean anonymous;
	public double priority;
	public boolean allowPingbacks;
	public boolean answer;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}