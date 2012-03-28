/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.journal.model.JournalFolder;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing JournalFolder in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolder
 * @generated
 */
public class JournalFolderCacheModel implements CacheModel<JournalFolder>,
	Serializable {
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

	public JournalFolder toEntityModel() {
		JournalFolderImpl journalFolderImpl = new JournalFolderImpl();

		if (uuid == null) {
			journalFolderImpl.setUuid(StringPool.BLANK);
		}
		else {
			journalFolderImpl.setUuid(uuid);
		}

		journalFolderImpl.setFolderId(folderId);
		journalFolderImpl.setGroupId(groupId);
		journalFolderImpl.setCompanyId(companyId);
		journalFolderImpl.setUserId(userId);

		if (userName == null) {
			journalFolderImpl.setUserName(StringPool.BLANK);
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

		if (name == null) {
			journalFolderImpl.setName(StringPool.BLANK);
		}
		else {
			journalFolderImpl.setName(name);
		}

		if (description == null) {
			journalFolderImpl.setDescription(StringPool.BLANK);
		}
		else {
			journalFolderImpl.setDescription(description);
		}

		journalFolderImpl.resetOriginalValues();

		return journalFolderImpl;
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