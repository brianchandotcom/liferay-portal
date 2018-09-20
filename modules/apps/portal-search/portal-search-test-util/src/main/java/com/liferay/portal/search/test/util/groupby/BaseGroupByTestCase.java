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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.search.test.util.AssertUtils;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author André de Oliveira
 * @author Tibor Lipusz
 */
public abstract class BaseGroupByTestCase extends BaseIndexingTestCase {

	@Test
	public void testFieldNamesDefault() throws Exception {
		indexDuplicates("one", 1);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> searchContext.setGroupBy(
						new GroupBy(GROUP_FIELD)));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertFieldNames(
						"one",
						Arrays.asList(
							"companyId", "entryClassName", "entryClassPK",
							"groupId", "uid", "userName"),
						hits, indexingTestHelper));
			});
	}

	@Test
	public void testFieldNamesSame() throws Exception {
		indexDuplicates("one", 1);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> searchContext.setGroupBy(
						new GroupBy(GROUP_FIELD)));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertFieldNames(
						"one", getFieldNames(hits), hits, indexingTestHelper));
			});
	}

	@Test
	public void testFieldNamesSameWithSelected() throws Exception {
		indexDuplicates("one", 1);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						searchContext.setGroupBy(new GroupBy(GROUP_FIELD));

						QueryConfig queryConfig =
							searchContext.getQueryConfig();

						queryConfig.addSelectedFieldNames(
							Field.COMPANY_ID, Field.UID);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertFieldNames(
						"one", getFieldNames(hits), hits, indexingTestHelper));
			});
	}

	@Test
	public void testFieldNamesSelected() throws Exception {
		indexDuplicates("one", 1);

		assertSearch(
			indexingTestHelper -> {
				String[] fieldNames = {Field.COMPANY_ID, Field.UID};

				indexingTestHelper.define(
					searchContext -> {
						searchContext.setGroupBy(new GroupBy(GROUP_FIELD));

						QueryConfig queryConfig =
							searchContext.getQueryConfig();

						queryConfig.addSelectedFieldNames(fieldNames);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertFieldNames(
						"one", Arrays.asList(fieldNames), hits,
						indexingTestHelper));
			});
	}

	@Test
	public void testGroupBy() throws Exception {
		Map<String, Integer> map1 = new HashMap<String, Integer>() {
			{
				put("sixteen", 16);
				put("three", 3);
				put("two", 2);
			}
		};

		map1.forEach((key, value) -> indexDuplicates(key, value));

		Set<Map.Entry<String, Integer>> entries = map1.entrySet();

		Map<String, String> map2 = entries.stream().collect(
			Collectors.toMap(
				Map.Entry::getKey,
				entry -> getCountPairString(
					entry.getValue(), entry.getValue())));

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> searchContext.setGroupBy(
						new GroupBy(GROUP_FIELD)));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroups(map2, hits, indexingTestHelper));
			});
	}

	@Test
	public void testStartAndEnd() throws Exception {
		indexDuplicates("sixteen", 16);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						searchContext.setEnd(9);
						searchContext.setGroupBy(new GroupBy(GROUP_FIELD));
						searchContext.setStart(4);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroups(
						toMap("sixteen", "16|6"), hits, indexingTestHelper));
			});
	}

	@Test
	public void testStartAndSize() throws Exception {
		indexDuplicates("sixteen", 16);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						GroupBy groupBy = new GroupBy(GROUP_FIELD);

						groupBy.setSize(3);
						groupBy.setStart(8);

						searchContext.setGroupBy(groupBy);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroups(
						toMap("sixteen", "16|3"), hits, indexingTestHelper));
			});
	}

	protected static String sort(Collection<String> collection) {
		List<String> list = new ArrayList<>(collection);

		Collections.sort(list);

		return list.toString();
	}

	protected void assertFieldNames(
		String key, Collection<String> fieldNames, Hits hits1,
		IndexingTestHelper indexingTestHelper) {

		Map<String, Hits> hitsMap = hits1.getGroupedHits();

		Hits hits2 = hitsMap.get(key);

		Assert.assertEquals(
			indexingTestHelper.getQueryString(), sort(fieldNames),
			sort(getFieldNames(hits2)));
	}

	protected void assertGroups(
		Map<String, String> expectedCountsMap, Hits hits,
		IndexingTestHelper indexingTestHelper) {

		Map<String, Hits> hitsMap = hits.getGroupedHits();

		Collection<Map.Entry<String, Hits>> entries = hitsMap.entrySet();

		Map<String, String> actualCountsMap = entries.stream().collect(
			Collectors.toMap(
				Map.Entry::getKey,
				entry -> getCountPairString(entry.getValue())));

		AssertUtils.assertEquals(
			indexingTestHelper.getQueryString(), expectedCountsMap,
			actualCountsMap);
	}

	protected String getCountPairString(Hits hits) {
		Document[] docs = hits.getDocs();

		return getCountPairString(hits.getLength(), docs.length);
	}

	protected String getCountPairString(int hitsCount, int docsCount) {
		return hitsCount + StringPool.PIPE + docsCount;
	}

	protected Collection<String> getFieldNames(Hits hits) {
		Assert.assertNotNull(hits);

		Assert.assertNotEquals(0, hits.getLength());

		Document document = hits.doc(0);

		Map<String, Field> fields = document.getFields();

		Assert.assertFalse(fields.isEmpty());

		return fields.keySet();
	}

	protected void indexDuplicates(final String name, int count) {
		String field = GROUP_FIELD;

		for (int i = 1; i <= count; i++) {
			try {
				addDocument(DocumentCreationHelpers.singleKeyword(field, name));
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected static final String GROUP_FIELD = Field.USER_NAME;

}