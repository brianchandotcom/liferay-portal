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

package com.liferay.portal.security.membership.policy.site.samples;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.security.membershippolicy.BaseSiteMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicy;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.security.membership.policy.site.BaseSiteMembershipPolicyTestCase;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = SiteMembershipPolicy.class
)
public class TestSiteMembershipPolicy extends BaseSiteMembershipPolicy {

	@Override
	public void checkMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException {

		for (long forbiddenGroupId :
				BaseSiteMembershipPolicyTestCase.getForbiddenGroupIds()) {

			if (forbiddenGroupId == 0) {
				continue;
			}

			if (ArrayUtil.contains(addGroupIds, forbiddenGroupId)) {
				throw new MembershipPolicyException(
					MembershipPolicyException.SITE_MEMBERSHIP_NOT_ALLOWED);
			}
		}

		for (long requiredGroupId :
				BaseSiteMembershipPolicyTestCase.getRequiredGroupIds()) {

			if (requiredGroupId == 0) {
				continue;
			}

			if (ArrayUtil.contains(removeGroupIds, requiredGroupId)) {
				throw new MembershipPolicyException(
					MembershipPolicyException.SITE_MEMBERSHIP_REQUIRED);
			}
		}
	}

	@Override
	public void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {

		long[] addUserGroupRoleIds = new long[2];
		long[] removeUserGroupRoleIds = new long[2];

		if ((addUserGroupRoles != null) && !addUserGroupRoles.isEmpty()) {
			for (UserGroupRole addUserGroupRole : addUserGroupRoles) {
				addUserGroupRoleIds[0] = addUserGroupRole.getRoleId();
			}
		}

		if ((removeUserGroupRoles != null) && !removeUserGroupRoles.isEmpty()) {
			for (UserGroupRole removeUserGroupRole : removeUserGroupRoles) {
				removeUserGroupRoleIds[0] = removeUserGroupRole.getRoleId();
			}
		}

		for (long forbiddenGroupRoleId :
				BaseSiteMembershipPolicyTestCase.getForbiddenRoleIds()) {

			if (forbiddenGroupRoleId == 0) {
				continue;
			}

			if (ArrayUtil.contains(addUserGroupRoleIds, forbiddenGroupRoleId)) {
				throw new MembershipPolicyException(
					MembershipPolicyException.ROLE_MEMBERSHIP_NOT_ALLOWED);
			}
		}

		for (long requiredGroupRoleId :
				BaseSiteMembershipPolicyTestCase.getRequiredRoleIds()) {

			if (requiredGroupRoleId == 0) {
				continue;
			}

			if (ArrayUtil.contains(
					removeUserGroupRoleIds, requiredGroupRoleId)) {

				throw new MembershipPolicyException(
					MembershipPolicyException.ROLE_MEMBERSHIP_REQUIRED);
			}
		}
	}

	@Override
	public void propagateMembership(
		long[] userIds, long[] addGroupIds, long[] removeGroupIds) {

		BaseSiteMembershipPolicyTestCase.setPropagateMembership(true);
	}

	@Override
	public void propagateRoles(
		List<UserGroupRole> addUserGroupRoles,
		List<UserGroupRole> removeUserGroupRoles) {

		BaseSiteMembershipPolicyTestCase.setPropagateRoles(true);
	}

	@Override
	public void verifyPolicy() {
		BaseSiteMembershipPolicyTestCase.setVerify(true);
	}

	@Override
	public void verifyPolicy(Group group) {
		verifyPolicy();
	}

	@Override
	public void verifyPolicy(
		Group group, Group oldGroup, List<AssetCategory> oldAssetCategories,
		List<AssetTag> oldAssetTags,
		Map<String, Serializable> oldExpandoAttributes,
		UnicodeProperties oldTypeSettingsProperties) {

		Assert.assertNotNull(group);
		Assert.assertNotNull(oldGroup);

		if (oldTypeSettingsProperties == null) {
			Assert.assertNotNull(oldAssetCategories);
			Assert.assertNotNull(oldAssetTags);
			Assert.assertNotNull(oldExpandoAttributes);
		}
		else {
			Assert.assertNull(oldAssetCategories);
			Assert.assertNull(oldAssetTags);
			Assert.assertNull(oldExpandoAttributes);
		}

		verifyPolicy(group);
	}

	@Override
	public void verifyPolicy(Role role) {
		verifyPolicy();
	}

	@Override
	public void verifyPolicy(
		Role role, Role oldRole,
		Map<String, Serializable> oldExpandoAttributes) {

		Assert.assertNotNull(role);
		Assert.assertNotNull(oldRole);
		Assert.assertNotNull(oldExpandoAttributes);

		verifyPolicy(role);
	}

}