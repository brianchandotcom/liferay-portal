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
import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.service.DEDataDefinitionSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionSavePermissionsResponse;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
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
public class DEDataDefinitionSavePermissionsRequestExecutor {

	public DEDataDefinitionSavePermissionsRequestExecutor(
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	public DEDataDefinitionSavePermissionsResponse execute(
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest)
		throws Exception {

		long companyId = deDataDefinitionSavePermissionsRequest.getCompanyId();

		List<String> roleNames =
			deDataDefinitionSavePermissionsRequest.getRoleNames();

		List<String> actionIds = new ArrayList<>();

		if (deDataDefinitionSavePermissionsRequest.isAddDataDefinition()) {
			actionIds.add(DEActionKeys.ADD_DATA_DEFINITION_ACTION);
		}

		if (deDataDefinitionSavePermissionsRequest.isDefinePermissions()) {
			actionIds.add(DEActionKeys.DEFINE_PERMISSIONS_ACTION);
		}

		List<String> rolesNotFound = new ArrayList<>();
		List<Role> roles = new ArrayList<>();

		for (String roleName : roleNames) {
			try {
				roles.add(_roleLocalService.getRole(companyId, roleName));
			}
			catch (NoSuchRoleException nsre) {
				rolesNotFound.add(roleName);
			}
		}

		if (!rolesNotFound.isEmpty()) {
			throw new DEDataDefinitionException.NoSuchRoles(
				ArrayUtil.toStringArray(rolesNotFound));
		}

		for (Role role : roles) {
			_resourcePermissionLocalService.setResourcePermissions(
				companyId, "com.liferay.data.engine",
				ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
				role.getRoleId(), ArrayUtil.toStringArray(actionIds));
		}

		return DEDataDefinitionSavePermissionsResponse.Builder.of(
			ArrayUtil.toStringArray(roleNames));
	}

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}