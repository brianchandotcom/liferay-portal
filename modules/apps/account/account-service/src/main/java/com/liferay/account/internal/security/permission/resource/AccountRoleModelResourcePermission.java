/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.security.permission.resource;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.role.AccountRolePermissionThreadLocal;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountRole",
	service = ModelResourcePermission.class
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

		long contextAccountEntryId =
			AccountRolePermissionThreadLocal.getAccountEntryId();

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
				RolePermissionUtil.contains(
					permissionChecker, role.getRoleId(), ActionKeys.VIEW)) {

				return true;
			}

			if (Objects.equals(actionId, AccountActionKeys.ASSIGN_USERS) &&
				(accountEntryId > 0) &&
				_accountEntryModelResourcePermission.contains(
					permissionChecker, accountEntryId,
					ActionKeys.MANAGE_USERS)) {

				return true;
			}
		}

		if ((group == null) && (accountRoleAccountEntryId > 0)) {
			AccountEntry accountEntry =
				_accountEntryLocalService.getAccountEntry(
					accountRoleAccountEntryId);

			group = accountEntry.getAccountEntryGroup();
		}

		return permissionChecker.hasPermission(
			group, AccountRole.class.getName(), accountRoleId, actionId);
	}

	@Override
	public String getModelName() {
		return AccountRole.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference(
		target = "(resource.name=" + AccountConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}