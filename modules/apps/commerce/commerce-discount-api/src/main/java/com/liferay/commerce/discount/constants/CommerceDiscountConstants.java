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

package com.liferay.commerce.discount.constants;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceDiscountConstants {

	public static final String LEVEL_L1 = "L1";

	public static final String LEVEL_L2 = "L2";

	public static final String LEVEL_L3 = "L3";

	public static final String LEVEL_L4 = "L4";

	public static final String[] LEVELS = {
		LEVEL_L1, LEVEL_L2, LEVEL_L3, LEVEL_L4
	};

	public static final String LIMITATION_TYPE_LIMITED = "limited";

	public static final String LIMITATION_TYPE_LIMITED_FOR_ACCOUNTS =
		"limited-for-accounts";

	public static final String LIMITATION_TYPE_LIMITED_FOR_ACCOUNTS_AND_TOTAL =
		"limited-for-accounts-and-total";

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static final String LIMITATION_TYPE_LIMITED_FOR_USERS =
		"limited-for-users";

	public static final String LIMITATION_TYPE_UNLIMITED = "unlimited";

	public static final String[] LIMITATION_TYPES = {
		LIMITATION_TYPE_UNLIMITED, LIMITATION_TYPE_LIMITED,
		LIMITATION_TYPE_LIMITED_FOR_ACCOUNTS,
		LIMITATION_TYPE_LIMITED_FOR_ACCOUNTS_AND_TOTAL
	};

	public static final String RESOURCE_NAME = "com.liferay.commerce.discount";

	public static final String TARGET_CATEGORIES = "categories";

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static final String TARGET_PRICING_CLASS = "product-groups";

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static final String TARGET_PRODUCT = "products";

	public static final String TARGET_PRODUCT_GROUPS = "product-groups";

	public static final String TARGET_PRODUCTS = "products";

	public static final String TARGET_SHIPPING = "shipping";

	public static final String TARGET_SUBTOTAL = "subtotal";

	public static final String TARGET_TOTAL = "total";

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static final String TYPE_ABSOLUTE = "fixed-amount";

	public static final String TYPE_FIXED_AMOUNT = "fixed-amount";

	public static final String TYPE_PERCENTAGE = "percentage";

	public static final String[] TYPES = {TYPE_PERCENTAGE, TYPE_FIXED_AMOUNT};

	public static final String VALIDATOR_TYPE_POST_QUALIFICATION =
		"post-qualification";

	public static final String VALIDATOR_TYPE_PRE_QUALIFICATION =
		"pre-qualification";

}