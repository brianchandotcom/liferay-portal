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
import com.liferay.portal.kernel.search.test.SearchTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.junit.Assert;
import org.junit.Before;
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
	AssetRendererFactoryRegistryUtil.class, DLAppLocalServiceUtil.class,
	IndexerRegistryUtil.class
})
@RunWith(PowerMockRunner.class)
public class SearchResultUtilDLFileEntryTest
	extends BaseSearchResultUtilTestCase {

	@Before
	@Override
	public void setUp() {
		super.setUp();

		setUpDLAppLocalServiceUtil();
	}

	@Test
	public void testDLFileEntry() throws Exception {
		SearchResult searchResult = assertOneSearchResult(
			SearchTestUtil.createDocument(_DL_FILE_ENTRY_CLASS_NAME));

		Assert.assertEquals(
			_DL_FILE_ENTRY_CLASS_NAME, searchResult.getClassName());
		Assert.assertEquals(
			SearchTestUtil.ENTRY_CLASS_PK, searchResult.getClassPK());

		assertEmptyFileEntryTuples(searchResult);

		Assert.assertNull(searchResult.getSummary());

		verifyZeroInteractions(_dlAppLocalService);

		assertEmptyMBMessages(searchResult);
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
			_dlAppLocalService.getFileEntry(SearchTestUtil.ENTRY_CLASS_PK)
		).thenReturn(
			_fileEntry
		);

		final Indexer indexer = Mockito.mock(Indexer.class);

		replace(
			method(IndexerRegistryUtil.class, "getIndexer", String.class)
		).with(
			new InvocationHandler() {

				@Override
				public Indexer invoke(
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

		List<Tuple> fileEntryTuples = searchResult.getFileEntryTuples();

		Assert.assertEquals(1, fileEntryTuples.size());

		Tuple tuple = fileEntryTuples.get(0);

		FileEntry tupleFileEntry = (FileEntry)tuple.getObject(0);

		Assert.assertSame(_fileEntry, tupleFileEntry);

		Summary tupleSummary = (Summary)tuple.getObject(1);

		Assert.assertSame(summary, tupleSummary);
		Assert.assertEquals(content, tupleSummary.getContent());
		Assert.assertEquals(title, tupleSummary.getTitle());

		assertEmptyMBMessages(searchResult);
		assertEmptyVersions(searchResult);
	}

	@Test
	public void testDLFileEntryMissing() throws Exception {
		when(
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

		assertEmptyFileEntryTuples(searchResult);

		Mockito.verify(
			_dlAppLocalService
		).getFileEntry(
			SearchTestUtil.ENTRY_CLASS_PK
		);

		Assert.assertNull(searchResult.getSummary());

		verifyStatic();

		AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME);

		verifyStatic();

		IndexerRegistryUtil.getIndexer(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME);

		assertEmptyMBMessages(searchResult);
		assertEmptyVersions(searchResult);
	}

	@Test
	public void testDLFileEntryWithBrokenIndexer() throws Exception {
		when(
			_dlAppLocalService.getFileEntry(SearchTestUtil.ENTRY_CLASS_PK)
		).thenReturn(
			_fileEntry
		);

		Indexer indexer = Mockito.mock(Indexer.class);

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

		SearchResult searchResult = assertOneSearchResult(document);

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

		assertEmptyFileEntryTuples(searchResult);

		Mockito.verify(
			_dlAppLocalService
		).getFileEntry(
			SearchTestUtil.ENTRY_CLASS_PK
		);

		assertEmptyMBMessages(searchResult);
		assertEmptyVersions(searchResult);
	}

	protected void setUpDLAppLocalServiceUtil() {
		mockStatic(DLAppLocalServiceUtil.class, Mockito.CALLS_REAL_METHODS);

		stub(
			method(DLAppLocalServiceUtil.class, "getService")
		).toReturn(
			_dlAppLocalService
		);
	}

	private static final String _DL_FILE_ENTRY_CLASS_NAME =
		DLFileEntry.class.getName();

	@Mock
	private DLAppLocalService _dlAppLocalService;

	@Mock
	private FileEntry _fileEntry;

}