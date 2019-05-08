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

package com.liferay.portal.search.test.util.filter;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.search.filter.DateRangeFilterBuilder;
import com.liferay.portal.search.internal.filter.DateRangeFilterBuilderImpl;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * @author Eric Yan
 */
public abstract class BaseDateRangeFilterTestCase extends BaseIndexingTestCase {

	@Test
	public void testBeforeLowerBound() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001123000000");

		assertNoHits();
	}

	@Test
	public void testBeforeRange() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001123000000");
		dateRangeFilterBuilder.setTo("20001124000000");

		assertNoHits();
	}

	@Test
	public void testBeforeUpperBound() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setTo("20001123000000");

		assertHits("20001122000000");
	}

	@Test
	public void testLowerBoundExclusive() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001122000000");
		dateRangeFilterBuilder.setIncludeLower(false);

		assertNoHits();
	}

	@Test
	public void testLowerBoundInclusive() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001122000000");
		dateRangeFilterBuilder.setIncludeLower(true);

		assertHits("20001122000000");
	}

	@Test
	public void testPastLowerBound() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001121000000");

		assertHits("20001122000000");
	}

	@Test
	public void testPastRange() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001120000000");
		dateRangeFilterBuilder.setTo("20001121000000");

		assertNoHits();
	}

	@Test
	public void testPastUpperBound() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setTo("20001121000000");

		assertNoHits();
	}

	@Test
	public void testRange() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001121000000");
		dateRangeFilterBuilder.setTo("20001123000000");

		assertHits("20001122000000");
	}

	@Test
	public void testRangeExclusive() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001122000000");
		dateRangeFilterBuilder.setTo("20001122000000");

		assertNoHits();
	}

	@Test
	public void testRangeInclusive() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001122000000");
		dateRangeFilterBuilder.setIncludeLower(true);
		dateRangeFilterBuilder.setIncludeUpper(true);
		dateRangeFilterBuilder.setTo("20001122000000");

		assertHits("20001122000000");
	}

	@Test
	public void testRangeLowerBoundExclusive() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001122000000");
		dateRangeFilterBuilder.setTo("20001123000000");

		assertNoHits();
	}

	@Test
	public void testRangeLowerBoundInclusive() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001122000000");
		dateRangeFilterBuilder.setIncludeLower(true);
		dateRangeFilterBuilder.setTo("20001123000000");

		assertHits("20001122000000");
	}

	@Test
	public void testRangeUpperBoundExclusive() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001121000000");
		dateRangeFilterBuilder.setTo("20001122000000");

		assertNoHits();
	}

	@Test
	public void testRangeUpperBoundInclusive() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001121000000");
		dateRangeFilterBuilder.setIncludeUpper(true);
		dateRangeFilterBuilder.setTo("20001122000000");

		assertHits("20001122000000");
	}

	@Test
	public void testUpperBoundExclusive() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setIncludeUpper(false);
		dateRangeFilterBuilder.setTo("20001122000000");

		assertNoHits();
	}

	@Test
	public void testUpperBoundInclusive() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setIncludeUpper(true);
		dateRangeFilterBuilder.setTo("20001122000000");

		assertHits("20001122000000");
	}

	protected void addDocument(Date date) throws Exception {
		addDocument(DocumentCreationHelpers.singleDate(FIELD, date));
	}

	protected void assertHits(String... expectedValues) throws Exception {
		assertSearch(Arrays.asList(expectedValues));
	}

	protected void assertNoHits() throws Exception {
		assertSearch(Arrays.asList());
	}

	protected void assertSearch(List<String> expectedValues) throws Exception {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setFilter(dateRangeFilterBuilder.build());

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> DocumentsAssert.assertValues(
						indexingTestHelper.getRequestString(), hits.getDocs(),
						FIELD, expectedValues));
			});
	}

	protected DateRangeFilterBuilder createDateRangeFilterBuilder() {
		DateRangeFilterBuilder dateRangeFilterBuilder =
			new DateRangeFilterBuilderImpl();

		dateRangeFilterBuilder.setFieldName(FIELD);

		return dateRangeFilterBuilder;
	}

	protected Date getDate(int year, int month, int date) {
		LocalDateTime localDateTime = LocalDateTime.of(
			year, month, date, 0, 0, 0);

		ZonedDateTime zonedDateTime = ZonedDateTime.of(
			localDateTime, ZoneId.systemDefault());

		return Date.from(zonedDateTime.toInstant());
	}

	@Override
	protected void setPreBooleanFilter(Filter filter, Query query) {
		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(filter, BooleanClauseOccur.MUST);

		query.setPreBooleanFilter(booleanFilter);
	}

	protected static final String FIELD = Field.EXPIRATION_DATE;

	protected DateRangeFilterBuilder dateRangeFilterBuilder =
		createDateRangeFilterBuilder();

}