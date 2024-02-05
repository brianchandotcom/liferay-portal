/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.connection.IndexName;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;

import org.opensearch.client.opensearch._types.ErrorResponse;
import org.opensearch.client.opensearch._types.OpenSearchException;

/**
 * @author Bryan Engler
 * @author Petteri Karttunen
 */
public class LiferayTypeMappingsModifiedDateFieldTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_liferayIndexFixture = new LiferayIndexFixture(
			new IndexName(testName.getMethodName()));

		_liferayIndexFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_liferayIndexFixture.tearDown();
	}

	@Test
	public void testDate() throws Exception {
		expectedException.expect(OpenSearchException.class);
		expectedException.expectMessage(
			"[mapper_parsing_exception] failed to parse field [modified] of " +
				"type [date]");

		index(new Date(1512506556L));
	}

	@Test
	public void testLong() throws Exception {
		index(20171115050402L);

		_liferayIndexFixture.assertType("modified", "date");
	}

	@Test
	public void testLongMalformed() throws Exception {
		expectedException.expect(OpenSearchException.class);
		expectedException.expectMessage(
			"[mapper_parsing_exception] failed to parse field [modified] of " +
				"type [date]");

		index(1512506556L);
	}

	@Test
	public void testString() throws Exception {
		index("20171115050402");

		_liferayIndexFixture.assertType("modified", "date");
	}

	@Test
	public void testStringMalformed() throws Exception {
		expectedException.expect(OpenSearchException.class);
		expectedException.expectMessage(
			"failed to parse field [modified] of type [date] in document");

		index("2017-11-15 05:04:02");
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Rule
	public TestName testName = new TestName();

	protected void index(Object value) throws Exception {
		try {
			_liferayIndexFixture.index(
				Collections.singletonMap(Field.MODIFIED_DATE, value));
		}
		catch (OpenSearchException openSearchException1) {
			ErrorResponse originalErrorResponse =
				openSearchException1.response();

			OpenSearchException openSearchException2 = new OpenSearchException(
				ErrorResponse.of(
					errorResponse -> errorResponse.error(
						openSearchException1.error()
					).status(
						originalErrorResponse.status()
					)));

			openSearchException2.initCause(openSearchException1);

			throw openSearchException2;
		}
	}

	private LiferayIndexFixture _liferayIndexFixture;

}