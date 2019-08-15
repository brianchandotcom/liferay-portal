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
import com.liferay.account.role.AccountRole;
import com.liferay.account.role.AccountRoleManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalService;

import java.util.ArrayList;
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
			long userId, String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		Role role = _roleLocalService.addRole(
			userId, AccountEntry.class.getName(), 0,
			_ACCOUNT_ROLE_NAMESPACE + name, titleMap, descriptionMap,
			_TYPE_ACCOUNT, null, null);

		return new AccountRoleImpl(role);
	}

	@Override
	public List<AccountRole> getAccountRoles(long companyId) {
		List<AccountRole> accountRoles = new ArrayList<>();

		List<Role> roles = _roleLocalService.getRoles(
			companyId, new int[] {_TYPE_ACCOUNT});

		for (Role role : roles) {
			accountRoles.add(new AccountRoleImpl(role));
		}

		return accountRoles;
	}

	private static final String _ACCOUNT_ROLE_NAMESPACE = "lfr-account-";

	private static final int _TYPE_ACCOUNT = 501;

	@Reference
	private RoleLocalService _roleLocalService;

}