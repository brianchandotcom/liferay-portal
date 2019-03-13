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

import com.liferay.data.engine.constants.DEDataLayoutConstants;
import com.liferay.data.engine.exception.DEDataLayoutException;
import com.liferay.data.engine.service.DEDataLayoutDeleteModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataLayoutDeleteModelPermissionsResponse;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcela Cunha
 */
public class DEDataLayoutDeleteModelPermissionsRequestExecutor {

	public DEDataLayoutDeleteModelPermissionsRequestExecutor(
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	public DEDataLayoutDeleteModelPermissionsResponse execute(
			DEDataLayoutDeleteModelPermissionsRequest
				deDataLayoutDeleteModelPermissionsRequest)
		throws Exception {

		List<String> actionIds = new ArrayList<>();
		List<String> noSuchRoleNames = new ArrayList<>();
		List<Role> roles = new ArrayList<>();

		if (deDataLayoutDeleteModelPermissionsRequest.isRevokeDelete()) {
			actionIds.add(ActionKeys.DELETE);
		}

		if (deDataLayoutDeleteModelPermissionsRequest.isRevokeUpdate()) {
			actionIds.add(ActionKeys.UPDATE);
		}

		if (deDataLayoutDeleteModelPermissionsRequest.isRevokeView()) {
			actionIds.add(ActionKeys.VIEW);
		}

		long companyId =
			deDataLayoutDeleteModelPermissionsRequest.getCompanyId();

		long deDataLayoutId =
			deDataLayoutDeleteModelPermissionsRequest.getDEDataLayoutId();

		List<String> roleNames =
			deDataLayoutDeleteModelPermissionsRequest.getRoleNames();

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
			for (String actionId : actionIds) {
				_resourcePermissionLocalService.removeResourcePermission(
					companyId, DEDataLayoutConstants.MODEL_RESOURCE_NAME,
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(deDataLayoutId), role.getRoleId(), actionId);
			}
		}

		return DEDataLayoutDeleteModelPermissionsResponse.Builder.of(
			deDataLayoutId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DEDataLayoutDeleteModelPermissionsRequestExecutor.class);

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}