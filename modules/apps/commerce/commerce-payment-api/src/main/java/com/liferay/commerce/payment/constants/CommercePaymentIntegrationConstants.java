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