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
import com.liferay.portal.search.test.util.logging.ExpectedLog;
import com.liferay.portal.search.test.util.logging.ExpectedLogMethodTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

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

	@ExpectedLog(
		expectedClass = SolrIndexWriter.class,
		expectedLevel = ExpectedLog.Level.WARNING, expectedLog = "404 Not Found"
	)
	@Test
	public void testAddDocument() throws Exception {
		addDocument(
			DocumentCreationHelpers.singleKeyword(
				Field.EXPIRATION_DATE, "text"));
	}

	@ExpectedLog(
		expectedClass = SolrIndexWriter.class,
		expectedLevel = ExpectedLog.Level.WARNING,
		expectedLog = "Bulk add failed"
	)
	@Test
	public void testAddDocuments() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.addDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = BulkDocumentRequestExecutorImpl.class,
		expectedLevel = ExpectedLog.Level.WARNING, expectedLog = "404 Not Found"
	)
	@Test
	public void testAddDocumentsBulkExecutor() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.addDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = SolrIndexWriter.class,
		expectedLevel = ExpectedLog.Level.WARNING, expectedLog = "404 Not Found"
	)
	@Test
	public void testCommit() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.commit(createSearchContext());
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = SolrIndexWriter.class,
		expectedLevel = ExpectedLog.Level.WARNING, expectedLog = "404 Not Found"
	)
	@Test
	public void testDeleteDocument() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteDocument(createSearchContext(), null);
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = SolrIndexWriter.class,
		expectedLevel = ExpectedLog.Level.WARNING,
		expectedLog = "Bulk delete failed"
	)
	@Test
	public void testDeleteDocuments() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteDocuments(
				createSearchContext(), Collections.singletonList(null));
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = BulkDocumentRequestExecutorImpl.class,
		expectedLevel = ExpectedLog.Level.WARNING, expectedLog = "404 Not Found"
	)
	@Test
	public void testDeleteDocumentsBulkExecutor() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteDocuments(
				createSearchContext(), Collections.singletonList(null));
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = SolrIndexWriter.class,
		expectedLevel = ExpectedLog.Level.WARNING, expectedLog = "null"
	)
	@Test
	public void testDeleteEntityDocuments() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteEntityDocuments(createSearchContext(), null);
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = SolrIndexWriter.class,
		expectedLevel = ExpectedLog.Level.WARNING, expectedLog = "404 Not Found"
	)
	@Test
	public void testPartiallyUpdateDocument() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.partiallyUpdateDocument(
				createSearchContext(), getTestDocument());
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = SolrIndexWriter.class,
		expectedLevel = ExpectedLog.Level.WARNING,
		expectedLog = "Bulk partial update failed"
	)
	@Test
	public void testPartiallyUpdateDocuments() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.partiallyUpdateDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = BulkDocumentRequestExecutorImpl.class,
		expectedLevel = ExpectedLog.Level.WARNING, expectedLog = "404 Not Found"
	)
	@Test
	public void testPartiallyUpdateDocumentsBulkExecutor() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.partiallyUpdateDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = SolrIndexWriter.class,
		expectedLevel = ExpectedLog.Level.WARNING, expectedLog = "Update failed"
	)
	@Test
	public void testUpdateDocument() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.updateDocument(
				createSearchContext(), getTestDocument());
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = BulkDocumentRequestExecutorImpl.class,
		expectedLevel = ExpectedLog.Level.WARNING, expectedLog = "404 Not Found"
	)
	@Test
	public void testUpdateDocumentBulkExecutor() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.updateDocument(
				createSearchContext(), getTestDocument());
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = SolrIndexWriter.class,
		expectedLevel = ExpectedLog.Level.WARNING, expectedLog = "Update failed"
	)
	@Test
	public void testUpdateDocuments() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.updateDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));
		}
		catch (SearchException searchException) {
		}
	}

	@ExpectedLog(
		expectedClass = BulkDocumentRequestExecutorImpl.class,
		expectedLevel = ExpectedLog.Level.WARNING, expectedLog = "404 Not Found"
	)
	@Test
	public void testUpdateDocumentsBulkExecutor() {
		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.updateDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));
		}
		catch (SearchException searchException) {
		}
	}

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