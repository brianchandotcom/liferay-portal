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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class PostgreSQLTransformerTest implements TransformerTestCase {

	@Override
	@Test
	public void testPostTransform() {
		String sql = "select * from Foo";

		String transformedSql = _transformer.postTransform(false, sql);

		Assert.assertEquals(sql, transformedSql);

		transformedSql = _transformer.postTransform(true, sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Test
	public void testPostTransformReplaceNegativeComparison() {
		String sql = "select * from Foo where foo != -1";

		String transformedSql = _transformer.postTransform(false, sql);

		Assert.assertEquals(
			"select * from Foo where foo != ( -1)", transformedSql);

		transformedSql = _transformer.postTransform(true, sql);

		Assert.assertEquals(
			"select * from Foo where foo != ( -1)", transformedSql);
	}

	@Override
	@Test
	public void testReplaceBitwiseCheck() {
		String sql = "select BITAND(foo,bar) from Foo";

		String transformedSql = _transformer.replaceBitwiseCheck(sql);

		Assert.assertEquals("select (foo & bar) from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceCastClobText() {
		String sql = "select CAST_CLOB_TEXT(foo) from Foo";

		String transformedSql = _transformer.replaceCastClobText(sql);

		Assert.assertEquals(
			"select CAST(foo AS TEXT) from Foo", transformedSql);
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

		Assert.assertEquals("select foo / bar from Foo", transformedSql);
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

		Assert.assertEquals(
			"select CAST(NULL AS TIMESTAMP) from Foo", transformedSql);
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

	private final PostgreSQLTransformer _transformer =
		new PostgreSQLTransformer();

}