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

package com.liferay.portal.vulcan.internal.security.permission;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Tambara
 */
public class LiberalPermissionChecker implements PermissionChecker {

	@Override
	public PermissionChecker clone() {
		return this;
	}

	@Override
	public long getCompanyId() {
		return _user.getCompanyId();
	}

	@Override
	public long[] getGuestUserRoleIds() {
		return PermissionChecker.DEFAULT_ROLE_IDS;
	}

	@Override
	public long getOwnerRoleId() {
		return _ownerRole.getRoleId();
	}

	@Override
	public Map<Object, Object> getPermissionChecksMap() {
		return new HashMap<>();
	}

	@Override
	public long[] getRoleIds(long userId, long groupId) {
		return PermissionChecker.DEFAULT_ROLE_IDS;
	}

	@Override
	public User getUser() {
		return _user;
	}

	@Override
	public UserBag getUserBag() {
		return null;
	}

	@Override
	public long getUserId() {
		return _user.getUserId();
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, long primKey, long ownerId,
		String actionId) {

		return true;
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, String primKey, long ownerId,
		String actionId) {

		return true;
	}

	@Override
	public boolean hasPermission(
		Group group, String name, long primKey, String actionId) {

		return true;
	}

	@Override
	public boolean hasPermission(
		Group group, String name, String primKey, String actionId) {

		return true;
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId) {

		return true;
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		return true;
	}

	@Override
	public void init(User user) {
		_user = user;

		try {
			_ownerRole = RoleLocalServiceUtil.getRole(
				user.getCompanyId(), RoleConstants.OWNER);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	public boolean isCheckGuest() {
		return PropsValues.PERMISSIONS_CHECK_GUEST_ENABLED;
	}

	@Override
	public boolean isCompanyAdmin() {
		return true;
	}

	@Override
	public boolean isCompanyAdmin(long companyId) {
		return true;
	}

	@Override
	public boolean isContentReviewer(long companyId, long groupId) {
		return true;
	}

	@Override
	public boolean isGroupAdmin(long groupId) {
		return true;
	}

	@Override
	public boolean isGroupMember(long groupId) {
		return true;
	}

	@Override
	public boolean isGroupOwner(long groupId) {
		return true;
	}

	@Override
	public boolean isOmniadmin() {
		return true;
	}

	@Override
	public boolean isOrganizationAdmin(long organizationId) {
		return true;
	}

	@Override
	public boolean isOrganizationOwner(long organizationId) {
		return true;
	}

	@Override
	public boolean isSignedIn() {
		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiberalPermissionChecker.class);

	private Role _ownerRole;
	private User _user;

}