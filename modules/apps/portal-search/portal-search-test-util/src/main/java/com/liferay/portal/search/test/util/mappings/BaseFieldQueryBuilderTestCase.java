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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

		String[] values2 = values;

		assertSearch(
			indexingTestHelper -> {
				String keywords = values2[0];

				prepareSearch(indexingTestHelper, keywords);

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> {
						Document[] documents = hits.getDocs();

						String field = getField();

						List<String> expectedValues = Arrays.asList(values2);

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
							keywords + "->" + actualValues,
							expectedValues.toString(), actualValues.toString());
					});
			});
	}

	protected void assertSearch(final String keywords, final int size)
		throws Exception {

		_assertCount(keywords, size);
	}

	protected void assertSearch(String keywords, List<String> values)
		throws Exception {

		assertSearch(
			indexingTestHelper -> {
				prepareSearch(indexingTestHelper, keywords);

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> DocumentsAssert.assertValues(
						keywords, hits.getDocs(), getField(), values));
			});
	}

	protected void assertSearchCount(final String keywords, final int size)
		throws Exception {

		assertSearch(
			indexingTestHelper -> {
				prepareSearch(indexingTestHelper, keywords);

				long count = indexingTestHelper.searchCount();

				Assert.assertEquals(keywords, size, count);
			});
	}

	protected void assertSearchIgnoreRelevance(
			String keywords, List<String> values)
		throws Exception {

		assertSearch(
			indexingTestHelper -> {
				prepareSearch(indexingTestHelper, keywords);

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> DocumentsAssert.assertValuesIgnoreRelevance(
						keywords, hits.getDocs(), getField(), values));
			});
	}

	protected void assertSearchNoHits(String keywords) throws Exception {
		_assertCount(keywords, 0);
	}

	protected Query buildQuery(final String keywords) {
		FieldQueryBuilder fieldQueryBuilder = createFieldQueryBuilder();

		Query query = fieldQueryBuilder.build(getField(), keywords);

		setPreBooleanFilter(
			new TermFilter(Field.COMPANY_ID, String.valueOf(getCompanyId())),
			query);

		return query;
	}

	protected abstract FieldQueryBuilder createFieldQueryBuilder();

	protected Hits doSearch(final String keywords) throws Exception {
		Query query = buildQuery(keywords);

		return search(createSearchContext(), query);
	}

	protected abstract String getField();

	protected void prepareSearch(
		IndexingTestHelper indexingTestHelper, String keywords) {

		Query query = buildQuery(keywords);

		indexingTestHelper.setQuery(query);
	}

	protected String[] transformFieldValues(String... values) {
		return null;
	}

	private void _assertCount(String keywords, int size) throws Exception {
		assertSearch(
			indexingTestHelper -> {
				prepareSearch(indexingTestHelper, keywords);

				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.size(
						size + 1));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> DocumentsAssert.assertCount(
						keywords, hits.getDocs(), getField(), size));
			});
	}

}