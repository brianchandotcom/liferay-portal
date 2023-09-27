/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.organizations.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.permission.OrganizationPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.Organization",
	service = ModelResourcePermission.class
)
public class OrganizationModelResourcePermission
	implements ModelResourcePermission<Organization> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException {

		organizationPermission.check(
			permissionChecker, organizationId, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, Organization organization,
			String actionId)
		throws PortalException {

		organizationPermission.check(permissionChecker, organization, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException {

		return organizationPermission.contains(
			permissionChecker, organizationId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Organization organization,
			String actionId)
		throws PortalException {

		return organizationPermission.contains(
			permissionChecker, organization, actionId);
	}

	@Override
	public String getModelName() {
		return Organization.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected OrganizationPermission organizationPermission;

}