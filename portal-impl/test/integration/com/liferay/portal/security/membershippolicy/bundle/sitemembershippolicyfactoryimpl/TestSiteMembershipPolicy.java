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

package com.liferay.portal.security.membershippolicy.bundle.sitemembershippolicyfactoryimpl;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicy;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestSiteMembershipPolicy implements SiteMembershipPolicy {

	@Override
	public void checkMembership(
		long[] userIds, long[] addGroupIds, long[] removeGroupIds) {

		_atomicBoolean.set(Boolean.TRUE);
	}

	@Override
	public void checkRoles(
		List<UserGroupRole> addUserGroupRoles,
		List<UserGroupRole> removeUserGroupRoles) {

		_atomicBoolean.set(Boolean.TRUE);
	}

	@Override
	public boolean isMembershipAllowed(long userId, long groupId) {
		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMembershipProtected(
		PermissionChecker permissionChecker, long userId, long groupId) {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMembershipRequired(long userId, long groupId) {
		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleAllowed(long userId, long groupId, long roleId) {
		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleProtected(
		PermissionChecker permissionChecker, long userId, long groupId,
		long roleId) {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleRequired(long userId, long groupId, long roleId) {
		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public void propagateMembership(
		long[] userIds, long[] addUserGroupIds, long[] removeUserGroupIds) {

		_atomicBoolean.set(Boolean.TRUE);
	}

	@Override
	public void propagateRoles(
		List<UserGroupRole> addUserGroupRoles,
		List<UserGroupRole> removeUserGroupRoles) {

		_atomicBoolean.set(Boolean.TRUE);
	}

	@Override
	public void verifyPolicy() {
		_atomicBoolean.set(Boolean.TRUE);
	}

	@Override
	public void verifyPolicy(Group group) {
		_atomicBoolean.set(Boolean.TRUE);
	}

	@Override
	public void verifyPolicy(
		Group group, Group oldGroup, List<AssetCategory> oldAssetCategories,
		List<AssetTag> oldAssetTags,
		Map<String, Serializable> oldExpandoAttributes,
		UnicodeProperties oldTypeSettingsProperties) {

		_atomicBoolean.set(Boolean.TRUE);
	}

	@Override
	public void verifyPolicy(Role role) {
		_atomicBoolean.set(Boolean.TRUE);
	}

	@Override
	public void verifyPolicy(
		Role role, Role oldRole,
		Map<String, Serializable> oldExpandoAttributes) {

		_atomicBoolean.set(Boolean.TRUE);
	}

	@Reference(target = "(test=AtomicState)")
	protected void setAtomicBoolean(AtomicBoolean atomicBoolean) {
		_atomicBoolean = atomicBoolean;
	}

	private AtomicBoolean _atomicBoolean;

}