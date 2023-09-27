/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.membershippolicy;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.model.Organization;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public class DummyOrganizationMembershipPolicy
	extends BaseOrganizationMembershipPolicy {

	@Override
	public void checkMembership(
		long[] userIds, long[] addOrganizationIds,
		long[] removeOrganizationIds) {
	}

	@Override
	public boolean isMembershipAllowed(long userId, long groupId) {
		return true;
	}

	@Override
	public boolean isMembershipRequired(long userId, long groupId) {
		return false;
	}

	@Override
	public boolean isRoleAllowed(long userId, long groupId, long roleId) {
		return true;
	}

	@Override
	public boolean isRoleRequired(long userId, long groupId, long roleId) {
		return false;
	}

	@Override
	public void propagateMembership(
		long[] userIds, long[] addOrganizationIds,
		long[] removeOrganizationIds) {
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

}