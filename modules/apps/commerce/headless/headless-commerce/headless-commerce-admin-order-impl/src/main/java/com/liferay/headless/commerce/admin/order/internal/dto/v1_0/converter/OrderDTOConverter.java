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
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Status;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.model.CommerceOrder",
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

		Locale locale = dtoConverterContext.getLocale();

		ResourceBundle resourceBundle = LanguageResources.getResourceBundle(
			locale);

		String commerceOrderStatusLabel = _getCommerceOrderStatusLabel(
			commerceOrder.getOrderStatus(), locale);

		String commerceOrderStatusLabelI18n = _getCommerceOrderStatusLabelI18n(
			commerceOrder.getOrderStatus(), locale);

		String commerceOrderWorkflowStatusLabel =
			WorkflowConstants.getStatusLabel(commerceOrder.getStatus());

		String commerceOrderWorkflowStatusLabelI18n = LanguageUtil.get(
			resourceBundle,
			WorkflowConstants.getStatusLabel(commerceOrder.getStatus()));

		String commerceOrderPaymentStatusLabel =
			CommerceOrderConstants.getPaymentStatusLabel(
				commerceOrder.getPaymentStatus());

		String commerceOrderPaymentStatusLabelI18n = LanguageUtil.get(
			resourceBundle,
			CommerceOrderConstants.getPaymentStatusLabel(
				commerceOrder.getPaymentStatus()));

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		Order order = new Order() {
			{
				accountExternalReferenceCode =
					commerceAccount.getExternalReferenceCode();
				accountId = commerceOrder.getCommerceAccountId();
				actions = dtoConverterContext.getActions();
				advanceStatus = commerceOrder.getAdvanceStatus();
				billingAddressId = commerceOrder.getBillingAddressId();
				channelExternalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				channelId = commerceChannel.getCommerceChannelId();
				couponCode = commerceOrder.getCouponCode();
				createDate = commerceOrder.getCreateDate();
				currencyCode = commerceCurrency.getCode();
				customFields = expandoBridge.getAttributes();
				deliveryTermDescription =
					commerceOrder.getDeliveryCommerceTermEntryDescription();
				deliveryTermId = commerceOrder.getDeliveryCommerceTermEntryId();
				deliveryTermName =
					commerceOrder.getDeliveryCommerceTermEntryName();
				externalReferenceCode =
					commerceOrder.getExternalReferenceCode();
				id = commerceOrder.getCommerceOrderId();
				lastPriceUpdateDate = commerceOrder.getLastPriceUpdateDate();
				modifiedDate = commerceOrder.getModifiedDate();
				orderDate = commerceOrder.getOrderDate();
				orderStatus = commerceOrder.getOrderStatus();
				orderStatusInfo = _getOrderStatusInfo(
					commerceOrder.getOrderStatus(), commerceOrderStatusLabel,
					commerceOrderStatusLabelI18n);
				orderTypeExternalReferenceCode =
					_getOrderTypeExternalReferenceCode(
						commerceOrder.getCommerceOrderTypeId());
				orderTypeId = commerceOrder.getCommerceOrderTypeId();
				paymentMethod = commerceOrder.getCommercePaymentMethodKey();
				paymentStatus = commerceOrder.getPaymentStatus();
				paymentStatusInfo = _getPaymentStatusInfo(
					commerceOrder.getPaymentStatus(),
					commerceOrderPaymentStatusLabel,
					commerceOrderPaymentStatusLabelI18n);
				paymentTermDescription =
					commerceOrder.getPaymentCommerceTermEntryDescription();
				paymentTermId = commerceOrder.getPaymentCommerceTermEntryId();
				paymentTermName =
					commerceOrder.getPaymentCommerceTermEntryName();
				printedNote = commerceOrder.getPrintedNote();
				purchaseOrderNumber = commerceOrder.getPurchaseOrderNumber();
				requestedDeliveryDate =
					commerceOrder.getRequestedDeliveryDate();
				shippingAddressId = commerceOrder.getShippingAddressId();
				shippingMethod = _getShippingMethodEngineKey(
					commerceShippingMethod);
				shippingOption = commerceOrder.getShippingOptionName();
				transactionId = commerceOrder.getTransactionId();
				workflowStatusInfo = _toStatus(
					commerceOrder.getStatus(), commerceOrderWorkflowStatusLabel,
					commerceOrderWorkflowStatusLabelI18n);
			}
		};

		_setOrderSubtotal(commerceCurrency, commerceOrder, order, locale);

		_setOrderShipping(commerceCurrency, commerceOrder, order, locale);

		BigDecimal taxAmount = commerceOrder.getTaxAmount();

		if (taxAmount != null) {
			order.setTaxAmount(taxAmount);
			order.setTaxAmountFormatted(
				_formatPrice(taxAmount, commerceCurrency, locale));
			order.setTaxAmountValue(taxAmount.doubleValue());
		}

		_setOrderTotal(commerceCurrency, commerceOrder, order, locale);

		return order;
	}

	private String _formatPrice(
			BigDecimal price, CommerceCurrency commerceCurrency, Locale locale)
		throws Exception {

		if (price == null) {
			price = BigDecimal.ZERO;
		}

		return _commercePriceFormatter.format(commerceCurrency, price, locale);
	}

	private String _getCommerceOrderStatusLabel(
		int orderStatus, Locale locale) {

		String commerceOrderStatusLabel =
			CommerceOrderConstants.getOrderStatusLabel(orderStatus);

		if (!Validator.isBlank(commerceOrderStatusLabel)) {
			return commerceOrderStatusLabel;
		}

		CommerceOrderStatus commerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(orderStatus);

		if (commerceOrderStatus != null) {
			return commerceOrderStatus.getLabel(locale);
		}

		return commerceOrderStatusLabel;
	}

	private String _getCommerceOrderStatusLabelI18n(
		int orderStatus, Locale locale) {

		String commerceOrderStatusLabelI18n = LanguageUtil.get(
			locale, CommerceOrderConstants.getOrderStatusLabel(orderStatus));

		if (!Validator.isBlank(commerceOrderStatusLabelI18n)) {
			return commerceOrderStatusLabelI18n;
		}

		return _getCommerceOrderStatusLabel(orderStatus, locale);
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

	private String _getShippingMethodEngineKey(
		CommerceShippingMethod commerceShippingMethod) {

		if (commerceShippingMethod == null) {
			return null;
		}

		return commerceShippingMethod.getEngineKey();
	}

	private void _setOrderShipping(
			CommerceCurrency commerceCurrency, CommerceOrder commerceOrder,
			Order order, Locale locale)
		throws Exception {

		CommerceMoney commerceOrderShippingAmountCommerceMoney =
			commerceOrder.getShippingMoney();

		order.setShippingAmountFormatted(
			commerceOrderShippingAmountCommerceMoney.format(locale));

		BigDecimal commerceOrderShippingValue =
			commerceOrderShippingAmountCommerceMoney.getPrice();

		if (commerceOrderShippingValue != null) {
			order.setShippingAmountValue(
				commerceOrderShippingValue.doubleValue());
		}

		CommerceMoney commerceOrderShippingWithTaxAmountCommerceMoney =
			commerceOrder.getShippingWithTaxAmountMoney();

		if (commerceOrderShippingWithTaxAmountCommerceMoney != null) {
			order.setShippingWithTaxAmountFormatted(
				commerceOrderShippingWithTaxAmountCommerceMoney.format(locale));

			BigDecimal commerceOrderShippingWithTaxAmountValue =
				commerceOrderShippingWithTaxAmountCommerceMoney.getPrice();

			if (commerceOrderShippingWithTaxAmountValue != null) {
				order.setShippingWithTaxAmountValue(
					commerceOrderShippingWithTaxAmountValue.doubleValue());
			}
		}

		BigDecimal shippingDiscountAmount =
			commerceOrder.getShippingDiscountAmount();

		if (shippingDiscountAmount != null) {
			order.setShippingDiscountAmount(shippingDiscountAmount);
			order.setShippingDiscountAmountFormatted(
				_formatPrice(shippingDiscountAmount, commerceCurrency, locale));
			order.setShippingDiscountPercentageLevel1(
				commerceOrder.getShippingDiscountPercentageLevel1());
			order.setShippingDiscountPercentageLevel2(
				commerceOrder.getShippingDiscountPercentageLevel2());
			order.setShippingDiscountPercentageLevel3(
				commerceOrder.getShippingDiscountPercentageLevel3());
			order.setShippingDiscountPercentageLevel4(
				commerceOrder.getShippingDiscountPercentageLevel4());
		}

		BigDecimal shippingDiscountWithTaxAmount =
			commerceOrder.getShippingDiscountWithTaxAmount();

		if (shippingDiscountWithTaxAmount != null) {
			order.setShippingDiscountWithTaxAmount(
				shippingDiscountWithTaxAmount);
			order.setShippingDiscountWithTaxAmountFormatted(
				_formatPrice(
					shippingDiscountWithTaxAmount, commerceCurrency, locale));
			order.setShippingDiscountPercentageLevel1WithTaxAmount(
				commerceOrder.
					getShippingDiscountPercentageLevel1WithTaxAmount());
			order.setShippingDiscountPercentageLevel2WithTaxAmount(
				commerceOrder.
					getShippingDiscountPercentageLevel2WithTaxAmount());
			order.setShippingDiscountPercentageLevel3WithTaxAmount(
				commerceOrder.
					getShippingDiscountPercentageLevel3WithTaxAmount());
			order.setShippingDiscountPercentageLevel4WithTaxAmount(
				commerceOrder.
					getShippingDiscountPercentageLevel4WithTaxAmount());
		}
	}

	private void _setOrderSubtotal(
			CommerceCurrency commerceCurrency, CommerceOrder commerceOrder,
			Order order, Locale locale)
		throws Exception {

		CommerceMoney commerceOrderSubtotalCommerceMoney =
			commerceOrder.getSubtotalMoney();

		if (commerceOrderSubtotalCommerceMoney != null) {
			order.setSubtotalFormatted(
				commerceOrderSubtotalCommerceMoney.format(locale));

			BigDecimal commerceOrderSubtotalValue =
				commerceOrderSubtotalCommerceMoney.getPrice();

			if (commerceOrderSubtotalValue != null) {
				order.setSubtotalAmount(
					commerceOrderSubtotalValue.doubleValue());
			}
		}

		CommerceMoney commerceOrderSubtotalWithTaxAmountCommerceMoney =
			commerceOrder.getSubtotalWithTaxAmountMoney();

		if (commerceOrderSubtotalWithTaxAmountCommerceMoney != null) {
			order.setSubtotalWithTaxAmountFormatted(
				commerceOrderSubtotalWithTaxAmountCommerceMoney.format(locale));

			BigDecimal commerceOrderSubtotalWithTaxAmountValue =
				commerceOrderSubtotalWithTaxAmountCommerceMoney.getPrice();

			if (commerceOrderSubtotalWithTaxAmountValue != null) {
				order.setSubtotalWithTaxAmountValue(
					commerceOrderSubtotalWithTaxAmountValue.doubleValue());
			}
		}

		BigDecimal subtotalDiscountAmount =
			commerceOrder.getSubtotalDiscountAmount();

		if (subtotalDiscountAmount != null) {
			order.setSubtotalDiscountAmount(subtotalDiscountAmount);
			order.setSubtotalDiscountAmountFormatted(
				_formatPrice(subtotalDiscountAmount, commerceCurrency, locale));
			order.setSubtotalDiscountPercentageLevel1(
				commerceOrder.getSubtotalDiscountPercentageLevel1());
			order.setSubtotalDiscountPercentageLevel2(
				commerceOrder.getSubtotalDiscountPercentageLevel2());
			order.setSubtotalDiscountPercentageLevel3(
				commerceOrder.getSubtotalDiscountPercentageLevel3());
			order.setSubtotalDiscountPercentageLevel4(
				commerceOrder.getSubtotalDiscountPercentageLevel4());
		}

		BigDecimal subtotalDiscountWithTaxAmount =
			commerceOrder.getSubtotalDiscountWithTaxAmount();

		if (subtotalDiscountWithTaxAmount != null) {
			order.setSubtotalDiscountWithTaxAmount(
				subtotalDiscountWithTaxAmount);
			order.setSubtotalDiscountWithTaxAmountFormatted(
				_formatPrice(
					subtotalDiscountWithTaxAmount, commerceCurrency, locale));
			order.setSubtotalDiscountPercentageLevel1WithTaxAmount(
				commerceOrder.
					getSubtotalDiscountPercentageLevel1WithTaxAmount());
			order.setSubtotalDiscountPercentageLevel2WithTaxAmount(
				commerceOrder.
					getSubtotalDiscountPercentageLevel2WithTaxAmount());
			order.setSubtotalDiscountPercentageLevel3WithTaxAmount(
				commerceOrder.
					getSubtotalDiscountPercentageLevel3WithTaxAmount());
			order.setSubtotalDiscountPercentageLevel4WithTaxAmount(
				commerceOrder.
					getSubtotalDiscountPercentageLevel4WithTaxAmount());
		}
	}

	private void _setOrderTotal(
			CommerceCurrency commerceCurrency, CommerceOrder commerceOrder,
			Order order, Locale locale)
		throws Exception {

		CommerceMoney commerceOrderTotalCommerceMoney =
			commerceOrder.getTotalMoney();

		if (commerceOrderTotalCommerceMoney != null) {
			order.setTotalFormatted(
				commerceOrderTotalCommerceMoney.format(locale));

			BigDecimal commerceOrderTotalValue =
				commerceOrderTotalCommerceMoney.getPrice();

			if (commerceOrderTotalValue != null) {
				order.setTotalAmount(commerceOrderTotalValue.doubleValue());
			}
		}

		CommerceMoney commerceOrderTotalWithTaxAmountMoney =
			commerceOrder.getTotalWithTaxAmountMoney();

		if (commerceOrderTotalWithTaxAmountMoney != null) {
			order.setTotalWithTaxAmountFormatted(
				commerceOrderTotalWithTaxAmountMoney.format(locale));

			BigDecimal commerceOrderTotalWithTaxAmountValue =
				commerceOrderTotalWithTaxAmountMoney.getPrice();

			if (commerceOrderTotalWithTaxAmountValue != null) {
				order.setTotalWithTaxAmountValue(
					commerceOrderTotalWithTaxAmountValue.doubleValue());
			}
		}

		BigDecimal totalDiscountAmount = commerceOrder.getTotalDiscountAmount();

		if (totalDiscountAmount != null) {
			order.setTotalDiscountAmount(totalDiscountAmount);
			order.setTotalDiscountAmountFormatted(
				_formatPrice(totalDiscountAmount, commerceCurrency, locale));
			order.setTotalDiscountPercentageLevel1(
				commerceOrder.getTotalDiscountPercentageLevel1());
			order.setTotalDiscountPercentageLevel2(
				commerceOrder.getTotalDiscountPercentageLevel2());
			order.setTotalDiscountPercentageLevel3(
				commerceOrder.getTotalDiscountPercentageLevel3());
			order.setTotalDiscountPercentageLevel4(
				commerceOrder.getTotalDiscountPercentageLevel4());
		}

		BigDecimal totalDiscountWithTaxAmount =
			commerceOrder.getTotalDiscountWithTaxAmount();

		if (totalDiscountWithTaxAmount != null) {
			order.setTotalDiscountWithTaxAmount(totalDiscountWithTaxAmount);
			order.setTotalDiscountWithTaxAmountFormatted(
				_formatPrice(
					totalDiscountWithTaxAmount, commerceCurrency, locale));
			order.setSubtotalDiscountPercentageLevel1WithTaxAmount(
				commerceOrder.getTotalDiscountPercentageLevel1WithTaxAmount());
			order.setSubtotalDiscountPercentageLevel2WithTaxAmount(
				commerceOrder.getTotalDiscountPercentageLevel2WithTaxAmount());
			order.setSubtotalDiscountPercentageLevel3WithTaxAmount(
				commerceOrder.getTotalDiscountPercentageLevel3WithTaxAmount());
			order.setSubtotalDiscountPercentageLevel4WithTaxAmount(
				commerceOrder.getTotalDiscountPercentageLevel4WithTaxAmount());
		}
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
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

}