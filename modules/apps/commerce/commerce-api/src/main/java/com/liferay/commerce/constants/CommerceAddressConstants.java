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