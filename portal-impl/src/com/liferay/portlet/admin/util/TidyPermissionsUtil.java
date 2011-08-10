/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.admin.util;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Raymond Augé
 */
public class TidyPermissionsUtil {

	public static void tidyAddToPagePermissions(ActionRequest actionRequest)
		throws Exception {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			cleanupAddToPagePermissions_5(actionRequest);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			cleanupAddToPagePermissions_6(actionRequest);
		}
	}

	protected static void cleanupAddToPagePermissions_5(
			ActionRequest actionRequest)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(actionRequest);

		Role role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		doCleanupAddToPagePermissions_5(companyId, role.getRoleId(), false);

		role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.USER);

		doCleanupAddToPagePermissions_5(companyId, role.getRoleId(), false);

		role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.POWER_USER);

		doCleanupAddToPagePermissions_5(companyId, role.getRoleId(), true);
	}

	protected static void cleanupAddToPagePermissions_6(
			ActionRequest actionRequest)
		throws Exception {


		long companyId = PortalUtil.getCompanyId(actionRequest);

		Role role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		doCleanupAddToPagePermissions_6(companyId, role.getRoleId(), false);

		role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.USER);

		doCleanupAddToPagePermissions_6(companyId, role.getRoleId(), false);

		role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.POWER_USER);

		doCleanupAddToPagePermissions_6(companyId, role.getRoleId(), true);
	}

	protected static void doCleanupAddToPagePermissions_5(
			long companyId, long roleId, boolean limitScope)
		throws Exception {

		List<Permission> rolePermissions =
			PermissionLocalServiceUtil.getRolePermissions(roleId);

		Group userPersonalSite = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.USER_PERSONAL_SITE);

		String groupIdString = String.valueOf(userPersonalSite.getGroupId());

		for (Permission permission : rolePermissions) {
			if (permission.getActionId() == ActionKeys.ADD_TO_PAGE) {
				PermissionLocalServiceUtil.unsetRolePermission(
					roleId, companyId, permission.getName(),
					permission.getScope(), permission.getPrimKey(),
					ActionKeys.ADD_TO_PAGE);

				if (limitScope &&
					!permission.getPrimKey().equals(groupIdString)) {

					PermissionLocalServiceUtil.setRolePermission(
						roleId, companyId, permission.getName(),
						ResourceConstants.SCOPE_GROUP, groupIdString,
						ActionKeys.ADD_TO_PAGE);
				}
			}
		}
	}

	protected static void doCleanupAddToPagePermissions_6(
			long companyId, long roleId, boolean limitScope)
		throws Exception {

		List<ResourcePermission> roleResourcePermissions =
			ResourcePermissionLocalServiceUtil.getRoleResourcePermissions(
				roleId);

		Group userPersonalSite = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.USER_PERSONAL_SITE);

		String groupIdString = String.valueOf(userPersonalSite.getGroupId());

		for (ResourcePermission resourcePermission : roleResourcePermissions) {
			if (resourcePermission.hasActionId(ActionKeys.ADD_TO_PAGE)) {
				ResourcePermissionLocalServiceUtil.removeResourcePermission(
					companyId, resourcePermission.getName(),
					resourcePermission.getScope(),
					resourcePermission.getPrimKey(), roleId,
					ActionKeys.ADD_TO_PAGE);

				if (limitScope &&
					!resourcePermission.getPrimKey().equals(groupIdString)) {

					ResourcePermissionLocalServiceUtil.addResourcePermission(
						companyId, resourcePermission.getName(),
						ResourceConstants.SCOPE_GROUP, groupIdString ,roleId,
						ActionKeys.ADD_TO_PAGE);
				}
			}
		}
	}

}