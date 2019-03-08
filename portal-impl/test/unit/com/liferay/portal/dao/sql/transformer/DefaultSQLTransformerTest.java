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

import java.util.Arrays;
import java.util.Collections;
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
			Arrays.asList(_toUpperCaseFunction, _trimFunction));

		String sql = sqlTransformer.transform(" select * from Table ");

		Assert.assertEquals("SELECT * FROM TABLE", sql);
	}

	@Test
	public void testTransformWithNoFunctions() {
		String sql = "select * from Foo";

		SQLTransformer sqlTransformer = new DefaultSQLTransformer(
			Collections.emptyList());

		Assert.assertEquals(sql, sqlTransformer.transform(sql));
	}

	@Test
	public void testTransformWithNullFunction() {
		String sql = "select * from Foo";

		SQLTransformer sqlTransformer = new DefaultSQLTransformer(null);

		Assert.assertEquals(sql, sqlTransformer.transform(sql));
	}

	@Test
	public void testTransformWithOneFunction() {
		SQLTransformer sqlTransformer = new DefaultSQLTransformer(
			Arrays.asList(Function.identity()));

		Assert.assertNull(sqlTransformer.transform(null));
	}

	private final Function<String, String> _toUpperCaseFunction =
		sql -> StringUtil.toUpperCase(sql);
	private final Function<String, String> _trimFunction = sql -> sql.trim();

}