/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.synonym;

import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexResponse;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexResponse;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.connection.IndexName;
import com.liferay.portal.search.opensearch2.internal.document.SingleFieldFixture;
import com.liferay.portal.search.opensearch2.internal.query.QueryFactories;
import com.liferay.portal.search.opensearch2.internal.query.SearchAssert;
import com.liferay.portal.search.opensearch2.internal.util.ResourceUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.opensearch._types.query_dsl.MatchPhraseQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;

/**
 * @author Adam Brandizzi
 * @author Petteri Karttunen
 */
public class SynonymFiltersTest extends BaseOpenSearchTestCase {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_singleFieldFixture = new SingleFieldFixture(
			openSearchConnectionManager.getOpenSearchClient(),
			new IndexName(_SYNONYM_INDEX_NAME));

		_singleFieldFixture.setField(_FIELD_NAME);
		_singleFieldFixture.setQueryBuilderFactory(QueryFactories.MATCH);
	}

	@After
	public void tearDown() throws Exception {
		_deleteIndex();
	}

	@Test
	public void testSynonymFilterFailsWithSpaceInSynonymSetAndMatchPhraseQuery()
		throws Exception {

		_createIndex("filter-spaced");

		_singleFieldFixture.indexDocument("git hash");
		_singleFieldFixture.indexDocument("stable");

		_assertMatchPhraseQuerySearch("stable", "git hash");
	}

	@Test
	public void testSynonymFilterIgnoresQuoteInSearchString() throws Exception {
		_createIndex("filter-unquoted");

		_singleFieldFixture.indexDocument("\"stable\"");
		_singleFieldFixture.indexDocument("upstream");

		_singleFieldFixture.assertSearch(
			"\"stable\"", "\"stable\"", "upstream");
	}

	@Test
	public void testSynonymFilterIgnoresQuoteInSynonymSet() throws Exception {
		_createIndex("filter-quoted");

		_singleFieldFixture.indexDocument("\"stable\"");
		_singleFieldFixture.indexDocument("upstream");

		_singleFieldFixture.assertSearch("stable", "\"stable\"", "upstream");
	}

	@Test
	public void testSynonymFilterIgnoresSpaceInSearchString() throws Exception {
		_createIndex("filter-spaced");

		_singleFieldFixture.indexDocument("git hash");
		_singleFieldFixture.indexDocument("stable");

		_singleFieldFixture.assertSearch("git hash", "git hash", "stable");
	}

	@Test
	public void testSynonymFilterIgnoresSpaceInSynonymSet() throws Exception {
		_createIndex("filter-spaced");

		_singleFieldFixture.indexDocument("git hash");
		_singleFieldFixture.indexDocument("stable");

		_singleFieldFixture.assertSearch("stable", "git hash", "stable");
	}

	@Test
	public void testSynonymGraphFilterIgnoresQuoteInSearchString()
		throws Exception {

		_createIndex("graph-filter-unquoted");

		_singleFieldFixture.indexDocument("\"stable\"");
		_singleFieldFixture.indexDocument("upstream");

		_singleFieldFixture.assertSearch(
			"\"stable\"", "\"stable\"", "upstream");
	}

	@Test
	public void testSynonymGraphFilterIgnoresQuoteInSynonymSet()
		throws Exception {

		_createIndex("graph-filter-quoted");

		_singleFieldFixture.indexDocument("\"stable\"");
		_singleFieldFixture.indexDocument("upstream");

		_singleFieldFixture.assertSearch("stable", "\"stable\"", "upstream");
	}

	@Test
	public void testSynonymGraphFilterIgnoresSpaceInSearchString()
		throws Exception {

		_createIndex("graph-filter-spaced");

		_singleFieldFixture.indexDocument("git hash");
		_singleFieldFixture.indexDocument("stable");

		_singleFieldFixture.assertSearch("git hash", "git hash", "stable");
	}

	@Test
	public void testSynonymGraphFilterIgnoresSpaceInSynonymSet()
		throws Exception {

		_createIndex("graph-filter-spaced");

		_singleFieldFixture.indexDocument("git hash");
		_singleFieldFixture.indexDocument("stable");

		_singleFieldFixture.assertSearch("stable", "git hash", "stable");
	}

	@Test
	public void testSynonymGraphFilterWorksWithSpaceInSynonymSetAndMatchPhraseQuery()
		throws Exception {

		_createIndex("graph-filter-spaced");

		_singleFieldFixture.indexDocument("git hash");
		_singleFieldFixture.indexDocument("stable");

		_assertMatchPhraseQuerySearch("stable", "git hash", "stable");
	}

	private void _assertMatchPhraseQuerySearch(
			String text, String... expectedValues)
		throws Exception {

		MatchPhraseQuery.Builder builder = new MatchPhraseQuery.Builder();

		builder.field(_FIELD_NAME);
		builder.query(text);

		SearchAssert.assertSearch(
			openSearchConnectionManager.getOpenSearchClient(), _FIELD_NAME,
			new Query(builder.build()), expectedValues);
	}

	private void _createIndex(String suffix) {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			_SYNONYM_INDEX_NAME);

		createIndexRequest.setSource(_getSource(suffix));

		CreateIndexResponse createIndexResponse = searchEngineAdapter.execute(
			createIndexRequest);

		Assert.assertTrue(createIndexResponse.isAcknowledged());
	}

	private void _deleteIndex() {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			_SYNONYM_INDEX_NAME);

		DeleteIndexResponse deleteIndexResponse = searchEngineAdapter.execute(
			deleteIndexRequest);

		Assert.assertTrue(deleteIndexResponse.isAcknowledged());
	}

	private String _getSource(String suffix) {
		return ResourceUtil.getResourceAsString(
			getClass(),
			"dependencies/synonym-filters-test-synonym-" + suffix + ".json");
	}

	private static final String _FIELD_NAME = "content";

	private static final String _SYNONYM_INDEX_NAME = "test-synonyms";

	private static SingleFieldFixture _singleFieldFixture;

}