/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.rescore;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.rescore.Rescore;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 * @author Wade Cao
 */
public abstract class BaseRescoreTestCase extends BaseIndexingTestCase {

	@Test
	public void testRescore() {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(_TITLE, value),
			"alpha zeta", "alpha alpha", "alpha beta beta");

		Query query = queries.string(_TITLE.concat(":alpha"));

		assertSearch(
			Arrays.asList("alpha alpha", "alpha zeta", "alpha beta beta"),
			_TITLE, query, null, null);

		assertSearch(
			Arrays.asList("alpha beta beta", "alpha alpha", "alpha zeta"),
			_TITLE, query, null, Arrays.asList(buildRescore(_TITLE, "beta")));
	}

	@Test
	public void testRescoreQuery() {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(_TITLE, value),
			"alpha alpha", "alpha gamma gamma", "alpha beta beta");

		Query query = queries.string(_TITLE.concat(":alpha"));

		assertSearch(
			Arrays.asList(
				"alpha alpha", "alpha gamma gamma", "alpha beta beta"),
			_TITLE, query, null, null);

		assertSearch(
			Arrays.asList(
				"alpha beta beta", "alpha alpha", "alpha gamma gamma"),
			_TITLE, query, queries.match(_TITLE, "beta"), null);
	}

	@Test
	public void testRescores() {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(_TITLE, value),
			"alpha alpha", "alpha gamma gamma", "alpha beta beta beta");

		Query query = queries.string(_TITLE.concat(":alpha"));

		assertSearch(
			Arrays.asList(
				"alpha alpha", "alpha gamma gamma", "alpha beta beta beta"),
			_TITLE, query, null, null);

		assertSearch(
			Arrays.asList(
				"alpha beta beta beta", "alpha gamma gamma", "alpha alpha"),
			_TITLE, query, null,
			Arrays.asList(
				buildRescore(_TITLE, "beta"), buildRescore(_TITLE, "gamma")));
	}

	protected void assertSearch(
		List<String> expectedValues, String fieldName, Query query,
		Query rescoreQuery, List<Rescore> rescores) {

		assertSearch(
			indexingTestHelper -> {
				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames(
					String.valueOf(getCompanyId()));
				searchSearchRequest.setQuery(query);
				searchSearchRequest.setRescoreQuery(rescoreQuery);
				searchSearchRequest.setRescores(rescores);
				searchSearchRequest.setSize(30);

				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(searchSearchRequest);

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				Assert.assertEquals(
					expectedValues.toString(),
					String.valueOf(
						TransformUtil.transform(
							searchHits.getSearchHits(),
							searchHit -> {
								Document document = searchHit.getDocument();

								Map<String, Field> fields =
									document.getFields();

								Field field = fields.get(fieldName);

								return (String)field.getValue();
							})));
			});
	}

	protected Rescore buildRescore(String fieldName, String value) {
		return rescoreBuilderFactory.builder(
			queries.match(fieldName, value)
		).windowSize(
			100
		).build();
	}

	private static final String _TITLE = "title";

}