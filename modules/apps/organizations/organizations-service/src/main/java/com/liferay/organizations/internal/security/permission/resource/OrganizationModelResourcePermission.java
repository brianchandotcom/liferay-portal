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