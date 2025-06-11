/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBTypeToSQLMap;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.PreupgradeVerifyDatabasePrivileges;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class PreupgradeVerifyDatabasePrivilegesTest
	extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_dataSource = InfrastructureUtil.getDataSource();

		_connection = DataAccess.getConnection();
	}

	@Before
	public void setUp() throws Exception {
		_createTestUser();

		_testUserDataSource = DataSourceFactoryUtil.initDataSource(
			PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME,
			PropsValues.JDBC_DEFAULT_URL, "testUser", "liferay",
			StringPool.BLANK);
	}

	@After
	public void tearDown() throws Exception {
		InfrastructureUtil.setDataSource(_dataSource);

		if (_testUserDataSource != null) {
			DataSourceFactoryUtil.destroyDataSource(_testUserDataSource);
		}

		_db.runSQL("drop user testUser");
	}

	@Test
	public void testVerifyAlterTablePrivilege() throws Exception {
		_revokePrivileges("alter");
		_revokePrivileges("index");

		InfrastructureUtil.setDataSource(_testUserDataSource);

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			String cause = exception.getCause(
			).getMessage();

			Assert.assertTrue(
				cause.contains("ALTER command denied to user 'testUser'"));
		}
		finally {
			InfrastructureUtil.setDataSource(_dataSource);
		}
	}

	@Test
	public void testVerifyCreateTablePrivilege() throws Exception {
		_revokePrivileges("create");

		InfrastructureUtil.setDataSource(_testUserDataSource);

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			String cause = exception.getCause(
			).getMessage();

			Assert.assertTrue(
				cause.contains("CREATE command denied to user 'testUser'"));
		}
	}

	@Test
	public void testVerifyDeleteRowPrivilege() throws Exception {
		_revokePrivileges("delete");

		InfrastructureUtil.setDataSource(_testUserDataSource);

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			String cause = exception.getCause(
			).getMessage();

			Assert.assertTrue(
				cause.contains("DELETE command denied to user 'testUser'"));
		}
		finally {
			InfrastructureUtil.setDataSource(_dataSource);
		}
	}

	@Test
	public void testVerifyInsertTablePrivilege() throws Exception {
		_revokePrivileges("insert");

		InfrastructureUtil.setDataSource(_testUserDataSource);

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			String cause = exception.getCause(
			).getMessage();

			Assert.assertTrue(
				cause.contains("INSERT command denied to user 'testUser'"));
		}
		finally {
			InfrastructureUtil.setDataSource(_dataSource);
		}
	}

	@Test
	public void testVerifyUpdateRowPrivilege() throws Exception {
		_revokePrivileges("update");

		InfrastructureUtil.setDataSource(_testUserDataSource);

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			String cause = exception.getCause(
			).getMessage();

			Assert.assertTrue(
				cause.contains("UPDATE command denied to user 'testUser'"));
		}
		finally {
			InfrastructureUtil.setDataSource(_dataSource);
		}
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new PreupgradeVerifyDatabasePrivileges();
	}

	private void _createTestUser() throws Exception {
		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(
			"create user 'testUser'@'%' identified by 'liferay';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new DBTypeToSQLMap(
			"grant select on *.* to 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new DBTypeToSQLMap(
			"grant create on *.* to 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new DBTypeToSQLMap(
			"grant alter on *.* to 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new DBTypeToSQLMap(
			"grant index on *.* to 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new DBTypeToSQLMap(
			"grant insert on *.* to 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new DBTypeToSQLMap(
			"grant delete on *.* to 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new DBTypeToSQLMap(
			"grant update on *.* to 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new DBTypeToSQLMap(
			"grant drop on *.* to 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);
	}

	private void _revokePrivileges(String privilege) throws Exception {
		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(
			StringBundler.concat(
				"revoke ", privilege, " on *.* from 'testUser'@'%';"));

		_db.runSQL(_connection, dbTypeToSQLMap);
	}

	private static DataSource _testUserDataSource;
	private static Connection _connection;
	private static DataSource _dataSource;
	private static DB _db;

}