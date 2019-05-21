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
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseDateHistogramAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testDateHistogramAggregationIntervalDay() throws Exception {
		addDocument(getDate("2017-02-01T00:00:00"));
		addDocument(getDate("2017-02-02T00:00:00"));
		addDocument(getDate("2017-02-02T00:00:00"));
		addDocument(getDate("2017-02-03T00:00:00"));
		addDocument(getDate("2017-02-03T00:00:00"));
		addDocument(getDate("2017-02-03T00:00:00"));
		addDocument(getDate("2017-02-05T00:00:00"));

		DateHistogramAggregation dateHistogramAggregation = getAggregation(
			"1d");

		dateHistogramAggregation.setMinDocCount(1L);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						dateHistogramAggregation));

				indexingTestHelper.search();

				DateHistogramAggregationResult dateHistogramAggregationResult =
					indexingTestHelper.getAggregationResult(
						dateHistogramAggregation);

				List<Bucket> buckets = new ArrayList<>(
					dateHistogramAggregationResult.getBuckets());

				Assert.assertEquals(buckets.toString(), 4, buckets.size());

				assertBucket(buckets.get(0), "20170201000000", 1);
				assertBucket(buckets.get(1), "20170202000000", 2);
				assertBucket(buckets.get(2), "20170203000000", 3);
				assertBucket(buckets.get(3), "20170205000000", 1);
			});
	}

	@Test
	public void testDateHistogramAggregationIntervalHour() throws Exception {
		addDocument(getDate("2017-02-01T09:02:00"));
		addDocument(getDate("2017-02-01T09:35:00"));
		addDocument(getDate("2017-02-01T10:15:00"));
		addDocument(getDate("2017-02-01T13:06:00"));
		addDocument(getDate("2017-02-01T14:04:00"));
		addDocument(getDate("2017-02-01T14:05:00"));
		addDocument(getDate("2017-02-01T15:59:00"));
		addDocument(getDate("2017-02-01T16:06:00"));
		addDocument(getDate("2017-02-01T16:48:00"));
		addDocument(getDate("2017-02-01T16:59:00"));

		DateHistogramAggregation dateHistogramAggregation = getAggregation(
			"1h");

		dateHistogramAggregation.setMinDocCount(1L);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						dateHistogramAggregation));

				indexingTestHelper.search();

				DateHistogramAggregationResult dateHistogramAggregationResult =
					indexingTestHelper.getAggregationResult(
						dateHistogramAggregation);

				List<Bucket> buckets = new ArrayList<>(
					dateHistogramAggregationResult.getBuckets());

				Assert.assertEquals(buckets.toString(), 6, buckets.size());

				assertBucket(buckets.get(0), "20170201090000", 2);
				assertBucket(buckets.get(1), "20170201100000", 1);
				assertBucket(buckets.get(2), "20170201130000", 1);
				assertBucket(buckets.get(3), "20170201140000", 2);
				assertBucket(buckets.get(4), "20170201150000", 1);
				assertBucket(buckets.get(5), "20170201160000", 3);
			});
	}

	@Test
	public void testDateHistogramAggregationIntervalMinute() throws Exception {
		addDocument(getDate("2017-02-01T09:02:35"));
		addDocument(getDate("2017-02-01T09:02:59"));
		addDocument(getDate("2017-02-01T09:15:37"));
		addDocument(getDate("2017-02-01T09:16:04"));
		addDocument(getDate("2017-02-01T09:16:42"));

		DateHistogramAggregation dateHistogramAggregation = getAggregation(
			"1m");

		dateHistogramAggregation.setMinDocCount(1L);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						dateHistogramAggregation));

				indexingTestHelper.search();

				DateHistogramAggregationResult dateHistogramAggregationResult =
					indexingTestHelper.getAggregationResult(
						dateHistogramAggregation);

				List<Bucket> buckets = new ArrayList<>(
					dateHistogramAggregationResult.getBuckets());

				Assert.assertEquals(buckets.toString(), 3, buckets.size());

				assertBucket(buckets.get(0), "20170201090200", 2);
				assertBucket(buckets.get(1), "20170201091500", 1);
				assertBucket(buckets.get(2), "20170201091600", 2);
			});
	}

	@Test
	public void testDateHistogramAggregationIntervalMonth() throws Exception {
		addDocument(getDate("2017-01-01T00:00:00"));
		addDocument(getDate("2017-02-02T00:00:00"));
		addDocument(getDate("2017-02-03T00:00:00"));
		addDocument(getDate("2017-03-04T00:00:00"));
		addDocument(getDate("2017-03-05T00:00:00"));
		addDocument(getDate("2017-03-06T00:00:00"));

		DateHistogramAggregation dateHistogramAggregation = getAggregation(
			"1M");

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						dateHistogramAggregation));

				indexingTestHelper.search();

				DateHistogramAggregationResult dateHistogramAggregationResult =
					indexingTestHelper.getAggregationResult(
						dateHistogramAggregation);

				List<Bucket> buckets = new ArrayList<>(
					dateHistogramAggregationResult.getBuckets());

				Assert.assertEquals(buckets.toString(), 3, buckets.size());

				assertBucket(buckets.get(0), "20170101000000", 1);
				assertBucket(buckets.get(1), "20170201000000", 2);
				assertBucket(buckets.get(2), "20170301000000", 3);
			});
	}

	@Test
	public void testDateHistogramAggregationIntervalSecond() throws Exception {
		addDocument(getDate("2017-02-01T00:00:05"));
		addDocument(getDate("2017-02-01T00:00:11"));
		addDocument(getDate("2017-02-01T00:00:11"));
		addDocument(getDate("2017-02-01T00:00:37"));
		addDocument(getDate("2017-02-01T00:00:37"));
		addDocument(getDate("2017-02-01T00:00:37"));

		DateHistogramAggregation dateHistogramAggregation = getAggregation(
			"1s");

		dateHistogramAggregation.setMinDocCount(1L);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						dateHistogramAggregation));

				indexingTestHelper.search();

				DateHistogramAggregationResult dateHistogramAggregationResult =
					indexingTestHelper.getAggregationResult(
						dateHistogramAggregation);

				List<Bucket> buckets = new ArrayList<>(
					dateHistogramAggregationResult.getBuckets());

				Assert.assertEquals(buckets.toString(), 3, buckets.size());

				assertBucket(buckets.get(0), "20170201000005", 1);
				assertBucket(buckets.get(1), "20170201000011", 2);
				assertBucket(buckets.get(2), "20170201000037", 3);
			});
	}

	@Test
	public void testDateHistogramAggregationIntervalYear() throws Exception {
		addDocument(getDate("2015-01-01T00:00:00"));
		addDocument(getDate("2015-06-24T00:00:00"));
		addDocument(getDate("2015-11-13T00:00:00"));
		addDocument(getDate("2016-03-04T00:00:00"));
		addDocument(getDate("2017-12-31T00:00:00"));

		DateHistogramAggregation dateHistogramAggregation = getAggregation(
			"1y");

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						dateHistogramAggregation));

				indexingTestHelper.search();

				DateHistogramAggregationResult dateHistogramAggregationResult =
					indexingTestHelper.getAggregationResult(
						dateHistogramAggregation);

				List<Bucket> buckets = new ArrayList<>(
					dateHistogramAggregationResult.getBuckets());

				Assert.assertEquals(buckets.toString(), 3, buckets.size());

				assertBucket(buckets.get(0), "20150101000000", 3);
				assertBucket(buckets.get(1), "20160101000000", 1);
				assertBucket(buckets.get(2), "20170101000000", 1);
			});
	}

	@Test
	public void testDateHistogramAggregationMinDocCount() throws Exception {
		addDocument(getDate("2017-02-01T00:00:05"));
		addDocument(getDate("2017-02-01T00:00:11"));
		addDocument(getDate("2017-02-01T00:00:11"));
		addDocument(getDate("2017-02-01T00:00:13"));
		addDocument(getDate("2017-02-01T00:00:21"));

		DateHistogramAggregation dateHistogramAggregation = getAggregation(
			"5s");

		dateHistogramAggregation.setMinDocCount(0L);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						dateHistogramAggregation));

				indexingTestHelper.search();

				DateHistogramAggregationResult dateHistogramAggregationResult =
					indexingTestHelper.getAggregationResult(
						dateHistogramAggregation);

				List<Bucket> buckets = new ArrayList<>(
					dateHistogramAggregationResult.getBuckets());

				Assert.assertEquals(buckets.toString(), 4, buckets.size());

				assertBucket(buckets.get(0), "20170201000005", 1);
				assertBucket(buckets.get(1), "20170201000010", 3);
				assertBucket(buckets.get(2), "20170201000015", 0);
				assertBucket(buckets.get(3), "20170201000020", 1);
			});
	}

	@Test
	public void testDateHistogramAggregationOffset() throws Exception {
		addDocument(getDate("2018-02-01T00:00:00"));
		addDocument(getDate("2018-02-03T00:00:00"));
		addDocument(getDate("2018-02-05T00:00:00"));
		addDocument(getDate("2018-02-07T00:00:00"));
		addDocument(getDate("2018-02-09T00:00:00"));
		addDocument(getDate("2018-02-11T00:00:00"));
		addDocument(getDate("2018-02-13T00:00:00"));

		DateHistogramAggregation dateHistogramAggregation = getAggregation(
			"1w");

		dateHistogramAggregation.setOffset(-86400000L);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						dateHistogramAggregation));

				indexingTestHelper.search();

				DateHistogramAggregationResult dateHistogramAggregationResult =
					indexingTestHelper.getAggregationResult(
						dateHistogramAggregation);

				List<Bucket> buckets = new ArrayList<>(
					dateHistogramAggregationResult.getBuckets());

				Assert.assertEquals(buckets.toString(), 3, buckets.size());

				assertBucket(buckets.get(0), "20180128000000", 2);
				assertBucket(buckets.get(1), "20180204000000", 3);
				assertBucket(buckets.get(2), "20180211000000", 2);
			});
	}

	protected void addDocument(Date date) throws Exception {
		addDocument(DocumentCreationHelpers.singleDate(_FIELD, date));
	}

	protected void assertBucket(
		Bucket bucket, String expectedKey, long expectedCount) {

		Assert.assertEquals(expectedKey, bucket.getKey());
		Assert.assertEquals(expectedCount, bucket.getDocCount());
	}

	protected DateHistogramAggregation getAggregation(
		String dateHistogramInterval) {

		DateHistogramAggregation dateHistogramAggregation =
			aggregations.dateHistogram("date_histogram", _FIELD);

		dateHistogramAggregation.setDateHistogramInterval(
			dateHistogramInterval);

		return dateHistogramAggregation;
	}

	protected Date getDate(String date) {
		LocalDateTime localDateTime = LocalDateTime.parse(date);

		ZonedDateTime zonedDateTime = ZonedDateTime.of(
			localDateTime, ZoneId.systemDefault());

		return Date.from(zonedDateTime.toInstant());
	}

	private static final String _FIELD = Field.EXPIRATION_DATE;

}