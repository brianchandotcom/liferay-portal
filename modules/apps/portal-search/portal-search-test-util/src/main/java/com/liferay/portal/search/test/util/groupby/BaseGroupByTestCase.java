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

package com.liferay.portal.search.test.util.groupby;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author André de Oliveira
 * @author Tibor Lipusz
 */
public abstract class BaseGroupByTestCase extends BaseIndexingTestCase {

	@Test
	public void testDefaultFieldNames() throws Exception {
		addDocuments("one", 1);

		SearchContext searchContext = createSearchContext();

		searchContext.setGroupBy(new GroupBy(GROUP_FIELD));

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				assertFieldNames(
					"one",
					new String[] {
						"companyId", "entryClassName", "entryClassPK",
						"groupId", "uid", "userName"
					},
					searchContext);

				return null;
			});
	}

	@Test
	public void testGroupBy() throws Exception {
		addDocuments("sixteen", 16);
		addDocuments("three", 3);
		addDocuments("two", 2);

		SearchContext searchContext = createSearchContext();

		searchContext.setGroupBy(new GroupBy(GROUP_FIELD));

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Map<String, Hits> groupedHitsMap = searchGroups(searchContext);

				Assert.assertEquals(
					groupedHitsMap.toString(), 3, groupedHitsMap.size());

				assertGroup("sixteen", 16, groupedHitsMap);
				assertGroup("three", 3, groupedHitsMap);
				assertGroup("two", 2, groupedHitsMap);

				return null;
			});
	}

	@Test
	public void testSelectedFieldNames() throws Exception {
		addDocuments("one", 1);

		SearchContext searchContext = createSearchContext();

		searchContext.setGroupBy(new GroupBy(GROUP_FIELD));

		QueryConfig queryConfig = searchContext.getQueryConfig();

		String[] fieldNames = {Field.COMPANY_ID, Field.UID};

		queryConfig.addSelectedFieldNames(fieldNames);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				assertFieldNames("one", fieldNames, searchContext);

				return null;
			});
	}

	@Test
	public void testStartAndEnd() throws Exception {
		addDocuments("sixteen", 16);

		SearchContext searchContext = createSearchContext();

		searchContext.setEnd(9);
		searchContext.setGroupBy(new GroupBy(GROUP_FIELD));
		searchContext.setStart(4);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Map<String, Hits> groupedHitsMap = searchGroups(searchContext);

				assertGroup("sixteen", 16, 6, groupedHitsMap);

				return null;
			});
	}

	@Test
	public void testStartAndSize() throws Exception {
		addDocuments("sixteen", 16);

		SearchContext searchContext = createSearchContext();

		GroupBy groupBy = new GroupBy(GROUP_FIELD);

		groupBy.setSize(3);
		groupBy.setStart(8);

		searchContext.setGroupBy(groupBy);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Map<String, Hits> groupedHitsMap = searchGroups(searchContext);

				assertGroup("sixteen", 16, 3, groupedHitsMap);

				return null;
			});
	}

	protected static String sort(Collection<String> collection) {
		List<String> list = new ArrayList<>(collection);

		Collections.sort(list);

		return list.toString();
	}

	protected void addDocuments(final String name, int count) throws Exception {
		String field = GROUP_FIELD;

		for (int i = 1; i <= count; i++) {
			addDocument(DocumentCreationHelpers.singleKeyword(field, name));
		}
	}

	protected void assertFieldNames(
			String key, String[] fieldNames, SearchContext searchContext)
		throws Exception {

		Map<String, Hits> groupedHitsMap = searchGroups(searchContext);

		Hits hits = groupedHitsMap.get(key);

		Assert.assertNotNull(hits);

		Assert.assertEquals(
			sort(Arrays.asList(fieldNames)), sort(getFieldNames(hits.doc(0))));
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

	protected Set<String> getFieldNames(Document document) {
		Map<String, Field> fields = document.getFields();

		Assert.assertFalse(fields.isEmpty());

		return fields.keySet();
	}

	protected Map<String, Hits> searchGroups(SearchContext searchContext)
		throws Exception {

		Hits hits = search(searchContext);

		Map<String, Hits> groupedHitsMap = hits.getGroupedHits();

		Assert.assertNotNull(groupedHitsMap);

		return groupedHitsMap;
	}

	protected static final String GROUP_FIELD = Field.USER_NAME;

}