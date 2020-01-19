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

package com.liferay.portal.vulcan.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.vulcan.permissions.Permissions;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public class PermissionsUtil {

	public static ModelPermissions getModelPermissions(
			long companyId, Permissions[] permissions, long primKey,
			String resourceName,
			ResourceActionLocalService resourceActionLocalService,
			ResourcePermissionLocalService resourcePermissionLocalService,
			RoleLocalService roleLocalService)
		throws PortalException {

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			new String[0], new String[0], resourceName);

		for (Permissions itemPermissions : permissions) {
			String[] actionIds = itemPermissions.getActionIds();

			if (actionIds.length == 0) {
				List<ResourceAction> resourceActions =
					resourceActionLocalService.getResourceActions(resourceName);

				Role role = roleLocalService.getRole(
					companyId, itemPermissions.getRoleName());

				for (ResourceAction resourceAction : resourceActions) {
					resourcePermissionLocalService.removeResourcePermission(
						companyId, resourceName,
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(primKey), role.getRoleId(),
						resourceAction.getActionId());
				}

				continue;
			}

			modelPermissions.addRolePermissions(
				itemPermissions.getRoleName(), itemPermissions.getActionIds());
		}

		return modelPermissions;
	}

}