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

import com.liferay.document.library.kernel.model.DLFileEntry;
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
 * The cache model class for representing DLFileEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryCacheModel
	implements CacheModel<DLFileEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileEntryCacheModel)) {
			return false;
		}

		DLFileEntryCacheModel dlFileEntryCacheModel =
			(DLFileEntryCacheModel)obj;

		if ((fileEntryId == dlFileEntryCacheModel.fileEntryId) &&
			(mvccVersion == dlFileEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fileEntryId);

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
		StringBundler sb = new StringBundler(63);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
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
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", folderId=");
		sb.append(folderId);
		sb.append(", treePath=");
		sb.append(treePath);
		sb.append(", name=");
		sb.append(name);
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
		sb.append(", extraSettings=");
		sb.append(extraSettings);
		sb.append(", fileEntryTypeId=");
		sb.append(fileEntryTypeId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", size=");
		sb.append(size);
		sb.append(", readCount=");
		sb.append(readCount);
		sb.append(", smallImageId=");
		sb.append(smallImageId);
		sb.append(", largeImageId=");
		sb.append(largeImageId);
		sb.append(", custom1ImageId=");
		sb.append(custom1ImageId);
		sb.append(", custom2ImageId=");
		sb.append(custom2ImageId);
		sb.append(", manualCheckInRequired=");
		sb.append(manualCheckInRequired);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DLFileEntry toEntityModel() {
		DLFileEntryImpl dlFileEntryImpl = new DLFileEntryImpl();

		dlFileEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			dlFileEntryImpl.setUuid("");
		}
		else {
			dlFileEntryImpl.setUuid(uuid);
		}

		dlFileEntryImpl.setFileEntryId(fileEntryId);
		dlFileEntryImpl.setGroupId(groupId);
		dlFileEntryImpl.setCompanyId(companyId);
		dlFileEntryImpl.setUserId(userId);

		if (userName == null) {
			dlFileEntryImpl.setUserName("");
		}
		else {
			dlFileEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			dlFileEntryImpl.setCreateDate(null);
		}
		else {
			dlFileEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			dlFileEntryImpl.setModifiedDate(null);
		}
		else {
			dlFileEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		dlFileEntryImpl.setClassNameId(classNameId);
		dlFileEntryImpl.setClassPK(classPK);
		dlFileEntryImpl.setRepositoryId(repositoryId);
		dlFileEntryImpl.setFolderId(folderId);

		if (treePath == null) {
			dlFileEntryImpl.setTreePath("");
		}
		else {
			dlFileEntryImpl.setTreePath(treePath);
		}

		if (name == null) {
			dlFileEntryImpl.setName("");
		}
		else {
			dlFileEntryImpl.setName(name);
		}

		if (fileName == null) {
			dlFileEntryImpl.setFileName("");
		}
		else {
			dlFileEntryImpl.setFileName(fileName);
		}

		if (extension == null) {
			dlFileEntryImpl.setExtension("");
		}
		else {
			dlFileEntryImpl.setExtension(extension);
		}

		if (mimeType == null) {
			dlFileEntryImpl.setMimeType("");
		}
		else {
			dlFileEntryImpl.setMimeType(mimeType);
		}

		if (title == null) {
			dlFileEntryImpl.setTitle("");
		}
		else {
			dlFileEntryImpl.setTitle(title);
		}

		if (description == null) {
			dlFileEntryImpl.setDescription("");
		}
		else {
			dlFileEntryImpl.setDescription(description);
		}

		if (extraSettings == null) {
			dlFileEntryImpl.setExtraSettings("");
		}
		else {
			dlFileEntryImpl.setExtraSettings(extraSettings);
		}

		dlFileEntryImpl.setFileEntryTypeId(fileEntryTypeId);

		if (version == null) {
			dlFileEntryImpl.setVersion("");
		}
		else {
			dlFileEntryImpl.setVersion(version);
		}

		dlFileEntryImpl.setSize(size);
		dlFileEntryImpl.setReadCount(readCount);
		dlFileEntryImpl.setSmallImageId(smallImageId);
		dlFileEntryImpl.setLargeImageId(largeImageId);
		dlFileEntryImpl.setCustom1ImageId(custom1ImageId);
		dlFileEntryImpl.setCustom2ImageId(custom2ImageId);
		dlFileEntryImpl.setManualCheckInRequired(manualCheckInRequired);

		if (lastPublishDate == Long.MIN_VALUE) {
			dlFileEntryImpl.setLastPublishDate(null);
		}
		else {
			dlFileEntryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		dlFileEntryImpl.resetOriginalValues();

		return dlFileEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		fileEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		repositoryId = objectInput.readLong();

		folderId = objectInput.readLong();
		treePath = objectInput.readUTF();
		name = objectInput.readUTF();
		fileName = objectInput.readUTF();
		extension = objectInput.readUTF();
		mimeType = objectInput.readUTF();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		extraSettings = objectInput.readUTF();

		fileEntryTypeId = objectInput.readLong();
		version = objectInput.readUTF();

		size = objectInput.readLong();

		readCount = objectInput.readInt();

		smallImageId = objectInput.readLong();

		largeImageId = objectInput.readLong();

		custom1ImageId = objectInput.readLong();

		custom2ImageId = objectInput.readLong();

		manualCheckInRequired = objectInput.readBoolean();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(fileEntryId);

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

		objectOutput.writeLong(repositoryId);

		objectOutput.writeLong(folderId);

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

		objectOutput.writeInt(readCount);

		objectOutput.writeLong(smallImageId);

		objectOutput.writeLong(largeImageId);

		objectOutput.writeLong(custom1ImageId);

		objectOutput.writeLong(custom2ImageId);

		objectOutput.writeBoolean(manualCheckInRequired);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long fileEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long repositoryId;
	public long folderId;
	public String treePath;
	public String name;
	public String fileName;
	public String extension;
	public String mimeType;
	public String title;
	public String description;
	public String extraSettings;
	public long fileEntryTypeId;
	public String version;
	public long size;
	public int readCount;
	public long smallImageId;
	public long largeImageId;
	public long custom1ImageId;
	public long custom2ImageId;
	public boolean manualCheckInRequired;
	public long lastPublishDate;

}