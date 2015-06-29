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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.test.BaseSearchResultUtilTestCase;
import com.liferay.portal.search.test.SearchTestUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest( {
	AssetRendererFactoryRegistryUtil.class, IndexerRegistryUtil.class,
	ServiceTrackerCollections.class
})
@RunWith(PowerMockRunner.class)
public class SearchResultUtilTest extends BaseSearchResultUtilTestCase {

	@Test
	public void testBlankDocument() {
		SearchResult searchResult = assertOneSearchResult(new DocumentImpl());

		Assert.assertNull(searchResult.getSummary());

		assertSearchResult(searchResult);
	}

	@Test
	public void testNoDocuments() {
		List<SearchResult> searchResults = SearchTestUtil.getSearchResults();

		Assert.assertEquals(0, searchResults.size());
	}

	@Test
	public void testSummaryFromAssetRenderer() throws Exception {
		when(
			assetRenderer.getSearchSummary((Locale)Matchers.any())
		).thenReturn(
			SearchTestUtil.SUMMARY_CONTENT
		);

		when(
			assetRenderer.getTitle((Locale)Matchers.any())
		).thenReturn(
			SearchTestUtil.SUMMARY_TITLE
		);

		when(
			assetRendererFactory.getAssetRenderer(Matchers.anyLong())
		).thenReturn(
			assetRenderer
		);

		stub(
			method(
				AssetRendererFactoryRegistryUtil.class,
				"getAssetRendererFactoryByClassName", String.class)
		).toReturn(
			assetRendererFactory
		);

		SearchResult searchResult = assertOneSearchResult(new DocumentImpl());

		Summary summary = searchResult.getSummary();

		Assert.assertEquals(
			SearchTestUtil.SUMMARY_CONTENT, summary.getContent());
		Assert.assertEquals(
			BaseSearchResultManager.SUMMARY_MAX_CONTENT_LENGTH,
			summary.getMaxContentLength());
		Assert.assertEquals(SearchTestUtil.SUMMARY_TITLE, summary.getTitle());

		assertSearchResult(searchResult);
	}

	@Test
	public void testSummaryFromIndexer() throws Exception {
		Indexer<?> indexer = Mockito.mock(Indexer.class);

		Summary summary = new Summary(
			null, SearchTestUtil.SUMMARY_TITLE, SearchTestUtil.SUMMARY_CONTENT);

		when(
			indexer.getSummary(
				(Document)Matchers.any(), Matchers.anyString(),
				(PortletRequest)Matchers.isNull(),
				(PortletResponse)Matchers.isNull())
		).thenReturn(
			summary
		);

		stub(
			method(IndexerRegistryUtil.class, "getIndexer", String.class)
		).toReturn(
			indexer
		);

		SearchResult searchResult = assertOneSearchResult(new DocumentImpl());

		Assert.assertSame(summary, searchResult.getSummary());

		assertSearchResult(searchResult);
	}

	@Test
	public void testTwoDocumentsWithSameEntryKey() {
		String className = RandomTestUtil.randomString();

		Document document1 = SearchTestUtil.createDocument(className);
		Document document2 = SearchTestUtil.createDocument(className);

		List<SearchResult> searchResults = SearchTestUtil.getSearchResults(
			document1, document2);

		Assert.assertEquals(1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(searchResult.getClassName(), className);
		Assert.assertEquals(
			searchResult.getClassPK(), SearchTestUtil.ENTRY_CLASS_PK);
	}

	protected void assertSearchResult(SearchResult searchResult) {
		Assert.assertEquals(StringPool.BLANK, searchResult.getClassName());
		Assert.assertEquals(0L, searchResult.getClassPK());

		assertEmptyCommentRelatedSearchResults(searchResult);
		assertEmptyFileEntryRelatedSearchResults(searchResult);
		assertEmptyVersions(searchResult);
	}

}