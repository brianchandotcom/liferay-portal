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

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.MoreLikeThisQuery;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public abstract class BaseMoreLikeThisQueryTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testMoreLikeThisWithField() throws Exception {
		addDocuments("java eclipse", "java liferay", "java liferay eclipse");

		String[] queryFields = {_FIELD_NAME};
		String[] queryValues = {"java"};

		assertSearch(
			queryFields, queryValues, null,
			Arrays.asList(
				"java eclipse", "java liferay", "java liferay eclipse"));
	}

	@Test
	public void testMoreLikeThisWithFieldDocumentIdentifiers()
		throws Exception {

		addDocuments("java eclipse", "java liferay", "java liferay eclipse");

		String[] queryFields = {_FIELD_NAME};
		String[] queryValues = {"java"};

		MoreLikeThisQuery.DocumentIdentifier documentIdentifier =
			queries.documentIdentifier("_all", null, "1");

		MoreLikeThisQuery.DocumentIdentifier[] documentIdentifiers = {
			documentIdentifier
		};

		assertSearch(
			queryFields, queryValues, documentIdentifiers,
			Arrays.asList(
				"java eclipse", "java liferay", "java liferay eclipse"));
	}

	@Test
	public void testMoreLikeThisWithoutFieldAndDisalbedAll() throws Exception {
		addDocuments("java eclipse", "java liferay", "java liferay eclipse");

		String[] queryFields = {};
		String[] queryValues = {"java"};

		assertSearch(queryFields, queryValues, null, new ArrayList<>());
	}

	protected void addDocuments(String... values) throws Exception {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(_FIELD_NAME, value),
			Arrays.asList(values));
	}

	protected void assertSearch(
		String[] queryFields, String[] queryValues,
		MoreLikeThisQuery.DocumentIdentifier[] documentIdentifiers,
		List<String> expectedValues) {

		assertSearch(
			indexingTestHelper -> {
				MoreLikeThisQuery moreLikeThisQuery = queries.moreLikeThis(
					queryFields, queryValues);

				moreLikeThisQuery.setMinDocFrequency(Integer.valueOf(1));
				moreLikeThisQuery.setMinTermFrequency(Integer.valueOf(1));

				if (documentIdentifiers != null) {
					moreLikeThisQuery.addDocumentIdentifiers(
						documentIdentifiers);
				}

				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("_all");
				searchSearchRequest.setQuery(moreLikeThisQuery);
				searchSearchRequest.setSize(30);

				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(searchSearchRequest);

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				Assert.assertEquals(
					"Total hits", expectedValues.size(),
					searchHits.getTotalHits());

				List<SearchHit> searchHitsList = searchHits.getSearchHits();

				Assert.assertEquals(
					"Retrieved hits", expectedValues.size(),
					searchHitsList.size());

				List<String> actualValues = new ArrayList<>();

				searchHitsList.forEach(
					searchHit -> {
						Document document = searchHit.getDocument();

						actualValues.add(document.getString(_FIELD_NAME));
					});

				Assert.assertEquals(
					"Retrieved hits ->" + actualValues,
					expectedValues.toString(), actualValues.toString());
			});
	}

	private static final String _FIELD_NAME = "title";

}