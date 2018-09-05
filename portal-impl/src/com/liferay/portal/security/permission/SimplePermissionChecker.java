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

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.UserBag;

/**
 * @author Brian Wing Shun Chan
 */
public class SimplePermissionChecker extends BasePermissionChecker {

	@Override
	public SimplePermissionChecker clone() {
		return new SimplePermissionChecker();
	}

	@Override
	public UserBag getUserBag() {
		return null;
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, String primKey, long ownerId,
		String actionId) {

		return hasPermission(actionId);
	}

	@Override
	public boolean hasPermission(
		Group group, String name, String primKey, String actionId) {

		return hasPermission(actionId);
	}

	@Override
	public boolean isCompanyAdmin() {
		return signedIn;
	}

	@Override
	public boolean isCompanyAdmin(long companyId) {
		return signedIn;
	}

	@Override
	public boolean isContentReviewer(long companyId, long groupId) {
		return signedIn;
	}

	@Override
	public boolean isGroupAdmin(long groupId) {
		return signedIn;
	}

	@Override
	public boolean isGroupMember(long groupId) {
		return signedIn;
	}

	@Override
	public boolean isGroupOwner(long groupId) {
		return signedIn;
	}

	@Override
	public boolean isOrganizationAdmin(long organizationId) {
		return signedIn;
	}

	@Override
	public boolean isOrganizationOwner(long organizationId) {
		return signedIn;
	}

	protected boolean hasPermission(String actionId) {
		if (signedIn) {
			return true;
		}

		if (actionId.equals(ActionKeys.VIEW)) {
			return true;
		}

		return false;
	}

}