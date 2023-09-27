/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.role;

import com.liferay.account.role.AccountRole;
import com.liferay.portal.kernel.model.Role;

/**
 * @author Drew Brokke
 */
public class AccountRoleImpl implements AccountRole {

	public AccountRoleImpl(Role role) {
		_role = role;
	}

	@Override
	public String getName() {
		return _role.getName();
	}

	@Override
	public long getRoleId() {
		return _role.getRoleId();
	}

	private final Role _role;

}