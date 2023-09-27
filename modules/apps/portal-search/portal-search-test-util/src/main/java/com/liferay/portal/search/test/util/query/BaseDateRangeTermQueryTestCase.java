/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.query;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.DateRangeTermQuery;
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

		DateRangeTermQuery dateRangeTermQuery = queries.dateRangeTerm(
			Field.EXPIRATION_DATE, true, true, "20170101", "20181231");

		dateRangeTermQuery.setDateFormat("yyyyMMdd");

		assertSearch(
			indexingTestHelper -> {
				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(
						new SearchSearchRequest() {
							{
								addSorts(sorts.field(Field.EXPIRATION_DATE));
								setIndexNames("_all");
								setQuery(dateRangeTermQuery);
							}
						});

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				Assert.assertEquals("Total hits", 4, searchHits.getTotalHits());

				List<SearchHit> searchHitsList = searchHits.getSearchHits();

				Assert.assertEquals("Retrieved hits", 4, searchHitsList.size());

				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

				searchHitsList.forEach(
					searchHit -> {
						Document document = searchHit.getDocument();

						String expirationDateString = document.getDate(
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
						catch (ParseException parseException) {
							Assert.fail(
								StackTraceUtil.getStackTrace(parseException));
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