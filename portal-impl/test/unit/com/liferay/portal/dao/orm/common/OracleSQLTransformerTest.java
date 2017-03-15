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

import com.liferay.portal.dao.db.TestDB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Manuel de la Peña
 */
@PrepareForTest(DBManagerUtil.class)
@RunWith(PowerMockRunner.class)
public class OracleSQLTransformerTest extends BaseSQLTransformerTestCase {

	@Test
	public void testReplaceCastText() {
		Assert.assertEquals(
			"select CAST(foo AS VARCHAR(4000)) from Foo",
			SQLTransformer.transform("select CAST_TEXT(foo) from Foo"));
	}

	@Test
	public void testReplaceEscape() {
		Assert.assertEquals(
			"select foo from Foo where foo LIKE ? ESCAPE '\\'",
			SQLTransformer.transform("select foo from Foo where foo LIKE ?"));
	}

	@Test
	public void testReplaceNotEqualsBlankString() {
		Assert.assertEquals(
			"select * from Foo where foo IS NOT NULL",
			SQLTransformer.transform("select * from Foo where foo != ''"));
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
		return "select TRUNC(foo /  bar) from Foo";
	}

	@Override
	protected String getNullDateTransformedSQL() {
		return "select NULL from Foo";
	}

	@Override
	protected void mockDB() {
		PowerMockito.mockStatic(DBManagerUtil.class);

		Mockito.when(DBManagerUtil.getDB()).thenReturn(
			new TestDB(DBType.ORACLE, 1, 0));
	}

}