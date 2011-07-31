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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceBlockConstants;
import com.liferay.portal.model.ResourceBlockPermissionsContainer;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.service.base.ResourceTypePermissionLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.ResourceTypePermissionPK;

import java.util.List;

/**
 * Manages resource type permissions.
 *
 * <p>
 * Never call the update methods of this service directly, always go through
 * the resource block local service.
 * </p>
 *
 * @author Connor McKay
 */
public class ResourceTypePermissionLocalServiceImpl
	extends ResourceTypePermissionLocalServiceBaseImpl {

	public List<ResourceTypePermission> findByRoleId(long roleId)
		throws SystemException {

		return resourceTypePermissionPersistence.findByRoleId(roleId);
	}

	public List<ResourceTypePermission> findByGroupScope(
			long companyId, String name, long roleId)
		throws SystemException {

		return resourceTypePermissionFinder.findByGroupScopeC_N_R(
			companyId, name, roleId);
	}

	public ResourceBlockPermissionsContainer
			getResourceBlockPermissionsContainer(
			long companyId, long groupId, String name)
		throws SystemException {

		List<ResourceTypePermission> resourceTypePermissions =
			resourceTypePermissionFinder.findByEitherScopeC_G_N(
			companyId, groupId, name);

		ResourceBlockPermissionsContainer resourceBlockPermissionContainer =
			new ResourceBlockPermissionsContainer();

		for (ResourceTypePermission resourceTypePermission :
			resourceTypePermissions) {

			resourceBlockPermissionContainer.setPermissions(
				resourceTypePermission.getRoleId(),
				resourceTypePermission.getActionIds());
		}

		return resourceBlockPermissionContainer;
	}

	public long getCompanyScopeActionIds(
			long companyId, String name, long roleId)
		throws SystemException {

		return getGroupScopeActionIds(companyId, 0, name, roleId);
	}

	public long getGroupScopeActionIds(
			long companyId, long groupId, String name, long roleId)
		throws SystemException {

		ResourceTypePermissionPK pk =
			new ResourceTypePermissionPK(companyId, groupId, name, roleId);

		ResourceTypePermission resourceTypePermission =
			resourceTypePermissionPersistence.fetchByPrimaryKey(pk);

		if (resourceTypePermission == null) {
			return 0;
		}
		else {
			return resourceTypePermission.getActionIds();
		}
	}

	public boolean hasEitherScopePermission(
			long companyId, String name, long roleId, String actionId)
		throws PortalException, SystemException {

		ResourceAction resourceAction =
			resourceActionLocalService.getResourceAction(name, actionId);

		List<ResourceTypePermission> resourceTypePermissions =
			resourceTypePermissionPersistence.findByC_N_R(
			companyId, name, roleId);

		for (ResourceTypePermission resourceTypePermission :
			resourceTypePermissions) {

			long actionIdsLong = resourceTypePermission.getActionIds();
			long bitwiseValue = resourceAction.getBitwiseValue();

			if ((actionIdsLong & bitwiseValue) == bitwiseValue) {
				return true;
			}
		}

		return false;
	}

	public boolean hasCompanyScopePermission(
			long companyId, String name, long roleId, String actionId)
		throws PortalException, SystemException {

		return hasGroupScopePermission(companyId, 0, name, roleId, actionId);
	}

	public boolean hasGroupScopePermission(
			long companyId, long groupId, String name, long roleId,
			String actionId)
		throws PortalException, SystemException {

		ResourceAction resourceAction =
			resourceActionLocalService.getResourceAction(name, actionId);

		ResourceTypePermissionPK pk =
			new ResourceTypePermissionPK(companyId, groupId, name, roleId);

		ResourceTypePermission resourceTypePermission =
			getResourceTypePermission(pk);

		long actionIdsLong = resourceTypePermission.getActionIds();
		long bitwiseValue = resourceAction.getBitwiseValue();

		if ((actionIdsLong & bitwiseValue) == bitwiseValue) {
			return true;
		}
		else {
			return false;
		}
	}

	public void updateCompanyScopeResourceTypePermissions(
			long companyId, String name, long roleId, long actionIdsLong,
			long operator)
		throws SystemException {

		updateGroupScopeResourceTypePermissions(
			companyId, 0, name, roleId, actionIdsLong, operator);
	}

	public void updateGroupScopeResourceTypePermissions(
			long companyId, long groupId, String name, long roleId,
			long actionIdsLong, long operator)
		throws SystemException {

		ResourceTypePermissionPK pk =
			new ResourceTypePermissionPK(companyId, groupId, name, roleId);

		ResourceTypePermission resourceTypePermission =
			resourceTypePermissionPersistence.fetchByPrimaryKey(pk);

		if (resourceTypePermission == null) {
			if (actionIdsLong == 0) {
				return;
			}

			resourceTypePermission =
				resourceTypePermissionPersistence.create(pk);
		}

		if (operator == ResourceBlockConstants.OPERATOR_ADD) {
			actionIdsLong |= resourceTypePermission.getActionIds();
		}
		else if (operator == ResourceBlockConstants.OPERATOR_REMOVE) {
			actionIdsLong =
				resourceTypePermission.getActionIds() & (~actionIdsLong);
		}

		if (actionIdsLong == 0) {
			deleteResourceTypePermission(resourceTypePermission);
		}
		else {
			resourceTypePermission.setActionIds(actionIdsLong);
			updateResourceTypePermission(resourceTypePermission);
		}
	}

}