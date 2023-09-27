/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		if (signedIn || actionId.equals(ActionKeys.VIEW)) {
			return true;
		}

		return false;
	}

}