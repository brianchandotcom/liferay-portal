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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link PermissionService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PermissionService
 * @generated
 */
public class PermissionServiceWrapper implements PermissionService {
	public PermissionServiceWrapper(PermissionService permissionService) {
		_permissionService = permissionService;
	}

	public void checkPermission(long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.checkPermission(groupId, resourceId);
	}

	public void checkPermission(long groupId, java.lang.String name,
		long primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.checkPermission(groupId, name, primKey);
	}

	public void checkPermission(long groupId, java.lang.String name,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.checkPermission(groupId, name, primKey);
	}

	public boolean hasGroupPermission(long groupId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionService.hasGroupPermission(groupId, actionId,
			resourceId);
	}

	public boolean hasUserPermission(long userId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionService.hasUserPermission(userId, actionId, resourceId);
	}

	public boolean hasUserPermissions(long userId, long groupId,
		java.util.List<com.liferay.portal.model.Resource> resources,
		java.lang.String actionId,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _permissionService.hasUserPermissions(userId, groupId,
			resources, actionId, permissionCheckerBag);
	}

	public void setGroupPermissions(long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setGroupPermissions(groupId, actionIds, resourceId);
	}

	public void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setGroupPermissions(className, classPK, groupId,
			actionIds, resourceId);
	}

	public void setIndividualPermissions(long groupId, long companyId,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setIndividualPermissions(groupId, companyId,
			roleIdsToActionIds, resourceId);
	}

	public void setOrgGroupPermissions(long organizationId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setOrgGroupPermissions(organizationId, groupId,
			actionIds, resourceId);
	}

	public void setRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setRolePermission(roleId, groupId, name, scope,
			primKey, actionId);
	}

	public void setRolePermissions(long roleId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setRolePermissions(roleId, groupId, actionIds,
			resourceId);
	}

	public void setUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setUserPermissions(userId, groupId, actionIds,
			resourceId);
	}

	public void unsetRolePermission(long roleId, long groupId, long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.unsetRolePermission(roleId, groupId, permissionId);
	}

	public void unsetRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.unsetRolePermission(roleId, groupId, name, scope,
			primKey, actionId);
	}

	public void unsetRolePermissions(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.unsetRolePermissions(roleId, groupId, name, scope,
			actionId);
	}

	public void unsetUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.unsetUserPermissions(userId, groupId, actionIds,
			resourceId);
	}

	public PermissionService getWrappedPermissionService() {
		return _permissionService;
	}

	public void setWrappedPermissionService(PermissionService permissionService) {
		_permissionService = permissionService;
	}

	private PermissionService _permissionService;
}