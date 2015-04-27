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

package com.liferay.portlet.rolesadmin.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.RoleServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static Role getRole(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long roleId = ParamUtil.getLong(request, "roleId");

		Role role = null;

		Group group = (Group)request.getAttribute(WebKeys.GROUP);

		if ((group != null) && group.isOrganization()) {
			long organizationId = group.getOrganizationId();

			while (organizationId !=
						OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				long organizationGroupId = organization.getGroupId();

				if (GroupPermissionUtil.contains(
						permissionChecker, organizationGroupId,
						ActionKeys.ASSIGN_USER_ROLES) ||
					OrganizationPermissionUtil.contains(
						permissionChecker, organization,
						ActionKeys.ASSIGN_USER_ROLES) ||
					UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						themeDisplay.getUserId(), organizationGroupId,
						RoleConstants.ORGANIZATION_ADMINISTRATOR, true) ||
					UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						themeDisplay.getUserId(), organizationGroupId,
						RoleConstants.ORGANIZATION_OWNER, true)) {

					if (roleId > 0) {
						role = RoleLocalServiceUtil.getRole(roleId);
					}

					break;
				}

				organizationId = organization.getParentOrganizationId();
			}

			if ((roleId > 0) && (role == null)) {
				role = RoleServiceUtil.getRole(roleId);
			}
		}
		else if ((group != null) && group.isRegularSite()) {
			if (GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.ASSIGN_USER_ROLES) ||
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					themeDisplay.getUserId(), group.getGroupId(),
					RoleConstants.SITE_ADMINISTRATOR, true) ||
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					themeDisplay.getUserId(), group.getGroupId(),
					RoleConstants.SITE_OWNER, true)) {

				if (roleId > 0) {
					role = RoleLocalServiceUtil.getRole(roleId);
				}
			}
			else {
				if (roleId > 0) {
					role = RoleServiceUtil.getRole(roleId);
				}
			}
		}
		else {
			if (roleId > 0) {
				role = RoleServiceUtil.getRole(roleId);
			}
		}

		return role;
	}

	public static Role getRole(PortletRequest portletRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getRole(request);
	}

}