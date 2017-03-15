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
public class SQLServerSQLTransformerTest extends BaseSQLTransformerTestCase {

	@Test
	public void testReplaceCastText() {
		Assert.assertEquals(
			"select CAST(foo AS NVARCHAR(MAX)) from Foo",
			SQLTransformer.transform("select CAST_TEXT(foo) from Foo"));
	}

	@Override
	protected String getBitwiseCheckTransformedSQL() {
		return "select (foo &  bar) from Foo";
	}

	@Override
	protected String getBooleanTransformedSQL() {
		return "select * from Foo where foo = 0 and bar = 1";
	}

	@Override
	protected String getCastClobTextTransformedSQL() {
		return "select CAST(foo AS NVARCHAR(MAX)) from Foo";
	}

	@Override
	protected String getIntegerDivisionTransformedSQL() {
		return "select foo /  bar from Foo";
	}

	@Override
	protected String getModTransformedSQL() {
		return "select foo %  bar from Foo";
	}

	@Override
	protected String getNullDateTransformedSQL() {
		return "select NULL from Foo";
	}

	@Override
	protected String getSubstrOriginalSQL() {
		return "select SUBSTR(foo, 1, 1) from Foo";
	}

	@Override
	protected String getSubstrTransformedSQL() {
		return "select SUBSTRING(foo,  1,  1) from Foo";
	}

	@Override
	protected void mockDB() {
		PowerMockito.mockStatic(DBManagerUtil.class);

		Mockito.when(DBManagerUtil.getDB()).thenReturn(
			new TestSQLServerDB(1, 0));
	}

	private static final class TestSQLServerDB extends TestDB {

		public TestSQLServerDB(int majorVersion, int minorVersion) {
			super(DBType.SQLSERVER, majorVersion, minorVersion);
		}

		@Override
		protected String[] getTemplate() {
			return new String[] {
				"NOOP", "1", "0", "NOOP", "NOOP", " NOOP", " NOOP", " NOOP",
				" NOOP", " NOOP", " NOOP", " NOOP", " NOOP", " NOOP", " NOOP",
				" NOOP", "NOOP"
			};
		}

	}

}