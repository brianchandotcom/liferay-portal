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

package com.liferay.portal.search.test.util.indexing;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Preston Crary
 * @author André de Oliveira
 */
public abstract class BaseIndexSearcherTestCase extends BaseIndexingTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		for (int i = 0; i < _TOTAL_DOCUMENTS; i++) {
			addDocument(
				DocumentCreationHelpers.singleText(
					"test-field",
					RandomTestUtil.randomString(
						UniqueStringRandomizerBumper.INSTANCE)));
		}
	}

	@Test
	public void testCountWithoutStartAndEnd() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();

		SearchContext searchContext = createSearchContext();

		long count = indexSearcher.searchCount(
			searchContext, getDefaultQuery());

		Assert.assertEquals(_TOTAL_DOCUMENTS, count);
	}

	@Test
	public void testCountWithStartAndEnd() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();

		SearchContext searchContext = createSearchContext();

		searchContext.setEnd(QueryUtil.ALL_POS);
		searchContext.setStart(QueryUtil.ALL_POS);

		long count = indexSearcher.searchCount(
			searchContext, getDefaultQuery());

		Assert.assertEquals(_TOTAL_DOCUMENTS, count);
	}

	@Test
	public void testSearchPaginationAfter() throws Exception {
		_assertPagination(21, 22, 1);
	}

	@Test
	public void testSearchPaginationAll() throws Exception {
		_assertPagination(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, _TOTAL_DOCUMENTS);
	}

	@Test
	public void testSearchPaginationFirst() throws Exception {
		_assertPagination(QueryUtil.ALL_POS, 1, 1);
	}

	@Test
	public void testSearchPaginationInvalidEnd() throws Exception {
		try {
			_assertPagination(1, -2, 0);
		}
		catch (SearchException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(IllegalArgumentException.class, cause.getClass());
			Assert.assertEquals("Invalid end -2", cause.getMessage());
		}
	}

	@Test
	public void testSearchPaginationInvalidStart() throws Exception {
		try {
			_assertPagination(-2, 1, 0);
		}
		catch (SearchException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(IllegalArgumentException.class, cause.getClass());
			Assert.assertEquals("Invalid start -2", cause.getMessage());
		}
	}

	@Test
	public void testSearchPaginationLast() throws Exception {
		_assertPagination(19, QueryUtil.ALL_POS, 1);
	}

	@Test
	public void testSearchPaginationMiddle() throws Exception {
		_assertPagination(5, 15, 10);
	}

	@Test
	public void testSearchPaginationNone() throws Exception {
		_assertPagination(0, 0, 0);
	}

	private void _assertPagination(int start, int end, int expectedSize)
		throws Exception {

		IndexSearcher indexSearcher = getIndexSearcher();

		SearchContext searchContext = createSearchContext();

		searchContext.setEnd(end);
		searchContext.setStart(start);

		Hits hits = indexSearcher.search(searchContext, getDefaultQuery());

		Assert.assertEquals(
			hits.toString(), _TOTAL_DOCUMENTS, hits.getLength());

		Document[] documents = hits.getDocs();

		Assert.assertEquals(
			Arrays.toString(documents), expectedSize, documents.length);
	}

	private static final int _TOTAL_DOCUMENTS = 20;

}