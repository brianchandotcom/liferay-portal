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

package com.liferay.account.admin.web.internal.display;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;

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

	private AccountRoleDisplay(AccountRole accountRole) throws Exception {
		_accountRole = accountRole;

		_role = accountRole.getRole();
	}

	private final AccountRole _accountRole;
	private final Role _role;

}