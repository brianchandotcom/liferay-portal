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

import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.Shipment;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.Status;
import com.liferay.headless.commerce.admin.shipment.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
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

		String commerceShipmentStatusLabel =
			CommerceShipmentConstants.getShipmentStatusLabel(
				commerceShipment.getStatus());

		String commerceShipmentStatusLabelI18n = _language.get(
			LanguageResources.getResourceBundle(
				dtoConverterContext.getLocale()),
			CommerceShipmentConstants.getShipmentStatusLabel(
				commerceShipment.getStatus()));

		return new Shipment() {
			{
				accountId = commerceShipment.getCommerceAccountId();
				actions = dtoConverterContext.getActions();
				carrier = commerceShipment.getCarrier();
				createDate = commerceShipment.getCreateDate();
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					CommerceShipment.class.getName(),
					commerceShipment.getCommerceShipmentId(),
					commerceShipment.getCompanyId(),
					dtoConverterContext.getLocale());
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
				status = _toStatus(
					commerceShipment.getStatus(), commerceShipmentStatusLabel,
					commerceShipmentStatusLabelI18n);
				trackingNumber = commerceShipment.getTrackingNumber();
				trackingURL = commerceShipment.getTrackingURL();
				userName = commerceShipment.getUserName();
			}
		};
	}

	private Status _toStatus(
		int statusCode, String shipmentStatusLabel,
		String shipmentStatusLabelI18n) {

		return new Status() {
			{
				code = statusCode;
				label = shipmentStatusLabel;
				label_i18n = shipmentStatusLabelI18n;
			}
		};
	}

	@Reference
	private CommerceShipmentService _commerceShipmentService;

	@Reference
	private Language _language;

}