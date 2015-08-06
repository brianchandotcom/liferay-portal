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

package com.liferay.users.admin.web.portlet;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portlet.BaseControlPanelEntry;

import java.util.List;

/**
 * @author Jorge Ferrer
 * @author Zsolt Berentey
 */
public class UsersControlPanelEntry extends BaseControlPanelEntry {

	@Override
	protected boolean hasPermissionImplicitlyGranted(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		List<UserGroupRole> userGroupRoles =
			UserGroupRoleLocalServiceUtil.getUserGroupRoles(
				permissionChecker.getUserId());

		for (UserGroupRole userGroupRole : userGroupRoles) {
			Role role = userGroupRole.getRole();

			String roleName = role.getName();

			if (roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.ORGANIZATION_OWNER)) {

				return true;
			}
		}

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getUserOrganizations(
				permissionChecker.getUserId());

		for (Organization organization : organizations) {
			if (OrganizationPermissionUtil.contains(
					permissionChecker, organization, ActionKeys.MANAGE_USERS)) {

				return true;
			}

			if (OrganizationPermissionUtil.contains(
					permissionChecker, organization,
					ActionKeys.MANAGE_SUBORGANIZATIONS)) {

				return true;
			}

			/*if (OrganizationPermissionUtil.contains(
					permissionChecker, organization.getOrganizationId(),
					ActionKeys.VIEW)) {

				return true;
			}*/
		}

		return false;
	}

}