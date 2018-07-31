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

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Account;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class AccountPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, Account account,
			String actionId)
		throws PortalException {

		getAccountPermission().check(permissionChecker, account, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long accountId,
			String actionId)
		throws PortalException {

		getAccountPermission().check(permissionChecker, accountId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Account account,
			String actionId)
		throws PortalException {

		return getAccountPermission().contains(
			permissionChecker, account, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long accountId,
			String actionId)
		throws PortalException {

		return getAccountPermission().contains(
			permissionChecker, accountId, actionId);
	}

	public static AccountPermission getAccountPermission() {
		return _accountPermission;
	}

	public void setAccountPermission(AccountPermission accountPermission) {
		_accountPermission = accountPermission;
	}

	private static AccountPermission _accountPermission;

}