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

package com.liferay.knowledge.base.model.impl;

import com.liferay.knowledge.base.model.KBFolder;
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
 * The cache model class for representing KBFolder in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KBFolderCacheModel
	implements CacheModel<KBFolder>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KBFolderCacheModel)) {
			return false;
		}

		KBFolderCacheModel kbFolderCacheModel = (KBFolderCacheModel)obj;

		if ((kbFolderId == kbFolderCacheModel.kbFolderId) &&
			(mvccVersion == kbFolderCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kbFolderId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", kbFolderId=");
		sb.append(kbFolderId);
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
		sb.append(", parentKBFolderId=");
		sb.append(parentKBFolderId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", urlTitle=");
		sb.append(urlTitle);
		sb.append(", description=");
		sb.append(description);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KBFolder toEntityModel() {
		KBFolderImpl kbFolderImpl = new KBFolderImpl();

		kbFolderImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			kbFolderImpl.setUuid("");
		}
		else {
			kbFolderImpl.setUuid(uuid);
		}

		kbFolderImpl.setKbFolderId(kbFolderId);
		kbFolderImpl.setGroupId(groupId);
		kbFolderImpl.setCompanyId(companyId);
		kbFolderImpl.setUserId(userId);

		if (userName == null) {
			kbFolderImpl.setUserName("");
		}
		else {
			kbFolderImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kbFolderImpl.setCreateDate(null);
		}
		else {
			kbFolderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kbFolderImpl.setModifiedDate(null);
		}
		else {
			kbFolderImpl.setModifiedDate(new Date(modifiedDate));
		}

		kbFolderImpl.setParentKBFolderId(parentKBFolderId);

		if (name == null) {
			kbFolderImpl.setName("");
		}
		else {
			kbFolderImpl.setName(name);
		}

		if (urlTitle == null) {
			kbFolderImpl.setUrlTitle("");
		}
		else {
			kbFolderImpl.setUrlTitle(urlTitle);
		}

		if (description == null) {
			kbFolderImpl.setDescription("");
		}
		else {
			kbFolderImpl.setDescription(description);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			kbFolderImpl.setLastPublishDate(null);
		}
		else {
			kbFolderImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		kbFolderImpl.resetOriginalValues();

		return kbFolderImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		kbFolderId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		parentKBFolderId = objectInput.readLong();
		name = objectInput.readUTF();
		urlTitle = objectInput.readUTF();
		description = objectInput.readUTF();
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

		objectOutput.writeLong(kbFolderId);

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

		objectOutput.writeLong(parentKBFolderId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (urlTitle == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(urlTitle);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long kbFolderId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentKBFolderId;
	public String name;
	public String urlTitle;
	public String description;
	public long lastPublishDate;

}