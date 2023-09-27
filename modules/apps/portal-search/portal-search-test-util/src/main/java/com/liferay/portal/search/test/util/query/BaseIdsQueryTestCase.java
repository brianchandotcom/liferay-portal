/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.query;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.List;
import java.util.function.Consumer;

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

		Sort sort = sorts.field(Field.USER_NAME, SortOrder.DESC);

		assertSearch(
			idsQuery, searchSearchRequest -> searchSearchRequest.addSorts(sort),
			"[delta, alpha]");
	}

	@Test
	public void testIdsQueryBoost() {
		index(1, "alpha");
		index(2, "bravo");
		index(3, "charlie");
		index(4, "delta");

		BooleanQuery booleanQuery = queries.booleanQuery();

		IdsQuery idsQuery = queries.ids();

		idsQuery.addIds("4");
		idsQuery.setBoost(1000F);

		TermQuery termQuery = queries.term(Field.USER_NAME, "alpha");

		termQuery.setBoost(500F);

		booleanQuery.addShouldQueryClauses(idsQuery, termQuery);

		assertSearch(booleanQuery, "[delta, alpha]");
	}

	protected void assertSearch(
		Query query, Consumer<SearchSearchRequest> consumer, String expected) {

		assertSearch(
			indexingTestHelper -> {
				SearchSearchRequest searchSearchRequest =
					getSearchSearchRequest(query);

				if (consumer != null) {
					consumer.accept(searchSearchRequest);
				}

				assertSearch(searchSearchRequest, expected);
			});
	}

	protected void assertSearch(Query query, String expected) {
		assertSearch(query, null, expected);
	}

	protected void assertSearch(
		SearchSearchRequest searchSearchRequest, String expected) {

		SearchEngineAdapter searchEngineAdapter = getSearchEngineAdapter();

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		DocumentsAssert.assertValues(
			searchSearchResponse.getSearchRequestString(),
			getDocuments(searchSearchResponse.getSearchHits()), Field.USER_NAME,
			expected);
	}

	protected List<Document> getDocuments(SearchHits searchHits) {
		return TransformUtil.transform(
			searchHits.getSearchHits(), SearchHit::getDocument);
	}

	protected SearchSearchRequest getSearchSearchRequest(Query query) {
		return new SearchSearchRequest() {
			{
				setIndexNames("_all");
				setQuery(query);
			}
		};
	}

	protected void index(int uid, String userName) {
		addDocument(
			document -> {
				document.addKeyword(Field.UID, uid);
				document.addKeyword(Field.USER_NAME, userName);
			});
	}

}