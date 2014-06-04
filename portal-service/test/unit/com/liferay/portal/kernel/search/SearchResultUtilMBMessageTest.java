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

import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.ThrowsExceptionClass;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest( {
	AssetRendererFactoryRegistryUtil.class, IndexerRegistryUtil.class,
	MBMessageLocalServiceUtil.class
})
@RunWith(PowerMockRunner.class)
public class SearchResultUtilMBMessageTest
	extends BaseSearchResultUtilTestCase {

	@Before
	public void setUp() {
		doSetUp();

		setUpMBMessage();
	}

	@Test
	public void testMBMessageMissingFromService() throws Exception {
		when(
			mbMessageLocalService.getMessage(SearchTestUtil.ENTRY_CLASS_PK)
		).thenReturn(
			null
		);

		SearchResult searchResult = searchSingleDocument(
			createMBMessageDocumentWithAlternateKey());

		Assert.assertEquals(
			SearchTestUtil.DOCUMENT_CLASS_NAME, searchResult.getClassName());
		Assert.assertEquals(
			SearchTestUtil.DOCUMENT_CLASS_PK, searchResult.getClassPK());

		List<MBMessage> mbMessages = searchResult.getMBMessages();

		Assert.assertTrue(
			"MBMessageLocalService is attempted, no message returned",
			mbMessages.isEmpty());

		Mockito.verify(
			mbMessageLocalService
		).getMessage(
			SearchTestUtil.ENTRY_CLASS_PK
		);

		Assert.assertNull(
			"Indexer and AssetRenderer are both attempted, no summary returned",
			searchResult.getSummary());

		verifyStatic();

		IndexerRegistryUtil.getIndexer(SearchTestUtil.DOCUMENT_CLASS_NAME);

		verifyStatic();

		AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
			SearchTestUtil.DOCUMENT_CLASS_NAME);

		assertThatFileEntryTuplesAndVersionsAreEmpty(searchResult);
	}

	@Test
	public void testMBMessageWithKeyInDocument() throws Exception {
		when(
			mbMessageLocalService.getMessage(SearchTestUtil.ENTRY_CLASS_PK)
		).thenReturn(
			mbMessage
		);

		mockStatic(
			IndexerRegistryUtil.class,
			new ThrowsExceptionClass(IllegalStateException.class));

		SearchResult searchResult = searchSingleDocument(
			createMBMessageDocumentWithAlternateKey());

		Assert.assertEquals(
			SearchTestUtil.DOCUMENT_CLASS_NAME, searchResult.getClassName());
		Assert.assertEquals(
			SearchTestUtil.DOCUMENT_CLASS_PK, searchResult.getClassPK());

		List<MBMessage> mbMessages = searchResult.getMBMessages();

		Assert.assertSame(mbMessage, mbMessages.get(0));
		Assert.assertEquals(1, mbMessages.size());

		Assert.assertNull(searchResult.getSummary());

		assertThatFileEntryTuplesAndVersionsAreEmpty(searchResult);
	}

	@Test
	public void testMBMessageWithoutKeyInDocument() throws Exception {
		SearchResult searchResult = searchSingleDocument(
			createMBMessageDocument());

		Assert.assertEquals(MBMESSAGE_CLASS_NAME, searchResult.getClassName());
		Assert.assertEquals(
			SearchTestUtil.ENTRY_CLASS_PK, searchResult.getClassPK());

		List<MBMessage> mbMessages = searchResult.getMBMessages();

		Assert.assertTrue(
			"MBMessageLocalService must not be invoked at all",
			mbMessages.isEmpty());

		verifyZeroInteractions(mbMessageLocalService);

		Assert.assertNull(searchResult.getSummary());

		assertThatFileEntryTuplesAndVersionsAreEmpty(searchResult);
	}

	@Test
	public void testTwoDocumentsWithSameAlternateKey() {
		long baseEntryPK = SearchTestUtil.ENTRY_CLASS_PK;

		Document documentA = createMBMessageDocumentWithAlternateKey(
			baseEntryPK);
		Document documentB = createMBMessageDocumentWithAlternateKey(
			baseEntryPK + 1);

		List<SearchResult> searchResults = getSearchResults(
			documentA, documentB);

		Assert.assertEquals("two hits, one result", 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			searchResult.getClassName(), SearchTestUtil.DOCUMENT_CLASS_NAME);
		Assert.assertEquals(
			searchResult.getClassPK(), SearchTestUtil.DOCUMENT_CLASS_PK);
	}

	protected void assertThatFileEntryTuplesAndVersionsAreEmpty(
		SearchResult searchResult) {

		assertThatFileEntryTuplesIsEmpty(searchResult);

		List<String> versions = searchResult.getVersions();

		Assert.assertTrue(versions.isEmpty());
	}

	protected Document createMBMessageDocument() {
		return SearchTestUtil.createDocument(MBMESSAGE_CLASS_NAME);
	}

	protected Document createMBMessageDocumentWithAlternateKey() {
		return SearchTestUtil.createDocumentWithAlternateKey(MBMESSAGE_CLASS_NAME);
	}

	protected Document createMBMessageDocumentWithAlternateKey(
		long entryClassPK) {

		return SearchTestUtil.createDocumentWithAlternateKey(
			MBMESSAGE_CLASS_NAME, entryClassPK);
	}

	protected void setUpMBMessage() {
		mockStatic(MBMessageLocalServiceUtil.class, Mockito.CALLS_REAL_METHODS);

		stub(
			method(MBMessageLocalServiceUtil.class, "getService")
		).toReturn(
			mbMessageLocalService
		);
	}

	protected static final String MBMESSAGE_CLASS_NAME =
		MBMessage.class.getName();

	@Mock
	protected MBMessage mbMessage;

	@Mock
	protected MBMessageLocalService mbMessageLocalService;

}