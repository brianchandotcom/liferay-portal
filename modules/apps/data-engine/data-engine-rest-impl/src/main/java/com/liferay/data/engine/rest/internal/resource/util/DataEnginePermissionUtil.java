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

package com.liferay.data.engine.rest.internal.resource.util;

import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

/**
 * @author Brian Wing Shun Chan
 */
public class DataEnginePermissionUtil {

	public static void checkPermission(
			String actionId, GroupLocalService groupLocalService, Long siteId)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		Group group = groupLocalService.fetchGroup(siteId);

		if ((group != null) && group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		String resourceName = "com.liferay.data.engine";

		if (!permissionChecker.hasPermission(
				group, resourceName, siteId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, resourceName, siteId, actionId);
		}
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
			catch (NoSuchRoleException noSuchRoleException) {
				if (_log.isDebugEnabled()) {
					_log.debug(roleName, noSuchRoleException);
				}

				invalidRoleNames.add(roleName);
			}
		}

		if (!invalidRoleNames.isEmpty()) {
			throw new ValidationException(
				"Invalid roles: " + ArrayUtil.toStringArray(invalidRoleNames));
		}

		return roles;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataEnginePermissionUtil.class);

}