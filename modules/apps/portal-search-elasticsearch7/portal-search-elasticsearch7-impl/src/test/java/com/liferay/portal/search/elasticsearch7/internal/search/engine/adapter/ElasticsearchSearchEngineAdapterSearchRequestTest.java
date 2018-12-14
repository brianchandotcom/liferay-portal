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
import org.elasticsearch.common.xcontent.XContentType;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class ElasticsearchSearchEngineAdapterSearchRequestTest {

	@Before
	public void setUp() throws Exception {
		_documentFixture.setUp();

		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchSearchEngineAdapterIndexRequestTest.class.
				getSimpleName());

		_elasticsearchFixture.setUp();

		_searchEngineAdapter = createSearchEngineAdapter(_elasticsearchFixture);

		_restHighLevelClient = _elasticsearchFixture.getRestHighLevelClient();

		_indicesClient = _restHighLevelClient.indices();

		_createIndex();

		StringBundler sb = new StringBundler(14);

		sb.append("{\n\"dynamic_templates\": [\n{\n");
		sb.append("\"template_en\": {\n\"mapping\": {\n");
		sb.append("\"analyzer\": \"english\",\n\"store\": true,\n");
		sb.append("\"term_vector\": \"with_positions_offsets\",\n");
		sb.append("\"type\": \"text\"\n},\n");
		sb.append("\"match\": \"\\\\w+_en\\\\b|\\\\w+_en_[A-Z]{2}\\\\b\",\n");
		sb.append("\"match_mapping_type\": \"string\",\n");
		sb.append("\"match_pattern\": \"regex\"\n}\n}\n],\n");
		sb.append("\"properties\": {\n\"companyId\": {\n");
		sb.append("\"store\": true,\n\"type\": \"keyword\"\n},\n");
		sb.append("\"languageId\": {\n\"index\": false,\n");
		sb.append("\"store\": true,\n\"type\": \"keyword\"\n},");
		sb.append("\"keywordSuggestion\" : {\n\"type\" : \"completion\"\n");
		sb.append("}\n\n}\n}");

		_putMapping(_MAPPING_NAME, sb.toString());
	}

	@After
	public void tearDown() throws Exception {
		_deleteIndex();

		_documentFixture.tearDown();

		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testCompletionSuggester() throws IOException {
		indexSuggestKeyword("message");
		indexSuggestKeyword("search");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			_INDEX_NAME);

		Suggester suggester = new CompletionSuggester(
			"completion", "keywordSuggestion", "sear");

		suggestSearchRequest.addSuggester(suggester);

		Suggester suggester2 = new CompletionSuggester(
			"completion2", "keywordSuggestion", "messa");

		suggestSearchRequest.addSuggester(suggester2);

		SuggestSearchResponse suggestSearchResponse =
			_searchEngineAdapter.execute(suggestSearchRequest);

		Map<String, SuggestSearchResult> suggestSearchResultMap =
			suggestSearchResponse.getSuggestSearchResultMap();

		assertSuggestion(
			suggestSearchResultMap, "completion|[search]",
			"completion2|[message]");
	}

	@Test
	public void testGlobalText() throws IOException {
		indexSuggestKeyword("search");

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

		Map<String, SuggestSearchResult> suggestSearchResultMap =
			suggestSearchResponse.getSuggestSearchResultMap();

		assertSuggestion(
			suggestSearchResultMap, "completion|[search]", "term|[search]");
	}

	@Test
	public void testGlobalTextOverride() throws IOException {
		indexSuggestKeyword("message");
		indexSuggestKeyword("search");

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

		Map<String, SuggestSearchResult> suggestSearchResultMap =
			suggestSearchResponse.getSuggestSearchResultMap();

		assertSuggestion(
			suggestSearchResultMap, "completion|[message]", "term|[search]");
	}

	@Test
	public void testPhraseSuggester() throws IOException {
		indexSuggestKeyword("indexed this phrase");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			_INDEX_NAME);

		PhraseSuggester phraseSuggester = new PhraseSuggester(
			"phrase", _LOCALIZED_FIELD_NAME, "indexef   this   phrasd");

		phraseSuggester.setSize(2);

		suggestSearchRequest.addSuggester(phraseSuggester);

		SuggestSearchResponse suggestSearchResponse =
			_searchEngineAdapter.execute(suggestSearchRequest);

		Map<String, SuggestSearchResult> suggestSearchResultMap =
			suggestSearchResponse.getSuggestSearchResultMap();

		assertSuggestion(
			suggestSearchResultMap, 2, "phrase|[indexef phrase, index phrasd]");
	}

	@Test
	public void testTermSuggester() throws IOException {
		indexSuggestKeyword("message");
		indexSuggestKeyword("search");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			_INDEX_NAME);

		Suggester suggester = new TermSuggester(
			"termSuggestion", _LOCALIZED_FIELD_NAME, "searc");

		suggestSearchRequest.addSuggester(suggester);

		SuggestSearchResponse suggestSearchResponse =
			_searchEngineAdapter.execute(suggestSearchRequest);

		Map<String, SuggestSearchResult> suggestSearchResultMap =
			suggestSearchResponse.getSuggestSearchResultMap();

		assertSuggestion(suggestSearchResultMap, "termSuggestion|[search]");
	}

	protected void assertSuggestion(
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
				toList(suggestSearchResultEntryOptions));

			Assert.assertEquals(expectedSuggestions, actualSuggestions);
		}
	}

	protected void assertSuggestion(
		Map<String, SuggestSearchResult> suggestSearchResultsMap,
		String... expectedSuggestionsString) {

		assertSuggestion(suggestSearchResultsMap, 1, expectedSuggestionsString);
	}

	protected SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				setSearchRequestExecutor(
					createSearchRequestExecutor(elasticsearchClientResolver));
			}
		};
	}

	protected SearchRequestExecutor createSearchRequestExecutor(
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

	protected String getUID(String value) {
		StringBundler sb = new StringBundler(5);

		sb.append(_DEFAULT_COMPANY_ID);
		sb.append("_");
		sb.append(_LOCALIZED_FIELD_NAME);
		sb.append("_");
		sb.append(value);

		return sb.toString();
	}

	protected void indexSuggestKeyword(String value) throws IOException {
		Document document = new DocumentImpl();

		document.addKeyword(_LOCALIZED_FIELD_NAME, value);
		document.addKeyword("keywordSuggestion", value);

		document.addKeyword(Field.COMPANY_ID, _DEFAULT_COMPANY_ID);
		document.addKeyword(Field.LANGUAGE_ID, _EN_US_LANGUAGE_ID);
		document.addKeyword(Field.TYPE, "spellCheckKeyword");
		document.addKeyword(Field.UID, getUID(value));

		_indexDocument(document);

		GetResponse getResponse = _getDocument(getUID(value));

		Assert.assertTrue(
			"Expected document added: " + value, getResponse.isExists());
	}

	protected List<String> toList(
		List<SuggestSearchResult.Entry.Option>
			suggestSearchResultEntryOptions) {

		List<String> options = new ArrayList<>();

		for (SuggestSearchResult.Entry.Option suggestSearchResultEntryOption :
				suggestSearchResultEntryOptions) {

			options.add(suggestSearchResultEntryOption.getText());
		}

		return options;
	}

	private void _createIndex() {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			_INDEX_NAME);

		try {
			_indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private void _deleteIndex() {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			_INDEX_NAME);

		try {
			_indicesClient.delete(deleteIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private GetResponse _getDocument(String id) {
		GetRequest getRequest = new GetRequest();

		getRequest.id(id);
		getRequest.index(_INDEX_NAME);

		try {
			return _restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private void _indexDocument(Document document) {
		IndexRequest indexRequest = new IndexRequest(_INDEX_NAME);

		indexRequest.id(document.getUID());
		indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		indexRequest.type(_MAPPING_NAME);

		ElasticsearchDocumentFactory elasticsearchDocumentFactory =
			new DefaultElasticsearchDocumentFactory();

		String elasticsearchDocument =
			elasticsearchDocumentFactory.getElasticsearchDocument(document);

		indexRequest.source(elasticsearchDocument, XContentType.JSON);

		try {
			_restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
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
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static final long _DEFAULT_COMPANY_ID = 12345;

	private static final String _EN_US_LANGUAGE_ID = "en_US";

	private static final String _INDEX_NAME = "test_request_index";

	private static final String _LOCALIZED_FIELD_NAME =
		"spellCheckKeyword_en_US";

	private static final String _MAPPING_NAME = "test_mapping";

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private ElasticsearchFixture _elasticsearchFixture;
	private IndicesClient _indicesClient;
	private RestHighLevelClient _restHighLevelClient;
	private SearchEngineAdapter _searchEngineAdapter;

}