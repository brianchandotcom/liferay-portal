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
public class DefaultSQLTransformerTest {

	@Test
	public void testTransformNullSql() {
		SQLTransformer sqlTransformer = new DefaultSQLTransformer(
			new Function[] {_noTransformationFunction});

		Assert.assertNull(sqlTransformer.transform(null));
	}

	@Test
	public void testTransformWithNoFunctions() {
		SQLTransformer sqlTransformer = new DefaultSQLTransformer(
			new Function[0]);

		String sql = "select * from Foo";

		Assert.assertEquals(sql, sqlTransformer.transform(sql));
	}

	@Test
	public void testTransformWithNullFunctions() {
		SQLTransformer sqlTransformer = new DefaultSQLTransformer(null);

		String sql = "select * from Foo";

		Assert.assertEquals(sql, sqlTransformer.transform(sql));
	}

	private final Function<String, String> _noTransformationFunction =
		(String sql) -> sql;

}