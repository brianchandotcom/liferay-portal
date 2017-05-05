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

import com.liferay.portal.dao.db.TestDB;
import com.liferay.portal.kernel.dao.db.DBType;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class DB2SQLTransformerLogicTest
	extends BaseSQLTransformerLogicTestCase {

	public DB2SQLTransformerLogicTest() {
		super(new TestDB(DBType.DB2, 1, 0));
	}

	@Test
	public void testAlterColumnType() {
		Assert.assertEquals(
			"ALTER TABLE T ALTER COLUMN C SET DATA TYPE VARCHAR(256)",
			sqlTransformer.transform("ALTER_COLUMN_TYPE T C VARCHAR(256)"));
	}

	@Override
	@Test
	public void testReplaceBitwiseCheckWithExtraWhitespace() {
		Assert.assertEquals(
			getBitwiseCheckTransformedSQL(),
			sqlTransformer.transform(getBitwiseCheckOriginalSQL()));
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

	@Override
	protected String getBooleanTransformedSQL() {
		return "select * from Foo where foo = FALSE and bar = TRUE";
	}

	protected String getCastClobTextTransformedSQL() {
		return "select CAST(foo AS VARCHAR(254)) from Foo";
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