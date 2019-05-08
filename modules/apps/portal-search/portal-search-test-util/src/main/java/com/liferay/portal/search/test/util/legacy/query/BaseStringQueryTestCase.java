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

package com.liferay.portal.search.test.util.legacy.query;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * @author Tibor Lipusz
 * @author André de Oliveira
 */
public abstract class BaseStringQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testBooleanOperatorAnd() throws Exception {
		addDocuments("java eclipse", "java liferay", "java liferay eclipse");

		assertSearch(
			"java AND eclipse",
			Arrays.asList("java eclipse", "java liferay eclipse"));
		assertSearch(
			"eclipse AND liferay", Arrays.asList("java liferay eclipse"));
	}

	@Test
	public void testBooleanOperatorAndWithNot() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("alpha AND NOT bravo", Arrays.asList("alpha charlie"));
	}

	@Test
	public void testBooleanOperatorNot() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("NOT alpha", Arrays.asList("charlie delta"));
		assertSearch(
			"NOT bravo", Arrays.asList("alpha charlie", "charlie delta"));
	}

	@Test
	public void testBooleanOperatorNotDeep() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch(
			"+(*:* NOT alpha) +charlie", Arrays.asList("charlie delta"));
	}

	@Test
	public void testBooleanOperatorOr() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch(
			"alpha OR charlie",
			Arrays.asList("alpha charlie", "alpha bravo", "charlie delta"));
		assertSearch(
			"alpha OR delta",
			Arrays.asList("charlie delta", "alpha bravo", "alpha charlie"));
		assertSearch(
			"bravo OR delta", Arrays.asList("alpha bravo", "charlie delta"));
	}

	@Test
	public void testBooleanOperatorOrWithNot() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("alpha OR NOT bravo", Arrays.asList("alpha charlie"));
	}

	@Test
	public void testField() throws Exception {
		addDocuments("java", "eclipse", "liferay");

		assertSearch(
			"title:(java OR eclipse)", Arrays.asList("java", "eclipse"));
		assertSearch("description:(java OR eclipse)", Collections.emptyList());
	}

	@Test
	public void testPrefixOperatorMust() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("+alpha", Arrays.asList("alpha bravo", "alpha charlie"));
		assertSearch(
			"+alpha bravo", Arrays.asList("alpha bravo", "alpha charlie"));
		assertSearch("+alpha +bravo", Arrays.asList("alpha bravo"));
		assertSearch(
			"+alpha delta", Arrays.asList("alpha bravo", "alpha charlie"));
		assertSearch("+alpha +delta", Arrays.asList());
	}

	@Test
	public void testPrefixOperatorMustNot() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("-alpha", Arrays.asList("charlie delta"));
		assertSearch("-alpha bravo", Arrays.asList());
		assertSearch("-alpha -bravo", Arrays.asList("charlie delta"));
		assertSearch("-alpha delta", Arrays.asList("charlie delta"));
		assertSearch("-alpha -delta", Arrays.asList());
	}

	@Test
	public void testPrefixOperatorMustNotWithBooleanOperatorOr()
		throws Exception {

		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("(-bravo OR alpha)", Arrays.asList("alpha charlie"));
		assertSearch("-(bravo OR alpha)", Arrays.asList("charlie delta"));
		assertSearch("-(bravo) OR (alpha)", Arrays.asList("alpha charlie"));
		assertSearch("-(bravo) OR alpha", Arrays.asList("alpha charlie"));
		assertSearch("-bravo OR (alpha)", Arrays.asList("alpha charlie"));
		assertSearch("-bravo OR alpha", Arrays.asList("alpha charlie"));
	}

	protected void addDocuments(String... values) throws Exception {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(_FIELD_NAME, value),
			Arrays.asList(values));
	}

	protected void assertSearch(String queryString, List<String> expectedValues)
		throws Exception {

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setFilter(
					new TermFilter(
						Field.ENTRY_CLASS_NAME, getEntryClassName()));
				indexingTestHelper.setQuery(new StringQuery(queryString));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> DocumentsAssert.assertValues(
						indexingTestHelper.getRequestString(), hits.getDocs(),
						_FIELD_NAME, expectedValues));
			});
	}

	protected Void doAssertSearch(String query, List<String> expectedValues)
		throws Exception {

		SearchContext searchContext = createSearchContext();

		StringQuery stringQuery = new StringQuery(query);

		Hits hits = search(searchContext, stringQuery);

		DocumentsAssert.assertValues(
			query, hits.getDocs(), _FIELD_NAME, expectedValues);

		return null;
	}

	private static final String _FIELD_NAME = "title";

}