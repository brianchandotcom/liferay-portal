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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class DB2SQLTransformerLogicTest
	extends BaseSQLTransformerLogicTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	public DB2SQLTransformerLogicTest() {
		super(new TestDB(DBType.DB2, 1, 0));
	}

	@Override
	public String getDropTableIfExistsTextTransformedSQL() {
		return StringBundler.concat(
			"BEGIN\n", "DECLARE CONTINUE HANDLER FOR SQLSTATE '42704'\n",
			"BEGIN END;\n", "EXECUTE IMMEDIATE 'DROP TABLE Foo';\n", "END");
	}

	@Override
	@Test
	public void testReplaceBitwiseCheckWithExtraWhitespace() {
		Assert.assertEquals(
			getBitwiseCheckTransformedSQL(),
			sqlTransformer.transform(getBitwiseCheckOriginalSQL()));
	}

	@Test
	public void testReplaceCastText() {
		Assert.assertEquals(
			"select CAST(foo AS VARCHAR(2000)) from Foo",
			sqlTransformer.transform(getCastTextOriginalSQL()));
	}

	@Test
	public void testReplaceConcat() {
		Assert.assertEquals(
			"select * from Foo where foo LIKE CAST(bar AS VARCHAR(2000)) " +
				"CONCAT COALESCE(CAST(? AS VARCHAR(2000)),'')",
			sqlTransformer.transform(
				"select * from Foo where foo LIKE CONCAT(CAST_TEXT(bar),?)"));
	}

	@Override
	@Test
	public void testReplaceModWithExtraWhitespace() {
		Assert.assertEquals(
			getModTransformedSQL(),
			sqlTransformer.transform(getModOriginalSQL()));
	}

	@Test
	public void testReplaceQuestionMark() {
		_testReplaceQuestionMark("select foo from Foo where foo LIKE ?");
		_testReplaceQuestionMark("select foo, ?, bar, ? from Foo");
		_testReplaceQuestionMark("select * from Foo where foo = ? And bar = ?");
		_testReplaceQuestionMark(
			"select * from Foo where case when foo = ? then ? else ? end");
		_testReplaceQuestionMark(
			"select bar, ?, case when foo = ? then ? else ? end as columnA " +
				"from Foo");

		Assert.assertEquals(
			"select * from Foo where foo = \" ?\"",
			sqlTransformer.transform("select * from Foo where foo = \" ?\""));
		Assert.assertEquals(
			"select * from Foo where foo = \' ?\'",
			sqlTransformer.transform("select * from Foo where foo = \' ?\'"));
	}

	@Override
	protected String getBooleanTransformedSQL() {
		return "select * from Foo where foo = FALSE and bar = TRUE";
	}

	@Override
	protected String getCastClobTextTransformedSQL() {
		return "select CAST(foo AS VARCHAR(2000)) from Foo";
	}

	@Override
	protected String getIntegerDivisionTransformedSQL() {
		return "select foo / bar from Foo";
	}

	@Override
	protected String getNullDateTransformedSQL() {
		return "select NULL from Foo";
	}

	private void _testReplaceQuestionMark(String sql) {
		Assert.assertEquals(
			StringUtil.replace(
				sql, CharPool.QUESTION,
				"COALESCE(CAST(? AS VARCHAR(2000)),'')"),
			sqlTransformer.transform(sql));
	}

}