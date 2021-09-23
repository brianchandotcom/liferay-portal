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

package com.liferay.commerce.util;

import com.liferay.account.constants.AccountConstants;
import com.liferay.commerce.account.constants.CommerceAccountConstants;

/**
 * @author Luca Pellizzon
 */
public class AccountEntryAllowedTypesUtil {

	public static String[] getAllowedTypes(int commerceSiteType) {
		if (commerceSiteType == CommerceAccountConstants.SITE_TYPE_B2B) {
			return new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS};
		}

		if (commerceSiteType == CommerceAccountConstants.SITE_TYPE_B2C) {
			return new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON};
		}

		if (commerceSiteType == CommerceAccountConstants.SITE_TYPE_B2X) {
			return new String[] {
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
				AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
			};
		}

		return AccountConstants.ACCOUNT_ENTRY_TYPES_DEFAULT_ALLOWED_TYPES;
	}

}