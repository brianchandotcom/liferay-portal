/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.query;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseMatchAllQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testMatchAllQuery() {
		int count = 20;

		double[] sequence = _generateNumberSequenceClosed(count);

		addDocuments(
			number -> DocumentCreationHelpers.singleNumber(
				Field.PRIORITY, number),
			sequence);

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
							Arrays.toString(sequence));
					});
			});
	}

	@Test
	public void testMatchAllQueryWithSize0() {
		int count = 20;

		addDocuments(
			number -> DocumentCreationHelpers.singleNumber(
				Field.PRIORITY, number),
			_generateNumberSequenceClosed(count));

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

	private double[] _generateNumberSequenceClosed(int count) {
		double[] sequence = new double[count];

		Arrays.setAll(sequence, i -> i + 1);

		return sequence;
	}

}