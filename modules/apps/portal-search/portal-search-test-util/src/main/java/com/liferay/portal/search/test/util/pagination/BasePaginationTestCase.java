/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.pagination;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Preston Crary
 * @author André de Oliveira
 */
public abstract class BasePaginationTestCase extends BaseIndexingTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		for (int i = 0; i < _TOTAL_DOCUMENTS; i++) {
			addDocument(
				DocumentCreationHelpers.singleText(
					"test-field",
					StringUtil.toLowerCase(testName.getMethodName())));
		}
	}

	@Test
	public void testBadEnd() throws Exception {
		_assertBadRange(QueryUtil.ALL_POS, -2, "Invalid end -2");
	}

	@Test
	public void testBadStart() throws Exception {
		_assertBadRange(-2, QueryUtil.ALL_POS, "Invalid start -2");
	}

	@Test
	public void testFirst() throws Exception {
		_assertPagination(0, 1, 1);
	}

	@Test
	public void testFirstUnbounded() throws Exception {
		_assertPagination(QueryUtil.ALL_POS, 1, 1);
	}

	@Test
	public void testLast() throws Exception {
		_assertPagination(_TOTAL_DOCUMENTS - 1, _TOTAL_DOCUMENTS, 1);
	}

	@Test
	public void testLastUnbounded() throws Exception {
		_assertPagination(_TOTAL_DOCUMENTS - 1, QueryUtil.ALL_POS, 1);
	}

	@Test
	public void testMiddle() throws Exception {
		_assertPagination(5, 8, 3);
	}

	@Test
	public void testMiddleOne() throws Exception {
		_assertPagination(5, 6, 1);
	}

	@Test
	public void testMiddleUntilPastLast() throws Exception {
		_assertPagination(_TOTAL_DOCUMENTS - 4, _TOTAL_DOCUMENTS + 4, 4);
	}

	@Test
	public void testNextToLast() throws Exception {
		_assertPagination(_TOTAL_DOCUMENTS - 2, _TOTAL_DOCUMENTS - 1, 1);
	}

	@Test
	public void testNone() throws Exception {
		_assertPagination(0, 0, 0);
	}

	@Test
	public void testPastLast() throws Exception {
		_assertPagination(_TOTAL_DOCUMENTS, _TOTAL_DOCUMENTS + 1, 1);
	}

	@Test
	public void testUnbounded() throws Exception {
		_assertPagination(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, _TOTAL_DOCUMENTS);
	}

	@Test
	public void testWayPastLast() throws Exception {
		_assertPagination(_TOTAL_DOCUMENTS + 5, _TOTAL_DOCUMENTS + 6, 1);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private void _assertBadRange(int start, int end, String message) {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(message);

		search(_createSearchContext(start, end));
	}

	private void _assertPagination(int start, int end, int expectedSize)
		throws Exception {

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						searchContext.setEnd(end);
						searchContext.setStart(start);
					});

				indexingTestHelper.setQuery(getDefaultQuery());

				Assert.assertEquals(
					_TOTAL_DOCUMENTS, indexingTestHelper.searchCount());

				indexingTestHelper.search();

				indexingTestHelper.assertResultCount(expectedSize);
			});
	}

	private SearchContext _createSearchContext(int start, int end) {
		SearchContext searchContext = createSearchContext();

		searchContext.setEnd(end);
		searchContext.setStart(start);

		return searchContext;
	}

	private static final int _TOTAL_DOCUMENTS = 20;

}