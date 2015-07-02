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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.test.BaseSearchResultUtilTestCase;
import com.liferay.portal.search.test.SearchTestUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.registry.collections.ServiceTrackerCollections;

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

		verifyZeroInteractions(dlAppLocalService);

		assertEmptyCommentRelatedSearchResults(searchResult);
		assertEmptyVersions(searchResult);
	}

	@Test
	public void testDLFileEntryAttachment() throws Exception {
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

		replace(
			method(
				AssetRendererFactoryRegistryUtil.class,
				"getAssetRendererFactoryByClassName", String.class)
		).with(
			new InvocationHandler() {

				@Override
				public AssetRendererFactory invoke(
						Object proxy, Method method, Object[] args)
					throws Throwable {

					String className = (String)args[0];

					if (_DL_FILE_ENTRY_CLASS_NAME.equals(className)) {
						return null;
					}

					if (SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME.equals(
							className)) {

						return assetRendererFactory;
					}

					throw new IllegalArgumentException();
				}

			}
		);

		when(
			assetRendererFactory.getAssetRenderer(
				SearchTestUtil.ATTACHMENT_OWNER_CLASS_PK)
		).thenReturn(
			assetRenderer
		);

		when(
			dlAppLocalService.getFileEntry(SearchTestUtil.ENTRY_CLASS_PK)
		).thenReturn(
			_fileEntry
		);

		final Indexer<?> indexer = Mockito.mock(Indexer.class);

		replace(
			method(IndexerRegistryUtil.class, "getIndexer", String.class)
		).with(
			new InvocationHandler() {

				@Override
				public Indexer<?> invoke(
						Object proxy, Method method, Object[] args)
					throws Throwable {

					String className = (String)args[0];

					if (_DL_FILE_ENTRY_CLASS_NAME.equals(className)) {
						return indexer;
					}

					if (SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME.equals(
							className)) {

						return null;
					}

					throw new IllegalArgumentException();
				}

			}
		);

		String title = RandomTestUtil.randomString();
		String content = RandomTestUtil.randomString();

		Summary summary = new Summary(null, title, content);

		doReturn(
			summary
		).when(
			indexer
		).getSummary(
			(Document)Matchers.any(), Matchers.anyString(),
			(PortletRequest)Matchers.isNull(),
			(PortletResponse)Matchers.isNull());

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

		Assert.assertEquals(1, relatedSearchResults.size());

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
		when(
			dlAppLocalService.getFileEntry(SearchTestUtil.ENTRY_CLASS_PK)
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
			dlAppLocalService
		).getFileEntry(
			SearchTestUtil.ENTRY_CLASS_PK
		);

		Assert.assertNull(searchResult.getSummary());

		verifyStatic(Mockito.atLeastOnce());

		AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME);

		verifyStatic();

		IndexerRegistryUtil.getIndexer(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME);

		assertEmptyCommentRelatedSearchResults(searchResult);
		assertEmptyVersions(searchResult);
	}

	@Test
	public void testDLFileEntryWithBrokenIndexer() throws Exception {
		when(
			dlAppLocalService.getFileEntry(SearchTestUtil.ENTRY_CLASS_PK)
		).thenReturn(
			_fileEntry
		);

		Indexer<?> indexer = Mockito.mock(Indexer.class);

		Mockito.doThrow(
			IllegalArgumentException.class
		).when(
			indexer
		).getSummary(
			(Document)Matchers.any(), Matchers.anyString(),
			(PortletRequest)Matchers.any(), (PortletResponse)Matchers.any());

		stub(
			method(IndexerRegistryUtil.class, "getIndexer", String.class)
		).toReturn(
			indexer
		);

		Document document = SearchTestUtil.createAttachmentDocument(
			_DL_FILE_ENTRY_CLASS_NAME);

		String snippet = RandomTestUtil.randomString();

		document.add(new Field(Field.SNIPPET, snippet));

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					SearchResultUtil.class.getName(), Level.WARNING)) {

			SearchResult searchResult = assertOneSearchResult(document);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			Assert.assertEquals(
				"Search index is stale and contains entry {" + entryClassPK +
					"}", logRecord.getMessage());

			Assert.assertEquals(
				SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME,
				searchResult.getClassName());
			Assert.assertEquals(
				SearchTestUtil.ATTACHMENT_OWNER_CLASS_PK,
				searchResult.getClassPK());
			Assert.assertNull(searchResult.getSummary());

			verifyStatic();

			IndexerRegistryUtil.getIndexer(_DL_FILE_ENTRY_CLASS_NAME);

			Mockito.verify(
				indexer
			).getSummary(
				document, snippet, null, null
			);

			assertEmptyFileEntryRelatedSearchResults(searchResult);

			Mockito.verify(
				dlAppLocalService
			).getFileEntry(
				SearchTestUtil.ENTRY_CLASS_PK
			);

			assertEmptyCommentRelatedSearchResults(searchResult);
			assertEmptyVersions(searchResult);
		}
	}

	private static final String _DL_FILE_ENTRY_CLASS_NAME =
		DLFileEntry.class.getName();

	@Mock
	private FileEntry _fileEntry;

}