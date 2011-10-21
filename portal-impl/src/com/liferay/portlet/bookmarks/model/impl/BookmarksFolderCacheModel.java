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

package com.liferay.portlet.bookmarks.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.bookmarks.model.BookmarksFolder;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing BookmarksFolder in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolder
 * @generated
 */
public class BookmarksFolderCacheModel implements CacheModel<BookmarksFolder>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

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
		sb.append(", resourceBlockId=");
		sb.append(resourceBlockId);
		sb.append(", parentFolderId=");
		sb.append(parentFolderId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	public BookmarksFolder toEntityModel() {
		BookmarksFolderImpl bookmarksFolderImpl = new BookmarksFolderImpl();

		if (uuid == null) {
			bookmarksFolderImpl.setUuid(StringPool.BLANK);
		}
		else {
			bookmarksFolderImpl.setUuid(uuid);
		}

		bookmarksFolderImpl.setFolderId(folderId);
		bookmarksFolderImpl.setGroupId(groupId);
		bookmarksFolderImpl.setCompanyId(companyId);
		bookmarksFolderImpl.setUserId(userId);

		if (userName == null) {
			bookmarksFolderImpl.setUserName(StringPool.BLANK);
		}
		else {
			bookmarksFolderImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			bookmarksFolderImpl.setCreateDate(null);
		}
		else {
			bookmarksFolderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			bookmarksFolderImpl.setModifiedDate(null);
		}
		else {
			bookmarksFolderImpl.setModifiedDate(new Date(modifiedDate));
		}

		bookmarksFolderImpl.setResourceBlockId(resourceBlockId);
		bookmarksFolderImpl.setParentFolderId(parentFolderId);

		if (name == null) {
			bookmarksFolderImpl.setName(StringPool.BLANK);
		}
		else {
			bookmarksFolderImpl.setName(name);
		}

		if (description == null) {
			bookmarksFolderImpl.setDescription(StringPool.BLANK);
		}
		else {
			bookmarksFolderImpl.setDescription(description);
		}

		bookmarksFolderImpl.resetOriginalValues();

		return bookmarksFolderImpl;
	}

	public String uuid;
	public long folderId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long resourceBlockId;
	public long parentFolderId;
	public String name;
	public String description;
}