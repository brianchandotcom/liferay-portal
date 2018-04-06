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

package com.liferay.portal.security.membershippolicy.bundle.organizationmembershippolicyfactory;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.security.membershippolicy.OrganizationMembershipPolicy;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestOrganizationMembershipPolicy
	implements OrganizationMembershipPolicy {

	@Override
	public void checkMembership(
		long[] userIds, long[] addOrganizationIds,
		long[] removeOrganizationIds) {
	}

	@Override
	public void checkRoles(
		List<UserGroupRole> addUserGroupRoles,
		List<UserGroupRole> removeUserGroupRoles) {
	}

	@Override
	public boolean isMembershipAllowed(long userId, long organizationId) {
		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMembershipProtected(
		PermissionChecker permissionChecker, long userId, long organizationId) {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMembershipRequired(long userId, long organizationId) {
		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleAllowed(
		long userId, long organizationId, long roleId) {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleProtected(
		PermissionChecker permissionChecker, long userId, long organizationId,
		long roleId) {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleRequired(
		long userId, long organizationId, long roleId) {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public void propagateMembership(
		long[] userIds, long[] addOrganizationIds,
		long[] removeOrganizationIds) {
	}

	@Override
	public void propagateRoles(
		List<UserGroupRole> addUserGroupRoles,
		List<UserGroupRole> removeUserGroupRoles) {
	}

	@Override
	public void verifyPolicy() {
	}

	@Override
	public void verifyPolicy(Organization organization) {
	}

	@Override
	public void verifyPolicy(
		Organization organization, Organization oldOrganization,
		List<AssetCategory> oldAssetCategories, List<AssetTag> oldAssetTags,
		Map<String, Serializable> oldExpandoAttributes) {
	}

	@Override
	public void verifyPolicy(Role role) {
	}

	@Override
	public void verifyPolicy(
		Role role, Role oldRole,
		Map<String, Serializable> oldExpandoAttributes) {
	}

}