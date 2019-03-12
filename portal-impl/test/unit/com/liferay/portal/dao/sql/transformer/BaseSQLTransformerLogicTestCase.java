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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.dao.db.DBManagerImpl;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManager;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 * @author Brian Wing Shun Chan
 */
public abstract class BaseSQLTransformerLogicTestCase {

	public BaseSQLTransformerLogicTestCase(DB db) {
		DBManager dbManager = new DBManagerImpl();

		DBManagerUtil.setDBManager(dbManager);

		dbManager.setDB(db);

		sqlTransformerFunction = SQLTransformerFunctionFactory.create();
	}

	@Test
	public void testReplaceBitwiseCheck() {
		Assert.assertEquals(
			getBitwiseCheckTransformedSQL(),
			sqlTransformerFunction.apply(getBitwiseCheckOriginalSQL()));
	}

	@Test
	public void testReplaceBitwiseCheckWithExtraWhitespace() {
		Assert.assertEquals(
			getBitwiseCheckTransformedSQL(),
			sqlTransformerFunction.apply(
				_addExtraWhitespaceFunction.apply(
					getBitwiseCheckOriginalSQL())));
	}

	@Test
	public void testReplaceBoolean() {
		Assert.assertEquals(
			getBooleanTransformedSQL(),
			sqlTransformerFunction.apply(getBooleanOriginalSQL()));
	}

	@Test
	public void testReplaceCastClobText() {
		Assert.assertEquals(
			getCastClobTextTransformedSQL(),
			sqlTransformerFunction.apply(getCastClobTextOriginalSQL()));
	}

	@Test
	public void testReplaceCastLong() {
		Assert.assertEquals(
			getCastLongTransformedSQL(),
			sqlTransformerFunction.apply(getCastLongOriginalSQL()));
	}

	@Test
	public void testReplaceCrossJoin() {
		Assert.assertEquals(
			getCrossJoinTransformedSQL(),
			sqlTransformerFunction.apply(getCrossJoinOriginalSQL()));
	}

	@Test
	public void testReplaceDropTableIfExistsText() {
		Assert.assertEquals(
			getDropTableIfExistsTextTransformedSQL(),
			sqlTransformerFunction.apply(
				getDropTableIfExistsTextOriginalSQL()));
	}

	@Test
	public void testReplaceInstr() {
		Assert.assertEquals(
			getInstrTransformedSQL(),
			sqlTransformerFunction.apply(getInstrOriginalSQL()));
	}

	@Test
	public void testReplaceInstrWithExtraWhitespace() {
		Assert.assertEquals(
			getInstrTransformedSQL(),
			sqlTransformerFunction.apply(
				_addExtraWhitespaceFunction.apply(getInstrOriginalSQL())));
	}

	@Test
	public void testReplaceIntegerDivision() {
		Assert.assertEquals(
			getIntegerDivisionTransformedSQL(),
			sqlTransformerFunction.apply(getIntegerDivisionOriginalSQL()));
	}

	@Test
	public void testReplaceIntegerDivisionWithExtraWhitespace() {
		Assert.assertEquals(
			getIntegerDivisionTransformedSQL(),
			sqlTransformerFunction.apply(
				_addExtraWhitespaceFunction.apply(
					getIntegerDivisionOriginalSQL())));
	}

	@Test
	public void testReplaceMod() {
		Assert.assertEquals(
			getModTransformedSQL(),
			sqlTransformerFunction.apply(getModOriginalSQL()));
	}

	@Test
	public void testReplaceModWithExtraWhitespace() {
		Assert.assertEquals(
			getModTransformedSQL(),
			sqlTransformerFunction.apply(
				_addExtraWhitespaceFunction.apply(getModOriginalSQL())));
	}

	@Test
	public void testReplaceNullDate() {
		Assert.assertEquals(
			getNullDateTransformedSQL(),
			sqlTransformerFunction.apply(getNullDateOriginalSQL()));
	}

	@Test
	public void testReplaceReplace() {
		Assert.assertEquals(
			getReplaceTransformedSQL(),
			sqlTransformerFunction.apply(getReplaceOriginalSQL()));
	}

	@Test
	public void testReplaceSubstr() {
		Assert.assertEquals(
			getSubstrTransformedSQL(),
			sqlTransformerFunction.apply(getSubstrOriginalSQL()));
	}

	@Test
	public void testReplaceSubstrWithExtraWhitespace() {
		Assert.assertEquals(
			getSubstrTransformedSQL(),
			sqlTransformerFunction.apply(
				_addExtraWhitespaceFunction.apply(getSubstrOriginalSQL())));
	}

	@Test
	public void testTransform() {
		String sql = "select * from Foo";

		Assert.assertEquals(sql, sqlTransformerFunction.apply(sql));
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

	protected String getCastTextOriginalSQL() {
		return "select CAST_TEXT(foo) from Foo";
	}

	protected String getCrossJoinOriginalSQL() {
		return "select * from Foo CROSS JOIN Bar";
	}

	protected String getCrossJoinTransformedSQL() {
		return getCrossJoinOriginalSQL();
	}

	protected String getDropTableIfExistsTextOriginalSQL() {
		return "DROP_TABLE_IF_EXISTS(Foo)";
	}

	protected String getDropTableIfExistsTextTransformedSQL() {
		return getDropTableIfExistsTextOriginalSQL();
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

	protected Function<String, String> sqlTransformerFunction;

	private final Function<String, String> _addExtraWhitespaceFunction =
		sql -> StringUtil.replace(sql, CharPool.COMMA, "   ,   ");

}