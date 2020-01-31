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

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Pastor
 * @author Alberto Chaparro
 */
public class SQLServerDBTest extends BaseDBTestCase {

	@Test
	public void testRewordAlterColumnType() throws IOException {
		Assert.assertEquals(
			"alter table DLFolder alter column name nvarchar(255);\n",
			buildSQL("alter_column_type DLFolder name VARCHAR(255);"));
	}

	@Test
	public void testRewordAlterColumnTypeNoSemicolon() throws IOException {
		Assert.assertEquals(
			"alter table DLFolder alter column name nvarchar(255);\n",
			buildSQL("alter_column_type DLFolder name VARCHAR(255)"));
	}

	@Test
	public void testRewordAlterColumnTypeNotNull() throws IOException {
		Assert.assertEquals(
			"alter table DLFolder alter column name nvarchar(255) not null;\n",
			buildSQL("alter_column_type DLFolder name VARCHAR(255) not null;"));
	}

	@Test
	public void testRewordAlterColumnTypeNull() throws IOException {
		Assert.assertEquals(
			"alter table DLFolder alter column name nvarchar(255) null;\n",
			buildSQL("alter_column_type DLFolder name VARCHAR(255) null;"));
	}

	@Test
	public void testRewordRenameTable() throws IOException {
		Assert.assertEquals(
			"exec sp_rename 'a', 'b';\n", buildSQL(RENAME_TABLE_QUERY));
	}

	@Override
	protected DB getDB() {
		return new SQLServerDB(0, 0);
	}

}