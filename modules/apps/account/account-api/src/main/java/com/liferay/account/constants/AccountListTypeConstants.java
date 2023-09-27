/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.constants;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.model.ListTypeConstants;

/**
 * @author Pei-Jung Lan
 */
public class AccountListTypeConstants {

	public static final String ACCOUNT_ENTRY_ADDRESS =
		AccountEntry.class.getName() + ListTypeConstants.ADDRESS;

	public static final String ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING = "billing";

	public static final String ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING_AND_SHIPPING =
		"billing-and-shipping";

	public static final String ACCOUNT_ENTRY_ADDRESS_TYPE_SHIPPING = "shipping";

}