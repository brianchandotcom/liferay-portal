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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.suggest.CompletionSuggester;
import com.liferay.portal.kernel.search.suggest.PhraseSuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.TermSuggester;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.document.DefaultElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch7.internal.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search.SearchRequestExecutorFixture;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchResult;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class ElasticsearchSearchEngineAdapterSearchRequestTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchSearchEngineAdapterSearchRequestTest.class);

		_elasticsearchFixture.setUp();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Before
	public void setUp() throws Exception {
		_documentFixture.setUp();

		_searchEngineAdapter = createSearchEngineAdapter(_elasticsearchFixture);

		_restHighLevelClient = _elasticsearchFixture.getRestHighLevelClient();

		_indicesClient = _restHighLevelClient.indices();

		_createIndex();

		_putMapping(
			_MAPPING_NAME,
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

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				setSearchRequestExecutor(
					_createSearchRequestExecutor(elasticsearchClientResolver));
			}
		};
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
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			_INDEX_NAME);

		try {
			_indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SearchRequestExecutor _createSearchRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		SearchRequestExecutorFixture searchRequestExecutorFixture =
			new SearchRequestExecutorFixture() {
				{
					setElasticsearchClientResolver(elasticsearchClientResolver);
				}
			};

		searchRequestExecutorFixture.setUp();

		return searchRequestExecutorFixture.getSearchRequestExecutor();
	}

	private void _deleteIndex() {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			_INDEX_NAME);

		try {
			_indicesClient.delete(deleteIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private GetResponse _getDocument(String id) {
		GetRequest getRequest = new GetRequest();

		getRequest.id(id);
		getRequest.index(_INDEX_NAME);

		try {
			return _restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private String _getUID(String value) {
		return StringBundler.concat(
			_DEFAULT_COMPANY_ID, "_", _LOCALIZED_FIELD_NAME, "_", value);
	}

	private void _indexDocument(Document document) {
		IndexRequest indexRequest = new IndexRequest(_INDEX_NAME);

		indexRequest.id(document.getUID());
		indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		indexRequest.type(_MAPPING_NAME);

		ElasticsearchDocumentFactory elasticsearchDocumentFactory =
			new DefaultElasticsearchDocumentFactory();

		indexRequest.source(
			elasticsearchDocumentFactory.getElasticsearchDocument(document),
			XContentType.JSON);

		try {
			_restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
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
			"Expected document added: " + value, getResponse.isExists());
	}

	private void _putMapping(String mappingName, String mappingSource) {
		PutMappingRequest putMappingRequest = new PutMappingRequest(
			_INDEX_NAME);

		putMappingRequest.source(mappingSource, XContentType.JSON);
		putMappingRequest.type(mappingName);

		try {
			_indicesClient.putMapping(
				putMappingRequest, RequestOptions.DEFAULT);
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

	private static final String _INDEX_NAME = "test_request_index";

	private static final String _LOCALIZED_FIELD_NAME =
		"spellCheckKeyword_en_US";

	private static final String _MAPPING_NAME = "test_mapping";

	private static ElasticsearchFixture _elasticsearchFixture;

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private IndicesClient _indicesClient;
	private RestHighLevelClient _restHighLevelClient;
	private SearchEngineAdapter _searchEngineAdapter;

}