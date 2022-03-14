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

package com.liferay.commerce.payment.web.internal.display;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;

/**
 * @author Andrea Sbarra
 */
public class CommerceAccountEntryDisplay {

	public static CommerceAccountEntryDisplay of(AccountEntry accountEntry) {
		if (accountEntry != null) {
			return new CommerceAccountEntryDisplay(accountEntry);
		}

		return _EMPTY_INSTANCE;
	}

	public static CommerceAccountEntryDisplay of(long accountEntryId) {
		return of(
			AccountEntryLocalServiceUtil.fetchAccountEntry(accountEntryId));
	}

	public AccountEntry getAccountEntry() {
		return _accountEntry;
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public String getDefaultCommercePaymentMethodKey() {
		return _defaultCommercePaymentMethodKey;
	}

	private CommerceAccountEntryDisplay() {
		_accountEntry = null;
		_accountEntryId = 0;
		_defaultCommercePaymentMethodKey = StringPool.BLANK;
	}

	private CommerceAccountEntryDisplay(AccountEntry accountEntry) {
		_accountEntry = accountEntry;

		_accountEntryId = accountEntry.getAccountEntryId();
		_defaultCommercePaymentMethodKey =
			accountEntry.getDefaultCPaymentMethodKey();
	}

	private static final CommerceAccountEntryDisplay _EMPTY_INSTANCE =
		new CommerceAccountEntryDisplay();

	private final AccountEntry _accountEntry;
	private final long _accountEntryId;
	private final String _defaultCommercePaymentMethodKey;

}