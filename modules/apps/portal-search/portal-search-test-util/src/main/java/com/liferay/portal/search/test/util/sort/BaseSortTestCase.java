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

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactory;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.search.internal.SortFactoryImpl;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelper;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Date;
import java.util.function.Function;
import java.util.stream.Stream;

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

		SortFactory sortFactory = new SortFactoryImpl();

		assertOrder(
			sortFactory.getDefaultSorts(), Field.PRIORITY, "[3.0, 2.0, 1.0]");
	}

	@Test
	public void testPriorityField() throws Exception {
		testDoubleField(Field.PRIORITY);
	}

	@Test
	public void testPriorityFieldSortable() throws Exception {
		testDoubleFieldSortable(Field.PRIORITY);
	}

	@Test
	public void testScore() throws Exception {
		String fieldName = "testField";

		addDocuments(
			value -> DocumentCreationHelpers.singleText(fieldName, value),
			Stream.of("alpha", "charlie"));

		Query query = getScoredQuery(fieldName, "charlie");

		assertOrder(
			getScoreSortArray(Sort.CUSTOM_TYPE, false), fieldName,
			"[charlie, alpha]", query);
		assertOrder(
			getScoreSortArray(Sort.CUSTOM_TYPE, true), fieldName,
			"[alpha, charlie]", query);
		assertOrder(
			getScoreSortArray(Sort.SCORE_TYPE, false), fieldName,
			"[charlie, alpha]", query);
		assertOrder(
			getScoreSortArray(Sort.SCORE_TYPE, true), fieldName,
			"[alpha, charlie]", query);
	}

	protected void addDocuments(
			Function<Double, DocumentCreationHelper> function, double... values)
		throws Exception {

		for (double value : values) {
			addDocument(function.apply(value));
		}
	}

	protected void assertOrder(
		Sort[] sorts, String fieldName, String expected) {

		assertOrder(sorts, fieldName, expected, null);
	}

	protected void assertOrder(
		Sort[] sorts, String fieldName, String expected, Query query) {

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> searchContext.setSorts(sorts));

				if (query != null) {
					indexingTestHelper.setQuery(query);
				}

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> DocumentsAssert.assertValues(
						indexingTestHelper.getRequestString(), hits.getDocs(),
						fieldName, expected));
			});
	}

	protected void assertOrder(
		String fieldName, int sortType, boolean reverse, String expected) {

		assertOrder(
			new Sort[] {new Sort(fieldName, sortType, reverse)}, fieldName,
			expected);
	}

	protected Query getScoredQuery(String fieldName, String fieldValue) {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		booleanQueryImpl.addExactTerm(fieldName, fieldValue);

		booleanQueryImpl.add(getDefaultQuery(), BooleanClauseOccur.SHOULD);

		return booleanQueryImpl;
	}

	protected abstract String getScoreParameter();

	protected Sort[] getScoreSortArray(int type, boolean reverse) {
		return new Sort[] {new Sort(getScoreParameter(), type, reverse)};
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
			fieldName, Sort.DOUBLE_TYPE, false, "[1.0, 5.3, 10.0, 40.0]");
		assertOrder(
			fieldName, Sort.DOUBLE_TYPE, true, "[40.0, 10.0, 5.3, 1.0]");
	}

	protected void testDoubleFieldSortable(String fieldName) throws Exception {
		testDoubleField(
			fieldName,
			value -> DocumentCreationHelpers.singleNumberSortable(
				fieldName, value));
	}

}