/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.OrganizationLocalServiceUtil;

/**
 * @author Charles May
 * @author Jorge Ferrer
 */
public class OrganizationPermissionImpl implements OrganizationPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, organizationId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, Organization organization,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, organization, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException, SystemException {

		if (organizationId > 0) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(organizationId);

			return contains(permissionChecker, organization, actionId);
		}
		else {
			return false;
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] organizationIds,
			String actionId)
		throws PortalException, SystemException {

		if ((organizationIds == null) || (organizationIds.length == 0)) {
			return true;
		}

		for (long organizationId : organizationIds) {
			check(permissionChecker, organizationId, actionId);
		}

		return true;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Organization organization,
			String actionId)
		throws PortalException, SystemException {

		Group group = organization.getGroup();

		long groupId = group.getGroupId();

		if (contains(permissionChecker, groupId, organization, actionId)) {
			return true;
		}

		return false;
	}

	protected boolean contains(
			PermissionChecker permissionChecker, long groupId,
			Organization organization, String actionId)
		throws PortalException, SystemException {

		while ((organization != null) &&
			   (organization.getOrganizationId() !=
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID)) {

			if (permissionChecker.hasPermission(
					groupId, Organization.class.getName(),
					organization.getOrganizationId(), actionId)) {

				return true;
			}

			organization = organization.getParentOrganization();
		}

		return false;
	}

}