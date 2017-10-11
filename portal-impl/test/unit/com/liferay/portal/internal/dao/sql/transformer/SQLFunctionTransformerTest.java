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

package com.liferay.portal.internal.dao.sql.transformer;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael Bowerman
 */
public class SQLFunctionTransformerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(SQLFunctionTransformer.class);
			}

		};

	@Test
	public void testNestedFunctionCalls() {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer("TEST(", "", " DELIMITER ", "");

		String transformedSQL = sqlFunctionTransformer.transform(
			"TEST(a, TEST(b, TEST(c), d))");

		Assert.assertEquals(
			"a DELIMITER  b DELIMITER  c DELIMITER  d", transformedSQL);
	}

	@Test
	public void testParenthesesInStrings() {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer("TEST(", "", " DELIMITER ", "");

		String transformedSQL = sqlFunctionTransformer.transform(
			"select TEST(')', a, b, 'c)d'), data from table");

		Assert.assertEquals(
			"select ')' DELIMITER  a DELIMITER  b DELIMITER  'c)d', data " +
				"from table",
			transformedSQL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPotentialInfiniteLoopThrowsException1() {
		new SQLFunctionTransformer("WORLD(", "", "HELLO WORLD()", "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPotentialInfiniteLoopThrowsException2() {
		new SQLFunctionTransformer("WORLD(", "", "", "HELLO WORLD()");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPotentialInfiniteLoopThrowsException3() {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer("TEST(", "", " DELIMITER ", "");

		sqlFunctionTransformer.transform("TEST('This string is not closed)");
	}

	@Test
	public void testSQLUntransformedWithNoFunctionCalls() {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer("TEST(", "", " DELIMITER ", "");

		String transformedSQL = sqlFunctionTransformer.transform(
			"TEST2(a, b, c)");

		Assert.assertEquals("TEST2(a, b, c)", transformedSQL);
	}

	@Test
	public void testUnclosedFunctionCall() {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer("TEST(", "", " DELIMITER ", "");

		String transformedSQL = sqlFunctionTransformer.transform(
			"TEST(a, b, c");

		Assert.assertEquals("a DELIMITER  b DELIMITER  c", transformedSQL);
	}

}