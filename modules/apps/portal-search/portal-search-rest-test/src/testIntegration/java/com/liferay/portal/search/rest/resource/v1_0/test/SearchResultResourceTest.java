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
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
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
@FeatureFlags("LPS-179669")
@RunWith(Arquillian.class)
public class SearchResultResourceTest extends BaseSearchResultResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_locale = LocaleUtil.getSiteDefault();

		_ddmStructure = _addJournalArticleDDMStructure(_locale);

		_searchEngine = _searchEngineHelper.getSearchEngine();

		_user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testGroup, _user.getUserId());

		_assetCategory = _addAssetCategory(_serviceContext, _user);
		_assetTag = _addAssetTag(_serviceContext, _user);

		_journalArticle = _addJournalArticle(
			_assetCategory, _assetTag, _serviceContext, _user);
	}

	@Override
	@Test
	public void testPostSearchPage() throws Exception {
		SearchPage<SearchResult> page = _postSearchPage(
			_journalArticle.getArticleId());

		Assert.assertEquals(1L, page.getTotalCount());
		Assert.assertEquals(1L, page.getPage());
	}

	@Test
	public void testPostSearchPageWithCategoryFacetConfiguration()
		throws Exception {

		_assertFacetConfiguration(
			false, null, "category", _assetCategory.getCategoryId(),
			String.valueOf(_assetCategory.getCategoryId()));
	}

	@Test
	public void testPostSearchPageWithCategoryTreeFacetConfiguration()
		throws Exception {

		_assertFacetConfiguration(
			false,
			HashMapBuilder.<String, Object>put(
				"mode", "tree"
			).put(
				"vocabularyIds",
				new String[] {String.valueOf(_assetCategory.getVocabularyId())}
			).build(),
			"category", _assetCategory.getCategoryId(),
			String.valueOf(_assetCategory.getCategoryId()));
	}

	@Test
	public void testPostSearchPageWithCustomFacetConfiguration()
		throws Exception {

		_assertFacetConfiguration(
			false,
			HashMapBuilder.<String, Object>put(
				"field", Field.COMPANY_ID
			).build(),
			"custom", testCompany.getCompanyId(),
			String.valueOf(testCompany.getCompanyId()));
	}

	@Test
	public void testPostSearchPageWithDateRangeFacetConfiguration()
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

		Assert.assertEquals(
			"1",
			_jsonFactory.createJSONObject(
				termJSONArray.getString(0)
			).getString(
				"displayName"
			));
	}

	@Test
	public void testPostSearchPageWithEmbeddedNestedFields() throws Exception {
		ObjectDefinition objectDefinition =
			_addObjectDefinitionWithObjectEntry();

		SearchPage<SearchResult> page = _postSearchPage(
			objectDefinition.getClassName(), null,
			objectDefinition.getUserName(), "embedded",
			new SearchRequestBody());

		Collection<SearchResult> items = page.getItems();

		Assert.assertFalse(items.isEmpty());

		for (SearchResult item : items) {
			Assert.assertNotNull(item.getEmbedded());
		}
	}

	@Test
	public void testPostSearchPageWithFolderFacetConfiguration()
		throws Exception {

		_assertFacetConfiguration(
			false, null, "folder", _journalArticle.getFolderId(),
			String.valueOf(_journalArticle.getFolderId()));
	}

	@Test
	public void testPostSearchPageWithHighlightConfiguration()
		throws Exception {

		SearchPage<SearchResult> page =
			_postSearchPageWithSXPBlueprintConfiguration(
				_user.getModelClassName(), _user.getFullName(),
				_addSXPBlueprint(true));

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

	@Test
	public void testPostSearchPageWithNestedFacetConfiguration()
		throws Exception {

		_addJournalArticleWithDDMStructure();

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
					"ddm__keyword__", _ddmStructure.getStructureId(), "__name_",
					LocaleUtil.toLanguageId(_locale))
			).put(
				"path", "ddmFieldArray"
			).build(),
			"nested", "test", "test");
	}

	@Test
	public void testPostSearchPageWithoutHighlightConfiguration()
		throws Exception {

		SearchPage<SearchResult> page =
			_postSearchPageWithSXPBlueprintConfiguration(
				_user.getModelClassName(), _user.getFullName(),
				_addSXPBlueprint(false));

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

	@Test
	public void testPostSearchPageWithSiteFacetConfiguration()
		throws Exception {

		_assertFacetConfiguration(
			true, null, "site", testGroup.getGroupId(),
			String.valueOf(testGroup.getGroupId()));
	}

	@Test
	public void testPostSearchPageWithTagFacetConfiguration() throws Exception {
		_assertFacetConfiguration(
			true, null, "tag", _assetTag.getName(), _assetTag.getName());
	}

	@Test
	public void testPostSearchPageWithTypeFacetConfiguration()
		throws Exception {

		_assertFacetConfiguration(
			false, null, "type", StringPool.BLANK,
			JournalArticle.class.getName(), JournalFolder.class.getName(),
			User.class.getName());
	}

	@Test
	public void testPostSearchPageWithUserFacetConfiguration()
		throws Exception {

		_assertFacetConfiguration(
			true, null, "user", StringUtil.toLowerCase(_user.getFullName()),
			String.valueOf(_user.getUserId()));
	}

	@Test
	public void testPostSearchPageZeroResults() throws Exception {
		SearchPage<SearchResult> page = _postSearchPage(
			"shouldnotmatchanything");

		Assert.assertEquals(0L, page.getTotalCount());
	}

	private AssetCategory _addAssetCategory(
			ServiceContext serviceContext, User user)
		throws Exception {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addDefaultVocabulary(
				testGroup.getGroupId());

		return _assetCategoryLocalService.addCategory(
			user.getUserId(), testGroup.getGroupId(), StringUtil.randomString(),
			assetVocabulary.getVocabularyId(), serviceContext);
	}

	private AssetTag _addAssetTag(ServiceContext serviceContext, User user)
		throws Exception {

		return _assetTagLocalService.addTag(
			user.getUserId(), testGroup.getGroupId(), StringUtil.randomString(),
			serviceContext);
	}

	private JournalArticle _addJournalArticle(
			AssetCategory assetCategory, AssetTag assetTag,
			ServiceContext serviceContext, User user)
		throws Exception {

		JournalFolder journalFolder = _journalFolderLocalService.addFolder(
			null, user.getUserId(), testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringPool.BLANK, serviceContext);

		return JournalTestUtil.addArticle(
			testGroup.getGroupId(), journalFolder.getFolderId(),
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), user.getUserId(),
				new long[] {assetCategory.getCategoryId()},
				new String[] {assetTag.getName()}));
	}

	private DDMStructure _addJournalArticleDDMStructure(Locale locale)
		throws Exception {

		Class<JournalArticle> clazz = JournalArticle.class;

		return DDMStructureTestUtil.addStructure(
			testGroup.getGroupId(), clazz.getName(),
			DDMStructureTestUtil.getSampleDDMForm(
				"name", "string", "keyword", true, "text",
				new Locale[] {locale}, locale),
			locale);
	}

	private void _addJournalArticleWithDDMStructure() throws Exception {
		_journalArticleLocalService.addArticle(
			null, _user.getUserId(), testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			HashMapBuilder.put(
				_locale, StringUtil.randomString()
			).build(),
			HashMapBuilder.put(
				_locale, StringUtil.randomString()
			).build(),
			DDMStructureTestUtil.getSampleStructuredContent("test"),
			_ddmStructure.getStructureId(), null, _serviceContext);
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

	private SXPBlueprint _addSXPBlueprint(boolean highlightingEnabled)
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

		if (highlightingEnabled) {
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
			String facetName, Object facetValues, String... expectedValues)
		throws Exception {

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

		Assert.assertNotNull(facetsMap);

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

		Arrays.sort(expectedValues);
		Arrays.sort(termValues);

		if (anyMatch) {
			Assert.assertFalse(
				Collections.disjoint(
					Arrays.asList(expectedValues), Arrays.asList(termValues)));
		}
		else {
			Assert.assertArrayEquals(expectedValues, termValues);
		}

		return page;
	}

	private JSONObject _createSXPBlueprintHighlightConfigurationJSON() {
		return JSONUtil.put(
			"fields",
			JSONUtil.put(
				"fullName",
				JSONUtil.put(
					"fragment_size", 100
				).put(
					"number_of_fragments", 10
				))
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

			map.put(key, searchFacetsJSONObject.getJSONArray(key));
		}

		return map;
	}

	private String _getUserHighlightedFullName() {
		return StringBundler.concat(
			HighlightUtil.HIGHLIGHT_TAG_OPEN, _user.getFirstName(),
			HighlightUtil.HIGHLIGHT_TAG_CLOSE, StringPool.SPACE,
			HighlightUtil.HIGHLIGHT_TAG_OPEN, _user.getLastName(),
			HighlightUtil.HIGHLIGHT_TAG_CLOSE);
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

		return _toSearchPage(
			HTTPTestUtil.invokeToJSONObject(
				searchRequestBody.toString(),
				_getEndpointURL(
					entryClassNames, filter, keywords, nestedFields),
				Http.Method.POST));
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

	private SearchPage<SearchResult> _toSearchPage(JSONObject jsonObject) {
		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		if (itemsJSONArray == null) {
			itemsJSONArray = JSONFactoryUtil.createJSONArray();
		}

		List<SearchResult> searchResults = new ArrayList<>();

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			searchResults.add(
				SearchResult.toDTO(
					itemsJSONArray.get(
						i
					).toString()));
		}

		return SearchPage.of(
			null, null, _getSearchFacets(jsonObject), searchResults,
			Pagination.of(
				jsonObject.getInt("page"), jsonObject.getInt("pageSize")),
			jsonObject.getLong("totalCount"));
	}

	private AssetCategory _assetCategory;

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	private AssetTag _assetTag;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	private DDMStructure _ddmStructure;
	private JournalArticle _journalArticle;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private JournalFolderLocalService _journalFolderLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	private Locale _locale;
	private SearchEngine _searchEngine;

	@Inject
	private SearchEngineHelper _searchEngineHelper;

	private ServiceContext _serviceContext;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	private User _user;

}