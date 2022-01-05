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

package com.liferay.portal.search.elasticsearch7.internal.synonym;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch7.internal.document.SingleFieldFixture;
import com.liferay.portal.search.elasticsearch7.internal.index.constants.LiferayTypeMappingsConstants;
import com.liferay.portal.search.elasticsearch7.internal.query.QueryBuilderFactories;
import com.liferay.portal.search.elasticsearch7.internal.query.SearchAssert;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.ElasticsearchSearchEngineAdapterImpl;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.ElasticsearchSearchEngineAdapterIndexRequestTest;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.IndexRequestExecutorFixture;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexResponse;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexResponse;
import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.elasticsearch.index.query.MatchPhraseQueryBuilder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class SynonymFiltersTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchSearchEngineAdapterIndexRequestTest.class.
				getSimpleName());

		_elasticsearchFixture.setUp();

		_searchEngineAdapter = _createSearchEngineAdapter(
			_elasticsearchFixture);

		_singleFieldFixture = new SingleFieldFixture(
			_elasticsearchFixture.getRestHighLevelClient(),
			new IndexName(_INDEX_NAME),
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE);

		_singleFieldFixture.setField(_FIELD_NAME);
		_singleFieldFixture.setQueryBuilderFactory(QueryBuilderFactories.MATCH);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@After
	public void tearDown() throws Exception {
		_deleteIndex();
	}

	@Test
	public void testSynonymFilterFailsWithSpaceInSynonymSetAndMatchPhraseQuery()
		throws Exception {

		_createIndex("synonym-filter-spaced");

		_singleFieldFixture.indexDocument("git hash");
		_singleFieldFixture.indexDocument("stable");

		_assertMatchPhraseQuerySearch("stable", "git hash");
	}

	@Test
	public void testSynonymFilterIgnoresQuoteInSearchString() throws Exception {
		_createIndex("synonym-filter-unquoted");

		_singleFieldFixture.indexDocument("\"stable\"");
		_singleFieldFixture.indexDocument("upstream");

		_singleFieldFixture.assertSearch(
			"\"stable\"", "\"stable\"", "upstream");
	}

	@Test
	public void testSynonymFilterIgnoresQuoteInSynonymSet() throws Exception {
		_createIndex("synonym-filter-quoted");

		_singleFieldFixture.indexDocument("\"stable\"");
		_singleFieldFixture.indexDocument("upstream");

		_singleFieldFixture.assertSearch("stable", "\"stable\"", "upstream");
	}

	@Test
	public void testSynonymFilterIgnoresSpaceInSearchString() throws Exception {
		_createIndex("synonym-filter-spaced");

		_singleFieldFixture.indexDocument("git hash");
		_singleFieldFixture.indexDocument("stable");

		_singleFieldFixture.assertSearch("git hash", "git hash", "stable");
	}

	@Test
	public void testSynonymFilterIgnoresSpaceInSynonymSet() throws Exception {
		_createIndex("synonym-filter-spaced");

		_singleFieldFixture.indexDocument("git hash");
		_singleFieldFixture.indexDocument("stable");

		_singleFieldFixture.assertSearch("stable", "git hash", "stable");
	}

	@Test
	public void testSynonymGraphFilterIgnoresQuoteInSearchString()
		throws Exception {

		_createIndex("synonym-graph-filter-unquoted");

		_singleFieldFixture.indexDocument("\"stable\"");
		_singleFieldFixture.indexDocument("upstream");

		_singleFieldFixture.assertSearch(
			"\"stable\"", "\"stable\"", "upstream");
	}

	@Test
	public void testSynonymGraphFilterIgnoresQuoteInSynonymSet()
		throws Exception {

		_createIndex("synonym-graph-filter-quoted");

		_singleFieldFixture.indexDocument("\"stable\"");
		_singleFieldFixture.indexDocument("upstream");

		_singleFieldFixture.assertSearch("stable", "\"stable\"", "upstream");
	}

	@Test
	public void testSynonymGraphFilterIgnoresSpaceInSearchString()
		throws Exception {

		_createIndex("synonym-graph-filter-spaced");

		_singleFieldFixture.indexDocument("git hash");
		_singleFieldFixture.indexDocument("stable");

		_singleFieldFixture.assertSearch("git hash", "git hash", "stable");
	}

	@Test
	public void testSynonymGraphFilterIgnoresSpaceInSynonymSet()
		throws Exception {

		_createIndex("synonym-graph-filter-spaced");

		_singleFieldFixture.indexDocument("git hash");
		_singleFieldFixture.indexDocument("stable");

		_singleFieldFixture.assertSearch("stable", "git hash", "stable");
	}

	@Test
	public void testSynonymGraphFilterWorksWithSpaceInSynonymSetAndMatchPhraseQuery()
		throws Exception {

		_createIndex("synonym-graph-filter-spaced");

		_singleFieldFixture.indexDocument("git hash");
		_singleFieldFixture.indexDocument("stable");

		_assertMatchPhraseQuerySearch("stable", "git hash", "stable");
	}

	private static IndexRequestExecutor _createIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		IndexRequestExecutorFixture indexRequestExecutorFixture =
			new IndexRequestExecutorFixture() {
				{
					setElasticsearchClientResolver(elasticsearchClientResolver);
				}
			};

		indexRequestExecutorFixture.setUp();

		return indexRequestExecutorFixture.getIndexRequestExecutor();
	}

	private static SearchEngineAdapter _createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				setIndexRequestExecutor(
					_createIndexRequestExecutor(elasticsearchClientResolver));
			}
		};
	}

	private void _assertMatchPhraseQuerySearch(
			String text, String... expectedValues)
		throws Exception {

		MatchPhraseQueryBuilder matchPhraseQueryBuilder =
			new MatchPhraseQueryBuilder(_FIELD_NAME, text);

		SearchAssert.assertSearch(
			_elasticsearchFixture.getRestHighLevelClient(), _FIELD_NAME,
			matchPhraseQueryBuilder, expectedValues);
	}

	private void _createIndex(String suffix) {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			_INDEX_NAME);

		createIndexRequest.setSource(_getSource(suffix));

		CreateIndexResponse createIndexResponse = _searchEngineAdapter.execute(
			createIndexRequest);

		Assert.assertTrue(createIndexResponse.isAcknowledged());
	}

	private void _deleteIndex() {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			_INDEX_NAME);

		DeleteIndexResponse deleteIndexResponse = _searchEngineAdapter.execute(
			deleteIndexRequest);

		Assert.assertTrue(deleteIndexResponse.isAcknowledged());
	}

	private String _getSource(String suffix) {
		return ResourceUtil.getResourceAsString(
			getClass(),
			"dependencies/synonym-filters-test-" + suffix + ".json");
	}

	private static final String _FIELD_NAME = "content";

	private static final String _INDEX_NAME = "test_synonyms";

	private static ElasticsearchFixture _elasticsearchFixture;
	private static SearchEngineAdapter _searchEngineAdapter;
	private static SingleFieldFixture _singleFieldFixture;

}