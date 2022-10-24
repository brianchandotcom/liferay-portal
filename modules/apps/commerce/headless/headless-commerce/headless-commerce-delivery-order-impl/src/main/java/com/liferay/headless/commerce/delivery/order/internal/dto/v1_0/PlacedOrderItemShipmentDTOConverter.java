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

package com.liferay.headless.commerce.delivery.order.internal.dto.v1_0;

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItemShipment;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "dto.class.name=com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItemShipment",
	service = {DTOConverter.class, PlacedOrderItemShipmentDTOConverter.class}
)
public class PlacedOrderItemShipmentDTOConverter
	implements DTOConverter<CommerceShipment, PlacedOrderItemShipment> {

	@Override
	public String getContentType() {
		return PlacedOrderItemShipment.class.getSimpleName();
	}

	@Override
	public PlacedOrderItemShipment toDTO(
			DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.getCommerceShipmentItem(
				(Long)dtoConverterContext.getId());

		CommerceShipment commerceShipment =
			commerceShipmentItem.getCommerceShipment();

		return new PlacedOrderItemShipment() {
			{
				accountId = commerceShipment.getCommerceAccountId();
				author = commerceShipment.getUserName();
				carrier = commerceShipment.getCarrier();
				createDate = commerceShipment.getCreateDate();
				estimatedDeliveryDate = commerceShipment.getExpectedDate();
				estimatedShippingDate = commerceShipment.getShippingDate();
				id = commerceShipment.getCommerceShipmentId();
				modifiedDate = commerceShipment.getModifiedDate();
				quantity = commerceShipmentItem.getQuantity();
				shippingAddressId = commerceShipment.getCommerceAddressId();
				shippingMethodId =
					commerceShipment.getCommerceShippingMethodId();
				shippingOptionName = commerceShipment.getShippingOptionName();
				trackingNumber = commerceShipment.getTrackingNumber();
			}
		};
	}

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

}