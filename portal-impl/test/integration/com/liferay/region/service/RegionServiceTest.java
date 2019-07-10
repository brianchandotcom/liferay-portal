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

package com.liferay.region.service;

import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.RegionServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class RegionServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testJapanRegionOrdering() {
		Country countryJapan = CountryServiceUtil.fetchCountryByA2("JP");

		Assert.assertNotNull(countryJapan);

		long countryId = countryJapan.getCountryId();

		List<Region> regions = RegionServiceUtil.getRegions(countryId, true);

		List<Region> sortedRegions = ListUtil.sort(
			RegionServiceUtil.getRegions(countryId, true),
			(region1, region2) -> {
				String regionCode1 = region1.getRegionCode();
				String regionCode2 = region2.getRegionCode();

				return regionCode1.compareTo(regionCode2);
			});

		Assert.assertEquals(
			"Japan regions should be sorted by region code", sortedRegions,
			regions);
	}

}