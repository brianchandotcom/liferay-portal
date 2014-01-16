/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.RepositoryEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing RepositoryEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryEntry
 * @generated
 */
public class RepositoryEntryCacheModel implements CacheModel<RepositoryEntry>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", repositoryEntryId=");
		sb.append(repositoryEntryId);
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
		sb.append(", mappedId=");
		sb.append(mappedId);
		sb.append(", manualCheckInRequired=");
		sb.append(manualCheckInRequired);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RepositoryEntry toEntityModel() {
		RepositoryEntryImpl repositoryEntryImpl = new RepositoryEntryImpl();

		if (uuid == null) {
			repositoryEntryImpl.setUuid(StringPool.BLANK);
		}
		else {
			repositoryEntryImpl.setUuid(uuid);
		}

		repositoryEntryImpl.setRepositoryEntryId(repositoryEntryId);
		repositoryEntryImpl.setGroupId(groupId);
		repositoryEntryImpl.setCompanyId(companyId);
		repositoryEntryImpl.setUserId(userId);

		if (userName == null) {
			repositoryEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			repositoryEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			repositoryEntryImpl.setCreateDate(null);
		}
		else {
			repositoryEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			repositoryEntryImpl.setModifiedDate(null);
		}
		else {
			repositoryEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		repositoryEntryImpl.setRepositoryId(repositoryId);

		if (mappedId == null) {
			repositoryEntryImpl.setMappedId(StringPool.BLANK);
		}
		else {
			repositoryEntryImpl.setMappedId(mappedId);
		}

		repositoryEntryImpl.setManualCheckInRequired(manualCheckInRequired);
		repositoryEntryImpl.setMvccVersion(mvccVersion);

		repositoryEntryImpl.resetOriginalValues();

		return repositoryEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		repositoryEntryId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		repositoryId = objectInput.readLong();
		mappedId = objectInput.readUTF();
		manualCheckInRequired = objectInput.readBoolean();
		mvccVersion = objectInput.readLong();
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

		objectOutput.writeLong(repositoryEntryId);
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
		objectOutput.writeLong(repositoryId);

		if (mappedId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(mappedId);
		}

		objectOutput.writeBoolean(manualCheckInRequired);
		objectOutput.writeLong(mvccVersion);
	}

	public String uuid;
	public long repositoryEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long repositoryId;
	public String mappedId;
	public boolean manualCheckInRequired;
	public long mvccVersion;
}