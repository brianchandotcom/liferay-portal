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

package com.liferay.content.repository.model.impl;

import com.liferay.content.repository.model.ContentRepositoryEntry;
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
 * The cache model class for representing ContentRepositoryEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ContentRepositoryEntryCacheModel
	implements CacheModel<ContentRepositoryEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ContentRepositoryEntryCacheModel)) {
			return false;
		}

		ContentRepositoryEntryCacheModel contentRepositoryEntryCacheModel =
			(ContentRepositoryEntryCacheModel)obj;

		if ((contentRepositoryEntryId ==
				contentRepositoryEntryCacheModel.contentRepositoryEntryId) &&
			(mvccVersion == contentRepositoryEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, contentRepositoryEntryId);

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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", contentRepositoryEntryId=");
		sb.append(contentRepositoryEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ContentRepositoryEntry toEntityModel() {
		ContentRepositoryEntryImpl contentRepositoryEntryImpl =
			new ContentRepositoryEntryImpl();

		contentRepositoryEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			contentRepositoryEntryImpl.setUuid("");
		}
		else {
			contentRepositoryEntryImpl.setUuid(uuid);
		}

		contentRepositoryEntryImpl.setContentRepositoryEntryId(
			contentRepositoryEntryId);
		contentRepositoryEntryImpl.setGroupId(groupId);
		contentRepositoryEntryImpl.setCompanyId(companyId);
		contentRepositoryEntryImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			contentRepositoryEntryImpl.setCreateDate(null);
		}
		else {
			contentRepositoryEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			contentRepositoryEntryImpl.setModifiedDate(null);
		}
		else {
			contentRepositoryEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		contentRepositoryEntryImpl.resetOriginalValues();

		return contentRepositoryEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		contentRepositoryEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
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

		objectOutput.writeLong(contentRepositoryEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
	}

	public long mvccVersion;
	public String uuid;
	public long contentRepositoryEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;

}