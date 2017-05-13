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

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author Tibor Lipusz
 * @author André de Oliveira
 */
public abstract class BaseStringQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testAnd() throws Exception {
		addDocument("description", "java eclipse");
		addDocument("description", "java liferay");
		addDocument("description", "java liferay eclipse");

		assertSearch(
			"java AND eclipse", "description",
			Arrays.asList("java eclipse", "java liferay eclipse"));

		assertSearch(
			"eclipse AND liferay", "description",
			Arrays.asList("java liferay eclipse"));
	}

	@Test
	public void testField() throws Exception {
		addDocument("title", "java");
		addDocument("title", "eclipse");
		addDocument("title", "liferay");

		assertSearch(
			"title:(java OR eclipse)", "title",
			Arrays.asList("java", "eclipse"));

		assertSearch(
			"description:(java OR eclipse)", "title", Collections.emptyList());
	}

	protected void addDocument(String fieldName, String... fieldValues)
		throws Exception {

		addDocument(DocumentCreationHelpers.singleText(fieldName, fieldValues));
	}

	protected void assertSearch(
			String query, String fieldName, List<String> expectedValues)
		throws Exception {

		SearchContext searchContext = createSearchContext();

		StringQuery stringQuery = _createStringQuery(query, searchContext);

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			() -> {
				Hits hits = search(searchContext, stringQuery);

				DocumentsAssert.assertValues(
					query, hits.getDocs(), fieldName, expectedValues);

				return null;
			});
	}

	private StringQuery _createStringQuery(
		String query, SearchContext searchContext) {

		StringQuery stringQuery = new StringQuery(query);

		stringQuery.setQueryConfig(searchContext.getQueryConfig());

		return stringQuery;
	}

}