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

package com.liferay.portlet.imagegallery.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.imagegallery.model.IGFolder;

import java.util.Date;

/**
 * The cache model class for representing IGFolder in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see IGFolder
 * @generated
 */
public class IGFolderCacheModel implements CacheModel<IGFolder> {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

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
		sb.append(", parentFolderId=");
		sb.append(parentFolderId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	public IGFolder toEntityModel() {
		IGFolderImpl igFolderImpl = new IGFolderImpl();

		if (uuid == null) {
			igFolderImpl.setUuid(StringPool.BLANK);
		}
		else {
			igFolderImpl.setUuid(uuid);
		}

		igFolderImpl.setFolderId(folderId);
		igFolderImpl.setGroupId(groupId);
		igFolderImpl.setCompanyId(companyId);
		igFolderImpl.setUserId(userId);

		if (userName == null) {
			igFolderImpl.setUserName(StringPool.BLANK);
		}
		else {
			igFolderImpl.setUserName(userName);
		}

		if (createDate > 0) {
			igFolderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate > 0) {
			igFolderImpl.setModifiedDate(new Date(modifiedDate));
		}

		igFolderImpl.setParentFolderId(parentFolderId);

		if (name == null) {
			igFolderImpl.setName(StringPool.BLANK);
		}
		else {
			igFolderImpl.setName(name);
		}

		if (description == null) {
			igFolderImpl.setDescription(StringPool.BLANK);
		}
		else {
			igFolderImpl.setDescription(description);
		}

		igFolderImpl.resetOriginalValues();

		return igFolderImpl;
	}

	public String uuid;
	public long folderId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentFolderId;
	public String name;
	public String description;
}