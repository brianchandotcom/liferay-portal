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

	public static final String ACCOUNT_ENTRY_CONTACT_ADDRESS =
		AccountEntry.class.getName() + ".contact" + ListTypeConstants.ADDRESS;

	public static final String ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_BILLING =
		"billing";

	public static final String ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_OTHER =
		"other";

	public static final String ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_P_O_BOX =
		"p-o-box";

	public static final String ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_SHIPPING =
		"shipping";

	public static final String ACCOUNT_ENTRY_EMAIL_ADDRESS =
		AccountEntry.class.getName() + ListTypeConstants.EMAIL_ADDRESS;

	public static final String ACCOUNT_ENTRY_EMAIL_ADDRESS_TYPE_EMAIL_ADDRESS =
		"email-address";

	public static final String
		ACCOUNT_ENTRY_EMAIL_ADDRESS_TYPE_EMAIL_ADDRESS_2 = "email-address-2";

	public static final String
		ACCOUNT_ENTRY_EMAIL_ADDRESS_TYPE_EMAIL_ADDRESS_3 = "email-address-3";

	public static final String ACCOUNT_ENTRY_PHONE =
		AccountEntry.class.getName() + ListTypeConstants.PHONE;

	public static final String ACCOUNT_ENTRY_PHONE_TYPE_FAX = "fax";

	public static final String ACCOUNT_ENTRY_PHONE_TYPE_LOCAL = "local";

	public static final String ACCOUNT_ENTRY_PHONE_TYPE_OTHER = "other";

	public static final String ACCOUNT_ENTRY_PHONE_TYPE_TOLL_FREE = "toll-free";

	public static final String ACCOUNT_ENTRY_PHONE_TYPE_TTY = "tty";

	public static final String ACCOUNT_ENTRY_WEBSITE =
		AccountEntry.class.getName() + ListTypeConstants.WEBSITE;

	public static final String ACCOUNT_ENTRY_WEBSITE_TYPE_INTRANET = "intranet";

	public static final String ACCOUNT_ENTRY_WEBSITE_TYPE_PUBLIC = "public";

}