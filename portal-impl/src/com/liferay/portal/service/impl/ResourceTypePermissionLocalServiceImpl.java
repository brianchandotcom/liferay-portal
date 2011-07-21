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
import com.liferay.portal.model.ResourceBlockPermissionsContainer;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.service.base.ResourceTypePermissionLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.ResourceTypePermissionPK;

import java.util.List;

/**
 * @author Connor McKay
 */
public class ResourceTypePermissionLocalServiceImpl
	extends ResourceTypePermissionLocalServiceBaseImpl {

	public ResourceBlockPermissionsContainer
			getResourceBlockPermissionsContainer(
			long companyId, long groupId, String name)
		throws SystemException {

		List<ResourceTypePermission> resourceTypePermissions =
			resourceTypePermissionFinder.findByC_G_N(
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

	public void setResourceTypePermissions(
			long companyId, String name, long roleId, long actionIdsLong)
		throws SystemException {

		setResourceTypePermissions(
			companyId, 0, name, roleId, actionIdsLong);
	}

	public void setResourceTypePermissions(
			long companyId, long groupId, String name, long roleId,
			long actionIdsLong)
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

		if (actionIdsLong == 0) {
			deleteResourceTypePermission(resourceTypePermission);
		}
		else {
			resourceTypePermission.setActionIds(actionIdsLong);
			updateResourceTypePermission(resourceTypePermission);
		}
	}
}