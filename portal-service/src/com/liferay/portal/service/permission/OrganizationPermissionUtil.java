/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Organization;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class OrganizationPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException, SystemException {

		getOrganizationPermission().check(
			permissionChecker, organizationId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, Organization organization,
			String actionId)
		throws PortalException, SystemException {

		getOrganizationPermission().check(
			permissionChecker, organization, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException, SystemException {

		return getOrganizationPermission().contains(
			permissionChecker, organizationId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long[] organizationIds,
			String actionId)
		throws PortalException, SystemException {

		return getOrganizationPermission().contains(
			permissionChecker, organizationIds, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Organization organization,
			String actionId)
		throws PortalException, SystemException {

		return getOrganizationPermission().contains(
			permissionChecker, organization, actionId);
	}

	public static OrganizationPermission getOrganizationPermission() {
		PortalRuntimePermission.checkGetBeanProperty(
			OrganizationPermissionUtil.class);

		return _organizationPermission;
	}

	public void setOrganizationPermission(
		OrganizationPermission organizationPermission) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_organizationPermission = organizationPermission;
	}

	private static OrganizationPermission _organizationPermission;

}