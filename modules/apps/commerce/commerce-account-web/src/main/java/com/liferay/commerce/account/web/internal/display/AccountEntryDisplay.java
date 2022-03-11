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

package com.liferay.commerce.account.web.internal.display;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;

/**
 * @author Andrea Sbarra
 */
public class AccountEntryDisplay {

	public static AccountEntryDisplay of(AccountEntry accountEntry) {
		if (accountEntry != null) {
			return new AccountEntryDisplay(accountEntry);
		}

		return new AccountEntryDisplay();
	}

	public static AccountEntryDisplay of(long accountEntryId) {
		return of(
			AccountEntryLocalServiceUtil.fetchAccountEntry(accountEntryId));
	}

	public AccountEntry getAccountEntry() {
		return _accountEntry;
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public String getName() {
		return _name;
	}

	private AccountEntryDisplay() {
		_accountEntry = null;
		_accountEntryId = 0;
		_name = StringPool.BLANK;
	}

	private AccountEntryDisplay(AccountEntry accountEntry) {
		_accountEntry = accountEntry;

		_accountEntryId = accountEntry.getAccountEntryId();

		_name = accountEntry.getName();
	}

	private final AccountEntry _accountEntry;
	private final long _accountEntryId;
	private final String _name;

}