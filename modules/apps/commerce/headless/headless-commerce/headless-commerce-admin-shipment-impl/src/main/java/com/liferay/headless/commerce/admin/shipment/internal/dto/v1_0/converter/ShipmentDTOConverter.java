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

package com.liferay.headless.commerce.admin.shipment.internal.dto.v1_0.converter;

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.Shipment;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.model.CommerceShipment",
	service = {DTOConverter.class, ShipmentDTOConverter.class}
)
public class ShipmentDTOConverter
	implements DTOConverter<CommerceShipment, Shipment> {

	@Override
	public String getContentType() {
		return Shipment.class.getSimpleName();
	}

	@Override
	public Shipment toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.getCommerceShipment(
				(Long)dtoConverterContext.getId());

		return new Shipment() {
			{
				accountId = commerceShipment.getCommerceAccountId();
				actions = dtoConverterContext.getActions();
				carrier = commerceShipment.getCarrier();
				createDate = commerceShipment.getCreateDate();
				expectedDate = commerceShipment.getExpectedDate();
				externalReferenceCode =
					commerceShipment.getExternalReferenceCode();
				id = commerceShipment.getCommerceShipmentId();
				modifiedDate = commerceShipment.getModifiedDate();
				shippingAddressId = commerceShipment.getCommerceAddressId();
				shippingDate = commerceShipment.getShippingDate();
				shippingMethodId =
					commerceShipment.getCommerceShippingMethodId();
				shippingOptionName = commerceShipment.getShippingOptionName();
				trackingNumber = commerceShipment.getTrackingNumber();
				userName = commerceShipment.getUserName();
			}
		};
	}

	@Reference
	private CommerceShipmentService _commerceShipmentService;

}