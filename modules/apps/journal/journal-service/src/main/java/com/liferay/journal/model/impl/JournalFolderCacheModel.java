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

package com.liferay.journal.model.impl;

import com.liferay.journal.model.JournalFolder;
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
 * The cache model class for representing JournalFolder in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class JournalFolderCacheModel
	implements CacheModel<JournalFolder>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalFolderCacheModel)) {
			return false;
		}

		JournalFolderCacheModel journalFolderCacheModel =
			(JournalFolderCacheModel)obj;

		if ((folderId == journalFolderCacheModel.folderId) &&
			(mvccVersion == journalFolderCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, folderId);

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
		StringBundler sb = new StringBundler(39);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", folderId=");
		sb.append(folderId);
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
		sb.append(", parentFolderId=");
		sb.append(parentFolderId);
		sb.append(", treePath=");
		sb.append(treePath);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", restrictionType=");
		sb.append(restrictionType);
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
	public JournalFolder toEntityModel() {
		JournalFolderImpl journalFolderImpl = new JournalFolderImpl();

		journalFolderImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			journalFolderImpl.setUuid("");
		}
		else {
			journalFolderImpl.setUuid(uuid);
		}

		journalFolderImpl.setFolderId(folderId);
		journalFolderImpl.setGroupId(groupId);
		journalFolderImpl.setCompanyId(companyId);
		journalFolderImpl.setUserId(userId);

		if (userName == null) {
			journalFolderImpl.setUserName("");
		}
		else {
			journalFolderImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			journalFolderImpl.setCreateDate(null);
		}
		else {
			journalFolderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			journalFolderImpl.setModifiedDate(null);
		}
		else {
			journalFolderImpl.setModifiedDate(new Date(modifiedDate));
		}

		journalFolderImpl.setParentFolderId(parentFolderId);

		if (treePath == null) {
			journalFolderImpl.setTreePath("");
		}
		else {
			journalFolderImpl.setTreePath(treePath);
		}

		if (name == null) {
			journalFolderImpl.setName("");
		}
		else {
			journalFolderImpl.setName(name);
		}

		if (description == null) {
			journalFolderImpl.setDescription("");
		}
		else {
			journalFolderImpl.setDescription(description);
		}

		journalFolderImpl.setRestrictionType(restrictionType);

		if (lastPublishDate == Long.MIN_VALUE) {
			journalFolderImpl.setLastPublishDate(null);
		}
		else {
			journalFolderImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		journalFolderImpl.setStatus(status);
		journalFolderImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			journalFolderImpl.setStatusByUserName("");
		}
		else {
			journalFolderImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			journalFolderImpl.setStatusDate(null);
		}
		else {
			journalFolderImpl.setStatusDate(new Date(statusDate));
		}

		journalFolderImpl.resetOriginalValues();

		return journalFolderImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		folderId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		parentFolderId = objectInput.readLong();
		treePath = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		restrictionType = objectInput.readInt();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
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

		objectOutput.writeLong(folderId);

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

		objectOutput.writeLong(parentFolderId);

		if (treePath == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(treePath);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeInt(restrictionType);
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
	public String uuid;
	public long folderId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentFolderId;
	public String treePath;
	public String name;
	public String description;
	public int restrictionType;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}