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

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Address;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "model.class.name=com.liferay.headless.commerce.delivery.cart.dto.v1_0.Address",
	service = {AddressDTOConverter.class, DTOConverter.class}
)
public class AddressDTOConverter
	implements DTOConverter<CommerceAddress, Address> {

	@Override
	public String getContentType() {
		return Address.class.getSimpleName();
	}

	public Address toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceAddress commerceAddress =
			_commerceAddressService.getCommerceAddress(
				(Long)dtoConverterContext.getId());

		CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		Locale locale = dtoConverterContext.getLocale();

		Address address = new Address() {
			{
				city = commerceAddress.getCity();
				country = commerceCountry.getName(locale);
				countryISOCode = commerceCountry.getTwoLettersISOCode();
				description = commerceAddress.getDescription();
				id = commerceAddress.getCommerceAddressId();
				latitude = commerceAddress.getLatitude();
				longitude = commerceAddress.getLongitude();
				name = commerceAddress.getName();
				phoneNumber = commerceAddress.getPhoneNumber();
				street1 = commerceAddress.getStreet1();
				street2 = commerceAddress.getStreet2();
				street3 = commerceAddress.getStreet3();
				typeId = commerceAddress.getType();
				zip = commerceAddress.getZip();
			}
		};

		if (commerceRegion != null) {
			address.setRegion(commerceRegion.getName());
			address.setRegionISOCode(_getRegionISOCode(commerceRegion));
		}

		return address;
	}

	private String _getRegionISOCode(CommerceRegion commerceRegion) {
		if (commerceRegion == null) {
			return StringPool.BLANK;
		}

		return commerceRegion.getCode();
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

}