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
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.bucket.GlobalAggregation;
import com.liferay.portal.search.aggregation.bucket.GlobalAggregationResult;
import com.liferay.portal.search.aggregation.metrics.AvgAggregation;
import com.liferay.portal.search.aggregation.metrics.AvgAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseGlobalAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testGlobalAggregation() throws Exception {
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
		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "SomeUser2");
				document.addNumber(Field.PRIORITY, 6);
			});
		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "SomeUser2");
				document.addNumber(Field.PRIORITY, 7);
			});

		GlobalAggregation globalAggregation = aggregations.global("global");

		AvgAggregation avgAggregation = aggregations.avg("avg", Field.PRIORITY);

		globalAggregation.addChildAggregation(avgAggregation);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						globalAggregation));

				indexingTestHelper.search();

				GlobalAggregationResult globalAggregationResult =
					indexingTestHelper.getAggregationResult(globalAggregation);

				Assert.assertEquals(
					7, globalAggregationResult.getDocCount(), 0);

				Map<String, AggregationResult> childrenAggregationResults =
					globalAggregationResult.getChildrenAggregationResultsMap();

				Assert.assertEquals(
					"Children aggregations", 1,
					childrenAggregationResults.size());

				AvgAggregationResult avgAggregationResult =
					(AvgAggregationResult)
						globalAggregationResult.getChildAggregationResult(
							"avg");

				Assert.assertEquals(
					"Avg priority", 4, avgAggregationResult.getValue(), 0);
			});
	}

}