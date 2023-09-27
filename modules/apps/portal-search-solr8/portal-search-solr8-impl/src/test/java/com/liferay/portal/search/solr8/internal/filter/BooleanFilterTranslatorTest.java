/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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