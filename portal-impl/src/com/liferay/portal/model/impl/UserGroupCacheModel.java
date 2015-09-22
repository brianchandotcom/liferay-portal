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

package com.liferay.portal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.UserGroup;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing UserGroup in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroup
 * @generated
 */
@ProviderType
public class UserGroupCacheModel implements CacheModel<UserGroup>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserGroupCacheModel)) {
			return false;
		}

		UserGroupCacheModel userGroupCacheModel = (UserGroupCacheModel)obj;

		if ((userGroupId == userGroupCacheModel.userGroupId) &&
				(mvccVersion == userGroupCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, userGroupId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", userGroupId=");
		sb.append(userGroupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", parentUserGroupId=");
		sb.append(parentUserGroupId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", addedByLDAPImport=");
		sb.append(addedByLDAPImport);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserGroup toEntityModel() {
		UserGroupImpl userGroupImpl = new UserGroupImpl();

		userGroupImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			userGroupImpl.setUuid(StringPool.BLANK);
		}
		else {
			userGroupImpl.setUuid(uuid);
		}

		userGroupImpl.setUserGroupId(userGroupId);
		userGroupImpl.setUserId(userId);

		if (userName == null) {
			userGroupImpl.setUserName(StringPool.BLANK);
		}
		else {
			userGroupImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			userGroupImpl.setCreateDate(null);
		}
		else {
			userGroupImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			userGroupImpl.setModifiedDate(null);
		}
		else {
			userGroupImpl.setModifiedDate(new Date(modifiedDate));
		}

		userGroupImpl.setParentUserGroupId(parentUserGroupId);

		if (name == null) {
			userGroupImpl.setName(StringPool.BLANK);
		}
		else {
			userGroupImpl.setName(name);
		}

		if (description == null) {
			userGroupImpl.setDescription(StringPool.BLANK);
		}
		else {
			userGroupImpl.setDescription(description);
		}

		userGroupImpl.setAddedByLDAPImport(addedByLDAPImport);

		if (lastPublishDate == Long.MIN_VALUE) {
			userGroupImpl.setLastPublishDate(null);
		}
		else {
			userGroupImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		userGroupImpl.setCompanyId(companyId);

		userGroupImpl.resetOriginalValues();

		return userGroupImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		userGroupId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		parentUserGroupId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		addedByLDAPImport = objectInput.readBoolean();
		lastPublishDate = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(userGroupId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(parentUserGroupId);

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

		objectOutput.writeBoolean(addedByLDAPImport);
		objectOutput.writeLong(lastPublishDate);
		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public String uuid;
	public long userGroupId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentUserGroupId;
	public String name;
	public String description;
	public boolean addedByLDAPImport;
	public long lastPublishDate;
	public long companyId;
}