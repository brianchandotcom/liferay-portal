/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.logging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.test.util.logging.ExpectedLog;
import com.liferay.portal.search.test.util.logging.ExpectedLogMethodTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			ExpectedLogMethodTestRule.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@ExpectedLog(
		expectedClass = ElasticsearchExceptionHandlerTest.class,
		expectedLevel = ExpectedLog.Level.INFO,
		expectedLog = ElasticsearchExceptionHandler.INDEX_NOT_FOUND_EXCEPTION_MESSAGE
	)
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

	@ExpectedLog(
		expectedClass = ElasticsearchExceptionHandlerTest.class,
		expectedLevel = ExpectedLog.Level.INFO,
		expectedLog = ElasticsearchExceptionHandler.INDEX_NOT_FOUND_EXCEPTION_MESSAGE
	)
	@Test
	public void testDeleteIndexNotFoundLogExceptionsOnlyTrue()
		throws Throwable {

		ElasticsearchExceptionHandler elasticsearchExceptionHandler =
			new ElasticsearchExceptionHandler(_log, true);

		elasticsearchExceptionHandler.handleDeleteDocumentException(
			new SearchException(
				ElasticsearchExceptionHandler.
					INDEX_NOT_FOUND_EXCEPTION_MESSAGE));
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

	@ExpectedLog(
		expectedClass = ElasticsearchExceptionHandlerTest.class,
		expectedLevel = ExpectedLog.Level.WARNING,
		expectedLog = "deletion failed is only logged"
	)
	@Test
	public void testDeleteLogExceptionsOnlyTrue() throws Throwable {
		ElasticsearchExceptionHandler elasticsearchExceptionHandler =
			new ElasticsearchExceptionHandler(_log, true);

		elasticsearchExceptionHandler.handleDeleteDocumentException(
			new SearchException("deletion failed is only logged"));
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

	@ExpectedLog(
		expectedClass = ElasticsearchExceptionHandlerTest.class,
		expectedLevel = ExpectedLog.Level.WARNING,
		expectedLog = "some random message"
	)
	@Test
	public void testLogExceptionsOnlyTrue() throws Throwable {
		ElasticsearchExceptionHandler elasticsearchExceptionHandler =
			new ElasticsearchExceptionHandler(_log, true);

		elasticsearchExceptionHandler.logOrThrow(
			new SearchException("some random message"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchExceptionHandlerTest.class);

}