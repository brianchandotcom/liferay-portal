/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.logging;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.search.elasticsearch8.internal.ElasticsearchIndexWriter;
import com.liferay.portal.search.elasticsearch8.internal.indexing.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document.BulkDocumentRequestExecutor;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

/**
 * @author Bryan Engler
 */
public class ElasticsearchIndexWriterExceptionsTest
	extends BaseIndexingTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAddDocument() throws SearchException {
		try {
			addDocument(
				DocumentCreationHelpers.singleKeyword(
					Field.EXPIRATION_DATE, "text"));

			Assert.fail("Expected ElasticsearchException was not thrown");
		}
		catch (ElasticsearchException elasticsearchException) {
			String expectedMessage =
				"failed to parse field [expirationDate] of type [date] in " +
					"document with id";

			_assertElasticsearchException(
				message -> Assert.assertTrue(
					message + " does not contain " + expectedMessage,
					message.contains(expectedMessage)),
				elasticsearchException, "document_parsing_exception");
		}
	}

	@Test
	public void testAddDocuments() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.ERROR)) {

			String expectedMessage =
				"failed to parse field [expirationDate] of type [date] in " +
					"document with id";

			List<Document> documents = new ArrayList<>();

			Document document = new DocumentImpl();

			document.addKeyword(Field.EXPIRATION_DATE, "text");

			documents.add(document);

			IndexWriter indexWriter = getIndexWriter();

			_assertRuntimeException(
				"Bulk add failed",
				() -> indexWriter.addDocuments(
					createSearchContext(), documents));

			_assertLogCapture(
				message -> Assert.assertTrue(
					message + " does not contain " + expectedMessage,
					message.contains(expectedMessage)),
				logCapture, LoggerTestUtil.ERROR);
		}
	}

	@Test
	public void testCommit() throws SearchException {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(1);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.commit(searchContext);

			Assert.fail("Expected ElasticsearchException was not thrown");
		}
		catch (ElasticsearchException elasticsearchException) {
			_assertElasticsearchException(
				message -> Assert.assertEquals("no such index [1]", message),
				elasticsearchException, "index_not_found_exception");
		}
	}

	@Test
	public void testDeleteDocument() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ElasticsearchIndexWriter.class.getName(),
				LoggerTestUtil.INFO)) {

			SearchContext searchContext = new SearchContext();

			searchContext.setCompanyId(1);

			IndexWriter indexWriter = getIndexWriter();

			try {
				indexWriter.deleteDocument(searchContext, _UID);
			}
			catch (SearchException searchException) {
				if (_log.isDebugEnabled()) {
					_log.debug(searchException);
				}
			}

			_assertLogCapture(
				message -> Assert.assertEquals(
					StringBundler.concat(
						ElasticsearchException.class.getName(),
						": [es/delete] failed: [index_not_found_exception] no ",
						"such index [", _UID, "]"),
					message),
				logCapture, LoggerTestUtil.INFO);
		}
	}

	@Test
	public void testDeleteDocuments() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.ERROR)) {

			String expectedMessage = StringBundler.concat(
				"no such index [", _UID, "]");

			SearchContext searchContext = new SearchContext();

			searchContext.setCompanyId(1);

			List<String> uids = new ArrayList<>();

			uids.add(_UID);

			IndexWriter indexWriter = getIndexWriter();

			_assertRuntimeException(
				"Bulk delete failed",
				() -> indexWriter.deleteDocuments(searchContext, uids));

			_assertLogCapture(
				message -> Assert.assertTrue(
					message + " does not contain " + expectedMessage,
					message.contains(expectedMessage)),
				logCapture, LoggerTestUtil.ERROR);
		}
	}

	@Test
	public void testDeleteEntityDocuments() throws SearchException {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(1);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteEntityDocuments(searchContext, "test");

			Assert.fail("Expected ElasticsearchException was not thrown");
		}
		catch (ElasticsearchException elasticsearchException) {
			_assertElasticsearchException(
				message -> Assert.assertEquals("no such index [1]", message),
				elasticsearchException, "index_not_found_exception");
		}
	}

	@Test
	public void testPartiallyUpdateDocument() throws SearchException {
		Document document = new DocumentImpl();

		document.addKeyword(Field.UID, _UID);

		IndexWriter indexWriter = getIndexWriter();

		indexWriter.partiallyUpdateDocument(createSearchContext(), document);
	}

	@Test
	public void testPartiallyUpdateDocuments() throws SearchException {
		Document document = new DocumentImpl();

		List<Document> documents = new ArrayList<>();

		document.addKeyword(Field.UID, _UID);

		documents.add(document);

		IndexWriter indexWriter = getIndexWriter();

		indexWriter.partiallyUpdateDocuments(createSearchContext(), documents);
	}

	@Test
	public void testUpdateDocument() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.ERROR)) {

			String expectedMessage =
				"failed to parse field [expirationDate] of type [date] in " +
					"document with id";

			Document document = new DocumentImpl();

			document.addKeyword(Field.EXPIRATION_DATE, "text");
			document.addKeyword(Field.UID, _UID);

			IndexWriter indexWriter = getIndexWriter();

			_assertRuntimeException(
				"Update failed",
				() -> indexWriter.updateDocument(
					createSearchContext(), document));

			_assertLogCapture(
				message -> Assert.assertTrue(
					message + " does not contain " + expectedMessage,
					message.contains(expectedMessage)),
				logCapture, LoggerTestUtil.ERROR);
		}
	}

	@Test
	public void testUpdateDocuments() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.ERROR)) {

			String expectedMessage =
				"failed to parse field [expirationDate] of type [date] in " +
					"document with id";

			List<Document> documents = new ArrayList<>();

			Document document = new DocumentImpl();

			document.addKeyword(Field.EXPIRATION_DATE, "text");
			document.addKeyword(Field.UID, _UID);

			documents.add(document);

			IndexWriter indexWriter = getIndexWriter();

			_assertRuntimeException(
				"Bulk update failed",
				() -> indexWriter.updateDocuments(
					createSearchContext(), documents));

			_assertLogCapture(
				message -> Assert.assertTrue(
					message + " does not contain " + expectedMessage,
					message.contains(expectedMessage)),
				logCapture, LoggerTestUtil.ERROR);
		}
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	private void _assertElasticsearchException(
		Consumer<String> consumer,
		ElasticsearchException elasticsearchException, String expectedType) {

		Assert.assertEquals(
			expectedType,
			elasticsearchException.error(
			).type());

		consumer.accept(
			elasticsearchException.error(
			).reason());
	}

	private void _assertLogCapture(
		Consumer<String> consumer, LogCapture logCapture, String logLevel) {

		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

		LogEntry logEntry = logEntries.get(0);

		Assert.assertEquals(logLevel, logEntry.getPriority());
		consumer.accept(logEntry.getMessage());
	}

	private void _assertRuntimeException(
		String expectedMessage, ThrowingRunnable runnable) {

		RuntimeException runtimeException = Assert.assertThrows(
			RuntimeException.class, runnable);

		Assert.assertEquals(expectedMessage, runtimeException.getMessage());
	}

	private static final String _UID = "1";

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchIndexWriterExceptionsTest.class);

}