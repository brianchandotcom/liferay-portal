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
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.base.ResourcePermissionServiceBaseImpl;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourcePermissionServiceImpl
	extends ResourcePermissionServiceBaseImpl {

	public void addResourcePermission(
			long groupId, long companyId, String name, int scope,
			String primKey, long roleId, String actionId)
		throws PortalException, SystemException {

		permissionService.checkPermission(
			groupId, Role.class.getName(), roleId);

		resourcePermissionLocalService.addResourcePermission(
			companyId, name, scope, primKey, roleId, actionId);
	}

	public void setIndividualResourcePermissions(
			long groupId, long companyId, String name, String primKey,
			Map<Long, String[]> roleIdsToActionIds)
		throws PortalException, SystemException {

		permissionService.checkPermission(groupId, name, primKey);

		resourcePermissionLocalService.setResourcePermissions(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey,
			roleIdsToActionIds);
	}

	public void setIndividualResourcePermissions(
			long groupId, long companyId, String name, String primKey,
			long roleId, String[] actionIds)
		throws PortalException, SystemException {

		permissionService.checkPermission(groupId, name, primKey);

		resourcePermissionLocalService.setResourcePermissions(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey,
			roleId, actionIds);
	}

	public void removeResourcePermission(
			long groupId, long companyId, String name, int scope,
			String primKey, long roleId, String actionId)
		throws PortalException, SystemException {

		permissionService.checkPermission(
			groupId, Role.class.getName(), roleId);

		resourcePermissionLocalService.removeResourcePermission(
			companyId, name, scope, primKey, roleId, actionId);
	}

	public void removeResourcePermissions(
			long groupId, long companyId, String name, int scope, long roleId,
			String actionId)
		throws PortalException, SystemException {

		permissionService.checkPermission(
			groupId, Role.class.getName(), roleId);

		resourcePermissionLocalService.removeResourcePermissions(
			companyId, name, scope, roleId, actionId);
	}

}