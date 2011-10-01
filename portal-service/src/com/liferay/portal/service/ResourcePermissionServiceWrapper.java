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
 * This class is a wrapper for {@link ResourcePermissionService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePermissionService
 * @generated
 */
public class ResourcePermissionServiceWrapper
	implements ResourcePermissionService {
	public ResourcePermissionServiceWrapper(
		ResourcePermissionService resourcePermissionService) {
		_resourcePermissionService = resourcePermissionService;
	}

	public void addResourcePermission(long groupId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionService.addResourcePermission(groupId, companyId,
			name, scope, primKey, roleId, actionId);
	}

	public void setIndividualResourcePermissions(long groupId, long companyId,
		java.lang.String name, java.lang.String primKey,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionService.setIndividualResourcePermissions(groupId,
			companyId, name, primKey, roleIdsToActionIds);
	}

	public void setIndividualResourcePermissions(long groupId, long companyId,
		java.lang.String name, java.lang.String primKey, long roleId,
		java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionService.setIndividualResourcePermissions(groupId,
			companyId, name, primKey, roleId, actionIds);
	}

	public void removeResourcePermission(long groupId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionService.removeResourcePermission(groupId, companyId,
			name, scope, primKey, roleId, actionId);
	}

	public void removeResourcePermissions(long groupId, long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionService.removeResourcePermissions(groupId,
			companyId, name, scope, roleId, actionId);
	}

	public ResourcePermissionService getWrappedResourcePermissionService() {
		return _resourcePermissionService;
	}

	public void setWrappedResourcePermissionService(
		ResourcePermissionService resourcePermissionService) {
		_resourcePermissionService = resourcePermissionService;
	}

	private ResourcePermissionService _resourcePermissionService;
}