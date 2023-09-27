/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link PermissionService}.
 *
 * @author Brian Wing Shun Chan
 * @see PermissionService
 * @generated
 */
public class PermissionServiceWrapper
	implements PermissionService, ServiceWrapper<PermissionService> {

	public PermissionServiceWrapper() {
		this(null);
	}

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
	public void checkPermission(
			long groupId, java.lang.String name, long primKey)
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
	public void checkPermission(
			long groupId, java.lang.String name, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_permissionService.checkPermission(groupId, name, primKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
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