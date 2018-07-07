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

package com.liferay.portal.search.solr.internal.groupby;

import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.solr.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.groupby.BaseGroupByTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author Tibor Lipusz
 * @author André de Oliveira
 */
public class GroupByTest extends BaseGroupByTestCase {

	@Test
	public void testSolrReturnsGroupedHitsOnly() throws Exception {
		addDocuments("one", 1);

		SearchContext searchContext = createSearchContext();

		searchContext.setGroupBy(new GroupBy(GROUP_FIELD));

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				assertGroupedHitsOnly("one", searchContext);

				return null;
			});
	}

	protected void assertGroupedHitsOnly(
			String key, SearchContext searchContext)
		throws Exception {

		Hits hits1 = search(searchContext);

		Assert.assertEquals(hits1.toString(), 0, hits1.getLength());

		Map<String, Hits> groupedHitsMap = hits1.getGroupedHits();

		Hits hits2 = groupedHitsMap.get(key);

		Assert.assertNotNull(hits2);

		Assert.assertNotEquals(0, hits2.getLength());
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return new SolrIndexingFixture();
	}

}