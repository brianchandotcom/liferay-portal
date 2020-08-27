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

package com.liferay.commerce.pricing.constants;

/**
 * @author Riccardo Alberti
 * @author Alessio Antonio Rendina
 */
public class CommercePriceModifierConstants {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static final String MODIFIER_TYPE_ABSOLUTE = "fixed-amount";

	public static final String MODIFIER_TYPE_FIXED_AMOUNT = "fixed-amount";

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static final String MODIFIER_TYPE_OVERRIDE = "replace";

	public static final String MODIFIER_TYPE_PERCENTAGE = "percentage";

	public static final String MODIFIER_TYPE_REPLACE = "replace";

	public static final String TARGET_CATALOG = "catalog";

	public static final String TARGET_CATEGORIES = "categories";

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static final String TARGET_PRICING_CLASS = "product-groups";

	public static final String TARGET_PRODUCT_GROUPS = "product-groups";

	public static final String TARGET_PRODUCTS = "products";

	public static final String[] TARGETS = {
		TARGET_CATALOG, TARGET_CATEGORIES, TARGET_PRODUCT_GROUPS,
		TARGET_PRODUCTS
	};

}