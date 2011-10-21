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

import com.liferay.portlet.documentlibrary.model.DLFileVersion;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing DLFileVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersion
 * @generated
 */
public class DLFileVersionCacheModel implements CacheModel<DLFileVersion>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(45);

		sb.append("{fileVersionId=");
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
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", folderId=");
		sb.append(folderId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
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

	public DLFileVersion toEntityModel() {
		DLFileVersionImpl dlFileVersionImpl = new DLFileVersionImpl();

		dlFileVersionImpl.setFileVersionId(fileVersionId);
		dlFileVersionImpl.setGroupId(groupId);
		dlFileVersionImpl.setCompanyId(companyId);
		dlFileVersionImpl.setUserId(userId);

		if (userName == null) {
			dlFileVersionImpl.setUserName(StringPool.BLANK);
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

		dlFileVersionImpl.setRepositoryId(repositoryId);
		dlFileVersionImpl.setFolderId(folderId);
		dlFileVersionImpl.setFileEntryId(fileEntryId);

		if (extension == null) {
			dlFileVersionImpl.setExtension(StringPool.BLANK);
		}
		else {
			dlFileVersionImpl.setExtension(extension);
		}

		if (mimeType == null) {
			dlFileVersionImpl.setMimeType(StringPool.BLANK);
		}
		else {
			dlFileVersionImpl.setMimeType(mimeType);
		}

		if (title == null) {
			dlFileVersionImpl.setTitle(StringPool.BLANK);
		}
		else {
			dlFileVersionImpl.setTitle(title);
		}

		if (description == null) {
			dlFileVersionImpl.setDescription(StringPool.BLANK);
		}
		else {
			dlFileVersionImpl.setDescription(description);
		}

		if (changeLog == null) {
			dlFileVersionImpl.setChangeLog(StringPool.BLANK);
		}
		else {
			dlFileVersionImpl.setChangeLog(changeLog);
		}

		if (extraSettings == null) {
			dlFileVersionImpl.setExtraSettings(StringPool.BLANK);
		}
		else {
			dlFileVersionImpl.setExtraSettings(extraSettings);
		}

		dlFileVersionImpl.setFileEntryTypeId(fileEntryTypeId);

		if (version == null) {
			dlFileVersionImpl.setVersion(StringPool.BLANK);
		}
		else {
			dlFileVersionImpl.setVersion(version);
		}

		dlFileVersionImpl.setSize(size);
		dlFileVersionImpl.setStatus(status);
		dlFileVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			dlFileVersionImpl.setStatusByUserName(StringPool.BLANK);
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

	public long fileVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long repositoryId;
	public long folderId;
	public long fileEntryId;
	public String extension;
	public String mimeType;
	public String title;
	public String description;
	public String changeLog;
	public String extraSettings;
	public long fileEntryTypeId;
	public String version;
	public long size;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}