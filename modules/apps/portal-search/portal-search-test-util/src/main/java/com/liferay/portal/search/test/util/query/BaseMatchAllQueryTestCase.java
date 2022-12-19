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

package com.liferay.portal.search.test.util.query;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseMatchAllQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testMatchAllQuery() {
		int count = 20;

		List<Double> list = IntStream.rangeClosed(
			1, count
		).mapToObj(
			Double::valueOf
		).collect(
			Collectors.toList()
		);

		list.forEach(
			i -> addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i)));

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder ->
						searchRequestBuilder.withSearchRequestBuilder(
							this::addMatchAllQuery
						).size(
							30
						).sorts(
							sorts.field(Field.PRIORITY)
						));

				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse -> {
						SearchHits searchHits = searchResponse.getSearchHits();

						Assert.assertEquals(
							"Total hits: " + searchResponse.getRequestString(),
							count, searchHits.getTotalHits());

						DocumentsAssert.assertValues(
							searchResponse.getRequestString(),
							searchResponse.getDocuments(), Field.PRIORITY,
							String.valueOf(list));
					});
			});
	}

	@Test
	public void testMatchAllQueryWithSize0() {
		int count = 20;

		IntStream.rangeClosed(
			1, count
		).forEach(
			i -> addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i))
		);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder ->
						searchRequestBuilder.withSearchRequestBuilder(
							this::addMatchAllQuery
						).size(
							0
						));

				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse -> {
						SearchHits searchHits = searchResponse.getSearchHits();

						Assert.assertEquals(
							"Total hits: " + searchResponse.getRequestString(),
							count, searchHits.getTotalHits());

						DocumentsAssert.assertValues(
							searchResponse.getRequestString(),
							searchResponse.getDocuments(), Field.PRIORITY,
							"[]");
					});
			});
	}

	protected void addMatchAllQuery(SearchRequestBuilder searchRequestBuilder) {
		searchRequestBuilder.addComplexQueryPart(
			complexQueryPartBuilderFactory.builder(
			).query(
				queries.matchAll()
			).build());
	}

}