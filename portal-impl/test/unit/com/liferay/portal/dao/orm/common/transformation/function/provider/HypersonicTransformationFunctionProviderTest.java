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

package com.liferay.portal.dao.orm.common.transformation.function.provider;

import com.liferay.portal.dao.db.HypersonicDB;
import com.liferay.portal.dao.orm.common.PortalSQLTransformer;
import com.liferay.portal.dao.orm.common.Transformer;
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
public class HypersonicTransformationFunctionProviderTest
	implements TransformationFunctionProviderTestCase {

	@Before
	public void setUp() {
		HypersonicDB db = new HypersonicDB(1, 0);

		mockDB(db);

		_transformer = PortalSQLTransformer.buildSQLTransformer(
			db, new HypersonicTransformationFunctionProvider());
	}

	@Override
	@Test
	public void testReplaceBitwiseCheck() {
		String sql = "select BITAND(foo,bar) from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Override
	@Test
	public void testReplaceCastClobText() {
		String sql = "select CAST_CLOB_TEXT(foo) from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals(
			"select CONVERT(foo, SQL_VARCHAR) from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceCastLong() {
		String sql = "select CAST_LONG(foo) from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals(
			"select CONVERT(foo, SQL_BIGINT) from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceCrossJoin() {
		String sql = "select * from Foo CROSS JOIN Bar";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Override
	@Test
	public void testReplaceInStr() {
		String sql = "select INSTR(foo) from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Override
	@Test
	public void testReplaceIntegerDivision() {
		String sql = "select INTEGER_DIV(foo,bar) from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals("select foo / bar from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceMod() {
		String sql = "select MOD(foo,bar) from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals("select MOD(foo,bar) from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceNullDate() {
		String sql = "select [$NULL_DATE$] from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals("select NULL from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceReplace() {
		String sql = "select replace(foo) from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals("select replace(foo) from Foo", transformedSql);
	}

	@Override
	@Test
	public void testReplaceSubst() {
		String sql = "select foo from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	@Test
	public void testTransform() {
		String sql = "select * from Foo";

		String transformedSql = _transformer.transform(sql);

		Assert.assertEquals(sql, transformedSql);
	}

	private Transformer _transformer;

}