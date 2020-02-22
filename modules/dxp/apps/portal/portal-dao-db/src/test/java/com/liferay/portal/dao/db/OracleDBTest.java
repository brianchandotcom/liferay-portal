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
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.test.BaseDBTestCase;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.stubbing.OngoingStubbing;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.ConstructorExpectationSetup;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Shinn Lok
 * @author Alberto Chaparro
 */
@PrepareForTest({DataAccess.class, OracleDB.class})
@RunWith(PowerMockRunner.class)
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
		_mockIsNullable(false);

		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR);\n",
			buildSQL(
				"alter_column_type DLFolder userName VARCHAR(75) not null;"));
	}

	@Test
	public void testRewordAlterColumnTypeNotNullWhenNull() throws Exception {
		_mockIsNullable(true);

		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR) not " +
				"null;\n",
			buildSQL(
				"alter_column_type DLFolder userName VARCHAR(75) not null;"));
	}

	@Test
	public void testRewordAlterColumnTypeNullWhenNotNull() throws Exception {
		_mockIsNullable(false);

		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR) null;\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75) null;"));
	}

	@Test
	public void testRewordAlterColumnTypeNullWhenNull() throws Exception {
		_mockIsNullable(true);

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
		return new OracleDB(0, 0);
	}

	private void _mockIsNullable(boolean nullable) throws Exception {
		PowerMockito.mockStatic(DataAccess.class);

		PowerMockito.when(
			DataAccess.getConnection()
		).thenReturn(
			null
		);

		DBInspector dbInspector = PowerMockito.mock(DBInspector.class);

		ConstructorExpectationSetup<DBInspector>
			dbInspectorConstructorExpectationSetup = PowerMockito.whenNew(
				DBInspector.class);

		OngoingStubbing dbInspectorOngoingStubbing =
			dbInspectorConstructorExpectationSetup.withAnyArguments();

		dbInspectorOngoingStubbing.thenReturn(dbInspector);

		PowerMockito.when(
			dbInspector.isNullable("DLFolder", "userName")
		).thenReturn(
			nullable
		);
	}

}