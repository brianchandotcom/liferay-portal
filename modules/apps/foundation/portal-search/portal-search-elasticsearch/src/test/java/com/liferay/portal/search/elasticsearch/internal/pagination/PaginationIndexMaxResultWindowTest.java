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

package com.liferay.portal.search.elasticsearch.internal.pagination;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch.internal.ElasticsearchIndexingFixture;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.internal.connection.IndexCreator;
import com.liferay.portal.search.elasticsearch.internal.connection.IndicesAdminClientSupplier;
import com.liferay.portal.search.elasticsearch.internal.connection.LiferayIndexCreationHelper;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.search.query.QueryPhaseExecutionException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author André de Oliveira
 */
public class PaginationIndexMaxResultWindowTest extends BaseIndexingTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		for (int i = 0; i < TOTAL_DOCUMENTS; i++) {
			addDocument(
				DocumentCreationHelpers.singleText(FIELD, String.valueOf(i)));
		}
	}

	@Test
	public void testResultWindowIsTooLarge() throws Exception {
		int start = 1;

		int end = start + INDEX_MAX_RESULT_WINDOW;

		Assert.assertTrue(end < TOTAL_DOCUMENTS);

		expectedException.expect(QueryPhaseExecutionException.class);
		expectedException.expectMessage(
			"Result window is too large, from + size must be less than or " +
				"equal to: [" + INDEX_MAX_RESULT_WINDOW + "] but was [" + end +
					"]");

		try {
			search(_createSearchContext(start, end));
		}
		catch (SearchException se) {
			Throwable t = se.getCause();

			throw (Exception)t.getCause();
		}
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Override
	protected IndexingFixture createIndexingFixture() {
		ElasticsearchFixture elasticsearchFixture = new ElasticsearchFixture(
			PaginationTest.class.getSimpleName());

		IndexCreator indexCreator = new IndexCreator(elasticsearchFixture);

		indexCreator.setIndexCreationHelper(
			new UnpaginableIndexCreationHelper(elasticsearchFixture));

		return new ElasticsearchIndexingFixture(
			elasticsearchFixture, BaseIndexingTestCase.COMPANY_ID,
			indexCreator);
	}

	protected static final String FIELD = RandomTestUtil.randomString();

	protected static final int INDEX_MAX_RESULT_WINDOW = 5;

	protected static final int TOTAL_DOCUMENTS = INDEX_MAX_RESULT_WINDOW + 3;

	private SearchContext _createSearchContext(int start, int end) {
		SearchContext searchContext = createSearchContext();

		searchContext.setEnd(end);
		searchContext.setStart(start);

		return searchContext;
	}

	private static class UnpaginableIndexCreationHelper
		extends LiferayIndexCreationHelper {

		public UnpaginableIndexCreationHelper(
			IndicesAdminClientSupplier indicesAdminClientSupplier) {

			super(indicesAdminClientSupplier);
		}

		@Override
		public void contributeIndexSettings(Settings.Builder builder) {
			super.contributeIndexSettings(builder);

			builder.put("index.max_result_window", INDEX_MAX_RESULT_WINDOW);
		}

	}

}