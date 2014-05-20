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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.hamcrest.collection.IsEmptyCollection;

import org.junit.Assert;
import org.junit.Before;
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
	AssetRendererFactoryRegistryUtil.class, IndexerRegistryUtil.class
})
@RunWith(PowerMockRunner.class)
public class SearchResultUtilTest extends BaseSearchResultUtilTestCase {

	@Before
	public void setUp() {
		doSetUp();
	}

	@Test
	public void testBlankDocument() {
		searchBlankDocument();

		Assert.assertNull(
			"Summary should be null with no Indexer or AssetRenderer defined",
			result.getSummary());

		assertThatEverythingUnrelatedIsEmpty();
	}

	@Test
	public void testNoDocuments() {
		List<SearchResult> searchResults = getSearchResults();

		Assert.assertEquals("no hits, no results", 0, searchResults.size());
	}

	@Test
	public void testSummaryFromAssetRenderer() throws Exception {
		when(
			assetRendererFactory.getAssetRenderer(Matchers.anyLong())
		).thenReturn(
			assetRenderer
		);

		when(
			assetRenderer.getSearchSummary((Locale)Matchers.any())
		).thenReturn(
			SUMMARY_CONTENT
		);

		when(
			assetRenderer.getTitle((Locale)Matchers.any())
		).thenReturn(
			SUMMARY_TITLE
		);

		stub(
			method(
				AssetRendererFactoryRegistryUtil.class,
				"getAssetRendererFactoryByClassName", String.class)
		).toReturn(
			assetRendererFactory
		);

		searchBlankDocument();

		Summary summary = result.getSummary();

		Assert.assertEquals(SUMMARY_CONTENT, summary.getContent());
		Assert.assertEquals(200, summary.getMaxContentLength());
		Assert.assertSame(portletURL, summary.getPortletURL());
		Assert.assertEquals(SUMMARY_TITLE, summary.getTitle());

		assertThatEverythingUnrelatedIsEmpty();
	}

	@Test
	public void testSummaryFromIndexer() throws Exception {
		Indexer indexer = Mockito.mock(Indexer.class);

		Summary summary = new Summary(
			null, SUMMARY_TITLE, SUMMARY_CONTENT, null);

		when(
			indexer.getSummary(
				(Document)Matchers.any(), Matchers.anyString(),
				(PortletURL)Matchers.any(), (PortletRequest)Matchers.isNull(),
				(PortletResponse)Matchers.isNull())
		).thenReturn(
			summary
		);

		stub(
			method(IndexerRegistryUtil.class, "getIndexer", String.class)
		).toReturn(
			indexer
		);

		searchBlankDocument();

		Assert.assertSame(summary, result.getSummary());

		assertThatEverythingUnrelatedIsEmpty();
	}

	@Test
	public void testTwoDocumentsWithSameEntryKey() {
		String className = "__className__";

		Document documentA = newDocument(className);
		Document documentB = newDocument(className);

		List<SearchResult> searchResults = getSearchResults(
			documentA, documentB);

		Assert.assertEquals("two hits, one result", 1, searchResults.size());

		result = searchResults.get(0);

		Assert.assertEquals(result.getClassName(), className);
		Assert.assertEquals(result.getClassPK(), ENTRY_CLASS_PK);
	}

	protected void assertThatEverythingUnrelatedIsEmpty() {
		Assert.assertEquals(StringPool.BLANK, result.getClassName());
		Assert.assertEquals(0L, result.getClassPK());

		Assert.assertThat(
			result.getFileEntryTuples(), IsEmptyCollection.empty());
		Assert.assertThat(result.getMBMessages(), IsEmptyCollection.empty());
		Assert.assertThat(result.getVersions(), IsEmptyCollection.empty());
	}

	protected void searchBlankDocument() {
		searchSingleDocument(new DocumentImpl());
	}

}