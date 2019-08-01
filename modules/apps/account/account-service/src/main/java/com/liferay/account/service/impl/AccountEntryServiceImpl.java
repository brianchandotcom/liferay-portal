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

package com.liferay.account.service.impl;

import com.liferay.account.constants.AccountEntryActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.base.AccountEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=account",
		"json.web.service.context.path=AccountEntry"
	},
	service = AopService.class
)
public class AccountEntryServiceImpl extends AccountEntryServiceBaseImpl {

	@Override
	public AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, long logoId, int status)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), AccountEntryActionKeys.ADD_ACCOUNT_ENTRY);

		return accountEntryLocalService.addAccountEntry(
			userId, parentAccountEntryId, name, description, logoId, status);
	}

	@Override
	public List<AccountEntry> getAccountEntries(
			long companyId, int status, int start, int end,
			OrderByComparator<AccountEntry> obc)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.hasPermission(
				null, AccountEntry.class.getName(), companyId,
				AccountEntryActionKeys.VIEW)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, AccountEntry.class.getName(), 0,
				AccountEntryActionKeys.VIEW);
		}

		return accountEntryLocalService.getAccountEntries(
			companyId, status, start, end, obc);
	}

}