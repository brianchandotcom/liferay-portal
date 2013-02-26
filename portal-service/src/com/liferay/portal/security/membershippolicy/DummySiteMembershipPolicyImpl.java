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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class DummySiteMembershipPolicyImpl
	extends BaseSiteMembershipPolicyImpl {

	public void checkAddMembership(long[] userIds, long[] groupId)
		throws PortalException, SystemException {
	}

	public void checkAddRoles(long[] userIds, long[] groupIds, long[] roleIds)
		throws PortalException, SystemException {
	}

	public void checkRemoveMembership(long[] userIds, long[] groupIds)
		throws PortalException, SystemException {
	}

	public void checkRemoveRoles(
			long[] userIds, long[] groupIds, long[] roleIds)
		throws PortalException, SystemException {
	}

	public void propagateAddMembership(long[] userIds, long groupId)
		throws PortalException, SystemException {
	}

	public void propagateAddRoles(List<UserGroupRole> userGroupRole)
		throws PortalException, SystemException {
	}

	public void propagateRemoveMembership(long[] userIds, long groupId)
		throws PortalException, SystemException {
	}

	public void propagateRemoveRoles(long userId, long groupId, long roleId)
		throws PortalException, SystemException {
	}

	public void verifyPolicy(Group group)
		throws PortalException, SystemException {
	}

	public void verifyPolicy(Role role)
		throws PortalException, SystemException {
	}

	public void verifyUpdatePolicy(
			Group group, Group oldGroup,
			List<AssetCategory> oldAssetCatergories,
			List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException {
	}

	public void verifyUpdatePolicy(
			Group group, Group oldGroup, String oldTypeSettings)
		throws PortalException, SystemException {
	}

}