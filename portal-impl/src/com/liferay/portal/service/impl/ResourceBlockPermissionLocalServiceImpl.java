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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ResourceBlockConstants;
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.model.ResourceBlockPermissionsContainer;
import com.liferay.portal.service.base.ResourceBlockPermissionLocalServiceBaseImpl;

import java.util.List;
import java.util.Map;

/**
 * Manages resource block permissions.
 *
 * <p>
 * Never directly access this service, always go through the resource block
 * local service.
 * </p>
 *
 * @author Connor McKay
 */
public class ResourceBlockPermissionLocalServiceImpl
	extends ResourceBlockPermissionLocalServiceBaseImpl {

	public void addResourceBlockPermissions(
			long resourceBlockId,
			ResourceBlockPermissionsContainer resourceBlockPermissionsContainer)
		throws SystemException {

		Map<Long, Long> permissions =
			resourceBlockPermissionsContainer.getPermissions();

		for (Map.Entry<Long, Long> permission : permissions.entrySet()) {
			long resourceBlockPermissionId = counterLocalService.increment();

			ResourceBlockPermission resourceBlockPermission =
				resourceBlockPermissionPersistence.create(
					resourceBlockPermissionId);

			resourceBlockPermission.setResourceBlockId(resourceBlockId);
			resourceBlockPermission.setRoleId(permission.getKey());
			resourceBlockPermission.setActionIds(permission.getValue());

			updateResourceBlockPermission(resourceBlockPermission);
		}
	}

	public void deleteResourceBlockPermissions(long resourceBlockId)
		throws SystemException {

		resourceBlockPermissionPersistence.removeByResourceBlockId(
			resourceBlockId);
	}

	public ResourceBlockPermissionsContainer
			getResourceBlockPermissionsContainer(long resourceBlockId)
		throws SystemException {

		List<ResourceBlockPermission> resourceBlockPermissions =
			resourceBlockPermissionPersistence.findByResourceBlockId(
				resourceBlockId);

		ResourceBlockPermissionsContainer resourceBlockPermissionContainer =
			new ResourceBlockPermissionsContainer();

		for (ResourceBlockPermission resourceBlockPermission :
				resourceBlockPermissions) {

			resourceBlockPermissionContainer.setPermissions(
				resourceBlockPermission.getRoleId(),
				resourceBlockPermission.getActionIds());
		}

		return resourceBlockPermissionContainer;
	}

	public void updateResourceBlockPermission(
			long resourceBlockId, long roleId, long actionIdsLong, int operator)
		throws SystemException {

		ResourceBlockPermission resourceBlockPermission =
			resourceBlockPermissionPersistence.fetchByR_R(
				resourceBlockId, roleId);

		if (resourceBlockPermission == null) {
			if (actionIdsLong == 0) {
				return;
			}

			long resourceBlockPermissionId = counterLocalService.increment();

			resourceBlockPermission = resourceBlockPermissionPersistence.create(
				resourceBlockPermissionId);

			resourceBlockPermission.setResourceBlockId(resourceBlockId);
			resourceBlockPermission.setRoleId(roleId);
		}

		if (operator == ResourceBlockConstants.OPERATOR_ADD) {
			actionIdsLong |= resourceBlockPermission.getActionIds();
		}
		else if (operator == ResourceBlockConstants.OPERATOR_REMOVE) {
			actionIdsLong =
				resourceBlockPermission.getActionIds() & (~actionIdsLong);
		}

		if (actionIdsLong == 0) {
			deleteResourceBlockPermission(resourceBlockPermission);
		}
		else {
			resourceBlockPermission.setActionIds(actionIdsLong);

			updateResourceBlockPermission(resourceBlockPermission);
		}
	}

}