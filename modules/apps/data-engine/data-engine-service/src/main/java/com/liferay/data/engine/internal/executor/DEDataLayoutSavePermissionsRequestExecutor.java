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

package com.liferay.data.engine.internal.executor;

import com.liferay.data.engine.constants.DEActionKeys;
import com.liferay.data.engine.exception.DEDataLayoutException;
import com.liferay.data.engine.service.DEDataLayoutSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataLayoutSavePermissionsResponse;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcela Cunha
 */
public class DEDataLayoutSavePermissionsRequestExecutor {

	public DEDataLayoutSavePermissionsRequestExecutor(
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	public DEDataLayoutSavePermissionsResponse execute(
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest)
		throws Exception {

		long companyId = deDataLayoutSavePermissionsRequest.getCompanyId();

		List<String> roleNames =
			deDataLayoutSavePermissionsRequest.getRoleNames();

		List<String> actionIds = new ArrayList<>();

		if (deDataLayoutSavePermissionsRequest.isAddDataLayout()) {
			actionIds.add(DEActionKeys.ADD_DATA_LAYOUT);
		}

		if (deDataLayoutSavePermissionsRequest.isDefinePermissions()) {
			actionIds.add(DEActionKeys.DEFINE_PERMISSIONS_ACTION);
		}

		List<String> noSuchRoleNames = new ArrayList<>();
		List<Role> roles = new ArrayList<>();

		for (String roleName : roleNames) {
			try {
				roles.add(_roleLocalService.getRole(companyId, roleName));
			}
			catch (NoSuchRoleException nsre) {
				if (_log.isDebugEnabled()) {
					_log.debug(roleName, nsre);
				}

				noSuchRoleNames.add(roleName);
			}
		}

		if (!noSuchRoleNames.isEmpty()) {
			throw new DEDataLayoutException.NoSuchRoles(
				ArrayUtil.toStringArray(noSuchRoleNames));
		}

		for (Role role : roles) {
			_resourcePermissionLocalService.setResourcePermissions(
				companyId, "com.liferay.data.engine",
				ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
				role.getRoleId(), ArrayUtil.toStringArray(actionIds));
		}

		return DEDataLayoutSavePermissionsResponse.Builder.of(
			ArrayUtil.toStringArray(roleNames));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DEDataLayoutSavePermissionsRequestExecutor.class);

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}