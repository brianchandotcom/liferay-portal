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

package com.liferay.bookmarks.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.bookmarks.model.BookmarksFolder;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing BookmarksFolder in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolder
 * @generated
 */
@ProviderType
public class BookmarksFolderCacheModel implements CacheModel<BookmarksFolder>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(35);

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
		sb.append(", treePath=");
		sb.append(treePath);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
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

		if (treePath == null) {
			bookmarksFolderImpl.setTreePath(StringPool.BLANK);
		}
		else {
			bookmarksFolderImpl.setTreePath(treePath);
		}

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

		bookmarksFolderImpl.setStatus(status);
		bookmarksFolderImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			bookmarksFolderImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			bookmarksFolderImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			bookmarksFolderImpl.setStatusDate(null);
		}
		else {
			bookmarksFolderImpl.setStatusDate(new Date(statusDate));
		}

		bookmarksFolderImpl.resetOriginalValues();

		return bookmarksFolderImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		folderId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		resourceBlockId = objectInput.readLong();
		parentFolderId = objectInput.readLong();
		treePath = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		status = objectInput.readInt();
		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(folderId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(resourceBlockId);
		objectOutput.writeLong(parentFolderId);

		if (treePath == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(treePath);
		}

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeInt(status);
		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
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
	public String treePath;
	public String name;
	public String description;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}