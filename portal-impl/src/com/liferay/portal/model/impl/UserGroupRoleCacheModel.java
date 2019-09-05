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
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.persistence.UserGroupRolePK;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing UserGroupRole in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserGroupRoleCacheModel
	implements CacheModel<UserGroupRole>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserGroupRoleCacheModel)) {
			return false;
		}

		UserGroupRoleCacheModel userGroupRoleCacheModel =
			(UserGroupRoleCacheModel)obj;

		if (userGroupRolePK.equals(userGroupRoleCacheModel.userGroupRolePK) &&
			(mvccVersion == userGroupRoleCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, userGroupRolePK);

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
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", roleId=");
		sb.append(roleId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserGroupRole toEntityModel() {
		UserGroupRoleImpl userGroupRoleImpl = new UserGroupRoleImpl();

		userGroupRoleImpl.setMvccVersion(mvccVersion);
		userGroupRoleImpl.setUserId(userId);
		userGroupRoleImpl.setGroupId(groupId);
		userGroupRoleImpl.setRoleId(roleId);
		userGroupRoleImpl.setCompanyId(companyId);

		userGroupRoleImpl.resetOriginalValues();

		return userGroupRoleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		userId = objectInput.readLong();

		groupId = objectInput.readLong();

		roleId = objectInput.readLong();

		companyId = objectInput.readLong();

		userGroupRolePK = new UserGroupRolePK(userId, groupId, roleId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(roleId);

		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public long userId;
	public long groupId;
	public long roleId;
	public long companyId;
	public transient UserGroupRolePK userGroupRolePK;

}