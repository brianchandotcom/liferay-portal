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

package com.liferay.asset.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vagner B.Castro
 */
@RunWith(Arquillian.class)
@Sync
public class AssetVocabularyMultiLanguageSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpUserSearchFixture();

		setUpAssetVocabularyFixture();

		setUpAssetVocabularyIndexerFixture();

		_defaultLocale = LocaleThreadLocal.getDefaultLocale();
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setDefaultLocale(_defaultLocale);
	}

	@Test
	public void testEnglishDescription() throws Exception {
		setTestLocale(LocaleUtil.US);

		_addAssetVocabularyMultiLanguage();

		Map<String, String> descriptionMap = HashMapBuilder.put(
			"description", _ENGLISH_DESCRIPTION
		).put(
			"description_en_US", _ENGLISH_DESCRIPTION
		).put(
			"description_ja_JP", _JAPANESE_DESCRIPTION
		).build();

		String keyword = "description";

		assertFieldValues(
			"description", LocaleUtil.US, descriptionMap, keyword);
	}

	@Test
	public void testEnglishTitle() throws Exception {
		setTestLocale(LocaleUtil.US);

		_addAssetVocabularyMultiLanguage();

		Map<String, String> titleMap = HashMapBuilder.put(
			"title", _ENGLISH_TITLE
		).put(
			"title_en_US", _ENGLISH_TITLE
		).put(
			"title_ja_JP", _JAPANESE_TITLE
		).put(
			"title_sortable", _ENGLISH_TITLE
		).build();

		String word1 = "title";
		String word2 = "tit";

		Stream.of(
			word1, word2
		).forEach(
			keywords -> assertFieldValues(
				"title", LocaleUtil.US, titleMap, keywords)
		);
	}

	@Test
	public void testJapaneseDescription() throws Exception {
		setTestLocale(LocaleUtil.JAPAN);

		_addAssetVocabularyMultiLanguage();

		Map<String, String> descriptionMap = HashMapBuilder.put(
			"description", _JAPANESE_DESCRIPTION
		).put(
			"description_en_US", _ENGLISH_DESCRIPTION
		).put(
			"description_ja_JP", _JAPANESE_DESCRIPTION
		).build();

		String word1 = "新規";
		String word2 = "作成";
		String prefix1 = "新";
		String prefix2 = "作";

		Stream.of(
			word1, word2, prefix1, prefix2
		).forEach(
			keywords -> assertFieldValues(
				"description", LocaleUtil.JAPAN, descriptionMap, keywords)
		);
	}

	@Test
	public void testJapaneseTitle() throws Exception {
		setTestLocale(LocaleUtil.JAPAN);

		_addAssetVocabularyMultiLanguage();

		Map<String, String> titleMap = HashMapBuilder.put(
			"title", _JAPANESE_TITLE
		).put(
			"title_en_US", _ENGLISH_TITLE
		).put(
			"title_ja_JP", _JAPANESE_TITLE
		).put(
			"title_sortable", _JAPANESE_TITLE
		).build();

		String word1 = "新規";
		String word2 = "作成";
		String prefix1 = "新";
		String prefix2 = "作";

		Stream.of(
			word1, word2, prefix1, prefix2
		).forEach(
			keywords -> assertFieldValues(
				"title", LocaleUtil.JAPAN, titleMap, keywords)
		);
	}

	protected void assertFieldValues(
		String prefix, Locale locale, Map<String, String> titleStrings,
		String searchTerm) {

		Document document = assetVocabularyIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		FieldValuesAssert.assertFieldValues(
			titleStrings, prefix, document, searchTerm);
	}

	protected void setTestLocale(Locale locale) throws Exception {
		assetVocabularyFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	protected void setUpAssetVocabularyFixture() {
		assetVocabularyFixture = new AssetVocabularyFixture(_group);

		_assetVocabularies = assetVocabularyFixture.getAssetVocabularies();
	}

	protected void setUpAssetVocabularyIndexerFixture() {
		assetVocabularyIndexerFixture = new IndexerFixture<>(
			AssetVocabulary.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();
	}

	protected AssetVocabularyFixture assetVocabularyFixture;
	protected IndexerFixture<AssetVocabulary> assetVocabularyIndexerFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected UserSearchFixture userSearchFixture;

	private void _addAssetVocabularyMultiLanguage() throws Exception {
		assetVocabularyFixture.createAssetVocabulary(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, _ENGLISH_TITLE);
					put(LocaleUtil.JAPAN, _JAPANESE_TITLE);
				}
			},
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, _ENGLISH_DESCRIPTION);
					put(LocaleUtil.JAPAN, _JAPANESE_DESCRIPTION);
				}
			});
	}

	private static final String _ENGLISH_DESCRIPTION = "description";

	private static final String _ENGLISH_TITLE = "title";

	private static final String _JAPANESE_DESCRIPTION = "新規作成";

	private static final String _JAPANESE_TITLE = "新規作成";

	@DeleteAfterTestRun
	private List<AssetVocabulary> _assetVocabularies;

	private Locale _defaultLocale;
	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}