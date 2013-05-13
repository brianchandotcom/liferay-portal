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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Account;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.AccountLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AccountPermissionImpl implements AccountPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, Account account,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, account, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long accountId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, accountId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, Account account, String actionId) {

		//long groupId = account.getGroupId();
		long groupId = 0;

		return permissionChecker.hasPermission(
			groupId, Account.class.getName(), account.getAccountId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long accountId,
			String actionId)
		throws PortalException, SystemException {

		Account account = AccountLocalServiceUtil.getAccount(accountId);

		return contains(permissionChecker, account, actionId);
	}

}