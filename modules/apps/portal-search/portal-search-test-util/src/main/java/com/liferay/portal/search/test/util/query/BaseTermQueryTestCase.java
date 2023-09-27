/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.query;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseTermQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testTermQuery() {
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
				TermQuery termQuery = queries.term(
					Field.USER_NAME, "SomeUser5");

				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("_all");
				searchSearchRequest.setQuery(termQuery);
				searchSearchRequest.setSize(30);

				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(searchSearchRequest);

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				Assert.assertEquals("Total hits", 1, searchHits.getTotalHits());

				List<SearchHit> searchHitsList = searchHits.getSearchHits();

				Assert.assertEquals("Retrieved hits", 1, searchHitsList.size());

				SearchHit searchHit = searchHitsList.get(0);

				Document document = searchHit.getDocument();

				Assert.assertEquals(
					"SomeUser5", document.getString(Field.USER_NAME));
			});
	}

}