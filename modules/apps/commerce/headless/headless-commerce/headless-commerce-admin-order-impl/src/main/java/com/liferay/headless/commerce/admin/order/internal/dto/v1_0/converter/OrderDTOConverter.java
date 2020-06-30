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

package com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.model.CommerceOrder",
	service = {DTOConverter.class, OrderDTOConverter.class}
)
public class OrderDTOConverter implements DTOConverter<CommerceOrder, Order> {

	@Override
	public String getContentType() {
		return Order.class.getSimpleName();
	}

	@Override
	public Order toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			(Long)dtoConverterContext.getId());

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();
		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();
		CommerceShippingMethod commerceShippingMethod =
			commerceOrder.getCommerceShippingMethod();
		ExpandoBridge expandoBridge = commerceOrder.getExpandoBridge();

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		Order order = new Order() {
			{
				accountExternalReferenceCode =
					commerceAccount.getExternalReferenceCode();
				accountId = commerceOrder.getCommerceAccountId();
				advanceStatus = commerceOrder.getAdvanceStatus();
				billingAddressId = commerceOrder.getBillingAddressId();
				channelId = commerceChannel.getCommerceChannelId();
				couponCode = commerceOrder.getCouponCode();
				createDate = commerceOrder.getCreateDate();
				currencyCode = commerceCurrency.getName(
					dtoConverterContext.getLocale());
				customFields = expandoBridge.getAttributes();
				externalReferenceCode =
					commerceOrder.getExternalReferenceCode();
				id = commerceOrder.getCommerceOrderId();
				lastPriceUpdateDate = commerceOrder.getLastPriceUpdateDate();
				modifiedDate = commerceOrder.getModifiedDate();
				orderStatus = commerceOrder.getOrderStatus();
				paymentMethod = commerceOrder.getCommercePaymentMethodKey();
				paymentStatus = commerceOrder.getPaymentStatus();
				printedNote = commerceOrder.getPrintedNote();
				purchaseOrderNumber = commerceOrder.getPurchaseOrderNumber();
				requestedDeliveryDate =
					commerceOrder.getRequestedDeliveryDate();
				shippingAddressId = commerceOrder.getShippingAddressId();
				shippingMethod = _getShippingMethodEngineKey(
					commerceShippingMethod);
				shippingOption = commerceOrder.getShippingOptionName();
				transactionId = commerceOrder.getTransactionId();
			}
		};

		Locale locale = dtoConverterContext.getLocale();

		CommerceMoney commerceOrderSubTotalMoney =
			commerceOrder.getSubtotalMoney();

		order.setSubtotalFormatted(commerceOrderSubTotalMoney.format(locale));

		BigDecimal commerceOrderSubTotalMoneyPrice =
			commerceOrderSubTotalMoney.getPrice();

		order.setSubtotalAmount(commerceOrderSubTotalMoneyPrice.doubleValue());

		BigDecimal subtotalDiscountAmount =
			commerceOrder.getSubtotalDiscountAmount();

		if (subtotalDiscountAmount != null) {
			order.setSubtotalDiscountAmount(
				subtotalDiscountAmount.doubleValue());

			order.setSubtotalDiscountAmountFormatted(
				_formatPrice(subtotalDiscountAmount, commerceCurrency, locale));

			BigDecimal subtotalDiscountPercentageLevel1 =
				commerceOrder.getSubtotalDiscountPercentageLevel1();

			BigDecimal subtotalDiscountPercentageLevel2 =
				commerceOrder.getSubtotalDiscountPercentageLevel2();

			BigDecimal subtotalDiscountPercentageLevel3 =
				commerceOrder.getSubtotalDiscountPercentageLevel3();

			BigDecimal subtotalDiscountPercentageLevel4 =
				commerceOrder.getSubtotalDiscountPercentageLevel4();

			order.setSubtotalDiscountPercentageLevel1(
				subtotalDiscountPercentageLevel1.doubleValue());

			order.setSubtotalDiscountPercentageLevel2(
				subtotalDiscountPercentageLevel2.doubleValue());

			order.setSubtotalDiscountPercentageLevel3(
				subtotalDiscountPercentageLevel3.doubleValue());

			order.setSubtotalDiscountPercentageLevel4(
				subtotalDiscountPercentageLevel4.doubleValue());
		}

		CommerceMoney commerceOrderPriceShippingValue =
			commerceOrder.getShippingMoney();

		order.setShippingAmountFormatted(
			commerceOrderPriceShippingValue.format(locale));

		BigDecimal commerceOrderPriceShippingValuePrice =
			commerceOrderPriceShippingValue.getPrice();

		order.setShippingAmountValue(
			commerceOrderPriceShippingValuePrice.doubleValue());

		BigDecimal shippingDiscountAmount =
			commerceOrder.getShippingDiscountAmount();

		if (shippingDiscountAmount != null) {
			order.setShippingDiscountAmount(
				shippingDiscountAmount.doubleValue());

			order.setShippingDiscountAmountFormatted(
				_formatPrice(shippingDiscountAmount, commerceCurrency, locale));

			BigDecimal shippingDiscountPercentageLevel1 =
				commerceOrder.getShippingDiscountPercentageLevel1();

			BigDecimal shippingDiscountPercentageLevel2 =
				commerceOrder.getShippingDiscountPercentageLevel2();

			BigDecimal shippingDiscountPercentageLevel3 =
				commerceOrder.getShippingDiscountPercentageLevel3();

			BigDecimal shippingDiscountPercentageLevel4 =
				commerceOrder.getShippingDiscountPercentageLevel4();

			order.setShippingDiscountPercentageLevel1(
				shippingDiscountPercentageLevel1.doubleValue());

			order.setShippingDiscountPercentageLevel2(
				shippingDiscountPercentageLevel2.doubleValue());

			order.setShippingDiscountPercentageLevel3(
				shippingDiscountPercentageLevel3.doubleValue());

			order.setShippingDiscountPercentageLevel4(
				shippingDiscountPercentageLevel4.doubleValue());
		}

		BigDecimal taxAmount = commerceOrder.getTaxAmount();

		if (taxAmount != null) {
			order.setTaxAmount(taxAmount.doubleValue());
			order.setTaxAmountFormatted(
				_formatPrice(taxAmount, commerceCurrency, locale));
		}

		CommerceMoney commerceOrderTotalMoney = commerceOrder.getTotalMoney();

		order.setTotalFormatted(commerceOrderTotalMoney.format(locale));

		BigDecimal commerceOrderTotalPrice = commerceOrderTotalMoney.getPrice();

		order.setTotalAmount(commerceOrderTotalPrice.doubleValue());

		BigDecimal totalDiscountAmount = commerceOrder.getTotalDiscountAmount();

		if (totalDiscountAmount != null) {
			order.setTotalDiscountAmount(totalDiscountAmount.doubleValue());

			order.setTotalDiscountAmountFormatted(
				_formatPrice(totalDiscountAmount, commerceCurrency, locale));

			BigDecimal totalDiscountPercentageLevel1 =
				commerceOrder.getTotalDiscountPercentageLevel1();

			BigDecimal totalDiscountPercentageLevel2 =
				commerceOrder.getTotalDiscountPercentageLevel2();

			BigDecimal totalDiscountPercentageLevel3 =
				commerceOrder.getTotalDiscountPercentageLevel3();

			BigDecimal totalDiscountPercentageLevel4 =
				commerceOrder.getTotalDiscountPercentageLevel4();

			order.setTotalDiscountPercentageLevel1(
				totalDiscountPercentageLevel1.doubleValue());

			order.setTotalDiscountPercentageLevel2(
				totalDiscountPercentageLevel2.doubleValue());

			order.setTotalDiscountPercentageLevel3(
				totalDiscountPercentageLevel3.doubleValue());

			order.setTotalDiscountPercentageLevel4(
				totalDiscountPercentageLevel4.doubleValue());
		}

		return order;
	}

	private String _formatPrice(
			BigDecimal price, CommerceCurrency commerceCurrency, Locale locale)
		throws PortalException {

		if (price == null) {
			price = BigDecimal.ZERO;
		}

		return _commercePriceFormatter.format(commerceCurrency, price, locale);
	}

	private String _getShippingMethodEngineKey(
		CommerceShippingMethod commerceShippingMethod) {

		if (commerceShippingMethod == null) {
			return null;
		}

		return commerceShippingMethod.getEngineKey();
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

}