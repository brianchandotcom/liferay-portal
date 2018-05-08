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

package com.liferay.text.localizer.taglib.internal.address;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.text.localizer.address.AddressTextLocalizer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Drew Brokke
 */
public class USAddressTextLocalizerTest {

	@Before
	public void setUp() {
		_address = _createAddress();
	}

	@Test
	public void testAllFields() {
		_setCity(_address);
		_setCountry(_address);
		_setRegion(_address);
		_setStreets(_address);
		_setZip(_address);

		Assert.assertEquals(
			StringBundler.concat(
				_STREET_1, StringPool.NEW_LINE, _STREET_2, StringPool.NEW_LINE,
				_STREET_3, StringPool.NEW_LINE, _CITY,
				StringPool.COMMA_AND_SPACE, _REGION_NAME, StringPool.SPACE,
				_ZIP, StringPool.NEW_LINE, _COUNTRY_NAME),
			_addressTextLocalizer.format(_address));
	}

	@Test
	public void testCountryLine() {
		_setCountry(_address);

		Assert.assertEquals(
			_COUNTRY_NAME, _addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithCity() {
		_setCity(_address);

		Assert.assertEquals(_CITY, _addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithCityAndRegionName() {
		_setCity(_address);
		_setRegion(_address);

		Assert.assertEquals(
			StringBundler.concat(
				_CITY, StringPool.COMMA_AND_SPACE, _REGION_NAME),
			_addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithCityAndRegionNameAndZip() {
		_setCity(_address);
		_setRegion(_address);
		_setZip(_address);

		Assert.assertEquals(
			StringBundler.concat(
				_CITY, StringPool.COMMA_AND_SPACE, _REGION_NAME,
				StringPool.SPACE, _ZIP),
			_addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithCityAndZip() {
		_setCity(_address);
		_setZip(_address);

		Assert.assertEquals(
			StringBundler.concat(_CITY, StringPool.COMMA_AND_SPACE, _ZIP),
			_addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithRegionName() {
		_setRegion(_address);

		Assert.assertEquals(
			_REGION_NAME, _addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithRegionNameAndZip() {
		_setRegion(_address);
		_setZip(_address);

		Assert.assertEquals(
			StringBundler.concat(_REGION_NAME, StringPool.SPACE, _ZIP),
			_addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithZip() {
		_setZip(_address);

		Assert.assertEquals(_ZIP, _addressTextLocalizer.format(_address));
	}

	@Test
	public void testStreetAndCountryLines() {
		_setCountry(_address);
		_setStreets(_address);

		Assert.assertEquals(
			StringBundler.concat(
				_STREET_1, StringPool.NEW_LINE, _STREET_2, StringPool.NEW_LINE,
				_STREET_3, StringPool.NEW_LINE, _COUNTRY_NAME),
			_addressTextLocalizer.format(_address));
	}

	@Test
	public void testStreetLines() {
		_setStreets(_address);

		Assert.assertEquals(
			StringBundler.concat(
				_STREET_1, StringPool.NEW_LINE, _STREET_2, StringPool.NEW_LINE,
				_STREET_3),
			_addressTextLocalizer.format(_address));
	}

	@Test
	public void testWillEscapeFields() {
		String unescapedValue =
			"<script type=\"text/javascript\">alert(\"Hello World\");</script>";

		Mockito.doReturn(
			unescapedValue
		).when(
			_address
		).getCity();

		Mockito.doReturn(
			unescapedValue
		).when(
			_address
		).getStreet1();

		Mockito.doReturn(
			unescapedValue
		).when(
			_address
		).getStreet2();

		Mockito.doReturn(
			unescapedValue
		).when(
			_address
		).getStreet3();

		Mockito.doReturn(
			unescapedValue
		).when(
			_address
		).getZip();

		_setCountry(_address, unescapedValue);
		_setRegion(_address, unescapedValue);

		String escapedValue = _html.escape(unescapedValue);

		Assert.assertEquals(
			StringBundler.concat(
				escapedValue, StringPool.NEW_LINE, escapedValue,
				StringPool.NEW_LINE, escapedValue, StringPool.NEW_LINE,
				escapedValue, StringPool.COMMA_AND_SPACE, escapedValue,
				StringPool.SPACE, escapedValue, StringPool.NEW_LINE,
				escapedValue),
			_addressTextLocalizer.format(_address));
	}

	private Address _createAddress() {
		Address address = Mockito.mock(Address.class);

		Mockito.doReturn(
			_toEscapedModel(address)
		).when(
			address
		).toEscapedModel();

		return address;
	}

	private AddressTextLocalizer _createAddressTextLocalizer() {
		return new USAddressTextLocalizer() {
			{
				html = new HtmlImpl();
			}
		};
	}

	private void _setCity(Address address) {
		Mockito.doReturn(
			_CITY
		).when(
			address
		).getCity();
	}

	private void _setCountry(Address address) {
		_setCountry(address, _COUNTRY_NAME);
	}

	private void _setCountry(Address address, String countryName) {
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
			countryName
		).when(
			country
		).getName();
	}

	private void _setRegion(Address address) {
		_setRegion(address, _REGION_NAME);
	}

	private void _setRegion(Address address, String regionName) {
		Region region = Mockito.mock(Region.class);

		Mockito.doReturn(
			region
		).when(
			address
		).getRegion();

		Mockito.doReturn(
			regionName
		).when(
			region
		).getName();

		Mockito.doReturn(
			RandomTestUtil.randomLong()
		).when(
			region
		).getRegionId();
	}

	private void _setStreets(Address address) {
		Mockito.doReturn(
			_STREET_1
		).when(
			address
		).getStreet1();

		Mockito.doReturn(
			_STREET_2
		).when(
			address
		).getStreet2();

		Mockito.doReturn(
			_STREET_3
		).when(
			address
		).getStreet3();
	}

	private void _setZip(Address address) {
		Mockito.doReturn(
			_ZIP
		).when(
			address
		).getZip();
	}

	private Address _toEscapedModel(Address address) {
		Address escapedAddress = Mockito.mock(Address.class);

		Mockito.doAnswer(
			invocationOnMock -> _html.escape(address.getCity())
		).when(
			escapedAddress
		).getCity();

		Mockito.doAnswer(
			invocationOnMock -> _html.escape(address.getStreet1())
		).when(
			escapedAddress
		).getStreet1();

		Mockito.doAnswer(
			invocationOnMock -> _html.escape(address.getStreet2())
		).when(
			escapedAddress
		).getStreet2();

		Mockito.doAnswer(
			invocationOnMock -> _html.escape(address.getStreet3())
		).when(
			escapedAddress
		).getStreet3();

		Mockito.doAnswer(
			invocationOnMock -> _html.escape(address.getZip())
		).when(
			escapedAddress
		).getZip();

		return escapedAddress;
	}

	private static final String _CITY = RandomTestUtil.randomString();

	private static final String _COUNTRY_NAME = RandomTestUtil.randomString();

	private static final String _REGION_NAME = RandomTestUtil.randomString();

	private static final String _STREET_1 = RandomTestUtil.randomString();

	private static final String _STREET_2 = RandomTestUtil.randomString();

	private static final String _STREET_3 = RandomTestUtil.randomString();

	private static final String _ZIP = RandomTestUtil.randomString();

	private Address _address;
	private final AddressTextLocalizer _addressTextLocalizer =
		_createAddressTextLocalizer();
	private final Html _html = new HtmlImpl();

}