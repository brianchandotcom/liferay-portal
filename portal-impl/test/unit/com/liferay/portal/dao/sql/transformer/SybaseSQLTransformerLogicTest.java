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
public class SybaseSQLTransformerLogicTest
	extends BaseSQLTransformerLogicTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	public SybaseSQLTransformerLogicTest() {
		super(new TestSybaseDB(1, 0));
	}

	@Override
	public String getDropTableIfExistsTextTransformedSQL() {
		return StringBundler.concat(
			"IF EXISTS(select 1 from sysobjects where name = 'Foo' and type = ",
			"'U')\n", "BEGIN\n", "DROP TABLE Foo\n", "END");
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
				" NOOP", "NOOP", "NOOP"
			};
		}

	}

}