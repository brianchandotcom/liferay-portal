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

import com.liferay.portlet.documentlibrary.model.DLFolder;

import java.util.Date;

/**
 * The cache model class for representing DLFolder in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLFolder
 * @generated
 */
public class DLFolderCacheModel implements CacheModel<DLFolder> {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", lastPostDate=");
		sb.append(lastPostDate);
		sb.append("}");

		return sb.toString();
	}

	public DLFolder toEntityModel() {
		DLFolderImpl dlFolderImpl = new DLFolderImpl();

		if (uuid == null) {
			dlFolderImpl.setUuid(StringPool.BLANK);
		}
		else {
			dlFolderImpl.setUuid(uuid);
		}

		dlFolderImpl.setFolderId(folderId);
		dlFolderImpl.setGroupId(groupId);
		dlFolderImpl.setCompanyId(companyId);
		dlFolderImpl.setUserId(userId);

		if (userName == null) {
			dlFolderImpl.setUserName(StringPool.BLANK);
		}
		else {
			dlFolderImpl.setUserName(userName);
		}

		if (createDate > 0) {
			dlFolderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate > 0) {
			dlFolderImpl.setModifiedDate(new Date(modifiedDate));
		}

		dlFolderImpl.setRepositoryId(repositoryId);
		dlFolderImpl.setMountPoint(mountPoint);
		dlFolderImpl.setParentFolderId(parentFolderId);

		if (name == null) {
			dlFolderImpl.setName(StringPool.BLANK);
		}
		else {
			dlFolderImpl.setName(name);
		}

		if (description == null) {
			dlFolderImpl.setDescription(StringPool.BLANK);
		}
		else {
			dlFolderImpl.setDescription(description);
		}

		if (lastPostDate > 0) {
			dlFolderImpl.setLastPostDate(new Date(lastPostDate));
		}

		dlFolderImpl.resetOriginalValues();

		return dlFolderImpl;
	}

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
	public String name;
	public String description;
	public long lastPostDate;
}