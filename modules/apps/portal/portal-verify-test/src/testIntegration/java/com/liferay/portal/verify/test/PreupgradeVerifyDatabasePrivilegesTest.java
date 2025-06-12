/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
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
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
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

		if (DBManagerUtil.getDBType() == DBType.POSTGRESQL) {
			_db.runSQL(
				"revoke all privileges on all tables in schema public from " +
					"testuser");
		}

		_db.runSQL("drop user testUser");

		if (DBManagerUtil.getDBType() == DBType.SQLSERVER) {
			_db.runSQL("drop login testUser");
		}
	}

	@Test
	public void testVerifyAlterTablePrivilege() throws Exception {
		Assume.assumeTrue(
			(_db.getDBType() == DBType.MYSQL) ||
			(_db.getDBType() == DBType.MARIADB));

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
		Assume.assumeTrue(
			(_db.getDBType() == DBType.MYSQL) ||
			(_db.getDBType() == DBType.MARIADB));

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

	@Ignore
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

	@Ignore
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

	@Ignore
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
		DBInspector dbInspector = new DBInspector(DataAccess.getConnection());

		if (DBManagerUtil.getDBType() == DBType.SQLSERVER) {
			_db.runSQL(
				StringBundler.concat(
					"create login [testUser] with password = 'liferay', ",
					"default_database = [", dbInspector.getCatalog(),
					"], check_policy = off; use lportal;"));
		}

		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(
			"create user 'testUser'@'%' identified BY 'liferay'");

		dbTypeToSQLMap.add(
			DBType.POSTGRESQL, "create user testUser with password 'liferay'");

		dbTypeToSQLMap.add(
			DBType.SQLSERVER,
			StringBundler.concat(
				"create user [testUser] for login [testUser] with ",
				"default_schema = ", dbInspector.getSchema()));

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new DBTypeToSQLMap(
			"grant create,alter,index,select,insert,delete,update,drop on " +
				"*.* to 'testUser'@'%'");

		dbTypeToSQLMap.add(
			DBType.POSTGRESQL,
			StringBundler.concat(
				"grant select, insert, delete, update on all tables in schema",
				dbInspector.getSchema(), " to testUser"));

		dbTypeToSQLMap.add(
			DBType.SQLSERVER,
			"grant select,insert,alter, update, delete on schema::dbo to " +
				"testUser");

		_db.runSQL(_connection, dbTypeToSQLMap);
	}

	private void _revokePrivileges(String privilege) throws Exception {
		DBInspector dbInspector = new DBInspector(DataAccess.getConnection());

		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(
			StringBundler.concat(
				"revoke ", privilege, " on *.* from 'testUser'@'%'"));

		if (privilege.equals("create")) {
			dbTypeToSQLMap.add(
				DBType.POSTGRESQL,
				StringBundler.concat(
					"revoke create on schema ", dbInspector.getSchema(),
					" from testUser"));
		}
		else {
			dbTypeToSQLMap.add(
				DBType.POSTGRESQL,
				StringBundler.concat(
					"revoke ", privilege, " on all tables in schema ",
					dbInspector.getSchema(), " from testUser"));
		}

		dbTypeToSQLMap.add(
			DBType.SQLSERVER,
			StringBundler.concat(
				"revoke ", privilege, " on schema::dbo from testUser"));

		_db.runSQL(_connection, dbTypeToSQLMap);
	}

	private static Connection _connection;
	private static DataSource _dataSource;
	private static DB _db;
	private static DataSource _testUserDataSource;

}