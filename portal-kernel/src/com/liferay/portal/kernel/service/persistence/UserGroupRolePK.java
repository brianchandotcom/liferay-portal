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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserGroupRolePK
	implements Comparable<UserGroupRolePK>, Serializable {

	public long userId;
	public long groupId;
	public long roleId;

	public UserGroupRolePK() {
	}

	public UserGroupRolePK(long userId, long groupId, long roleId) {
		this.userId = userId;
		this.groupId = groupId;
		this.roleId = roleId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	@Override
	public int compareTo(UserGroupRolePK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (userId < pk.userId) {
			value = -1;
		}
		else if (userId > pk.userId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (groupId < pk.groupId) {
			value = -1;
		}
		else if (groupId > pk.groupId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (roleId < pk.roleId) {
			value = -1;
		}
		else if (roleId > pk.roleId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserGroupRolePK)) {
			return false;
		}

		UserGroupRolePK pk = (UserGroupRolePK)obj;

		if ((userId == pk.userId) && (groupId == pk.groupId) &&
			(roleId == pk.roleId)) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = HashUtil.hash(hashCode, userId);
		hashCode = HashUtil.hash(hashCode, groupId);
		hashCode = HashUtil.hash(hashCode, roleId);

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(8);

		sb.append("{");

		sb.append("userId=");

		sb.append(userId);
		sb.append(", groupId=");

		sb.append(groupId);
		sb.append(", roleId=");

		sb.append(roleId);

		sb.append("}");

		return sb.toString();
	}

}