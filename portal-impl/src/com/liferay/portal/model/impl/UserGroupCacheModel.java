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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.UserGroup;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing UserGroup in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserGroupCacheModel
	implements CacheModel<UserGroup>, Externalizable, MVCCModel {

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
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", userGroupId=");
		sb.append(userGroupId);
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
		sb.append(", parentUserGroupId=");
		sb.append(parentUserGroupId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", addedByLDAPImport=");
		sb.append(addedByLDAPImport);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserGroup toEntityModel() {
		UserGroupImpl userGroupImpl = new UserGroupImpl();

		userGroupImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			userGroupImpl.setUuid("");
		}
		else {
			userGroupImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			userGroupImpl.setExternalReferenceCode("");
		}
		else {
			userGroupImpl.setExternalReferenceCode(externalReferenceCode);
		}

		userGroupImpl.setUserGroupId(userGroupId);
		userGroupImpl.setCompanyId(companyId);
		userGroupImpl.setUserId(userId);

		if (userName == null) {
			userGroupImpl.setUserName("");
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
			userGroupImpl.setName("");
		}
		else {
			userGroupImpl.setName(name);
		}

		if (description == null) {
			userGroupImpl.setDescription("");
		}
		else {
			userGroupImpl.setDescription(description);
		}

		userGroupImpl.setAddedByLDAPImport(addedByLDAPImport);

		userGroupImpl.resetOriginalValues();

		return userGroupImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		userGroupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		parentUserGroupId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		addedByLDAPImport = objectInput.readBoolean();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(userGroupId);

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

		objectOutput.writeLong(parentUserGroupId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeBoolean(addedByLDAPImport);
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long userGroupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentUserGroupId;
	public String name;
	public String description;
	public boolean addedByLDAPImport;

}