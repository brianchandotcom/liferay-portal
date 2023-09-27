/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model.impl;

import com.liferay.message.boards.model.MBThread;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

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
	implements CacheModel<MBThread>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MBThreadCacheModel)) {
			return false;
		}

		MBThreadCacheModel mbThreadCacheModel = (MBThreadCacheModel)object;

		if ((threadId == mbThreadCacheModel.threadId) &&
			(mvccVersion == mbThreadCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, threadId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(47);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
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

		mbThreadImpl.setMvccVersion(mvccVersion);
		mbThreadImpl.setCtCollectionId(ctCollectionId);

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
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
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
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

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

	public long mvccVersion;
	public long ctCollectionId;
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