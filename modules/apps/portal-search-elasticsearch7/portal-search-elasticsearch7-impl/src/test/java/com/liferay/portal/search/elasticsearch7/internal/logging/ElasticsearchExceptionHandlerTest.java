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

package com.liferay.portal.search.elasticsearch7.internal.logging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.search.test.util.logging.ExpectedLogTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.logging.Level;

import org.junit.Before;
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

	@Before
	public void setUp() {
		expectedLogTestRule.configure(
			ElasticsearchExceptionHandlerTest.class, Level.WARNING);
	}

	@Test
	public void testDeleteIndexNotFoundLogExceptionsOnlyFalse()
		throws Throwable {

		expectedLogTestRule.configure(
			ElasticsearchExceptionHandlerTest.class, Level.INFO);
		expectedLogTestRule.expectMessage(
			ElasticsearchExceptionHandler.INDEX_NOT_FOUND_EXCEPTION_MESSAGE);

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

		expectedLogTestRule.configure(
			ElasticsearchExceptionHandlerTest.class, Level.INFO);
		expectedLogTestRule.expectMessage(
			ElasticsearchExceptionHandler.INDEX_NOT_FOUND_EXCEPTION_MESSAGE);

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

	@Test
	public void testDeleteLogExceptionsOnlyTrue() throws Throwable {
		expectedLogTestRule.expectMessage("deletion failed is only logged");

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

	@Test
	public void testLogExceptionsOnlyTrue() throws Throwable {
		expectedLogTestRule.expectMessage("some random message");

		ElasticsearchExceptionHandler elasticsearchExceptionHandler =
			new ElasticsearchExceptionHandler(_log, true);

		elasticsearchExceptionHandler.logOrThrow(
			new SearchException("some random message"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Rule
	public ExpectedLogTestRule expectedLogTestRule = ExpectedLogTestRule.none();

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchExceptionHandlerTest.class);

}