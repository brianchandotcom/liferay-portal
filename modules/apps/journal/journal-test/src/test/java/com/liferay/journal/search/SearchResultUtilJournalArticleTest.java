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

package com.liferay.journal.search;

import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultManager;
import com.liferay.portal.kernel.search.SummaryFactory;
import com.liferay.portal.kernel.search.result.SearchResultTranslator;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.internal.result.SearchResultManagerImpl;
import com.liferay.portal.search.internal.result.SearchResultTranslatorImpl;
import com.liferay.portal.search.internal.result.SummaryFactoryImpl;
import com.liferay.portal.search.test.util.BaseSearchResultUtilTestCase;
import com.liferay.portal.search.test.util.SearchTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.logging.Level;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class SearchResultUtilJournalArticleTest
	extends BaseSearchResultUtilTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		super.setUp();
	}

	@Test
	public void testJournalArticle() {
		SearchResult searchResult = assertOneSearchResult(createDocument());

		assertSearchResult(searchResult);

		Assert.assertNull(searchResult.getSummary());
	}

	@Test
	public void testJournalArticleWithDefectiveIndexer() throws Exception {
		Mockito.doThrow(
			IllegalArgumentException.class
		).when(
			_indexer
		).getSummary(
			(Document)Matchers.any(), Matchers.anyString(),
			(PortletRequest)Matchers.any(), (PortletResponse)Matchers.any()
		);

		Mockito.when(
			_indexerRegistry.getIndexer(Mockito.anyString())
		).thenReturn(
			_indexer
		);

		Document document = createDocument();

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				SearchResultTranslatorImpl.class.getName(), Level.WARNING)) {

			SearchResult searchResult = assertOneSearchResult(document);

			assertSearchResult(searchResult);

			Assert.assertNull(searchResult.getSummary());

			Mockito.verify(
				_indexer
			).getSummary(
				document, StringPool.BLANK, null, null
			);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Search index is stale and contains entry {" +
					document.get(Field.ENTRY_CLASS_PK) + "}",
				logEntry.getMessage());
		}
	}

	protected void assertSearchResult(SearchResult searchResult) {
		Assert.assertEquals(
			_CLASS_NAME_JOURNAL_ARTICLE, searchResult.getClassName());
		Assert.assertEquals(
			SearchTestUtil.ENTRY_CLASS_PK, searchResult.getClassPK());

		List<String> versions = searchResult.getVersions();

		Assert.assertEquals(_DOCUMENT_VERSION, versions.get(0));
		Assert.assertEquals(versions.toString(), 1, versions.size());

		assertEmptyCommentRelatedSearchResults(searchResult);
		assertEmptyFileEntryRelatedSearchResults(searchResult);
	}

	protected Document createDocument() {
		Document document = SearchTestUtil.createDocument(
			_CLASS_NAME_JOURNAL_ARTICLE);

		document.add(new Field(Field.VERSION, _DOCUMENT_VERSION));

		return document;
	}

	protected SearchResultManager createSearchResultManager() {
		SearchResultManagerImpl searchResultManagerImpl =
			new SearchResultManagerImpl();

		searchResultManagerImpl.setSummaryFactory(createSummaryFactory());

		return searchResultManagerImpl;
	}

	@Override
	protected SearchResultTranslator createSearchResultTranslator() {
		SearchResultTranslatorImpl searchResultTranslatorImpl =
			new SearchResultTranslatorImpl();

		searchResultTranslatorImpl.setSearchResultManager(
			createSearchResultManager());

		return searchResultTranslatorImpl;
	}

	protected SummaryFactory createSummaryFactory() {
		SummaryFactoryImpl summaryFactoryImpl = new SummaryFactoryImpl();

		summaryFactoryImpl.setIndexerRegistry(_indexerRegistry);

		return summaryFactoryImpl;
	}

	private static final String _CLASS_NAME_JOURNAL_ARTICLE =
		JournalArticle.class.getName();

	private static final String _DOCUMENT_VERSION = String.valueOf(
		RandomTestUtil.randomInt());

	@Mock
	private Indexer<Object> _indexer;

	@Mock
	private IndexerRegistry _indexerRegistry;

}