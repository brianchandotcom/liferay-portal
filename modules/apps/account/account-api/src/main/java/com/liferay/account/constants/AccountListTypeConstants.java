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