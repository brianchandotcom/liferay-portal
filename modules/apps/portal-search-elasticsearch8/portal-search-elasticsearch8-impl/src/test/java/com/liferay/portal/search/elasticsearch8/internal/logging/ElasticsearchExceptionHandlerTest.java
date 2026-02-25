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
import org.junit.rules.ExpectedException;

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
		throws Throwable {

		ElasticsearchExceptionHandler elasticsearchExceptionHandler =
			new ElasticsearchExceptionHandler(_log, false);

		elasticsearchExceptionHandler.handleDeleteDocumentException(
			new SearchException(
				ElasticsearchExceptionHandler.
					INDEX_NOT_FOUND_EXCEPTION_MESSAGE));
	}

	@Test
	public void testDeleteIndexNotFoundLogExceptionsOnlyTrue()
		throws Throwable {

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

			_assertLogCapture(logCapture, searchException, LoggerTestUtil.INFO);
		}
	}

	@Test
	public void testDeleteLogExceptionsOnlyFalse() throws Throwable {
		expectedException.expect(SearchException.class);
		expectedException.expectMessage(
			"deletion failed and results in exception");

		ElasticsearchExceptionHandler elasticsearchExceptionHandler =
			new ElasticsearchExceptionHandler(_log, false);

		elasticsearchExceptionHandler.handleDeleteDocumentException(
			new SearchException("deletion failed and results in exception"));
	}

	@Test
	public void testDeleteLogExceptionsOnlyTrue() throws Throwable {
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
				logCapture, searchException, LoggerTestUtil.ERROR);
		}
	}

	@Test
	public void testLogExceptionsOnlyFalse() throws Throwable {
		expectedException.expect(SearchException.class);
		expectedException.expectMessage("some other random message");

		ElasticsearchExceptionHandler elasticsearchExceptionHandler =
			new ElasticsearchExceptionHandler(_log, false);

		elasticsearchExceptionHandler.logOrThrow(
			new SearchException("some other random message"));
	}

	@Test
	public void testLogExceptionsOnlyTrue() throws Throwable {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ElasticsearchExceptionHandlerTest.class.getName(),
				LoggerTestUtil.ERROR)) {

			ElasticsearchExceptionHandler elasticsearchExceptionHandler =
				new ElasticsearchExceptionHandler(_log, true);

			SearchException searchException = new SearchException(
				"some random message");

			elasticsearchExceptionHandler.logOrThrow(searchException);

			_assertLogCapture(
				logCapture, searchException, LoggerTestUtil.ERROR);
		}
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private void _assertLogCapture(
		LogCapture logCapture, SearchException searchException,
		String logLevel) {

		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

		LogEntry logEntry = logEntries.get(0);

		Assert.assertEquals(logLevel, logEntry.getPriority());

		Assert.assertSame(searchException, logEntry.getThrowable());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchExceptionHandlerTest.class);

}