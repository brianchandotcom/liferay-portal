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

package com.liferay.user.groups.admin.web.internal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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
public class UserGroupMultiLanguageSearchTest {

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

		setUpUserGroupIndexerFixture();

		setUpUserGroupFixture();

		_defaultLocale = LocaleThreadLocal.getDefaultLocale();
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setDefaultLocale(_defaultLocale);
	}

	@Test
	public void testChineseValue() throws Exception {
		_testLocaleKeywords(LocaleUtil.CHINA, "你好");
	}

	@Test
	public void testEnglishValue() throws Exception {
		_testLocaleKeywords(
			LocaleUtil.US,
			StringUtil.toLowerCase(RandomTestUtil.randomString()));
	}

	@Test
	public void testJapaneseValue() throws Exception {
		_testLocaleKeywords(LocaleUtil.JAPAN, "東京");
	}

	protected void assertFieldValues(
		String prefix, Locale locale, Map<String, String> map,
		String searchTerm) {

		Document document = userGroupIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		FieldValuesAssert.assertFieldValues(map, prefix, document, searchTerm);
	}

	protected void setLocale(Locale locale) throws Exception {
		userGroupFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	protected void setUpUserGroupFixture() {
		userGroupFixture = new UserGroupFixture(_group);
	}

	protected void setUpUserGroupIndexerFixture() {
		userGroupIndexerFixture = new IndexerFixture<>(UserGroup.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();
	}

	protected UserGroupFixture userGroupFixture;
	protected IndexerFixture<UserGroup> userGroupIndexerFixture;
	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _getMapResult(String keywords) {
		return HashMapBuilder.<String, String>put(
			_NAME, keywords
		).put(
			_NAME + StringPool.UNDERLINE + Field.SORTABLE_FIELD_SUFFIX, keywords
		).build();
	}

	private void _testLocaleKeywords(Locale locale, String keywords)
		throws Exception {

		setLocale(locale);

		userGroupFixture.createUserGroup(keywords);

		Map<String, String> map = _getMapResult(keywords);

		assertFieldValues(_NAME, locale, map, keywords);
	}

	private static final String _NAME = "name";

	private Locale _defaultLocale;
	private Group _group;

}