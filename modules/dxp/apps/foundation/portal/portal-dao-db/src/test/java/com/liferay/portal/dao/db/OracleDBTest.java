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

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shinn Lok
 */
public class OracleDBTest extends BaseDBTestCase {

	@Test
	public void testRewordAlterColumnType() throws IOException {
		Assert.assertEquals(
			"alter table DLFolder modify name VARCHAR2(255 CHAR);\n",
			buildSQL("alter_column_type DLFolder name VARCHAR(255) null;"));
	}

	@Test
	public void testRewordAlterColumnTypeLowerCase() throws IOException {
		Assert.assertEquals(
			"alter table DLFolder modify name VARCHAR2(255 CHAR);\n",
			buildSQL("alter_column_type DLFolder name varchar(255);"));
	}

	@Test
	public void testRewordAlterColumnTypeString() throws IOException {
		Assert.assertEquals(
			"alter table BlogsEntry modify description VARCHAR2(4000 CHAR);\n",
			buildSQL("alter_column_type BlogsEntry description STRING;"));
	}

	@Test
	public void testRewordAlterColumnTypeStringNull() throws IOException {
		Assert.assertEquals(
			"alter table BlogsEntry modify description VARCHAR2(4000 CHAR);\n",
			buildSQL("alter_column_type BlogsEntry description STRING null;"));
	}

	@Override
	protected DB getDB() {
		return new OracleDB(0, 0);
	}

}