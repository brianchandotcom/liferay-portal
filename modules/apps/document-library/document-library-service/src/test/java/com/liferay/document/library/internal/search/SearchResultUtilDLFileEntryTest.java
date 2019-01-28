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

package com.liferay.document.library.internal.search;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.RelatedSearchResult;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultManager;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.SummaryFactory;
import com.liferay.portal.kernel.search.result.SearchResultContributor;
import com.liferay.portal.kernel.search.result.SearchResultTranslator;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.internal.result.SearchResultManagerImpl;
import com.liferay.portal.search.internal.result.SearchResultTranslatorImpl;
import com.liferay.portal.search.internal.result.SummaryFactoryImpl;
import com.liferay.portal.search.test.util.BaseSearchResultUtilTestCase;
import com.liferay.portal.search.test.util.SearchTestUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.LogRecord;

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
public class SearchResultUtilDLFileEntryTest
	extends BaseSearchResultUtilTestCase {

	@Test
	public void testDLFileEntry() throws Exception {
		SearchResult searchResult = assertOneSearchResult(
			SearchTestUtil.createDocument(_DL_FILE_ENTRY_CLASS_NAME));

		Assert.assertEquals(
			_DL_FILE_ENTRY_CLASS_NAME, searchResult.getClassName());
		Assert.assertEquals(
			SearchTestUtil.ENTRY_CLASS_PK, searchResult.getClassPK());

		assertEmptyFileEntryRelatedSearchResults(searchResult);

		Assert.assertNull(searchResult.getSummary());

		PowerMockito.verifyZeroInteractions(_dlAppLocalService);

		assertEmptyCommentRelatedSearchResults(searchResult);
		assertEmptyVersions(searchResult);
	}

	@Test
	public void testDLFileEntryAttachment() throws Exception {
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

		PowerMockito.replace(
			PowerMockito.method(
				AssetRendererFactoryRegistryUtil.class,
				"getAssetRendererFactoryByClassName", String.class)
		).with(
			new InvocationHandler() {

				@Override
				public AssetRendererFactory<?> invoke(
						Object proxy, Method method, Object[] args)
					throws Throwable {

					String className = (String)args[0];

					if (_DL_FILE_ENTRY_CLASS_NAME.equals(className)) {
						return null;
					}

					if (SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME.equals(
							className)) {

						return _assetRendererFactory;
					}

					throw new IllegalArgumentException();
				}

			}
		);

		Mockito.doReturn(
			_assetRenderer
		).when(
			_assetRendererFactory
		).getAssetRenderer(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_PK
		);

		Mockito.when(
			_dlAppLocalService.getFileEntry(SearchTestUtil.ENTRY_CLASS_PK)
		).thenReturn(
			_fileEntry
		);

		Mockito.doThrow(
			new IllegalArgumentException()
		).when(
			_indexerRegistry
		).getIndexer(
			Mockito.anyString()
		);

		Mockito.doReturn(
			_indexer
		).when(
			_indexerRegistry
		).getIndexer(
			_DL_FILE_ENTRY_CLASS_NAME
		);

		Mockito.doReturn(
			null
		).when(
			_indexerRegistry
		).getIndexer(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME
		);

		String title = RandomTestUtil.randomString();
		String content = RandomTestUtil.randomString();

		Summary summary = new Summary(null, title, content);

		Mockito.doReturn(
			summary
		).when(
			_indexer
		).getSummary(
			(Document)Matchers.any(), Matchers.anyString(),
			(PortletRequest)Matchers.isNull(),
			(PortletResponse)Matchers.isNull()
		);

		SearchResult searchResult = assertOneSearchResult(
			SearchTestUtil.createAttachmentDocument(_DL_FILE_ENTRY_CLASS_NAME));

		Assert.assertEquals(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME,
			searchResult.getClassName());
		Assert.assertEquals(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_PK,
			searchResult.getClassPK());

		Summary searchResultSummary = searchResult.getSummary();

		Assert.assertNotSame(summary, searchResultSummary);
		Assert.assertEquals(
			SearchTestUtil.SUMMARY_CONTENT, searchResultSummary.getContent());
		Assert.assertEquals(
			SearchTestUtil.SUMMARY_TITLE, searchResultSummary.getTitle());

		List<RelatedSearchResult<FileEntry>> relatedSearchResults =
			searchResult.getFileEntryRelatedSearchResults();

		Assert.assertEquals(
			relatedSearchResults.toString(), 1, relatedSearchResults.size());

		RelatedSearchResult<FileEntry> relatedSearchResult =
			relatedSearchResults.get(0);

		FileEntry relatedSearchResultFileEntry = relatedSearchResult.getModel();

		Assert.assertSame(_fileEntry, relatedSearchResultFileEntry);

		Summary relatedSearchResultSummary = relatedSearchResult.getSummary();

		Assert.assertSame(summary, relatedSearchResultSummary);
		Assert.assertEquals(content, relatedSearchResultSummary.getContent());
		Assert.assertEquals(title, relatedSearchResultSummary.getTitle());

		assertEmptyCommentRelatedSearchResults(searchResult);
		assertEmptyVersions(searchResult);
	}

	@Test
	public void testDLFileEntryMissing() throws Exception {
		Mockito.when(
			_dlAppLocalService.getFileEntry(SearchTestUtil.ENTRY_CLASS_PK)
		).thenReturn(
			null
		);

		SearchResult searchResult = assertOneSearchResult(
			SearchTestUtil.createAttachmentDocument(_DL_FILE_ENTRY_CLASS_NAME));

		Assert.assertEquals(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME,
			searchResult.getClassName());
		Assert.assertEquals(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_PK,
			searchResult.getClassPK());

		assertEmptyFileEntryRelatedSearchResults(searchResult);

		Mockito.verify(
			_dlAppLocalService
		).getFileEntry(
			SearchTestUtil.ENTRY_CLASS_PK
		);

		Assert.assertNull(searchResult.getSummary());

		Mockito.verify(
			_indexerRegistry
		).getIndexer(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME
		);

		PowerMockito.verifyStatic(Mockito.atLeastOnce());

		AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME);

		assertEmptyCommentRelatedSearchResults(searchResult);
		assertEmptyVersions(searchResult);
	}

	@Test
	public void testDLFileEntryWithBrokenIndexer() throws Exception {
		Mockito.when(
			_dlAppLocalService.getFileEntry(SearchTestUtil.ENTRY_CLASS_PK)
		).thenReturn(
			_fileEntry
		);

		Mockito.doThrow(
			new IllegalArgumentException()
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

		Document document = SearchTestUtil.createAttachmentDocument(
			_DL_FILE_ENTRY_CLASS_NAME);

		String snippet = RandomTestUtil.randomString();

		document.add(new Field(Field.SNIPPET, snippet));

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					SearchResultTranslatorImpl.class.getName(),
					Level.WARNING)) {

			SearchResult searchResult = assertOneSearchResult(document);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			Assert.assertEquals(
				"Search index is stale and contains entry {" + entryClassPK +
					"}",
				logRecord.getMessage());

			Assert.assertEquals(
				SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME,
				searchResult.getClassName());
			Assert.assertEquals(
				SearchTestUtil.ATTACHMENT_OWNER_CLASS_PK,
				searchResult.getClassPK());
			Assert.assertNull(searchResult.getSummary());

			Mockito.verify(
				_indexerRegistry
			).getIndexer(
				_DL_FILE_ENTRY_CLASS_NAME
			);

			Mockito.verify(
				_indexer
			).getSummary(
				document, snippet, null, null
			);

			assertEmptyFileEntryRelatedSearchResults(searchResult);

			Mockito.verify(
				_dlAppLocalService
			).getFileEntry(
				SearchTestUtil.ENTRY_CLASS_PK
			);

			assertEmptyCommentRelatedSearchResults(searchResult);
			assertEmptyVersions(searchResult);
		}
	}

	protected SearchResultContributor createSearchResultContributor() {
		DLFileEntrySearchResultContributor dlFileEntrySearchResultContributor =
			new DLFileEntrySearchResultContributor();

		dlFileEntrySearchResultContributor.setClassNameLocalService(
			classNameLocalService);
		dlFileEntrySearchResultContributor.setDLAppLocalService(
			_dlAppLocalService);
		dlFileEntrySearchResultContributor.setSummaryFactory(
			createSummaryFactory());

		return dlFileEntrySearchResultContributor;
	}

	protected SearchResultManager createSearchResultManager() {
		SearchResultManagerImpl searchResultManagerImpl =
			new SearchResultManagerImpl();

		searchResultManagerImpl.addSearchResultContributor(
			createSearchResultContributor());
		searchResultManagerImpl.setClassNameLocalService(classNameLocalService);
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

	private static final String _DL_FILE_ENTRY_CLASS_NAME =
		DLFileEntry.class.getName();

	@Mock
	@SuppressWarnings("rawtypes")
	private AssetRenderer _assetRenderer;

	@Mock
	private AssetRendererFactory<?> _assetRendererFactory;

	@Mock
	private DLAppLocalService _dlAppLocalService;

	@Mock
	private FileEntry _fileEntry;

	@Mock
	private Indexer<Object> _indexer;

	@Mock
	private IndexerRegistry _indexerRegistry;

}