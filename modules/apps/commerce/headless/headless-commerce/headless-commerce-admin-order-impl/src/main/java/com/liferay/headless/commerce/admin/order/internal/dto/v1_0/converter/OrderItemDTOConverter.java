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
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.core.dto.v1_0.converter.DTOConverter;
import com.liferay.headless.commerce.core.dto.v1_0.converter.DTOConverterContext;
import com.liferay.headless.commerce.core.dto.v1_0.converter.DTOConverterRegistry;
import com.liferay.headless.commerce.core.dto.v1_0.converter.DefaultDTOConverterContext;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.petra.string.StringPool;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.model.CommerceOrderItem",
	service = {DTOConverter.class, OrderItemDTOConverter.class}
)
public class OrderItemDTOConverter implements DTOConverter {

	@Override
	public String getContentType() {
		return OrderItem.class.getSimpleName();
	}

	public OrderItem toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(
				dtoConverterContext.getResourcePrimKey());

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();
		CPInstance cpInstance = commerceOrderItem.fetchCPInstance();
		ExpandoBridge expandoBridge = commerceOrderItem.getExpandoBridge();

		return new OrderItem() {
			{
				bookedQuantityId = commerceOrderItem.getBookedQuantityId();
				customFields = expandoBridge.getAttributes();
				deliveryGroup = commerceOrderItem.getDeliveryGroup();
				discountAmount = commerceOrderItem.getDiscountAmount();
				discountPercentageLevel1 =
					commerceOrderItem.getDiscountPercentageLevel1();
				discountPercentageLevel2 =
					commerceOrderItem.getDiscountPercentageLevel2();
				discountPercentageLevel3 =
					commerceOrderItem.getDiscountPercentageLevel3();
				discountPercentageLevel4 =
					commerceOrderItem.getDiscountPercentageLevel4();
				externalReferenceCode =
					commerceOrderItem.getExternalReferenceCode();
				finalPrice = commerceOrderItem.getFinalPrice();
				id = commerceOrderItem.getCommerceOrderItemId();
				name = LanguageUtils.getLanguageIdMap(
					commerceOrderItem.getNameMap());
				orderExternalReferenceCode =
					commerceOrder.getExternalReferenceCode();
				orderId = commerceOrder.getCommerceOrderId();
				printedNote = commerceOrderItem.getPrintedNote();
				promoPrice = commerceOrderItem.getPromoPrice();
				quantity = commerceOrderItem.getQuantity();
				requestedDeliveryDate =
					commerceOrderItem.getRequestedDeliveryDate();
				shippedQuantity = commerceOrderItem.getShippedQuantity();
				shippingAddress = _getShippingAddress(
					dtoConverterContext.getLocale(),
					commerceOrderItem.getShippingAddressId());
				shippingAddressId = commerceOrderItem.getShippingAddressId();
				sku = commerceOrderItem.getSku();
				skuExternalReferenceCode = _getSkuExternalReferenceCode(
					cpInstance);
				skuId = _getSkuId(cpInstance);
				subscription = commerceOrderItem.isSubscription();
				unitPrice = commerceOrderItem.getUnitPrice();
			}
		};
	}

	private ShippingAddress _getShippingAddress(
			Locale locale, long shippingAddressId)
		throws Exception {

		if (shippingAddressId <= 0) {
			return new ShippingAddress();
		}

		DTOConverter shippingAddressDTOConverter =
			_dtoConverterRegistry.getDTOConverter("ShippingAddress");

		return (ShippingAddress)shippingAddressDTOConverter.toDTO(
			new DefaultDTOConverterContext(locale, shippingAddressId));
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
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}