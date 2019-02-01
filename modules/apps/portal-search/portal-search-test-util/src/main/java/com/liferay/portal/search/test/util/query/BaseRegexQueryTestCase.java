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
import com.liferay.portal.search.query.RegexQuery;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseRegexQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testRegexQuery() {
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

		for (int i = 15; i < 20; i++) {
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
				RegexQuery regexQuery = queries.regex(
					Field.USER_NAME, "OtherUser<0-9>");

				regexQuery.setRegexFlags(RegexQuery.RegexFlag.INTERVAL);

				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("_all");
				searchSearchRequest.setQuery(regexQuery);
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
					"Total hits", 10, searchHits.getTotalHits());

				List<SearchHit> searchHitList = searchHits.getSearchHits();

				Assert.assertEquals("Retrieved hits", 10, searchHitList.size());

				searchHitList.forEach(
					searchHit -> {
						Document document = searchHit.getDocument();

						String userName = (String)document.getFieldValue(
							Field.USER_NAME);

						Assert.assertTrue(userName.startsWith("OtherUser"));
					});
			});
	}

}