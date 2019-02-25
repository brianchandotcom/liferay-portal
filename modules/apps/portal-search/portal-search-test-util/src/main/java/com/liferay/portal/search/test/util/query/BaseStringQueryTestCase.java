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

package com.liferay.portal.search.test.util.query;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.StringQuery;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseStringQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testBooleanOperatorAnd() throws Exception {
		addDocuments("java eclipse", "java liferay", "java liferay eclipse");

		assertSearch(
			"java AND eclipse",
			Arrays.asList("java eclipse", "java liferay eclipse"));
		assertSearch(
			"eclipse AND liferay", Arrays.asList("java liferay eclipse"));
	}

	@Test
	public void testBooleanOperatorAndWithNot() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("alpha AND NOT bravo", Arrays.asList("alpha charlie"));
	}

	@Test
	public void testBooleanOperatorNot() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("NOT alpha", Arrays.asList("charlie delta"));
		assertSearch(
			"NOT bravo", Arrays.asList("alpha charlie", "charlie delta"));
	}

	@Test
	public void testBooleanOperatorNotDeep() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch(
			"+(*:* NOT alpha) +charlie", Arrays.asList("charlie delta"));
	}

	@Test
	public void testBooleanOperatorOr() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch(
			"alpha OR charlie",
			Arrays.asList("alpha charlie", "alpha bravo", "charlie delta"));
		assertSearch(
			"alpha OR delta",
			Arrays.asList("charlie delta", "alpha bravo", "alpha charlie"));
		assertSearch(
			"bravo OR delta", Arrays.asList("alpha bravo", "charlie delta"));
	}

	@Test
	public void testBooleanOperatorOrWithNot() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("alpha OR NOT bravo", Arrays.asList("alpha charlie"));
	}

	@Test
	public void testField() throws Exception {
		addDocuments("java", "eclipse", "liferay");

		assertSearch(
			"title:(java OR eclipse)", Arrays.asList("java", "eclipse"));
		assertSearch("description:(java OR eclipse)", Collections.emptyList());
	}

	@Test
	public void testPrefixOperatorMust() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("+alpha", Arrays.asList("alpha bravo", "alpha charlie"));
		assertSearch(
			"+alpha bravo", Arrays.asList("alpha bravo", "alpha charlie"));
		assertSearch("+alpha +bravo", Arrays.asList("alpha bravo"));
		assertSearch(
			"+alpha delta", Arrays.asList("alpha bravo", "alpha charlie"));
		assertSearch("+alpha +delta", Arrays.asList());
	}

	@Test
	public void testPrefixOperatorMustNot() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("-alpha", Arrays.asList("charlie delta"));
		assertSearch("-alpha bravo", Arrays.asList());
		assertSearch("-alpha -bravo", Arrays.asList("charlie delta"));
		assertSearch("-alpha delta", Arrays.asList("charlie delta"));
		assertSearch("-alpha -delta", Arrays.asList());
	}

	@Test
	public void testPrefixOperatorMustNotWithBooleanOperatorOr()
		throws Exception {

		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("(-bravo OR alpha)", Arrays.asList("alpha charlie"));
		assertSearch("-(bravo OR alpha)", Arrays.asList("charlie delta"));
		assertSearch("-(bravo) OR (alpha)", Arrays.asList("alpha charlie"));
		assertSearch("-(bravo) OR alpha", Arrays.asList("alpha charlie"));
		assertSearch("-bravo OR (alpha)", Arrays.asList("alpha charlie"));
		assertSearch("-bravo OR alpha", Arrays.asList("alpha charlie"));
	}

	@Test
	public void testStringQuery() {
		for (int i = 0; i < 10; i++) {
			addDocument(
				DocumentCreationHelpers.singleKeyword(
					Field.USER_NAME, "SomeUser" + i));
			addDocument(
				DocumentCreationHelpers.singleKeyword(
					Field.USER_NAME, "OtherUser" + i));
			addDocument(
				DocumentCreationHelpers.singleKeyword(
					Field.USER_NAME, "Other" + i));
		}

		assertSearch(
			indexingTestHelper -> {
				StringQuery stringQuery = queries.string(
					"SomeUser* OR OtherUser* ");

				stringQuery.setDefaultField(Field.USER_NAME);

				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("_all");
				searchSearchRequest.setQuery(stringQuery);
				searchSearchRequest.setSize(30);

				FieldSort fieldSort = new FieldSort(Field.USER_NAME);

				fieldSort.setSortOrder(SortOrder.ASC);

				searchSearchRequest.addSorts(fieldSort);

				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(searchSearchRequest);

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				Assert.assertEquals(
					"Total hits", 20, searchHits.getTotalHits());

				List<SearchHit> searchHitList = searchHits.getSearchHits();

				Assert.assertEquals("Retrieved hits", 20, searchHitList.size());

				searchHitList.forEach(
					searchHit -> {
						Document document = searchHit.getDocument();

						String userName = (String)document.getFieldValue(
							Field.USER_NAME);

						Assert.assertTrue(
							userName.startsWith("OtherUser") ||
							userName.startsWith("SomeUser"));
					});
			});
	}

	protected void addDocuments(String... values) throws Exception {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(_FIELD_NAME, value),
			Arrays.asList(values));
	}

	protected void assertSearch(
		String queryString, List<String> expectedValues) {

		assertSearch(
			indexingTestHelper -> {
				StringQuery stringQuery = queries.string(queryString);

				stringQuery.setDefaultField(_FIELD_NAME);

				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("_all");
				searchSearchRequest.setQuery(stringQuery);
				searchSearchRequest.setSize(30);

				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(searchSearchRequest);

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				Assert.assertEquals(
					"Total hits", expectedValues.size(),
					searchHits.getTotalHits());

				List<SearchHit> searchHitList = searchHits.getSearchHits();

				Assert.assertEquals(
					"Retrieved hits", expectedValues.size(),
					searchHitList.size());

				List<String> actualValues = new ArrayList<>();

				searchHitList.forEach(
					searchHit -> {
						Document document = searchHit.getDocument();

						actualValues.add(
							(String)document.getFieldValue(_FIELD_NAME));
					});

				Assert.assertEquals(
					"Retrieved hits ->" + actualValues,
					expectedValues.toString(), actualValues.toString());
			});
	}

	private static final String _FIELD_NAME = "title";

}