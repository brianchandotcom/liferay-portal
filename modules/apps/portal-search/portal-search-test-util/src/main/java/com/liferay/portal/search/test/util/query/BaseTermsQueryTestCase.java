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
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseTermsQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testTermsQuery() {
		addDocument(
			document -> {
				document.addKeyword(Field.UID, 1);
				document.addKeyword(Field.USER_NAME, "SomeUser1");
				document.addNumber(Field.PRIORITY, 1);
			});
		addDocument(
			document -> {
				document.addKeyword(Field.UID, 2);
				document.addKeyword(Field.USER_NAME, "SomeUser2");
				document.addNumber(Field.PRIORITY, 1);
			});
		addDocument(
			document -> {
				document.addKeyword(Field.UID, 3);
				document.addKeyword(Field.USER_NAME, "SomeUser3");
				document.addNumber(Field.PRIORITY, 1);
			});
		addDocument(
			document -> {
				document.addKeyword(Field.UID, 4);
				document.addKeyword(Field.USER_NAME, "SomeUser4");
				document.addNumber(Field.PRIORITY, 1);
			});
		addDocument(
			document -> {
				document.addKeyword(Field.UID, 5);
				document.addKeyword(Field.USER_NAME, "SomeUser5");
				document.addNumber(Field.PRIORITY, 1);
			});

		assertSearch(
			indexingTestHelper -> {
				TermsQuery termsQuery = queries.terms(Field.USER_NAME);

				termsQuery.addValues("SomeUser3", "SomeUser4");

				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("_all");
				searchSearchRequest.setQuery(termsQuery);

				FieldSort fieldSort = new FieldSort(Field.USER_NAME);

				fieldSort.setSortOrder(SortOrder.ASC);

				searchSearchRequest.addSorts(fieldSort);

				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(searchSearchRequest);

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				Assert.assertEquals("Total hits", 2, searchHits.getTotalHits());

				List<SearchHit> searchHitList = searchHits.getSearchHits();

				Assert.assertEquals("Retrieved hits", 2, searchHitList.size());

				SearchHit searchHit1 = searchHitList.get(0);

				Document document1 = searchHit1.getDocument();

				Assert.assertEquals(
					"SomeUser3", document1.getFieldValue(Field.USER_NAME));

				SearchHit searchHit2 = searchHitList.get(1);

				Document document2 = searchHit2.getDocument();

				Assert.assertEquals(
					"SomeUser4", document2.getFieldValue(Field.USER_NAME));
			});
	}

}