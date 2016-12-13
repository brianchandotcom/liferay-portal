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

package com.liferay.portal.dao.orm.common.transformers;

import com.liferay.portal.dao.db.MySQLDB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Manuel de la Peña
 */
@PrepareForTest(DBManagerUtil.class)
@RunWith(PowerMockRunner.class)
public class MySQLTransformerTest implements TransformerTestCase {

	@Before
	public void setUp() {
		mockDB(_db);

		_transformer.setDB(_db);
	}

	@Override
	@Test
	public void testReplaceBitwiseCheck() {
		String sql = "select BITAND(foo,bar) from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals("select (foo & bar) from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceCastClobText() {
		String sql = "select CAST_CLOB_TEXT(foo) from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals("select foo from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceCastLong() {
		String sql = "select CAST_LONG(foo) from Foo";

		String transformedSql = _transformer.replaceCastLong(sql);

		Assert.assertEquals("select foo from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceCrossJoin() {
		String sql = "select * from Foo CROSS JOIN Bar";

		String transformedSql = _transformer.replaceCrossJoin(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Override
	@Test
	public void testReplaceInStr() {
		String sql = "select INSTR(foo) from Foo";

		String transformedSql = _transformer.replaceInStr(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Override
	@Test
	public void testReplaceIntegerDivision() {
		String sql = "select INTEGER_DIV(foo,bar) from Foo";

		String transformedSql = _transformer.replaceIntegerDivision(sql);

		Assert.assertEquals("select foo DIV bar from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceMod() {
		String sql = "select MOD(foo,bar) from Foo";

		String transformedSql = _transformer.replaceMod(sql);

		Assert.assertEquals("select foo % bar from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceNullDate() {
		String sql = "select [$NULL_DATE$] from Foo";

		String transformedSql = _transformer.replaceNullDate(sql);

		Assert.assertEquals("select NULL from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceReplace() {
		String sql = "select replace(foo) from Foo";

		String transformedSql = _transformer.replaceReplace(sql);

		Assert.assertEquals("select str_replace(foo) from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceSubst() {
		String sql = "select foo from Foo";

		String transformedSql = _transformer.replaceSubstr(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Test
	public void testTransform() {
		String sql = "select * from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Test
	public void testTransformLower() {
		String sql = "select lower(foo) from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals("select foo from Foo", transformedSql);
	}

	@Test
	public void testTransformLowerMultiple() {
		String sql = "select lower(foo), bar, lower(baaz) from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals("select foo, bar, baaz from Foo", transformedSql);
	}

	@Test
	public void testTransformLowerRecursive() {
		String sql = "select lower( lower(foo)) from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals("select  lower(foo) from Foo", transformedSql);
	}

	@Test
	public void testTransformLowerWithoutClosing() {
		String sql = "select lower(foo from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Test
	public void testTransformSupportsStringCaseSensitiveQuery() {
		_transformer = new MySQLTransformer(true);

		_transformer.setDB(_db);

		String sql = "select * from foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals("select * from foo", transformedSql);

		sql = "select lower(foo) from Foo";

		transformedSql = _transformer.transform(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	private static final boolean _SUPPORTS_STRING_CASE_SENSITIVE_QUERY = false;

	private final MySQLDB _db = new MySQLDB(5, 7);

	private MySQLTransformer _transformer = new MySQLTransformer(
		_SUPPORTS_STRING_CASE_SENSITIVE_QUERY);

}