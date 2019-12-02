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

package com.liferay.account.internal.instance.lifecycle;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddDefaultAccountRolesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		User defaultUser = company.getDefaultUser();

		if (!_exists(AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_USER)) {
			AccountRole accountRole = _addAccountRole(
				defaultUser.getUserId(),
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_USER);

			_addResourcePermissions(
				accountRole.getRoleId(), _accountUserResourceActionsMap);
		}

		if (!_exists(
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_POWER_USER)) {

			AccountRole accountRole = _addAccountRole(
				defaultUser.getUserId(),
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_POWER_USER);

			_addResourcePermissions(
				accountRole.getRoleId(), _accountUserResourceActionsMap);
			_addResourcePermissions(
				accountRole.getRoleId(), _accountPowerUserResourceActionsMap);
		}

		if (!_exists(AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_OWNER)) {
			_addRole(
				defaultUser.getUserId(),
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_OWNER);
		}

		if (!_exists(
				AccountRoleConstants.
					REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR)) {

			_addRole(
				defaultUser.getUserId(),
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR);
		}
	}

	private AccountRole _addAccountRole(long userId, String roleName)
		throws PortalException {

		return _accountRoleLocalService.addAccountRole(
			userId, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, roleName,
			HashMapBuilder.<Locale, String>put(
				LocaleThreadLocal.getDefaultLocale(), roleName
			).build(),
			null);
	}

	private void _addResourcePermissions(
			long roleId, Map<String, String[]> resourceActionsMap)
		throws PortalException {

		for (Map.Entry<String, String[]> entry :
				resourceActionsMap.entrySet()) {

			for (String resourceAction : entry.getValue()) {
				String resourceName = entry.getKey();

				_resourcePermissionLocalService.addResourcePermission(
					CompanyThreadLocal.getCompanyId(), resourceName,
					ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", roleId,
					resourceAction);
			}
		}
	}

	private void _addRole(long userId, String roleName) throws PortalException {
		_roleLocalService.addRole(
			userId, null, 0, roleName,
			HashMapBuilder.<Locale, String>put(
				LocaleThreadLocal.getDefaultLocale(), roleName
			).build(),
			null, RoleConstants.TYPE_REGULAR, null, null);
	}

	private boolean _exists(String roleName) {
		Role role = _roleLocalService.fetchRole(
			CompanyThreadLocal.getCompanyId(), roleName);

		if (role != null) {
			return true;
		}

		return false;
	}

	private static final Map<String, String[]>
		_accountPowerUserResourceActionsMap = HashMapBuilder.put(
			AccountConstants.RESOURCE_NAME,
			new String[] {AccountActionKeys.ADD_ACCOUNT_ENTRY}
		).put(
			AccountEntry.class.getName(),
			new String[] {ActionKeys.UPDATE, ActionKeys.MANAGE_USERS}
		).build();
	private static final Map<String, String[]> _accountUserResourceActionsMap =
		HashMapBuilder.put(
			AccountEntry.class.getName(), new String[] {ActionKeys.VIEW}
		).build();

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}