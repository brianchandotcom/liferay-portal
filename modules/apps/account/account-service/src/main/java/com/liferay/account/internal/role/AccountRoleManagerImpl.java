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

package com.liferay.account.internal.role;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.role.AccountRoleManager;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = AccountRoleManager.class)
public class AccountRoleManagerImpl implements AccountRoleManager {

	@Override
	public AccountRole addAccountRole(
			long userId, long accountEntryId, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap)
		throws PortalException {

		return _accountRoleLocalService.addAccountRole(
			userId, accountEntryId, name, titleMap, descriptionMap);
	}

	@Override
	public void associateUser(long accountEntryId, long roleId, long userId)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntryId);

		_userGroupRoleLocalService.addUserGroupRoles(
			userId, accountEntry.getAccountEntryGroupId(), new long[] {roleId});
	}

	@Override
	public List<AccountRole> getAccountRoles(long accountEntryId, long userId)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntryId);

		return TransformUtil.transform(
			_userGroupRoleLocalService.getUserGroupRoles(
				userId, accountEntry.getAccountEntryGroupId()),
			userGroupRole -> _accountRoleLocalService.getAccountRoleByRoleId(
				userGroupRole.getRoleId()));
	}

	@Override
	public List<AccountRole> getAccountRoles(
		long companyId, long[] accountEntryIds) {

		return _accountRoleLocalService.getAccountRolesByAccountEntryIds(
			accountEntryIds);
	}

	@Override
	public void unassociateUser(long accountEntryId, long roleId, long userId)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntryId);

		_userGroupRoleLocalService.deleteUserGroupRoles(
			userId, accountEntry.getAccountEntryGroupId(), new long[] {roleId});
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}