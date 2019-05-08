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

package com.liferay.portal.search.elasticsearch6.internal.logging;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.search.elasticsearch6.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import org.elasticsearch.action.search.SearchPhaseExecutionException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Bryan Engler
 */
public class ElasticsearchIndexSearcherExceptionsTest
	extends BaseIndexingTestCase {

	@Test
	public void testExceptionThrownWhenQueryMalformedSearch() {
		expectedException.expect(SearchPhaseExecutionException.class);
		expectedException.expectMessage("all shards failed");

		search(createSearchContext(), getMalformedQuery());
	}

	@Test
	public void testExceptionThrownWhenQueryMalformedSearchCount() {
		expectedException.expect(SearchPhaseExecutionException.class);
		expectedException.expectMessage("all shards failed");

		searchCount(createSearchContext(), getMalformedQuery());
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	protected Query getMalformedQuery() {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		booleanQueryImpl.add(
			new TermQueryImpl(Field.EXPIRATION_DATE, "text"),
			BooleanClauseOccur.MUST);

		return booleanQueryImpl;
	}

}