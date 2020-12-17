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
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
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
public class CountryLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddCountry() throws Exception {
		boolean billingAllowed = RandomTestUtil.randomBoolean();
		String number = RandomTestUtil.randomString();
		double position = RandomTestUtil.randomDouble();
		boolean shippingAllowed = RandomTestUtil.randomBoolean();
		boolean subjectToVAT = RandomTestUtil.randomBoolean();
		boolean zipRequired = RandomTestUtil.randomBoolean();

		Country country = _addCountry(
			billingAllowed, number, position, shippingAllowed, subjectToVAT,
			zipRequired);

		Assert.assertEquals(billingAllowed, country.isBillingAllowed());
		Assert.assertEquals(number, country.getNumber());
		Assert.assertEquals(position, country.getPosition(), 0);
		Assert.assertEquals(shippingAllowed, country.isShippingAllowed());
		Assert.assertEquals(subjectToVAT, country.isSubjectToVAT());
		Assert.assertEquals(zipRequired, country.isZipRequired());

		Assert.assertNotNull(
			_countryLocalService.fetchCountry(country.getCountryId()));
	}

	@Test
	public void testDeleteCountry() throws Exception {
		Country country = _addCountry(
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomString(),
			RandomTestUtil.randomDouble(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean());

		Organization organization = OrganizationTestUtil.addOrganization();

		organization.setCountryId(country.getCountryId());

		organization = _organizationLocalService.updateOrganization(
			organization);

		Assert.assertFalse(
			ListUtil.isEmpty(
				_organizationLocalService.search(
					country.getCompanyId(),
					OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null,
					null, null, country.getCountryId(), null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS)));

		_countryLocalService.deleteCountry(country);

		Assert.assertNull(
			_countryLocalService.fetchCountry(country.getCountryId()));

		organization = _organizationLocalService.getOrganization(
			organization.getOrganizationId());

		Assert.assertEquals(0, organization.getCountryId());

		Assert.assertTrue(
			ListUtil.isEmpty(
				_organizationLocalService.search(
					country.getCompanyId(),
					OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null,
					null, null, country.getCountryId(), null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS)));
	}

	@Test
	public void testUpdateCountry() throws Exception {
		boolean billingAllowed = RandomTestUtil.randomBoolean();
		boolean shippingAllowed = RandomTestUtil.randomBoolean();
		boolean subjectToVAT = RandomTestUtil.randomBoolean();

		Country country = _addCountry(
			billingAllowed, RandomTestUtil.randomString(),
			RandomTestUtil.randomDouble(), shippingAllowed, subjectToVAT, true);

		String number = String.valueOf(9999 + RandomTestUtil.nextInt());
		int position = RandomTestUtil.randomInt();

		Country updatedCountry = _countryLocalService.updateCountry(
			country.getCountryId(), country.getA2(), country.getA3(),
			country.isActive(), !billingAllowed, country.getIdd(),
			country.getName(), number, position, !shippingAllowed,
			!subjectToVAT);

		Assert.assertEquals(!billingAllowed, updatedCountry.isBillingAllowed());
		Assert.assertEquals(number, updatedCountry.getNumber());
		Assert.assertEquals(position, updatedCountry.getPosition(), 0);
		Assert.assertEquals(
			!shippingAllowed, updatedCountry.isShippingAllowed());
		Assert.assertEquals(!subjectToVAT, updatedCountry.isSubjectToVAT());
	}

	private Country _addCountry(
			boolean billingAllowed, String number, double position,
			boolean shippingAllowed, boolean subjectToVAT, boolean zipRequired)
		throws Exception {

		return _countryLocalService.addCountry(
			"aa", "aaa", true, billingAllowed, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), number, position, shippingAllowed,
			subjectToVAT, zipRequired,
			ServiceContextTestUtil.getServiceContext());
	}

	@Inject
	private static CountryLocalService _countryLocalService;

	@Inject
	private static OrganizationLocalService _organizationLocalService;

	@Inject
	private static RegionService _regionService;

}