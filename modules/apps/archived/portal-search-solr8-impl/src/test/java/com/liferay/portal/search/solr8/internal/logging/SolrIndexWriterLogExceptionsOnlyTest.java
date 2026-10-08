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
import com.liferay.portal.search.solr8.internal.SolrIndexWriter;
import com.liferay.portal.search.solr8.internal.SolrUnitTestRequirements;
import com.liferay.portal.search.solr8.internal.indexing.SolrIndexingFixture;
import com.liferay.portal.search.solr8.internal.search.engine.adapter.document.BulkDocumentRequestExecutor;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

import org.junit.After;
import org.junit.Assert;
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
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
	public void testAddDocument() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SolrIndexWriter.class.getName(), LoggerTestUtil.ERROR)) {

			addDocument(
				DocumentCreationHelpers.singleKeyword(
					Field.EXPIRATION_DATE, "text"));

			_assertLogCapture(
				message -> _assertErrorMessage(_EXPECTED_PREFIX, message),
				logCapture, LoggerTestUtil.ERROR,
				HttpSolrClient.RemoteSolrException.class);
		}
	}

	@Test
	public void testAddDocuments() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SolrIndexWriter.class.getName(), LoggerTestUtil.ERROR)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.addDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));

			_assertLogCapture(
				message -> Assert.assertEquals("Bulk add failed", message),
				logCapture, LoggerTestUtil.ERROR);
		}
	}

	@Test
	public void testAddDocumentsBulkExecutor() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.WARN)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.addDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));

			_assertLogCapture(
				message -> _assertErrorMessage(_EXPECTED_BULK_PREFIX, message),
				logCapture, LoggerTestUtil.WARN);
		}
	}

	@Test
	public void testCommit() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SolrIndexWriter.class.getName(), LoggerTestUtil.ERROR)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.commit(createSearchContext());

			_assertLogCapture(
				message -> _assertErrorMessage(_EXPECTED_PREFIX, message),
				logCapture, LoggerTestUtil.ERROR,
				HttpSolrClient.RemoteSolrException.class);
		}
	}

	@Test
	public void testDeleteDocument() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SolrIndexWriter.class.getName(), LoggerTestUtil.ERROR)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.deleteDocument(createSearchContext(), null);

			_assertLogCapture(
				message -> _assertErrorMessage(_EXPECTED_PREFIX, message),
				logCapture, LoggerTestUtil.ERROR,
				HttpSolrClient.RemoteSolrException.class);
		}
	}

	@Test
	public void testDeleteDocuments() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SolrIndexWriter.class.getName(), LoggerTestUtil.ERROR)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.deleteDocuments(
				createSearchContext(), Collections.singletonList(null));

			_assertLogCapture(
				message -> Assert.assertEquals("Bulk delete failed", message),
				logCapture, LoggerTestUtil.ERROR);
		}
	}

	@Test
	public void testDeleteDocumentsBulkExecutor() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.WARN)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.deleteDocuments(
				createSearchContext(), Collections.singletonList(null));

			_assertLogCapture(
				message -> _assertErrorMessage(_EXPECTED_BULK_PREFIX, message),
				logCapture, LoggerTestUtil.WARN);
		}
	}

	@Test
	public void testDeleteEntityDocuments() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SolrIndexWriter.class.getName(), LoggerTestUtil.ERROR)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.deleteEntityDocuments(createSearchContext(), null);

			String expectedMessage =
				"Cannot invoke \"String.isEmpty()\" because \"value\" is null";

			_assertLogCapture(
				message -> Assert.assertEquals(expectedMessage, message),
				logCapture, LoggerTestUtil.ERROR, NullPointerException.class);
		}
	}

	@Test
	public void testPartiallyUpdateDocument() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SolrIndexWriter.class.getName(), LoggerTestUtil.ERROR)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.partiallyUpdateDocument(
				createSearchContext(), getTestDocument());

			_assertLogCapture(
				message -> _assertErrorMessage(_EXPECTED_PREFIX, message),
				logCapture, LoggerTestUtil.ERROR,
				HttpSolrClient.RemoteSolrException.class);
		}
	}

	@Test
	public void testPartiallyUpdateDocuments() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SolrIndexWriter.class.getName(), LoggerTestUtil.ERROR)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.partiallyUpdateDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));

			_assertLogCapture(
				message -> Assert.assertEquals(
					"Bulk partial update failed", message),
				logCapture, LoggerTestUtil.ERROR);
		}
	}

	@Test
	public void testPartiallyUpdateDocumentsBulkExecutor()
		throws SearchException {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.WARN)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.partiallyUpdateDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));

			_assertLogCapture(
				message -> _assertErrorMessage(_EXPECTED_BULK_PREFIX, message),
				logCapture, LoggerTestUtil.WARN);
		}
	}

	@Test
	public void testUpdateDocument() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SolrIndexWriter.class.getName(), LoggerTestUtil.ERROR)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.updateDocument(
				createSearchContext(), getTestDocument());

			String expectedMessagePrefix = "Update failed: " + _EXPECTED_PREFIX;

			_assertLogCapture(
				message -> _assertErrorMessage(expectedMessagePrefix, message),
				logCapture, LoggerTestUtil.ERROR);
		}
	}

	@Test
	public void testUpdateDocumentBulkExecutor() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.WARN)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.updateDocument(
				createSearchContext(), getTestDocument());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			for (LogEntry logEntry : logEntries) {
				_assertLogEntry(
					message -> _assertErrorMessage(
						_EXPECTED_BULK_PREFIX, message),
					logEntry, LoggerTestUtil.WARN, null);
			}
		}
	}

	@Test
	public void testUpdateDocuments() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SolrIndexWriter.class.getName(), LoggerTestUtil.ERROR)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.updateDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));

			String expectedMessagePrefix = "Update failed: " + _EXPECTED_PREFIX;

			_assertLogCapture(
				message -> _assertErrorMessage(expectedMessagePrefix, message),
				logCapture, LoggerTestUtil.ERROR);
		}
	}

	@Test
	public void testUpdateDocumentsBulkExecutor() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.WARN)) {

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.updateDocuments(
				createSearchContext(),
				Collections.singletonList(getTestDocument()));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			for (LogEntry logEntry : logEntries) {
				_assertLogEntry(
					message -> _assertErrorMessage(
						_EXPECTED_BULK_PREFIX, message),
					logEntry, LoggerTestUtil.WARN, null);
			}
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

	private void _assertErrorMessage(String expectedPrefix, String message) {
		Assert.assertTrue(
			message + " does not contain " + _EXPECTED_MIME_TYPE,
			message.contains(_EXPECTED_MIME_TYPE));
		Assert.assertTrue(
			message + " does not start with " + expectedPrefix,
			message.startsWith(expectedPrefix));
		Assert.assertTrue(
			message + " does not contain " + _EXPECTED_STATUS,
			message.contains(_EXPECTED_STATUS));
	}

	private void _assertLogCapture(
		Consumer<String> consumer, LogCapture logCapture, String logLevel) {

		_assertLogCapture(consumer, logCapture, logLevel, null);
	}

	private void _assertLogCapture(
		Consumer<String> consumer, LogCapture logCapture, String logLevel,
		Class<? extends Throwable> throwableClass) {

		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

		_assertLogEntry(consumer, logEntries.get(0), logLevel, throwableClass);
	}

	private void _assertLogEntry(
		Consumer<String> consumer, LogEntry logEntry, String logLevel,
		Class<? extends Throwable> throwableClass) {

		Assert.assertEquals(logLevel, logEntry.getPriority());

		Throwable throwable = logEntry.getThrowable();

		if (throwableClass == null) {
			Assert.assertNull(throwable);
		}
		else {
			Assert.assertSame(throwableClass, throwable.getClass());
		}

		consumer.accept(logEntry.getMessage());
	}

	private static final String _COLLECTION_NAME = "alpha";

	private static final String _EXPECTED_BULK_PREFIX =
		"{class=class " + HttpSolrClient.RemoteSolrException.class.getName() +
			", message=Error from server at";

	private static final String _EXPECTED_MIME_TYPE =
		"Expected mime type application/octet-stream but got text";

	private static final String _EXPECTED_PREFIX = "Error from server at";

	private static final String _EXPECTED_STATUS = "Error 404 Not Found";

}