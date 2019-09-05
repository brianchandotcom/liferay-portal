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
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.service.persistence.UserGroupGroupRolePK;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing UserGroupGroupRole in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserGroupGroupRoleCacheModel
	implements CacheModel<UserGroupGroupRole>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserGroupGroupRoleCacheModel)) {
			return false;
		}

		UserGroupGroupRoleCacheModel userGroupGroupRoleCacheModel =
			(UserGroupGroupRoleCacheModel)obj;

		if (userGroupGroupRolePK.equals(
				userGroupGroupRoleCacheModel.userGroupGroupRolePK) &&
			(mvccVersion == userGroupGroupRoleCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, userGroupGroupRolePK);

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
		sb.append(", userGroupId=");
		sb.append(userGroupId);
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
	public UserGroupGroupRole toEntityModel() {
		UserGroupGroupRoleImpl userGroupGroupRoleImpl =
			new UserGroupGroupRoleImpl();

		userGroupGroupRoleImpl.setMvccVersion(mvccVersion);
		userGroupGroupRoleImpl.setUserGroupId(userGroupId);
		userGroupGroupRoleImpl.setGroupId(groupId);
		userGroupGroupRoleImpl.setRoleId(roleId);
		userGroupGroupRoleImpl.setCompanyId(companyId);

		userGroupGroupRoleImpl.resetOriginalValues();

		return userGroupGroupRoleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		userGroupId = objectInput.readLong();

		groupId = objectInput.readLong();

		roleId = objectInput.readLong();

		companyId = objectInput.readLong();

		userGroupGroupRolePK = new UserGroupGroupRolePK(
			userGroupId, groupId, roleId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(userGroupId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(roleId);

		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public long userGroupId;
	public long groupId;
	public long roleId;
	public long companyId;
	public transient UserGroupGroupRolePK userGroupGroupRolePK;

}