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

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.test.util.Checker;
import com.liferay.portal.search.test.util.Checkers;
import com.liferay.portal.search.test.util.SearchAssert;
import com.liferay.portal.search.test.util.Searcher;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

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

		addDocument(DocumentCreationHelpers.singleField(getField(), values));

		for (String keywords : values) {
			assertSearch(
				keywords, Checkers.present(keywords, getField(), keywords));
		}
	}

	protected void assertSearch(final String keywords, Checker checker)
		throws Exception {

		SearchAssert.assertSearch(
			new Searcher() {

				@Override
				public Hits search() throws Exception {
					return doSearch(keywords);
				}

			},
			checker);
	}

	protected void assertSearch(String keywords, int size) throws Exception {
		assertSearch(keywords, Checkers.size(keywords, getField(), size));
	}

	protected void assertSearch(String keywords, String... values)
		throws Exception {

		assertSearch(keywords, Checkers.values(keywords, getField(), values));
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

}