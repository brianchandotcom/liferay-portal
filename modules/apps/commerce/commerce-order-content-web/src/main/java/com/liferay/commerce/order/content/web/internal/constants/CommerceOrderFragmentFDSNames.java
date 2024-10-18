/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.constants;

import com.liferay.commerce.constants.CommerceFragmentRendererKeys;

/**
 * @author Gianmarco Brunialti Masera
 */
public class CommerceOrderFragmentFDSNames {

	public static final String PENDING_ORDER_ITEMS =
		CommerceFragmentRendererKeys.ORDER_ITEMS_DATA_SET +
			"-pendingOrderItems";

	public static final String PENDING_ORDERS =
		CommerceFragmentRendererKeys.ORDERS_DATA_SET + "-pendingOrders";

	public static final String PLACED_ORDER_ITEMS =
		CommerceFragmentRendererKeys.ORDER_ITEMS_DATA_SET + "-placedOrderItems";

	public static final String PLACED_ORDER_RETURNS =
		CommerceFragmentRendererKeys.ORDER_RETURNS_DATA_SET +
			"-placedOrderReturns";

	public static final String PLACED_ORDERS =
		CommerceFragmentRendererKeys.ORDERS_DATA_SET + "-placedOrders";

}