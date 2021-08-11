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
		StringBundler sb = new StringBundler(5);

		sb.append("BEGIN\n");
		sb.append("DECLARE CONTINUE HANDLER FOR SQLSTATE '42704'\n");
		sb.append("BEGIN END;\n");
		sb.append("EXECUTE IMMEDIATE 'DROP TABLE Foo';\n");
		sb.append("END");

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
	public void testReplaceCaseWhenThen() {
		Assert.assertEquals(
			StringBundler.concat(
				"select * from Foo where case when foo = COALESCE(CAST(? AS ",
				"VARCHAR(32672)),'') then COALESCE(CAST(? AS VARCHAR(32672)),",
				"'') else COALESCE(CAST(? AS VARCHAR(32672)),'') end"),
			sqlTransformer.transform(
				"select * from Foo where case when foo = ? then ? else ? end"));

		Assert.assertEquals(
			StringBundler.concat(
				"select bar, COALESCE(CAST(? AS VARCHAR(32672)),''), case ",
				"when foo = COALESCE(CAST(? AS VARCHAR(32672)),'') then ",
				"COALESCE(CAST(? AS VARCHAR(32672)),'') else COALESCE(CAST(? ",
				"AS VARCHAR(32672)),'') end as columnA from Foo"),
			sqlTransformer.transform(
				"select bar, ?, case when foo = ? then ? else ? end as " +
					"columnA from Foo"));
	}

	@Test
	public void testReplaceLike() {
		Assert.assertEquals(
			"select foo from Foo where foo LIKE COALESCE(" +
				"CAST(? AS VARCHAR(32672)),'')",
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
	public void testReplaceSelect() {
		Assert.assertEquals(
			"select foo, COALESCE(CAST(? AS VARCHAR(32672)),''), bar, " +
				"COALESCE(CAST(? AS VARCHAR(32672)),'') from Foo",
			sqlTransformer.transform("select foo, ?, bar, ? from Foo"));
	}

	@Override
	protected String getBooleanTransformedSQL() {
		return "select * from Foo where foo = FALSE and bar = TRUE";
	}

	@Override
	protected String getCastClobTextTransformedSQL() {
		return "select CAST(foo AS VARCHAR(32672)) from Foo";
	}

	@Override
	protected String getIntegerDivisionTransformedSQL() {
		return "select foo / bar from Foo";
	}

	@Override
	protected String getNullDateTransformedSQL() {
		return "select NULL from Foo";
	}

}