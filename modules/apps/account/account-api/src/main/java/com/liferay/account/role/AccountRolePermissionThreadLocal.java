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