/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.query;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.WrapperQuery;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public abstract class BaseWrapperQueryTestCase extends BaseIndexingTestCase {

	@BeforeClass
	public static void setUpClassJSONUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testWrapperQuery() {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(_FIELD_NAME, value),
			"java eclipse", "java liferay", "java liferay eclipse",
			"C is the best language");

		assertSearch(
			"liferay uses java",
			Arrays.asList(
				"java liferay", "java liferay eclipse", "java eclipse"));
	}

	protected void assertSearch(Object value, List<String> expectedValues) {
		assertSearch(
			indexingTestHelper -> {
				WrapperQuery wrapperQuery = queries.wrapper(
					JSONUtil.put(
						"match", JSONUtil.put(_FIELD_NAME, value)
					).toString());

				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("_all");
				searchSearchRequest.setQuery(wrapperQuery);
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

	private static final String _FIELD_NAME = "content";

}