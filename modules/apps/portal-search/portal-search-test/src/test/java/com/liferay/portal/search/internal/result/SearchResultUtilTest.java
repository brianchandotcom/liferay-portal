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

package com.liferay.portal.search.internal.result;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.SummaryFactory;
import com.liferay.portal.kernel.search.result.SearchResultTranslator;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.BaseSearchResultUtilTestCase;
import com.liferay.portal.search.test.util.SearchTestUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest(AssetRendererFactoryRegistryUtil.class)
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
		List<SearchResult> searchResults = SearchTestUtil.getSearchResults(
			searchResultTranslator);

		Assert.assertEquals(searchResults.toString(), 0, searchResults.size());
	}

	@Test
	public void testSummaryFromAssetRenderer() throws Exception {
		Mockito.when(
			_assetRenderer.getSearchSummary((Locale)Matchers.any())
		).thenReturn(
			SearchTestUtil.SUMMARY_CONTENT
		);

		Mockito.when(
			_assetRenderer.getTitle((Locale)Matchers.any())
		).thenReturn(
			SearchTestUtil.SUMMARY_TITLE
		);

		Mockito.when(
			_assetRendererFactory.getAssetRenderer(Matchers.anyLong())
		).thenReturn(
			_assetRenderer
		);

		PowerMockito.stub(
			PowerMockito.method(
				AssetRendererFactoryRegistryUtil.class,
				"getAssetRendererFactoryByClassName", String.class)
		).toReturn(
			_assetRendererFactory
		);

		SearchResult searchResult = assertOneSearchResult(new DocumentImpl());

		Summary summary = searchResult.getSummary();

		Assert.assertEquals(
			SearchTestUtil.SUMMARY_CONTENT, summary.getContent());
		Assert.assertEquals(
			SummaryFactoryImpl.SUMMARY_MAX_CONTENT_LENGTH,
			summary.getMaxContentLength());
		Assert.assertEquals(SearchTestUtil.SUMMARY_TITLE, summary.getTitle());

		assertSearchResult(searchResult);
	}

	@Test
	public void testSummaryFromIndexer() throws Exception {
		Summary summary = new Summary(
			null, SearchTestUtil.SUMMARY_TITLE, SearchTestUtil.SUMMARY_CONTENT);

		Mockito.when(
			_indexer.getSummary(
				(Document)Matchers.any(), Matchers.anyString(),
				(PortletRequest)Matchers.isNull(),
				(PortletResponse)Matchers.isNull())
		).thenReturn(
			summary
		);

		Mockito.when(
			_indexerRegistry.getIndexer(Mockito.anyString())
		).thenReturn(
			_indexer
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
			searchResultTranslator, document1, document2);

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

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

	protected SearchResultManagerImpl createSearchResultManagerImpl() {
		SearchResultManagerImpl searchResultManagerImpl =
			new SearchResultManagerImpl();

		searchResultManagerImpl.setClassNameLocalService(classNameLocalService);
		searchResultManagerImpl.setSummaryFactory(createSummaryFactory());

		return searchResultManagerImpl;
	}

	@Override
	protected SearchResultTranslator createSearchResultTranslator() {
		SearchResultTranslatorImpl searchResultTranslatorImpl =
			new SearchResultTranslatorImpl();

		searchResultTranslatorImpl.setSearchResultManager(
			createSearchResultManagerImpl());

		return searchResultTranslatorImpl;
	}

	protected SummaryFactory createSummaryFactory() {
		SummaryFactoryImpl summaryFactoryImpl = new SummaryFactoryImpl();

		summaryFactoryImpl.setIndexerRegistry(_indexerRegistry);

		return summaryFactoryImpl;
	}

	@Mock
	@SuppressWarnings("rawtypes")
	private AssetRenderer _assetRenderer;

	@Mock
	private AssetRendererFactory<?> _assetRendererFactory;

	@Mock
	private Indexer<Object> _indexer;

	@Mock
	private IndexerRegistry _indexerRegistry;

}