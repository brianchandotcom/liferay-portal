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