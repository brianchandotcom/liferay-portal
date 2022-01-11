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

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPMeasurementUnitService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.util.CommerceOrderItemQuantityFormatter;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.model.CommerceOrderItem",
	service = {DTOConverter.class, OrderItemDTOConverter.class}
)
public class OrderItemDTOConverter
	implements DTOConverter<CommerceOrderItem, OrderItem> {

	@Override
	public String getContentType() {
		return OrderItem.class.getSimpleName();
	}

	@Override
	public OrderItem toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(
				(Long)dtoConverterContext.getId());

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();
		CPInstance cpInstance = commerceOrderItem.fetchCPInstance();
		ExpandoBridge expandoBridge = commerceOrderItem.getExpandoBridge();

		return new OrderItem() {
			{
				bookedQuantityId = commerceOrderItem.getBookedQuantityId();
				customFields = expandoBridge.getAttributes();
				decimalQuantity = commerceOrderItem.getDecimalQuantity();
				deliveryGroup = commerceOrderItem.getDeliveryGroup();
				discountAmount = commerceOrderItem.getDiscountAmount();
				discountPercentageLevel1 =
					commerceOrderItem.getDiscountPercentageLevel1();
				discountPercentageLevel1WithTaxAmount =
					commerceOrderItem.
						getDiscountPercentageLevel1WithTaxAmount();
				discountPercentageLevel2 =
					commerceOrderItem.getDiscountPercentageLevel2();
				discountPercentageLevel2WithTaxAmount =
					commerceOrderItem.
						getDiscountPercentageLevel2WithTaxAmount();
				discountPercentageLevel3 =
					commerceOrderItem.getDiscountPercentageLevel3();
				discountPercentageLevel3WithTaxAmount =
					commerceOrderItem.
						getDiscountPercentageLevel3WithTaxAmount();
				discountPercentageLevel4 =
					commerceOrderItem.getDiscountPercentageLevel4();
				discountPercentageLevel4WithTaxAmount =
					commerceOrderItem.
						getDiscountPercentageLevel4WithTaxAmount();
				discountWithTaxAmount =
					commerceOrderItem.getDiscountWithTaxAmount();
				externalReferenceCode =
					commerceOrderItem.getExternalReferenceCode();
				finalPrice = commerceOrderItem.getFinalPrice();
				finalPriceWithTaxAmount =
					commerceOrderItem.getFinalPriceWithTaxAmount();
				formattedQuantity = _commerceOrderItemQuantityFormatter.format(
					commerceOrderItem, dtoConverterContext.getLocale());
				id = commerceOrderItem.getCommerceOrderItemId();
				name = LanguageUtils.getLanguageIdMap(
					commerceOrderItem.getNameMap());
				options = commerceOrderItem.getJson();
				orderExternalReferenceCode =
					commerceOrder.getExternalReferenceCode();
				orderId = commerceOrder.getCommerceOrderId();
				printedNote = commerceOrderItem.getPrintedNote();
				promoPrice = commerceOrderItem.getPromoPrice();
				promoPriceWithTaxAmount =
					commerceOrderItem.getPromoPriceWithTaxAmount();
				quantity = commerceOrderItem.getQuantity();
				requestedDeliveryDate =
					commerceOrderItem.getRequestedDeliveryDate();
				shippedQuantity = commerceOrderItem.getShippedQuantity();
				shippingAddressId = commerceOrderItem.getShippingAddressId();
				sku = commerceOrderItem.getSku();
				skuExternalReferenceCode = _getSkuExternalReferenceCode(
					cpInstance);
				skuId = _getSkuId(cpInstance);
				subscription = commerceOrderItem.isSubscription();
				unitPrice = commerceOrderItem.getUnitPrice();
				unitPriceWithTaxAmount =
					commerceOrderItem.getUnitPriceWithTaxAmount();

				setUnitOfMeasure(
					() -> {
						if (commerceOrderItem.getCPMeasurementUnitId() <= 0) {
							return StringPool.BLANK;
						}

						CPMeasurementUnit cpMeasurementUnit =
							_cpMeasurementUnitService.getCPMeasurementUnit(
								commerceOrderItem.getCPMeasurementUnitId());

						return cpMeasurementUnit.getKey();
					});
			}
		};
	}

	private String _getSkuExternalReferenceCode(CPInstance cpInstance) {
		if (cpInstance == null) {
			return StringPool.BLANK;
		}

		return cpInstance.getExternalReferenceCode();
	}

	private Long _getSkuId(CPInstance cpInstance) {
		if (cpInstance == null) {
			return 0L;
		}

		return cpInstance.getCPInstanceId();
	}

	@Reference
	private CommerceOrderItemQuantityFormatter
		_commerceOrderItemQuantityFormatter;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CPMeasurementUnitService _cpMeasurementUnitService;

}