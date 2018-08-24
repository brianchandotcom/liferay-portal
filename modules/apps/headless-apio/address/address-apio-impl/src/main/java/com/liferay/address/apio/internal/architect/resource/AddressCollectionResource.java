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

package com.liferay.address.apio.internal.architect.resource;

import com.liferay.address.apio.architect.identifier.AddressIdentifier;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.AddressService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionService;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@code Address} resources
 * through a web API.
 *
 * @author Javier Gamarra
 * @review
 */
@Component(immediate = true)
public class AddressCollectionResource
	implements ItemResource<Address, Long, AddressIdentifier> {

	@Override
	public String getName() {
		return "addresses";
	}

	@Override
	public ItemRoutes<Address, Long> itemRoutes(
		ItemRoutes.Builder<Address, Long> builder) {

		return builder.addGetter(
			_addressService::getAddress
		).build();
	}

	@Override
	public Representor<Address> representor(
		Representor.Builder<Address, Long> builder) {

		return builder.types(
			"Address"
		).identifier(
			Address::getAddressId
		).addLocalizedStringByLocale(
			"addressCountry", this::_getCountry
		).addString(
			"addressLocality", Address::getCity
		).addString(
			"addressRegion", this::_getRegion
		).addString(
			"addressType",
			address -> {
				ListType type = address.getType();

				return type.getName();
			}
		).addString(
			"postalCode", Address::getZip
		).addString(
			"streetAddressLine1", Address::getStreet1
		).addString(
			"streetAddressLine2", Address::getStreet2
		).addString(
			"streetAddressLine3", Address::getStreet3
		).build();
	}

	private String _getCountry(Address address, Locale locale) {
		return Try.success(
			address.getCountryId()
		).map(
			_countryService::getCountry
		).map(
			country -> country.getName(locale)
		).orElse(
			null
		);
	}

	private String _getRegion(Address address) {
		return Try.success(
			address.getRegionId()
		).map(
			_regionService::getRegion
		).map(
			Region::getName
		).orElse(
			null
		);
	}

	@Reference
	private AddressService _addressService;

	@Reference
	private CountryService _countryService;

	@Reference
	private RegionService _regionService;

}