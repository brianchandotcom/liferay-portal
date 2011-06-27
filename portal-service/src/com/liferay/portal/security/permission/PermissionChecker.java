/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.User;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public interface PermissionChecker {

	public static final long[] DEFAULT_ROLE_IDS = {};

	public long getCompanyId();

	public long getOwnerRoleId();

	public long[] getRoleIds(long userId, long groupId);

	public long getUserId();

	public boolean hasOwnerPermission(
		long companyId, String name, long primKey, long ownerId,
		String actionId);

	public boolean hasOwnerPermission(
		long companyId, String name, String primKey, long ownerId,
		String actionId);

	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId);

	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId);

	public boolean hasUserPermission(
		long groupId, String name, String primKey, String actionId,
		boolean checkAdmin);

	public void init(User user, boolean checkGuest);

	public boolean isCheckGuest();

	/**
	 * @deprecated As of 6.1, renamed to {@link #isGroupAdmin(long)}
	 */
	public boolean isCommunityAdmin(long groupId);

	/**
	 * @deprecated As of 6.1, renamed to {@link #isGroupOwner(long)}
	 */
	public boolean isCommunityOwner(long groupId);

	public boolean isCompanyAdmin();

	public boolean isCompanyAdmin(long companyId);

	public boolean isGroupAdmin(long groupId);

	public boolean isGroupOwner(long groupId);

	public boolean isOmniadmin();

	public boolean isSignedIn();

	/**
	 * @deprecated Does nothing
	 */
	public void resetValues();

	public void setCheckGuest(boolean checkGuest);

	/**
	 * @deprecated Does nothing
	 */
	public void setValues(PortletRequest portletRequest);

}