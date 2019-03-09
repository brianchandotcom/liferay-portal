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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class HQLToJPQLTransformerLogicTest {

	@Test
	public void testReplaceCompositeId() {
		Assert.assertEquals(
			"select * from Foo where foo.a = 1",
			HQLToJPQLTransformerLogic.replaceCompositeIdMarker(
				"select * from Foo where foo\\.id\\.a = 1"));
	}

	@Test
	public void testReplaceNotEquals() {
		Assert.assertEquals(
			"select * from Foo where foo <> 1",
			HQLToJPQLTransformerLogic.replaceNotEquals(
				"select * from Foo where foo != 1"));
	}

	@Test
	public void testTransformWithMultipleQuestions() {
		String originalSQL = "select * from Foo where";

		String transformedSQL = new String(originalSQL);

		for (int i = 1; i <= 100; i++) {
			String positionalParameter = " and foo" + i + " = ?";

			originalSQL += positionalParameter;
			transformedSQL += positionalParameter + i;
		}

		Assert.assertEquals(
			transformedSQL,
			HQLToJPQLTransformerLogic.replacePositionalParameter(originalSQL));
	}

	@Test
	public void testTransformWithNoQuestions() {
		String sql = "select * from Foo";

		Assert.assertEquals(
			sql, HQLToJPQLTransformerLogic.replacePositionalParameter(sql));
	}

	@Test
	public void testTransformWithOneQuestion() {
		String sql = "select * from Foo where foo = ?";

		Assert.assertEquals(
			"select * from Foo where foo = ?1",
			HQLToJPQLTransformerLogic.replacePositionalParameter(sql));
	}

}