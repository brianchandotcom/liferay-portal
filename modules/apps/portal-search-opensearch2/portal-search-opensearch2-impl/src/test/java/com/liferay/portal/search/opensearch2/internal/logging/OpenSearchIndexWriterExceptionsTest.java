/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.logging;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.opensearch2.internal.OpenSearchIndexWriter;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document.BulkDocumentRequestExecutor;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.opensearch._types.ErrorCause;
import org.opensearch.client.opensearch._types.OpenSearchException;

/**
 * @author Bryan Engler
 */
public class OpenSearchIndexWriterExceptionsTest extends BaseIndexingTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		String indexName = String.valueOf(_COMPANY_ID);

		SearchEngineAdapter searchEngineAdapter = getSearchEngineAdapter();

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			searchEngineAdapter.execute(
				new IndicesExistsIndexRequest(indexName));

		if (!indicesExistsIndexResponse.isExists()) {
			return;
		}

		searchEngineAdapter.execute(new DeleteIndexRequest(indexName));
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		IndexWriter indexWriter = getIndexWriter();

		if (indexWriter == null) {
			return;
		}

		indexWriter.deleteDocument(createSearchContext(), _UID);
	}

	@Test
	public void testAddDocument() {
		try {
			addDocument(
				DocumentCreationHelpers.singleKeyword(
					Field.EXPIRATION_DATE, _INVALID_DATE));

			Assert.fail();
		}
		catch (OpenSearchException openSearchException) {
			String expectedMessage =
				"failed to parse field [expirationDate] of type [date] in " +
					"document with id";

			_assertOpenSearchException(
				message -> Assert.assertTrue(
					message + " does not contain " + expectedMessage,
					message.contains(expectedMessage)),
				"mapper_parsing_exception", openSearchException);
		}
	}

	@Test
	public void testAddDocuments() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.ERROR)) {

			Document document = new DocumentImpl();

			document.addKeyword(Field.EXPIRATION_DATE, _INVALID_DATE);

			IndexWriter indexWriter = getIndexWriter();

			try {
				indexWriter.addDocuments(
					createSearchContext(), Arrays.asList(document));

				Assert.fail();
			}
			catch (SystemException systemException) {
				Assert.assertEquals(
					"Bulk add failed", systemException.getMessage());
			}

			String expectedMessage =
				"failed to parse field [expirationDate] of type [date] in " +
					"document with id";

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

		searchContext.setCompanyId(_COMPANY_ID);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.commit(searchContext);

			Assert.fail();
		}
		catch (OpenSearchException openSearchException) {
			_assertOpenSearchException(
				message -> Assert.assertEquals(
					"no such index [" + _COMPANY_ID + "]", message),
				"index_not_found_exception", openSearchException);
		}
	}

	@Test
	public void testDeleteDocument() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				OpenSearchIndexWriter.class.getName(), LoggerTestUtil.INFO)) {

			SearchContext searchContext = new SearchContext();

			searchContext.setCompanyId(_COMPANY_ID);

			IndexWriter indexWriter = getIndexWriter();

			indexWriter.deleteDocument(searchContext, _UID);

			String expectedMessage = StringBundler.concat(
				OpenSearchException.class.getName(),
				": Request failed: [index_not_found_exception] no such index ",
				"[", _COMPANY_ID, "]");

			_assertLogCapture(
				message -> Assert.assertEquals(expectedMessage, message),
				logCapture, LoggerTestUtil.INFO, OpenSearchException.class);
		}
	}

	@Test
	public void testDeleteDocuments() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.ERROR)) {

			SearchContext searchContext = new SearchContext();

			searchContext.setCompanyId(_COMPANY_ID);

			IndexWriter indexWriter = getIndexWriter();

			try {
				indexWriter.deleteDocuments(searchContext, Arrays.asList(_UID));

				Assert.fail();
			}
			catch (SystemException systemException) {
				Assert.assertEquals(
					"Bulk delete failed", systemException.getMessage());
			}

			String expectedMessage = "no such index [" + _COMPANY_ID + "]";

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

		searchContext.setCompanyId(_COMPANY_ID);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteEntityDocuments(searchContext, "test");

			Assert.fail();
		}
		catch (OpenSearchException openSearchException) {
			_assertOpenSearchException(
				message -> Assert.assertEquals(
					"no such index [" + _COMPANY_ID + "]", message),
				"index_not_found_exception", openSearchException);
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
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.ERROR)) {

			Document document = new DocumentImpl();

			document.addKeyword(Field.UID, _UID);

			IndexWriter indexWriter = getIndexWriter();

			try {
				indexWriter.partiallyUpdateDocuments(
					createSearchContext(), Arrays.asList(document));

				Assert.fail();
			}
			catch (SystemException systemException) {
				Assert.assertEquals(
					"Bulk partial update failed", systemException.getMessage());
			}

			String expectedMessage = "[" + _UID + "]: document missing";

			_assertLogCapture(
				message -> Assert.assertTrue(
					message + " does not contain " + expectedMessage,
					message.contains(expectedMessage)),
				logCapture, LoggerTestUtil.ERROR);
		}
	}

	@Test
	public void testUpdateDocument() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.ERROR)) {

			Document document = new DocumentImpl();

			document.addKeyword(Field.EXPIRATION_DATE, _INVALID_DATE);
			document.addKeyword(Field.UID, _UID);

			IndexWriter indexWriter = getIndexWriter();

			try {
				indexWriter.updateDocument(createSearchContext(), document);

				Assert.fail();
			}
			catch (SystemException systemException) {
				Assert.assertEquals(
					"Update failed", systemException.getMessage());
			}

			String expectedMessage =
				"failed to parse field [expirationDate] of type [date] in " +
					"document with id";

			_assertLogCapture(
				message -> Assert.assertTrue(
					message + " does not contain " + expectedMessage,
					message.contains(expectedMessage)),
				logCapture, LoggerTestUtil.ERROR);
		}
	}

	@Test
	public void testUpdateDocuments() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BulkDocumentRequestExecutor.class.getName(),
				LoggerTestUtil.ERROR)) {

			Document document = new DocumentImpl();

			document.addKeyword(Field.EXPIRATION_DATE, _INVALID_DATE);
			document.addKeyword(Field.UID, _UID);

			IndexWriter indexWriter = getIndexWriter();

			try {
				indexWriter.updateDocuments(
					createSearchContext(), Arrays.asList(document));

				Assert.fail();
			}
			catch (SystemException systemException) {
				Assert.assertEquals(
					"Bulk update failed", systemException.getMessage());
			}

			String expectedMessage =
				"failed to parse field [expirationDate] of type [date] in " +
					"document with id";

			_assertLogCapture(
				message -> Assert.assertTrue(
					message + " does not contain " + expectedMessage,
					message.contains(expectedMessage)),
				logCapture, LoggerTestUtil.ERROR);
		}
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

	private void _assertLogCapture(
		Consumer<String> consumer, LogCapture logCapture, String logLevel) {

		_assertLogCapture(consumer, logCapture, logLevel, null);
	}

	private void _assertLogCapture(
		Consumer<String> consumer, LogCapture logCapture, String logLevel,
		Class<?> throwableClass) {

		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

		LogEntry logEntry = logEntries.get(0);

		Assert.assertEquals(logLevel, logEntry.getPriority());

		Throwable throwable = logEntry.getThrowable();

		if (throwableClass == null) {
			Assert.assertNull(String.valueOf(throwable), throwable);
		}
		else {
			Assert.assertSame(throwableClass, throwable.getClass());
		}

		consumer.accept(logEntry.getMessage());
	}

	private void _assertOpenSearchException(
		Consumer<String> consumer, String expectedType,
		OpenSearchException openSearchException) {

		ErrorCause errorCause = openSearchException.error();

		Assert.assertEquals(expectedType, errorCause.type());
		consumer.accept(errorCause.reason());
	}

	private static final long _COMPANY_ID = 1;

	private static final String _INVALID_DATE = "text";

	private static final String _UID = "1";

}