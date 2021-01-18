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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Comparator;
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

		User user = TestPropsValues.getUser();

		// Address

		Address address = _addressLocalService.addAddress(
			null, user.getUserId(), null, user.getContactId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
			country.getCountryId(), RandomTestUtil.randomLong(), false, false,
			"1234567890", ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(country.getCountryId(), address.getCountryId());

		// Organization

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

		// Assert address is deleted

		Assert.assertNull(
			_addressLocalService.fetchAddress(address.getAddressId()));

		// Assert organization is updated

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
	public void testSearchCountries() throws Exception {
		String keywords = RandomTestUtil.randomString();

		Country country = _addCountry(
			"a1", "a11", true, RandomTestUtil.randomString());

		List<Country> expectedCountries = Arrays.asList(
			_addCountry("a2", "a22", true, keywords),
			_addCountry(
				"a3", "a33", true, keywords + RandomTestUtil.randomString()));

		_addCountry("a4", "a44", false, RandomTestUtil.randomString());

		BaseModelSearchResult<Country> baseModelSearchResult =
			_countryLocalService.searchCountries(
				country.getCompanyId(), true, keywords, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				OrderByComparatorFactoryUtil.create("Country", "name", true));

		Assert.assertEquals(
			expectedCountries.size(), baseModelSearchResult.getLength());

		Assert.assertEquals(
			expectedCountries, baseModelSearchResult.getBaseModels());

		String localizedCountryName = RandomTestUtil.randomString();

		_countryLocalService.updateCountryLocalization(
			country, "de_DE", localizedCountryName);

		baseModelSearchResult = _countryLocalService.searchCountries(
			country.getCompanyId(), true, localizedCountryName,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, baseModelSearchResult.getLength());

		List<Country> countries = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(country, countries.get(0));
	}

	@Test
	public void testSearchCountriesPagination() throws Exception {
		String keywords = RandomTestUtil.randomString();

		List<Country> expectedCountries = Arrays.asList(
			_addCountry(
				"a1", "a11", true, keywords + RandomTestUtil.randomString()),
			_addCountry(
				"a2", "a22", true, keywords + RandomTestUtil.randomString()),
			_addCountry(
				"a3", "a33", true, keywords + RandomTestUtil.randomString()),
			_addCountry(
				"a4", "a44", true, keywords + RandomTestUtil.randomString()),
			_addCountry(
				"a5", "a55", true, keywords + RandomTestUtil.randomString()));

		_addCountry(
			"a6", "a66", false, keywords + RandomTestUtil.randomString());

		Comparator<Country> comparator = Comparator.comparing(
			Country::getName, String.CASE_INSENSITIVE_ORDER);

		_assertSearchCountriesPaginationSort(
			ListUtil.sort(expectedCountries, comparator), keywords,
			OrderByComparatorFactoryUtil.create("Country", "name", true),
			ServiceContextTestUtil.getServiceContext());
		_assertSearchCountriesPaginationSort(
			ListUtil.sort(expectedCountries, comparator.reversed()), keywords,
			OrderByComparatorFactoryUtil.create("Country", "name", false),
			ServiceContextTestUtil.getServiceContext());
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

	private Country _addCountry(
			String a2, String a3, boolean active, String name)
		throws Exception {

		return _countryLocalService.addCountry(
			a2, a3, active, RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomString(), name, RandomTestUtil.randomString(),
			RandomTestUtil.randomDouble(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			ServiceContextTestUtil.getServiceContext());
	}

	private void _assertSearchCountriesPaginationSort(
			List<Country> expectedCountries, String keywords,
			OrderByComparator<Country> orderByComparator,
			ServiceContext serviceContext)
		throws Exception {

		int end = 3;
		int start = 1;

		BaseModelSearchResult<Country> baseModelSearchResult =
			_countryLocalService.searchCountries(
				serviceContext.getCompanyId(), true, keywords, start, end,
				orderByComparator);

		List<Country> actualCountries = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(
			actualCountries.toString(), end - start, actualCountries.size());

		for (int i = 0; i < (end - start); i++) {
			Assert.assertEquals(
				expectedCountries.get(start + i), actualCountries.get(i));
		}
	}

	@Inject
	private static AddressLocalService _addressLocalService;

	@Inject
	private static CountryLocalService _countryLocalService;

	@Inject
	private static OrganizationLocalService _organizationLocalService;

	@Inject
	private static RegionService _regionService;

}