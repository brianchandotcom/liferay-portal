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

package com.liferay.journal.friendlyurl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ricardo Couso
 */
@RunWith(Arquillian.class)
public class JournalArticleFriendlyUrlTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUniqueFriendlyURLAfterUpdate() throws Exception {
		String title1 = RandomTestUtil.randomString();
		Locale[] locales = {LocaleUtil.FRANCE, LocaleUtil.US};

		Map<Locale, String> titleMap1 = _getLocalizedMap(title1, locales);

		JournalArticle article = _addJournalArticleWithTitleMap(titleMap1);

		String title2 = RandomTestUtil.randomString();

		Map<Locale, String> titleMap2 = _getLocalizedMap(title2, locales);

		_addJournalArticleWithTitleMap(titleMap2);

		JournalArticle updatedArticle = _updateJournalArticleWithTitleMap(
			article, titleMap2);

		Map<Locale, String> friendlyURLMap = updatedArticle.getFriendlyURLMap();

		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalizeWithEncoding(title2 + "-2"),
			friendlyURLMap.get(LocaleUtil.US));

		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalizeWithEncoding(title2 + "-2-1"),
			friendlyURLMap.get(LocaleUtil.FRANCE));
	}

	@Test
	public void testUniqueFriendlyURLForDifferentTitles() throws Exception {
		String frTitle = RandomTestUtil.randomString();
		String usTitle = RandomTestUtil.randomString();

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.FRANCE, frTitle
		).put(
			LocaleUtil.US, usTitle
		).build();

		JournalArticle article = _addJournalArticleWithTitleMap(titleMap);

		Map<Locale, String> friendlyURLMap = article.getFriendlyURLMap();

		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalizeWithEncoding(usTitle),
			friendlyURLMap.get(LocaleUtil.US));

		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalizeWithEncoding(frTitle),
			friendlyURLMap.get(LocaleUtil.FRANCE));
	}

	@Test
	public void testUniqueFriendlyURLForExistingArticle() throws Exception {
		String title = RandomTestUtil.randomString();

		Map<Locale, String> titleMap = _getLocalizedMap(
			title, new Locale[] {LocaleUtil.US});

		_addJournalArticleWithTitleMap(titleMap);

		JournalArticle article = _addJournalArticleWithTitleMap(titleMap);

		Map<Locale, String> friendlyURLMap = article.getFriendlyURLMap();

		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalizeWithEncoding(title + "-1"),
			friendlyURLMap.get(LocaleUtil.US));
	}

	@Test
	public void testUniqueFriendlyURLForSameTitles() throws Exception {
		String title = RandomTestUtil.randomString();
		Locale[] locales = {LocaleUtil.FRANCE, LocaleUtil.US};

		Map<Locale, String> titleMap = _getLocalizedMap(title, locales);

		JournalArticle article = _addJournalArticleWithTitleMap(titleMap);

		Map<Locale, String> friendlyURLMap = article.getFriendlyURLMap();

		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalizeWithEncoding(title),
			friendlyURLMap.get(LocaleUtil.US));

		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalizeWithEncoding(title + "-1"),
			friendlyURLMap.get(LocaleUtil.FRANCE));
	}

	@Test
	public void testUniqueFriendlyURLForSameTitlesWithExistingArticle()
		throws Exception {

		Locale[] locales = {LocaleUtil.FRANCE, LocaleUtil.US};
		String title = RandomTestUtil.randomString();

		Map<Locale, String> titleMap = _getLocalizedMap(title, locales);

		_addJournalArticleWithTitleMap(titleMap);

		JournalArticle article = _addJournalArticleWithTitleMap(titleMap);

		Map<Locale, String> friendlyURLMap = article.getFriendlyURLMap();

		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalizeWithEncoding(title + "-2"),
			friendlyURLMap.get(LocaleUtil.US));

		Assert.assertEquals(
			FriendlyURLNormalizerUtil.normalizeWithEncoding(title + "-2-1"),
			friendlyURLMap.get(LocaleUtil.FRANCE));
	}

	private JournalArticle _addJournalArticleWithTitleMap(
			Map<Locale, String> titleMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, titleMap, titleMap,
			titleMap, LocaleUtil.US, false, true, serviceContext);
	}

	private Map<Locale, String> _getLocalizedMap(
		String value, Locale[] locales) {

		Map<Locale, String> valuesMap = new HashMap<>();

		for (Locale locale : locales) {
			valuesMap.put(locale, value);
		}

		return valuesMap;
	}

	private JournalArticle _updateJournalArticleWithTitleMap(
			JournalArticle article, Map<Locale, String> titleMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return JournalTestUtil.updateArticle(
			article, titleMap, article.getContent(), false, true,
			serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

}