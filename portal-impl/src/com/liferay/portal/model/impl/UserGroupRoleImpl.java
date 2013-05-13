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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupRoleImpl extends UserGroupRoleBaseImpl {

	public UserGroupRoleImpl() {
	}

	@Override
	public Group getGroup() throws PortalException, SystemException {
		return GroupLocalServiceUtil.getGroup(getGroupId());
	}

	@Override
	public Role getRole() throws PortalException, SystemException {
		return RoleLocalServiceUtil.getRole(getRoleId());
	}

	@Override
	public User getUser() throws PortalException, SystemException {
		return UserLocalServiceUtil.getUser(getUserId());
	}

}