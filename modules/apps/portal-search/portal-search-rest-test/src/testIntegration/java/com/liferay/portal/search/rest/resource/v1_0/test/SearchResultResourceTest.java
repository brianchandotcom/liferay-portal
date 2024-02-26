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
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.rest.dto.v1_0.FacetConfiguration;
import com.liferay.portal.search.rest.dto.v1_0.SearchRequestBody;
import com.liferay.portal.search.rest.dto.v1_0.SearchResult;
import com.liferay.portal.search.rest.pagination.SearchPage;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.net.URLEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.time.DateFormatUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Almir Ferreira
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

	@FeatureFlags("LPS-179669")
	@Override
	@Test
	public void testPostSearchPage() throws Exception {
		AssetCategory assetCategory = _addAssetCategory();
		AssetTag assetTag = _addAssetTag();

		DDMStructure ddmStructure = _addJournalArticleDDMStructure();

		_addJournalArticleWithDDMStructure(ddmStructure);

		JournalArticle journalArticle = _addJournalArticle(
			assetCategory, assetTag);

		ObjectDefinition objectDefinition =
			_addObjectDefinitionWithObjectEntry();

		SXPBlueprint sxpBlueprintWithHighlightConfiguration = _addSXPBlueprint(
			true);
		SXPBlueprint sxpBlueprintWithoutHighlightConfiguration =
			_addSXPBlueprint(false);

		_testPostSearchPageWithCategoryFacetConfiguration(assetCategory);
		_testPostSearchPageWithCategoryTreeFacetConfiguration(assetCategory);
		_testPostSearchPageWithCustomFacetConfiguration();
		_testPostSearchPageWithDateRangeFacetConfiguration();
		_testPostSearchPageWithEmbeddedNestedFields(objectDefinition);
		_testPostSearchPageWithFolderFacetConfiguration(journalArticle);
		_testPostSearchPageWithHighlightConfiguration(
			sxpBlueprintWithHighlightConfiguration);
		_testPostSearchPageWithKeywords(journalArticle);
		_testPostSearchPageWithNestedFacetConfiguration(ddmStructure);
		_testPostSearchPageWithoutHighlightConfiguration(
			sxpBlueprintWithoutHighlightConfiguration);
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

	private ObjectDefinition _addObjectDefinitionWithObjectEntry()
		throws Exception {

		ObjectField objectField = ObjectFieldUtil.createObjectField(
			"Text", "String", true, true, null,
			StringUtil.toLowerCase(RandomTestUtil.randomString()), "test",
			false);

		objectField.setExternalReferenceCode(RandomTestUtil.randomString());

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(objectField));

		ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, "test", RandomTestUtil.randomString());

		return objectDefinition;
	}

	private SXPBlueprint _addSXPBlueprint(boolean highlightConfiguration)
		throws Exception {

		JSONObject configurationJSONObject = JSONUtil.put(
			"advancedConfiguration",
			JSONUtil.put(
				"source",
				JSONUtil.put(
					"fetchSource", true
				).put(
					"includes",
					JSONFactoryUtil.createJSONArray(
					).put(
						"fullName"
					)
				))
		).put(
			"generalConfiguration",
			JSONUtil.put(
				"searchableAssetTypes",
				JSONUtil.put("com.liferay.portal.kernel.model.User"))
		).put(
			"queryConfiguration", JSONUtil.put("applyIndexerClauses", true)
		);

		if (highlightConfiguration) {
			configurationJSONObject.put(
				"highlightConfiguration",
				_createSXPBlueprintHighlightConfigurationJSON());
		}

		return _sxpBlueprintLocalService.addSXPBlueprint(
			null, _user.getUserId(), configurationJSONObject.toString(),
			Collections.singletonMap(_locale, StringPool.BLANK), null,
			StringPool.BLANK,
			Collections.singletonMap(_locale, RandomTestUtil.randomString()),
			_serviceContext);
	}

	private SearchPage<SearchResult> _assertFacetConfiguration(
			boolean anyMatch, Map<String, Object> facetAttributes,
			String facetName, Object facetValues, Object... expectedValues)
		throws Exception {

		Arrays.sort(expectedValues);

		SearchPage<SearchResult> page = _postSearchPageWithFacetConfiguration(
			new FacetConfiguration() {
				{
					attributes = facetAttributes;
					frequencyThreshold = 1;
					name = facetName;
					values = new Object[] {facetValues};
				}
			});

		Map<String, Object> facetsMap =
			(Map<String, Object>)page.getSearchFacets();

		Assert.assertTrue(facetsMap.containsKey(facetName));

		List<String> termValuesList = new ArrayList<>();

		JSONArray termJSONArray = (JSONArray)facetsMap.get(facetName);

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

		if (anyMatch) {
			Assert.assertFalse(
				Collections.disjoint(
					Arrays.asList((String[])expectedValues),
					Arrays.asList(termValues)));
		}
		else {
			Assert.assertTrue(Objects.deepEquals(expectedValues, termValues));
		}

		return page;
	}

	private void _assertFacetConfiguration(
			boolean anyMatch, String facetName, Object facetValues,
			String... expectedValues)
		throws Exception {

		_assertFacetConfiguration(
			anyMatch, null, facetName, facetValues, expectedValues);
	}

	private JSONObject _createSXPBlueprintHighlightConfigurationJSON() {
		JSONObject fieldsJSONObject = JSONUtil.put(
			"fullName",
			JSONUtil.put(
				"fragment_size", 100
			).put(
				"number_of_fragments", 10
			));

		return JSONUtil.put(
			"fields", fieldsJSONObject
		).put(
			"post_tags",
			JSONFactoryUtil.createJSONArray(
			).put(
				"</liferay-hl>"
			)
		).put(
			"pre_tags",
			JSONFactoryUtil.createJSONArray(
			).put(
				"<liferay-hl>"
			)
		).put(
			"require_field_match", true
		);
	}

	private String _getEndpointURL(
			String entryClassNames, String filter, String keywords,
			String nestedFields)
		throws Exception {

		List<String> parameters = new ArrayList<>();

		if (!Validator.isBlank(entryClassNames)) {
			parameters.add(
				"entryClassNames=" +
					URLEncoder.encode(entryClassNames, StringPool.UTF8));
		}

		if (!Validator.isBlank(filter)) {
			parameters.add(
				"filter=" + URLEncoder.encode(filter, StringPool.UTF8));
		}

		if (!Validator.isBlank(keywords)) {
			parameters.add(
				"search=" + URLEncoder.encode(keywords, StringPool.UTF8));
		}

		if (!Validator.isBlank(nestedFields)) {
			parameters.add(
				"nestedFields=" +
					URLEncoder.encode(nestedFields, StringPool.UTF8));
		}

		String endpoint = "portal-search-rest/v1.0/search";

		if (!parameters.isEmpty()) {
			endpoint += "?" + StringUtil.merge(parameters, "&");
		}

		return endpoint;
	}

	private HashMap<String, JSONArray> _getSearchFacets(JSONObject jsonObject) {
		JSONObject searchFacetsJSONObject = jsonObject.getJSONObject(
			"searchFacets");

		if (searchFacetsJSONObject == null) {
			return null;
		}

		HashMap<String, JSONArray> map = new HashMap<>();
		Iterator<String> iterator = searchFacetsJSONObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			JSONArray valueJSONArray = searchFacetsJSONObject.getJSONArray(key);

			map.put(key, valueJSONArray);
		}

		return map;
	}

	private String _getUserHighlightedFullName() {
		return StringBundler.concat(
			"<liferay-hl>", _user.getFirstName(), "</liferay-hl> ",
			"<liferay-hl>", _user.getLastName(), "</liferay-hl>");
	}

	private SearchPage<SearchResult> _postSearchPage(String keywords)
		throws Exception {

		return _postSearchPage(
			null,
			"groupIds/any(g:g eq " + String.valueOf(testGroup.getGroupId()) +
				")",
			keywords, null, new SearchRequestBody());
	}

	private SearchPage<SearchResult> _postSearchPage(
			String entryClassNames, String filter, String keywords,
			String nestedFields, SearchRequestBody searchRequestBody)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			searchRequestBody.toString(),
			_getEndpointURL(entryClassNames, filter, keywords, nestedFields),
			Http.Method.POST);

		return _toSearchPage(jsonObject);
	}

	private SearchPage<SearchResult> _postSearchPageWithFacetConfiguration(
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

		return _postSearchPage(
			null,
			"groupIds/any(g:g eq " + String.valueOf(testGroup.getGroupId()) +
				")",
			null, null, searchRequestBody);
	}

	private SearchPage<SearchResult>
			_postSearchPageWithSXPBlueprintConfiguration(
				String entryClassNames, String keywords,
				SXPBlueprint sxpBlueprint)
		throws Exception {

		SearchRequestBody searchRequestBody = new SearchRequestBody() {
			{
				attributes = HashMapBuilder.<String, Object>put(
					"search.experiences.blueprint.external.reference.code",
					sxpBlueprint.getExternalReferenceCode()
				).build();
			}
		};

		return _postSearchPage(
			entryClassNames, null, keywords, null, searchRequestBody);
	}

	private void _testPostSearchPageWithCategoryFacetConfiguration(
			AssetCategory assetCategory)
		throws Exception {

		_assertFacetConfiguration(
			false, "category", assetCategory.getCategoryId(),
			String.valueOf(assetCategory.getCategoryId()));
	}

	private void _testPostSearchPageWithCategoryTreeFacetConfiguration(
			AssetCategory assetCategory)
		throws Exception {

		_assertFacetConfiguration(
			false,
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
			false,
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

		SearchPage<SearchResult> page = _assertFacetConfiguration(
			false,
			HashMapBuilder.<String, Object>put(
				"field", "modified"
			).put(
				"format", "yyyyMMddHHmmss"
			).put(
				"ranges", rangesJSONArray
			).build(),
			"date-range", range, range);

		Map<String, Object> facetsMap =
			(Map<String, Object>)page.getSearchFacets();

		JSONArray termJSONArray = (JSONArray)facetsMap.get("date-range");

		JSONObject termJSONObject = _jsonFactory.createJSONObject(
			termJSONArray.getString(0));

		Assert.assertEquals("1", termJSONObject.getString("displayName"));
	}

	private void _testPostSearchPageWithEmbeddedNestedFields(
			ObjectDefinition objectDefinition)
		throws Exception {

		String entryClassName = objectDefinition.getClassName();

		SearchPage<SearchResult> page = _postSearchPage(
			entryClassName, null, objectDefinition.getUserName(), "embedded",
			new SearchRequestBody());

		Collection<SearchResult> items = page.getItems();

		Assert.assertFalse(items.isEmpty());

		for (SearchResult item : items) {
			Assert.assertNotNull(item.getEmbedded());
		}
	}

	private void _testPostSearchPageWithFolderFacetConfiguration(
			JournalArticle journalArticle)
		throws Exception {

		_assertFacetConfiguration(
			false, "folder", journalArticle.getFolderId(),
			String.valueOf(journalArticle.getFolderId()));
	}

	private void _testPostSearchPageWithHighlightConfiguration(
			SXPBlueprint sxpBlueprint)
		throws Exception {

		SearchPage<SearchResult> page =
			_postSearchPageWithSXPBlueprintConfiguration(
				_user.getModelClassName(), _user.getFullName(), sxpBlueprint);

		List<SearchResult> searchResults = ListUtil.fromCollection(
			page.getItems());

		Assert.assertFalse(searchResults.isEmpty());

		Assert.assertTrue(
			ListUtil.count(
				searchResults,
				searchResult -> Objects.equals(
					searchResult.getTitle(), _getUserHighlightedFullName())) >=
						1);
	}

	private void _testPostSearchPageWithKeywords(JournalArticle journalArticle)
		throws Exception {

		SearchPage<SearchResult> page = _postSearchPage(
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
			false,
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

	private void _testPostSearchPageWithoutHighlightConfiguration(
			SXPBlueprint sxpBlueprint)
		throws Exception {

		SearchPage<SearchResult> page =
			_postSearchPageWithSXPBlueprintConfiguration(
				_user.getModelClassName(), _user.getFullName(), sxpBlueprint);

		List<SearchResult> searchResults = ListUtil.fromCollection(
			page.getItems());

		Assert.assertFalse(searchResults.isEmpty());

		Assert.assertTrue(
			ListUtil.count(
				searchResults,
				searchResult -> Objects.equals(
					searchResult.getTitle(), _user.getFullName())) >= 1);

		Assert.assertEquals(
			0,
			ListUtil.count(
				searchResults,
				searchResult -> Objects.equals(
					searchResult.getTitle(), _getUserHighlightedFullName())));
	}

	private void _testPostSearchPageWithSiteFacetConfiguration()
		throws Exception {

		_assertFacetConfiguration(
			true, "site", testGroup.getGroupId(),
			String.valueOf(testGroup.getGroupId()));
	}

	private void _testPostSearchPageWithTagFacetConfiguration(AssetTag assetTag)
		throws Exception {

		_assertFacetConfiguration(
			true, "tag", assetTag.getName(), assetTag.getName());
	}

	private void _testPostSearchPageWithTypeFacetConfiguration()
		throws Exception {

		Class<JournalArticle> journalArticleClass = JournalArticle.class;
		Class<JournalFolder> journalFolderClass = JournalFolder.class;
		Class<User> userClass = User.class;

		_assertFacetConfiguration(
			false, "type", StringPool.BLANK, journalArticleClass.getName(),
			journalFolderClass.getName(), userClass.getName());
	}

	private void _testPostSearchPageWithUserFacetConfiguration()
		throws Exception {

		String userFullName = StringUtil.toLowerCase(_user.getFullName());

		_assertFacetConfiguration(
			true, "user", userFullName, String.valueOf(_user.getUserId()));
	}

	private void _testPostSearchPageZeroResults() throws Exception {
		SearchPage<SearchResult> page = _postSearchPage(
			"shouldnotmatchanything");

		Assert.assertEquals(0L, page.getTotalCount());
	}

	private SearchPage<SearchResult> _toSearchPage(JSONObject jsonObject) {
		Pagination pagination = Pagination.of(
			jsonObject.getInt("page"), jsonObject.getInt("pageSize"));

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		List<SearchResult> searchResults = new ArrayList<>();

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			Object item = itemsJSONArray.get(i);

			SearchResult searchResult = SearchResult.toDTO(item.toString());

			searchResults.add(searchResult);
		}

		long totalCount = jsonObject.getLong("totalCount");

		return SearchPage.of(
			null, null, _getSearchFacets(jsonObject), searchResults, pagination,
			totalCount);
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

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	private User _user;

}