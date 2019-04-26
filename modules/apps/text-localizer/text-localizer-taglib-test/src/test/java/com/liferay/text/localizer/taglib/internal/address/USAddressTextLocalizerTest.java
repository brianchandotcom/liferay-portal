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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.AddressWrapper;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.CountryWrapper;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.RegionWrapper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.text.localizer.address.AddressTextLocalizer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		_setCity(_CITY);
		_setCountry(_COUNTRY_NAME);
		_setRegion(_REGION_NAME);
		_setStreets(_STREET_1, _STREET_2, _STREET_3);
		_setZip(_ZIP);

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
		_setCountry(_COUNTRY_NAME);

		Assert.assertEquals(
			_COUNTRY_NAME, _addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithCity() {
		_setCity(_CITY);

		Assert.assertEquals(_CITY, _addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithCityAndRegionName() {
		_setCity(_CITY);
		_setRegion(_REGION_NAME);

		Assert.assertEquals(
			StringBundler.concat(
				_CITY, StringPool.COMMA_AND_SPACE, _REGION_NAME),
			_addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithCityAndRegionNameAndZip() {
		_setCity(_CITY);
		_setRegion(_REGION_NAME);
		_setZip(_ZIP);

		Assert.assertEquals(
			StringBundler.concat(
				_CITY, StringPool.COMMA_AND_SPACE, _REGION_NAME,
				StringPool.SPACE, _ZIP),
			_addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithCityAndZip() {
		_setCity(_CITY);
		_setZip(_ZIP);

		Assert.assertEquals(
			StringBundler.concat(_CITY, StringPool.COMMA_AND_SPACE, _ZIP),
			_addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithRegionName() {
		_setRegion(_REGION_NAME);

		Assert.assertEquals(
			_REGION_NAME, _addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithRegionNameAndZip() {
		_setRegion(_REGION_NAME);
		_setZip(_ZIP);

		Assert.assertEquals(
			StringBundler.concat(_REGION_NAME, StringPool.SPACE, _ZIP),
			_addressTextLocalizer.format(_address));
	}

	@Test
	public void testRegionLineWithZip() {
		_setZip(_ZIP);

		Assert.assertEquals(_ZIP, _addressTextLocalizer.format(_address));
	}

	@Test
	public void testStreetAndCountryLines() {
		_setCountry(_COUNTRY_NAME);
		_setStreets(_STREET_1, _STREET_2, _STREET_3);

		Assert.assertEquals(
			StringBundler.concat(
				_STREET_1, StringPool.NEW_LINE, _STREET_2, StringPool.NEW_LINE,
				_STREET_3, StringPool.NEW_LINE, _COUNTRY_NAME),
			_addressTextLocalizer.format(_address));
	}

	@Test
	public void testStreetLines() {
		_setStreets(_STREET_1, _STREET_2, _STREET_3);

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

		_setCity(unescapedValue);
		_setStreets(unescapedValue, unescapedValue, unescapedValue);
		_setZip(unescapedValue);

		_setCountry(unescapedValue);
		_setRegion(unescapedValue);

		Html html = new HtmlImpl();

		String escapedValue = html.escape(unescapedValue);

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
		return new AddressWrapper(null) {

			@Override
			public Country getCountry() {
				return _country;
			}

			@Override
			public Region getRegion() {
				return _region;
			}

			@Override
			public Address toEscapedModel() {
				Html html = new HtmlImpl();

				return new AddressWrapper(null) {

					@Override
					public String getCity() {
						return html.escape(_city);
					}

					@Override
					public String getStreet1() {
						return html.escape(_street1);
					}

					@Override
					public String getStreet2() {
						return html.escape(_street2);
					}

					@Override
					public String getStreet3() {
						return html.escape(_street3);
					}

					@Override
					public String getZip() {
						return html.escape(_zip);
					}

				};
			}

		};
	}

	private AddressTextLocalizer _createAddressTextLocalizer() {
		return new USAddressTextLocalizer() {
			{
				html = new HtmlImpl();
			}
		};
	}

	private void _setCity(String city) {
		_city = city;
	}

	private void _setCountry(String countryName) {
		_country = new CountryWrapper(null) {

			@Override
			public long getCountryId() {
				return RandomTestUtil.randomLong();
			}

			@Override
			public String getName() {
				return countryName;
			}

		};
	}

	private void _setRegion(String regionName) {
		_region = new RegionWrapper(null) {

			@Override
			public String getName() {
				return regionName;
			}

			@Override
			public long getRegionId() {
				return RandomTestUtil.randomLong();
			}

		};
	}

	private void _setStreets(String street1, String street2, String street3) {
		_street1 = street1;
		_street2 = street2;
		_street3 = street3;
	}

	private void _setZip(String zip) {
		_zip = zip;
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
	private String _city;
	private Country _country;
	private Region _region;
	private String _street1;
	private String _street2;
	private String _street3;
	private String _zip;

}