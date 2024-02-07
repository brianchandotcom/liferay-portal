/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.search.suggest.CompletionSuggester;
import com.liferay.portal.kernel.search.suggest.PhraseSuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.TermSuggester;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.OpenPointInTimeRequest;
import com.liferay.portal.search.engine.adapter.search.OpenPointInTimeResponse;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchResult;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.internal.sort.SortsImpl;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.document.OpenSearchDocumentFactory;
import com.liferay.portal.search.opensearch2.internal.document.OpenSearchDocumentFactoryImpl;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search.SearchRequestExecutorFixture;
import com.liferay.portal.search.opensearch2.internal.util.IndexUtil;
import com.liferay.portal.search.pit.PointInTime;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.Refresh;
import org.opensearch.client.opensearch.core.GetRequest;
import org.opensearch.client.opensearch.core.GetResponse;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.DeleteIndexRequest;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;
import org.opensearch.client.opensearch.indices.PutMappingRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Michael C. Han
 */
public class OpenSearchSearchEngineAdapterSearchRequestTest
	extends BaseOpenSearchTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Mockito.when(
			FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);

		_openSearchClient = openSearchConnectionManager.getOpenSearchClient();

		_openSearchIndicesClient = _openSearchClient.indices();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_frameworkUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_createIndex();

		_documentFixture.setUp();

		_putMapping(
			JSONUtil.put(
				"dynamic_templates",
				JSONUtil.put(
					JSONUtil.put(
						"template_en",
						JSONUtil.put(
							"mapping",
							JSONUtil.put(
								"analyzer", "english"
							).put(
								"store", true
							).put(
								"term_vector", "with_positions_offsets"
							).put(
								"type", "text"
							)
						).put(
							"match", "\\w+_en\\b|\\w+_en_[A-Z]{2}\\b"
						).put(
							"match_mapping_type", "string"
						).put(
							"match_pattern", "regex"
						)))
			).put(
				"properties",
				JSONUtil.put(
					"companyId",
					JSONUtil.put(
						"store", true
					).put(
						"type", "keyword"
					)
				).put(
					"keywordSuggestion", JSONUtil.put("type", "completion")
				).put(
					"languageId",
					JSONUtil.put(
						"index", false
					).put(
						"store", true
					).put(
						"type", "keyword"
					)
				)
			).toString());
	}

	@After
	public void tearDown() throws Exception {
		_deleteIndex();

		_documentFixture.tearDown();
	}

	@Test
	public void testCompletionSuggester() throws IOException {
		_indexSuggestKeyword("message");
		_indexSuggestKeyword("search");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			TEST_INDEX_NAME);

		Suggester suggester1 = new CompletionSuggester(
			"completion", "keywordSuggestion", "sear");

		suggestSearchRequest.addSuggester(suggester1);

		Suggester suggester2 = new CompletionSuggester(
			"completion2", "keywordSuggestion", "messa");

		suggestSearchRequest.addSuggester(suggester2);

		SuggestSearchResponse suggestSearchResponse =
			searchEngineAdapter.execute(suggestSearchRequest);

		_assertSuggestion(
			suggestSearchResponse.getSuggestSearchResultMap(),
			"completion|[search]", "completion2|[message]");
	}

	@Test
	public void testDeepPaginationWithScroll() throws Exception {
		_indexSuggestKeyword(RandomTestUtil.randomString());
		_indexSuggestKeyword(RandomTestUtil.randomString());
		_indexSuggestKeyword(RandomTestUtil.randomString());

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(TEST_INDEX_NAME);
		searchSearchRequest.setQuery(new MatchAllQuery());
		searchSearchRequest.setScrollKeepAliveMinutes(1);
		searchSearchRequest.setSize(1);

		for (int i = 0; i < 3; i++) {
			SearchSearchResponse searchSearchResponse =
				searchEngineAdapter.execute(searchSearchRequest);

			Assert.assertEquals(1, _getDocumentsLength(searchSearchResponse));

			searchSearchRequest.setScrollId(searchSearchResponse.getScrollId());
		}

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		Assert.assertEquals(0, _getDocumentsLength(searchSearchResponse));
	}

	@Test
	public void testDeepPaginationWithSearchAfter() throws IOException {
		_indexSuggestKeyword(RandomTestUtil.randomString());
		_indexSuggestKeyword(RandomTestUtil.randomString());
		_indexSuggestKeyword(RandomTestUtil.randomString());

		OpenPointInTimeRequest openPointInTimeRequest =
			new OpenPointInTimeRequest(1);

		openPointInTimeRequest.setIndices(TEST_INDEX_NAME);

		OpenPointInTimeResponse openPointInTimeResponse =
			searchEngineAdapter.execute(openPointInTimeRequest);

		PointInTime pointInTime = new PointInTime(
			openPointInTimeResponse.pitId());

		Sorts sorts = new SortsImpl();

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(TEST_INDEX_NAME);
		searchSearchRequest.setPointInTime(pointInTime);
		searchSearchRequest.setQuery(new MatchAllQuery());
		searchSearchRequest.setSize(1);
		searchSearchRequest.addSorts(sorts.field("_id", SortOrder.DESC));
		searchSearchRequest.setStart(0);

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		for (int i = 0; i < 3; i++) {
			Assert.assertEquals(1, _getDocumentsLength(searchSearchResponse));

			SearchHits searchHits = searchSearchResponse.getSearchHits();

			List<SearchHit> searchHitList = searchHits.getSearchHits();

			SearchHit lastSearchHit = searchHitList.get(
				searchHitList.size() - 1);

			searchSearchRequest.setSearchAfter(lastSearchHit.getSortValues());

			searchSearchResponse = searchEngineAdapter.execute(
				searchSearchRequest);
		}

		Assert.assertEquals(0, _getDocumentsLength(searchSearchResponse));
	}

	@Test
	public void testGlobalText() throws IOException {
		_indexSuggestKeyword("search");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			TEST_INDEX_NAME);

		suggestSearchRequest.setGlobalText("sear");

		Suggester completionSuggester = new CompletionSuggester(
			"completion", "keywordSuggestion", null);

		Suggester termSuggester = new TermSuggester(
			"term", _LOCALIZED_FIELD_NAME);

		suggestSearchRequest.addSuggester(completionSuggester);
		suggestSearchRequest.addSuggester(termSuggester);

		SuggestSearchResponse suggestSearchResponse =
			searchEngineAdapter.execute(suggestSearchRequest);

		_assertSuggestion(
			suggestSearchResponse.getSuggestSearchResultMap(),
			"completion|[search]", "term|[search]");
	}

	@Test
	public void testGlobalTextOverride() throws IOException {
		_indexSuggestKeyword("message");
		_indexSuggestKeyword("search");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			TEST_INDEX_NAME);

		suggestSearchRequest.setGlobalText("sear");

		Suggester completionSuggester = new CompletionSuggester(
			"completion", "keywordSuggestion", "messa");

		Suggester termSuggester = new TermSuggester(
			"term", _LOCALIZED_FIELD_NAME);

		suggestSearchRequest.addSuggester(completionSuggester);
		suggestSearchRequest.addSuggester(termSuggester);

		SuggestSearchResponse suggestSearchResponse =
			searchEngineAdapter.execute(suggestSearchRequest);

		_assertSuggestion(
			suggestSearchResponse.getSuggestSearchResultMap(),
			"completion|[message]", "term|[search]");
	}

	@Test
	public void testPhraseSuggester() throws IOException {
		_indexSuggestKeyword("indexed this phrase");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			TEST_INDEX_NAME);

		PhraseSuggester phraseSuggester = new PhraseSuggester(
			"phrase", _LOCALIZED_FIELD_NAME, "indexef   this   phrasd");

		phraseSuggester.setSize(2);

		suggestSearchRequest.addSuggester(phraseSuggester);

		SuggestSearchResponse suggestSearchResponse =
			searchEngineAdapter.execute(suggestSearchRequest);

		_assertSuggestion(
			2, suggestSearchResponse.getSuggestSearchResultMap(),
			"phrase|[indexef phrase, index phrasd]");
	}

	@Test
	public void testTermSuggester() throws IOException {
		_indexSuggestKeyword("message");
		_indexSuggestKeyword("search");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			TEST_INDEX_NAME);

		Suggester suggester = new TermSuggester(
			"termSuggestion", _LOCALIZED_FIELD_NAME, "searc");

		suggestSearchRequest.addSuggester(suggester);

		SuggestSearchResponse suggestSearchResponse =
			searchEngineAdapter.execute(suggestSearchRequest);

		_assertSuggestion(
			suggestSearchResponse.getSuggestSearchResultMap(),
			"termSuggestion|[search]");
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		OpenSearchConnectionManager openSearchConnectionManager) {

		SearchEngineAdapter searchEngineAdapter =
			new OpenSearchSearchEngineAdapterImpl();

		ReflectionTestUtil.setFieldValue(
			searchEngineAdapter, "_searchRequestExecutor",
			_createSearchRequestExecutor(openSearchConnectionManager));

		return searchEngineAdapter;
	}

	private static SearchRequestExecutor _createSearchRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_searchRequestExecutorFixture = new SearchRequestExecutorFixture() {
			{
				setOpenSearchConnectionManager(openSearchConnectionManager);
			}
		};

		_searchRequestExecutorFixture.setUp();

		return _searchRequestExecutorFixture.getSearchRequestExecutor();
	}

	private void _assertSuggestion(
		int size, Map<String, SuggestSearchResult> suggestSearchResultMap,
		String... expectedSuggestionsString) {

		for (String expectedSuggestionString : expectedSuggestionsString) {
			List<String> expectedSuggestionParts = StringUtil.split(
				expectedSuggestionString, '|');

			String suggesterName = expectedSuggestionParts.get(0);
			String expectedSuggestions = expectedSuggestionParts.get(1);

			SuggestSearchResult suggestSearchResult =
				suggestSearchResultMap.get(suggesterName);

			List<SuggestSearchResult.Entry> suggestSearchResultEntries =
				suggestSearchResult.getEntries();

			Assert.assertEquals(
				"Expected 1 SuggestSearchResult.Entry", 1,
				suggestSearchResultEntries.size());

			SuggestSearchResult.Entry suggestSearchResultEntry =
				suggestSearchResultEntries.get(0);

			List<SuggestSearchResult.Entry.Option>
				suggestSearchResultEntryOptions =
					suggestSearchResultEntry.getOptions();

			Assert.assertEquals(
				"Expected " + size + " SuggestSearchResult.Entry.Option", size,
				suggestSearchResultEntryOptions.size());

			String actualSuggestions = String.valueOf(
				_toList(suggestSearchResultEntryOptions));

			Assert.assertEquals(expectedSuggestions, actualSuggestions);
		}
	}

	private void _assertSuggestion(
		Map<String, SuggestSearchResult> suggestSearchResultsMap,
		String... expectedSuggestionsString) {

		_assertSuggestion(
			1, suggestSearchResultsMap, expectedSuggestionsString);
	}

	private void _createIndex() {
		try {
			_openSearchIndicesClient.create(
				CreateIndexRequest.of(
					createIndexRequest -> createIndexRequest.index(
						TEST_INDEX_NAME)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _deleteIndex() {
		try {
			_openSearchIndicesClient.delete(
				DeleteIndexRequest.of(
					deleteIndexRequest -> deleteIndexRequest.index(
						TEST_INDEX_NAME)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private GetResponse<JsonData> _getDocument(String id) {
		try {
			return _openSearchClient.get(
				GetRequest.of(
					getRequest -> getRequest.id(
						id
					).index(
						TEST_INDEX_NAME
					)),
				JsonData.class);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private int _getDocumentsLength(SearchSearchResponse searchSearchResponse) {
		Hits hits = searchSearchResponse.getHits();

		Document[] documents = hits.getDocs();

		return documents.length;
	}

	private String _getUID(String value) {
		return StringBundler.concat(
			_DEFAULT_COMPANY_ID, "_", _LOCALIZED_FIELD_NAME, "_", value);
	}

	private void _indexDocument(Document document) {
		IndexRequest.Builder<JsonData> indexRequestBuilder =
			new IndexRequest.Builder<>();

		indexRequestBuilder.id(document.getUID());
		indexRequestBuilder.index(TEST_INDEX_NAME);
		indexRequestBuilder.refresh(Refresh.True);

		OpenSearchDocumentFactory openSearchDocumentFactory =
			new OpenSearchDocumentFactoryImpl();

		indexRequestBuilder.document(
			openSearchDocumentFactory.getOpenSearchDocument(document));

		try {
			_openSearchClient.index(indexRequestBuilder.build());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _indexSuggestKeyword(String value) throws IOException {
		Document document = new DocumentImpl();

		document.addKeyword(_LOCALIZED_FIELD_NAME, value);
		document.addKeyword("keywordSuggestion", value);

		document.addKeyword(Field.COMPANY_ID, _DEFAULT_COMPANY_ID);
		document.addKeyword(Field.LANGUAGE_ID, _EN_US_LANGUAGE_ID);
		document.addKeyword(Field.TYPE, "spellCheckKeyword");
		document.addKeyword(Field.UID, _getUID(value));

		_indexDocument(document);

		GetResponse getResponse = _getDocument(_getUID(value));

		Assert.assertTrue(
			"Expected document added: " + value, getResponse.found());
	}

	private void _putMapping(String mappingSource) throws Exception {
		JSONObject mappingsJSONObject = JSONFactoryUtil.createJSONObject(
			mappingSource);

		PutMappingRequest.Builder builder = new PutMappingRequest.Builder();

		builder.dynamicTemplates(
			IndexUtil.getDynamicTemplatesMap(mappingsJSONObject));
		builder.index(TEST_INDEX_NAME);
		builder.properties(IndexUtil.getPropertiesMap(mappingsJSONObject));

		try {
			_openSearchIndicesClient.putMapping(builder.build());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private List<String> _toList(
		List<SuggestSearchResult.Entry.Option>
			suggestSearchResultEntryOptions) {

		List<String> options = new ArrayList<>();

		for (SuggestSearchResult.Entry.Option suggestSearchResultEntryOption :
				suggestSearchResultEntryOptions) {

			options.add(suggestSearchResultEntryOption.getText());
		}

		return options;
	}

	private static final long _DEFAULT_COMPANY_ID = 12345;

	private static final String _EN_US_LANGUAGE_ID = "en_US";

	private static final String _LOCALIZED_FIELD_NAME =
		"spellCheckKeyword_en_US";

	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);
	private static OpenSearchClient _openSearchClient;
	private static OpenSearchIndicesClient _openSearchIndicesClient;
	private static SearchRequestExecutorFixture _searchRequestExecutorFixture;

	private final DocumentFixture _documentFixture = new DocumentFixture();

}