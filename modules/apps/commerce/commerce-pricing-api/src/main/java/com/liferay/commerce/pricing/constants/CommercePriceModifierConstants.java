/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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