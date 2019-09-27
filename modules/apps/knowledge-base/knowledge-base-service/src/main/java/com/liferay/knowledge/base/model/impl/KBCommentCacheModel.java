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

package com.liferay.knowledge.base.model.impl;

import com.liferay.knowledge.base.model.KBComment;
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
 * The cache model class for representing KBComment in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KBCommentCacheModel
	implements CacheModel<KBComment>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KBCommentCacheModel)) {
			return false;
		}

		KBCommentCacheModel kbCommentCacheModel = (KBCommentCacheModel)obj;

		if ((kbCommentId == kbCommentCacheModel.kbCommentId) &&
			(mvccVersion == kbCommentCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kbCommentId);

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
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", kbCommentId=");
		sb.append(kbCommentId);
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
		sb.append(", content=");
		sb.append(content);
		sb.append(", userRating=");
		sb.append(userRating);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KBComment toEntityModel() {
		KBCommentImpl kbCommentImpl = new KBCommentImpl();

		kbCommentImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			kbCommentImpl.setUuid("");
		}
		else {
			kbCommentImpl.setUuid(uuid);
		}

		kbCommentImpl.setKbCommentId(kbCommentId);
		kbCommentImpl.setGroupId(groupId);
		kbCommentImpl.setCompanyId(companyId);
		kbCommentImpl.setUserId(userId);

		if (userName == null) {
			kbCommentImpl.setUserName("");
		}
		else {
			kbCommentImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kbCommentImpl.setCreateDate(null);
		}
		else {
			kbCommentImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kbCommentImpl.setModifiedDate(null);
		}
		else {
			kbCommentImpl.setModifiedDate(new Date(modifiedDate));
		}

		kbCommentImpl.setClassNameId(classNameId);
		kbCommentImpl.setClassPK(classPK);

		if (content == null) {
			kbCommentImpl.setContent("");
		}
		else {
			kbCommentImpl.setContent(content);
		}

		kbCommentImpl.setUserRating(userRating);

		if (lastPublishDate == Long.MIN_VALUE) {
			kbCommentImpl.setLastPublishDate(null);
		}
		else {
			kbCommentImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		kbCommentImpl.setStatus(status);

		kbCommentImpl.resetOriginalValues();

		return kbCommentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		kbCommentId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		content = objectInput.readUTF();

		userRating = objectInput.readInt();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(kbCommentId);

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

		if (content == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(content);
		}

		objectOutput.writeInt(userRating);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public String uuid;
	public long kbCommentId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String content;
	public int userRating;
	public long lastPublishDate;
	public int status;

}