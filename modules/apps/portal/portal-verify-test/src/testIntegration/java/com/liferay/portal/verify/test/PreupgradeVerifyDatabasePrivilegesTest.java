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

		_badUserDataSource = DataSourceFactoryUtil.initDataSource(
			PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME,
			PropsValues.JDBC_DEFAULT_URL, "testUser", "liferay",
			StringPool.BLANK);
	}

	@After
	public void tearDown() throws Exception {
		InfrastructureUtil.setDataSource(_dataSource);

		if (_badUserDataSource != null) {
			DataSourceFactoryUtil.destroyDataSource(_badUserDataSource);
		}

		_db.runSQL("drop user testUser");

	}

	@Test
	public void testVerifyCreateTablePrivilege() throws Exception {
		revokePrivileges("CREATE");;

		InfrastructureUtil.setDataSource(
			_badUserDataSource);

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			String cause = exception.getCause().getMessage();

			Assert.assertTrue(
				cause.contains("CREATE command denied to user 'testUser'"));
		}
	}

	@Test
	public void testVerifyAlterTablePrivilege() throws Exception {
		revokePrivileges("ALTER");
		revokePrivileges("INDEX");

		InfrastructureUtil.setDataSource(
			_badUserDataSource);

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			String cause = exception.getCause().getMessage();

			Assert.assertTrue(
				cause.contains("ALTER command denied to user 'testUser'"));
		}
		finally {
			InfrastructureUtil.setDataSource(_dataSource);

		}
	}

	@Test
	public void testVerifyInsertTablePrivilege() throws Exception {
		revokePrivileges("insert");

		InfrastructureUtil.setDataSource(
			_badUserDataSource);

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			String cause = exception.getCause().getMessage();

			Assert.assertTrue(
				cause.contains("INSERT command denied to user 'testUser'"));
		}
		finally {
			InfrastructureUtil.setDataSource(_dataSource);
		}
	}

	@Test
	public void testVerifyUpdateRowPrivilege() throws Exception {
		revokePrivileges("update");

		InfrastructureUtil.setDataSource(
			_badUserDataSource);

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			String cause = exception.getCause().getMessage();

			Assert.assertTrue(
				cause.contains("UPDATE command denied to user 'testUser'"));
		}
		finally {
			InfrastructureUtil.setDataSource(_dataSource);
		}
	}

	@Test
	public void testVerifyDeleteRowPrivilege() throws Exception {
		revokePrivileges("delete");

		InfrastructureUtil.setDataSource(
			_badUserDataSource);

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			String cause = exception.getCause().getMessage();

			Assert.assertTrue(
				cause.contains("DELETE command denied to user 'testUser'"));
		}
		finally {
			InfrastructureUtil.setDataSource(_dataSource);
		}
	}


	private static void _createTestUser() throws Exception {
		DBTypeToSQLMap dbTypeToSQLMap = new
			DBTypeToSQLMap("CREATE USER 'testUser'@'%' IDENTIFIED BY 'liferay';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new
			DBTypeToSQLMap("GRANT SELECT ON *.* TO 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new
			DBTypeToSQLMap("GRANT CREATE ON *.* TO 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new
			DBTypeToSQLMap("GRANT ALTER ON *.* TO 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new
			DBTypeToSQLMap("GRANT INDEX ON *.* TO 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new
			DBTypeToSQLMap("GRANT INSERT ON *.* TO 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new
			DBTypeToSQLMap("GRANT DELETE ON *.* TO 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new
			DBTypeToSQLMap("GRANT UPDATE ON *.* TO 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);

		dbTypeToSQLMap = new
			DBTypeToSQLMap("GRANT DROP ON *.* TO 'testUser'@'%';");

		_db.runSQL(_connection, dbTypeToSQLMap);
	}

	private static void revokePrivileges(String privilege) throws Exception {
		DBTypeToSQLMap dbTypeToSQLMap = new
			DBTypeToSQLMap(StringBundler.concat("REVOKE ",privilege," ON *.* FROM 'testUser'@'%';"));

		_db.runSQL(_connection, dbTypeToSQLMap);
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new PreupgradeVerifyDatabasePrivileges();
	}

	private static DataSource _badUserDataSource;
	private static Connection _connection;
	private static DataSource _dataSource;
	private static DB _db;

}