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

import com.liferay.data.engine.exception.DEDataRecordCollectionException;
import com.liferay.data.engine.service.DEDataRecordCollectionDeletePermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionDeletePermissionsResponse;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollectionDeletePermissionsRequestExecutor {

	public DEDataRecordCollectionDeletePermissionsRequestExecutor(
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	public DEDataRecordCollectionDeletePermissionsResponse execute(
			DEDataRecordCollectionDeletePermissionsRequest
				deDataRecordCollectionRemovePermissionsRequest)
		throws Exception {

		long companyId =
			deDataRecordCollectionRemovePermissionsRequest.getCompanyId();

		List<String> roleNames =
			deDataRecordCollectionRemovePermissionsRequest.getRoleNames();

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
			throw new DEDataRecordCollectionException.NoSuchRoles(
				ArrayUtil.toStringArray(noSuchRoleNames));
		}

		for (Role role : roles) {
			ResourcePermission resourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					companyId, "com.liferay.data.engine",
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					role.getRoleId());

			if (resourcePermission != null) {
				_resourcePermissionLocalService.deleteResourcePermission(
					resourcePermission);
			}
		}

		return DEDataRecordCollectionDeletePermissionsResponse.Builder.of(
			ArrayUtil.toStringArray(roleNames));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DEDataRecordCollectionDeletePermissionsRequestExecutor.class);

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}