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

package com.liferay.portal.search.solr7.internal.logging;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.solr7.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import java.util.Collections;
import java.util.Map;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Bryan Engler
 */
public class SolrIndexWriterExceptionsTest extends BaseIndexingTestCase {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddDocument() {
		expectedException.expect(HttpSolrClient.RemoteSolrException.class);
		expectedException.expectMessage(
			"Problem accessing /solr/liferay/" + _COLLECTION_NAME);

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
		expectedException.expectMessage(
			"Problem accessing /solr/liferay/" + _COLLECTION_NAME);

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
		expectedException.expectMessage(
			"Problem accessing /solr/liferay/" + _COLLECTION_NAME);

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
		expectedException.expectMessage(
			"Problem accessing /solr/liferay/" + _COLLECTION_NAME);

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
		expectedException.expectMessage(
			"Problem accessing /solr/liferay/" + _COLLECTION_NAME);

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
		Map<String, Object> solrConfigurationProperties =
			HashMapBuilder.<String, Object>put(
				"defaultCollection", _COLLECTION_NAME
			).build();

		return new SolrIndexingFixture(solrConfigurationProperties);
	}

	protected Document getTestDocument() {
		Document document = new DocumentImpl();

		document.addUID(
			RandomTestUtil.randomString(), RandomTestUtil.randomLong());

		return document;
	}

	private static final String _COLLECTION_NAME = "alpha";

}