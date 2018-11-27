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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.suggest.CompletionSuggester;
import com.liferay.portal.kernel.search.suggest.PhraseSuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.TermSuggester;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.document.DefaultElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch6.internal.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search.SearchRequestExecutorFixture;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchResult;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;

import java.io.IOException;

import java.util.List;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.get.GetAction;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexAction;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
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

		Client client = _elasticsearchFixture.getClient();

		AdminClient adminClient = client.admin();

		_indicesAdminClient = adminClient.indices();

		_searchEngineAdapter = createSearchEngineAdapter(_elasticsearchFixture);

		createIndex();

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

		putMapping(_MAPPING_NAME, sb.toString());
	}

	@After
	public void tearDown() throws Exception {
		deleteIndex();

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

		suggestSearchRequest.setSuggester(suggester);

		SuggestSearchResponse suggestSearchResponse =
			_searchEngineAdapter.execute(suggestSearchRequest);

		SuggestSearchResult suggestSearchResult =
			suggestSearchResponse.getSuggesterResult("completion");

		assertSuggestion(suggestSearchResult, "search");

		SuggestSearchRequest suggestSearchRequest2 = new SuggestSearchRequest(
			_INDEX_NAME);

		Suggester suggester2 = new CompletionSuggester(
			"completion", "keywordSuggestion", "messa");

		suggestSearchRequest2.setSuggester(suggester2);

		SuggestSearchResponse suggestSearchResponse2 =
			_searchEngineAdapter.execute(suggestSearchRequest2);

		SuggestSearchResult suggestSearchResult2 =
			suggestSearchResponse2.getSuggesterResult("completion");

		assertSuggestion(suggestSearchResult2, "message");
	}

	@Test
	public void testPhraseSuggester() throws IOException {
		indexSuggestKeyword("indexed this phrase");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			_INDEX_NAME);

		Suggester suggester = new PhraseSuggester(
			"phrase", _LOCALIZED_FIELD_NAME, "indexef   this   phrasd");

		suggestSearchRequest.setSuggester(suggester);

		SuggestSearchResponse suggestSearchResponse =
			_searchEngineAdapter.execute(suggestSearchRequest);

		SuggestSearchResult suggestSearchResult =
			suggestSearchResponse.getSuggesterResult("phrase");

		assertSuggestion(suggestSearchResult, "indexed this phrase");
	}

	@Test
	public void testTermSuggest() throws IOException {
		indexSuggestKeyword("message");
		indexSuggestKeyword("search");

		SuggestSearchRequest suggestSearchRequest = new SuggestSearchRequest(
			_INDEX_NAME);

		Suggester suggester = new TermSuggester(
			"termSuggestion", _LOCALIZED_FIELD_NAME, "searc");

		suggestSearchRequest.setSuggester(suggester);

		SuggestSearchResponse suggestSearchResponse =
			_searchEngineAdapter.execute(suggestSearchRequest);

		SuggestSearchResult suggestSearchResult =
			suggestSearchResponse.getSuggesterResult("termSuggestion");

		assertSuggestion(suggestSearchResult, "search");
	}

	protected void assertSuggestion(
		SuggestSearchResult suggestSearchResult, String expectedSuggestion) {

		List<SuggestSearchResult.Entry> suggestSearchResultEntries =
			suggestSearchResult.getEntries();

		Assert.assertEquals(
			"Expected 1 SuggestSearchResult.Entry", 1,
			suggestSearchResultEntries.size());

		SuggestSearchResult.Entry suggestSearchResultEntry =
			suggestSearchResultEntries.get(0);

		List<SuggestSearchResult.Entry.Option> suggestSearchResultEntryOptions =
			suggestSearchResultEntry.getOptions();

		Assert.assertEquals(
			"Expected 1 SuggestSearchResult.Entry.Option", 1,
			suggestSearchResultEntryOptions.size());

		SuggestSearchResult.Entry.Option suggestSearchResultEntryOption =
			suggestSearchResultEntryOptions.get(0);

		Assert.assertEquals(
			expectedSuggestion, suggestSearchResultEntryOption.getText());
	}

	protected void createIndex() {
		CreateIndexRequestBuilder createIndexRequestBuilder =
			_indicesAdminClient.prepareCreate(_INDEX_NAME);

		createIndexRequestBuilder.get();
	}

	protected SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				setSearchRequestExecutor(
					createSearchRequestExecutor(
						elasticsearchClientResolver));
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

	protected void deleteIndex() {
		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			_indicesAdminClient.prepareDelete(_INDEX_NAME);

		deleteIndexRequestBuilder.get();
	}

	protected GetResponse getDocument(String value) {
		GetRequestBuilder getRequestBuilder =
			GetAction.INSTANCE.newRequestBuilder(
				_elasticsearchFixture.getClient());

		getRequestBuilder.setId(getUID(value));
		getRequestBuilder.setIndex(_INDEX_NAME);

		return getRequestBuilder.get();
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

		IndexRequestBuilder indexRequestBuilder =
			IndexAction.INSTANCE.newRequestBuilder(
				_elasticsearchFixture.getClient());

		indexRequestBuilder.setRefreshPolicy(
			WriteRequest.RefreshPolicy.IMMEDIATE);

		indexRequestBuilder.setId(document.getUID());

		indexRequestBuilder.setIndex(_INDEX_NAME);
		indexRequestBuilder.setType(_MAPPING_NAME);

		ElasticsearchDocumentFactory elasticsearchDocumentFactory =
			new DefaultElasticsearchDocumentFactory();

		String elasticsearchDocument =
			elasticsearchDocumentFactory.getElasticsearchDocument(document);

		indexRequestBuilder.setSource(elasticsearchDocument, XContentType.JSON);

		indexRequestBuilder.get();

		GetResponse getResponse = getDocument(value);

		Assert.assertTrue(
			"Expected document added: " + value, getResponse.isExists());
	}

	protected void putMapping(String mappingName, String mappingSource) {
		PutMappingRequestBuilder putMappingRequestBuilder =
			_indicesAdminClient.preparePutMapping(_INDEX_NAME);

		putMappingRequestBuilder.setSource(mappingSource, XContentType.JSON);
		putMappingRequestBuilder.setType(mappingName);

		putMappingRequestBuilder.get();
	}

	private static final long _DEFAULT_COMPANY_ID = 12345;

	private static final String _EN_US_LANGUAGE_ID = "en_US";

	private static final String _INDEX_NAME = "test_request_index";

	private static final String _LOCALIZED_FIELD_NAME =
		"spellCheckKeyword_en_US";

	private static final String _MAPPING_NAME = "test_mapping";

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private ElasticsearchFixture _elasticsearchFixture;
	private IndicesAdminClient _indicesAdminClient;
	private SearchEngineAdapter _searchEngineAdapter;

}