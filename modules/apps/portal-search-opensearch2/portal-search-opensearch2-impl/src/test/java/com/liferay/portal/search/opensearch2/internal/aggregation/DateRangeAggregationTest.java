/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.aggregation;

import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.aggregation.AggregationAssert;
import com.liferay.portal.search.test.util.aggregation.bucket.BaseDateRangeAggregationTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class DateRangeAggregationTest extends BaseDateRangeAggregationTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static final OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Override
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

		dateRangeAggregation.addRange("1", "20160101", "20161231");
		dateRangeAggregation.addRange("2", "20170101", "20171231");
		dateRangeAggregation.addRange("3", "20180101", null);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						dateRangeAggregation));

				indexingTestHelper.search();

				AggregationAssert.assertBuckets(
					"[1=2, 2=2, 3=3]",
					indexingTestHelper.getAggregationResult(
						dateRangeAggregation));
			});
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

}