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
import com.liferay.headless.admin.address.client.dto.v1_0.Country;
import com.liferay.headless.admin.address.client.pagination.Page;
import com.liferay.headless.admin.address.client.pagination.Pagination;
import com.liferay.headless.admin.address.client.serdes.v1_0.CountrySerDes;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.RandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class CountryResourceTest extends BaseCountryResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		for (com.liferay.portal.kernel.model.Country country :
				_countryLocalService.getCompanyCountries(
					TestPropsValues.getCompanyId())) {

			_updateExistingCountryInfo(country.getA2(), country.getA3());
		}
	}

	@Override
	@Test
	public void testGetCountriesPage() throws Exception {
		Page<Country> page = countryResource.getCountriesPage(
			null, null, Pagination.of(1, _getPageSize()), null);

		long totalCount = page.getTotalCount();

		Country country1 = testGetCountriesPage_addCountry(randomCountry());

		Country country2 = testGetCountriesPage_addCountry(randomCountry());

		page = countryResource.getCountriesPage(
			null, null, Pagination.of(1, _getPageSize()), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(country1, (List<Country>)page.getItems());
		assertContains(country2, (List<Country>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testGraphQLGetCountriesPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"countries",
			HashMapBuilder.<String, Object>put(
				"page", 1
			).put(
				"pageSize", _getPageSize()
			).build(),
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject countriesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/countries");

		long totalCount = countriesJSONObject.getLong("totalCount");

		Country country1 = testGraphQLGetCountriesPage_addCountry();
		Country country2 = testGraphQLGetCountriesPage_addCountry();

		countriesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/countries");

		Assert.assertEquals(
			totalCount + 2, countriesJSONObject.getLong("totalCount"));

		assertContains(
			country1,
			Arrays.asList(
				CountrySerDes.toDTOs(countriesJSONObject.getString("items"))));
		assertContains(
			country2,
			Arrays.asList(
				CountrySerDes.toDTOs(countriesJSONObject.getString("items"))));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"a2", "a3", "name", "number"};
	}

	@Override
	protected Country randomCountry() throws Exception {
		Country country = super.randomCountry();

		country.setA2(
			RandomTestUtil.randomString(2, _getRandomizerBumper(_countryA2s)));
		country.setA3(
			RandomTestUtil.randomString(3, _getRandomizerBumper(_countryA3s)));
		country.setNumber(RandomTestUtil.nextInt());

		return country;
	}

	@Override
	protected Country testGetCountriesPage_addCountry(Country country)
		throws Exception {

		return _addCountry(country);
	}

	@Override
	protected Country testGraphQLCountry_addCountry() throws Exception {
		return _addCountry(randomCountry());
	}

	@Override
	protected Country testGraphQLGetCountriesPage_addCountry()
		throws Exception {

		return _addCountry(randomCountry());
	}

	private Country _addCountry(Country country) throws Exception {
		com.liferay.portal.kernel.model.Country serviceBuilderCountry =
			_countryLocalService.addCountry(
				country.getA2(), country.getA3(), country.getActive(),
				country.getBillingAllowed(), String.valueOf(country.getIdd()),
				country.getName(), String.valueOf(country.getNumber()),
				country.getPosition(), country.getShippingAllowed(),
				country.getSubjectToVAT(), country.getZipRequired(),
				ServiceContextTestUtil.getServiceContext());

		_updateExistingCountryInfo(
			serviceBuilderCountry.getA2(), serviceBuilderCountry.getA3());

		com.liferay.headless.admin.address.dto.v1_0.Country apiCountry =
			_countryResourceDTOConverter.toDTO(serviceBuilderCountry);

		return Country.toDTO(String.valueOf(apiCountry));
	}

	private int _getPageSize() {
		return _countryA2s.size() + 10;
	}

	private RandomizerBumper<String> _getRandomizerBumper(
		List<String> existingValues) {

		return randomValue ->
			StringUtil.isLowerCase(randomValue) &&
			!existingValues.contains(randomValue);
	}

	private void _updateExistingCountryInfo(String a2, String a3) {
		_countryA2s.add(StringUtil.toLowerCase(a2));
		_countryA3s.add(StringUtil.toLowerCase(a3));
	}

	private final List<String> _countryA2s = new ArrayList<>();
	private final List<String> _countryA3s = new ArrayList<>();

	@Inject
	private CountryLocalService _countryLocalService;

	@Inject(filter = "dto.class.name=com.liferay.portal.kernel.model.Country")
	private DTOConverter
		<com.liferay.portal.kernel.model.Country,
		 com.liferay.headless.admin.address.dto.v1_0.Country>
			_countryResourceDTOConverter;

}