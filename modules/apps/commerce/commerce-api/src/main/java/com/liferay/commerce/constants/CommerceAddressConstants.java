/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.constants;

/**
 * @author Alec Sloan
 */
public class CommerceAddressConstants {

	public static final int ADDRESS_TYPE_BILLING = 1;

	public static final int ADDRESS_TYPE_BILLING_AND_SHIPPING = 2;

	public static final int ADDRESS_TYPE_SHIPPING = 3;

	public static final int[] ADDRESS_TYPES = {
		ADDRESS_TYPE_BILLING, ADDRESS_TYPE_BILLING_AND_SHIPPING,
		ADDRESS_TYPE_SHIPPING
	};

	public static String getAddressTypeLabel(int type) {
		if (type == ADDRESS_TYPE_BILLING) {
			return "billing";
		}
		else if (type == ADDRESS_TYPE_BILLING_AND_SHIPPING) {
			return "billing-and-shipping";
		}
		else if (type == ADDRESS_TYPE_SHIPPING) {
			return "shipping";
		}

		return null;
	}

}