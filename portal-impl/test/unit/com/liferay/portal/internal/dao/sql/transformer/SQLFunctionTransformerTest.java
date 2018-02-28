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
import com.liferay.portal.kernel.util.StringPool;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael Bowerman
 */
public class SQLFunctionTransformerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testCommasInStrings() {
		String sql = "TEST('a, b', ', c, d')";

		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer("TEST(", "", " DELIMITER ", "");

		Assert.assertEquals(
			"'a, b' DELIMITER  ', c, d'",
			sqlFunctionTransformer.transform(sql));
	}

	@Test
	public void testEscapedQuotesInStrings() {
		String sql = "TEST('\\'ab', 'c\\'d', 'ef\\'', 'gh\\\\')";

		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer("TEST(", "", " DELIMITER ", "");

		Assert.assertEquals(
			"'\\'ab' DELIMITER  'c\\'d' DELIMITER  'ef\\'' DELIMITER  'gh\\\\'",
			sqlFunctionTransformer.transform(sql));
	}

	@Test
	public void testFunctionPrefixMustEndInOpenParenthesis() {
		try {
			new SQLFunctionTransformer("WORLD", "", " DELIMITER ", "");

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("WORLD", iae.getMessage());
		}
	}

	@Test
	public void testIdentityTransformation() {
		String sql = "TEST(a, TEST(b, TEST(c), d))";

		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer("TEST(", "TEST(", ",", ")");

		Assert.assertEquals(sql, sqlFunctionTransformer.transform(sql));
	}

	@Test
	public void testMixedFunctionCalls() {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer("TEST2(", "function{", "-", "}");

		Assert.assertEquals(
			"TEST1(a, function{b- TEST1(c)- d}), function{e}",
			sqlFunctionTransformer.transform(
				"TEST1(a, TEST2(b, TEST1(c), d)), TEST2(e)"));
	}

	@Test
	public void testNestedFunctionCalls() {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer("TEST(", "", " DELIMITER ", "");

		String transformedSQL = sqlFunctionTransformer.transform(
			"TEST(a, TEST(TEST(b), c), d)");

		Assert.assertEquals(
			"a DELIMITER  b DELIMITER  c DELIMITER  d", transformedSQL);
	}

	@Test
	public void testNestedPrefixAndSuffix() {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer("TEST(", "function{", "-", "}");

		Assert.assertEquals(
			"function{a- function{b- function{c}- d}}",
			sqlFunctionTransformer.transform("TEST(a, TEST(b, TEST(c), d))"));
	}

	@Test
	public void testNonUpperCaseFunctionPrefixThrowsException() {
		try {
			new SQLFunctionTransformer("World(", "", "", "Hello World()");

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("World(", iae.getMessage());
		}
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

	@Test
	public void testPotentialInfiniteLoopThrowsException1() {
		try {
			new SQLFunctionTransformer("WORLD(", "", "HELLO WORLD()", "");

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("HELLO WORLD()", iae.getMessage());
		}
	}

	@Test
	public void testPotentialInfiniteLoopThrowsException2() {
		try {
			new SQLFunctionTransformer("WORLD(", "", "", "HELLO WORLD()");

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("HELLO WORLD()", iae.getMessage());
		}
	}

	@Test
	public void testPotentialInfiniteLoopThrowsException3() {
		try {
			SQLFunctionTransformer sqlFunctionTransformer =
				new SQLFunctionTransformer("TEST(", "", " DELIMITER ", "");

			sqlFunctionTransformer.transform(
				"TEST('This string is not closed)");

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Unclosed string literal in: TEST('This string is not closed)",
				iae.getMessage());
		}
	}

	@Test
	public void testRemoveFunctionTransformation() {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer(
				"TEST(", StringPool.BLANK, StringPool.BLANK, StringPool.BLANK);

		Assert.assertEquals(
			"a b c d",
			sqlFunctionTransformer.transform("TEST(a, TEST(b, TEST(c), d))"));
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
		try {
			SQLFunctionTransformer sqlFunctionTransformer =
				new SQLFunctionTransformer("TEST(", "", " DELIMITER ", "");

			sqlFunctionTransformer.transform("TEST(a, b, c");

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Unclosed function in: TEST(a, b, c", iae.getMessage());
		}
	}

}