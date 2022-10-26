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
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountGroup",
	service = ModelResourcePermission.class
)
public class AccountGroupModelResourcePermission
	implements ModelResourcePermission<AccountGroup> {

	@Override
	public void check(
			PermissionChecker permissionChecker, AccountGroup accountGroup,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, accountGroup, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AccountEntry.class.getName(),
				accountGroup.getAccountGroupId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long accountGroupId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, accountGroupId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AccountEntry.class.getName(), accountGroupId,
				actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, AccountGroup accountGroup,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker, accountGroup.getAccountGroupId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long accountGroupId,
			String actionId)
		throws PortalException {

		AccountGroup accountGroup = _accountGroupLocalService.fetchAccountGroup(
			accountGroupId);

		if ((accountGroup != null) &&
			permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(), AccountGroup.class.getName(),
				accountGroupId, accountGroup.getUserId(), actionId)) {

			return true;
		}

		if (Objects.equals(actionId, ActionKeys.VIEW)) {
			for (AccountGroupRel accountGroupRel :
					_accountGroupRelLocalService.
						getAccountGroupRelsByAccountGroupId(accountGroupId)) {

				if (Objects.equals(
						accountGroupRel.getClassName(),
						AccountEntry.class.getName()) &&
					_accountEntryModelResourcePermission.contains(
						permissionChecker, accountGroupRel.getClassPK(),
						AccountActionKeys.VIEW_ACCOUNT_GROUPS)) {

					return true;
				}
			}
		}

		return permissionChecker.hasPermission(
			null, AccountGroup.class.getName(), accountGroupId, actionId);
	}

	@Override
	public String getModelName() {
		return AccountGroup.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference(
		target = "(resource.name=" + AccountConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}