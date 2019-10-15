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

package com.liferay.polls.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
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
public class PollsQuestionMultiLanguageSearchTest {

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

		setUpPollsQuestionFixture();

		setUpPollsQuestionIndexerFixture();

		_defaultLocale = LocaleThreadLocal.getDefaultLocale();
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setDefaultLocale(_defaultLocale);
	}

	@Test
	public void testEnglishDescription() throws Exception {
		_testLocaleKeyword(LocaleUtil.US, _DESCRIPTION, _KEYWORD_US);
	}

	@Test
	public void testEnglishTitle() throws Exception {
		_testLocaleKeyword(LocaleUtil.US, _TITLE, _KEYWORD_US);
	}

	@Test
	public void testJapaneseDescription() throws Exception {
		_testLocaleKeyword(LocaleUtil.JAPAN, _DESCRIPTION, _KEYWORD_JP);
	}

	@Test
	public void testJapaneseTitle() throws Exception {
		_testLocaleKeyword(LocaleUtil.JAPAN, _TITLE, _KEYWORD_JP);
	}

	protected void assertFieldValues(
		String prefix, Locale locale, Map<String, String> map,
		String searchTerm) {

		Document document = pollsQuestionIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		FieldValuesAssert.assertFieldValues(map, prefix, document, searchTerm);
	}

	protected void setTestLocale(Locale locale) throws Exception {
		pollsQuestionFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	protected void setUpPollsQuestionFixture() {
		pollsQuestionFixture = new PollsQuestionFixture(_group);

		_pollsQuestions = pollsQuestionFixture.getPollsQuestions();
	}

	protected void setUpPollsQuestionIndexerFixture() {
		pollsQuestionIndexerFixture = new IndexerFixture<>(PollsQuestion.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();
	}

	protected PollsQuestionFixture pollsQuestionFixture;
	protected IndexerFixture<PollsQuestion> pollsQuestionIndexerFixture;
	protected UserSearchFixture userSearchFixture;

	private void _createPollsQuestionMultiLanguage() throws Exception {
		pollsQuestionFixture.createPollsQuestion(
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.JAPAN, _KEYWORD_JP);
					put(LocaleUtil.US, _KEYWORD_US);
				}
			},
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.JAPAN, _KEYWORD_JP);
					put(LocaleUtil.US, _KEYWORD_US);
				}
			});
	}

	private Map<String, String> _getMapResult(String prefix) {
		return new HashMap<String, String>() {
			{
				put(prefix, _KEYWORD_US + StringPool.SPACE + _KEYWORD_JP);

				if (prefix == _TITLE) {
					put(
						prefix + "_sortable",
						_KEYWORD_US + StringPool.SPACE + _KEYWORD_JP);
				}
			}
		};
	}

	private void _testLocaleKeyword(
			Locale locale, String prefix, String keyword)
		throws Exception {

		setTestLocale(locale);

		_createPollsQuestionMultiLanguage();

		assertFieldValues(prefix, locale, _getMapResult(prefix), keyword);
	}

	private static final String _DESCRIPTION = "description";

	private static final String _KEYWORD_JP = "東京";

	private static final String _KEYWORD_US = "new item";

	private static final String _TITLE = "title";

	private Locale _defaultLocale;
	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<PollsQuestion> _pollsQuestions;

	@DeleteAfterTestRun
	private List<User> _users;

}