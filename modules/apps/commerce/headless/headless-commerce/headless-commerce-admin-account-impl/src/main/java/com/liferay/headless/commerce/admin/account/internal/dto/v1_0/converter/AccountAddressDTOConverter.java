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

package com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountAddress;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.model.CommerceAddress",
	service = {AccountAddressDTOConverter.class, DTOConverter.class}
)
public class AccountAddressDTOConverter
	implements DTOConverter<CommerceAddress, AccountAddress> {

	@Override
	public String getContentType() {
		return AccountAddress.class.getSimpleName();
	}

	@Override
	public AccountAddress toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceAddress commerceAddress =
			_commerceAddressService.getCommerceAddress(
				(Long)dtoConverterContext.getId());

		Country country = commerceAddress.getCountry();

		return new AccountAddress() {
			{
				city = commerceAddress.getCity();
				countryISOCode = country.getA2();
				defaultBilling = commerceAddress.isDefaultBilling();
				defaultShipping = commerceAddress.isDefaultShipping();
				description = commerceAddress.getDescription();
				externalReferenceCode =
					commerceAddress.getExternalReferenceCode();
				id = commerceAddress.getCommerceAddressId();
				latitude = commerceAddress.getLatitude();
				longitude = commerceAddress.getLongitude();
				name = commerceAddress.getName();
				phoneNumber = commerceAddress.getPhoneNumber();
				regionISOCode = _getRegionISOCode(commerceAddress);
				street1 = commerceAddress.getStreet1();
				street2 = commerceAddress.getStreet2();
				street3 = commerceAddress.getStreet3();
				type = commerceAddress.getType();
				zip = commerceAddress.getZip();
			}
		};
	}

	private String _getRegionISOCode(CommerceAddress commerceAddress)
		throws Exception {

		Region region = commerceAddress.getRegion();

		if (region == null) {
			return StringPool.BLANK;
		}

		return region.getRegionCode();
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

}