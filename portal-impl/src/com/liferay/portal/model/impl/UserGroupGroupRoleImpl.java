/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Norbert Kocsis
 */
public class UserGroupGroupRoleImpl extends UserGroupGroupRoleBaseImpl {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UserGroupGroupRole)) {
			return false;
		}

		UserGroupGroupRole userGroupGroupRole = (UserGroupGroupRole)object;

		if ((getUserGroupId() == userGroupGroupRole.getUserGroupId()) &&
			(getGroupId() == userGroupGroupRole.getGroupId()) &&
			(getRoleId() == userGroupGroupRole.getRoleId())) {

			return true;
		}

		return false;
	}

	@Override
	public Group getGroup() throws PortalException {
		return GroupLocalServiceUtil.getGroup(getGroupId());
	}

	@Override
	public Role getRole() throws PortalException {
		return RoleLocalServiceUtil.getRole(getRoleId());
	}

	@Override
	public UserGroup getUserGroup() throws PortalException {
		return UserGroupLocalServiceUtil.getUserGroup(getUserGroupId());
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, getUserGroupId());

		hash = HashUtil.hash(hash, getGroupId());

		return HashUtil.hash(hash, getRoleId());
	}

}