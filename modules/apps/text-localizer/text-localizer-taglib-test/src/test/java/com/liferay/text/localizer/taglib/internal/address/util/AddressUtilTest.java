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
import com.liferay.portal.kernel.model.AddressWrapper;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.CountryWrapper;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.RegionWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class AddressUtilTest {

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetCountryNameOptionalEmptyWithNoCountry() {
		Optional<String> countryNameOptional =
			AddressUtil.getCountryNameOptional(
				new AddressWrapper(null) {

					@Override
					public Country getCountry() {
						return null;
					}

				});

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
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setLanguageId(LocaleUtil.toLanguageId(LocaleUtil.US));

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		Optional<String> countryNameOptional =
			AddressUtil.getCountryNameOptional(_getAddressWithCountry());

		Assert.assertEquals(_COUNTRY_NAME_LOCALIZED, countryNameOptional.get());
	}

	@Test
	public void testGetCountryNameOptionalNotLocalized() {
		Optional<String> countryNameOptional =
			AddressUtil.getCountryNameOptional(_getAddressWithCountry());

		Assert.assertEquals(_COUNTRY_NAME, countryNameOptional.get());
	}

	@Test
	public void testGetRegionNameOptional() {
		Optional<String> regionNameOptional = AddressUtil.getRegionNameOptional(
			new AddressWrapper(null) {

				@Override
				public Region getRegion() {
					return new RegionWrapper(null) {

						@Override
						public String getName() {
							return _REGION_NAME;
						}

						@Override
						public long getRegionId() {
							return RandomTestUtil.randomLong();
						}

					};
				}

			});

		Assert.assertEquals(_REGION_NAME, regionNameOptional.get());
	}

	@Test
	public void testGetRegionNameOptionalEmptyWithNoRegion() {
		Optional<String> regionNameOptional = AddressUtil.getRegionNameOptional(
			new AddressWrapper(null) {

				@Override
				public Region getRegion() {
					return null;
				}

			});

		Assert.assertFalse(regionNameOptional.isPresent());
	}

	@Test
	public void testGetRegionNameOptionalEmptyWithNullAddress() {
		Optional<String> regionNameOptional = AddressUtil.getRegionNameOptional(
			null);

		Assert.assertFalse(regionNameOptional.isPresent());
	}

	private Address _getAddressWithCountry() {
		return new AddressWrapper(null) {

			@Override
			public Country getCountry() {
				return new CountryWrapper(null) {

					@Override
					public long getCountryId() {
						return RandomTestUtil.randomLong();
					}

					@Override
					public String getName() {
						return _COUNTRY_NAME;
					}

					@Override
					public String getName(Locale locale) {
						return _COUNTRY_NAME_LOCALIZED;
					}

				};
			}

		};
	}

	private static final String _COUNTRY_NAME = RandomTestUtil.randomString();

	private static final String _COUNTRY_NAME_LOCALIZED =
		RandomTestUtil.randomString();

	private static final String _REGION_NAME = RandomTestUtil.randomString();

}