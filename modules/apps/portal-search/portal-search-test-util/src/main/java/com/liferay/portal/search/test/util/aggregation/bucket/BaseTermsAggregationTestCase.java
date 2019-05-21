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
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.bucket.Order;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.test.util.aggregation.AggregationAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseTermsAggregationTestCase
	extends BaseIndexingTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		String userName1 = "SomeUser1";
		String userName2 = "SomeUser2";
		String userName3 = "SomeUser3";

		index(1, userName1);
		index(2, userName2);
		index(3, userName3);
		index(4, userName1);
		index(5, userName2);
		index(6, userName3);
		index(7, userName1);
		index(8, userName2);
		index(9, userName1);
		index(10, userName2);
		index(11, userName2);
	}

	@Test
	public void testTerms() throws Exception {
		TermsAggregation termsAggregation = getAggregation();

		assertBuckets(
			termsAggregation, "[SomeUser2=5, SomeUser1=4, SomeUser3=2]");
	}

	@Test
	public void testTermsWithOrder() throws Exception {
		TermsAggregation termsAggregation = getAggregation();

		termsAggregation.addOrders(Order.key(true));

		assertBuckets(
			termsAggregation, "[SomeUser1=4, SomeUser2=5, SomeUser3=2]");
	}

	protected void assertBuckets(Aggregation aggregation, String expected) {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						aggregation));

				indexingTestHelper.search();

				AggregationAssert.assertBuckets(
					expected,
					indexingTestHelper.getAggregationResult(aggregation));
			});
	}

	protected TermsAggregation getAggregation() {
		return aggregations.terms("terms", Field.USER_NAME);
	}

	protected void index(int priority, String userName) {
		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, userName);
				document.addNumber(Field.PRIORITY, priority);
			});
	}

}