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

package com.liferay.portal.search.solr8.internal.filter;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.search.solr8.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Joshua Cords
 */
public class BooleanFilterTranslatorTest extends BaseIndexingTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMustNotContainsReturnsResultsSolr() {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(_FIELD_NAME, value),
			"alpha bravo", "alpha charlie", "charlie delta");

		BooleanFilter mustNotContainBooleanFilter = new BooleanFilter();

		TermFilter titleTermFilter = new TermFilter(_FIELD_NAME, "delta");

		mustNotContainBooleanFilter.add(
			titleTermFilter, BooleanClauseOccur.MUST_NOT);

		assertSearch(
			mustNotContainBooleanFilter,
			Arrays.asList("alpha bravo", "alpha charlie"));
	}

	protected void assertSearch(
		BooleanFilter booleanFilter, List<String> expectedValues) {

		BooleanFilter baseBooleanFilter = new BooleanFilter();

		TermFilter classTermFilter = new TermFilter(
			Field.ENTRY_CLASS_NAME, getEntryClassName());

		baseBooleanFilter.add(booleanFilter, BooleanClauseOccur.MUST);
		baseBooleanFilter.add(classTermFilter, BooleanClauseOccur.MUST);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setFilter(baseBooleanFilter);
				indexingTestHelper.setQuery(new MatchAllQuery());

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> DocumentsAssert.assertValuesIgnoreRelevance(
						indexingTestHelper.getRequestString(), hits.getDocs(),
						_FIELD_NAME, expectedValues));
			});
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new SolrIndexingFixture();
	}

	private static final String _FIELD_NAME = "title";

}