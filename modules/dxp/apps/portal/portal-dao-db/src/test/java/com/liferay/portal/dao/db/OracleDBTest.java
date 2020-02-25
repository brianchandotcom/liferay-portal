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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shinn Lok
 * @author Alberto Chaparro
 */
public class OracleDBTest extends BaseDBTestCase {

	@Test
	public void testRewordAlterColumnType() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR);\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75);"));
	}

	@Test
	public void testRewordAlterColumnTypeLowerCase() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR);\n",
			buildSQL("alter_column_type DLFolder userName varchar(75);"));
	}

	@Test
	public void testRewordAlterColumnTypeNoSemicolon() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR);\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75)"));
	}

	@Test
	public void testRewordAlterColumnTypeNotNullWhenNotNull() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR);\n",
			buildSQL(
				"alter_column_type DLFolder userName VARCHAR(75) not null;"));
	}

	@Test
	public void testRewordAlterColumnTypeNotNullWhenNull() throws Exception {
		_nullable = true;

		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR) not " +
				"null;\n",
			buildSQL(
				"alter_column_type DLFolder userName VARCHAR(75) not null;"));
	}

	@Test
	public void testRewordAlterColumnTypeNullWhenNotNull() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR) null;\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75) null;"));
	}

	@Test
	public void testRewordAlterColumnTypeNullWhenNull() throws Exception {
		_nullable = true;

		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR);\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75) null;"));
	}

	@Test
	public void testRewordAlterColumnTypeString() throws Exception {
		Assert.assertEquals(
			"alter table BlogsEntry modify description VARCHAR2(4000 CHAR);\n",
			buildSQL("alter_column_type BlogsEntry description STRING;"));
	}

	@Override
	protected DB getDB() {
		return new OracleDB(0, 0) {

			@Override
			protected boolean isNullable(String tableName, String columnName) {
				return _nullable;
			}

		};
	}

	private boolean _nullable;

}