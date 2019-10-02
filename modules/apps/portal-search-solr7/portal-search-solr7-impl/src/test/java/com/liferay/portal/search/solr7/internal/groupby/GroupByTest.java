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

package com.liferay.portal.search.solr7.internal.groupby;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.search.groupby.GroupByRequest;
import com.liferay.portal.search.groupby.GroupByResponse;
import com.liferay.portal.search.solr7.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.groupby.BaseGroupByTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author Tibor Lipusz
 * @author André de Oliveira
 */
public class GroupByTest extends BaseGroupByTestCase {

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
						toMap("five", "5|1"), hits, indexingTestHelper));
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
						toMap("five", "5|1"), hits, indexingTestHelper));
			});
	}

	@Test
	public void testGroupByTermsSortsScoreFieldAsc() throws Exception {
		assertGroupByTermsSortsScoreField(false);
	}

	@Test
	public void testGroupByTermsSortsScoreFieldDesc() throws Exception {
		assertGroupByTermsSortsScoreField(true);
	}

	@Test
	public void testGroupByTermsSortsSortFieldAsc() throws Exception {
		List<String> orderedResults = new ArrayList<>();

		orderedResults.add("one|1|1");
		orderedResults.add("two|2|1");
		orderedResults.add("three|3|1");

		assertGroupByTermsSortsSortField(orderedResults, false);
	}

	@Test
	public void testGroupByTermsSortsSortFieldDesc() throws Exception {
		List<String> orderedResults = new ArrayList<>();

		orderedResults.add("three|3|1");
		orderedResults.add("two|2|1");
		orderedResults.add("one|1|1");

		assertGroupByTermsSortsSortField(orderedResults, true);
	}

	protected void assertGroupByTermsSortsScoreField(boolean desc)
		throws Exception {

		indexTermsSortsDuplicates();

		List<String> orderedResults = new ArrayList<>();

		orderedResults.add("three|3|1");
		orderedResults.add("two|2|1");
		orderedResults.add("one|1|1");

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						GroupByRequest groupByRequest =
							groupByRequestFactory.getGroupByRequest(
								GROUP_FIELD);

						groupByRequest.setTermsSorts(
							new Sort("scoreField", Sort.SCORE_TYPE, desc));

						searchRequestBuilder.groupByRequests(groupByRequest);
					});

				BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

				booleanQueryImpl.addExactTerm(SORT_FIELD, "3");
				booleanQueryImpl.addExactTerm(SORT_FIELD, "2");

				booleanQueryImpl.add(
					getDefaultQuery(), BooleanClauseOccur.MUST);

				indexingTestHelper.setQuery(booleanQueryImpl);

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

	protected void assertGroupByTermsSortsSortField(
			List<String> orderedResults, boolean desc)
		throws Exception {

		indexTermsSortsDuplicates();

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						GroupByRequest groupByRequest =
							groupByRequestFactory.getGroupByRequest(
								GROUP_FIELD);

						groupByRequest.setTermsSorts(
							new Sort(SORT_FIELD, Sort.STRING_TYPE, desc));

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

	@Override
	protected IndexingFixture createIndexingFixture() {
		return new SolrIndexingFixture();
	}

	protected void indexTermsSortsDuplicates() {
		indexDuplicates("one", 1);
		indexDuplicates("two", 2);
		indexDuplicates("three", 3);
	}

}