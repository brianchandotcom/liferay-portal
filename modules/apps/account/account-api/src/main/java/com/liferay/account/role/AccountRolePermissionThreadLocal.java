/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.role;

import com.liferay.account.constants.AccountConstants;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Drew Brokke
 */
public class AccountRolePermissionThreadLocal {

	public static long getAccountEntryId() {
		return _accountEntryId.get();
	}

	public static void setAccountEntryId(long accountEntryId) {
		_accountEntryId.set(accountEntryId);
	}

	public static SafeCloseable setWithSafeCloseable(long accountEntryId) {
		return _accountEntryId.setWithSafeCloseable(accountEntryId);
	}

	private static final CentralizedThreadLocal<Long> _accountEntryId =
		new CentralizedThreadLocal<>(
			AccountRolePermissionThreadLocal.class + "._accountEntryId",
			() -> AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);

}