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

package com.liferay.commerce.discount.model;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceDiscountConstants {

	public static final String LIMITATION_TYPE_LIMITED = "limited";

	public static final String LIMITATION_TYPE_LIMITED_FOR_USERS =
		"limited-for-users";

	public static final String LIMITATION_TYPE_UNLIMITED = "unlimited";

	public static final String[] LIMITATION_TYPES = {
		LIMITATION_TYPE_UNLIMITED, LIMITATION_TYPE_LIMITED,
		LIMITATION_TYPE_LIMITED_FOR_USERS
	};

	public static final String TARGET_CATEGORIES = "categories";

	public static final String TARGET_PRODUCT = "product";

	public static final String TARGET_SHIPMENT = "shipment";

	public static final String TARGET_SUBTOTAL = "subtotal";

	public static final String TARGET_TOTAL = "total";

}