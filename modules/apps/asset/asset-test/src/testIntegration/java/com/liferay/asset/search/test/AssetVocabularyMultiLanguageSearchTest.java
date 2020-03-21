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
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.GroupBlueprint;
import com.liferay.users.admin.test.util.search.GroupSearchFixture;

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
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		GroupSearchFixture groupSearchFixture = new GroupSearchFixture();

		Group group = groupSearchFixture.addGroup(new GroupBlueprint());

		AssetVocabularyFixture assetVocabularyFixture =
			new AssetVocabularyFixture(assetVocabularyService, group);

		_assetVocabularies = assetVocabularyFixture.getAssetVocabularies();
		_assetVocabularyFixture = assetVocabularyFixture;

		_defaultLocale = LocaleThreadLocal.getDefaultLocale();
		_group = group;
		_groups = groupSearchFixture.getGroups();
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
		String prefix, Locale locale, Map<String, String> map,
		String searchTerm) {

		FieldValuesAssert.assertFieldValues(
			map, name -> name.startsWith(prefix),
			searcher.search(
				searchRequestBuilderFactory.builder(
				).companyId(
					getCompanyId()
				).groupIds(
					_group.getGroupId()
				).locale(
					locale
				).fields(
					StringPool.STAR
				).modelIndexerClasses(
					AssetVocabulary.class
				).queryString(
					searchTerm
				).build()));
	}

	protected long getCompanyId() {
		try {
			return TestPropsValues.getCompanyId();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	protected void setTestLocale(Locale locale) throws Exception {
		_assetVocabularyFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	@Inject
	protected AssetVocabularyService assetVocabularyService;

	@Inject(
		filter = "indexer.class.name=com.liferay.asset.kernel.model.AssetVocabulary"
	)
	protected Indexer<AssetVocabulary> indexer;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	@Inject
	protected Searcher searcher;

	@Inject
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private void _addAssetVocabularyMultiLanguage() throws Exception {
		_assetVocabularyFixture.createAssetVocabulary(
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

	private AssetVocabularyFixture _assetVocabularyFixture;
	private Locale _defaultLocale;
	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

}