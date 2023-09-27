/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.constants;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentMethodConstants {

	public static final String SERVLET_PATH = "commerce-payment";

	public static final int TYPE_OFFLINE = 2;

	public static final int TYPE_ONLINE_REDIRECT = 1;

	public static final int TYPE_ONLINE_STANDARD = 0;

	public static final int[] TYPES_ONLINE = {
		TYPE_ONLINE_STANDARD, TYPE_ONLINE_REDIRECT
	};

}