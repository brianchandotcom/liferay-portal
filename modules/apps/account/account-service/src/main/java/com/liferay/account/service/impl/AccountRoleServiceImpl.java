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

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.role.AccountRolePermissionThreadLocal;
import com.liferay.account.service.base.AccountRoleServiceBaseImpl;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.permission.PortalPermission;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=account",
		"json.web.service.context.path=AccountRole"
	},
	service = AopService.class
)
public class AccountRoleServiceImpl extends AccountRoleServiceBaseImpl {

	@Override
	public AccountRole addAccountRole(
			long accountEntryId, String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (accountEntryId > 0) {
			_accountEntryModelResourcePermission.check(
				permissionChecker, accountEntryId,
				AccountActionKeys.ADD_ACCOUNT_ROLE);
		}
		else {
			_portalPermission.check(permissionChecker, ActionKeys.ADD_ROLE);
		}

		return accountRoleLocalService.addAccountRole(
			permissionChecker.getUserId(), accountEntryId, name, titleMap,
			descriptionMap);
	}

	@Override
	public void associateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws PortalException {

		try (SafeCloseable safeCloseable =
				AccountRolePermissionThreadLocal.setWithSafeCloseable(
					accountEntryId)) {

			_accountRoleModelResourcePermission.check(
				getPermissionChecker(), accountRoleId,
				AccountActionKeys.ASSIGN_USERS);
		}

		accountRoleLocalService.associateUser(
			accountEntryId, accountRoleId, userId);
	}

	@Override
	public void associateUser(
			long accountEntryId, long[] accountRoleIds, long userId)
		throws PortalException {

		for (long accountRoleId : accountRoleIds) {
			associateUser(accountEntryId, accountRoleId, userId);
		}
	}

	@Override
	public AccountRole deleteAccountRole(AccountRole accountRole)
		throws PortalException {

		_accountRoleModelResourcePermission.check(
			getPermissionChecker(), accountRole, ActionKeys.DELETE);

		return accountRoleLocalService.deleteAccountRole(accountRole);
	}

	@Override
	public AccountRole deleteAccountRole(long accountRoleId)
		throws PortalException {

		_accountRoleModelResourcePermission.check(
			getPermissionChecker(), accountRoleId, ActionKeys.DELETE);

		return accountRoleLocalService.deleteAccountRole(accountRoleId);
	}

	@Override
	public AccountRole getAccountRoleByRoleId(long roleId)
		throws PortalException {

		AccountRole accountRole =
			accountRoleLocalService.getAccountRoleByRoleId(roleId);

		_accountRoleModelResourcePermission.check(
			getPermissionChecker(), accountRole, ActionKeys.VIEW);

		return accountRole;
	}

	@Override
	public BaseModelSearchResult<AccountRole> searchAccountRoles(
			long companyId, long[] accountEntryIds, String keywords,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator<?> orderByComparator)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (params == null) {
			params = new LinkedHashMap<>();
		}

		params.put("permissionUserId", permissionChecker.getUserId());

		return accountRoleLocalService.searchAccountRoles(
			companyId, accountEntryIds, keywords, params, start, end,
			orderByComparator);
	}

	@Override
	public void setUserAccountRoles(
			long accountEntryId, long[] accountRoleIds, long userId)
		throws PortalException {

		try (SafeCloseable safeCloseable =
				AccountRolePermissionThreadLocal.setWithSafeCloseable(
					accountEntryId)) {

			for (long accountRoleId : accountRoleIds) {
				_accountRoleModelResourcePermission.check(
					getPermissionChecker(), accountRoleId,
					AccountActionKeys.ASSIGN_USERS);
			}
		}

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.MANAGE_USERS);

		accountRoleLocalService.setUserAccountRoles(
			accountEntryId, accountRoleIds, userId);
	}

	@Override
	public void unassociateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws PortalException {

		try (SafeCloseable safeCloseable =
				AccountRolePermissionThreadLocal.setWithSafeCloseable(
					accountEntryId)) {

			_accountRoleModelResourcePermission.check(
				getPermissionChecker(), accountRoleId,
				AccountActionKeys.ASSIGN_USERS);
		}

		accountRoleLocalService.unassociateUser(
			accountEntryId, accountRoleId, userId);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountRole)"
	)
	private ModelResourcePermission<AccountRole>
		_accountRoleModelResourcePermission;

	@Reference
	private PortalPermission _portalPermission;

}