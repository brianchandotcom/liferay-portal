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

package com.liferay.organizations.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vagner B.C
 */
@RunWith(Arquillian.class)
public class OrganizationMultiLanguageSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpLocale();
		setUpOrganizationIndexerFixture();
		setUpUserSearchFixture();

		setUpOrganizationFixture();
	}

	@After
	public void tearDown() {
		tearDownLocale();
	}

	@Test
	public void testChineseName() throws Exception {
		Locale locale = LocaleUtil.CHINA;

		setLocale(locale);

		String keywords = "你好";

		organizationFixture.createOrganization(keywords);

		assertFieldValues(_PREFIX, locale, _getMapResult(keywords), keywords);
	}

	@Test
	public void testEnglishName() throws Exception {
		Locale locale = LocaleUtil.US;

		setLocale(locale);

		String keywords = "organization";

		organizationFixture.createOrganization(keywords);

		assertFieldValues(_PREFIX, locale, _getMapResult(keywords), keywords);
	}

	@Test
	public void testJapaneseName() throws Exception {
		Locale locale = LocaleUtil.JAPAN;

		setLocale(locale);

		String keywords = "東京";

		organizationFixture.createOrganization(keywords);

		assertFieldValues(_PREFIX, locale, _getMapResult(keywords), keywords);
	}

	protected void assertFieldValues(
		String prefix, Locale locale, Map<String, String> map,
		String searchTerm) {

		Document document = organizationIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		FieldValuesAssert.assertFieldValues(map, prefix, document, searchTerm);
	}

	protected void setLocale(Locale locale) throws Exception {
		organizationFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	protected void setUpLocale() {
		_defaultLocale = LocaleThreadLocal.getDefaultLocale();
	}

	protected void setUpOrganizationFixture() throws Exception {
		organizationFixture = new OrganizationFixture(
			organizationService, countryService, regionService);

		organizationFixture.setUp();

		organizationFixture.setGroup(group);

		_organizations = organizationFixture.getOrganizations();
	}

	protected void setUpOrganizationIndexerFixture() {
		organizationIndexerFixture = new IndexerFixture<>(Organization.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_groups = userSearchFixture.getGroups();
		_users = userSearchFixture.getUsers();

		group = userSearchFixture.addGroup();
	}

	protected void tearDownLocale() {
		LocaleThreadLocal.setDefaultLocale(_defaultLocale);
	}

	@Inject
	protected CountryService countryService;

	protected Group group;
	protected OrganizationFixture organizationFixture;
	protected IndexerFixture<Organization> organizationIndexerFixture;

	@Inject
	protected OrganizationService organizationService;

	@Inject
	protected RegionService regionService;

	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _getMapResult(String keywords) {
		return HashMapBuilder.put(
			"name", keywords
		).put(
			"name_sortable", keywords
		).put(
			"nameTreePath", keywords
		).put(
			"nameTreePath_String_sortable", keywords
		).build();
	}

	private static final String _PREFIX = "name";

	private Locale _defaultLocale;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<Organization> _organizations;

	@DeleteAfterTestRun
	private List<User> _users;

}