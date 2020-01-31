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
public class MySQLDBTest extends BaseDBTestCase {

	@Test
	public void testRewordAlterColumnType() throws IOException {
		Assert.assertEquals(
			"alter table DLFolder modify name varchar(255);\n",
			buildSQL("alter_column_type DLFolder name VARCHAR(255);"));
	}

	@Test
	public void testRewordAlterColumnTypeNoSemicolon() throws IOException {
		Assert.assertEquals(
			"alter table DLFolder modify name varchar(255);\n",
			buildSQL("alter_column_type DLFolder name VARCHAR(255)"));
	}

	@Test
	public void testRewordAlterColumnTypeNotNull() throws IOException {
		Assert.assertEquals(
			"alter table DLFolder modify name varchar(255) not null;\n",
			buildSQL("alter_column_type DLFolder name VARCHAR(255) not null;"));
	}

	@Test
	public void testRewordAlterColumnTypeNull() throws IOException {
		Assert.assertEquals(
			"alter table DLFolder modify name varchar(255) null;\n",
			buildSQL("alter_column_type DLFolder name VARCHAR(255) null;"));
	}

	@Test
	public void testRewordRenameTable() throws IOException {
		Assert.assertEquals(
			"rename table a to b;\n", buildSQL(RENAME_TABLE_QUERY));
	}

	@Override
	protected DB getDB() {
		return new MySQLDB(0, 0);
	}

}