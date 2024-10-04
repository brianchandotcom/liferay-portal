/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.groupby;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.groupby.GroupByRequest;
import com.liferay.portal.search.groupby.GroupByResponse;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.groupby.BaseGroupByTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author André de Oliveira
 * @author Tibor Lipusz
 * @author Petteri Karttunen
 */
public class GroupByTest extends BaseGroupByTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Test
	public void testGroupByDocsSizeDefault() throws Exception {
		indexDuplicates("five", 5);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> searchContext.setGroupBy(
						new GroupBy(GROUP_FIELD)));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroups(
						toMap("five", "5|3"), hits, indexingTestHelper));
			});
	}

	@Test
	public void testGroupByDocsSizeZero() throws Exception {
		indexDuplicates("five", 5);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						GroupBy groupBy = new GroupBy(GROUP_FIELD);

						groupBy.setSize(0);

						searchContext.setGroupBy(groupBy);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroups(
						toMap("five", "5|3"), hits, indexingTestHelper));
			});
	}

	@Test
	public void testGroupByTermsSortsCountAscKeyAsc() throws Exception {
		List<String> orderedResults = new ArrayList<>();

		orderedResults.add("one|2|2");
		orderedResults.add("two|2|2");
		orderedResults.add("three|3|3");

		_assertGroupByTermsSortsCountDescKeyDesc(false, false, orderedResults);
	}

	@Test
	public void testGroupByTermsSortsCountAscKeyDesc() throws Exception {
		List<String> orderedResults = new ArrayList<>();

		orderedResults.add("two|2|2");
		orderedResults.add("one|2|2");
		orderedResults.add("three|3|3");

		_assertGroupByTermsSortsCountDescKeyDesc(false, true, orderedResults);
	}

	@Test
	public void testGroupByTermsSortsCountDescKeyAsc() throws Exception {
		List<String> orderedResults = new ArrayList<>();

		orderedResults.add("three|3|3");
		orderedResults.add("one|2|2");
		orderedResults.add("two|2|2");

		_assertGroupByTermsSortsCountDescKeyDesc(true, false, orderedResults);
	}

	@Test
	public void testGroupByTermsSortsCountDescKeyDesc() throws Exception {
		List<String> orderedResults = new ArrayList<>();

		orderedResults.add("three|3|3");
		orderedResults.add("two|2|2");
		orderedResults.add("one|2|2");

		_assertGroupByTermsSortsCountDescKeyDesc(true, true, orderedResults);
	}

	@Test
	public void testGroupByTermsSortsDefault() throws Exception {
		List<String> orderedResults = new ArrayList<>();

		orderedResults.add("three|3|3");
		orderedResults.add("one|2|2");
		orderedResults.add("two|2|2");

		_indexTermsSortsDuplicates();

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						GroupBy groupBy = new GroupBy(GROUP_FIELD);

						searchContext.setGroupBy(groupBy);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroupsOrdered(
						orderedResults, hits.getGroupedHits(),
						indexingTestHelper));
			});
	}

	@Test
	public void testMultipleGroupByRequests() throws Exception {
		indexDuplicates("three", 3);
		indexDuplicates("two", 2);

		Map<String, List<String>> orderedResultsMap =
			HashMapBuilder.<String, List<String>>put(
				GROUP_FIELD, ListUtil.fromArray("three|3|3", "two|2|2")
			).put(
				SORT_FIELD, ListUtil.fromArray("1|2|2", "2|2|2", "3|1|1")
			).build();

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						GroupByRequest groupByRequest1 =
							groupByRequestFactory.getGroupByRequest(
								GROUP_FIELD);
						GroupByRequest groupByRequest2 =
							groupByRequestFactory.getGroupByRequest(SORT_FIELD);

						searchRequestBuilder.groupByRequests(
							groupByRequest1, groupByRequest2);
					});

				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse -> {
						List<GroupByResponse> groupByResponses =
							searchResponse.getGroupByResponses();

						Assert.assertEquals(
							groupByResponses.toString(), 2,
							groupByResponses.size());

						assertMultipleGroupsOrdered(
							orderedResultsMap, groupByResponses,
							indexingTestHelper);
					});
			});
	}

	@Override
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
							"scoreField", Sort.SCORE_TYPE, !desc);

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
					hits -> assertGroupsSorted(hits, desc, 3));
			});
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

	@Override
	protected Collection<String> getFieldNames(Hits hits) {
		Set<String> fieldNames = new HashSet<>();

		Assert.assertNotEquals(0, hits.getLength());

		Document document = hits.doc(0);

		Map<String, Field> fields = document.getFields();

		Assert.assertFalse(fields.isEmpty());

		fields.forEach(
			(k, v) -> {
				if (!k.contains(".")) {
					fieldNames.add(k);
				}
			});

		return fieldNames;
	}

	private void _assertGroupByTermsSortsCountDescKeyDesc(
			boolean countDesc, boolean keyDesc, List<String> orderedResults)
		throws Exception {

		_indexTermsSortsDuplicates();

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						Sort[] sorts = new Sort[2];

						sorts[0] = new Sort("_count", countDesc);
						sorts[1] = new Sort("_key", keyDesc);

						GroupByRequest groupByRequest =
							groupByRequestFactory.getGroupByRequest(
								GROUP_FIELD);

						groupByRequest.setTermsSorts(sorts);

						searchRequestBuilder.groupByRequests(groupByRequest);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> assertGroupsOrdered(
						orderedResults, hits.getGroupedHits(),
						indexingTestHelper));

				indexingTestHelper.verifyResponse(
					searchResponse -> {
						List<GroupByResponse> groupByResponses =
							searchResponse.getGroupByResponses();

						Assert.assertEquals(
							groupByResponses.toString(), 1,
							groupByResponses.size());

						GroupByResponse groupByResponse = groupByResponses.get(
							0);

						assertGroupsOrdered(
							orderedResults, groupByResponse.getHitsMap(),
							indexingTestHelper);
					});
			});
	}

	private void _indexTermsSortsDuplicates() {
		indexDuplicates("one", 2);
		indexDuplicates("two", 2);
		indexDuplicates("three", 3);
	}

}