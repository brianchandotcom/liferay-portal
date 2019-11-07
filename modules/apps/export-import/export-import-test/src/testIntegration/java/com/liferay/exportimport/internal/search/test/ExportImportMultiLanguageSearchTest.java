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

package com.liferay.exportimport.internal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.io.Serializable;

import java.util.HashMap;
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
public class ExportImportMultiLanguageSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpUserSearchFixture();

		setUpExportImportFixture();

		setUpExportImportIndexerFixture();

		_defaultLocale = LocaleThreadLocal.getDefaultLocale();
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setDefaultLocale(_defaultLocale);
	}

	@Test
	public void testChineseName() throws Exception {
		_testKeywordsLocale(LocaleUtil.CHINA, "你好");
	}

	@Test
	public void testEnglishName() throws Exception {
		_testKeywordsLocale(
			LocaleUtil.US,
			StringUtil.toLowerCase(RandomTestUtil.randomString()));
	}

	@Test
	public void testJapaneseName() throws Exception {
		_testKeywordsLocale(LocaleUtil.JAPAN, "東京");
	}

	protected void assertFieldValues(
		String prefix, Locale locale, Map<String, String> map,
		String searchTerm) {

		Document document = exportImportIndexerFixture.searchOnlyOne(
			searchTerm, locale, _addGroupAttribute());

		FieldValuesAssert.assertFieldValues(map, prefix, document, searchTerm);
	}

	protected void setTestLocale(Locale locale) throws Exception {
		exportImportFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	protected void setUpExportImportFixture() {
		exportImportFixture = new ExportImportFixture(_group);

		_exportImportConfigurations =
			exportImportFixture.getExportImportConfigurations();
	}

	protected void setUpExportImportIndexerFixture() {
		exportImportIndexerFixture = new IndexerFixture<>(
			ExportImportConfiguration.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();
	}

	protected ExportImportFixture exportImportFixture;
	protected IndexerFixture<ExportImportConfiguration>
		exportImportIndexerFixture;
	protected UserSearchFixture userSearchFixture;

	private HashMap<String, Serializable> _addGroupAttribute() {
		return HashMapBuilder.<String, Serializable>put(
			Field.GROUP_ID, _group.getGroupId()
		).build();
	}

	private Map<String, String> _getMapResult(String keywords) {
		return HashMapBuilder.<String, String>put(
			_PREFIX, keywords
		).put(
			Field.getSortableFieldName(_PREFIX), keywords
		).build();
	}

	private void _testKeywordsLocale(Locale locale, String keywords)
		throws Exception {

		setTestLocale(locale);

		exportImportFixture.createExportImport(keywords);

		assertFieldValues(_PREFIX, locale, _getMapResult(keywords), keywords);
	}

	private static final String _PREFIX = Field.NAME;

	private Locale _defaultLocale;

	@DeleteAfterTestRun
	private List<ExportImportConfiguration> _exportImportConfigurations;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}