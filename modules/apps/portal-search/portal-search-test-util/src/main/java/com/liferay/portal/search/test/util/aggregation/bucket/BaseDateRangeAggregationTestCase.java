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

package com.liferay.portal.search.test.util.aggregation.bucket;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.test.util.aggregation.AggregationAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Date;

import org.junit.Test;

/**
 * @author Inácio Nery
 */
public abstract class BaseDateRangeAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testDateRanges() throws Exception {
		addDocument(getDate("2016-02-01T00:00:00"));
		addDocument(getDate("2016-02-02T00:00:00"));
		addDocument(getDate("2017-02-02T00:00:00"));
		addDocument(getDate("2017-02-03T00:00:00"));
		addDocument(getDate("2018-02-03T00:00:00"));
		addDocument(getDate("2018-02-03T00:00:00"));
		addDocument(getDate("2019-02-05T00:00:00"));

		DateRangeAggregation dateRangeAggregation = getAggregation("yyyyMMdd");

		dateRangeAggregation.addRange("Before 2017", "20160101", "20161231");
		dateRangeAggregation.addRange("2017", "20170101", "20171231");
		dateRangeAggregation.addRange("After 2017", "20180101", "20191231");

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						dateRangeAggregation));

				indexingTestHelper.search();

				AggregationAssert.assertBuckets(
					"[Before 2017=2, 2017=2, After 2017=3]",
					indexingTestHelper.getAggregationResult(
						dateRangeAggregation));
			});
	}

	@Test
	public void testDateRangesWithKeys() throws Exception {
		addDocument(getDate("2016-02-01T00:00:00"));
		addDocument(getDate("2016-02-02T00:00:00"));
		addDocument(getDate("2017-02-02T00:00:00"));
		addDocument(getDate("2017-02-03T00:00:00"));
		addDocument(getDate("2018-02-03T00:00:00"));
		addDocument(getDate("2018-02-03T00:00:00"));
		addDocument(getDate("2019-02-05T00:00:00"));

		DateRangeAggregation dateRangeAggregation = getAggregation("yyyyMMdd");

		dateRangeAggregation.setKeyed(true);

		dateRangeAggregation.addRange("20160101", "20161231");
		dateRangeAggregation.addRange("20170101", "20171231");
		dateRangeAggregation.addRange("20180101", null);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						dateRangeAggregation));

				indexingTestHelper.search();

				AggregationAssert.assertBuckets(
					"[20160101-20161231=2, 20170101-20171231=2, 20180101-*=3]",
					indexingTestHelper.getAggregationResult(
						dateRangeAggregation));
			});
	}

	protected void addDocument(Date date) throws Exception {
		addDocument(DocumentCreationHelpers.singleDate(_FIELD, date));
	}

	protected DateRangeAggregation getAggregation(String format) {
		DateRangeAggregation dateRangeAggregation = aggregations.dateRange(
			"date_range", _FIELD);

		dateRangeAggregation.setFormat(format);

		return dateRangeAggregation;
	}

	protected Date getDate(String date) {
		LocalDateTime localDateTime = LocalDateTime.parse(date);

		ZonedDateTime zonedDateTime = ZonedDateTime.of(
			localDateTime, ZoneId.systemDefault());

		return Date.from(zonedDateTime.toInstant());
	}

	private static final String _FIELD = Field.EXPIRATION_DATE;

}