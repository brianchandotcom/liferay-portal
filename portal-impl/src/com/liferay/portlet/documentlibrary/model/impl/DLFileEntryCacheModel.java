/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing DLFileEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntry
 * @generated
 */
public class DLFileEntryCacheModel implements CacheModel<DLFileEntry>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(53);

		sb.append("{uuid=");
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
		sb.append(", versionUserId=");
		sb.append(versionUserId);
		sb.append(", versionUserName=");
		sb.append(versionUserName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", folderId=");
		sb.append(folderId);
		sb.append(", name=");
		sb.append(name);
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
		sb.append("}");

		return sb.toString();
	}

	public DLFileEntry toEntityModel() {
		DLFileEntryImpl dlFileEntryImpl = new DLFileEntryImpl();

		if (uuid == null) {
			dlFileEntryImpl.setUuid(StringPool.BLANK);
		}
		else {
			dlFileEntryImpl.setUuid(uuid);
		}

		dlFileEntryImpl.setFileEntryId(fileEntryId);
		dlFileEntryImpl.setGroupId(groupId);
		dlFileEntryImpl.setCompanyId(companyId);
		dlFileEntryImpl.setUserId(userId);

		if (userName == null) {
			dlFileEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			dlFileEntryImpl.setUserName(userName);
		}

		dlFileEntryImpl.setVersionUserId(versionUserId);

		if (versionUserName == null) {
			dlFileEntryImpl.setVersionUserName(StringPool.BLANK);
		}
		else {
			dlFileEntryImpl.setVersionUserName(versionUserName);
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

		dlFileEntryImpl.setRepositoryId(repositoryId);
		dlFileEntryImpl.setFolderId(folderId);

		if (name == null) {
			dlFileEntryImpl.setName(StringPool.BLANK);
		}
		else {
			dlFileEntryImpl.setName(name);
		}

		if (extension == null) {
			dlFileEntryImpl.setExtension(StringPool.BLANK);
		}
		else {
			dlFileEntryImpl.setExtension(extension);
		}

		if (mimeType == null) {
			dlFileEntryImpl.setMimeType(StringPool.BLANK);
		}
		else {
			dlFileEntryImpl.setMimeType(mimeType);
		}

		if (title == null) {
			dlFileEntryImpl.setTitle(StringPool.BLANK);
		}
		else {
			dlFileEntryImpl.setTitle(title);
		}

		if (description == null) {
			dlFileEntryImpl.setDescription(StringPool.BLANK);
		}
		else {
			dlFileEntryImpl.setDescription(description);
		}

		if (extraSettings == null) {
			dlFileEntryImpl.setExtraSettings(StringPool.BLANK);
		}
		else {
			dlFileEntryImpl.setExtraSettings(extraSettings);
		}

		dlFileEntryImpl.setFileEntryTypeId(fileEntryTypeId);

		if (version == null) {
			dlFileEntryImpl.setVersion(StringPool.BLANK);
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

		dlFileEntryImpl.resetOriginalValues();

		return dlFileEntryImpl;
	}

	public String uuid;
	public long fileEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long versionUserId;
	public String versionUserName;
	public long createDate;
	public long modifiedDate;
	public long repositoryId;
	public long folderId;
	public String name;
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
}