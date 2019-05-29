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
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseTermsQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testTermsQuery() {
		Stream.of(
			"alpha", "bravo", "charlie", "delta"
		).forEach(
			userName -> addDocument(
				DocumentCreationHelpers.singleKeyword(
					Field.USER_NAME, userName))
		);

		TermsQuery termsQuery = queries.terms(Field.USER_NAME);

		termsQuery.addValues("bravo", "charlie");

		Sort sort = sorts.field(Field.USER_NAME, SortOrder.DESC);

		String expected = "[charlie, bravo]";

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.query(
						termsQuery
					).sorts(
						sort
					));

				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse -> {
						DocumentsAssert.assertValues(
							searchResponse.getRequestString(),
							searchResponse.getDocumentsStream(),
							Field.USER_NAME, expected);

						SearchHits searchHits = searchResponse.getSearchHits();

						Assert.assertEquals(
							"Total hits", 2, searchHits.getTotalHits());
					});
			});
	}

}