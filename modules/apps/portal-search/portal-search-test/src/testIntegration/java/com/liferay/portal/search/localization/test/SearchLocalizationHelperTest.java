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

package com.liferay.portal.search.localization.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class SearchLocalizationHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_companyId = CompanyThreadLocal.getCompanyId();

		_groups = new ArrayList<>();
	}

	@After
	public void tearDown() {
		CompanyThreadLocal.setCompanyId(_companyId);
	}

	@Test
	public void testAddLocalizedField() {
		Map<Locale, String> map = HashMapBuilder.<Locale, String>put(
			LocaleUtil.BRAZIL, "exemplo"
		).put(
			LocaleUtil.SPAIN, "ejemplo"
		).build();
		Document document = new DocumentImpl();

		searchLocalizationHelper.addLocalizedField(
			document, "test", LocaleUtil.BRAZIL, map);

		Assert.assertEquals("exemplo", document.get("test"));
		Assert.assertEquals(
			"exemplo",
			document.get(Field.getLocalizedName(LocaleUtil.BRAZIL, "test")));
		Assert.assertEquals(
			"ejemplo",
			document.get(Field.getLocalizedName(LocaleUtil.SPAIN, "test")));
	}

	@Test
	public void testGetLocalesFromCompany() throws Exception {
		SearchContext searchContext = getSearchContext(
			addCompany(LocaleUtil.BRAZIL, LocaleUtil.JAPAN));

		Locale[] locales = searchLocalizationHelper.getLocales(searchContext);

		assertSameValues(locales, LocaleUtil.BRAZIL, LocaleUtil.JAPAN);
	}

	@Test
	public void testGetLocalesFromGroups() throws Exception {
		Company company = addCompany(
			LocaleUtil.BRAZIL, LocaleUtil.JAPAN, LocaleUtil.GERMANY,
			LocaleUtil.SPAIN);

		SearchContext searchContext = getSearchContext(
			company, addGroup(company, LocaleUtil.GERMANY),
			addGroup(company, LocaleUtil.SPAIN));

		Locale[] locales = searchLocalizationHelper.getLocales(searchContext);

		assertSameValues(locales, LocaleUtil.GERMANY, LocaleUtil.SPAIN);
	}

	@Test
	public void testGetLocalizedFieldNamesFromCompany() throws Exception {
		SearchContext searchContext = getSearchContext(
			addCompany(LocaleUtil.BRAZIL, LocaleUtil.JAPAN));

		String[] locales = searchLocalizationHelper.getLocalizedFieldNames(
			new String[] {"test", "example"}, searchContext);

		assertSameValues(
			locales, "test_pt_BR", "test_ja_JP", "example_pt_BR",
			"example_ja_JP");
	}

	@Test
	public void testGetLocalizedFieldNamesFromGroups() throws Exception {
		Company company = addCompany(
			LocaleUtil.BRAZIL, LocaleUtil.JAPAN, LocaleUtil.GERMANY,
			LocaleUtil.SPAIN);

		SearchContext searchContext = getSearchContext(
			company, addGroup(company, LocaleUtil.GERMANY),
			addGroup(company, LocaleUtil.SPAIN));

		String[] locales = searchLocalizationHelper.getLocalizedFieldNames(
			new String[] {"test", "example"}, searchContext);

		assertSameValues(
			locales, "test_de_DE", "test_es_ES", "example_de_DE",
			"example_es_ES");
	}

	protected Company addCompany(Locale... locales) throws Exception {
		Company company = CompanyTestUtil.addCompany();

		CompanyTestUtil.resetCompanyLocales(
			company.getCompanyId(), ListUtil.fromArray(locales), locales[0]);

		_company = company;

		return company;
	}

	protected Group addGroup(Company company, Locale... locales)
		throws Exception, PortalException {

		Group group = GroupTestUtil.addGroup(
			company.getCompanyId(), TestPropsValues.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), ListUtil.fromArray(locales), locales[0]);

		_groups.add(group);

		return group;
	}

	protected void assertSameValues(Object[] values, Object... expectedValues) {
		Assert.assertEquals(
			values.toString(), expectedValues.length, values.length);

		for (Object expectedValue : expectedValues) {
			Assert.assertTrue(ArrayUtil.contains(values, expectedValue));
		}
	}

	protected SearchContext getSearchContext(Company company, Group... groups)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setCompanyId(company.getCompanyId());

		Stream<Group> stream = Arrays.stream(groups);

		long[] groupIds = stream.mapToLong(
			Group::getGroupId
		).toArray();

		searchContext.setGroupIds(groupIds);

		return searchContext;
	}

	@Inject
	protected SearchLocalizationHelper searchLocalizationHelper;

	@DeleteAfterTestRun
	private Company _company;

	private Long _companyId;

	@DeleteAfterTestRun
	private List<Group> _groups;

}