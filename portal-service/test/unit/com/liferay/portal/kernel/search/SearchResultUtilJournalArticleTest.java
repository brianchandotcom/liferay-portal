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
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.List;

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
@PrepareForTest({IndexerRegistryUtil.class})
@RunWith(PowerMockRunner.class)
public class SearchResultUtilJournalArticleTest
	extends BaseSearchResultUtilTestCase {

	@Before
	public void setUp() throws Exception {
		doSetUp();
	}

	@Test
	public void testJournalArticle() {
		searchSingleDocument(newDocumentJournalArticleWithVersion());

		assertThatSearchResultHasVersion();

		Assert.assertNull(
			"Summary should be null with no Indexer or AssetRenderer defined",
			result.getSummary());
	}

	@Test
	public void testJournalArticleWithDefectiveIndexer() throws Exception {
		Indexer indexer = Mockito.mock(Indexer.class);

		Mockito.doThrow(
			IllegalArgumentException.class
		).when(
			indexer
		).getSummary(
			(Document)Matchers.any(), Matchers.anyString(),
			(PortletURL)Matchers.any(), (PortletRequest)Matchers.any(),
			(PortletResponse)Matchers.any());

		stub(
			method(IndexerRegistryUtil.class, "getIndexer", String.class)
		).toReturn(
			indexer
		);

		Document document = newDocumentJournalArticleWithVersion();

		searchSingleDocument(document);

		assertThatSearchResultHasVersion();

		Assert.assertNull(
			"Indexer is attempted, exception is discarded, no summary returned",
			result.getSummary());

		Mockito.verify(
			indexer
		).getSummary(
			document, StringPool.BLANK, portletURL, null, null
		);
	}

	protected void assertThatSearchResultHasVersion() {
		Assert.assertEquals(JOURNALARTICLE_CLASS_NAME, result.getClassName());
		Assert.assertEquals(ENTRY_CLASS_PK, result.getClassPK());

		Assert.assertThat(
			result.getFileEntryTuples(), IsEmptyCollection.empty());
		Assert.assertThat(result.getMBMessages(), IsEmptyCollection.empty());

		List<String> versions = result.getVersions();

		Assert.assertEquals(DOCUMENT_VERSION, versions.get(0));
		Assert.assertEquals(1, versions.size());
	}

	protected Document newDocumentJournalArticleWithVersion() {
		Document document = newDocument(JOURNALARTICLE_CLASS_NAME);

		document.add(new Field(Field.VERSION, DOCUMENT_VERSION));

		return document;
	}

	protected static final String DOCUMENT_VERSION = String.valueOf(
		RandomTestUtil.randomInt());

	protected static final String JOURNALARTICLE_CLASS_NAME =
		JournalArticle.class.getName();

}