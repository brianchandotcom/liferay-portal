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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Norbert Kocsis
 */
public class UserGroupGroupRoleImpl extends UserGroupGroupRoleBaseImpl {

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

}