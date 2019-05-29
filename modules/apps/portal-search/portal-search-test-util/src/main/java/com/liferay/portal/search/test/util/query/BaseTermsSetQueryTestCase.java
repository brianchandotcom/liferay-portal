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
import com.liferay.portal.search.query.TermsSetQuery;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseTermsSetQueryTestCase extends BaseIndexingTestCase {

	@Test
	public void testTermsSetQueryWithField() {
		index(1, "alpha");
		index(2, "alpha", "bravo");
		index(3, "alpha", "bravo", "charlie");

		assertTermsSetWithField(Arrays.asList("alpha"), "[alpha]");

		assertTermsSetWithField(
			Arrays.asList("alpha", "bravo"), "[[alpha, bravo], alpha]");

		assertTermsSetWithField(
			Arrays.asList("alpha", "bravo", "charlie"),
			"[[alpha, bravo, charlie], [alpha, bravo], alpha]");

		assertTermsSetWithField(Arrays.asList("bravo"), "[]");

		assertTermsSetWithField(Arrays.asList("bravo", "charlie"), "[]");
	}

	@Test
	public void testTermsSetQueryWithScript() {
		index(1, "alpha");
		index(2, "alpha", "bravo");
		index(3, "alpha", "bravo", "charlie");

		String source = "Math.min(params.num_terms, doc['priority'].value)";

		assertTermsSetWithScript(
			Arrays.asList("alpha"), source,
			"[[alpha, bravo, charlie], [alpha, bravo], alpha]");

		assertTermsSetWithScript(
			Arrays.asList("alpha", "bravo"), source,
			"[[alpha, bravo, charlie], [alpha, bravo], alpha]");

		assertTermsSetWithScript(
			Arrays.asList("alpha", "bravo", "charlie"), source,
			"[[alpha, bravo, charlie], [alpha, bravo], alpha]");

		assertTermsSetWithScript(
			Arrays.asList("bravo"), source,
			"[[alpha, bravo, charlie], [alpha, bravo]]");

		assertTermsSetWithScript(
			Arrays.asList("bravo", "charlie"), source,
			"[[alpha, bravo, charlie]]");
	}

	protected void assertTermsSet(
		TermsSetQuery termsSetQuery, String expected) {

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.query(
						termsSetQuery));

				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse ->
						DocumentsAssert.assertValuesIgnoreRelevance(
							searchResponse.getRequestString(),
							searchResponse.getDocumentsStream(),
							Field.USER_NAME, expected));
			});
	}

	protected void assertTermsSetWithField(
		List<Object> terms, String expected) {

		TermsSetQuery termsSetQuery = queries.termsSet(Field.USER_NAME, terms);

		termsSetQuery.setMinimumShouldMatchField(Field.PRIORITY);

		assertTermsSet(termsSetQuery, expected);
	}

	protected void assertTermsSetWithScript(
		List<Object> terms, String source, String expected) {

		TermsSetQuery termsSetQuery = queries.termsSet(Field.USER_NAME, terms);

		termsSetQuery.setMinimumShouldMatchScript(scripts.script(source));

		assertTermsSet(termsSetQuery, expected);
	}

	protected void index(int priority, String... userNames) {
		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, userNames);
				document.addNumber(Field.PRIORITY, priority);
			});
	}

}