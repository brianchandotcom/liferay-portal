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

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.search.elasticsearch7.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.MultiMatchQuery;
import com.liferay.portal.search.query.Operator;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class MultiMatchQueryTest extends BaseIndexingTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_indexUserDocuments();
	}

	@Test
	public void testMultiMatchQueryBoolPrefix() {
		MultiMatchQuery multiMatchQuery = queries.multiMatch(
			"delta", "firstName", "lastName");

		multiMatchQuery.setType(MultiMatchQuery.Type.BOOL_PREFIX);

		List<String> expected = Arrays.asList("userName4", "userName5");

		_assertSearch(expected, multiMatchQuery);
	}

	@Test
	public void testMultiMatchQueryCrossField() {
		MultiMatchQuery multiMatchQuery = queries.multiMatch(
			"bravo alpha", "firstName", "lastName");

		multiMatchQuery.setOperator(Operator.AND);
		multiMatchQuery.setType(MultiMatchQuery.Type.CROSS_FIELDS);

		List<String> expected = Arrays.asList("userName2");

		_assertSearch(expected, multiMatchQuery);
	}

	@Test
	public void testMultiMatchQueryDefault() {
		MultiMatchQuery multiMatchQuery = queries.multiMatch(
			"alpha", "firstName", "lastName");

		List<String> expected = Arrays.asList(
			"userName1", "userName2", "userName6");

		_assertSearch(expected, multiMatchQuery);
	}

	@Test
	public void testMultiMatchQueryPhrasePrefix() {
		MultiMatchQuery multiMatchQuery = queries.multiMatch(
			"bro", "firstName", "lastName");

		multiMatchQuery.setType(MultiMatchQuery.Type.PHRASE_PREFIX);

		List<String> expected = Arrays.asList("userName3");

		_assertSearch(expected, multiMatchQuery);
	}

	@Test
	public void testMultiMatchQueryTieBreaker() {
		MultiMatchQuery multiMatchQuery = queries.multiMatch(
			"delta", "firstName", "lastName");

		multiMatchQuery.setTieBreaker(Float.valueOf(0.3F));

		List<String> expected = Arrays.asList("userName4", "userName5");

		_assertSearch(expected, multiMatchQuery);
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	private void _assertSearch(
		List<String> expectedValues, MultiMatchQuery multiMatchQuery) {

		assertSearch(
			indexingTestHelper -> {
				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(
						new SearchSearchRequest() {
							{
								setIndexNames("_all");
								setQuery(multiMatchQuery);
								setSize(30);
							}
						});

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				List<SearchHit> searchHitsList = searchHits.getSearchHits();

				Hits hits = searchSearchResponse.getHits();

				DocumentsAssert.assertValuesIgnoreRelevance(
					"Retrieved hits ->", hits.getDocs(), Field.USER_NAME,
					expectedValues);

				Assert.assertEquals(
					"Retrieved hits", expectedValues.size(),
					searchHitsList.size());

				Assert.assertEquals(
					"Total hits", expectedValues.size(),
					searchHits.getTotalHits());
			});
	}

	private void _indexUserDocuments() {
		addDocument(
			document -> {
				document.addKeyword("firstName", "alpha");
				document.addKeyword("lastName", "omega");
				document.addKeyword(Field.USER_NAME, "userName1");
			});
		addDocument(
			document -> {
				document.addKeyword("firstName", "bravo");
				document.addKeyword("lastName", "alpha");
				document.addKeyword(Field.USER_NAME, "userName2");
			});
		addDocument(
			document -> {
				document.addKeyword("firstName", "bro charlie");
				document.addKeyword("lastName", "iota");
				document.addKeyword(Field.USER_NAME, "userName3");
			});
		addDocument(
			document -> {
				document.addKeyword("firstName", "delta");
				document.addKeyword("lastName", "omega");
				document.addKeyword(Field.USER_NAME, "userName4");
			});
		addDocument(
			document -> {
				document.addKeyword("firstName", "omega");
				document.addKeyword("lastName", "delta");
				document.addKeyword(Field.USER_NAME, "userName5");
			});
		addDocument(
			document -> {
				document.addKeyword("firstName", "alpha");
				document.addKeyword("lastName", "zeta");
				document.addKeyword(Field.USER_NAME, "userName6");
			});
	}

}