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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class OracleSQLTransformerLogicTest
	extends BaseSQLTransformerLogicTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	public OracleSQLTransformerLogicTest() {
		super(new TestDB(DBType.ORACLE, 1, 0));
	}

	@Override
	public String getDropTableIfExistsTextTransformedSQL() {
		StringBundler sb = new StringBundler(9);

		sb.append("BEGIN\n");
		sb.append("EXECUTE IMMEDIATE 'DROP TABLE Foo';\n");
		sb.append("EXCEPTION\n");
		sb.append("WHEN OTHERS THEN\n");
		sb.append("IF SQLCODE != -942 THEN\n");
		sb.append("RAISE;\n");
		sb.append("END IF;\n");
		sb.append("END;\n");
		sb.append("/");

		return sb.toString();
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
			"select CAST(foo AS VARCHAR(4000)) from Foo",
			sqlTransformer.transform(getCastTextOriginalSQL()));
	}

	@Test
	public void testReplaceEscape() {
		Assert.assertEquals(
			"select foo from Foo where foo LIKE ? ESCAPE '\\'",
			sqlTransformer.transform("select foo from Foo where foo LIKE ?"));
	}

	@Override
	@Test
	public void testReplaceModWithExtraWhitespace() {
		Assert.assertEquals(
			getModTransformedSQL(),
			sqlTransformer.transform(getModOriginalSQL()));
	}

	@Test
	public void testReplaceNotEqualsBlankString() {
		Assert.assertEquals(
			"select * from Foo where foo IS NOT NULL",
			sqlTransformer.transform("select * from Foo where foo != ''"));
	}

	@Override
	protected String getBooleanTransformedSQL() {
		return "select * from Foo where foo = FALSE and bar = TRUE";
	}

	@Override
	protected String getCastClobTextTransformedSQL() {
		return "select DBMS_LOB.SUBSTR(foo, 4000, 1) from Foo";
	}

	@Override
	protected String getIntegerDivisionTransformedSQL() {
		return "select TRUNC(foo / bar) from Foo";
	}

	@Override
	protected String getNullDateTransformedSQL() {
		return "select NULL from Foo";
	}

}