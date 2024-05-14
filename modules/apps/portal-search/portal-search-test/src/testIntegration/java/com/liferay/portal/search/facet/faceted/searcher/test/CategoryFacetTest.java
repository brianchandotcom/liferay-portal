/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.search.JournalArticleBlueprint;
import com.liferay.journal.test.util.search.JournalArticleContent;
import com.liferay.journal.test.util.search.JournalArticleTitle;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.category.CategoryFacetFactory;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dylan Rebelak
 */
@RunWith(Arquillian.class)
@Sync
public class CategoryFacetTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();

		_assetVocabulary = _assetVocabularyLocalService.addDefaultVocabulary(
			_group.getGroupId());
	}

	@Test
	public void testAggregation() throws Exception {
		_addCategory(RandomTestUtil.randomString());

		_addJournalArticle();

		_addUser();

		SearchContext searchContext = _getSearchContext();

		Facet facet = _categoryFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		_assertEntryClassNames(
			Arrays.asList(JournalArticle.class.getName(), User.class.getName()),
			hits, searchContext);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits,
			Collections.singletonMap(_getAssetVocabularyCategoryId(), 1));
	}

	@Test
	public void testAvoidResidualDataFromDDMStructureLocalServiceTest()
		throws Exception {

		// See LPS-58543

		_addCategory("To Do");

		_addUser();

		SearchContext searchContext = _getSearchContext();

		Facet facet = _categoryFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, search(searchContext),
			Collections.singletonMap(_getAssetVocabularyCategoryId(), 1));
	}

	@Test
	public void testSelection() throws Exception {
		_addCategory(RandomTestUtil.randomString());

		_addJournalArticle();

		_addUser();

		SearchContext searchContext = _getSearchContext();

		Facet facet = _categoryFacetFactory.newInstance(searchContext);

		facet.select(_getAssetVocabularyCategoryId());

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		_assertEntryClassNames(
			Collections.singletonList(User.class.getName()), hits,
			searchContext);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits,
			Collections.singletonMap(_getAssetVocabularyCategoryId(), 1));
	}

	private void _addCategory(String title) throws Exception {
		_assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), title,
			_assetVocabulary.getVocabularyId(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	private void _addJournalArticle() throws Exception {
		journalArticleSearchFixture.addArticle(
			new JournalArticleBlueprint() {
				{
					setGroupId(_group.getGroupId());
					setJournalArticleContent(
						new JournalArticleContent() {
							{
								put(
									LocaleUtil.US,
									RandomTestUtil.randomString());

								setDefaultLocale(LocaleUtil.US);
								setName("content");
							}
						});
					setJournalArticleTitle(
						new JournalArticleTitle() {
							{
								put(
									LocaleUtil.US,
									_assetCategory.getTitleCurrentValue());
							}
						});
				}
			});
	}

	private void _addUser() throws Exception {
		_user = UserTestUtil.addUser(_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		UserTestUtil.updateUser(_user, serviceContext);
	}

	private void _assertEntryClassNames(
		List<String> entryClassNames, Hits hits, SearchContext searchContext) {

		DocumentsAssert.assertValuesIgnoreRelevance(
			(String)searchContext.getAttribute("queryString"), hits.getDocs(),
			Field.ENTRY_CLASS_NAME, entryClassNames);
	}

	private String _getAssetVocabularyCategoryId() {
		return StringBundler.concat(
			_assetCategory.getVocabularyId(), StringPool.DASH,
			_assetCategory.getCategoryId());
	}

	private SearchContext _getSearchContext() throws Exception {
		SearchContext searchContext = getSearchContext(
			_assetCategory.getTitleCurrentValue());

		searchContext.setCategoryIds(
			new long[] {_assetCategory.getCategoryId()});
		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		return searchContext;
	}

	@DeleteAfterTestRun
	private AssetCategory _assetCategory;

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@DeleteAfterTestRun
	private AssetVocabulary _assetVocabulary;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private CategoryFacetFactory _categoryFacetFactory;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}