/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.engine;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.List;

/**
 * @author Alec Sloan
 */
public interface CommerceOrderEngine {

	public CommerceOrder checkCommerceOrderShipmentStatus(
			CommerceOrder commerceOrder, boolean secure)
		throws PortalException;

	public CommerceOrder checkoutCommerceOrder(
			CommerceOrder commerceOrder, long userId)
		throws PortalException;

	public CommerceOrderStatus getCurrentCommerceOrderStatus(
		CommerceOrder commerceOrder);

	public List<CommerceOrderStatus> getNextCommerceOrderStatuses(
			CommerceOrder commerceOrder)
		throws PortalException;

	public CommerceOrder transitionCommerceOrder(
			CommerceOrder commerceOrder, int orderStatus, long userId,
			boolean secure)
		throws PortalException;

	public CommerceOrder updateCommerceOrder(
			String externalReferenceCode, long commerceOrderId,
			long billingAddressId, long commerceShippingMethodId,
			long shippingAddressId, String advanceStatus,
			String commercePaymentMethodKey, String purchaseOrderNumber,
			BigDecimal shippingAmount, String shippingOptionName,
			BigDecimal shippingWithTaxAmount, BigDecimal subtotal,
			BigDecimal subtotalWithTaxAmount, BigDecimal taxAmount,
			BigDecimal total, BigDecimal totalDiscountAmount,
			BigDecimal totalWithTaxAmount, CommerceContext commerceContext,
			boolean recalculatePrice)
		throws PortalException;

}