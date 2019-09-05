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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.document.library.kernel.model.DLFolder;
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
 * The cache model class for representing DLFolder in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFolderCacheModel
	implements CacheModel<DLFolder>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFolderCacheModel)) {
			return false;
		}

		DLFolderCacheModel dlFolderCacheModel = (DLFolderCacheModel)obj;

		if ((folderId == dlFolderCacheModel.folderId) &&
			(mvccVersion == dlFolderCacheModel.mvccVersion)) {

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
		StringBundler sb = new StringBundler(49);

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
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", mountPoint=");
		sb.append(mountPoint);
		sb.append(", parentFolderId=");
		sb.append(parentFolderId);
		sb.append(", treePath=");
		sb.append(treePath);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", lastPostDate=");
		sb.append(lastPostDate);
		sb.append(", defaultFileEntryTypeId=");
		sb.append(defaultFileEntryTypeId);
		sb.append(", hidden=");
		sb.append(hidden);
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
	public DLFolder toEntityModel() {
		DLFolderImpl dlFolderImpl = new DLFolderImpl();

		dlFolderImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			dlFolderImpl.setUuid("");
		}
		else {
			dlFolderImpl.setUuid(uuid);
		}

		dlFolderImpl.setFolderId(folderId);
		dlFolderImpl.setGroupId(groupId);
		dlFolderImpl.setCompanyId(companyId);
		dlFolderImpl.setUserId(userId);

		if (userName == null) {
			dlFolderImpl.setUserName("");
		}
		else {
			dlFolderImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			dlFolderImpl.setCreateDate(null);
		}
		else {
			dlFolderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			dlFolderImpl.setModifiedDate(null);
		}
		else {
			dlFolderImpl.setModifiedDate(new Date(modifiedDate));
		}

		dlFolderImpl.setRepositoryId(repositoryId);
		dlFolderImpl.setMountPoint(mountPoint);
		dlFolderImpl.setParentFolderId(parentFolderId);

		if (treePath == null) {
			dlFolderImpl.setTreePath("");
		}
		else {
			dlFolderImpl.setTreePath(treePath);
		}

		if (name == null) {
			dlFolderImpl.setName("");
		}
		else {
			dlFolderImpl.setName(name);
		}

		if (description == null) {
			dlFolderImpl.setDescription("");
		}
		else {
			dlFolderImpl.setDescription(description);
		}

		if (lastPostDate == Long.MIN_VALUE) {
			dlFolderImpl.setLastPostDate(null);
		}
		else {
			dlFolderImpl.setLastPostDate(new Date(lastPostDate));
		}

		dlFolderImpl.setDefaultFileEntryTypeId(defaultFileEntryTypeId);
		dlFolderImpl.setHidden(hidden);
		dlFolderImpl.setRestrictionType(restrictionType);

		if (lastPublishDate == Long.MIN_VALUE) {
			dlFolderImpl.setLastPublishDate(null);
		}
		else {
			dlFolderImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		dlFolderImpl.setStatus(status);
		dlFolderImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			dlFolderImpl.setStatusByUserName("");
		}
		else {
			dlFolderImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			dlFolderImpl.setStatusDate(null);
		}
		else {
			dlFolderImpl.setStatusDate(new Date(statusDate));
		}

		dlFolderImpl.resetOriginalValues();

		return dlFolderImpl;
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

		repositoryId = objectInput.readLong();

		mountPoint = objectInput.readBoolean();

		parentFolderId = objectInput.readLong();
		treePath = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		lastPostDate = objectInput.readLong();

		defaultFileEntryTypeId = objectInput.readLong();

		hidden = objectInput.readBoolean();

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

		objectOutput.writeLong(repositoryId);

		objectOutput.writeBoolean(mountPoint);

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

		objectOutput.writeLong(lastPostDate);

		objectOutput.writeLong(defaultFileEntryTypeId);

		objectOutput.writeBoolean(hidden);

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
	public long repositoryId;
	public boolean mountPoint;
	public long parentFolderId;
	public String treePath;
	public String name;
	public String description;
	public long lastPostDate;
	public long defaultFileEntryTypeId;
	public boolean hidden;
	public int restrictionType;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}