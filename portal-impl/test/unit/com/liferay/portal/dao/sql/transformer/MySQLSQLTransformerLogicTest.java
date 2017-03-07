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
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Manuel de la Peña
 */
@PrepareForTest({DB.class, DBManagerUtil.class})
@RunWith(PowerMockRunner.class)
public class MySQLSQLTransformerLogicTest
	extends BaseSQLTransformerLogicTestCase {

	@Before
	public void setUp() {
		_db = _mockDB(false);

		setDB(_db);
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
		String sql = "select lower( lower(foo)) from Foo";

		String transformedSql = sqlTransformer.transform(sql);

		Assert.assertEquals("select  lower(foo) from Foo", transformedSql);
	}

	@Test
	public void testTransformLowerWithoutClosing() {
		String sql = "select lower(foo from Foo";

		String transformedSql = sqlTransformer.transform(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Test
	public void testTransformSupportsStringCaseSensitiveQuery() {
		_db = _mockDB(true);

		setDB(_db);

		String sql = "select * from foo";

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

	@Override
	protected SQLTransformerLogic getSQLTransformerLogic(DB db) {
		return new MySQLSQLTransformerLogic(db);
	}

	private DB _mockDB(boolean supportsStringCaseSensitiveQuery) {
		DB spy = Mockito.spy(new MySQLDB(5, 7));

		PowerMockito.when(
			spy.isSupportsStringCaseSensitiveQuery()
		).thenReturn(
			supportsStringCaseSensitiveQuery
		);

		return spy;
	}

	private DB _db;

}