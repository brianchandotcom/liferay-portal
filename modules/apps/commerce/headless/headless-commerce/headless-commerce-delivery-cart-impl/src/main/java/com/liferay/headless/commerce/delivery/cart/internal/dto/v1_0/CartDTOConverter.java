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

package com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.core.dto.v1_0.converter.DTOConverter;
import com.liferay.headless.commerce.core.dto.v1_0.converter.DTOConverterContext;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Summary;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "model.class.name=com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart",
	service = {CartDTOConverter.class, DTOConverter.class}
)
public class CartDTOConverter implements DTOConverter {

	@Override
	public String getContentType() {
		return Cart.class.getSimpleName();
	}

	public Cart toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			dtoConverterContext.getResourcePrimKey());

		ExpandoBridge expandoBridge = commerceOrder.getExpandoBridge();

		Locale locale = dtoConverterContext.getLocale();

		ResourceBundle resourceBundle = LanguageResources.getResourceBundle(
			locale);

		String workflowStatusLabel = LanguageUtil.get(
			resourceBundle,
			WorkflowConstants.getStatusLabel(commerceOrder.getStatus()));

		String commerceOrderPaymentStatusLabel = LanguageUtil.get(
			resourceBundle,
			CommerceOrderConstants.getPaymentStatusLabel(
				commerceOrder.getPaymentStatus()));

		Cart cart = new Cart() {
			{
				account = commerceOrder.getCommerceAccountName();
				accountId = commerceOrder.getCommerceAccountId();
				author = commerceOrder.getUserName();
				billingAddressId = commerceOrder.getBillingAddressId();
				couponCode = commerceOrder.getCouponCode();
				createDate = commerceOrder.getCreateDate();
				customFields = expandoBridge.getAttributes();
				id = commerceOrder.getCommerceOrderId();
				lastPriceUpdateDate = commerceOrder.getLastPriceUpdateDate();
				modifiedDate = commerceOrder.getModifiedDate();
				paymentMethod = commerceOrder.getCommercePaymentMethodKey();
				paymentStatus = commerceOrder.getPaymentStatus();
				paymentStatusLabel = commerceOrderPaymentStatusLabel;
				printedNote = commerceOrder.getPrintedNote();
				purchaseOrderNumber = commerceOrder.getPurchaseOrderNumber();
				shippingAddressId = commerceOrder.getShippingAddressId();
				status = workflowStatusLabel;
				summary = _getSummary(commerceOrder, locale);
			}
		};

		String paymentMethodKey = commerceOrder.getCommercePaymentMethodKey();

		if ((paymentMethodKey != null) && !paymentMethodKey.isEmpty()) {
			String commerceOrderPaymentMethodName =
				_commercePaymentEngine.getPaymentMethodName(
					paymentMethodKey, locale);

			cart.setPaymentMethodLabel(commerceOrderPaymentMethodName);
		}

		return cart;
	}

	private String _formatPrice(
			BigDecimal price, CommerceCurrency commerceCurrency, Locale locale)
		throws PortalException {

		if (price == null) {
			price = BigDecimal.ZERO;
		}

		return _commercePriceFormatter.format(commerceCurrency, price, locale);
	}

	private String[] _getFormattedDiscountPercentages(
			BigDecimal[] discountPercentages, Locale locale)
		throws PortalException {

		List<String> formattedDiscountPercentages = new ArrayList<>();

		for (BigDecimal percentage : discountPercentages) {
			formattedDiscountPercentages.add(
				_commercePriceFormatter.format(percentage, locale));
		}

		return formattedDiscountPercentages.toArray(new String[0]);
	}

	private Summary _getSummary(CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		CommerceMoney commerceOrderPriceShippingValue =
			commerceOrder.getShippingMoney();

		BigDecimal commerceOrderPriceShippingValuePrice =
			commerceOrderPriceShippingValue.getPrice();

		CommerceMoney commerceOrderPriceSubTotal =
			commerceOrder.getSubtotalMoney();

		BigDecimal orderPriceSubTotalPrice =
			commerceOrderPriceSubTotal.getPrice();

		BigDecimal taxAmount = commerceOrder.getTaxAmount();

		CommerceMoney commerceOrderPriceTotal = commerceOrder.getTotalMoney();

		BigDecimal orderPriceTotalPrice = commerceOrderPriceTotal.getPrice();

		Summary summary = new Summary() {
			{
				currency = commerceCurrency.getName(locale);
				itemsQuantity = commerceOrderItems.size();
				shippingValue =
					commerceOrderPriceShippingValuePrice.doubleValue();
				shippingValueFormatted = commerceOrderPriceShippingValue.format(
					locale);
				subtotal = orderPriceSubTotalPrice.doubleValue();
				subtotalFormatted = commerceOrderPriceSubTotal.format(locale);

				total = orderPriceTotalPrice.doubleValue();
				totalFormatted = commerceOrderPriceTotal.format(locale);
			}
		};

		if (taxAmount != null) {
			summary.setTaxValue(taxAmount.doubleValue());
			summary.setTaxValueFormatted(
				_formatPrice(taxAmount, commerceCurrency, locale));
		}

		BigDecimal shippingDiscountAmount =
			commerceOrder.getShippingDiscountAmount();

		if (shippingDiscountAmount != null) {
			summary.setShippingDiscountValue(
				shippingDiscountAmount.doubleValue());

			summary.setShippingDiscountValueFormatted(
				_formatPrice(shippingDiscountAmount, commerceCurrency, locale));

			summary.setShippingDiscountPercentages(
				_getFormattedDiscountPercentages(
					new BigDecimal[] {
						commerceOrder.getShippingDiscountPercentageLevel1(),
						commerceOrder.getShippingDiscountPercentageLevel2(),
						commerceOrder.getShippingDiscountPercentageLevel3(),
						commerceOrder.getShippingDiscountPercentageLevel4()
					},
					locale));
		}

		BigDecimal subtotalDiscountAmount =
			commerceOrder.getSubtotalDiscountAmount();

		if (subtotalDiscountAmount != null) {
			summary.setSubtotal(subtotalDiscountAmount.doubleValue());

			summary.setSubtotalDiscountValueFormatted(
				_formatPrice(subtotalDiscountAmount, commerceCurrency, locale));

			summary.setSubtotalDiscountPercentages(
				_getFormattedDiscountPercentages(
					new BigDecimal[] {
						commerceOrder.getSubtotalDiscountPercentageLevel1(),
						commerceOrder.getSubtotalDiscountPercentageLevel2(),
						commerceOrder.getSubtotalDiscountPercentageLevel3(),
						commerceOrder.getSubtotalDiscountPercentageLevel4()
					},
					locale));
		}

		BigDecimal totalDiscountAmount = commerceOrder.getTotalDiscountAmount();

		if (totalDiscountAmount != null) {
			summary.setTotal(totalDiscountAmount.doubleValue());

			summary.setTotalDiscountValueFormatted(
				_formatPrice(totalDiscountAmount, commerceCurrency, locale));

			summary.setTotalDiscountPercentages(
				_getFormattedDiscountPercentages(
					new BigDecimal[] {
						commerceOrder.getTotalDiscountPercentageLevel1(),
						commerceOrder.getTotalDiscountPercentageLevel2(),
						commerceOrder.getTotalDiscountPercentageLevel3(),
						commerceOrder.getTotalDiscountPercentageLevel4()
					},
					locale));
		}

		return summary;
	}

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePaymentEngine _commercePaymentEngine;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

}