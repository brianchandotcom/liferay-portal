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

package com.liferay.portal.search.unit.test.groupby;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.unit.test.BaseIndexingTestCase;

import java.util.Arrays;
import java.util.Map;

import org.junit.Assert;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public abstract class BaseGroupByTestCase extends BaseIndexingTestCase {

	protected void addDocuments(final String name, int count) throws Exception {
		final String field = GROUP_FIELD;

		for (int i = 1; i <= count; i++) {
			addDocument(
				new DocumentCreationHelper() {

					@Override
					public void populate(Document document) {
						document.addKeyword(field, name);
					}

				});
		}
	}

	protected void assertGroup(
		String key, int hitsCount, int docsCount,
		Map<String, Hits> groupedHitsMap) {

		Hits hits = groupedHitsMap.get(key);

		Assert.assertNotNull(hits);
		Assert.assertEquals(hits.toString(), hitsCount, hits.getLength());

		Document[] docs = hits.getDocs();

		Assert.assertEquals(Arrays.toString(docs), docsCount, docs.length);
	}

	protected void assertGroup(
		String key, int count, Map<String, Hits> groupedHitsMap) {

		assertGroup(key, count, count, groupedHitsMap);
	}

	protected Map<String, Hits> searchGroups(SearchContext searchContext)
		throws Exception {

		Hits hits = search(searchContext);

		Map<String, Hits> groupedHitsMap = hits.getGroupedHits();

		Assert.assertNotNull(groupedHitsMap);

		return groupedHitsMap;
	}

	protected void testGroupBy() throws Exception {
		addDocuments("sixteen", 16);
		addDocuments("three", 3);
		addDocuments("two", 2);

		SearchContext searchContext = createSearchContext();

		searchContext.setGroupBy(new GroupBy(GROUP_FIELD));

		Map<String, Hits> groupedHitsMap = searchGroups(searchContext);

		Assert.assertEquals(3, groupedHitsMap.size());

		assertGroup("sixteen", 16, groupedHitsMap);
		assertGroup("three", 3, groupedHitsMap);
		assertGroup("two", 2, groupedHitsMap);
	}

	protected void testStartAndEnd() throws Exception {
		addDocuments("sixteen", 16);

		SearchContext searchContext = createSearchContext();

		searchContext.setEnd(9);
		searchContext.setGroupBy(new GroupBy(GROUP_FIELD));
		searchContext.setStart(4);

		Map<String, Hits> groupedHitsMap = searchGroups(searchContext);

		assertGroup("sixteen", 16, 6, groupedHitsMap);
	}

	protected void testStartAndSize() throws Exception {
		addDocuments("sixteen", 16);

		SearchContext searchContext = createSearchContext();

		GroupBy groupBy = new GroupBy(GROUP_FIELD);

		groupBy.setSize(3);
		groupBy.setStart(8);

		searchContext.setGroupBy(groupBy);

		Map<String, Hits> groupedHitsMap = searchGroups(searchContext);

		assertGroup("sixteen", 16, 3, groupedHitsMap);
	}

	protected static final String GROUP_FIELD = Field.USER_NAME;

}