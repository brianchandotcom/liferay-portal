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

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.portal.search.elasticsearch7.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.filter.BaseDateRangeFilterTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import org.elasticsearch.action.search.SearchPhaseExecutionException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Eric Yan
 */
public class ElasticsearchDateRangeFilterTest
	extends BaseDateRangeFilterTestCase {

	@Test
	public void testDateFormat() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFormat("MMddyyyyHHmmss");
		dateRangeFilterBuilder.setFrom("11212000000000");
		dateRangeFilterBuilder.setTo("11232000000000");

		assertHits("20001122000000");
	}

	@Test
	public void testDateFormatWithMultiplePatterns() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFormat("MMddyyyyHHmmss || yyyy");
		dateRangeFilterBuilder.setFrom("2000");
		dateRangeFilterBuilder.setTo("11232000000000");

		assertHits("20001122000000");
	}

	@Test
	public void testMalformed() throws Exception {
		expectedException.expect(SearchPhaseExecutionException.class);
		expectedException.expectMessage("all shards failed");

		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("11212000000000");
		dateRangeFilterBuilder.setTo("11232000000000");

		assertNoHits();
	}

	@Test
	public void testMalformedMultiple() throws Exception {
		expectedException.expect(SearchPhaseExecutionException.class);
		expectedException.expectMessage("all shards failed");

		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("2000");
		dateRangeFilterBuilder.setTo("11232000000000");

		assertNoHits();
	}

	@Test
	public void testTimeZone() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001122010000");
		dateRangeFilterBuilder.setTimeZoneId("Etc/GMT-2");
		dateRangeFilterBuilder.setTo("20001122030000");

		assertHits("20001122000000");
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

}