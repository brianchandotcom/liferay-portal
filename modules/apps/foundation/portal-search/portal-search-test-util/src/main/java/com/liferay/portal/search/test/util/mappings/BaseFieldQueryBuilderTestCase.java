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

package com.liferay.portal.search.test.util.mappings;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public abstract class BaseFieldQueryBuilderTestCase
	extends BaseIndexingTestCase {

	protected void addDocument(String... values) throws Exception {
		String[] transformed = transformFieldValues(values);

		if (transformed != null) {
			values = transformed;
		}

		addDocument(DocumentCreationHelpers.singleText(getField(), values));

		final String[] expectedValues = values;

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_assertDocument(expectedValues);

					return null;
				}

			});
	}

	protected void assertSearch(final String keywords, final int size)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_assertCount(keywords, size);

					return null;
				}

			});
	}

	protected void assertSearch(
			final String keywords, final List<String> values)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_assertValues(keywords, values);

					return null;
				}

			});
	}

	protected void assertSearchNoHits(final String keywords) throws Exception {
		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_assertNoHits(keywords);

					return null;
				}

			});
	}

	protected abstract FieldQueryBuilder createFieldQueryBuilder();

	protected Hits doSearch(final String keywords) throws Exception {
		FieldQueryBuilder fieldQueryBuilder = createFieldQueryBuilder();

		Query query = fieldQueryBuilder.build(getField(), keywords);

		return search(createSearchContext(), query);
	}

	protected abstract String getField();

	protected String[] transformFieldValues(String... values) {
		return null;
	}

	private static List<String> _getValues(String field, Document... docs) {
		ArrayList<String> values = new ArrayList<>(docs.length);

		for (Document document : docs) {
			values.add(document.get(field));
		}

		return values;
	}

	private void _assertCount(String keywords, int count) throws Exception {
		Hits hits = doSearch(keywords);

		Document[] docs = hits.getDocs();

		if (docs.length == 0) {
			return;
		}

		List<String> values = _getValues(getField(), docs);

		Assert.assertEquals(keywords + "->" + values, count, docs.length);
	}

	private void _assertDocument(String... values) throws Exception {
		String keywords = values[0];

		Hits hits = doSearch(keywords);

		Document[] documents = hits.getDocs();

		List<String> expectedValues = Arrays.asList(values);

		String field = getField();

		List<String> actualValues = new ArrayList<>();

		for (Document document : documents) {
			List<String> documentValues = Arrays.asList(
				document.getValues(field));

			if (documentValues.equals(expectedValues)) {
				return;
			}

			actualValues.addAll(documentValues);
		}

		Assert.assertEquals(
			keywords + "->" + actualValues, expectedValues.toString(),
			actualValues.toString());
	}

	private void _assertNoHits(String keywords) throws Exception {
		_assertCount(keywords, 0);
	}

	private void _assertValues(String keywords, List<String> expectedValues)
		throws Exception {

		Hits hits = doSearch(keywords);

		Document[] documents = hits.getDocs();

		List<String> actualValues = _getValues(getField(), documents);

		Assert.assertEquals(
			keywords + "->" + actualValues, expectedValues.toString(),
			actualValues.toString());
	}

}