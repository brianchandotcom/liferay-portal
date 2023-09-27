/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.search.JournalArticleBlueprint;
import com.liferay.journal.test.util.search.JournalArticleContent;
import com.liferay.journal.test.util.search.JournalArticleSearchFixture;
import com.liferay.journal.test.util.search.JournalArticleTitle;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.SearchContextTestUtil;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.GroupBlueprint;
import com.liferay.users.admin.test.util.search.GroupSearchFixture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
@Sync
public class AssetCategoryVocabularyVisibilitySearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		WorkflowThreadLocal.setEnabled(false);

		_groupSearchFixture = new GroupSearchFixture();

		_group = _groupSearchFixture.addGroup(new GroupBlueprint());

		_journalArticleSearchFixture = new JournalArticleSearchFixture(
			_ddmStructureLocalService, _journalArticleLocalService, _portal);
	}

	@Test
	public void testVisibitityTypeInternal() throws Exception {
		AssetCategory assetCategory = _addAssetCategory(
			AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL);

		String keyword = "example";

		_addJournalArticle(assetCategory, keyword);

		_assertSearchInternalFields(
			keyword, _getAssetCategoryIds(assetCategory),
			_getAssetCategoryTitles(assetCategory));
		_assertSearchPublicFields(
			keyword, Collections.emptyList(), Collections.emptyList());
	}

	@Test
	public void testVisibitityTypePublic() throws Exception {
		AssetCategory assetCategory = _addAssetCategory(
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC);

		String keyword = "example";

		_addJournalArticle(assetCategory, keyword);

		_assertSearchInternalFields(
			keyword, Collections.emptyList(), Collections.emptyList());
		_assertSearchPublicFields(
			keyword, _getAssetCategoryIds(assetCategory),
			_getAssetCategoryTitles(assetCategory));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private AssetCategory _addAssetCategory(int visibilityType)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		Locale previousLocale = LocaleThreadLocal.getSiteDefaultLocale();

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.US);

		try {
			AssetVocabulary assetVocabulary = _addAssetVocabulary(
				visibilityType);

			AssetCategory assetCategory = _assetCategoryService.addCategory(
				_group.getGroupId(),
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, titleMap,
				new HashMap<>(), assetVocabulary.getVocabularyId(),
				new String[0], serviceContext);

			_assetCategories.add(assetCategory);

			return assetCategory;
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(previousLocale);
		}
	}

	private AssetVocabulary _addAssetVocabulary(int visibilityType)
		throws Exception {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addDefaultVocabulary(
				_group.getGroupId());

		assetVocabulary.setTitle(RandomTestUtil.randomString());
		assetVocabulary.setVisibilityType(visibilityType);

		assetVocabulary = _assetVocabularyLocalService.updateAssetVocabulary(
			assetVocabulary);

		_assetVocabularies.add(assetVocabulary);

		return assetVocabulary;
	}

	private void _addJournalArticle(AssetCategory assetCategory, String title) {
		_journalArticleSearchFixture.addArticle(
			new JournalArticleBlueprint() {
				{
					setAssetCategoryIds(
						new long[] {assetCategory.getCategoryId()});
					setGroupId(_group.getGroupId());
					setJournalArticleContent(
						new JournalArticleContent() {
							{
								put(LocaleUtil.US, title);

								setDefaultLocale(LocaleUtil.US);
								setName("content");
							}
						});
					setJournalArticleTitle(
						new JournalArticleTitle() {
							{
								put(
									LocaleUtil.US,
									RandomTestUtil.randomString());
							}
						});
				}
			});
	}

	private void _assertSearch(
			String keyword, String assetCategoryIdsFieldName,
			List<Long> expectedAssetCategoryIds,
			String assetCategoryTitlesFieldName,
			List<String> expectedAssetCategoryTitles)
		throws Exception, SearchException {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setKeywords(keyword);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(
			assetCategoryIdsFieldName, Field.ASSET_CATEGORY_TITLES);

		Hits hits = _indexer.search(searchContext);

		DocumentsAssert.assertValuesIgnoreRelevance(
			(String)searchContext.getAttribute("queryString"), hits.getDocs(),
			assetCategoryIdsFieldName,
			TransformUtil.transform(expectedAssetCategoryIds, String::valueOf));

		DocumentsAssert.assertValuesIgnoreRelevance(
			(String)searchContext.getAttribute("queryString"), hits.getDocs(),
			assetCategoryTitlesFieldName, expectedAssetCategoryTitles);
	}

	private void _assertSearchInternalFields(
			String keyword, List<Long> assetCategoryIds,
			List<String> assetCategoryTitles)
		throws Exception, SearchException {

		_assertSearch(
			keyword, Field.ASSET_INTERNAL_CATEGORY_IDS, assetCategoryIds,
			Field.getLocalizedName(
				LocaleUtil.US, Field.ASSET_INTERNAL_CATEGORY_TITLES),
			assetCategoryTitles);
	}

	private void _assertSearchPublicFields(
			String keyword, List<Long> assetCategoryIds,
			List<String> assetCategoryTitles)
		throws Exception, SearchException {

		_assertSearch(
			keyword, Field.ASSET_CATEGORY_IDS, assetCategoryIds,
			Field.getLocalizedName(LocaleUtil.US, Field.ASSET_CATEGORY_TITLES),
			assetCategoryTitles);
	}

	private List<Long> _getAssetCategoryIds(AssetCategory assetCategory) {
		return Arrays.asList(assetCategory.getCategoryId());
	}

	private List<String> _getAssetCategoryTitles(AssetCategory assetCategory) {
		Map<Locale, String> titleMap = assetCategory.getTitleMap();

		return TransformUtil.transform(titleMap.values(), String::toLowerCase);
	}

	@Inject
	private static AssetCategoryService _assetCategoryService;

	@Inject
	private static AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private static DDMStructureLocalService _ddmStructureLocalService;

	@Inject(
		filter = "indexer.class.name=com.liferay.journal.model.JournalArticle"
	)
	private static Indexer<JournalArticle> _indexer;

	@Inject
	private static Portal _portal;

	@DeleteAfterTestRun
	private List<AssetCategory> _assetCategories = new ArrayList<>();

	@DeleteAfterTestRun
	private List<AssetVocabulary> _assetVocabularies = new ArrayList<>();

	@DeleteAfterTestRun
	private Group _group;

	private GroupSearchFixture _groupSearchFixture;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	private JournalArticleSearchFixture _journalArticleSearchFixture;

	@Inject
	private SearchLocalizationHelper _searchLocalizationHelper;

}