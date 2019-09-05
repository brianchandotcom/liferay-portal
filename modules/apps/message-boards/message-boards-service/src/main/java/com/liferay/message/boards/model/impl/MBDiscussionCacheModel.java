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

import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MBDiscussion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBDiscussionCacheModel
	implements CacheModel<MBDiscussion>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MBDiscussionCacheModel)) {
			return false;
		}

		MBDiscussionCacheModel mbDiscussionCacheModel =
			(MBDiscussionCacheModel)obj;

		if (discussionId == mbDiscussionCacheModel.discussionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, discussionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", discussionId=");
		sb.append(discussionId);
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
		sb.append(", threadId=");
		sb.append(threadId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MBDiscussion toEntityModel() {
		MBDiscussionImpl mbDiscussionImpl = new MBDiscussionImpl();

		if (uuid == null) {
			mbDiscussionImpl.setUuid("");
		}
		else {
			mbDiscussionImpl.setUuid(uuid);
		}

		mbDiscussionImpl.setDiscussionId(discussionId);
		mbDiscussionImpl.setGroupId(groupId);
		mbDiscussionImpl.setCompanyId(companyId);
		mbDiscussionImpl.setUserId(userId);

		if (userName == null) {
			mbDiscussionImpl.setUserName("");
		}
		else {
			mbDiscussionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mbDiscussionImpl.setCreateDate(null);
		}
		else {
			mbDiscussionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mbDiscussionImpl.setModifiedDate(null);
		}
		else {
			mbDiscussionImpl.setModifiedDate(new Date(modifiedDate));
		}

		mbDiscussionImpl.setClassNameId(classNameId);
		mbDiscussionImpl.setClassPK(classPK);
		mbDiscussionImpl.setThreadId(threadId);

		if (lastPublishDate == Long.MIN_VALUE) {
			mbDiscussionImpl.setLastPublishDate(null);
		}
		else {
			mbDiscussionImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		mbDiscussionImpl.resetOriginalValues();

		return mbDiscussionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		discussionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		threadId = objectInput.readLong();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(discussionId);

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

		objectOutput.writeLong(threadId);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long discussionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long threadId;
	public long lastPublishDate;

}