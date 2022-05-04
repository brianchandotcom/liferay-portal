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

package com.liferay.headless.admin.address.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.address.client.dto.v1_0.Region;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class RegionResourceTest extends BaseRegionResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_country = _countryLocalService.addCountry(
			"a1", "a11", true, RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean(),
			ServiceContextTestUtil.getServiceContext());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_countryLocalService.deleteCountry(_country);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name", "position", "regionCode"};
	}

	@Override
	protected Region randomRegion() throws Exception {
		Region region = super.randomRegion();

		region.setCountryId(_country.getCountryId());

		return region;
	}

	@Override
	protected Region testGetCountryRegionsPage_addRegion(
			Long countryId, Region region)
		throws Exception {

		region.setCountryId(countryId);

		return _addRegion(region);
	}

	protected Long testGetCountryRegionsPage_getCountryId() throws Exception {
		return _country.getCountryId();
	}

	protected Long testGetCountryRegionsPage_getIrrelevantCountryId()
		throws Exception {

		Country country = _countryLocalService.addCountry(
			"a2", "a22", true, RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean(),
			ServiceContextTestUtil.getServiceContext());

		return country.getCountryId();
	}

	@Override
	protected Region testGetRegionsPage_addRegion(Region region)
		throws Exception {

		return _addRegion(region);
	}

	@Override
	protected Region testGraphQLRegion_addRegion() throws Exception {
		return _addRegion(randomRegion());
	}

	private Region _addRegion(Region region) throws Exception {
		_regionLocalService.addRegion(
			region.getCountryId(), region.getActive(), region.getName(),
			region.getPosition(), region.getRegionCode(),
			ServiceContextTestUtil.getServiceContext());

		return region;
	}

	private Country _country;

	@Inject
	private CountryLocalService _countryLocalService;

	@Inject
	private RegionLocalService _regionLocalService;

}