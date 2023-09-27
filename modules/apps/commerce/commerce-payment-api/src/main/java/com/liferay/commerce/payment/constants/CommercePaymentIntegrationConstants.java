/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.constants;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentIntegrationConstants {

	public static final int TYPE_FUNCTION_OFFLINE = 5;

	public static final int TYPE_FUNCTION_ONLINE_REDIRECT = 4;

	public static final int TYPE_FUNCTION_ONLINE_STANDARD = 3;

	public static final int TYPE_INTERNAL_OFFLINE = 2;

	public static final int TYPE_INTERNAL_ONLINE_REDIRECT = 1;

	public static final int TYPE_INTERNAL_ONLINE_STANDARD = 0;

	public static final int[] TYPES_FUNCTION = {
		TYPE_FUNCTION_OFFLINE, TYPE_FUNCTION_ONLINE_REDIRECT,
		TYPE_FUNCTION_ONLINE_STANDARD
	};

	public static final int[] TYPES_INTERNAL = {
		TYPE_INTERNAL_OFFLINE, TYPE_INTERNAL_ONLINE_REDIRECT,
		TYPE_INTERNAL_ONLINE_STANDARD
	};

}