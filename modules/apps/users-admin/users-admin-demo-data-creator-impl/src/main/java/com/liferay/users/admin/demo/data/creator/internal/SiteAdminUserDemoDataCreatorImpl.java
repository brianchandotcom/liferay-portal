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

package com.liferay.users.admin.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.users.admin.demo.data.creator.SiteAdminUserDemoDataCreator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = SiteAdminUserDemoDataCreator.class)
public class SiteAdminUserDemoDataCreatorImpl
	extends BaseUserDemoDataCreator implements SiteAdminUserDemoDataCreator {

	@Override
	public User create(long groupId) throws PortalException {
		return create(groupId, null);
	}

	@Override
	public User create(long groupId, String emailAddress)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		User user = createUser(group.getCompanyId(), emailAddress);

		userLocalService.addGroupUser(groupId, user.getUserId());

		Role role = _roleLocalService.getRole(
			group.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_userGroupRoleLocalService.addUserGroupRoles(
			user.getUserId(), groupId, new long[] {role.getRoleId()});

		return userLocalService.getUser(user.getUserId());
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}