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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.admin.util.OmniadminUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;

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
	public List<Long> getGuestResourceBlockIds(
		long companyId, long groupId, String name, String actionId) {

		return Collections.emptyList();
	}

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
		long groupId, String name, long primKey, String actionId) {

		return hasPermission(groupId, name, String.valueOf(primKey), actionId);
	}

	@Override
	public void init(User user) {
		this.user = user;

		if (user.isDefaultUser()) {
			this.defaultUserId = user.getUserId();
			this.signedIn = false;
		}
		else {
			try {
				this.defaultUserId = UserLocalServiceUtil.getDefaultUserId(
					user.getCompanyId());
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			this.signedIn = true;
		}

		try {
			this.ownerRole = RoleLocalServiceUtil.getRole(
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

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isGroupAdmin(long)}
	 */
	@Override
	public boolean isCommunityAdmin(long groupId) {
		return isGroupAdmin(groupId);
	}

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isGroupOwner(long)}
	 */
	@Override
	public boolean isCommunityOwner(long groupId) {
		return isGroupOwner(groupId);
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

	@Override
	public void resetValues() {
	}

	@Override
	public void setValues(PortletRequest portletRequest) {
	}

	protected boolean checkGuest = PropsValues.PERMISSIONS_CHECK_GUEST_ENABLED;
	protected long defaultUserId;
	protected Boolean omniadmin;
	protected Role ownerRole;
	protected boolean signedIn;
	protected User user;

	private static Log _log = LogFactoryUtil.getLog(
		BasePermissionChecker.class);

}