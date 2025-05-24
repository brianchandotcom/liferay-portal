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
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.PreUpgradeVerifyCharacterSet;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class PreUpgradeVerifyCharacterSetTest
	extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	public static void assume() {
		Assume.assumeTrue(
			(_db.getDBType() == DBType.MYSQL) ||
			(_db.getDBType() == DBType.MARIADB) ||
			(_db.getDBType() == DBType.POSTGRESQL));
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_db = DBManagerUtil.getDB();

		_dataSource = InfrastructureUtil.getDataSource();

		if ((_db.getDBType() == DBType.MYSQL) ||
			(_db.getDBType() == DBType.MARIADB)) {

			_db.runSQL("create database TestDB default character set latin1");
		}
		else if (_db.getDBType() == DBType.POSTGRESQL) {
			_db.runSQL(
				"create database \"TestDB\" encoding 'LATIN1' lc_ctype 'C' " +
					"lc_collate 'C' template template0");
		}

		_testDataSource = DataSourceFactoryUtil.initDataSource(
			PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME, _getSchemaURL(),
			PropsValues.JDBC_DEFAULT_USERNAME,
			PropsValues.JDBC_DEFAULT_PASSWORD, StringPool.BLANK);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_connection.close();

		if ((_db.getDBType() == DBType.MYSQL) ||
			(_db.getDBType() == DBType.MARIADB)) {

			_db.runSQL("drop schema TestDB");
		}
		else if (_db.getDBType() == DBType.POSTGRESQL) {
			_db.runSQL("drop database \"TestDB\"");
		}
	}

	@Test(expected = VerifyException.class)
	public void testVerifyUnsupportedCharacterSet() throws Exception {
		try {
			InfrastructureUtil.setDataSource(_testDataSource);

			super.testVerify();
		}
		finally {
			InfrastructureUtil.setDataSource(_dataSource);
		}
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new PreUpgradeVerifyCharacterSet();
	}

	private static String _getSchemaURL() throws Exception {
		if ((_db.getDBType() == DBType.MYSQL) ||
			(_db.getDBType() == DBType.MARIADB)) {

			return StringUtil.replace(
				PropsValues.JDBC_DEFAULT_URL, _connection.getCatalog(),
				"TestDB");
		}

		if (_db.getDBType() == DBType.POSTGRESQL) {
			String testString = StringBundler.concat(
				_connection.getCatalog(), "?currentSchema=",
				_connection.getSchema());

			return StringUtil.replace(
				PropsValues.JDBC_DEFAULT_URL, testString, "TestDB");
		}

		return null;
	}

	private static Connection _connection;
	private static DataSource _dataSource;
	private static DB _db;
	private static DataSource _testDataSource;

}