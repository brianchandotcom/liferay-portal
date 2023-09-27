/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.count;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Preston Crary
 * @author André de Oliveira
 * @author Tibor Lipusz
 */
public abstract class BaseCountTestCase extends BaseIndexingTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		for (int i = 0; i < _TOTAL_DOCUMENTS; i++) {
			addDocument(
				DocumentCreationHelpers.singleText(
					_FIELD, StringUtil.toLowerCase(testName.getMethodName())));
		}
	}

	@Test
	public void testAll() throws Exception {
		assertSearch(
			indexingTestHelper -> Assert.assertEquals(
				_TOTAL_DOCUMENTS, indexingTestHelper.searchCount()));
	}

	@Test
	public void testPaginationIsIgnored() throws Exception {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						int start = 5;

						searchContext.setEnd(start - 1);
						searchContext.setStart(start);
					});

				Assert.assertEquals(
					_TOTAL_DOCUMENTS, indexingTestHelper.searchCount());
			});
	}

	@Test
	public void testPostFilterWithoutMainQuery() throws Exception {
		assertSearch(
			indexingTestHelper -> {
				Query query = new BooleanQueryImpl();

				query.setPostFilter(_createBooleanFilter());

				indexingTestHelper.setQuery(query);

				Assert.assertEquals(
					_TOTAL_DOCUMENTS, indexingTestHelper.searchCount());
			});
	}

	@Test
	public void testPreFilterWithoutMainQuery() throws Exception {
		assertSearch(
			indexingTestHelper -> {
				Query query = new BooleanQueryImpl();

				query.setPreBooleanFilter(_createBooleanFilter());

				indexingTestHelper.setQuery(query);

				Assert.assertEquals(
					_TOTAL_DOCUMENTS, indexingTestHelper.searchCount());
			});
	}

	private BooleanFilter _createBooleanFilter() {
		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(
			new TermFilter(
				_FIELD, StringUtil.toLowerCase(testName.getMethodName())),
			BooleanClauseOccur.MUST);

		return booleanFilter;
	}

	private static final String _FIELD = "test-field";

	private static final int _TOTAL_DOCUMENTS = 20;

}