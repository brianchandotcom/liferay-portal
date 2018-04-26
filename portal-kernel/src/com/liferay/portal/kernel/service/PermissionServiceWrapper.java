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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link PermissionService}.
 *
 * @author Brian Wing Shun Chan
 * @see PermissionService
 * @generated
 */
@ProviderType
public class PermissionServiceWrapper implements PermissionService,
	ServiceWrapper<PermissionService> {
	public PermissionServiceWrapper(PermissionService permissionService) {
		_permissionService = permissionService;
	}

	/**
	* Checks to see if the group has permission to the service.
	*
	* @param groupId the primary key of the group
	* @param name the service name
	* @param primKey the primary key of the service
	*/
	@Override
	public void checkPermission(long groupId, String name, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		_permissionService.checkPermission(groupId, name, primKey);
	}

	/**
	* Checks to see if the group has permission to the service.
	*
	* @param groupId the primary key of the group
	* @param name the service name
	* @param primKey the primary key of the service
	*/
	@Override
	public void checkPermission(long groupId, String name, String primKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		_permissionService.checkPermission(groupId, name, primKey);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _permissionService.getOSGiServiceIdentifier();
	}

	@Override
	public PermissionService getWrappedService() {
		return _permissionService;
	}

	@Override
	public void setWrappedService(PermissionService permissionService) {
		_permissionService = permissionService;
	}

	private PermissionService _permissionService;
}