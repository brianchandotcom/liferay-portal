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

package com.liferay.address.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Albert Lee
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class RegionLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddRegion() throws Exception {
		Country country = _addCountry();

		Region region = _addRegion(country.getCountryId());

		Assert.assertNotNull(
			_regionLocalService.fetchRegion(region.getRegionId()));

		User user = TestPropsValues.getUser();

		Address address = _addressLocalService.addAddress(
			null, user.getUserId(), null, user.getContactId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			region.getRegionId(), region.getCountryId(),
			RandomTestUtil.randomLong(), false, false, "1234567890",
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(region.getCountryId(), address.getCountryId());
		Assert.assertEquals(region.getRegionId(), address.getRegionId());

		_regionLocalService.deleteRegion(region.getRegionId());

		Assert.assertNull(
			_addressLocalService.fetchAddress(address.getAddressId()));
		Assert.assertNull(
			_regionLocalService.fetchRegion(region.getRegionId()));
	}

	@Test
	public void testDeleteRegion() throws Exception {
		Country country = _addCountry();

		Region region = _addRegion(country.getCountryId());

		Organization organization = OrganizationTestUtil.addOrganization();

		organization.setRegionId(region.getRegionId());
		organization.setCountryId(region.getCountryId());

		_organizationLocalService.updateOrganization(organization);

		Assert.assertFalse(
			ListUtil.isEmpty(
				_organizationLocalService.search(
					country.getCompanyId(),
					OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null,
					null, region.getRegionId(), country.getCountryId(), null,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)));

		_regionLocalService.deleteRegion(region);

		Assert.assertNull(
			_regionLocalService.fetchRegion(region.getRegionId()));

		organization = _organizationLocalService.getOrganization(
			organization.getOrganizationId());

		Assert.assertEquals(0, organization.getRegionId());

		Assert.assertTrue(
			ListUtil.isEmpty(
				_organizationLocalService.search(
					country.getCompanyId(),
					OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null,
					null, region.getRegionId(), country.getCountryId(), null,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)));
	}

	@Test
	public void testUpdateRegion() throws Exception {
		Country country = _addCountry();

		Region region = _addRegion(country.getCountryId());

		boolean active = RandomTestUtil.randomBoolean();
		String name = RandomTestUtil.randomString();
		double position = RandomTestUtil.randomDouble();
		String regionCode = RandomTestUtil.randomString();

		region = _regionLocalService.updateRegion(
			region.getRegionId(), active, name, position, regionCode);

		Assert.assertEquals(active, region.isActive());
		Assert.assertEquals(name, region.getName());
		Assert.assertEquals(position, region.getPosition(), 0);
		Assert.assertEquals(regionCode, region.getRegionCode());
	}

	private Country _addCountry() throws Exception {
		return _countryLocalService.addCountry(
			"aa", "aaa", true, RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean(),
			ServiceContextTestUtil.getServiceContext());
	}

	private Region _addRegion(long countryId) throws Exception {
		return _regionLocalService.addRegion(
			countryId, RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

	@Inject
	private static AddressLocalService _addressLocalService;

	@Inject
	private static CountryLocalService _countryLocalService;

	@Inject
	private static OrganizationLocalService _organizationLocalService;

	@Inject
	private static RegionLocalService _regionLocalService;

}