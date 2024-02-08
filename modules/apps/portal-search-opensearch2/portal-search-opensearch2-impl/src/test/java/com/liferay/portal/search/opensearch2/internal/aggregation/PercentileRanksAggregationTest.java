/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.aggregation;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.metrics.PercentileRanksAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentileRanksAggregationResult;
import com.liferay.portal.search.aggregation.metrics.PercentilesMethod;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.aggregation.metrics.BasePercentileRanksAggregationTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;

/**
 * @author Rafael Praxedes
 */
public class PercentileRanksAggregationTest
	extends BasePercentileRanksAggregationTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static final OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Override
	protected void assertPercentileRanks(
		PercentilesMethod percentilesMethod, double[] values, String expected) {

		PercentileRanksAggregation percentileRanksAggregation =
			aggregations.percentileRanks(
				"percentileRanks", Field.PRIORITY, values);

		percentileRanksAggregation.setKeyed(false);
		percentileRanksAggregation.setPercentilesMethod(percentilesMethod);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						percentileRanksAggregation));

				indexingTestHelper.search();

				PercentileRanksAggregationResult
					percentileRanksAggregationResult =
						indexingTestHelper.getAggregationResult(
							percentileRanksAggregation);

				Assert.assertEquals(
					expected,
					String.valueOf(
						percentileRanksAggregationResult.getPercentiles()));
			});
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

}