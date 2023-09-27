/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.display;

import com.liferay.account.admin.web.internal.security.permission.resource.AccountRolePermission;
import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Locale;

/**
 * @author Pei-Jung Lan
 */
public class AccountRoleDisplay {

	public static AccountRoleDisplay of(AccountRole accountRole)
		throws Exception {

		if (accountRole == null) {
			return null;
		}

		return new AccountRoleDisplay(accountRole);
	}

	public long getAccountRoleId() {
		return _accountRole.getAccountRoleId();
	}

	public String getDescription(Locale locale) {
		return _role.getDescription(locale);
	}

	public String getName(Locale locale) throws Exception {
		return _role.getTitle(locale);
	}

	public Role getRole() {
		return _role;
	}

	public long getRoleId() {
		return _role.getRoleId();
	}

	public String getTypeLabel(Locale locale) {
		if (_accountRole.getAccountEntryId() ==
				AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT) {

			return LanguageUtil.get(locale, "shared");
		}

		return LanguageUtil.get(locale, "owned");
	}

	public boolean isShowRowURL(PermissionChecker permissionChecker) {
		if (AccountRoleConstants.isSharedRole(getRole()) &&
			!AccountRolePermission.contains(
				permissionChecker, getAccountRoleId(),
				AccountActionKeys.ASSIGN_USERS)) {

			return false;
		}

		if (!AccountRolePermission.contains(
				permissionChecker, getAccountRoleId(),
				AccountActionKeys.ASSIGN_USERS) &&
			!AccountRolePermission.contains(
				permissionChecker, getAccountRoleId(),
				ActionKeys.DEFINE_PERMISSIONS) &&
			!AccountRolePermission.contains(
				permissionChecker, getAccountRoleId(), ActionKeys.UPDATE)) {

			return false;
		}

		return true;
	}

	private AccountRoleDisplay(AccountRole accountRole) throws Exception {
		_accountRole = accountRole;

		_role = accountRole.getRole();
	}

	private final AccountRole _accountRole;
	private final Role _role;

}