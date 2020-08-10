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
public class CommercePricingConstants {

	public static final String DISCOUNT_ADDITION_METHOD = "addition";

	public static final String DISCOUNT_CHAIN_METHOD = "chain";

	public static final String ORDER_BY_HIERARCHY = "hierarchy";

	public static final String ORDER_BY_LOWEST_ENTRY = "lowest";

	public static final String SERVICE_NAME =
		"com.liferay.commerce.pricing.service.name";

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static final int TAX_EXCLUDED_FROM_FINAL_PRICE = 1;

	public static final String TAX_EXCLUDED_FROM_PRICE = "tax-excluded";

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static final int TAX_INCLUDED_IN_FINAL_PRICE = 0;

	public static final String TAX_INCLUDED_IN_PRICE = "tax-included";

	public static final String VERSION_1_0 = "v1.0";

	public static final String VERSION_2_0 = "v2.0";

}