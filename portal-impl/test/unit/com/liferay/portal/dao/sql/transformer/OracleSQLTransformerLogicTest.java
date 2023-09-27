/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return StringBundler.concat(
			"BEGIN\n", "EXECUTE IMMEDIATE 'DROP TABLE Foo';\n", "EXCEPTION\n",
			"WHEN OTHERS THEN\n", "IF SQLCODE != -942 THEN\n", "RAISE;\n",
			"END IF;\n", "END;\n", "/");
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