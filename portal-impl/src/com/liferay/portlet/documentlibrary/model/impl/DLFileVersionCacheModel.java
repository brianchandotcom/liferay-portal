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

import com.liferay.document.library.kernel.model.DLFileVersion;
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
 * The cache model class for representing DLFileVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileVersionCacheModel
	implements CacheModel<DLFileVersion>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileVersionCacheModel)) {
			return false;
		}

		DLFileVersionCacheModel dlFileVersionCacheModel =
			(DLFileVersionCacheModel)obj;

		if ((fileVersionId == dlFileVersionCacheModel.fileVersionId) &&
			(mvccVersion == dlFileVersionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fileVersionId);

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
		StringBundler sb = new StringBundler(59);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", fileVersionId=");
		sb.append(fileVersionId);
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
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", treePath=");
		sb.append(treePath);
		sb.append(", fileName=");
		sb.append(fileName);
		sb.append(", extension=");
		sb.append(extension);
		sb.append(", mimeType=");
		sb.append(mimeType);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", changeLog=");
		sb.append(changeLog);
		sb.append(", extraSettings=");
		sb.append(extraSettings);
		sb.append(", fileEntryTypeId=");
		sb.append(fileEntryTypeId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", size=");
		sb.append(size);
		sb.append(", checksum=");
		sb.append(checksum);
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
	public DLFileVersion toEntityModel() {
		DLFileVersionImpl dlFileVersionImpl = new DLFileVersionImpl();

		dlFileVersionImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			dlFileVersionImpl.setUuid("");
		}
		else {
			dlFileVersionImpl.setUuid(uuid);
		}

		dlFileVersionImpl.setFileVersionId(fileVersionId);
		dlFileVersionImpl.setGroupId(groupId);
		dlFileVersionImpl.setCompanyId(companyId);
		dlFileVersionImpl.setUserId(userId);

		if (userName == null) {
			dlFileVersionImpl.setUserName("");
		}
		else {
			dlFileVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			dlFileVersionImpl.setCreateDate(null);
		}
		else {
			dlFileVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			dlFileVersionImpl.setModifiedDate(null);
		}
		else {
			dlFileVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		dlFileVersionImpl.setRepositoryId(repositoryId);
		dlFileVersionImpl.setFolderId(folderId);
		dlFileVersionImpl.setFileEntryId(fileEntryId);

		if (treePath == null) {
			dlFileVersionImpl.setTreePath("");
		}
		else {
			dlFileVersionImpl.setTreePath(treePath);
		}

		if (fileName == null) {
			dlFileVersionImpl.setFileName("");
		}
		else {
			dlFileVersionImpl.setFileName(fileName);
		}

		if (extension == null) {
			dlFileVersionImpl.setExtension("");
		}
		else {
			dlFileVersionImpl.setExtension(extension);
		}

		if (mimeType == null) {
			dlFileVersionImpl.setMimeType("");
		}
		else {
			dlFileVersionImpl.setMimeType(mimeType);
		}

		if (title == null) {
			dlFileVersionImpl.setTitle("");
		}
		else {
			dlFileVersionImpl.setTitle(title);
		}

		if (description == null) {
			dlFileVersionImpl.setDescription("");
		}
		else {
			dlFileVersionImpl.setDescription(description);
		}

		if (changeLog == null) {
			dlFileVersionImpl.setChangeLog("");
		}
		else {
			dlFileVersionImpl.setChangeLog(changeLog);
		}

		if (extraSettings == null) {
			dlFileVersionImpl.setExtraSettings("");
		}
		else {
			dlFileVersionImpl.setExtraSettings(extraSettings);
		}

		dlFileVersionImpl.setFileEntryTypeId(fileEntryTypeId);

		if (version == null) {
			dlFileVersionImpl.setVersion("");
		}
		else {
			dlFileVersionImpl.setVersion(version);
		}

		dlFileVersionImpl.setSize(size);

		if (checksum == null) {
			dlFileVersionImpl.setChecksum("");
		}
		else {
			dlFileVersionImpl.setChecksum(checksum);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			dlFileVersionImpl.setLastPublishDate(null);
		}
		else {
			dlFileVersionImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		dlFileVersionImpl.setStatus(status);
		dlFileVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			dlFileVersionImpl.setStatusByUserName("");
		}
		else {
			dlFileVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			dlFileVersionImpl.setStatusDate(null);
		}
		else {
			dlFileVersionImpl.setStatusDate(new Date(statusDate));
		}

		dlFileVersionImpl.resetOriginalValues();

		return dlFileVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		fileVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		repositoryId = objectInput.readLong();

		folderId = objectInput.readLong();

		fileEntryId = objectInput.readLong();
		treePath = objectInput.readUTF();
		fileName = objectInput.readUTF();
		extension = objectInput.readUTF();
		mimeType = objectInput.readUTF();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		changeLog = objectInput.readUTF();
		extraSettings = objectInput.readUTF();

		fileEntryTypeId = objectInput.readLong();
		version = objectInput.readUTF();

		size = objectInput.readLong();
		checksum = objectInput.readUTF();
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

		objectOutput.writeLong(fileVersionId);

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

		objectOutput.writeLong(fileEntryId);

		if (treePath == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(treePath);
		}

		if (fileName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fileName);
		}

		if (extension == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(extension);
		}

		if (mimeType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(mimeType);
		}

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (changeLog == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(changeLog);
		}

		if (extraSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(extraSettings);
		}

		objectOutput.writeLong(fileEntryTypeId);

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeLong(size);

		if (checksum == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(checksum);
		}

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
	public long fileVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long repositoryId;
	public long folderId;
	public long fileEntryId;
	public String treePath;
	public String fileName;
	public String extension;
	public String mimeType;
	public String title;
	public String description;
	public String changeLog;
	public String extraSettings;
	public long fileEntryTypeId;
	public String version;
	public long size;
	public String checksum;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}