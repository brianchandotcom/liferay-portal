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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceBlock;
import com.liferay.portal.kernel.model.ResourceBlockConstants;
import com.liferay.portal.kernel.model.ResourceBlockPermission;
import com.liferay.portal.kernel.model.ResourceBlockPermissionsContainer;
import com.liferay.portal.service.base.ResourceBlockPermissionLocalServiceBaseImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manages resource block permissions.
 *
 * <p>
 * Never directly access this service, always go through the resource block
 * local service.
 * </p>
 *
 * @author     Connor McKay
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ResourceBlockPermissionLocalServiceImpl
	extends ResourceBlockPermissionLocalServiceBaseImpl {

	@Override
	public void addResourceBlockPermissions(
		long resourceBlockId,
		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer) {

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

	@Override
	public void deleteResourceBlockPermissions(long resourceBlockId) {
		resourceBlockPermissionPersistence.removeByResourceBlockId(
			resourceBlockId);
	}

	@Override
	public Map<Long, Set<String>> getAvailableResourceBlockPermissionActionIds(
			String name, long primKey, List<String> actionIds)
		throws PortalException {

		if (actionIds.isEmpty()) {
			return Collections.emptyMap();
		}

		ResourceBlock resourceBlock =
			resourceBlockLocalService.getResourceBlock(name, primKey);

		List<ResourceBlockPermission> resourceBlockPermissions =
			resourceBlockPermissionPersistence.findByResourceBlockId(
				resourceBlock.getResourceBlockId());

		Map<Long, Set<String>> roleIdsToActionIds = new HashMap<>();

		for (ResourceBlockPermission resourceBlockPermission :
				resourceBlockPermissions) {

			Set<String> availableActionIds = new HashSet<>();

			List<String> resourceBlockActionIds =
				resourceBlockLocalService.getActionIds(
					name, resourceBlockPermission.getActionIds());

			for (String actionId : actionIds) {
				if (resourceBlockActionIds.contains(actionId)) {
					availableActionIds.add(actionId);
				}
			}

			if (!availableActionIds.isEmpty()) {
				roleIdsToActionIds.put(
					resourceBlockPermission.getRoleId(), availableActionIds);
			}
		}

		return roleIdsToActionIds;
	}

	@Override
	public ResourceBlockPermissionsContainer
		getResourceBlockPermissionsContainer(long resourceBlockId) {

		List<ResourceBlockPermission> resourceBlockPermissions =
			resourceBlockPermissionPersistence.findByResourceBlockId(
				resourceBlockId);

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			new ResourceBlockPermissionsContainer();

		for (ResourceBlockPermission resourceBlockPermission :
				resourceBlockPermissions) {

			resourceBlockPermissionsContainer.setPermissions(
				resourceBlockPermission.getRoleId(),
				resourceBlockPermission.getActionIds());
		}

		return resourceBlockPermissionsContainer;
	}

	@Override
	public int getResourceBlockPermissionsCount(
		long resourceBlockId, long roleId) {

		return resourceBlockPermissionPersistence.countByR_R(
			resourceBlockId, roleId);
	}

	@Override
	public void updateResourceBlockPermission(
		long resourceBlockId, long roleId, long actionIdsLong, int operator) {

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
				resourceBlockPermission.getActionIds() & ~actionIdsLong;
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