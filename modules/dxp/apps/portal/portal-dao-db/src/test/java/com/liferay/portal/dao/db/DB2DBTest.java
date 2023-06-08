/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.test.BaseDBTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Miguel Pastor
 * @author Alberto Chaparro
 */
public class DB2DBTest extends BaseDBTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetVarcharDefaultValue() {
		DB db = getDB();

		Assert.assertEquals(db.getDefaultValue("'test'"), "test");
	}

	@Test
	public void testGetLongDefaultValue() {
		DB db = getDB();

		Assert.assertEquals(db.getDefaultValue("10"), "10");
	}

	@Test
	public void testRewordAlterColumnType() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder alter column userName set data type " +
				"varchar(75);\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75);"));
	}

	@Test
	public void testRewordAlterColumnTypeNoSemicolon() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder alter column userName set data type " +
				"varchar(75);\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75)"));
	}

	@Test
	public void testRewordAlterColumnTypeNotNull() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder alter column userName set data type " +
				"varchar(75);\n",
			buildSQL(
				"alter_column_type DLFolder userName VARCHAR(75) not null;"));
		Assert.assertEquals(
			"alter table DLFolder alter column userName set not null;",
			_nullableAlter);
	}

	@Test
	public void testRewordAlterColumnTypeNull() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder alter column userName set data type " +
				"varchar(75);\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75) null;"));
		Assert.assertEquals(
			"alter table DLFolder alter column userName drop not null;",
			_nullableAlter);
	}

	@Test
	public void testRewordRenameTable() throws Exception {
		Assert.assertEquals(
			"rename table a to b;\n", buildSQL(RENAME_TABLE_QUERY));
	}

	@Override
	protected DB getDB() {
		return new DB2DB(0, 0) {

			@Override
			public void runSQL(String template) {
				_nullableAlter = template;
			}

		};
	}

	private String _nullableAlter;

}