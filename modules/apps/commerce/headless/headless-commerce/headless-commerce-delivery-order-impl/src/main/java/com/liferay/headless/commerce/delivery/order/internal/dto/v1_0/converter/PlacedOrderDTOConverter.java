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

package com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.converter;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrder;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.Status;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.Summary;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "dto.class.name=com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrder",
	service = DTOConverter.class
)
public class PlacedOrderDTOConverter
	implements DTOConverter<CommerceOrder, PlacedOrder> {

	@Override
	public String getContentType() {
		return PlacedOrder.class.getSimpleName();
	}

	@Override
	public PlacedOrder toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			(Long)dtoConverterContext.getId());

		ExpandoBridge expandoBridge = commerceOrder.getExpandoBridge();

		Locale locale = dtoConverterContext.getLocale();

		ResourceBundle resourceBundle = LanguageResources.getResourceBundle(
			locale);

		String commerceOrderStatusLabel =
			CommerceOrderConstants.getOrderStatusLabel(
				commerceOrder.getOrderStatus());

		String commerceOrderStatusLabelI18n = _language.get(
			resourceBundle,
			CommerceOrderConstants.getOrderStatusLabel(
				commerceOrder.getOrderStatus()));

		String commerceOrderWorkflowStatusLabel =
			WorkflowConstants.getStatusLabel(commerceOrder.getStatus());

		String commerceOrderWorkflowStatusLabelI18n = _language.get(
			resourceBundle,
			WorkflowConstants.getStatusLabel(commerceOrder.getStatus()));

		String commerceOrderPaymentStatusLabel =
			CommerceOrderPaymentConstants.getOrderPaymentStatusLabel(
				commerceOrder.getPaymentStatus());

		String commerceOrderPaymentStatusLabelI18n = _language.get(
			resourceBundle,
			CommerceOrderPaymentConstants.getOrderPaymentStatusLabel(
				commerceOrder.getPaymentStatus()));

		PlacedOrder placedOrder = new PlacedOrder() {
			{
				account = commerceOrder.getCommerceAccountName();
				accountId = commerceOrder.getCommerceAccountId();
				author = commerceOrder.getUserName();
				couponCode = commerceOrder.getCouponCode();
				createDate = commerceOrder.getCreateDate();
				customFields = expandoBridge.getAttributes();
				id = commerceOrder.getCommerceOrderId();
				lastPriceUpdateDate = commerceOrder.getLastPriceUpdateDate();
				modifiedDate = commerceOrder.getModifiedDate();
				orderStatusInfo = _getOrderStatusInfo(
					commerceOrder.getOrderStatus(), commerceOrderStatusLabel,
					commerceOrderStatusLabelI18n);
				orderTypeExternalReferenceCode =
					_getOrderTypeExternalReferenceCode(
						commerceOrder.getCommerceOrderTypeId());
				orderTypeId = commerceOrder.getCommerceOrderTypeId();
				orderUUID = commerceOrder.getUuid();
				paymentMethod = commerceOrder.getCommercePaymentMethodKey();
				paymentStatus = commerceOrder.getPaymentStatus();
				paymentStatusInfo = _getPaymentStatusInfo(
					commerceOrder.getPaymentStatus(),
					commerceOrderPaymentStatusLabel,
					commerceOrderPaymentStatusLabelI18n);
				paymentStatusLabel = commerceOrderPaymentStatusLabel;
				placedOrderBillingAddressId =
					commerceOrder.getBillingAddressId();
				placedOrderShippingAddressId =
					commerceOrder.getShippingAddressId();
				printedNote = commerceOrder.getPrintedNote();
				purchaseOrderNumber = commerceOrder.getPurchaseOrderNumber();
				shippingOption = commerceOrder.getShippingOptionName();
				status = commerceOrderWorkflowStatusLabel;
				summary = _getSummary(commerceOrder, locale);
				workflowStatusInfo = _toStatus(
					commerceOrder.getStatus(), commerceOrderWorkflowStatusLabel,
					commerceOrderWorkflowStatusLabelI18n);

				setShippingMethod(
					() -> {
						CommerceShippingMethod commerceShippingMethod =
							commerceOrder.getCommerceShippingMethod();

						if (commerceShippingMethod == null) {
							return null;
						}

						return commerceShippingMethod.getEngineKey();
					});
			}
		};

		String paymentMethodKey = commerceOrder.getCommercePaymentMethodKey();

		if ((paymentMethodKey != null) && !paymentMethodKey.isEmpty()) {
			String commerceOrderPaymentMethodName =
				_commercePaymentEngine.getPaymentMethodName(
					paymentMethodKey, locale);

			placedOrder.setPaymentMethodLabel(commerceOrderPaymentMethodName);
		}

		return placedOrder;
	}

	private String _formatPrice(
			BigDecimal price, CommerceCurrency commerceCurrency, Locale locale)
		throws Exception {

		if (price == null) {
			price = BigDecimal.ZERO;
		}

		return _commercePriceFormatter.format(commerceCurrency, price, locale);
	}

	private String[] _getFormattedDiscountPercentages(
			BigDecimal[] discountPercentages, Locale locale)
		throws Exception {

		List<String> formattedDiscountPercentages = new ArrayList<>();

		for (BigDecimal percentage : discountPercentages) {
			formattedDiscountPercentages.add(
				_commercePriceFormatter.format(percentage, locale));
		}

		return formattedDiscountPercentages.toArray(new String[0]);
	}

	private Status _getOrderStatusInfo(
		int orderStatus, String commerceOrderStatusLabel,
		String commerceOrderStatusLabelI18n) {

		return new Status() {
			{
				code = orderStatus;
				label = commerceOrderStatusLabel;
				label_i18n = commerceOrderStatusLabelI18n;
			}
		};
	}

	private String _getOrderTypeExternalReferenceCode(long commerceOrderTypeId)
		throws Exception {

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeService.fetchCommerceOrderType(
				commerceOrderTypeId);

		if (commerceOrderType == null) {
			return null;
		}

		return commerceOrderType.getExternalReferenceCode();
	}

	private Status _getPaymentStatusInfo(
		int paymentStatus, String commerceOrderPaymentStatusLabel,
		String commerceOrderPaymentStatusLabelI18n) {

		return new Status() {
			{
				code = paymentStatus;
				label = commerceOrderPaymentStatusLabel;
				label_i18n = commerceOrderPaymentStatusLabelI18n;
			}
		};
	}

	private Summary _getSummary(CommerceOrder commerceOrder, Locale locale)
		throws Exception {

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		CommerceMoney commerceOrderPriceShippingValueCommerceMoney =
			commerceOrder.getShippingMoney();

		BigDecimal commerceOrderPriceShippingValuePrice =
			commerceOrderPriceShippingValueCommerceMoney.getPrice();

		CommerceMoney commerceOrderShippingValueWithTaxAmountCommerceMoney =
			commerceOrder.getShippingWithTaxAmountMoney();

		BigDecimal commerceOrderPriceShippingValueWithTaxAmountPrice =
			commerceOrderPriceShippingValuePrice;

		if (!commerceOrderShippingValueWithTaxAmountCommerceMoney.isEmpty()) {
			commerceOrderPriceShippingValueWithTaxAmountPrice =
				commerceOrderShippingValueWithTaxAmountCommerceMoney.getPrice();
		}

		CommerceMoney commerceOrderPriceSubtotalCommerceMoney =
			commerceOrder.getSubtotalMoney();

		BigDecimal orderPriceSubtotalPrice =
			commerceOrderPriceSubtotalCommerceMoney.getPrice();

		CommerceMoney commerceOrderPriceTotalCommerceMoney =
			commerceOrder.getTotalMoney();

		BigDecimal orderPriceTotalPrice =
			commerceOrderPriceTotalCommerceMoney.getPrice();

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		if (Objects.equals(
				commerceChannel.getPriceDisplayType(),
				CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

			orderPriceSubtotalPrice = commerceOrder.getSubtotalWithTaxAmount();
			orderPriceTotalPrice = commerceOrder.getTotalWithTaxAmount();
		}

		BigDecimal finalOrderPriceShippingValueWithTaxAmountPrice =
			commerceOrderPriceShippingValueWithTaxAmountPrice;
		BigDecimal finalOrderPriceSubtotalPrice = orderPriceSubtotalPrice;
		BigDecimal finalOrderPriceTotalPrice = orderPriceTotalPrice;

		Summary summary = new Summary() {
			{
				currency = commerceCurrency.getName(locale);
				itemsQuantity =
					_commerceOrderItemService.getCommerceOrderItemsQuantity(
						commerceOrder.getCommerceOrderId());
				shippingValue =
					commerceOrderPriceShippingValuePrice.doubleValue();
				shippingValueFormatted =
					commerceOrderPriceShippingValueCommerceMoney.format(locale);
				shippingValueWithTaxAmount =
					finalOrderPriceShippingValueWithTaxAmountPrice.
						doubleValue();
				shippingValueWithTaxAmountFormatted =
					commerceOrderShippingValueWithTaxAmountCommerceMoney.format(
						locale);
				subtotal = finalOrderPriceSubtotalPrice.doubleValue();
				subtotalFormatted =
					commerceOrderPriceSubtotalCommerceMoney.format(locale);

				total = finalOrderPriceTotalPrice.doubleValue();
				totalFormatted = commerceOrderPriceTotalCommerceMoney.format(
					locale);
			}
		};

		BigDecimal taxAmount = commerceOrder.getTaxAmount();

		if (taxAmount != null) {
			summary.setTaxValue(taxAmount.doubleValue());
			summary.setTaxValueFormatted(
				_formatPrice(taxAmount, commerceCurrency, locale));
		}

		_setShippingDiscountOnSummary(
			commerceOrder, commerceCurrency, locale,
			commerceChannel.getPriceDisplayType(), summary);

		_setSubtotalDiscountOnSummary(
			commerceOrder, commerceCurrency, locale,
			commerceChannel.getPriceDisplayType(), summary);

		_setTotalDiscountOnSummary(
			commerceOrder, commerceCurrency, locale,
			commerceChannel.getPriceDisplayType(), summary);

		return summary;
	}

	private void _setShippingDiscountOnSummary(
			CommerceOrder commerceOrder, CommerceCurrency commerceCurrency,
			Locale locale, String priceDisplayType, Summary summary)
		throws Exception {

		BigDecimal shippingDiscountAmount =
			commerceOrder.getShippingDiscountAmount();

		if (shippingDiscountAmount == null) {
			return;
		}

		BigDecimal shippingDiscountPercentageLevel1 =
			commerceOrder.getShippingDiscountPercentageLevel1();
		BigDecimal shippingDiscountPercentageLevel2 =
			commerceOrder.getShippingDiscountPercentageLevel2();
		BigDecimal shippingDiscountPercentageLevel3 =
			commerceOrder.getShippingDiscountPercentageLevel3();
		BigDecimal shippingDiscountPercentageLevel4 =
			commerceOrder.getShippingDiscountPercentageLevel4();

		if (Objects.equals(
				priceDisplayType,
				CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

			shippingDiscountAmount =
				commerceOrder.getShippingDiscountWithTaxAmount();

			shippingDiscountPercentageLevel1 =
				commerceOrder.
					getShippingDiscountPercentageLevel1WithTaxAmount();
			shippingDiscountPercentageLevel2 =
				commerceOrder.
					getShippingDiscountPercentageLevel2WithTaxAmount();
			shippingDiscountPercentageLevel3 =
				commerceOrder.
					getShippingDiscountPercentageLevel3WithTaxAmount();
			shippingDiscountPercentageLevel4 =
				commerceOrder.
					getShippingDiscountPercentageLevel4WithTaxAmount();
		}

		summary.setShippingDiscountPercentages(
			_getFormattedDiscountPercentages(
				new BigDecimal[] {
					shippingDiscountPercentageLevel1,
					shippingDiscountPercentageLevel2,
					shippingDiscountPercentageLevel3,
					shippingDiscountPercentageLevel4
				},
				locale));
		summary.setShippingDiscountValue(shippingDiscountAmount.doubleValue());
		summary.setShippingDiscountValueFormatted(
			_formatPrice(shippingDiscountAmount, commerceCurrency, locale));
	}

	private void _setSubtotalDiscountOnSummary(
			CommerceOrder commerceOrder, CommerceCurrency commerceCurrency,
			Locale locale, String priceDisplayType, Summary summary)
		throws Exception {

		BigDecimal subtotalDiscountAmount =
			commerceOrder.getSubtotalDiscountAmount();

		if (subtotalDiscountAmount == null) {
			return;
		}

		BigDecimal subtotalDiscountPercentageLevel1 =
			commerceOrder.getSubtotalDiscountPercentageLevel1();
		BigDecimal subtotalDiscountPercentageLevel2 =
			commerceOrder.getSubtotalDiscountPercentageLevel2();
		BigDecimal subtotalDiscountPercentageLevel3 =
			commerceOrder.getSubtotalDiscountPercentageLevel3();
		BigDecimal subtotalDiscountPercentageLevel4 =
			commerceOrder.getSubtotalDiscountPercentageLevel4();

		if (Objects.equals(
				priceDisplayType,
				CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

			subtotalDiscountAmount =
				commerceOrder.getSubtotalDiscountWithTaxAmount();

			subtotalDiscountPercentageLevel1 =
				commerceOrder.
					getSubtotalDiscountPercentageLevel1WithTaxAmount();
			subtotalDiscountPercentageLevel2 =
				commerceOrder.
					getSubtotalDiscountPercentageLevel2WithTaxAmount();
			subtotalDiscountPercentageLevel3 =
				commerceOrder.
					getSubtotalDiscountPercentageLevel3WithTaxAmount();
			subtotalDiscountPercentageLevel4 =
				commerceOrder.
					getSubtotalDiscountPercentageLevel4WithTaxAmount();
		}

		summary.setSubtotalDiscountPercentages(
			_getFormattedDiscountPercentages(
				new BigDecimal[] {
					subtotalDiscountPercentageLevel1,
					subtotalDiscountPercentageLevel2,
					subtotalDiscountPercentageLevel3,
					subtotalDiscountPercentageLevel4
				},
				locale));
		summary.setSubtotalDiscountValue(subtotalDiscountAmount.doubleValue());
		summary.setSubtotalDiscountValueFormatted(
			_formatPrice(subtotalDiscountAmount, commerceCurrency, locale));
	}

	private void _setTotalDiscountOnSummary(
			CommerceOrder commerceOrder, CommerceCurrency commerceCurrency,
			Locale locale, String priceDisplayType, Summary summary)
		throws Exception {

		BigDecimal totalDiscountAmount = commerceOrder.getTotalDiscountAmount();

		if (totalDiscountAmount == null) {
			return;
		}

		BigDecimal totalDiscountPercentageLevel1 =
			commerceOrder.getTotalDiscountPercentageLevel1();
		BigDecimal totalDiscountPercentageLevel2 =
			commerceOrder.getTotalDiscountPercentageLevel2();
		BigDecimal totalDiscountPercentageLevel3 =
			commerceOrder.getTotalDiscountPercentageLevel3();
		BigDecimal totalDiscountPercentageLevel4 =
			commerceOrder.getTotalDiscountPercentageLevel4();

		if (Objects.equals(
				priceDisplayType,
				CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

			totalDiscountAmount = commerceOrder.getTotalDiscountWithTaxAmount();

			totalDiscountPercentageLevel1 =
				commerceOrder.getTotalDiscountPercentageLevel1WithTaxAmount();
			totalDiscountPercentageLevel2 =
				commerceOrder.getTotalDiscountPercentageLevel2WithTaxAmount();
			totalDiscountPercentageLevel3 =
				commerceOrder.getTotalDiscountPercentageLevel3WithTaxAmount();
			totalDiscountPercentageLevel4 =
				commerceOrder.getTotalDiscountPercentageLevel4WithTaxAmount();
		}

		summary.setTotalDiscountPercentages(
			_getFormattedDiscountPercentages(
				new BigDecimal[] {
					totalDiscountPercentageLevel1,
					totalDiscountPercentageLevel2,
					totalDiscountPercentageLevel3, totalDiscountPercentageLevel4
				},
				locale));
		summary.setTotalDiscountValue(totalDiscountAmount.doubleValue());
		summary.setTotalDiscountValueFormatted(
			_formatPrice(totalDiscountAmount, commerceCurrency, locale));
	}

	private Status _toStatus(
		int orderStatus, String commerceOrderWorkflowStatusLabel,
		String commerceOrderWorkflowStatusLabelI18n) {

		return new Status() {
			{
				code = orderStatus;
				label = commerceOrderWorkflowStatusLabel;
				label_i18n = commerceOrderWorkflowStatusLabelI18n;
			}
		};
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private CommercePaymentEngine _commercePaymentEngine;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private Language _language;

}