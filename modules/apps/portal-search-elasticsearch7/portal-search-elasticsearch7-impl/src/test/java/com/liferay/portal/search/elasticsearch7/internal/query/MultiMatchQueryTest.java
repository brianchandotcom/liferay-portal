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

import com.liferay.portal.kernel.search.Document;
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
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
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

	@Test
	public void testMultiMatchQueryBoolPrefix() {
		_indexUserDocuments("delta", "omega", "userName4");

		_indexUserDocuments("omega", "delta", "userName5");

		MultiMatchQuery multiMatchQuery = queries.multiMatch(
			"delta", "firstName", "lastName");

		multiMatchQuery.setType(MultiMatchQuery.Type.BOOL_PREFIX);

		List<String> expected = Arrays.asList("userName4", "userName5");

		_assertSearch(expected, multiMatchQuery);
	}

	@Test
	public void testMultiMatchQueryCrossField() {
		_indexUserDocuments("bravo", "alpha", "userName2");

		MultiMatchQuery multiMatchQuery = queries.multiMatch(
			"bravo alpha", "firstName", "lastName");

		multiMatchQuery.setOperator(Operator.AND);
		multiMatchQuery.setType(MultiMatchQuery.Type.CROSS_FIELDS);

		List<String> expected = Arrays.asList("userName2");

		_assertSearch(expected, multiMatchQuery);
	}

	@Test
	public void testMultiMatchQueryDefault() {
		_indexUserDocuments("alpha", "omega", "userName1");

		_indexUserDocuments("bravo", "alpha", "userName2");

		_indexUserDocuments("alpha", "zeta", "userName6");

		MultiMatchQuery multiMatchQuery = queries.multiMatch(
			"alpha", "firstName", "lastName");

		List<String> expected = Arrays.asList(
			"userName1", "userName2", "userName6");

		_assertSearch(expected, multiMatchQuery);
	}

	@Test
	public void testMultiMatchQueryPhrasePrefix() {
		_indexUserDocuments("bro charlie", "iota", "userName3");

		MultiMatchQuery multiMatchQuery = queries.multiMatch(
			"bro", "firstName", "lastName");

		multiMatchQuery.setType(MultiMatchQuery.Type.PHRASE_PREFIX);

		List<String> expected = Arrays.asList("userName3");

		_assertSearch(expected, multiMatchQuery);
	}

	@Test
	public void testMultiMatchQueryTieBreaker() {
		_indexUserDocuments("delta", "omega", "userName4");

		_indexUserDocuments("omega", "delta", "userName5");

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

	private Document _createDocument(
		String firstName, String lastName, String userName) {

		Document document = DocumentFixture.newDocument(
			getCompanyId(), getGroupId(), getEntryClassName());

		document.addKeyword("firstName", firstName);
		document.addKeyword("lastName", lastName);
		document.addKeyword(Field.USER_NAME, userName);

		return document;
	}

	private void _indexUserDocuments(
		String firstName, String lastName, String userName) {

		Document document = _createDocument(firstName, lastName, userName);

		addDocument(document);

		_documents.add(document);
	}

	private final List<Document> _documents = new ArrayList<>();

}