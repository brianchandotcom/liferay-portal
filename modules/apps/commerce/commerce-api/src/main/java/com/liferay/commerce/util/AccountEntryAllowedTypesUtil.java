/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util;

import com.liferay.account.constants.AccountConstants;
import com.liferay.commerce.product.constants.CommerceChannelConstants;

/**
 * @author Luca Pellizzon
 */
public class AccountEntryAllowedTypesUtil {

	public static String[] getAllowedTypes(int commerceSiteType) {
		if (commerceSiteType == CommerceChannelConstants.SITE_TYPE_B2B) {
			return new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS};
		}

		if (commerceSiteType == CommerceChannelConstants.SITE_TYPE_B2C) {
			return new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON};
		}

		if (commerceSiteType == CommerceChannelConstants.SITE_TYPE_B2X) {
			return new String[] {
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
				AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
			};
		}

		return AccountConstants.ACCOUNT_ENTRY_TYPES_DEFAULT_ALLOWED_TYPES;
	}

}