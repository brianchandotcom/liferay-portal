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

import com.liferay.portal.dao.db.MySQLDB;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class MySQLSQLTransformerLogicTest
	extends BaseSQLTransformerLogicTestCase {

	public MySQLSQLTransformerLogicTest() {
		super(new MySQLDB(5, 7));
	}

	@Override
	@Test
	public void testReplaceModWithWhitespacesSurroundingCommas() {
		Assert.assertEquals(
			getModTransformedSQL(),
			sqlTransformer.transform(getModOriginalSQL()));
	}

	@Test
	public void testTransformLower() {
		String sql = "select lower(foo) from Foo";

		String transformedSql = sqlTransformer.transform(sql);

		Assert.assertEquals("select foo from Foo", transformedSql);
	}

	@Test
	public void testTransformLowerMultiple() {
		String sql = "select lower(foo), bar, lower(baaz) from Foo";

		String transformedSql = sqlTransformer.transform(sql);

		Assert.assertEquals("select foo, bar, baaz from Foo", transformedSql);
	}

	@Test
	public void testTransformLowerRecursive() {
		String sql = "select lower(lower(foo)) from Foo";

		String transformedSql = sqlTransformer.transform(sql);

		Assert.assertEquals("select lower(foo) from Foo", transformedSql);
	}

	@Test
	public void testTransformLowerWithoutClosing() {
		String sql = "select lower(foo from Foo";

		String transformedSql = sqlTransformer.transform(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Test
	public void testTransformSupportsStringCaseSensitiveQuery() {
		String sql = "select * from foo";

		MySQLDB mySQLDB = new MySQLDB(5, 7);

		mySQLDB.setSupportsStringCaseSensitiveQuery(true);

		SQLTransformer sqlTransformer = SQLTransformerFactory.getSQLTransformer(
			mySQLDB);

		String transformedSql = sqlTransformer.transform(sql);

		Assert.assertEquals("select * from foo", transformedSql);

		sql = "select lower(foo) from Foo";

		transformedSql = sqlTransformer.transform(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Override
	protected String getBitwiseCheckTransformedSQL() {
		return "select (foo & bar) from Foo";
	}

	@Override
	protected String getCastClobTextTransformedSQL() {
		return "select foo from Foo";
	}

	@Override
	protected String getCastLongOriginalSQL() {
		return "select CAST_LONG(foo) from Foo";
	}

	@Override
	protected String getCastLongTransformedSQL() {
		return "select foo from Foo";
	}

	@Override
	protected String getIntegerDivisionTransformedSQL() {
		return "select foo DIV bar from Foo";
	}

	@Override
	protected String getNullDateTransformedSQL() {
		return "select NULL from Foo";
	}

}