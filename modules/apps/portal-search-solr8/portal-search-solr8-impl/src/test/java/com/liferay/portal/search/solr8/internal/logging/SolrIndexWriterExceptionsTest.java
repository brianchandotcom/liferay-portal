/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.logging;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.solr8.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Bryan Engler
 */
public class SolrIndexWriterExceptionsTest extends BaseIndexingTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@After
	@Override
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddDocument() {
		expectedException.expect(HttpSolrClient.RemoteSolrException.class);
		expectedException.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedException.expectMessage("404 Not Found");

		addDocument(
			DocumentCreationHelpers.singleKeyword(
				Field.EXPIRATION_DATE, "text"));
	}

	@Test
	public void testAddDocuments() {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Bulk add failed");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.addDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testCommit() {
		expectedException.expect(HttpSolrClient.RemoteSolrException.class);
		expectedException.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedException.expectMessage("404 Not Found");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.commit(createSearchContext());
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testDeleteDocument() {
		expectedException.expect(HttpSolrClient.RemoteSolrException.class);
		expectedException.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedException.expectMessage("404 Not Found");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteDocument(createSearchContext(), null);
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testDeleteDocuments() {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Bulk delete failed");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteDocuments(
				createSearchContext(), Collections.singletonList(null));
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testDeleteEntityDocuments() {
		expectedException.expect(HttpSolrClient.RemoteSolrException.class);
		expectedException.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedException.expectMessage("404 Not Found");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteEntityDocuments(
				createSearchContext(), "className");
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testPartiallyUpdateDocument() {
		expectedException.expect(HttpSolrClient.RemoteSolrException.class);
		expectedException.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedException.expectMessage("404 Not Found");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.partiallyUpdateDocument(
				createSearchContext(), getTestDocument());
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testPartiallyUpdateDocuments() {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Bulk partial update failed");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.partiallyUpdateDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testUpdateDocument() {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Update failed");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.updateDocument(
				createSearchContext(), getTestDocument());
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testUpdateDocuments() {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Update failed");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.updateDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));
		}
		catch (SearchException searchException) {
		}
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new SolrIndexingFixture(
			HashMapBuilder.<String, Object>put(
				"defaultCollection", _COLLECTION_NAME
			).build());
	}

	protected Document getTestDocument() {
		Document document = new DocumentImpl();

		document.addUID(
			RandomTestUtil.randomString(), RandomTestUtil.randomLong());

		return document;
	}

	private static final String _COLLECTION_NAME = "alpha";

}