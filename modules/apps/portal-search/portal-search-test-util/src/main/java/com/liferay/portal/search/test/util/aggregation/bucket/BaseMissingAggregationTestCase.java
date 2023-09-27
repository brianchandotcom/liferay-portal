/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.aggregation.bucket;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.bucket.MissingAggregation;
import com.liferay.portal.search.aggregation.bucket.MissingAggregationResult;
import com.liferay.portal.search.aggregation.metrics.SumAggregation;
import com.liferay.portal.search.aggregation.metrics.SumAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseMissingAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testMissing() throws Exception {
		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "SomeUser1");
				document.addNumber(Field.PRIORITY, 1);
			});
		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "SomeUser1");
				document.addNumber(Field.PRIORITY, 2);
			});
		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "SomeUser1");
				document.addNumber(Field.PRIORITY, 3);
			});
		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "SomeUser2");
				document.addNumber(Field.PRIORITY, 4);
			});
		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "SomeUser2");
				document.addNumber(Field.PRIORITY, 5);
			});
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 6));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 7));

		MissingAggregation missingAggregation = aggregations.missing(
			"missing", Field.USER_NAME);

		SumAggregation sumAggregation = aggregations.sum("sum", Field.PRIORITY);

		missingAggregation.addChildAggregation(sumAggregation);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						missingAggregation));

				indexingTestHelper.search();

				MissingAggregationResult missingAggregationResult =
					indexingTestHelper.getAggregationResult(missingAggregation);

				Assert.assertEquals(
					"Documents missing user name", 2,
					missingAggregationResult.getDocCount());

				SumAggregationResult sumAggregationResult =
					(SumAggregationResult)
						missingAggregationResult.getChildAggregationResult(
							sumAggregation.getName());

				Assert.assertEquals(
					"SumUser1 total priorities", 13,
					sumAggregationResult.getValue(), 0);
			});
	}

}