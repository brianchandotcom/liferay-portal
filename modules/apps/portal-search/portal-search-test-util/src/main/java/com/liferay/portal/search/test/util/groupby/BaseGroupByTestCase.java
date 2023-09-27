/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.groupby;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.groupby.GroupByRequest;
import com.liferay.portal.search.groupby.GroupByRequestFactory;
import com.liferay.portal.search.groupby.GroupByResponse;
import com.liferay.portal.search.internal.groupby.GroupByRequestFactoryImpl;
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
					hits -> assertGroupedHitsFieldNames(
						"one",
						Arrays.asList(
							"companyId", "entryClassName", "entryClassPK",
							"groupId", SORT_FIELD, "uid", "userName"),
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
					hits -> assertGroupedHitsFieldNames(
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
					hits -> assertGroupedHitsFieldNames(
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
					hits -> assertGroupedHitsFieldNames(
						"one", Arrays.asList(fieldNames), hits,
						indexingTestHelper));
			});
	}

	@Test
	public void testGroupByDocsSize() throws Exception {
		indexDuplicates("five", 5);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						GroupBy groupBy = new GroupBy(GROUP_FIELD);

						groupBy.setSize(4);

						searchContext.setGroupBy(groupBy);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroups(
						toMap("five", "5|4"), hits, indexingTestHelper));
			});
	}

	@Test
	public void testGroupByDocsSortsScoreFieldAsc() throws Exception {
		assertGroupByDocsSortsScoreField(false);
	}

	@Test
	public void testGroupByDocsSortsScoreFieldDesc() throws Exception {
		assertGroupByDocsSortsScoreField(true);
	}

	@Test
	public void testGroupByDocsSortsSortFieldAsc() throws Exception {
		assertGroupByDocsSortsSortField(false);
	}

	@Test
	public void testGroupByDocsSortsSortFieldDesc() throws Exception {
		assertGroupByDocsSortsSortField(true);
	}

	@Test
	public void testGroupByDocsStart() throws Exception {
		Map<String, Integer> map1 = HashMapBuilder.put(
			"one", 1
		).put(
			"two", 2
		).build();

		Map<String, String> map2 = new HashMap<>();

		for (Map.Entry<String, Integer> entry : map1.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();

			indexDuplicates(key, value);

			map2.put(key, getCountPairString(value, value - 1));
		}

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						GroupBy groupBy = new GroupBy(GROUP_FIELD);

						groupBy.setStart(1);

						searchContext.setGroupBy(groupBy);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroups(map2, hits, indexingTestHelper));
			});
	}

	@Test
	public void testGroupByTermsSizeDefault() throws Exception {
		Map<String, Integer> map1 = HashMapBuilder.put(
			"eight", 2
		).put(
			"eleven", 2
		).put(
			"five", 2
		).put(
			"four", 2
		).put(
			"nine", 2
		).put(
			"one", 1
		).put(
			"seven", 2
		).put(
			"six", 2
		).put(
			"ten", 2
		).put(
			"three", 2
		).put(
			"two", 2
		).build();

		map1.forEach((key, value) -> indexDuplicates(key, value));

		map1.remove("one", 1);

		Map<String, String> map2 = new HashMap<>();

		for (Map.Entry<String, Integer> entry : map1.entrySet()) {
			map2.put(
				entry.getKey(),
				getCountPairString(entry.getValue(), entry.getValue()));
		}

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						GroupByRequest groupByRequest =
							groupByRequestFactory.getGroupByRequest(
								GROUP_FIELD);

						setTermsSortsAndDocsSize(groupByRequest);

						searchRequestBuilder.groupByRequests(groupByRequest);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroups(map2, hits, indexingTestHelper));
			});
	}

	@Test
	public void testGroupByTermsSizeLessThanDefault() throws Exception {
		indexDuplicates("one", 1);
		indexDuplicates("two", 2);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						GroupByRequest groupByRequest =
							groupByRequestFactory.getGroupByRequest(
								GROUP_FIELD);

						groupByRequest.setTermsSize(1);

						setTermsSortsAndDocsSize(groupByRequest);

						searchRequestBuilder.groupByRequests(groupByRequest);
					});

				try {
					indexingTestHelper.search();
				}
				catch (RuntimeException runtimeException) {
					if (_shouldIgnoreSearchEngineGlitchAndRetry(
							runtimeException)) {

						Assert.fail(runtimeException.getMessage());
					}

					throw runtimeException;
				}

				indexingTestHelper.verify(
					hits -> assertGroups(
						toMap("two", "2|2"), hits, indexingTestHelper));
			});
	}

	@Test
	public void testGroupByTermsSizeMoreThanDefault() throws Exception {
		Map<String, Integer> map1 = HashMapBuilder.put(
			"eight", 2
		).put(
			"eleven", 2
		).put(
			"five", 2
		).put(
			"four", 2
		).put(
			"nine", 2
		).put(
			"one", 1
		).put(
			"seven", 2
		).put(
			"six", 2
		).put(
			"ten", 2
		).put(
			"three", 2
		).put(
			"two", 2
		).build();

		Map<String, String> map2 = new HashMap<>();

		for (Map.Entry<String, Integer> entry : map1.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();

			indexDuplicates(key, value);

			map2.put(key, getCountPairString(value, value));
		}

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						GroupByRequest groupByRequest =
							groupByRequestFactory.getGroupByRequest(
								GROUP_FIELD);

						groupByRequest.setTermsSize(11);

						setTermsSortsAndDocsSize(groupByRequest);

						searchRequestBuilder.groupByRequests(groupByRequest);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroups(map2, hits, indexingTestHelper));
			});
	}

	@Test
	public void testGroupByTermsStart() throws Exception {
		indexDuplicates("one", 1);
		indexDuplicates("two", 2);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						GroupByRequest groupByRequest =
							groupByRequestFactory.getGroupByRequest(
								GROUP_FIELD);

						groupByRequest.setTermsSorts(
							new Sort(SORT_FIELD, Sort.STRING_TYPE, true));
						groupByRequest.setTermsStart(1);

						searchRequestBuilder.groupByRequests(groupByRequest);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroups(
						toMap("one", "1|1"), hits, indexingTestHelper));
			});
	}

	protected static String sort(Collection<String> collection) {
		List<String> list = new ArrayList<>(collection);

		Collections.sort(list);

		return list.toString();
	}

	protected void assertGroupByDocsSortsScoreField(boolean desc)
		throws Exception {

		indexDuplicates("one", 1);
		indexDuplicates("two", 2);
		indexDuplicates("three", 3);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						Sort[] sorts = new Sort[1];

						sorts[0] = new Sort(
							"scoreField", Sort.SCORE_TYPE, desc);

						GroupBy groupBy = new GroupBy(GROUP_FIELD);

						groupBy.setSize(3);
						groupBy.setSorts(sorts);

						searchContext.setGroupBy(groupBy);
					});

				BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

				booleanQueryImpl.addExactTerm(SORT_FIELD, "3");
				booleanQueryImpl.addExactTerm(SORT_FIELD, "2");

				booleanQueryImpl.add(
					getDefaultQuery(), BooleanClauseOccur.MUST);

				indexingTestHelper.setQuery(booleanQueryImpl);

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroupsSorted(hits, true, 3));
			});
	}

	protected void assertGroupByDocsSortsSortField(boolean desc)
		throws Exception {

		indexDuplicates("one", 2);
		indexDuplicates("two", 2);
		indexDuplicates("three", 3);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						Sort[] sorts = new Sort[1];

						sorts[0] = new Sort(SORT_FIELD, Sort.STRING_TYPE, desc);

						GroupBy groupBy = new GroupBy(GROUP_FIELD);

						groupBy.setSize(3);
						groupBy.setSorts(sorts);

						searchContext.setGroupBy(groupBy);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroupsSorted(hits, desc, 3));
			});
	}

	protected void assertGroupedHitsFieldNames(
		String key, Collection<String> expectedFieldNames, Hits hits,
		IndexingTestHelper indexingTestHelper) {

		Map<String, Hits> groupedHitsMap = hits.getGroupedHits();

		Hits groupedHits = groupedHitsMap.get(key);

		Assert.assertEquals(
			indexingTestHelper.getRequestString(), sort(expectedFieldNames),
			sort(getFieldNames(groupedHits)));
	}

	protected void assertGroups(
		Map<String, String> expectedCountsMap, Hits hits,
		IndexingTestHelper indexingTestHelper) {

		Map<String, String> actualCountsMap = new HashMap<>();

		Map<String, Hits> hitsMap = hits.getGroupedHits();

		for (Map.Entry<String, Hits> entry : hitsMap.entrySet()) {
			actualCountsMap.put(
				entry.getKey(), getCountPairString(entry.getValue()));
		}

		AssertUtils.assertEquals(
			indexingTestHelper.getRequestString(), expectedCountsMap,
			actualCountsMap);
	}

	protected void assertGroupsOrdered(
		List<String> expectedCountsList, Map<String, Hits> hitsMap,
		IndexingTestHelper indexingTestHelper) {

		List<String> actualCountsList = new ArrayList<>();

		for (Map.Entry<String, Hits> entry : hitsMap.entrySet()) {
			actualCountsList.add(
				getCountPairString(entry.getKey(), entry.getValue()));
		}

		AssertUtils.assertEquals(
			indexingTestHelper.getRequestString(), expectedCountsList,
			actualCountsList);
	}

	protected void assertGroupsSorted(
		Hits hits, boolean desc, int minDocCount) {

		Map<String, Hits> groupedHits = hits.getGroupedHits();

		int maxDocCount = 0;

		for (Map.Entry<String, Hits> entry : groupedHits.entrySet()) {
			Hits groupHits = entry.getValue();

			Document[] documents = groupHits.getDocs();

			if (documents.length > maxDocCount) {
				maxDocCount = documents.length;
			}

			for (int i = 0; i < documents.length; i++) {
				Document document = documents[i];

				String sortFieldValue = document.get(SORT_FIELD);

				if (desc) {
					Assert.assertEquals(
						document.toString(),
						String.valueOf(documents.length - i), sortFieldValue);
				}
				else {
					Assert.assertEquals(
						document.toString(), String.valueOf(i + 1),
						sortFieldValue);
				}
			}
		}

		Assert.assertEquals(maxDocCount, minDocCount);
	}

	protected void assertMultipleGroupsOrdered(
		Map<String, List<String>> expectedCountsMap,
		List<GroupByResponse> groupByResponses,
		IndexingTestHelper indexingTestHelper) {

		for (GroupByResponse groupByResponse : groupByResponses) {
			List<String> expectedCountsList = expectedCountsMap.get(
				groupByResponse.getField());

			assertGroupsOrdered(
				expectedCountsList, groupByResponse.getHitsMap(),
				indexingTestHelper);
		}
	}

	protected String getCountPairString(Hits hits) {
		Document[] docs = hits.getDocs();

		return getCountPairString(hits.getLength(), docs.length);
	}

	protected String getCountPairString(int hitsCount, int docsCount) {
		return hitsCount + StringPool.PIPE + docsCount;
	}

	protected String getCountPairString(String key, Hits hits) {
		Document[] docs = hits.getDocs();

		return key + StringPool.PIPE +
			getCountPairString(hits.getLength(), docs.length);
	}

	protected Collection<String> getFieldNames(Hits hits) {
		Assert.assertNotNull(hits);

		Assert.assertNotEquals(0, hits.getLength());

		Document document = hits.doc(0);

		Map<String, Field> fields = document.getFields();

		Assert.assertFalse(fields.isEmpty());

		return fields.keySet();
	}

	protected void indexDuplicates(String name, int count) {
		String field = GROUP_FIELD;

		for (int i = 1; i <= count; i++) {
			try {
				addDocument(
					DocumentCreationHelpers.twoKeywords(
						field, name, SORT_FIELD, String.valueOf(i)));
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	protected void setTermsSortsAndDocsSize(GroupByRequest groupByRequest) {
		groupByRequest.setTermsSorts(
			new Sort(SORT_FIELD, Sort.STRING_TYPE, true));
		groupByRequest.setDocsSize(2);
	}

	protected static final String GROUP_FIELD = Field.USER_NAME;

	protected static final String SORT_FIELD =
		Field.USER_ID + StringPool.UNDERLINE + Field.SORTABLE_FIELD_SUFFIX;

	protected final GroupByRequestFactory groupByRequestFactory =
		new GroupByRequestFactoryImpl();

	private boolean _shouldIgnoreSearchEngineGlitchAndRetry(
		RuntimeException runtimeException) {

		Throwable throwable1 = runtimeException.getCause();

		Throwable throwable2 = throwable1.getCause();

		String message = throwable2.getMessage();

		if (message.equals(
				"numHits must be > 0; please use TotalHitCountCollector if " +
					"you just need the total hit count")) {

			return true;
		}

		return false;
	}

}