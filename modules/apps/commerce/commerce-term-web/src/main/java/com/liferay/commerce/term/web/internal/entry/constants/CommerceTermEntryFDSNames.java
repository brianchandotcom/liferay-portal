/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.web.internal.entry.constants;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.commerce.term.constants.CommerceTermEntryPortletKeys;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTermEntryFDSNames {

	public static final String ACCOUNT_ENTRY_DELIVERY_TERM_ENTRIES =
		AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN +
			"-accountEntryDeliveryTermEntries";

	public static final String ACCOUNT_ENTRY_PAYMENT_TERM_ENTRIES =
		AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN +
			"-accountEntryPaymentTermEntries";

	public static final String TERM_ENTRIES =
		CommerceTermEntryPortletKeys.COMMERCE_TERM_ENTRY + "-termEntries";

	public static final String TERM_ENTRY_QUALIFIER_COMMERCE_ORDER_TYPES =
		CommerceTermEntryPortletKeys.COMMERCE_TERM_ENTRY +
			"-termEntryQualifierCommerceOrderTypes";

}