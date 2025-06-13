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
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.PreupgradeVerifyDatabasePrivileges;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
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
	public static void setUpClass() {
		_db = DBManagerUtil.getDB();

		_dataSource = InfrastructureUtil.getDataSource();
	}

	@Before
	public void setUp() throws Exception {
		_createTestUser();

		_testUserDataSource = DataSourceFactoryUtil.initDataSource(
			PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME,
			PropsValues.JDBC_DEFAULT_URL, "test", "liferay", StringPool.BLANK);
	}

	@After
	public void tearDown() throws Exception {
		InfrastructureUtil.setDataSource(_dataSource);

		if (_testUserDataSource != null) {
			DataSourceFactoryUtil.destroyDataSource(_testUserDataSource);
		}

		_db.runSQL("drop user test");
	}

	@Test
	public void testVerifyAlterTablePrivilege() throws Exception {
		Assume.assumeTrue(
			(_db.getDBType() == DBType.MYSQL) ||
			(_db.getDBType() == DBType.MARIADB));

		_revokePrivileges("alter");

		InfrastructureUtil.setDataSource(_testUserDataSource);

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			String cause = exception.getCause(
			).getMessage();

			Assert.assertTrue(
				cause.contains("ALTER command denied to user 'test'"));
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
				cause.contains("CREATE command denied to user 'test'"));
		}
	}

	@Test
	public void testVerifyDeleteRowPrivilege() throws Exception {
		Assume.assumeTrue(
			(_db.getDBType() == DBType.MYSQL) ||
			(_db.getDBType() == DBType.MARIADB));

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
				cause.contains("DELETE command denied to user 'test'"));
		}
		finally {
			InfrastructureUtil.setDataSource(_dataSource);
		}
	}

	@Test
	public void testVerifyInsertTablePrivilege() throws Exception {
		Assume.assumeTrue(
			(_db.getDBType() == DBType.MYSQL) ||
			(_db.getDBType() == DBType.MARIADB));

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
				cause.contains("INSERT command denied to user 'test'"));
		}
		finally {
			InfrastructureUtil.setDataSource(_dataSource);
		}
	}

	@Test
	public void testVerifyUpdateRowPrivilege() throws Exception {
		Assume.assumeTrue(
			(_db.getDBType() == DBType.MYSQL) ||
			(_db.getDBType() == DBType.MARIADB));

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
				cause.contains("UPDATE command denied to user 'test'"));
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
		_db.runSQL("create user 'test'@'%' identified BY 'liferay'");
		_db.runSQL(
			"grant create,alter,index,select,insert,delete,update,drop on " +
				"*.* to 'test'@'%'");
	}

	private void _revokePrivileges(String privilege) throws Exception {
		_db.runSQL(
			StringBundler.concat(
				"revoke ", privilege, " on *.* from 'test'@'%'"));
	}

	private static DataSource _dataSource;
	private static DB _db;
	private static DataSource _testUserDataSource;

}