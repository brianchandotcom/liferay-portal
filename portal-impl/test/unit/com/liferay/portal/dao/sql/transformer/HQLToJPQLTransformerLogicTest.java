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

package com.liferay.portal.dao.sql.transformer;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class HQLToJPQLTransformerLogicTest {

	@Test
	public void testReplaceCompositeId() {
		Function<String, String> function =
			HQLToJPQLTransformerLogic.getCompositeIdMarkerFunction();

		Assert.assertEquals(
			"select * from Foo where foo.a = 1",
			function.apply("select * from Foo where foo\\.id\\.a = 1"));
	}

	@Test
	public void testReplaceNotEquals() {
		Function<String, String> function =
			HQLToJPQLTransformerLogic.getNotEqualsFunction();

		Assert.assertEquals(
			"select * from Foo where foo <> 1",
			function.apply("select * from Foo where foo != 1"));
	}

	@Test
	public void testTransformWithMultipleQuestions() {
		String sql = "select * from Foo where";

		String expectedSql = new String(sql);

		for (int i = 1; i <= 100; i++) {
			String positionalParameter = " and foo" + i + " = ?";

			sql += positionalParameter;
			expectedSql += positionalParameter + i;
		}

		Function<String, String> positionalParameterFunction =
			HQLToJPQLTransformerLogic.getPositionalParameterFunction();

		Assert.assertEquals(
			expectedSql, positionalParameterFunction.apply(sql));
	}

	@Test
	public void testTransformWithOneQuestion() {
		String sql = "select * from Foo where foo = ?";

		Function<String, String> positionalParameterFunction =
			HQLToJPQLTransformerLogic.getPositionalParameterFunction();

		Assert.assertEquals(
			"select * from Foo where foo = ?1",
			positionalParameterFunction.apply(sql));
	}

	@Test
	public void testTransformWithZeroQuestions() {
		String sql = "select * from Foo";

		Function<String, String> positionalParameterFunction =
			HQLToJPQLTransformerLogic.getPositionalParameterFunction();

		Assert.assertEquals(sql, positionalParameterFunction.apply(sql));
	}

}