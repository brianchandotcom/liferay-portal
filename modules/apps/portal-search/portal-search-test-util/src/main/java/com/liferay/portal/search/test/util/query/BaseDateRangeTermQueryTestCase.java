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
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search2.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search2.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.DateRangeTermQuery;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
public abstract class BaseDateRangeTermQueryTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testDateRangeTermQuery() {
		addDocument(getDate("2016-02-01T00:00:00"));
		addDocument(getDate("2016-02-02T00:00:00"));
		addDocument(getDate("2017-02-02T00:00:00"));
		addDocument(getDate("2017-02-03T00:00:00"));
		addDocument(getDate("2018-02-03T00:00:00"));
		addDocument(getDate("2018-02-03T00:00:00"));
		addDocument(getDate("2019-02-05T00:00:00"));

		assertSearch(
			indexingTestHelper -> {
				DateRangeTermQuery dateRangeTermQuery = queries.dateRangeTerm(
					Field.EXPIRATION_DATE, true, true, "20170101", "20181231");

				dateRangeTermQuery.setDateFormat("yyyyMMdd");

				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("_all");
				searchSearchRequest.setQuery(dateRangeTermQuery);

				FieldSort fieldSort = new FieldSort(Field.EXPIRATION_DATE);

				fieldSort.setSortOrder(SortOrder.ASC);

				searchSearchRequest.addSorts(fieldSort);

				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(searchSearchRequest);

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				Assert.assertEquals("Total hits", 4, searchHits.getTotalHits());

				List<SearchHit> searchHitList = searchHits.getSearchHits();

				Assert.assertEquals("Retrieved hits", 4, searchHitList.size());

				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

				searchHitList.forEach(
					searchHit -> {
						Document document = searchHit.getDocument();

						String expirationDateString =
							(String)document.getFieldValue(
								Field.EXPIRATION_DATE);

						try {
							Date expirationDate = dateFormat.parse(
								expirationDateString);

							Assert.assertTrue(
								expirationDate.after(
									getDate("2016-12-31T23:59:59")));
							Assert.assertTrue(
								expirationDate.before(
									getDate("2018-12-31T23:59:59")));
						}
						catch (ParseException pe) {
							Assert.fail(StackTraceUtil.getStackTrace(pe));
						}
					});
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