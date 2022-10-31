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

package com.liferay.headless.commerce.delivery.order.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItem;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItemShipment;
import com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.PlacedOrderItemShipmentDTOConverter;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderItemShipmentResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/placed-order-item-shipment.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, PlacedOrderItemShipmentResource.class}
)
public class PlacedOrderItemShipmentResourceImpl
	extends BasePlacedOrderItemShipmentResourceImpl
	implements NestedFieldSupport {

	@NestedField(
		parentClass = PlacedOrderItem.class, value = "placedOrderItemShipments"
	)
	@Override
	public Page<PlacedOrderItemShipment>
			getPlacedOrderItemPlacedOrderItemShipmentsPage(
				@NestedFieldId("id") Long placedOrderItemId)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(placedOrderItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		if (commerceOrder.isOpen()) {
			throw new NoSuchOrderException();
		}

		return Page.of(
			transform(
				_commerceShipmentItemService.
					getCommerceShipmentItemsByCommerceOrderItemId(
						placedOrderItemId),
				commerceShipmentItem -> _toPlaceOrderItemShipment(
					commerceShipmentItem.getCommerceShipmentItemId())),
			null,
			_commerceShipmentItemService.
				getCommerceShipmentItemsCountByCommerceOrderItemId(
					placedOrderItemId));
	}

	private PlacedOrderItemShipment _toPlaceOrderItemShipment(
			long commerceShipmentItemId)
		throws Exception {

		return _placedOrderItemShipmentDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				_dtoConverterRegistry, commerceShipmentItemId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private PlacedOrderItemShipmentDTOConverter
		_placedOrderItemShipmentDTOConverter;

}