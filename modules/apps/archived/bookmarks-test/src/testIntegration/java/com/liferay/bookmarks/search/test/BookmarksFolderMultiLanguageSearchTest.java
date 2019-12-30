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

package com.liferay.bookmarks.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
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
public class BookmarksFolderMultiLanguageSearchTest {

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

		setUpBookmarksFolderFixture();

		setUpBookmarksFolderIndexerFixture();

		_defaultLocale = LocaleThreadLocal.getDefaultLocale();
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setDefaultLocale(_defaultLocale);
	}

	@Test
	public void testChineseTitle() throws Exception {
		Locale locale = LocaleUtil.CHINA;

		setTestLocale(locale);

		String keyWords = "你好";

		bookmarksFixture.createBookmarksFolder(keyWords);

		Map<String, String> map = HashMapBuilder.put(
			_PREFIX, keyWords
		).put(
			_PREFIX + "_sortable", keyWords
		).build();

		assertFieldValues(_PREFIX, locale, map, keyWords);
	}

	@Test
	public void testEnglishTitle() throws Exception {
		Locale locale = LocaleUtil.US;

		setTestLocale(locale);

		String keyWords = StringUtil.toLowerCase(RandomTestUtil.randomString());

		bookmarksFixture.createBookmarksFolder(keyWords);

		Map<String, String> map = HashMapBuilder.put(
			_PREFIX, keyWords
		).put(
			_PREFIX + "_sortable", keyWords
		).build();

		assertFieldValues(_PREFIX, locale, map, keyWords);
	}

	@Test
	public void testJapaneseTitle() throws Exception {
		Locale locale = LocaleUtil.JAPAN;

		setTestLocale(locale);

		String keyWords = "東京";

		bookmarksFixture.createBookmarksFolder(keyWords);

		Map<String, String> map = HashMapBuilder.put(
			_PREFIX, keyWords
		).put(
			_PREFIX + "_sortable", keyWords
		).build();

		assertFieldValues(_PREFIX, locale, map, keyWords);
	}

	protected void assertFieldValues(
		String prefix, Locale locale, Map<String, String> map,
		String searchTerm) {

		Document document = bookmarksFolderIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		FieldValuesAssert.assertFieldValues(map, prefix, document, searchTerm);
	}

	protected void setTestLocale(Locale locale) throws Exception {
		bookmarksFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	protected void setUpBookmarksFolderFixture() {
		bookmarksFixture = new BookmarksFixture(_group, _user);

		_bookmarksFolders = bookmarksFixture.getBookmarksFolders();
	}

	protected void setUpBookmarksFolderIndexerFixture() {
		bookmarksFolderIndexerFixture = new IndexerFixture<>(
			BookmarksFolder.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_user = userSearchFixture.addUser(
			RandomTestUtil.randomString(), _group);

		_users = userSearchFixture.getUsers();
	}

	protected BookmarksFixture bookmarksFixture;
	protected IndexerFixture<BookmarksFolder> bookmarksFolderIndexerFixture;
	protected UserSearchFixture userSearchFixture;

	private static final String _PREFIX = "title";

	@DeleteAfterTestRun
	private List<BookmarksFolder> _bookmarksFolders;

	private Locale _defaultLocale;
	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

}