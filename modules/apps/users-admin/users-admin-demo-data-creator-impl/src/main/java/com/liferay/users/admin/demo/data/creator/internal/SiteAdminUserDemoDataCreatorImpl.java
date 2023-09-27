/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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