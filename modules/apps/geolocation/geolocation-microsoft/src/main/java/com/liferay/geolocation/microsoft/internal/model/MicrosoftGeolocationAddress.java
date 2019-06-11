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

package com.liferay.geolocation.microsoft.internal.model;

import com.liferay.geolocation.model.GeolocationAddress;

/**
 * @author Eduardo García
 */
public class MicrosoftGeolocationAddress implements GeolocationAddress {

	public MicrosoftGeolocationAddress(
		String countryCode, String regionCode, String city, String street,
		String zip) {

		_countryCode = countryCode;
		_regionCode = regionCode;
		_city = city;
		_street = street;
		_zip = zip;
	}

	@Override
	public String getCity() {
		return _city;
	}

	@Override
	public String getCountryCode() {
		return _countryCode;
	}

	@Override
	public String getRegionCode() {
		return _regionCode;
	}

	@Override
	public String getStreet() {
		return _street;
	}

	@Override
	public String getZip() {
		return _zip;
	}

	private final String _city;
	private final String _countryCode;
	private final String _regionCode;
	private final String _street;
	private final String _zip;

}