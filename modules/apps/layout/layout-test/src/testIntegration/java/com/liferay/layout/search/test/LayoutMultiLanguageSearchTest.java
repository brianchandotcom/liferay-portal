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

package com.liferay.layout.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

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
public class LayoutMultiLanguageSearchTest {

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

		setUpLayoutFixture();

		setUpLayoutIndexerFixture();

		defaultLocale = LocaleThreadLocal.getDefaultLocale();
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setDefaultLocale(defaultLocale);
	}

	@Test
	public void testEnglishName() throws Exception {
		_testLocaleKeywords(LocaleUtil.US, _ENGLISH_KEYWORD, _NAME);
	}

	@Test
	public void testEnglishTitle() throws Exception {
		_testLocaleKeywords(LocaleUtil.US, _ENGLISH_KEYWORD, _TITLE);
	}

	@Test
	public void testJapaneseName() throws Exception {
		_testLocaleKeywords(LocaleUtil.JAPAN, _JAPANESE_KEYWORD, _NAME);
	}

	@Test
	public void testJapaneseTitle() throws Exception {
		_testLocaleKeywords(LocaleUtil.JAPAN, _JAPANESE_KEYWORD, _TITLE);
	}

	protected void assertFieldValues(
		String prefix, Locale locale, Map<String, String> titleStrings,
		String searchTerm) {

		Document document = layoutIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		FieldValuesAssert.assertFieldValues(
			titleStrings, prefix, document, searchTerm);
	}

	protected void setTestLocale(Locale locale) throws Exception {
		layoutFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	protected void setUpLayoutFixture() {
		layoutFixture = new LayoutFixture(_group);

		_layouts = layoutFixture.getLayouts();
	}

	protected void setUpLayoutIndexerFixture() {
		layoutIndexerFixture = new IndexerFixture<>(Layout.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();
	}

	protected Locale defaultLocale;
	protected LayoutFixture layoutFixture;
	protected IndexerFixture<Layout> layoutIndexerFixture;
	protected UserSearchFixture userSearchFixture;

	private void _addLayoutMultiLanguage() throws PortalException {
		layoutFixture.createLayout(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, _ENGLISH_KEYWORD);
					put(LocaleUtil.JAPAN, _JAPANESE_KEYWORD);
				}
			},
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, _ENGLISH_KEYWORD);
					put(LocaleUtil.JAPAN, _JAPANESE_KEYWORD);
				}
			});
	}

	private Map<String, String> _getMapResult(String prefix, String keyWords) {
		return new HashMap<String, String>() {
			{
				if (prefix != _TITLE) {
					put(prefix, keyWords);
				}

				put(prefix + "_en_US", _ENGLISH_KEYWORD);
				put(prefix + "_ja_JP", _JAPANESE_KEYWORD);
			}
		};
	}

	private void _testLocaleKeywords(
			Locale locale, String keyWords, String prefix)
		throws Exception {

		setTestLocale(locale);

		_addLayoutMultiLanguage();

		Map<String, String> map = _getMapResult(prefix, keyWords);

		assertFieldValues(prefix, locale, map, keyWords);
	}

	private static final String _ENGLISH_KEYWORD = "new item";

	private static final String _JAPANESE_KEYWORD = "新規作成";

	private static final String _NAME = "name";

	private static final String _TITLE = "title";

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<Layout> _layouts;

	@DeleteAfterTestRun
	private List<User> _users;

}