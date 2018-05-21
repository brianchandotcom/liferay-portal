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