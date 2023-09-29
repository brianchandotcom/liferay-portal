/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

/**
 * @author Brian Wing Shun Chan
 */
public class OrganizationPermissionUtil_IW {
	public static OrganizationPermissionUtil_IW getInstance() {
		return _instance;
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long organizationId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		OrganizationPermissionUtil.check(permissionChecker, organizationId,
			actionId);
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		com.liferay.portal.kernel.model.Organization organization,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		OrganizationPermissionUtil.check(permissionChecker, organization,
			actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long organizationId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return OrganizationPermissionUtil.contains(permissionChecker,
			organizationId, actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long[] organizationIds, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return OrganizationPermissionUtil.contains(permissionChecker,
			organizationIds, actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		com.liferay.portal.kernel.model.Organization organization,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return OrganizationPermissionUtil.contains(permissionChecker,
			organization, actionId);
	}

	private OrganizationPermissionUtil_IW() {
	}

	private static OrganizationPermissionUtil_IW _instance = new OrganizationPermissionUtil_IW();
}