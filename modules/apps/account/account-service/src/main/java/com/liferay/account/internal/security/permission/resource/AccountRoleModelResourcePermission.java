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

package com.liferay.account.internal.security.permission.resource;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.permission.RolePermission;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.account.model.AccountRole",
	service = {
		AccountRoleModelResourcePermission.class, ModelResourcePermission.class
	}
)
public class AccountRoleModelResourcePermission
	implements ModelResourcePermission<AccountRole> {

	@Override
	public void check(
			PermissionChecker permissionChecker, AccountRole accountRole,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, accountRole, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AccountRole.class.getName(),
				accountRole.getAccountRoleId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long accountRoleId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, accountRoleId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AccountRole.class.getName(), accountRoleId,
				actionId);
		}
	}

	public void checkWithAccountEntry(
			long accountEntryId, PermissionChecker permissionChecker,
			long accountRoleId, String actionId)
		throws PortalException {

		try (SafeCloseable safeCloseable =
				_accountEntryIdThreadLocal.setWithSafeCloseable(
					accountEntryId)) {

			check(permissionChecker, accountRoleId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, AccountRole accountRole,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker, accountRole.getAccountRoleId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long accountRoleId,
			String actionId)
		throws PortalException {

		Group group = null;

		long contextAccountEntryId = _accountEntryIdThreadLocal.get();

		if (contextAccountEntryId > 0) {
			AccountEntry accountEntry =
				_accountEntryLocalService.getAccountEntry(
					contextAccountEntryId);

			group = accountEntry.getAccountEntryGroup();
		}

		AccountRole accountRole = _accountRoleLocalService.fetchAccountRole(
			accountRoleId);

		if (accountRole == null) {
			return permissionChecker.hasPermission(
				group, AccountRole.class.getName(), 0L, actionId);
		}

		Role role = accountRole.getRole();

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(), AccountRole.class.getName(),
				accountRoleId, role.getUserId(), actionId)) {

			return true;
		}

		long accountRoleAccountEntryId = accountRole.getAccountEntryId();

		if ((accountRoleAccountEntryId >
				AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT) &&
			(contextAccountEntryId >
				AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT) &&
			!Objects.equals(accountRoleAccountEntryId, contextAccountEntryId)) {

			return false;
		}

		for (long accountEntryId :
				new long[] {accountRoleAccountEntryId, contextAccountEntryId}) {

			if ((Objects.equals(actionId, ActionKeys.VIEW) &&
				 (accountEntryId > 0) &&
				 _accountEntryModelResourcePermission.contains(
					 permissionChecker, accountEntryId,
					 AccountActionKeys.VIEW_ACCOUNT_ROLES)) ||
				_rolePermission.contains(
					permissionChecker, role.getRoleId(), ActionKeys.VIEW)) {

				return true;
			}
		}

		if ((group == null) && (accountRoleAccountEntryId > 0)) {
			AccountEntry accountEntry =
				_accountEntryLocalService.getAccountEntry(
					contextAccountEntryId);

			group = accountEntry.getAccountEntryGroup();
		}

		return permissionChecker.hasPermission(
			group, AccountRole.class.getName(), accountRoleId, actionId);
	}

	public boolean containsWithAccountEntry(
			long accountEntryId, PermissionChecker permissionChecker,
			long accountRoleId, String actionId)
		throws PortalException {

		try (SafeCloseable safeCloseable =
				_accountEntryIdThreadLocal.setWithSafeCloseable(
					accountEntryId)) {

			return contains(permissionChecker, accountRoleId, actionId);
		}
	}

	@Override
	public String getModelName() {
		return AccountRole.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	private final CentralizedThreadLocal<Long> _accountEntryIdThreadLocal =
		new CentralizedThreadLocal<>(
			AccountRoleModelResourcePermission.class + "._accountEntryId",
			() -> AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference(
		target = "(resource.name=" + AccountConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private RolePermission _rolePermission;

}