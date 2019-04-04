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

package com.liferay.data.engine.rest.internal.resource.v1_0.util;

import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.constants.DataEngineConstants;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;

/**
 * @author Brian Wing Shun Chan
 */
public class DataEnginePermissionUtil {

	public static void checkPermission(
			String actionId, Long contentSpaceId,
			GroupLocalService groupLocalService)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		Group group = groupLocalService.fetchGroup(contentSpaceId);

		if ((group != null) && group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (!permissionChecker.hasPermission(
				group, DataEngineConstants.RESOURCE_NAME, contentSpaceId,
				actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, DataEngineConstants.RESOURCE_NAME,
				contentSpaceId, actionId);
		}
	}

	public static void checkPermissionOperation(
			long contentSpaceId, GroupLocalService groupLocalService,
			String operation)
		throws Exception {

		if (!StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_DELETE_PERMISSION, operation) &&
			!StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_SAVE_PERMISSION, operation)) {

			throw new BadRequestException(
				"Operation must be 'delete' or 'save'");
		}

		checkPermission(
			DataActionKeys.DEFINE_PERMISSIONS, contentSpaceId,
			groupLocalService);
	}

	public static List<Role> getRoles(
			Company company, RoleLocalService roleLocalService,
			String[] roleNames)
		throws PortalException {

		List<String> invalidRoleNames = new ArrayList<>();
		List<Role> roles = new ArrayList<>();

		for (String roleName : roleNames) {
			try {
				Role role = roleLocalService.getRole(
					company.getCompanyId(), roleName);

				roles.add(role);
			}
			catch (NoSuchRoleException nsre) {
				if (_log.isDebugEnabled()) {
					_log.debug(roleName, nsre);
				}

				invalidRoleNames.add(roleName);
			}
		}

		if (!invalidRoleNames.isEmpty()) {
			throw new BadRequestException(
				"Invalid roles: " + ArrayUtil.toStringArray(invalidRoleNames));
		}

		return roles;
	}

	public static void persistPermission(
			Company company, List<String> actionIds, String operation,
			ResourcePermissionLocalService resourcePermissionLocalService,
			RoleLocalService roleLocalService, String[] roleNames)
		throws Exception {

		if (StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_SAVE_PERMISSION, operation)) {

			List<Role> roles = getRoles(company, roleLocalService, roleNames);

			for (Role role : roles) {
				resourcePermissionLocalService.setResourcePermissions(
					company.getCompanyId(), DataEngineConstants.RESOURCE_NAME,
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(company.getCompanyId()), role.getRoleId(),
					ArrayUtil.toStringArray(actionIds));
			}
		}
		else {
			List<Role> roles = getRoles(company, roleLocalService, roleNames);

			for (Role role : roles) {
				ResourcePermission resourcePermission =
					resourcePermissionLocalService.fetchResourcePermission(
						company.getCompanyId(),
						DataEngineConstants.RESOURCE_NAME,
						ResourceConstants.SCOPE_COMPANY,
						String.valueOf(company.getCompanyId()),
						role.getRoleId());

				if (resourcePermission != null) {
					resourcePermissionLocalService.deleteResourcePermission(
						resourcePermission);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataEnginePermissionUtil.class);

}