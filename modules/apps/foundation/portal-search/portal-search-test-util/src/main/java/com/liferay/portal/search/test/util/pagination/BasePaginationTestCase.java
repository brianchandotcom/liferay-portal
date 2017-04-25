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

package com.liferay.portal.search.test.util.pagination;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.After;
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

		for (int i = 0; i < TOTAL_DOCUMENTS; i++) {
			addDocument(
				DocumentCreationHelpers.singleText(
					"test-field",
					RandomTestUtil.randomString(
						UniqueStringRandomizerBumper.INSTANCE)));
		}
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testBadEnd() throws Exception {
		_assertBadRange(QueryUtil.ALL_POS, -2, "End cannot be negative: -2");
	}

	@Test
	public void testBadRange() throws Exception {
		_assertBadRange(10, 5, "End cannot be less than start: 5, 10");
	}

	@Test
	public void testBadStart() throws Exception {
		_assertBadRange(-2, QueryUtil.ALL_POS, "Start cannot be negative: -2");
	}

	@Test
	public void testCount() throws Exception {
		_assertSearchCountFindsAll(createSearchContext());
	}

	@Test
	public void testCountIgnoresPagination() throws Exception {
		_assertSearchCountFindsAll(_createSearchContext(11, 10));
	}

	@Test
	public void testCountUnbounded() throws Exception {
		_assertSearchCountFindsAll(
			_createSearchContext(QueryUtil.ALL_POS, QueryUtil.ALL_POS));
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
		_assertPagination(TOTAL_DOCUMENTS - 1, TOTAL_DOCUMENTS, 1);
	}

	@Test
	public void testLastUnbounded() throws Exception {
		_assertPagination(TOTAL_DOCUMENTS - 1, QueryUtil.ALL_POS, 1);
	}

	@Test
	public void testMiddle() throws Exception {
		_assertPagination(5, 8, 3);
	}

	@Test
	public void testMiddleNone() throws Exception {
		_assertPagination(5, 5, 0);
	}

	@Test
	public void testMiddleOne() throws Exception {
		_assertPagination(5, 6, 1);
	}

	@Test
	public void testMiddleUntilPastLast() throws Exception {
		_assertPagination(TOTAL_DOCUMENTS - 4, TOTAL_DOCUMENTS + 4, 4);
	}

	@Test
	public void testNextToLast() throws Exception {
		_assertPagination(TOTAL_DOCUMENTS - 2, TOTAL_DOCUMENTS - 1, 1);
	}

	@Test
	public void testNone() throws Exception {
		_assertPagination(0, 0, 0);
	}

	@Test
	public void testPastLast() throws Exception {
		_assertPagination(TOTAL_DOCUMENTS, TOTAL_DOCUMENTS + 1, 1);
	}

	@Test
	public void testPastLastUnbounded() throws Exception {
		_assertPagination(
			TOTAL_DOCUMENTS, QueryUtil.ALL_POS,
			DEFAULT_SEARCH_ENGINE_RESULT_WINDOW);
	}

	@Test
	public void testUnbounded() throws Exception {
		_assertPagination(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			DEFAULT_SEARCH_ENGINE_RESULT_WINDOW);
	}

	@Test
	public void testWayPastLast() throws Exception {
		_assertPagination(TOTAL_DOCUMENTS + 5, TOTAL_DOCUMENTS + 6, 1);
	}

	@Test
	public void testWayPastLastUnbounded() throws Exception {
		_assertPagination(
			TOTAL_DOCUMENTS + 5, QueryUtil.ALL_POS,
			DEFAULT_SEARCH_ENGINE_RESULT_WINDOW);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	protected static final int DEFAULT_SEARCH_ENGINE_RESULT_WINDOW = 10;

	protected static final int TOTAL_DOCUMENTS = 20;

	private void _assertBadRange(int start, int end, String message)
		throws Exception {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(message);

		try {
			search(_createSearchContext(start, end));
		}
		catch (SearchException se) {
			throw (Exception)se.getCause();
		}
	}

	private void _assertPagination(
			int start, int end, int expectedDocumentsLength)
		throws Exception {

		SearchContext searchContext = _createSearchContext(start, end);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Hits hits = search(searchContext);

				Assert.assertEquals(
					hits.toString(), TOTAL_DOCUMENTS, hits.getLength());

				Document[] documents = hits.getDocs();

				Assert.assertEquals(
					Arrays.toString(documents), expectedDocumentsLength,
					documents.length);

				return null;
			});
	}

	private void _assertSearchCountFindsAll(SearchContext searchContext)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				IndexSearcher indexSearcher = getIndexSearcher();

				long count = indexSearcher.searchCount(
					searchContext, getDefaultQuery());

				Assert.assertEquals(TOTAL_DOCUMENTS, count);

				return null;
			});
	}

	private SearchContext _createSearchContext(int start, int end) {
		SearchContext searchContext = createSearchContext();

		searchContext.setEnd(end);
		searchContext.setStart(start);

		return searchContext;
	}

}