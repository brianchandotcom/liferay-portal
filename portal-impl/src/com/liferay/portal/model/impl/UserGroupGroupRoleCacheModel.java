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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.UserGroupGroupRole;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing UserGroupGroupRole in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRole
 * @generated
 */
public class UserGroupGroupRoleCacheModel implements CacheModel<UserGroupGroupRole>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{userGroupId=");
		sb.append(userGroupId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", roleId=");
		sb.append(roleId);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserGroupGroupRole toEntityModel() {
		UserGroupGroupRoleImpl userGroupGroupRoleImpl = new UserGroupGroupRoleImpl();

		userGroupGroupRoleImpl.setUserGroupId(userGroupId);
		userGroupGroupRoleImpl.setGroupId(groupId);
		userGroupGroupRoleImpl.setRoleId(roleId);
		userGroupGroupRoleImpl.setMvccVersion(mvccVersion);

		userGroupGroupRoleImpl.resetOriginalValues();

		return userGroupGroupRoleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		userGroupId = objectInput.readLong();
		groupId = objectInput.readLong();
		roleId = objectInput.readLong();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(userGroupId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(roleId);
		objectOutput.writeLong(mvccVersion);
	}

	public long userGroupId;
	public long groupId;
	public long roleId;
	public long mvccVersion;
}