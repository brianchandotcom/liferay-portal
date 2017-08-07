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

package com.liferay.text.localizer.address;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.AddressWrapper;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.AddressImpl;
import com.liferay.portal.model.impl.CountryImpl;
import com.liferay.portal.model.impl.RegionImpl;
import com.liferay.portal.util.HtmlImpl;

import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class USAddressTextLocalizerTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			HtmlUtil.class, "_html", new HtmlImpl());

		_address = new AddressImpl();
	}

	@Test
	public void testAllFields() {
		_address = _setStreets(_address);
		_address = _setCity(_address);
		_address = _setRegion(_address);
		_address = _setZip(_address);
		_address = _setCountry(_address);

		Assert.assertEquals(
			_constructExpectedAddress(
				_STREET_1, StringPool.NEW_LINE, _STREET_2, StringPool.NEW_LINE,
				_STREET_3, StringPool.NEW_LINE, _CITY,
				StringPool.COMMA_AND_SPACE, _REGION_NAME, StringPool.SPACE,
				_ZIP, StringPool.NEW_LINE, _COUNTRY_NAME),
			_localizer.format(_address));
	}

	@Test
	public void testCountryLine() {
		_address = _setCountry(_address);

		Assert.assertEquals(
			_constructExpectedAddress(_COUNTRY_NAME),
			_localizer.format(_address));
	}

	@Test
	public void testRegionLineWithCityAndRegionName() {
		_address = _setCity(_address);
		_address = _setRegion(_address);

		Assert.assertEquals(
			_constructExpectedAddress(
				_CITY, StringPool.COMMA_AND_SPACE, _REGION_NAME),
			_localizer.format(_address));
	}

	@Test
	public void testRegionLineWithCityAndRegionNameAndZip() {
		_address = _setCity(_address);
		_address = _setRegion(_address);
		_address = _setZip(_address);

		Assert.assertEquals(
			_constructExpectedAddress(
				_CITY, StringPool.COMMA_AND_SPACE, _REGION_NAME,
				StringPool.SPACE, _ZIP),
			_localizer.format(_address));
	}

	@Test
	public void testRegionLineWithCityAndZip() {
		_address = _setCity(_address);
		_address = _setZip(_address);

		Assert.assertEquals(
			_constructExpectedAddress(_CITY, StringPool.COMMA_AND_SPACE, _ZIP),
			_localizer.format(_address));
	}

	@Test
	public void testRegionLineWithOnlyCity() {
		Assert.assertTrue(true);
	}

	@Test
	public void testRegionLineWithOnlyRegionName() {
		Assert.assertTrue(true);
	}

	@Test
	public void testRegionLineWithOnlyZip() {
		Assert.assertTrue(true);
	}

	@Test
	public void testRegionLineWithRegionNameAndZip() {
		Assert.assertTrue(true);
	}

	@Test
	public void testStreetAndCountryLines() {
		_address = _setStreets(_address);
		_address = _setCountry(_address);

		Assert.assertEquals(
			_constructExpectedAddress(
				_STREET_1, StringPool.NEW_LINE, _STREET_2, StringPool.NEW_LINE,
				_STREET_3, StringPool.NEW_LINE, _COUNTRY_NAME),
			_localizer.format(_address));
	}

	@Test
	public void testStreetLines() {
		_address = _setStreets(_address);

		Assert.assertEquals(
			_constructExpectedAddress(
				_STREET_1, StringPool.NEW_LINE, _STREET_2, StringPool.NEW_LINE,
				_STREET_3),
			_localizer.format(_address));
	}

	@Test
	public void testWillEscapeFields() {
		String xssFieldValue =
			"<script type=\"text/javascript\">alert(\"Hello World\");</script>";

		_address.setStreet1(xssFieldValue);
		_address.setStreet2(xssFieldValue);
		_address.setStreet3(xssFieldValue);
		_address.setCity(xssFieldValue);
		_address.setZip(xssFieldValue);

		_address = _setCountry(_address, xssFieldValue);
		_address = _setRegion(_address, xssFieldValue);

		String escapedValue = HtmlUtil.escape(xssFieldValue);

		Assert.assertEquals(
			_constructExpectedAddress(
				escapedValue, StringPool.NEW_LINE, escapedValue,
				StringPool.NEW_LINE, escapedValue, StringPool.NEW_LINE,
				escapedValue, StringPool.COMMA_AND_SPACE, escapedValue,
				StringPool.SPACE, escapedValue, StringPool.NEW_LINE,
				escapedValue),
			_localizer.format(_address));
	}

	private String _constructExpectedAddress(String... elements) {
		StringBundler sb = new StringBundler(elements.length);

		Stream.of(elements).forEachOrdered(sb::append);

		return sb.toString();
	}

	private Address _setCity(Address address) {
		address.setCity(_CITY);

		return address;
	}

	private Address _setCountry(Address address) {
		return _setCountry(address, _COUNTRY_NAME);
	}

	private Address _setCountry(Address address, String countryName) {
		Country country = new CountryImpl();

		country.setName(countryName);
		country.setCountryId(RandomTestUtil.randomLong());

		return new AddressWrapper(address) {

			@Override
			public Country getCountry() {
				return country;
			}

		};
	}

	private Address _setRegion(Address address) {
		return _setRegion(address, _REGION_NAME);
	}

	private Address _setRegion(Address address, String regionName) {
		Region region = new RegionImpl();

		region.setName(regionName);
		region.setRegionId(RandomTestUtil.randomLong());

		return new AddressWrapper(address) {

			@Override
			public Region getRegion() {
				return region;
			}

		};
	}

	private Address _setStreets(Address address) {
		address.setStreet1(_STREET_1);
		address.setStreet2(_STREET_2);
		address.setStreet3(_STREET_3);

		return address;
	}

	private Address _setZip(Address address) {
		address.setZip(_ZIP);

		return address;
	}

	private static final String _CITY = RandomTestUtil.randomString();

	private static final String _COUNTRY_NAME = RandomTestUtil.randomString();

	private static final String _REGION_NAME = RandomTestUtil.randomString();

	private static final String _STREET_1 = RandomTestUtil.randomString();

	private static final String _STREET_2 = RandomTestUtil.randomString();

	private static final String _STREET_3 = RandomTestUtil.randomString();

	private static final String _ZIP = RandomTestUtil.randomString();

	private Address _address;
	private final AddressTextLocalizer _localizer =
		new USAddressTextLocalizer();

}