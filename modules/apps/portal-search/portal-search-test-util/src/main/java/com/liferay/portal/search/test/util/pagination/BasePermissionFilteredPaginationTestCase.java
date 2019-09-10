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

package com.liferay.portal.search.test.util.pagination;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchResultPermissionFilter;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.configuration.DefaultSearchResultPermissionFilterConfiguration;
import com.liferay.portal.search.internal.facet.FacetPostProcessorImpl;
import com.liferay.portal.search.internal.permission.DefaultSearchResultPermissionFilter;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.mockito.AdditionalMatchers;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Eric Yan
 */
public abstract class BasePermissionFilteredPaginationTestCase
	extends BaseIndexingTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		permissionFilteredSearchResultAccurateCountThreshold = 9;
		searchQueryResultWindowLimit = 9;
	}

	@Test
	public void testAccurateCountThreshold() throws Exception {
		index(9, filtering(4));

		permissionFilteredSearchResultAccurateCountThreshold = 4;

		assertPaginationCounts(1, 9, 3, "[8, 8, 8]");
	}

	@Test
	public void testAccurateCountThresholdLessThanExpectedFiltered()
		throws Exception {

		index(9, filtering(4));

		permissionFilteredSearchResultAccurateCountThreshold = 3;

		assertPaginationCounts(1, 9, 3, "[9, 8, 8]");
	}

	@Test
	public void testAccurateCountThresholdMoreThanExpectedFiltered()
		throws Exception {

		index(9, filtering(4));

		permissionFilteredSearchResultAccurateCountThreshold = 5;

		assertPaginationCounts(1, 9, 3, "[8, 8, 8]");
	}

	@Test
	public void testAccurateCountThresholdNegativeOne() throws Exception {
		index(9, filtering(4));

		permissionFilteredSearchResultAccurateCountThreshold = -1;

		assertPaginationCounts(1, 9, 3, "[9, 8, 8]");
	}

	@Test
	public void testAccurateCountThresholdZero() throws Exception {
		index(9, filtering(4));

		permissionFilteredSearchResultAccurateCountThreshold = 0;

		assertPaginationCounts(1, 9, 3, "[9, 8, 8]");
	}

	@Test
	public void testExcludeEveryThird() throws Exception {
		index(9, filtering(3, 6, 9));

		assertPagination(1, 9, 3, "[[1, 2, 4], [5, 7, 8]]");
	}

	@Test
	public void testExcludeFirst() throws Exception {
		index(9, filtering(1));

		assertPagination(1, 9, 3, "[[2, 3, 4], [5, 6, 7], [8, 9]]");
	}

	@Test
	public void testExcludeLast() throws Exception {
		index(9, filtering(9));

		assertPagination(1, 9, 3, "[[1, 2, 3], [4, 5, 6], [7, 8]]");
	}

	@Test
	public void testExcludeMiddle() throws Exception {
		index(9, filtering(2, 3, 4, 5, 6, 7, 8));

		assertPagination(1, 9, 3, "[[1, 9]]");
	}

	@Test
	public void testMinimal() throws Exception {
		index(3, filtering());

		assertPagination(1, 3, 1, "[[1], [2], [3]]");
	}

	@Test
	public void testMinimalEndFirstIndex() throws Exception {
		index(3, filtering());

		assertPagination(1, 1, 1, "[[1]]");
	}

	@Test
	public void testMinimalEndMiddleIndex() throws Exception {
		index(3, filtering());

		assertPagination(1, 2, 1, "[[1], [2]]");
	}

	@Test
	public void testMinimalStartLastIndex() throws Exception {
		index(3, filtering());

		assertPagination(3, 3, 1, "[[3]]");
	}

	@Test
	public void testMinimalStartMiddleIndex() throws Exception {
		index(3, filtering());

		assertPagination(2, 3, 1, "[[2], [3]]");
	}

	@Test
	public void testPastLast() throws Exception {
		index(9, filtering());

		assertPagination(10, 12, 3, "[[7, 8, 9]]");
	}

	@Test
	public void testPastLastSecondIndex() throws Exception {
		index(9, filtering());

		assertPagination(11, 12, 3, "[[9]]");
	}

	@Test
	public void testPastLastThirdIndex() throws Exception {
		index(9, filtering());

		assertPagination(12, 12, 3, "[[9]]");
	}

	@Test
	public void testSearchQueryResultWindowLimitNegativeOne() throws Exception {
		index(9, filtering());

		searchQueryResultWindowLimit = -1;

		expectedException.expect(SystemException.class);
		expectedException.expectMessage(
			"Search result window size of 9 exceeds the configured limit of " +
				"-1");

		assertPagination(1, 1, 1, "[[1]]");
	}

	@Test
	public void testSearchQueryResultWindowLimitZero() throws Exception {
		index(9, filtering());

		searchQueryResultWindowLimit = 0;

		expectedException.expect(SystemException.class);
		expectedException.expectMessage(
			"Search result window size of 9 exceeds the configured limit of 0");

		assertPagination(1, 1, 1, "[[1]]");
	}

	@Test
	public void testSlidingWindow() throws Exception {
		index(9, filtering(1, 2, 3, 4, 5, 6, 7, 8));

		searchQueryResultWindowLimit = 3;

		assertPagination(1, 9, 9, "[[9]]");
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	protected void assertPagination(
			int from, int to, int pageSize, String expectedPaginationResult)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			() -> {
				doAssertPagination(
					from, to, pageSize, expectedPaginationResult);

				return null;
			});
	}

	protected void assertPaginationCounts(
			int from, int to, int pageSize,
			String expectedPaginationCountsResult)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			() -> {
				doAssertPaginationCounts(
					from, to, pageSize, expectedPaginationCountsResult);

				return null;
			});
	}

	protected List<Integer> createEntries(int totalEntries) {
		List<Integer> entries = new ArrayList<>(totalEntries);

		for (int i = 1; i <= totalEntries; i++) {
			entries.add(i);
		}

		return entries;
	}

	protected SearchContext createSearchContext(int start, int end) {
		SearchContext searchContext = createSearchContext();

		searchContext.setEnd(end);
		searchContext.setStart(start);

		searchContext.setSorts(
			new Sort(Field.PRIORITY, Sort.DOUBLE_TYPE, false));

		return searchContext;
	}

	protected SearchResultPermissionFilter createSearchResultPermissionFilter()
		throws Exception {

		IndexerRegistry indexerRegistry = Mockito.mock(IndexerRegistry.class);
		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);
		Props props = Mockito.mock(Props.class);
		RelatedEntryIndexerRegistry relatedEntryIndexerRegistry = Mockito.mock(
			RelatedEntryIndexerRegistry.class);

		DefaultSearchResultPermissionFilterConfiguration
			defaultSearchResultPermissionFilterConfiguration = Mockito.mock(
				DefaultSearchResultPermissionFilterConfiguration.class);

		setUpSearchResultPermissionFilterMocks(
			indexerRegistry, permissionChecker, props,
			defaultSearchResultPermissionFilterConfiguration);

		return new DefaultSearchResultPermissionFilter(
			new FacetPostProcessorImpl(), indexerRegistry, permissionChecker,
			props, relatedEntryIndexerRegistry, this::doSearch,
			defaultSearchResultPermissionFilterConfiguration);
	}

	protected void doAssertPagination(
			int from, int to, int pageSize, String expected)
		throws Exception {

		int pageCount = StringUtil.count(expected, CharPool.OPEN_BRACKET) - 1;

		Assert.assertTrue(pageCount > 0);

		List<List<Integer>> paginatedEntries = new ArrayList<>();

		for (int page = 1; page <= pageCount; page++) {
			int start = (from - 1) + ((page - 1) * pageSize);

			int end = start + pageSize;

			if (end > to) {
				end = to;
			}

			Hits hits = searchFilteredPagination(start, end);

			paginatedEntries.add(getEntries(hits));
		}

		String actual = paginatedEntries.toString();

		Assert.assertEquals(actual, expected, actual);
	}

	protected void doAssertPaginationCounts(
			int from, int to, int pageSize, String expected)
		throws Exception {

		int pageCount = StringUtil.count(expected, CharPool.COMMA) + 1;

		Assert.assertTrue(pageCount > 0);

		List<Integer> paginatedCounts = new ArrayList<>();

		for (int page = 1; page <= pageCount; page++) {
			int start = (from - 1) + ((page - 1) * pageSize);

			int end = start + pageSize;

			if (end > to) {
				end = to;
			}

			Hits hits = searchFilteredPagination(start, end);

			paginatedCounts.add(hits.getLength());
		}

		String actual = paginatedCounts.toString();

		Assert.assertEquals(actual, expected, actual);
	}

	protected Hits doSearch(SearchContext searchContext) {
		try {
			return search(searchContext);
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected List<Integer> filtering(Integer... entries) {
		return Arrays.asList(entries);
	}

	protected long getDocumentEntryClassPK(Document document) {
		return GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));
	}

	protected List<Integer> getEntries(Hits hits) {
		List<Integer> entries = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			long entry = getDocumentEntryClassPK(document);

			if (entry >= _FILTERED_ENTRY_IDENTIFIER) {
				entry = entry - _FILTERED_ENTRY_IDENTIFIER;
			}

			entries.add((int)entry);
		}

		return entries;
	}

	protected void index(int totalEntries, List<Integer> filteredEntries)
		throws Exception {

		List<Integer> entries = createEntries(totalEntries);

		indexEntries(entries, filteredEntries);
	}

	protected void indexEntries(
			List<Integer> entries, List<Integer> filteredEntries)
		throws Exception {

		for (Integer entry : entries) {
			long entryClassPK = entry;

			if (filteredEntries.contains(entry)) {
				entryClassPK += _FILTERED_ENTRY_IDENTIFIER;
			}

			addDocument(
				DocumentCreationHelpers.twoKeywords(
					Field.ENTRY_CLASS_PK, String.valueOf(entryClassPK),
					Field.PRIORITY, String.valueOf(entry)));
		}
	}

	protected Hits searchFilteredPagination(int start, int end)
		throws Exception {

		SearchResultPermissionFilter searchResultPermissionFilter =
			createSearchResultPermissionFilter();

		return searchResultPermissionFilter.search(
			createSearchContext(start, end));
	}

	protected void setUpSearchResultPermissionFilterMocks(
			IndexerRegistry indexerRegistry,
			PermissionChecker permissionChecker, Props props,
			DefaultSearchResultPermissionFilterConfiguration
				defaultSearchResultPermissionFilterConfiguration)
		throws Exception {

		Indexer indexer = Mockito.mock(Indexer.class);

		Mockito.when(
			indexer.hasPermission(
				Matchers.any(PermissionChecker.class), Matchers.anyString(),
				Matchers.anyLong(), Matchers.anyString())
		).thenReturn(
			true
		);

		Mockito.when(
			indexer.hasPermission(
				Matchers.any(PermissionChecker.class), Matchers.anyString(),
				AdditionalMatchers.geq(_FILTERED_ENTRY_IDENTIFIER),
				Matchers.anyString())
		).thenReturn(
			false
		);

		Mockito.when(
			indexer.isFilterSearch()
		).thenReturn(
			true
		);

		Mockito.when(
			indexerRegistry.getIndexer(Matchers.anyString())
		).thenReturn(
			indexer
		);

		Mockito.when(
			permissionChecker.getCompanyId()
		).thenReturn(
			getCompanyId()
		);

		Mockito.when(
			props.get(
				PropsKeys.INDEX_PERMISSION_FILTER_SEARCH_AMPLIFICATION_FACTOR)
		).thenReturn(
			"1.5"
		);

		Mockito.when(
			defaultSearchResultPermissionFilterConfiguration.
				permissionFilteredSearchResultAccurateCountThreshold()
		).thenReturn(
			permissionFilteredSearchResultAccurateCountThreshold
		);

		Mockito.when(
			defaultSearchResultPermissionFilterConfiguration.
				searchQueryResultWindowLimit()
		).thenReturn(
			searchQueryResultWindowLimit
		);
	}

	protected int permissionFilteredSearchResultAccurateCountThreshold;
	protected int searchQueryResultWindowLimit;

	private static final long _FILTERED_ENTRY_IDENTIFIER = 1000000;

}