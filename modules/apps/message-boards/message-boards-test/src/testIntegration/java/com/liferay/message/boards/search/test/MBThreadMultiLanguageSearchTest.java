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

package com.liferay.message.boards.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
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
public class MBThreadMultiLanguageSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpMBThreadIndexerFixture();
		_defaultLocale = LocaleThreadLocal.getDefaultLocale();
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setDefaultLocale(_defaultLocale);
	}

	@Test
	public void testChineseSubject() throws Exception {
		_testLocaleKeywords(LocaleUtil.CHINA, "你好");
	}

	@Test
	public void testEnglishSubject() throws Exception {
		_testLocaleKeywords(LocaleUtil.US, "firstName");
	}

	@Test
	public void testJapaneseSubject() throws Exception {
		_testLocaleKeywords(LocaleUtil.JAPAN, "東京");
	}

	protected void assertFieldValues(
		String prefix, Locale locale, Map<String, String> map,
		String searchTerm) {

		Document document = mbThreadIndexerFixture.searchOnlyOne(
			_user.getUserId(), searchTerm, locale);

		FieldValuesAssert.assertFieldValues(map, prefix, document, searchTerm);
	}

	protected void setTestLocale(Locale locale) throws Exception {
		mbFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	protected void setUpMBFixture() {
		mbFixture = new MBFixture(_group, _user);

		_mbThreads = mbFixture.getMbThreads();

		_mbCategories = mbFixture.getMbCategories();

		_mbMessages = mbFixture.getMbMessages();
	}

	protected void setUpMBThreadIndexerFixture() {
		mbThreadIndexerFixture = new IndexerFixture<>(MBThread.class);
	}

	protected void setUpUserSearchFixture(
			String firstName, String lastName, Locale locale)
		throws Exception {

		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_user = userSearchFixture.addUser(
			RandomTestUtil.randomString(), firstName, lastName, locale, _group);

		_users = userSearchFixture.getUsers();
	}

	protected MBFixture mbFixture;
	protected IndexerFixture<MBThread> mbThreadIndexerFixture;
	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _getResultMap(MBThread mbThread) {
		return HashMapBuilder.<String, String>put(
			Field.ENTRY_CLASS_PK, String.valueOf(mbThread.getThreadId())
		).build();
	}

	private void _testLocaleKeywords(Locale locale, String keywords)
		throws Exception {

		setUpUserSearchFixture(keywords, _LAST_NAME, locale);

		setUpMBFixture();

		setTestLocale(locale);

		MBMessage mbMessage = mbFixture.createMBMessageWithCategory(
			RandomTestUtil.randomString());

		MBThread mbThread = mbMessage.getThread();

		assertFieldValues(
			Field.ENTRY_CLASS_PK, locale, _getResultMap(mbThread), keywords);
	}

	private static final String _LAST_NAME = "lastName";

	private Locale _defaultLocale;
	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<MBCategory> _mbCategories;

	@DeleteAfterTestRun
	private List<MBMessage> _mbMessages;

	@DeleteAfterTestRun
	private List<MBThread> _mbThreads;

	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

}