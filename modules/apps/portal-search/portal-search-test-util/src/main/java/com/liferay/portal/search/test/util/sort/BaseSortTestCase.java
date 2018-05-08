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

package com.liferay.portal.search.test.util.sort;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactory;
import com.liferay.portal.search.internal.SortFactoryImpl;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelper;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.junit.Test;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public abstract class BaseSortTestCase extends BaseIndexingTestCase {

	@Test
	public void testDefaultSorts() throws Exception {
		double[] values = {1, 2, 3};

		addDocuments(
			value -> document -> {
				document.addDate(
					Field.MODIFIED_DATE, new Date(value.longValue()));
				document.addNumber(Field.PRIORITY, value);
			},
			values);

		SearchContext searchContext = createSearchContext();

		SortFactory sortFactory = new SortFactoryImpl();

		searchContext.setSorts(sortFactory.getDefaultSorts());

		assertOrder(
			Field.PRIORITY, searchContext, Arrays.asList("3.0", "2.0", "1.0"));
	}

	@Test
	public void testPriorityField() throws Exception {
		testDoubleField(Field.PRIORITY);
	}

	@Test
	public void testPriorityFieldSortable() throws Exception {
		testDoubleFieldSortable(Field.PRIORITY);
	}

	protected void addDocuments(
			Function<Double, DocumentCreationHelper> function, double... values)
		throws Exception {

		for (double value : values) {
			addDocument(function.apply(value));
		}
	}

	protected void assertOrder(
			String fieldName, int sortType, List<String> expectedValues)
		throws Exception {

		SearchContext searchContext = createSearchContext();

		searchContext.setSorts(new Sort(fieldName, sortType, false));

		assertOrder(fieldName, searchContext, expectedValues);
	}

	protected void assertOrder(
			String fieldName, SearchContext searchContext,
			List<String> expectedValues)
		throws Exception {

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(fieldName);

		Query query = getDefaultQuery();

		query.setQueryConfig(queryConfig);

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			() -> {
				Hits hits = search(searchContext, query);

				DocumentsAssert.assertValues(
					(String)searchContext.getAttribute("queryString"),
					hits.getDocs(), fieldName, expectedValues);

				return null;
			});
	}

	protected void testDoubleField(String fieldName) throws Exception {
		testDoubleField(
			fieldName,
			value -> DocumentCreationHelpers.singleNumber(fieldName, value));
	}

	protected void testDoubleField(
			String fieldName, Function<Double, DocumentCreationHelper> function)
		throws Exception {

		double[] values = {10, 1, 40, 5.3};

		addDocuments(function, values);

		assertOrder(
			fieldName, Sort.DOUBLE_TYPE,
			Arrays.asList("1.0", "5.3", "10.0", "40.0"));
	}

	protected void testDoubleFieldSortable(String fieldName) throws Exception {
		testDoubleField(
			fieldName,
			value -> DocumentCreationHelpers.singleNumberSortable(
				fieldName, value));
	}

}