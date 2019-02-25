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
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseIdsQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testIdsQuery() {
		index(1, "alpha");
		index(2, "bravo");
		index(3, "charlie");
		index(4, "delta");

		IdsQuery idsQuery = queries.ids();

		idsQuery.addIds("1", "4");

		FieldSort fieldSort = new FieldSort(Field.USER_NAME);

		fieldSort.setSortOrder(SortOrder.DESC);

		String expected = "[delta, alpha]";

		assertSearch(
			indexingTestHelper -> {
				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("_all");
				searchSearchRequest.setQuery(idsQuery);

				searchSearchRequest.addSorts(fieldSort);

				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(searchSearchRequest);

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				Stream<Document> stream = getDocumentsStream(searchHits);

				DocumentsAssert.assertValues(
					searchSearchResponse.getSearchRequestString(), stream,
					Field.USER_NAME, expected);
			});
	}

	protected Stream<Document> getDocumentsStream(SearchHits searchHits) {
		List<SearchHit> list = searchHits.getSearchHits();

		Stream<SearchHit> stream = list.stream();

		return stream.map(SearchHit::getDocument);
	}

	protected void index(int uid, String userName) {
		addDocument(
			document -> {
				document.addKeyword(Field.UID, uid);
				document.addKeyword(Field.USER_NAME, userName);
			});
	}

}