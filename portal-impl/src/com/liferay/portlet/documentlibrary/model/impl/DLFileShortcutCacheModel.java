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

import com.liferay.document.library.kernel.model.DLFileShortcut;
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
 * The cache model class for representing DLFileShortcut in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileShortcutCacheModel
	implements CacheModel<DLFileShortcut>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileShortcutCacheModel)) {
			return false;
		}

		DLFileShortcutCacheModel dlFileShortcutCacheModel =
			(DLFileShortcutCacheModel)obj;

		if ((fileShortcutId == dlFileShortcutCacheModel.fileShortcutId) &&
			(mvccVersion == dlFileShortcutCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fileShortcutId);

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
		sb.append(", fileShortcutId=");
		sb.append(fileShortcutId);
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
		sb.append(", folderId=");
		sb.append(folderId);
		sb.append(", toFileEntryId=");
		sb.append(toFileEntryId);
		sb.append(", treePath=");
		sb.append(treePath);
		sb.append(", active=");
		sb.append(active);
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
	public DLFileShortcut toEntityModel() {
		DLFileShortcutImpl dlFileShortcutImpl = new DLFileShortcutImpl();

		dlFileShortcutImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			dlFileShortcutImpl.setUuid("");
		}
		else {
			dlFileShortcutImpl.setUuid(uuid);
		}

		dlFileShortcutImpl.setFileShortcutId(fileShortcutId);
		dlFileShortcutImpl.setGroupId(groupId);
		dlFileShortcutImpl.setCompanyId(companyId);
		dlFileShortcutImpl.setUserId(userId);

		if (userName == null) {
			dlFileShortcutImpl.setUserName("");
		}
		else {
			dlFileShortcutImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			dlFileShortcutImpl.setCreateDate(null);
		}
		else {
			dlFileShortcutImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			dlFileShortcutImpl.setModifiedDate(null);
		}
		else {
			dlFileShortcutImpl.setModifiedDate(new Date(modifiedDate));
		}

		dlFileShortcutImpl.setRepositoryId(repositoryId);
		dlFileShortcutImpl.setFolderId(folderId);
		dlFileShortcutImpl.setToFileEntryId(toFileEntryId);

		if (treePath == null) {
			dlFileShortcutImpl.setTreePath("");
		}
		else {
			dlFileShortcutImpl.setTreePath(treePath);
		}

		dlFileShortcutImpl.setActive(active);

		if (lastPublishDate == Long.MIN_VALUE) {
			dlFileShortcutImpl.setLastPublishDate(null);
		}
		else {
			dlFileShortcutImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		dlFileShortcutImpl.setStatus(status);
		dlFileShortcutImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			dlFileShortcutImpl.setStatusByUserName("");
		}
		else {
			dlFileShortcutImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			dlFileShortcutImpl.setStatusDate(null);
		}
		else {
			dlFileShortcutImpl.setStatusDate(new Date(statusDate));
		}

		dlFileShortcutImpl.resetOriginalValues();

		return dlFileShortcutImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		fileShortcutId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		repositoryId = objectInput.readLong();

		folderId = objectInput.readLong();

		toFileEntryId = objectInput.readLong();
		treePath = objectInput.readUTF();

		active = objectInput.readBoolean();
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

		objectOutput.writeLong(fileShortcutId);

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

		objectOutput.writeLong(folderId);

		objectOutput.writeLong(toFileEntryId);

		if (treePath == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(treePath);
		}

		objectOutput.writeBoolean(active);
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
	public long fileShortcutId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long repositoryId;
	public long folderId;
	public long toFileEntryId;
	public String treePath;
	public boolean active;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}