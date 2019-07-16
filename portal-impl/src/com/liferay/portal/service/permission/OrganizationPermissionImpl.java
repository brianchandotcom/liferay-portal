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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.OrganizationPermission;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Charles May
 * @author Jorge Ferrer
 * @author Sergio González
 */
public class OrganizationPermissionImpl implements OrganizationPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, organizationId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Organization.class.getName(), organizationId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, Organization organization,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, organization, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Organization.class.getName(),
				organization.getOrganizationId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException {

		if (organizationId > 0) {
			return contains(
				permissionChecker,
				OrganizationLocalServiceUtil.getOrganization(organizationId),
				actionId);
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] organizationIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(organizationIds)) {
			return false;
		}

		for (long organizationId : organizationIds) {
			if (!contains(permissionChecker, organizationId, actionId)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Organization organization,
			String actionId)
		throws PortalException {

		long groupId = organization.getGroupId();

		if (contains(permissionChecker, groupId, organization, actionId)) {
			return true;
		}

		while (!organization.isRoot()) {
			Organization parentOrganization =
				organization.getParentOrganization();

			groupId = parentOrganization.getGroupId();

			if (contains(
					permissionChecker, groupId, parentOrganization,
					ActionKeys.MANAGE_SUBORGANIZATIONS)) {

				return true;
			}

			organization = parentOrganization;
		}

		return false;
	}

	protected boolean contains(
			PermissionChecker permissionChecker, long groupId,
			Organization organization, String actionId)
		throws PortalException {

		while ((organization != null) &&
			   (organization.getOrganizationId() !=
				   OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID)) {

			if (actionId.equals(ActionKeys.ADD_ORGANIZATION) &&
				(permissionChecker.hasPermission(
					groupId, Organization.class.getName(),
					organization.getOrganizationId(),
					ActionKeys.MANAGE_SUBORGANIZATIONS) ||
				 PortalPermissionUtil.contains(
					 permissionChecker, ActionKeys.ADD_ORGANIZATION))) {

				return true;
			}
			else if (permissionChecker.hasPermission(
						groupId, Organization.class.getName(),
						organization.getOrganizationId(), actionId)) {

				return true;
			}

			organization = organization.getParentOrganization();
		}

		return false;
	}

}