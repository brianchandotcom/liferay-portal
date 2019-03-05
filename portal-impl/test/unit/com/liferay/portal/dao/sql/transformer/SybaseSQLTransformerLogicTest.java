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
public class SybaseSQLTransformerLogicTest
	extends BaseSQLTransformerLogicTestCase {

	public SybaseSQLTransformerLogicTest() {
		super(new TestSybaseDB(1, 0));
	}

	@Override
	public String getDropTableIfExistsTextTransformedSQL() {
		return "DROP TABLE IF EXISTS Foo";
	}

	@Test
	public void testReplaceCastText() {
		Assert.assertEquals(
			"select CAST(foo AS NVARCHAR(5461)) from Foo",
			sqlTransformer.transform(getCastTextOriginalSQL()));
	}

	@Override
	protected String getBitwiseCheckTransformedSQL() {
		return "select (foo & bar) from Foo";
	}

	@Override
	protected String getBooleanTransformedSQL() {
		return "select * from Foo where foo = 0 and bar = 1";
	}

	@Override
	protected String getCastClobTextTransformedSQL() {
		return "select CAST(foo AS NVARCHAR(5461)) from Foo";
	}

	@Override
	protected String getCrossJoinTransformedSQL() {
		return "select * from Foo , Bar";
	}

	@Override
	protected String getIntegerDivisionTransformedSQL() {
		return "select foo / bar from Foo";
	}

	@Override
	protected String getModTransformedSQL() {
		return "select foo % bar from Foo";
	}

	@Override
	protected String getNullDateTransformedSQL() {
		return "select NULL from Foo";
	}

	@Override
	protected String getReplaceTransformedSQL() {
		return "select str_replace(foo) from Foo";
	}

	@Override
	protected String getSubstrOriginalSQL() {
		return "select SUBSTR(foo, 1, 1) from Foo";
	}

	@Override
	protected String getSubstrTransformedSQL() {
		return "select SUBSTRING(foo, 1, 1) from Foo";
	}

	private static final class TestSybaseDB extends TestDB {

		public TestSybaseDB(int majorVersion, int minorVersion) {
			super(DBType.SYBASE, majorVersion, minorVersion);
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