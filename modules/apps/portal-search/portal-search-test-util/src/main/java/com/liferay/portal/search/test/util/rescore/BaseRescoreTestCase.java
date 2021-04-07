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

package com.liferay.portal.search.test.util.rescore;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.rescore.Rescore;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public abstract class BaseRescoreTestCase extends BaseIndexingTestCase {

	@Test
	public void testRescore() {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(_TITLE, value),
			Arrays.asList("alpha zeta", "alpha alpha", "alpha beta beta"));

		Query query = queries.string(_TITLE.concat(":alpha"));

		assertSearch(
			_TITLE, query, (List<Rescore>)null,
			Arrays.asList("alpha alpha", "alpha zeta", "alpha beta beta"));

		assertSearch(
			_TITLE, query, Arrays.asList(buildRescore(_TITLE, "beta")),
			Arrays.asList("alpha beta beta", "alpha alpha", "alpha zeta"));
	}

	@Test
	public void testRescoreQuery() {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(_TITLE, value),
			Arrays.asList(
				"alpha alpha", "alpha gamma gamma", "alpha beta beta"));

		Query query = queries.string(_TITLE.concat(":alpha"));

		assertSearch(
			_TITLE, query, (Query)null,
			Arrays.asList(
				"alpha alpha", "alpha gamma gamma", "alpha beta beta"));

		Query rescoreQuery = queries.match(_TITLE, "beta");

		assertSearch(
			_TITLE, query, rescoreQuery,
			Arrays.asList(
				"alpha beta beta", "alpha alpha", "alpha gamma gamma"));
	}

	@Test
	public void testRescores() {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(_TITLE, value),
			Arrays.asList(
				"alpha alpha", "alpha gamma gamma", "alpha beta beta beta"));

		Query query = queries.string(_TITLE.concat(":alpha"));

		assertSearch(
			_TITLE, query, (List<Rescore>)null,
			Arrays.asList(
				"alpha alpha", "alpha gamma gamma", "alpha beta beta beta"));

		assertSearch(
			_TITLE, query,
			Arrays.asList(
				buildRescore(_TITLE, "beta"), buildRescore(_TITLE, "gamma")),
			Arrays.asList(
				"alpha beta beta beta", "alpha gamma gamma", "alpha alpha"));
	}

	protected void assertSearch(
		String fieldName, Query query, List<Rescore> rescores,
		List<String> expectedValues) {

		assertSearch(
			indexingTestHelper -> {
				SearchSearchRequest searchSearchRequest =
					getSearchSearchRequest(query);

				searchSearchRequest.setRescores(rescores);

				List<String> actualValues = getFieldValues(
					searchSearchRequest, fieldName);

				Assert.assertEquals(
					expectedValues.toString(), actualValues.toString());
			});
	}

	protected void assertSearch(
		String fieldName, Query query, Query rescoreQuery,
		List<String> expectedValues) {

		assertSearch(
			indexingTestHelper -> {
				SearchSearchRequest searchSearchRequest =
					getSearchSearchRequest(query);

				searchSearchRequest.setRescoreQuery(rescoreQuery);

				List<String> actualValues = getFieldValues(
					searchSearchRequest, fieldName);

				Assert.assertEquals(
					expectedValues.toString(), actualValues.toString());
			});
	}

	protected Rescore buildRescore(String fieldName, String value) {
		return rescoreBuilderFactory.builder(
			queries.match(fieldName, value)
		).windowSize(
			100
		).build();
	}

	protected String getFieldValue(String fieldName, SearchHit searchHit) {
		Document document = searchHit.getDocument();

		Map<String, Field> fields = document.getFields();

		Field field = fields.get(fieldName);

		return (String)field.getValue();
	}

	protected List<String> getFieldValues(
		SearchSearchRequest searchSearchRequest, String fieldName) {

		SearchEngineAdapter searchEngineAdapter = getSearchEngineAdapter();

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		Stream<SearchHit> stream = searchHitsList.stream();

		return stream.map(
			searchHit -> getFieldValue(fieldName, searchHit)
		).collect(
			Collectors.toList()
		);
	}

	protected SearchSearchRequest getSearchSearchRequest(Query query) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("_all");
		searchSearchRequest.setQuery(query);
		searchSearchRequest.setSize(30);

		return searchSearchRequest;
	}

	private static final String _TITLE = "title";

}