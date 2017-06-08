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

package com.liferay.portal.security.membershippolicy.bundle.organizationmembershippolicyfactoryimpl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.membershippolicy.OrganizationMembershipPolicy;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE}
)
public class TestOrganizationMembershipPolicy
	implements OrganizationMembershipPolicy {

	@Override
	public void checkMembership(
			long[] userIds, long[] addOrganizationIds,
			long[] removeOrganizationIds)
		throws PortalException {
	}

	@Override
	public void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {
	}

	@Override
	public boolean isMembershipAllowed(long userId, long organizationId)
		throws PortalException {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId,
			long organizationId)
		throws PortalException {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMembershipRequired(long userId, long organizationId)
		throws PortalException {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleAllowed(long userId, long organizationId, long roleId)
		throws PortalException {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId,
			long organizationId, long roleId)
		throws PortalException {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleRequired(long userId, long organizationId, long roleId)
		throws PortalException {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public void propagateMembership(
			long[] userIds, long[] addOrganizationIds,
			long[] removeOrganizationIds)
		throws PortalException {
	}

	@Override
	public void propagateRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {
	}

	@Override
	public void verifyPolicy() throws PortalException {
	}

	@Override
	public void verifyPolicy(Organization organization) throws PortalException {
	}

	@Override
	public void verifyPolicy(
			Organization organization, Organization oldOrganization,
			List<AssetCategory> oldAssetCategories, List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException {
	}

	@Override
	public void verifyPolicy(Role role) throws PortalException {
	}

	@Override
	public void verifyPolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException {
	}

}