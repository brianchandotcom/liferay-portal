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
import com.liferay.portal.search.engine.adapter.search2.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search2.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.ExistsQuery;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseExistsQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testExistsQuery() {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "SomeUser1");
				document.addNumber(Field.PRIORITY, 1);
			});

		assertSearch(
			indexingTestHelper -> {
				ExistsQuery existsQuery = queries.exists(Field.USER_NAME);

				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("_all");
				searchSearchRequest.setQuery(existsQuery);
				searchSearchRequest.setSize(30);

				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(searchSearchRequest);

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				Assert.assertEquals("Total hits", 1, searchHits.getTotalHits());

				List<SearchHit> searchHitList = searchHits.getSearchHits();

				Assert.assertEquals("Retrieved hits", 1, searchHitList.size());

				SearchHit searchHit = searchHitList.get(0);

				Document document = searchHit.getDocument();

				Assert.assertEquals(
					"Priority value", 1.0,
					(Double)document.getFieldValue(Field.PRIORITY), 0);

				Assert.assertEquals(
					"SomeUser1", document.getFieldValue(Field.USER_NAME));

			});
	}

}