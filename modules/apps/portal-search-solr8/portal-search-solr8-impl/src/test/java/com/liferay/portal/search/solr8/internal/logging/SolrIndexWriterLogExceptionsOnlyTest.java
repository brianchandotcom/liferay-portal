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

package com.liferay.portal.search.solr8.internal.logging;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.solr8.internal.SolrIndexWriter;
import com.liferay.portal.search.solr8.internal.SolrIndexingFixture;
import com.liferay.portal.search.solr8.internal.SolrUnitTestRequirements;
import com.liferay.portal.search.solr8.internal.search.engine.adapter.document.BulkDocumentRequestExecutorImpl;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.logging.ExpectedLogMethodTestRule;
import com.liferay.portal.search.test.util.logging.ExpectedLogTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bryan Engler
 */
public class SolrIndexWriterLogExceptionsOnlyTest extends BaseIndexingTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			ExpectedLogMethodTestRule.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Assume.assumeTrue(
			SolrUnitTestRequirements.isSolrExternallyStartedByDeveloper());
	}

	@After
	@Override
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddDocument() throws Exception {
		expectedLogTestRule.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedLogTestRule.expectMessage("404 Not Found");

		addDocument(
			DocumentCreationHelpers.singleKeyword(
				Field.EXPIRATION_DATE, "text"));
	}

	@Test
	public void testAddDocuments() {
		expectedLogTestRule.expectMessage("Bulk add failed");

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
	public void testAddDocumentsBulkExecutor() {
		expectedLogTestRule.configure(
			BulkDocumentRequestExecutorImpl.class, Level.WARNING);
		expectedLogTestRule.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedLogTestRule.expectMessage("404 Not Found");

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
		expectedLogTestRule.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedLogTestRule.expectMessage("404 Not Found");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.commit(createSearchContext());
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testDeleteDocument() {
		expectedLogTestRule.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedLogTestRule.expectMessage("404 Not Found");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteDocument(createSearchContext(), null);
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testDeleteDocuments() {
		expectedLogTestRule.expectMessage("Bulk delete failed");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteDocuments(
				createSearchContext(), Collections.singletonList(null));
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testDeleteDocumentsBulkExecutor() {
		expectedLogTestRule.configure(
			BulkDocumentRequestExecutorImpl.class, Level.WARNING);
		expectedLogTestRule.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedLogTestRule.expectMessage("404 Not Found");

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
		expectedLogTestRule.expectMessage("java.lang.NullPointerException");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteEntityDocuments(createSearchContext(), null);
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testPartiallyUpdateDocument() {
		expectedLogTestRule.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedLogTestRule.expectMessage("404 Not Found");

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
		expectedLogTestRule.expectMessage("Bulk partial update failed");

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
	public void testPartiallyUpdateDocumentsBulkExecutor() {
		expectedLogTestRule.configure(
			BulkDocumentRequestExecutorImpl.class, Level.WARNING);
		expectedLogTestRule.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedLogTestRule.expectMessage("404 Not Found");

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
		expectedLogTestRule.expectMessage("Update failed");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.updateDocument(
				createSearchContext(), getTestDocument());
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testUpdateDocumentBulkExecutor() {
		expectedLogTestRule.configure(
			BulkDocumentRequestExecutorImpl.class, Level.WARNING);
		expectedLogTestRule.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedLogTestRule.expectMessage("404 Not Found");

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
		expectedLogTestRule.expectMessage("Update failed");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.updateDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));
		}
		catch (SearchException searchException) {
		}
	}

	@Test
	public void testUpdateDocumentsBulkExecutor() {
		expectedLogTestRule.configure(
			BulkDocumentRequestExecutorImpl.class, Level.WARNING);
		expectedLogTestRule.expectMessage("/solr/liferay/" + _COLLECTION_NAME);
		expectedLogTestRule.expectMessage("404 Not Found");

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
	public ExpectedLogTestRule expectedLogTestRule = ExpectedLogTestRule.with(
		SolrIndexWriter.class, Level.WARNING);

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new SolrIndexingFixture(
			HashMapBuilder.<String, Object>put(
				"defaultCollection", _COLLECTION_NAME
			).put(
				"logExceptionsOnly", true
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