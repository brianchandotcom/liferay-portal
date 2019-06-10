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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.admin.util.OmniadminUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BasePermissionChecker implements PermissionChecker {

	@Override
	public abstract PermissionChecker clone();

	@Override
	public long getCompanyId() {
		return user.getCompanyId();
	}

	@Override
	public long[] getGuestUserRoleIds() {
		return PermissionChecker.DEFAULT_ROLE_IDS;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public List<Long> getOwnerResourceBlockIds(
		long companyId, long groupId, String name, String actionId) {

		return Collections.emptyList();
	}

	@Override
	public long getOwnerRoleId() {
		return ownerRole.getRoleId();
	}

	@Override
	public Map<Object, Object> getPermissionChecksMap() {
		return _permissionChecksMap;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public List<Long> getResourceBlockIds(
		long companyId, long groupId, long userId, String name,
		String actionId) {

		return Collections.emptyList();
	}

	@Override
	public long[] getRoleIds(long userId, long groupId) {
		return PermissionChecker.DEFAULT_ROLE_IDS;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public long getUserId() {
		return user.getUserId();
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, long primKey, long ownerId,
		String actionId) {

		return hasOwnerPermission(
			companyId, name, String.valueOf(primKey), ownerId, actionId);
	}

	@Override
	public boolean hasPermission(
		Group group, String name, long primKey, String actionId) {

		return hasPermission(group, name, String.valueOf(primKey), actionId);
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId) {

		return hasPermission(
			GroupLocalServiceUtil.fetchGroup(groupId), name,
			String.valueOf(primKey), actionId);
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		return hasPermission(
			GroupLocalServiceUtil.fetchGroup(groupId), name, primKey, actionId);
	}

	@Override
	public void init(User user) {
		this.user = user;

		if (user.isDefaultUser()) {
			defaultUserId = user.getUserId();
			signedIn = false;
		}
		else {
			try {
				defaultUserId = UserLocalServiceUtil.getDefaultUserId(
					user.getCompanyId());
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			signedIn = true;
		}

		try {
			ownerRole = RoleLocalServiceUtil.getRole(
				user.getCompanyId(), RoleConstants.OWNER);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public boolean isCheckGuest() {
		return checkGuest;
	}

	@Override
	public boolean isOmniadmin() {
		if (omniadmin == null) {
			omniadmin = Boolean.valueOf(OmniadminUtil.isOmniadmin(getUser()));
		}

		return omniadmin.booleanValue();
	}

	@Override
	public boolean isSignedIn() {
		return signedIn;
	}

	protected boolean checkGuest = PropsValues.PERMISSIONS_CHECK_GUEST_ENABLED;
	protected long defaultUserId;
	protected Boolean omniadmin;
	protected Role ownerRole;
	protected boolean signedIn;
	protected User user;

	private static final Log _log = LogFactoryUtil.getLog(
		BasePermissionChecker.class);

	private final Map<Object, Object> _permissionChecksMap = new HashMap<>();

}