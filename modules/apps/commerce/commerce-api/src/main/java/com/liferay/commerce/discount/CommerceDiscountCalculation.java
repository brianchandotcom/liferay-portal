/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public interface CommerceDiscountCalculation {

	public CommerceDiscountValue getOrderShippingCommerceDiscountValue(
			CommerceOrder commerceOrder, BigDecimal shippingAmount,
			CommerceContext commerceContext)
		throws PortalException;

	public CommerceDiscountValue getOrderSubtotalCommerceDiscountValue(
			CommerceOrder commerceOrder, BigDecimal subtotalAmount,
			CommerceContext commerceContext)
		throws PortalException;

	public CommerceDiscountValue getOrderTotalCommerceDiscountValue(
			CommerceOrder commerceOrder, BigDecimal totalAmount,
			CommerceContext commerceContext)
		throws PortalException;

	public CommerceDiscountValue getProductCommerceDiscountValue(
			long cpInstanceId, BigDecimal quantity, BigDecimal productUnitPrice,
			CommerceContext commerceContext)
		throws PortalException;

	public CommerceDiscountValue getProductCommerceDiscountValue(
			long cpInstanceId, long commercePriceListId, BigDecimal quantity,
			BigDecimal productUnitPrice, CommerceContext commerceContext)
		throws PortalException;

}