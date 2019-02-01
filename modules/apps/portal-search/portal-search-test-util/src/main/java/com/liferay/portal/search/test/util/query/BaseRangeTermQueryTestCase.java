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
import com.liferay.portal.search.query.RangeTermQuery;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseRangeTermQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testRangeTermQuery() {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		assertSearch(
			indexingTestHelper -> {
				RangeTermQuery rangeTermQuery = queries.rangeTerm(
					Field.PRIORITY, true, false);

				rangeTermQuery.setLowerBound(5);
				rangeTermQuery.setUpperBound(15);

				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("_all");
				searchSearchRequest.setQuery(rangeTermQuery);
				searchSearchRequest.setSize(15);

				FieldSort fieldSort = new FieldSort(Field.PRIORITY);

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

				int expectedValue = 5;

				for (SearchHit searchHit : searchHitList) {
					Document document = searchHit.getDocument();

					Assert.assertEquals(
						"Priority value", expectedValue,
						(Double)document.getFieldValue(Field.PRIORITY), 0);

					expectedValue++;
				}
			});
	}

	protected void addDocument(Date date) {
		addDocument(
			DocumentCreationHelpers.singleDate(Field.EXPIRATION_DATE, date));
	}

	protected Date getDate(String date) {
		LocalDateTime localDateTime = LocalDateTime.parse(date);

		ZonedDateTime zonedDateTime = ZonedDateTime.of(
			localDateTime, ZoneId.systemDefault());

		return Date.from(zonedDateTime.toInstant());
	}

}