/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.rest.client.dto.v1_0.FacetConfiguration;
import com.liferay.portal.search.rest.client.dto.v1_0.SearchRequestBody;
import com.liferay.portal.search.rest.client.dto.v1_0.SearchResult;
import com.liferay.portal.search.rest.client.pagination.Page;
import com.liferay.portal.search.rest.client.resource.v1_0.SearchResultResource;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.time.DateFormatUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@RunWith(Arquillian.class)
public class SearchResultResourceTest extends BaseSearchResultResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_locale = LocaleUtil.getSiteDefault();
		_searchEngine = _searchEngineHelper.getSearchEngine();

		_user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testGroup, _user.getUserId());
	}

	// LPS-186696

	@FeatureFlags("LPS-179669")
	@Ignore
	@Override
	@Test
	public void testPostSearchPage() throws Exception {
		AssetCategory assetCategory = _addAssetCategory();
		AssetTag assetTag = _addAssetTag();

		DDMStructure ddmStructure = _addJournalArticleDDMStructure();

		_addJournalArticleWithDDMStructure(ddmStructure);

		JournalArticle journalArticle = _addJournalArticle(
			assetCategory, assetTag);

		_testPostSearchPageWithCategoryFacetConfiguration(assetCategory);
		_testPostSearchPageWithCategoryTreeFacetConfiguration(assetCategory);
		_testPostSearchPageWithCustomFacetConfiguration();
		_testPostSearchPageWithDateRangeFacetConfiguration();
		_testPostSearchPageWithFolderFacetConfiguration(journalArticle);
		_testPostSearchPageWithKeywords(journalArticle);
		_testPostSearchPageWithNestedFacetConfiguration(ddmStructure);
		_testPostSearchPageWithSiteFacetConfiguration();
		_testPostSearchPageWithTagFacetConfiguration(assetTag);
		_testPostSearchPageWithTypeFacetConfiguration();
		_testPostSearchPageWithUserFacetConfiguration();
		_testPostSearchPageZeroResults();
	}

	private AssetCategory _addAssetCategory() throws Exception {
		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addDefaultVocabulary(
				testGroup.getGroupId());

		return _assetCategoryLocalService.addCategory(
			_user.getUserId(), testGroup.getGroupId(),
			StringUtil.randomString(), assetVocabulary.getVocabularyId(),
			_serviceContext);
	}

	private AssetTag _addAssetTag() throws Exception {
		return _assetTagLocalService.addTag(
			_user.getUserId(), testGroup.getGroupId(),
			StringUtil.randomString(), _serviceContext);
	}

	private JournalArticle _addJournalArticle(
			AssetCategory assetCategory, AssetTag assetTag)
		throws Exception {

		JournalFolder journalFolder = _journalFolderLocalService.addFolder(
			null, _user.getUserId(), testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringPool.BLANK, _serviceContext);

		return JournalTestUtil.addArticle(
			testGroup.getGroupId(), journalFolder.getFolderId(),
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), _user.getUserId(),
				new long[] {assetCategory.getCategoryId()},
				new String[] {assetTag.getName()}));
	}

	private DDMStructure _addJournalArticleDDMStructure() throws Exception {
		Class<JournalArticle> clazz = JournalArticle.class;

		return DDMStructureTestUtil.addStructure(
			testGroup.getGroupId(), clazz.getName(),
			DDMStructureTestUtil.getSampleDDMForm(
				"name", "string", "keyword", true, "text",
				new Locale[] {_locale}, _locale),
			_locale);
	}

	private JournalArticle _addJournalArticleWithDDMStructure(
			DDMStructure ddmStructure)
		throws Exception {

		return _journalArticleLocalService.addArticle(
			null, _user.getUserId(), testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			HashMapBuilder.put(
				_locale, StringUtil.randomString()
			).build(),
			HashMapBuilder.put(
				_locale, StringUtil.randomString()
			).build(),
			DDMStructureTestUtil.getSampleStructuredContent("test"),
			ddmStructure.getStructureId(), null, _serviceContext);
	}

	private void _assertFacetConfiguration(
			Map<String, Object> facetAttributes, String facetName,
			Object facetValues, Object... expectedValues)
		throws Exception {

		Arrays.sort(expectedValues);

		Page<SearchResult> page = _postSearchPageWithFacetConfiguration(
			new FacetConfiguration() {
				{
					attributes = facetAttributes;
					frequencyThreshold = 1;
					name = facetName;
					values = new Object[] {facetValues};
				}
			});

		Map<String, Object> facetsMap = (Map<String, Object>)page.getFacets();

		Assert.assertTrue(facetsMap.containsKey(facetName));

		List<String> termValuesList = new ArrayList<>();

		JSONArray termJSONArray = _jsonFactory.createJSONArray(
			(Object[])facetsMap.get(facetName));

		for (int i = 0; i < termJSONArray.length(); i++) {
			JSONObject termJSONObject = _jsonFactory.createJSONObject(
				termJSONArray.getString(i));

			Assert.assertTrue(termJSONObject.has("displayName"));
			Assert.assertTrue(termJSONObject.has("frequency"));
			Assert.assertTrue(termJSONObject.has("term"));

			termValuesList.add(termJSONObject.getString("term"));
		}

		String[] termValues = termValuesList.toArray(new String[0]);

		Arrays.sort(termValues);

		Assert.assertTrue(Objects.deepEquals(expectedValues, termValues));
	}

	private void _assertFacetConfiguration(
			String facetName, Object facetValues, String... expectedValues)
		throws Exception {

		_assertFacetConfiguration(null, facetName, facetValues, expectedValues);
	}

	private Page<SearchResult> _postSearchPage(String keywords)
		throws Exception {

		return _postSearchPage(null, keywords, null, new SearchRequestBody());
	}

	private Page<SearchResult> _postSearchPage(
			String entryClassNames, String keywords, String nestedFields,
			SearchRequestBody searchRequestBody)
		throws Exception {

		SearchResultResource.Builder builder = SearchResultResource.builder();

		searchResultResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", nestedFields
		).build();

		return searchResultResource.postSearchPage(
			entryClassNames, keywords,
			"groupIds/any(g:g%20eq%20" + String.valueOf(testGroup.getGroupId()),
			null, null, searchRequestBody);
	}

	private Page<SearchResult> _postSearchPageWithFacetConfiguration(
			FacetConfiguration facetConfiguration)
		throws Exception {

		facetConfiguration.setFrequencyThreshold(0);

		SearchRequestBody searchRequestBody = new SearchRequestBody() {
			{
				attributes = HashMapBuilder.<String, Object>put(
					"search.empty.search", true
				).build();

				facetConfigurations = new FacetConfiguration[] {
					facetConfiguration
				};
			}
		};

		return _postSearchPage(null, null, null, searchRequestBody);
	}

	private void _testPostSearchPageWithCategoryFacetConfiguration(
			AssetCategory assetCategory)
		throws Exception {

		_assertFacetConfiguration(
			"category", assetCategory.getCategoryId(),
			String.valueOf(assetCategory.getCategoryId()));
	}

	private void _testPostSearchPageWithCategoryTreeFacetConfiguration(
			AssetCategory assetCategory)
		throws Exception {

		_assertFacetConfiguration(
			HashMapBuilder.<String, Object>put(
				"mode", "tree"
			).put(
				"vocabularyIds",
				new String[] {String.valueOf(assetCategory.getVocabularyId())}
			).build(),
			"category", assetCategory.getCategoryId(),
			String.valueOf(assetCategory.getCategoryId()));
	}

	private void _testPostSearchPageWithCustomFacetConfiguration()
		throws Exception {

		_assertFacetConfiguration(
			HashMapBuilder.<String, Object>put(
				"field", Field.COMPANY_ID
			).build(),
			"custom", testCompany.getCompanyId(),
			String.valueOf(testCompany.getCompanyId()));
	}

	private void _testPostSearchPageWithDateRangeFacetConfiguration()
		throws Exception {

		LocalDateTime startOfDay = LocalDateTime.of(
			LocalDate.now(), LocalTime.MIN);

		JSONArray rangesJSONArray = _jsonFactory.createJSONArray();

		String range = StringBundler.concat(
			DateFormatUtils.format(
				Date.from(startOfDay.toInstant(ZoneOffset.ofHours(0))),
				"yyyyMMddHHmmss"),
			" TO ", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));

		rangesJSONArray.put(
			JSONUtil.put(
				"label", "1"
			).put(
				"range", range
			));

		_assertFacetConfiguration(
			HashMapBuilder.<String, Object>put(
				"field", "modified"
			).put(
				"format", "yyyyMMddHHmmss"
			).put(
				"ranges", rangesJSONArray
			).build(),
			"date-range", range, range);
	}

	private void _testPostSearchPageWithFolderFacetConfiguration(
			JournalArticle journalArticle)
		throws Exception {

		_assertFacetConfiguration(
			"folder", journalArticle.getFolderId(),
			String.valueOf(journalArticle.getFolderId()));
	}

	private void _testPostSearchPageWithKeywords(JournalArticle journalArticle)
		throws Exception {

		Page<SearchResult> page = _postSearchPage(
			journalArticle.getArticleId());

		Assert.assertEquals(1L, page.getTotalCount());
		Assert.assertEquals(1L, page.getPage());
	}

	private void _testPostSearchPageWithNestedFacetConfiguration(
			DDMStructure ddmStructure)
		throws Exception {

		if (Objects.equals(_searchEngine.getVendor(), "Solr")) {
			return;
		}

		_assertFacetConfiguration(
			HashMapBuilder.<String, Object>put(
				"field",
				"ddmFieldArray.ddmFieldValueKeyword_" +
					LocaleUtil.toLanguageId(_locale)
			).put(
				"filterField", "ddmFieldArray.ddmFieldName"
			).put(
				"filterValue",
				StringBundler.concat(
					"ddm__keyword__", ddmStructure.getStructureId(), "__name_",
					LocaleUtil.toLanguageId(_locale))
			).put(
				"path", "ddmFieldArray"
			).build(),
			"nested", "test", "test");
	}

	private void _testPostSearchPageWithSiteFacetConfiguration()
		throws Exception {

		_assertFacetConfiguration(
			"site", testGroup.getGroupId(),
			String.valueOf(testGroup.getGroupId()));
	}

	private void _testPostSearchPageWithTagFacetConfiguration(AssetTag assetTag)
		throws Exception {

		_assertFacetConfiguration(
			"tag", assetTag.getName(), assetTag.getName());
	}

	private void _testPostSearchPageWithTypeFacetConfiguration()
		throws Exception {

		Class<JournalArticle> journalArticleClass = JournalArticle.class;
		Class<JournalFolder> journalFolderClass = JournalFolder.class;
		Class<User> userClass = User.class;

		_assertFacetConfiguration(
			"type", StringPool.BLANK, journalArticleClass.getName(),
			journalFolderClass.getName(), userClass.getName());
	}

	private void _testPostSearchPageWithUserFacetConfiguration()
		throws Exception {

		String userFullName = StringUtil.toLowerCase(_user.getFullName());

		_assertFacetConfiguration("user", userFullName, userFullName);
	}

	private void _testPostSearchPageZeroResults() throws Exception {
		Page<SearchResult> page = _postSearchPage("shouldnotmatchanything");

		Assert.assertEquals(0L, page.getTotalCount());
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private JournalFolderLocalService _journalFolderLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	private Locale _locale;

	@Inject
	private Portal _portal;

	private SearchEngine _searchEngine;

	@Inject
	private SearchEngineHelper _searchEngineHelper;

	private ServiceContext _serviceContext;
	private User _user;

}