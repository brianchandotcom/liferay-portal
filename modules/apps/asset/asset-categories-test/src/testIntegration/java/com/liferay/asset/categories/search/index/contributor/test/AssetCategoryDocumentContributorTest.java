/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.search.index.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class AssetCategoryDocumentContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(_group.getGroupId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, user.getUserId());

		_setUpAssetCategoriesAndAssetVocabulary();

		_blogsEntry = BlogsTestUtil.addEntryWithWorkflow(
			user.getUserId(), RandomTestUtil.randomString(), false,
			_serviceContext);
		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null,
			HashMapBuilder.put(
				LocaleUtil.US, StringPool.BLANK
			).build(),
			LocaleUtil.getSiteDefault(), false, true, _serviceContext);
	}

	@Test
	public void testContributeGroupAssetCategoryExternalReferenceCodes()
		throws Exception {

		_testContribute(
			_blogsEntry,
			_getGroupAssetCategoryExpectedExternalReferenceCodes(
				_publicAssetCategories),
			"groupAssetCategoryExternalReferenceCodes");
		_testContribute(
			_journalArticle,
			_getGroupAssetCategoryExpectedExternalReferenceCodes(
				_publicAssetCategories),
			"groupAssetCategoryExternalReferenceCodes");
	}

	@Test
	public void testContributeGroupAssetInternalCategoryExternalReferenceCodes()
		throws Exception {

		_testContribute(
			_blogsEntry,
			_getGroupAssetCategoryExpectedExternalReferenceCodes(
				_internalAssetCategories),
			"groupAssetInternalCategoryExternalReferenceCodes");
		_testContribute(
			_journalArticle,
			_getGroupAssetCategoryExpectedExternalReferenceCodes(
				_internalAssetCategories),
			"groupAssetInternalCategoryExternalReferenceCodes");
	}

	@Test
	public void testContributeGroupAssetVocabularyCategoryExternalReferenceCodes()
		throws Exception {

		_testContribute(
			_blogsEntry,
			_getGroupAssetVocabularyCategoryExternalReferenceCodes(
				_publicAssetCategories),
			"groupAssetVocabularyCategoryExternalReferenceCodes");
		_testContribute(
			_journalArticle,
			_getGroupAssetVocabularyCategoryExternalReferenceCodes(
				_publicAssetCategories),
			"groupAssetVocabularyCategoryExternalReferenceCodes");
	}

	private List<String> _getGroupAssetCategoryExpectedExternalReferenceCodes(
		List<AssetCategory> assetCategories) {

		return TransformUtil.transform(
			assetCategories,
			assetCategory -> StringBundler.concat(
				_group.getExternalReferenceCode(), _DELIMITER,
				assetCategory.getExternalReferenceCode()));
	}

	private List<String> _getGroupAssetVocabularyCategoryExternalReferenceCodes(
		List<AssetCategory> assetCategories) {

		return TransformUtil.transform(
			assetCategories,
			assetCategory -> {
				AssetVocabulary assetVocabulary =
					_assetVocabularyLocalService.getAssetVocabulary(
						assetCategory.getVocabularyId());

				return StringBundler.concat(
					_group.getExternalReferenceCode(), _DELIMITER,
					assetVocabulary.getExternalReferenceCode(), _DELIMITER,
					assetCategory.getExternalReferenceCode());
			});
	}

	private boolean _isSearchEngineSolr() {
		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		return Objects.equals(searchEngine.getVendor(), "Solr");
	}

	private void _setUpAssetCategoriesAndAssetVocabulary() throws Exception {
		AssetVocabulary internalAssetVocabulary = _setUpAssetVocabulary(
			AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL);

		for (int i = 0; i < 2; i++) {
			AssetCategory assetCategory =
				_assetCategoryLocalService.addCategory(
					TestPropsValues.getUserId(), _group.getGroupId(),
					RandomTestUtil.randomString(),
					internalAssetVocabulary.getVocabularyId(), _serviceContext);

			_internalAssetCategories.add(assetCategory);
		}

		AssetVocabulary publicAssetVocabulary = _setUpAssetVocabulary(
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC);

		for (int i = 0; i < 2; i++) {
			AssetCategory assetCategory =
				_assetCategoryLocalService.addCategory(
					TestPropsValues.getUserId(), _group.getGroupId(),
					RandomTestUtil.randomString(),
					publicAssetVocabulary.getVocabularyId(), _serviceContext);

			_publicAssetCategories.add(assetCategory);
		}

		List<Long> assetCategoryIds = TransformUtil.transform(
			ListUtil.concat(_internalAssetCategories, _publicAssetCategories),
			assetCategory -> assetCategory.getCategoryId());

		_serviceContext.setAssetCategoryIds(
			ArrayUtil.toLongArray(assetCategoryIds));
	}

	private AssetVocabulary _setUpAssetVocabulary(int visibilityType)
		throws Exception {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), _serviceContext);

		assetVocabulary.setVisibilityType(visibilityType);

		return _assetVocabularyLocalService.updateAssetVocabulary(
			assetVocabulary);
	}

	private void _testContribute(
			ExternalReferenceCodeModel externalReferenceCodeModel,
			List<String> externalReferenceCodes, String fieldName)
		throws Exception {

		CountSearchRequest countSearchRequest = new CountSearchRequest();

		if (_isSearchEngineSolr()) {
			countSearchRequest.setIndexNames("liferay");
		}
		else {
			countSearchRequest.setIndexNames(
				"liferay-" + TestPropsValues.getCompanyId());
		}

		BooleanQuery booleanQuery = _queries.booleanQuery();

		ClassedModel classedModel = (ClassedModel)externalReferenceCodeModel;

		booleanQuery.addMustQueryClauses(
			_queries.term(Field.COMPANY_ID, TestPropsValues.getCompanyId()),
			_queries.term(
				Field.ENTRY_CLASS_NAME, classedModel.getModelClassName()),
			_queries.term(Field.GROUP_ID, _group.getGroupId()));

		for (String externalReferenceCode : externalReferenceCodes) {
			booleanQuery.addMustQueryClauses(
				_queries.term(fieldName, externalReferenceCode));
		}

		countSearchRequest.setQuery(booleanQuery);

		CountSearchResponse countSearchResponse = _searchEngineAdapter.execute(
			countSearchRequest);

		Assert.assertTrue(countSearchResponse.getCount() == 1);
	}

	private static final String _DELIMITER =
		StringPool.AMPERSAND + StringPool.AMPERSAND;

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	private BlogsEntry _blogsEntry;
	private Group _group;
	private final List<AssetCategory> _internalAssetCategories =
		new ArrayList<>(2);
	private JournalArticle _journalArticle;
	private final List<AssetCategory> _publicAssetCategories = new ArrayList<>(
		2);

	@Inject
	private Queries _queries;

	@Inject
	private SearchEngineAdapter _searchEngineAdapter;

	@Inject
	private SearchEngineHelper _searchEngineHelper;

	private ServiceContext _serviceContext;

}