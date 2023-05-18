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
import com.liferay.portal.search.rest.client.dto.v1_0.Facet;
import com.liferay.portal.search.rest.client.dto.v1_0.SearchRequestBody;
import com.liferay.portal.search.rest.client.dto.v1_0.SearchResponse;
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
import java.util.Set;

import org.apache.commons.lang3.time.DateFormatUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@RunWith(Arquillian.class)
public class SearchResponseResourceTest
	extends BaseSearchResponseResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_locale = LocaleUtil.getSiteDefault();

		_user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testGroup, _user.getUserId());
	}

	@FeatureFlags("LPS-179669")
	@Override
	@Test
	public void testPostSearch() throws Exception {
		super.testPostSearch();

		AssetCategory assetCategory = _addAssetCategory();
		AssetTag assetTag = _addAssetTag();

		DDMStructure ddmStructure = _addJournalArticleDDMStructure();

		_addJournalArticleWithDDMStructure(ddmStructure);

		JournalArticle journalArticle = _addJournalArticle(
			assetCategory, assetTag);

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		_testPostSearchWithCategoryFacet(assetCategory);
		_testPostSearchWithCategoryTreeFacet(assetCategory);
		_testPostSearchWithCustomFacet();
		_testPostSearchWithDateRangeFacet();
		_testPostSearchWithEntryClassNames();
		_testPostSearchWithFields(journalArticle, searchEngine);
		_testPostSearchWithFolderFacet(journalArticle);
		_testPostSearchWithIncludeAssetFields(journalArticle);
		_testPostSearchWithKeywords(journalArticle);

		if (Objects.equals(searchEngine.getVendor(), "Elasticsearch")) {
			_testPostSearchWithNestedFacet(ddmStructure);
		}

		_testPostSearchWithSiteFacet();
		_testPostSearchWithTagFacet(assetTag);
		_testPostSearchWithUserFacet();
		_testPostSearchWithTypeFacet();
		_testPostSearchZeroResults();
	}

	@Override
	protected SearchResponse testPostSearch_addSearchResponse(
			SearchResponse searchResponse)
		throws Exception {

		return searchResponse;
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

	private void _assertFacet(
			Map<String, Object> facetAttributes, String facetName,
			Object facetValues, Object... expectedValues)
		throws Exception {

		Arrays.sort(expectedValues);

		SearchResponse searchResponse = _postSearchWithFacet(
			new Facet() {
				{
					attributes = facetAttributes;
					frequencyThreshold = 1;
					name = facetName;
					values = new Object[] {facetValues};
				}
			});

		Map<String, Object> facetsMap = searchResponse.getFacets();

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

	private void _assertFacet(
			String facetName, Object facetValues, String... expectedValues)
		throws Exception {

		_assertFacet(null, facetName, facetValues, expectedValues);
	}

	private JSONObject _getFirstDocumentJSONObject(
			SearchResponse searchResponse)
		throws Exception {

		Object[] documents = searchResponse.getDocuments();

		return _jsonFactory.createJSONObject((String)documents[0]);
	}

	private SearchResponse _postSearch(String keywords) throws Exception {
		return _postSearch(
			null, null, null, keywords, null, new SearchRequestBody());
	}

	private SearchResponse _postSearch(
			String[] entryClassNames, Boolean includeAssetSearchSummary,
			Boolean includeAssetTitle, String keywords, String[] resultFields,
			SearchRequestBody searchRequestBody)
		throws Exception {

		return searchResponseResource.postSearch(
			null, entryClassNames, null, new Long[] {testGroup.getGroupId()},
			includeAssetSearchSummary, includeAssetTitle, null, null, keywords,
			resultFields, null, null, null, searchRequestBody);
	}

	private SearchResponse _postSearchWithFacet(Facet facet) throws Exception {
		facet.setFrequencyThreshold(0);

		SearchRequestBody searchRequestBody = new SearchRequestBody() {
			{
				facets = new Facet[] {facet};

				searchContextAttributes = HashMapBuilder.<String, Object>put(
					"search.empty.search", true
				).build();
			}
		};

		return _postSearch(null, null, null, null, null, searchRequestBody);
	}

	private void _testPostSearchWithCategoryFacet(AssetCategory assetCategory)
		throws Exception {

		_assertFacet(
			"category", assetCategory.getCategoryId(),
			String.valueOf(assetCategory.getCategoryId()));
	}

	private void _testPostSearchWithCategoryTreeFacet(
			AssetCategory assetCategory)
		throws Exception {

		_assertFacet(
			HashMapBuilder.<String, Object>put(
				"mode", "tree"
			).put(
				"vocabularyIds",
				new String[] {String.valueOf(assetCategory.getVocabularyId())}
			).build(),
			"category", assetCategory.getCategoryId(),
			String.valueOf(assetCategory.getCategoryId()));
	}

	private void _testPostSearchWithCustomFacet() throws Exception {
		_assertFacet(
			HashMapBuilder.<String, Object>put(
				"field", Field.COMPANY_ID
			).build(),
			"custom", testCompany.getCompanyId(),
			String.valueOf(testCompany.getCompanyId()));
	}

	private void _testPostSearchWithDateRangeFacet() throws Exception {
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

		_assertFacet(
			HashMapBuilder.<String, Object>put(
				"field", "modified"
			).put(
				"format", "yyyyMMddHHmmss"
			).put(
				"ranges", rangesJSONArray
			).build(),
			"date-range", range, range);
	}

	private void _testPostSearchWithEntryClassNames() throws Exception {
		Class<?> clazz = User.class;

		SearchResponse searchResponse = _postSearch(
			new String[] {clazz.getName()}, null, null, null, null,
			new SearchRequestBody() {
				{
					searchContextAttributes =
						HashMapBuilder.<String, Object>put(
							"search.empty.search", true
						).build();
				}
			});

		Assert.assertEquals(Long.valueOf(1), searchResponse.getTotalHits());

		JSONObject documentJSONObject = _getFirstDocumentJSONObject(
			searchResponse);

		Assert.assertEquals(
			documentJSONObject.getString(Field.ENTRY_CLASS_NAME),
			clazz.getName());
	}

	private void _testPostSearchWithFields(
			JournalArticle journalArticle, SearchEngine searchEngine)
		throws Exception {

		SearchResponse searchResponse = _postSearch(
			null, null, null, journalArticle.getTitle(),
			new String[] {Field.ARTICLE_ID}, new SearchRequestBody());

		JSONObject documentJSONObject = _getFirstDocumentJSONObject(
			searchResponse);

		Set<String> keySet = documentJSONObject.keySet();

		if (Objects.equals(searchEngine.getVendor(), "Solr")) {
			Assert.assertTrue(keySet.size() == 2);
		}
		else {
			Assert.assertTrue(keySet.size() == 3);
			Assert.assertTrue(keySet.contains("id"));
		}

		Assert.assertTrue(keySet.contains(Field.ARTICLE_ID));
		Assert.assertTrue(keySet.contains("score"));
	}

	private void _testPostSearchWithFolderFacet(JournalArticle journalArticle)
		throws Exception {

		_assertFacet(
			"folder", journalArticle.getFolderId(),
			String.valueOf(journalArticle.getFolderId()));
	}

	private void _testPostSearchWithIncludeAssetFields(
			JournalArticle journalArticle)
		throws Exception {

		SearchResponse searchResponse = _postSearch(
			null, true, true, journalArticle.getTitle(_locale), null,
			new SearchRequestBody());

		JSONObject documentJSONObject = _getFirstDocumentJSONObject(
			searchResponse);

		Assert.assertEquals(
			journalArticle.getTitle(_locale),
			documentJSONObject.getString("assetTitle"));
		Assert.assertEquals(
			journalArticle.getDescription(_locale),
			documentJSONObject.getString("assetSearchSummary"));
	}

	private void _testPostSearchWithKeywords(JournalArticle journalArticle)
		throws Exception {

		SearchResponse searchResponse = _postSearch(
			journalArticle.getArticleId());

		Assert.assertEquals(Long.valueOf(1), searchResponse.getTotalHits());
		Assert.assertEquals(1, (int)searchResponse.getPage());
	}

	private void _testPostSearchWithNestedFacet(DDMStructure ddmStructure)
		throws Exception {

		_assertFacet(
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

	private void _testPostSearchWithSiteFacet() throws Exception {
		_assertFacet(
			"site", testGroup.getGroupId(),
			String.valueOf(testGroup.getGroupId()));
	}

	private void _testPostSearchWithTagFacet(AssetTag assetTag)
		throws Exception {

		_assertFacet("tag", assetTag.getName(), assetTag.getName());
	}

	private void _testPostSearchWithTypeFacet() throws Exception {
		Class<JournalArticle> journalArticleClass = JournalArticle.class;
		Class<JournalFolder> journalFolderClass = JournalFolder.class;
		Class<User> userClass = User.class;

		_assertFacet(
			"type", StringPool.BLANK, journalArticleClass.getName(),
			journalFolderClass.getName(), userClass.getName());
	}

	private void _testPostSearchWithUserFacet() throws Exception {
		String userFullName = StringUtil.toLowerCase(_user.getFullName());

		_assertFacet("user", userFullName, userFullName);
	}

	private void _testPostSearchZeroResults() throws Exception {
		SearchResponse searchResponse = _postSearch("shouldnotmatchanything");

		Assert.assertEquals(Long.valueOf(0), searchResponse.getTotalHits());
		Assert.assertTrue(searchResponse.getMaxScore() == null);
		Assert.assertTrue(searchResponse.getPage() != null);
		Assert.assertTrue(searchResponse.getPageSize() != null);
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

	@Inject
	private SearchEngineHelper _searchEngineHelper;

	private ServiceContext _serviceContext;
	private User _user;

}