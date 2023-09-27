/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model.impl;

import com.liferay.message.boards.model.MBDiscussion;
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
 * The cache model class for representing MBDiscussion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBDiscussionCacheModel
	implements CacheModel<MBDiscussion>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MBDiscussionCacheModel)) {
			return false;
		}

		MBDiscussionCacheModel mbDiscussionCacheModel =
			(MBDiscussionCacheModel)object;

		if ((discussionId == mbDiscussionCacheModel.discussionId) &&
			(mvccVersion == mbDiscussionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, discussionId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
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

		mbDiscussionImpl.setMvccVersion(mvccVersion);
		mbDiscussionImpl.setCtCollectionId(ctCollectionId);

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
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
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
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

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

	public long mvccVersion;
	public long ctCollectionId;
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