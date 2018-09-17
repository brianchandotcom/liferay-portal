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
import com.liferay.portal.search.solr.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.groupby.BaseGroupByTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import java.util.Arrays;
import java.util.Collections;

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
		indexDuplicates("one", 1);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> searchContext.setGroupBy(
						new GroupBy(GROUP_FIELD)));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> {
						Assert.assertEquals(
							indexingTestHelper.getQueryString(),
							Collections.emptyList(),
							Arrays.asList(hits.getDocs()));

						assertGroups(
							toMap("one", "1|1"), hits, indexingTestHelper);
					});
			});
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return new SolrIndexingFixture();
	}

}