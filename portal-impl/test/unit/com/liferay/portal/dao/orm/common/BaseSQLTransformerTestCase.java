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

package com.liferay.portal.dao.orm.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public abstract class BaseSQLTransformerTestCase {

	@Before
	public void setUp() {
		mockDB();
	}

	@Test
	public void testReplaceBitwiseCheck() {
		Assert.assertEquals(
			getBitwiseCheckTransformedSQL(),
			SQLTransformer.transform(getBitwiseCheckOriginalSQL()));
	}

	@Test
	public void testReplaceBoolean() {
		Assert.assertEquals(
			getBooleanTransformedSQL(),
			SQLTransformer.transform(getBooleanOriginalSQL()));
	}

	@Test
	public void testReplaceCastClobText() {
		Assert.assertEquals(
			getCastClobTextTransformedSQL(),
			SQLTransformer.transform(getCastClobTextOriginalSQL()));
	}

	@Test
	public void testReplaceCastLong() {
		Assert.assertEquals(
			getCastLongTransformedSQL(),
			SQLTransformer.transform(getCastLongOriginalSQL()));
	}

	@Test
	public void testReplaceCrossJoin() {
		Assert.assertEquals(
			getCrossJoinTransformedSQL(),
			SQLTransformer.transform(getCrossJoinOriginalSQL()));
	}

	@Test
	public void testReplaceInstr() {
		Assert.assertEquals(
			getInstrTransformedSQL(),
			SQLTransformer.transform(getInstrOriginalSQL()));
	}

	@Test
	public void testReplaceIntegerDivision() {
		Assert.assertEquals(
			getIntegerDivisionTransformedSQL(),
			SQLTransformer.transform(getIntegerDivisionOriginalSQL()));
	}

	@Test
	public void testReplaceMod() {
		Assert.assertEquals(
			getModTransformedSQL(),
			SQLTransformer.transform(getModOriginalSQL()));
	}

	@Test
	public void testReplaceNullDate() {
		Assert.assertEquals(
			getNullDateTransformedSQL(),
			SQLTransformer.transform(getNullDateOriginalSQL()));
	}

	@Test
	public void testReplaceReplace() {
		Assert.assertEquals(
			getReplaceTransformedSQL(),
			SQLTransformer.transform(getReplaceOriginalSQL()));
	}

	@Test
	public void testReplaceSubstr() {
		Assert.assertEquals(
			getSubstrTransformedSQL(),
			SQLTransformer.transform(getSubstrOriginalSQL()));
	}

	@Test
	public void testTransform() {
		String sql = "select * from Foo";

		Assert.assertEquals(sql, SQLTransformer.transform(sql));
	}

	@Test
	public void testTransformFromHqlToJpqlWithCompositeMarker() {
		String sql = "select * from Foo where foo\\.id\\.bar = 1";

		Assert.assertEquals(
			"select * from Foo where foo.bar = 1",
			SQLTransformer.transformFromHqlToJpql(sql));
	}

	@Test
	public void testTransformFromHqlToJpqlWithNotEquals() {
		String sql = "select * from Foo where foo != 1";

		Assert.assertEquals(
			"select * from Foo where foo <> 1",
			SQLTransformer.transformFromHqlToJpql(sql));
	}

	@Test
	public void testTransformFromJpqlToHql() {
		Assert.assertEquals(
			"SELECT COUNT(*) FROM Foo foo",
			SQLTransformer.transformFromJpqlToHql(
				"SELECT COUNT(foo) FROM Foo foo"));
	}

	@Test
	public void testTransformFromJpqlToHqlNotMatching() {
		String sql = "SELECT * FROM Foo where foo != 1";

		Assert.assertEquals(sql, SQLTransformer.transformFromJpqlToHql(sql));
	}

	@Test
	public void testTransformFromJpqlToHqlWithAliasDifferentThanFieldCount() {
		Assert.assertEquals(
			"SELECT COUNT(bar) FROM Foo foo",
			SQLTransformer.transformFromJpqlToHql(
				"SELECT COUNT(bar) FROM Foo foo"));
	}

	@Test
	public void testTransformPositionalParametersWithMultipleQuestions() {
		String sql = "select * from Foo where";

		String expectedSql = new String(sql);

		for (int i = 1; i <= 100; i++) {
			String positionalParameter = " and foo" + i + " = ?";

			sql += positionalParameter;
			expectedSql += positionalParameter + i;
		}

		Assert.assertEquals(
			expectedSql, SQLTransformer.transformFromHqlToJpql(sql));
	}

	@Test
	public void testTransformPositionalParametersWithOneQuestion() {
		String sql = "select * from Foo where foo = ?";

		Assert.assertEquals(
			"select * from Foo where foo = ?1",
			SQLTransformer.transformFromHqlToJpql(sql));
	}

	@Test
	public void testTransformWithZeroQuestions() {
		String sql = "select * from Foo";

		Assert.assertEquals(sql, SQLTransformer.transformFromHqlToJpql(sql));
	}

	protected String getBitwiseCheckOriginalSQL() {
		return "select BITAND(foo, bar) from Foo";
	}

	protected String getBitwiseCheckTransformedSQL() {
		return getBitwiseCheckOriginalSQL();
	}

	protected String getBooleanOriginalSQL() {
		return "select * from Foo where foo = [$FALSE$] and bar = [$TRUE$]";
	}

	protected String getBooleanTransformedSQL() {
		return "select * from Foo where foo = false and bar = true";
	}

	protected String getCastClobTextOriginalSQL() {
		return "select CAST_CLOB_TEXT(foo) from Foo";
	}

	protected String getCastClobTextTransformedSQL() {
		return getCastClobTextOriginalSQL();
	}

	protected String getCastLongOriginalSQL() {
		return "select CONVERT(foo, SQL_BIGINT) from Foo";
	}

	protected String getCastLongTransformedSQL() {
		return getCastLongOriginalSQL();
	}

	protected String getCrossJoinOriginalSQL() {
		return "select * from Foo CROSS JOIN Bar";
	}

	protected String getCrossJoinTransformedSQL() {
		return getCrossJoinOriginalSQL();
	}

	protected String getInstrOriginalSQL() {
		return "select INSTR(foo) from Foo";
	}

	protected String getInstrTransformedSQL() {
		return getInstrOriginalSQL();
	}

	protected String getIntegerDivisionOriginalSQL() {
		return "select INTEGER_DIV(foo, bar) from Foo";
	}

	protected String getIntegerDivisionTransformedSQL() {
		return getIntegerDivisionOriginalSQL();
	}

	protected String getModOriginalSQL() {
		return "select MOD(foo, bar) from Foo";
	}

	protected String getModTransformedSQL() {
		return getModOriginalSQL();
	}

	protected String getNullDateOriginalSQL() {
		return "select [$NULL_DATE$] from Foo";
	}

	protected String getNullDateTransformedSQL() {
		return getNullDateOriginalSQL();
	}

	protected String getReplaceOriginalSQL() {
		return "select replace(foo) from Foo";
	}

	protected String getReplaceTransformedSQL() {
		return getReplaceOriginalSQL();
	}

	protected String getSubstrOriginalSQL() {
		return "select foo from Foo";
	}

	protected String getSubstrTransformedSQL() {
		return getSubstrOriginalSQL();
	}

	protected abstract void mockDB();

}