/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.logging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class ElasticsearchExceptionHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDeleteIndexNotFoundLogExceptionsOnlyFalse()
		throws SearchException {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ElasticsearchExceptionHandlerTest.class.getName(),
				LoggerTestUtil.INFO)) {

			ElasticsearchExceptionHandler elasticsearchExceptionHandler =
				new ElasticsearchExceptionHandler(_log, false);

			SearchException searchException = new SearchException(
				ElasticsearchExceptionHandler.
					INDEX_NOT_FOUND_EXCEPTION_MESSAGE);

			elasticsearchExceptionHandler.handleDeleteDocumentException(
				searchException);

			_assertLogCapture(logCapture, LoggerTestUtil.INFO, searchException);
		}
	}

	@Test
	public void testDeleteIndexNotFoundLogExceptionsOnlyTrue()
		throws SearchException {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ElasticsearchExceptionHandlerTest.class.getName(),
				LoggerTestUtil.INFO)) {

			ElasticsearchExceptionHandler elasticsearchExceptionHandler =
				new ElasticsearchExceptionHandler(_log, true);

			SearchException searchException = new SearchException(
				ElasticsearchExceptionHandler.
					INDEX_NOT_FOUND_EXCEPTION_MESSAGE);

			elasticsearchExceptionHandler.handleDeleteDocumentException(
				searchException);

			_assertLogCapture(logCapture, LoggerTestUtil.INFO, searchException);
		}
	}

	@Test
	public void testDeleteLogExceptionsOnlyFalse() {
		ElasticsearchExceptionHandler elasticsearchExceptionHandler =
			new ElasticsearchExceptionHandler(_log, false);

		SearchException searchException1 = new SearchException(
			"deletion failed and results in exception");

		try {
			elasticsearchExceptionHandler.handleDeleteDocumentException(
				searchException1);

			Assert.fail();
		}
		catch (SearchException searchException2) {
			Assert.assertSame(searchException1, searchException2);
		}
	}

	@Test
	public void testDeleteLogExceptionsOnlyTrue() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ElasticsearchExceptionHandlerTest.class.getName(),
				LoggerTestUtil.ERROR)) {

			ElasticsearchExceptionHandler elasticsearchExceptionHandler =
				new ElasticsearchExceptionHandler(_log, true);

			SearchException searchException = new SearchException(
				"deletion failed is only logged");

			elasticsearchExceptionHandler.handleDeleteDocumentException(
				searchException);

			_assertLogCapture(
				logCapture, LoggerTestUtil.ERROR, searchException);
		}
	}

	@Test
	public void testLogExceptionsOnlyFalse() {
		ElasticsearchExceptionHandler elasticsearchExceptionHandler =
			new ElasticsearchExceptionHandler(_log, false);

		SearchException searchException1 = new SearchException(
			"some other random message");

		try {
			elasticsearchExceptionHandler.logOrThrow(searchException1);

			Assert.fail();
		}
		catch (SearchException searchException2) {
			Assert.assertSame(searchException1, searchException2);
		}
	}

	@Test
	public void testLogExceptionsOnlyTrue() throws SearchException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ElasticsearchExceptionHandlerTest.class.getName(),
				LoggerTestUtil.ERROR)) {

			ElasticsearchExceptionHandler elasticsearchExceptionHandler =
				new ElasticsearchExceptionHandler(_log, true);

			SearchException searchException = new SearchException(
				"some random message");

			elasticsearchExceptionHandler.logOrThrow(searchException);

			_assertLogCapture(
				logCapture, LoggerTestUtil.ERROR, searchException);
		}
	}

	private void _assertLogCapture(
		LogCapture logCapture, String logLevel,
		SearchException searchException) {

		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

		LogEntry logEntry = logEntries.get(0);

		Assert.assertEquals(logLevel, logEntry.getPriority());
		Assert.assertEquals(searchException.toString(), logEntry.getMessage());
		Assert.assertSame(searchException, logEntry.getThrowable());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchExceptionHandlerTest.class);

}