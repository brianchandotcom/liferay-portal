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
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public interface OrganizationMembershipPolicy {

	public static final int SEARCH_INTERVAL = 500;

	public void checkAddMembership(long[] userIds, long[] organizationIds)
		throws PortalException, SystemException;

	public void checkAddRoles(
			long[] userIds, long[] organizationIds, long[] roleIds)
		throws PortalException, SystemException;

	public void checkRemoveMembership(long[] userIds, long[] organizationId)
		throws PortalException, SystemException;

	public void checkRemoveRoles(
			long[] userIds, long[] organizationIds, long[] roleIds)
		throws PortalException, SystemException;

	boolean isMembershipAllowed(long userId, long organizationId)
		throws PortalException, SystemException;

	boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId,
			long organizationId)
		throws PortalException, SystemException;

	boolean isMembershipRequired(long userId, long organizationId)
		throws PortalException, SystemException;

	boolean isRoleAllowed(long userId, long organizationId, long roleId)
		throws PortalException, SystemException;

	boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId,
			long organizationId, long roleId)
		throws PortalException, SystemException;

	boolean isRoleRequired(long userId, long organizationId, long roleId)
		throws PortalException, SystemException;

	public void propagateAddMembership(long[] userIds, long organizationIds)
		throws PortalException, SystemException;

	public void propagateAddRoles(List<UserGroupRole> userGroupRole)
		throws PortalException, SystemException;

	public void propagateRemoveMembership(long[] userIds, long organizationIds)
		throws PortalException, SystemException;

	public void propagateRemoveRole(
			long userId, long organizationId, long roleId)
		throws PortalException, SystemException;

	public void verifyPolicy() throws PortalException, SystemException;

	public void verifyPolicy(Organization organization)
		throws PortalException, SystemException;

	public void verifyPolicy(Role role) throws PortalException, SystemException;

	public void verifyUpdatePolicy(
			Organization organization, Organization oldOrganization,
			List<AssetCategory> oldAssetCategories, List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException;

}