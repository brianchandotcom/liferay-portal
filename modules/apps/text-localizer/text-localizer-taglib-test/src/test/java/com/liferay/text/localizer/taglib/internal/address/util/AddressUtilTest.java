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

package com.liferay.text.localizer.taglib.internal.address.util;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Drew Brokke
 */
public class AddressUtilTest {

	@Before
	public void setUp() {
		_address = Mockito.mock(Address.class);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetCountryNameOptionalEmptyWithNoCountry() {
		Optional<String> countryNameOptional =
			AddressUtil.getCountryNameOptional(_address);

		Assert.assertFalse(countryNameOptional.isPresent());
	}

	@Test
	public void testGetCountryNameOptionalEmptyWithNullAddress() {
		Optional<String> countryNameOptional =
			AddressUtil.getCountryNameOptional(null);

		Assert.assertFalse(countryNameOptional.isPresent());
	}

	@Test
	public void testGetCountryNameOptionalLocalized() {
		_address = _setCountry(_address);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setLanguageId(LocaleUtil.toLanguageId(LocaleUtil.US));

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		Optional<String> countryNameOptional =
			AddressUtil.getCountryNameOptional(_address);

		Assert.assertEquals(_COUNTRY_NAME_LOCALIZED, countryNameOptional.get());
	}

	@Test
	public void testGetCountryNameOptionalNotLocalized() {
		_address = _setCountry(_address);

		Optional<String> countryNameOptional =
			AddressUtil.getCountryNameOptional(_address);

		Assert.assertEquals(_COUNTRY_NAME, countryNameOptional.get());
	}

	@Test
	public void testGetRegionNameOptional() {
		Region region = Mockito.mock(Region.class);

		Mockito.doReturn(
			region
		).when(
			_address
		).getRegion();

		Mockito.doReturn(
			_REGION_NAME
		).when(
			region
		).getName();

		Mockito.doReturn(
			RandomTestUtil.randomLong()
		).when(
			region
		).getRegionId();

		Optional<String> regionNameOptional = AddressUtil.getRegionNameOptional(
			_address);

		Assert.assertEquals(_REGION_NAME, regionNameOptional.get());
	}

	@Test
	public void testGetRegionNameOptionalEmptyWithNoRegion() {
		Optional<String> regionNameOptional = AddressUtil.getRegionNameOptional(
			_address);

		Assert.assertFalse(regionNameOptional.isPresent());
	}

	@Test
	public void testGetRegionNameOptionalEmptyWithNullAddress() {
		Optional<String> regionNameOptional = AddressUtil.getRegionNameOptional(
			null);

		Assert.assertFalse(regionNameOptional.isPresent());
	}

	private Address _setCountry(Address address) {
		Country country = Mockito.mock(Country.class);

		Mockito.doReturn(
			country
		).when(
			address
		).getCountry();

		Mockito.doReturn(
			RandomTestUtil.randomLong()
		).when(
			country
		).getCountryId();

		Mockito.doReturn(
			_COUNTRY_NAME
		).when(
			country
		).getName();

		Mockito.doReturn(
			_COUNTRY_NAME_LOCALIZED
		).when(
			country
		).getName(
			Mockito.any(Locale.class)
		);

		return address;
	}

	private static final String _COUNTRY_NAME = RandomTestUtil.randomString();

	private static final String _COUNTRY_NAME_LOCALIZED =
		RandomTestUtil.randomString();

	private static final String _REGION_NAME = RandomTestUtil.randomString();

	private Address _address;

}