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

import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class DefaultSQLTransformerTest {

	@Test
	public void testTransformWithMultipleFunctions() {
		SQLTransformer sqlTransformer = new DefaultSQLTransformer(
			new Function[] {_toUpperCaseFunction, _trimFunction});

		String sql = sqlTransformer.transform(" select * from Table ");

		Assert.assertEquals("SELECT * FROM TABLE", sql);
	}

	@Test
	public void testTransformWithNoFunctions() {
		SQLTransformer sqlTransformer = new DefaultSQLTransformer(
			new Function[0]);

		String sql = "select * from Foo";

		Assert.assertEquals(sql, sqlTransformer.transform(sql));
	}

	@Test
	public void testTransformWithNullFunction() {
		SQLTransformer sqlTransformer = new DefaultSQLTransformer(null);

		String sql = "select * from Foo";

		Assert.assertEquals(sql, sqlTransformer.transform(sql));
	}

	@Test
	public void testTransformWithOneFunction() {
		SQLTransformer sqlTransformer = new DefaultSQLTransformer(
			new Function[] {_dummyFunction});

		Assert.assertNull(sqlTransformer.transform(null));
	}

	private final Function<String, String> _dummyFunction = (String sql) -> sql;
	private final Function<String, String> _toUpperCaseFunction =
		(String sql) -> StringUtil.toUpperCase(sql);
	private final Function<String, String> _trimFunction =
		(String sql) -> sql.trim();

}