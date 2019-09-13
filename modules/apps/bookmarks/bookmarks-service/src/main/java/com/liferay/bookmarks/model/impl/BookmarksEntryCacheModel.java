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

import com.liferay.bookmarks.model.BookmarksEntry;
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
 * The cache model class for representing BookmarksEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BookmarksEntryCacheModel
	implements CacheModel<BookmarksEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BookmarksEntryCacheModel)) {
			return false;
		}

		BookmarksEntryCacheModel bookmarksEntryCacheModel =
			(BookmarksEntryCacheModel)obj;

		if ((entryId == bookmarksEntryCacheModel.entryId) &&
			(mvccVersion == bookmarksEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, entryId);

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
		StringBundler sb = new StringBundler(43);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", entryId=");
		sb.append(entryId);
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
		sb.append(", folderId=");
		sb.append(folderId);
		sb.append(", treePath=");
		sb.append(treePath);
		sb.append(", name=");
		sb.append(name);
		sb.append(", url=");
		sb.append(url);
		sb.append(", description=");
		sb.append(description);
		sb.append(", visits=");
		sb.append(visits);
		sb.append(", priority=");
		sb.append(priority);
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
	public BookmarksEntry toEntityModel() {
		BookmarksEntryImpl bookmarksEntryImpl = new BookmarksEntryImpl();

		bookmarksEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			bookmarksEntryImpl.setUuid("");
		}
		else {
			bookmarksEntryImpl.setUuid(uuid);
		}

		bookmarksEntryImpl.setEntryId(entryId);
		bookmarksEntryImpl.setGroupId(groupId);
		bookmarksEntryImpl.setCompanyId(companyId);
		bookmarksEntryImpl.setUserId(userId);

		if (userName == null) {
			bookmarksEntryImpl.setUserName("");
		}
		else {
			bookmarksEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			bookmarksEntryImpl.setCreateDate(null);
		}
		else {
			bookmarksEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			bookmarksEntryImpl.setModifiedDate(null);
		}
		else {
			bookmarksEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		bookmarksEntryImpl.setFolderId(folderId);

		if (treePath == null) {
			bookmarksEntryImpl.setTreePath("");
		}
		else {
			bookmarksEntryImpl.setTreePath(treePath);
		}

		if (name == null) {
			bookmarksEntryImpl.setName("");
		}
		else {
			bookmarksEntryImpl.setName(name);
		}

		if (url == null) {
			bookmarksEntryImpl.setUrl("");
		}
		else {
			bookmarksEntryImpl.setUrl(url);
		}

		if (description == null) {
			bookmarksEntryImpl.setDescription("");
		}
		else {
			bookmarksEntryImpl.setDescription(description);
		}

		bookmarksEntryImpl.setVisits(visits);
		bookmarksEntryImpl.setPriority(priority);

		if (lastPublishDate == Long.MIN_VALUE) {
			bookmarksEntryImpl.setLastPublishDate(null);
		}
		else {
			bookmarksEntryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		bookmarksEntryImpl.setStatus(status);
		bookmarksEntryImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			bookmarksEntryImpl.setStatusByUserName("");
		}
		else {
			bookmarksEntryImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			bookmarksEntryImpl.setStatusDate(null);
		}
		else {
			bookmarksEntryImpl.setStatusDate(new Date(statusDate));
		}

		bookmarksEntryImpl.resetOriginalValues();

		return bookmarksEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		entryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		folderId = objectInput.readLong();
		treePath = objectInput.readUTF();
		name = objectInput.readUTF();
		url = objectInput.readUTF();
		description = objectInput.readUTF();

		visits = objectInput.readInt();

		priority = objectInput.readInt();
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

		objectOutput.writeLong(entryId);

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

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeInt(visits);

		objectOutput.writeInt(priority);
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
	public long entryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long folderId;
	public String treePath;
	public String name;
	public String url;
	public String description;
	public int visits;
	public int priority;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}