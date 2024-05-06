/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.logging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.test.rule.logging.ExpectedLogMethodTestRule;
import com.liferay.portal.search.test.util.logging.ExpectedLog;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Adam Brandizzi
 */
public class OpenSearchExceptionHandlerTest {

	@ClassRule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			ExpectedLogMethodTestRule.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@ExpectedLog(
		expectedClass = OpenSearchExceptionHandlerTest.class,
		expectedLevel = ExpectedLog.Level.INFO,
		expectedLog = OpenSearchExceptionHandler.INDEX_NOT_FOUND_EXCEPTION_MESSAGE
	)
	@Test
	public void testDeleteIndexNotFoundLogExceptionsOnlyFalse()
		throws Throwable {

		OpenSearchExceptionHandler openSearchExceptionHandler =
			new OpenSearchExceptionHandler(_log, false);

		openSearchExceptionHandler.handleDeleteDocumentException(
			new SearchException(
				OpenSearchExceptionHandler.INDEX_NOT_FOUND_EXCEPTION_MESSAGE));
	}

	@ExpectedLog(
		expectedClass = OpenSearchExceptionHandlerTest.class,
		expectedLevel = ExpectedLog.Level.INFO,
		expectedLog = OpenSearchExceptionHandler.INDEX_NOT_FOUND_EXCEPTION_MESSAGE
	)
	@Test
	public void testDeleteIndexNotFoundLogExceptionsOnlyTrue()
		throws Throwable {

		OpenSearchExceptionHandler openSearchExceptionHandler =
			new OpenSearchExceptionHandler(_log, true);

		openSearchExceptionHandler.handleDeleteDocumentException(
			new SearchException(
				OpenSearchExceptionHandler.INDEX_NOT_FOUND_EXCEPTION_MESSAGE));
	}

	@Test
	public void testDeleteLogExceptionsOnlyFalse() throws Throwable {
		expectedException.expect(SearchException.class);
		expectedException.expectMessage(
			"deletion failed and results in exception");

		OpenSearchExceptionHandler openSearchExceptionHandler =
			new OpenSearchExceptionHandler(_log, false);

		openSearchExceptionHandler.handleDeleteDocumentException(
			new SearchException("deletion failed and results in exception"));
	}

	@ExpectedLog(
		expectedClass = OpenSearchExceptionHandlerTest.class,
		expectedLevel = ExpectedLog.Level.WARNING,
		expectedLog = "deletion failed is only logged"
	)
	@Test
	public void testDeleteLogExceptionsOnlyTrue() throws Throwable {
		OpenSearchExceptionHandler openSearchExceptionHandler =
			new OpenSearchExceptionHandler(_log, true);

		openSearchExceptionHandler.handleDeleteDocumentException(
			new SearchException("deletion failed is only logged"));
	}

	@Test
	public void testLogExceptionsOnlyFalse() throws Throwable {
		expectedException.expect(SearchException.class);
		expectedException.expectMessage("some other random message");

		OpenSearchExceptionHandler openSearchExceptionHandler =
			new OpenSearchExceptionHandler(_log, false);

		openSearchExceptionHandler.logOrThrow(
			new SearchException("some other random message"));
	}

	@ExpectedLog(
		expectedClass = OpenSearchExceptionHandlerTest.class,
		expectedLevel = ExpectedLog.Level.WARNING,
		expectedLog = "some random message"
	)
	@Test
	public void testLogExceptionsOnlyTrue() throws Throwable {
		OpenSearchExceptionHandler openSearchExceptionHandler =
			new OpenSearchExceptionHandler(_log, true);

		openSearchExceptionHandler.logOrThrow(
			new SearchException("some random message"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static final Log _log = LogFactoryUtil.getLog(
		OpenSearchExceptionHandlerTest.class);

}