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
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

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
public class CountryLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddCountry() throws Exception {
		boolean billingAllowed = true;
		String number = RandomTestUtil.randomString();
		double position = RandomTestUtil.randomDouble();
		boolean shippingAllowed = true;
		boolean subjectToVAT = true;
		boolean zipRequired = true;

		Country country = _addCountry(
			billingAllowed, number, position, shippingAllowed, subjectToVAT,
			zipRequired);

		Assert.assertEquals(billingAllowed, country.isBillingAllowed());
		Assert.assertEquals(number, country.getNumber());
		Assert.assertEquals(position, country.getPosition(), 0);
		Assert.assertEquals(shippingAllowed, country.isShippingAllowed());
		Assert.assertEquals(subjectToVAT, country.isSubjectToVAT());
		Assert.assertEquals(zipRequired, country.isZipRequired());

		_countryLocalService.deleteCountry(country);

		long countryId = country.getCountryId();

		Assert.assertNull(_countryLocalService.fetchCountry(countryId));

		Assert.assertTrue(
			ListUtil.isEmpty(_regionService.getRegions(countryId)));

		List<Organization> organizationList = _organizationLocalService.search(
			country.getCompanyId(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null, null, null,
			countryId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Organization organization : organizationList) {
			Assert.assertEquals(0, organization.getCountryId());
			Assert.assertEquals(0, organization.getRegionId());
		}
	}

	@Test
	public void testUpdateCountry() throws Exception {
		Country country = _addCountry(
			true, RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
			true, true, true);

		Country updatedCountry = _countryLocalService.updateCountry(
			country.getCountryId(), country.getA2(), country.getA3(),
			country.isActive(), false, country.getIdd(), country.getName(),
			"12345", 54321, false, false, null);

		Assert.assertEquals(false, updatedCountry.isBillingAllowed());
		Assert.assertEquals("12345", updatedCountry.getNumber());
		Assert.assertEquals(54321, updatedCountry.getPosition(), 0);
		Assert.assertEquals(false, updatedCountry.isShippingAllowed());
		Assert.assertEquals(false, updatedCountry.isSubjectToVAT());
	}

	private Country _addCountry(
			boolean billingAllowed, String number, double position,
			boolean shippingAllowed, boolean subjectToVAT, boolean zipRequired)
		throws Exception {

		return _countryLocalService.addCountry(
			"aa", "aaa", true, billingAllowed, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), number, position, shippingAllowed,
			subjectToVAT, zipRequired, null,
			ServiceContextTestUtil.getServiceContext());
	}

	@Inject
	private static CountryLocalService _countryLocalService;

	@Inject
	private static OrganizationLocalService _organizationLocalService;

	@Inject
	private static RegionService _regionService;

}