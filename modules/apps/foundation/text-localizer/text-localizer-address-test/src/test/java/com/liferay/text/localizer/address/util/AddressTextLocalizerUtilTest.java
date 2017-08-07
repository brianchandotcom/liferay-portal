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

package com.liferay.text.localizer.address.util;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.AddressWrapper;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.CountryWrapper;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.impl.AddressImpl;
import com.liferay.portal.model.impl.CountryImpl;
import com.liferay.portal.model.impl.RegionImpl;

import java.util.Locale;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class AddressTextLocalizerUtilTest {

	@Before
	public void setUp() {
		_address = new AddressImpl();
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetCountryNameOptionalEmptyWithNoCountrySet() {
		Optional<String> countryNameOptional =
			AddressTextLocalizerUtil.getCountryNameOptional(_address);

		Assert.assertFalse(countryNameOptional.isPresent());
	}

	@Test
	public void testGetCountryNameOptionalEmptyWithNullAddress() {
		Optional<String> countryNameOptional =
			AddressTextLocalizerUtil.getCountryNameOptional(null);

		Assert.assertFalse(countryNameOptional.isPresent());
	}

	@Test
	public void testGetCountryNameOptionalLocalized() {
		_address = _setCountry(_address);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setLanguageId(LocaleUtil.toLanguageId(LocaleUtil.US));

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		Optional<String> countryNameOptional =
			AddressTextLocalizerUtil.getCountryNameOptional(_address);

		Assert.assertEquals(_COUNTRY_NAME_LOCALIZED, countryNameOptional.get());
	}

	@Test
	public void testGetCountryNameOptionalNotLocalized() {
		_address = _setCountry(_address);

		Optional<String> countryNameOptional =
			AddressTextLocalizerUtil.getCountryNameOptional(_address);

		Assert.assertEquals(_COUNTRY_NAME, countryNameOptional.get());
	}

	@Test
	public void testGetRegionNameOptional() {
		Region region = new RegionImpl();

		region.setName(_REGION_NAME);

		region.setRegionId(RandomTestUtil.randomLong());

		_address = new AddressWrapper(_address) {

			@Override
			public Region getRegion() {
				return region;
			}

		};

		Optional<String> regionNameOptional =
			AddressTextLocalizerUtil.getRegionNameOptional(_address);

		Assert.assertEquals(_REGION_NAME, regionNameOptional.get());
	}

	@Test
	public void testGetRegionNameOptionalEmptyWithNoRegionSet() {
		Optional<String> regionNameOptional =
			AddressTextLocalizerUtil.getRegionNameOptional(_address);

		Assert.assertFalse(regionNameOptional.isPresent());
	}

	@Test
	public void testGetRegionNameOptionalEmptyWithNullAddress() {
		Optional<String> regionNameOptional =
			AddressTextLocalizerUtil.getRegionNameOptional(null);

		Assert.assertFalse(regionNameOptional.isPresent());
	}

	private Address _setCountry(Address address) {
		Country country = new CountryWrapper(new CountryImpl()) {

			@Override
			public String getName(Locale locale) {
				return _COUNTRY_NAME_LOCALIZED;
			}

		};

		country.setName(_COUNTRY_NAME);

		country.setCountryId(RandomTestUtil.randomLong());

		return new AddressWrapper(address) {

			@Override
			public Country getCountry() {
				return country;
			}

		};
	}

	private static final String _COUNTRY_NAME = RandomTestUtil.randomString();

	private static final String _COUNTRY_NAME_LOCALIZED =
		RandomTestUtil.randomString();

	private static final String _REGION_NAME = RandomTestUtil.randomString();

	private Address _address;

}