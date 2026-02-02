/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.PutMappingRequest;
import co.elastic.clients.json.JsonData;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.search.suggest.CompletionSuggester;
import com.liferay.portal.kernel.search.suggest.PhraseSuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.TermSuggester;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch8.internal.document.ElasticsearchDocumentFactoryUtil;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search.SearchRequestExecutorFixture;
import com.liferay.portal.search.elasticsearch8.internal.util.IndexUtil;
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
import com.liferay.portal.search.pit.PointInTime;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Michael C. Han
 */
public class ElasticsearchSearchEngineAdapterSearchRequestTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Mockito.when(
			FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);

		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchSearchEngineAdapterSearchRequestTest.class);

		_elasticsearchFixture.setUp();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();

		_frameworkUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_documentFixture.setUp();

		_searchEngineAdapter = createSearchEngineAdapter(_elasticsearchFixture);

		_elasticsearchClient = _elasticsearchFixture.getElasticsearchClient();

		_elasticsearchIndicesClient = _elasticsearchClient.indices();

		_createIndex();

		_putMapping(
			StringBundler.concat(
				"{\n\"dynamic_templates\": [\n{\n",
				"\"template_en\": {\n\"mapping\": {\n",
				"\"analyzer\": \"english\",\n\"store\": true,\n",
				"\"term_vector\": \"with_positions_offsets\",\n",
				"\"type\": \"text\"\n},\n",
				"\"match\": \"\\\\w+_en\\\\b|\\\\w+_en_[A-Z]{2}\\\\b\",\n",
				"\"match_mapping_type\": \"string\",\n",
				"\"match_pattern\": \"regex\"\n}\n}\n],\n",
				"\"properties\": {\n\"companyId\": {\n",
				"\"store\": true,\n\"type\": \"keyword\"\n},\n",
				"\"languageId\": {\n\"index\": false,\n",
				"\"store\": true,\n\"type\": \"keyword\"\n},",
				"\"keywordSuggestion\" : {\n\"type\" : \"completion\"\n",
				"}\n\n}\n}"));
	}

	@After
	public void tearDown() throws Exception {
		_deleteIndex();

		_documentFixture.tearDown();

		_searchRequestExecutorFixture.tearDown();
	}

	@Test
	public void testCompletionSuggester() throws IOException {
		_indexSuggestKeyword("message");
		_indexSuggestKeyword("search");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			_INDEX_NAME);

		Suggester suggester1 = new CompletionSuggester(
			"completion", "keywordSuggestion", "sear");

		suggestSearchRequest.addSuggester(suggester1);

		Suggester suggester2 = new CompletionSuggester(
			"completion2", "keywordSuggestion", "messa");

		suggestSearchRequest.addSuggester(suggester2);

		SuggestSearchResponse suggestSearchResponse =
			_searchEngineAdapter.execute(suggestSearchRequest);

		_assertSuggestion(
			suggestSearchResponse.getSuggestSearchResultMap(),
			"completion|[search]", "completion2|[message]");
	}

	@Ignore
	@Test
	public void testDeepPaginationWithScroll() throws Exception {
		_indexSuggestKeyword(RandomTestUtil.randomString());
		_indexSuggestKeyword(RandomTestUtil.randomString());
		_indexSuggestKeyword(RandomTestUtil.randomString());

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(_INDEX_NAME);
		searchSearchRequest.setQuery(new MatchAllQuery());
		searchSearchRequest.setScrollKeepAliveMinutes(1);
		searchSearchRequest.setSize(1);

		for (int i = 0; i < 3; i++) {
			SearchSearchResponse searchSearchResponse =
				_searchEngineAdapter.execute(searchSearchRequest);

			Assert.assertEquals(1, _getDocumentsLength(searchSearchResponse));

			searchSearchRequest.setScrollId(searchSearchResponse.getScrollId());
		}

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		Assert.assertEquals(0, _getDocumentsLength(searchSearchResponse));
	}

	@Ignore
	@Test
	public void testDeepPaginationWithSearchAfter() throws IOException {
		_indexSuggestKeyword(RandomTestUtil.randomString());
		_indexSuggestKeyword(RandomTestUtil.randomString());
		_indexSuggestKeyword(RandomTestUtil.randomString());

		OpenPointInTimeRequest openPointInTimeRequest =
			new OpenPointInTimeRequest(1);

		openPointInTimeRequest.setIndices(_INDEX_NAME);

		OpenPointInTimeResponse openPointInTimeResponse =
			_searchEngineAdapter.execute(openPointInTimeRequest);

		PointInTime pointInTime = new PointInTime(
			openPointInTimeResponse.pitId());

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(_INDEX_NAME);
		searchSearchRequest.setPointInTime(pointInTime);
		searchSearchRequest.setQuery(new MatchAllQuery());
		searchSearchRequest.setSize(1);
		searchSearchRequest.setSorts(new Sort[] {new Sort("_count", true)});
		searchSearchRequest.setStart(0);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		for (int i = 0; i < 3; i++) {
			Assert.assertEquals(1, _getDocumentsLength(searchSearchResponse));

			SearchHits searchHits = searchSearchResponse.getSearchHits();

			List<SearchHit> searchHitList = searchHits.getSearchHits();

			SearchHit lastSearchHit = searchHitList.get(
				searchHitList.size() - 1);

			searchSearchRequest.setSearchAfter(lastSearchHit.getSortValues());

			searchSearchResponse = _searchEngineAdapter.execute(
				searchSearchRequest);
		}

		Assert.assertEquals(0, _getDocumentsLength(searchSearchResponse));
	}

	@Test
	public void testGlobalText() throws IOException {
		_indexSuggestKeyword("search");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			_INDEX_NAME);

		suggestSearchRequest.setGlobalText("sear");

		Suggester completionSuggester = new CompletionSuggester(
			"completion", "keywordSuggestion", null);

		Suggester termSuggester = new TermSuggester(
			"term", _LOCALIZED_FIELD_NAME);

		suggestSearchRequest.addSuggester(completionSuggester);
		suggestSearchRequest.addSuggester(termSuggester);

		SuggestSearchResponse suggestSearchResponse =
			_searchEngineAdapter.execute(suggestSearchRequest);

		_assertSuggestion(
			suggestSearchResponse.getSuggestSearchResultMap(),
			"completion|[search]", "term|[search]");
	}

	@Test
	public void testGlobalTextOverride() throws IOException {
		_indexSuggestKeyword("message");
		_indexSuggestKeyword("search");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			_INDEX_NAME);

		suggestSearchRequest.setGlobalText("sear");

		Suggester completionSuggester = new CompletionSuggester(
			"completion", "keywordSuggestion", "messa");

		Suggester termSuggester = new TermSuggester(
			"term", _LOCALIZED_FIELD_NAME);

		suggestSearchRequest.addSuggester(completionSuggester);
		suggestSearchRequest.addSuggester(termSuggester);

		SuggestSearchResponse suggestSearchResponse =
			_searchEngineAdapter.execute(suggestSearchRequest);

		_assertSuggestion(
			suggestSearchResponse.getSuggestSearchResultMap(),
			"completion|[message]", "term|[search]");
	}

	@Test
	public void testPhraseSuggester() throws IOException {
		_indexSuggestKeyword("indexed this phrase");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			_INDEX_NAME);

		PhraseSuggester phraseSuggester = new PhraseSuggester(
			"phrase", _LOCALIZED_FIELD_NAME, "indexef   this   phrasd");

		phraseSuggester.setSize(2);

		suggestSearchRequest.addSuggester(phraseSuggester);

		SuggestSearchResponse suggestSearchResponse =
			_searchEngineAdapter.execute(suggestSearchRequest);

		_assertSuggestion(
			suggestSearchResponse.getSuggestSearchResultMap(), 2,
			"phrase|[indexef phrase, index phrasd]");
	}

	@Test
	public void testTermSuggester() throws IOException {
		_indexSuggestKeyword("message");
		_indexSuggestKeyword("search");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			_INDEX_NAME);

		Suggester suggester = new TermSuggester(
			"termSuggestion", _LOCALIZED_FIELD_NAME, "searc");

		suggestSearchRequest.addSuggester(suggester);

		SuggestSearchResponse suggestSearchResponse =
			_searchEngineAdapter.execute(suggestSearchRequest);

		_assertSuggestion(
			suggestSearchResponse.getSuggestSearchResultMap(),
			"termSuggestion|[search]");
	}

	protected SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		SearchEngineAdapter searchEngineAdapter =
			new ElasticsearchSearchEngineAdapterImpl();

		ReflectionTestUtil.setFieldValue(
			searchEngineAdapter, "_searchRequestExecutor",
			_createSearchRequestExecutor(elasticsearchClientResolver));

		return searchEngineAdapter;
	}

	private void _assertSuggestion(
		Map<String, SuggestSearchResult> suggestSearchResultMap, int size,
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
			suggestSearchResultsMap, 1, expectedSuggestionsString);
	}

	private void _createIndex() {
		try {
			_elasticsearchIndicesClient.create(
				CreateIndexRequest.of(
					createIndexRequest -> createIndexRequest.index(
						_INDEX_NAME)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SearchRequestExecutor _createSearchRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_searchRequestExecutorFixture = new SearchRequestExecutorFixture() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};

		_searchRequestExecutorFixture.setUp();

		return _searchRequestExecutorFixture.getSearchRequestExecutor();
	}

	private void _deleteIndex() {
		try {
			_elasticsearchIndicesClient.delete(
				DeleteIndexRequest.of(
					deleteIndexRequest -> deleteIndexRequest.index(
						_INDEX_NAME)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private GetResponse<JsonData> _getDocument(String id) {
		try {
			return _elasticsearchClient.get(
				GetRequest.of(
					getRequest -> getRequest.id(
						id
					).index(
						_INDEX_NAME
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

		indexRequestBuilder.document(
			ElasticsearchDocumentFactoryUtil.getElasticsearchDocument(
				document));
		indexRequestBuilder.id(document.getUID());
		indexRequestBuilder.index(_INDEX_NAME);
		indexRequestBuilder.refresh(Refresh.True);

		try {
			_elasticsearchClient.index(indexRequestBuilder.build());
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
		builder.index(_INDEX_NAME);
		builder.properties(IndexUtil.getPropertiesMap(mappingsJSONObject));

		try {
			_elasticsearchIndicesClient.putMapping(builder.build());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private List<String> _toList(
		List<SuggestSearchResult.Entry.Option>
			suggestSearchResultEntryOptions) {

		return TransformUtil.transform(
			suggestSearchResultEntryOptions,
			suggestSearchResultEntryOption ->
				suggestSearchResultEntryOption.getText());
	}

	private static final long _DEFAULT_COMPANY_ID = 12345;

	private static final String _EN_US_LANGUAGE_ID = "en_US";

	private static final String _INDEX_NAME = "test_request_index";

	private static final String _LOCALIZED_FIELD_NAME =
		"spellCheckKeyword_en_US";

	private static ElasticsearchFixture _elasticsearchFixture;
	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private ElasticsearchClient _elasticsearchClient;
	private ElasticsearchIndicesClient _elasticsearchIndicesClient;
	private SearchEngineAdapter _searchEngineAdapter;
	private SearchRequestExecutorFixture _searchRequestExecutorFixture;

}