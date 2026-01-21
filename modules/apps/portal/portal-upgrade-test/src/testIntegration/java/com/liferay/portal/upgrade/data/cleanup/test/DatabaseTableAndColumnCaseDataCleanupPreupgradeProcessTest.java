/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.DatabaseTableAndColumnCaseDataCleanupPreupgradeProcess;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class DatabaseTableAndColumnCaseDataCleanupPreupgradeProcessTest
	extends DatabaseTableAndColumnCaseDataCleanupPreupgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		DBType dbType = DBManagerUtil.getDBType();

		Assume.assumeTrue(
			(dbType == DBType.MYSQL) || (dbType == DBType.MARIADB) ||
			(dbType == DBType.SQLSERVER));
	}

	@Before
	public void setUp() throws Exception {
		_connection = DataAccess.getConnection();
		_db = DBManagerUtil.getDB();
	}

	@After
	public void tearDown() throws Exception {
		DataAccess.cleanUp(_connection);
	}

	@Test
	public void testUpgradeWithObjectDefinitionDBTable() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		String objectDefinitionDBTableName = objectDefinition.getDBTableName();

		String invalidObjectDefinitionDBTableName = StringUtil.toUpperCase(
			objectDefinitionDBTableName);

		try (Connection connection = DataAccess.getConnection();
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				DatabaseTableAndColumnCaseDataCleanupPreupgradeProcess.class.
					getName(),
				LoggerTestUtil.INFO)) {

			DBPartitionUtil.forEachCompanyId(
				companyId -> _db.runSQL(
					"DROP_TABLE_IF_EXISTS(" + objectDefinitionDBTableName +
						")"));

			DBType dbType = DBManagerUtil.getDBType();

			if (dbType == DBType.SQLSERVER) {
				DBPartitionUtil.forEachCompanyId(
					companyId -> _db.runSQL(
						StringBundler.concat(
							"create table [",
							invalidObjectDefinitionDBTableName,
							"] ([testColumn] LONG)")));
			}
			else {
				DBPartitionUtil.forEachCompanyId(
					companyId -> _db.runSQL(
						StringBundler.concat(
							"create table `",
							invalidObjectDefinitionDBTableName,
							"` (`testColumn` LONG)")));
			}

			upgrade();

			List<String> messages = logCapture.getMessages();

			Assert.assertEquals(messages.toString(), 1, messages.size());

			Assert.assertTrue(
				messages.contains(
					StringBundler.concat(
						"Table ", objectDefinitionDBTableName,
						", altered because incorrect table name casing, was ",
						invalidObjectDefinitionDBTableName)));
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);

			DBPartitionUtil.forEachCompanyId(
				companyId -> _db.runSQL(
					"DROP_TABLE_IF_EXISTS(" +
						invalidObjectDefinitionDBTableName + ")"));
			DBPartitionUtil.forEachCompanyId(
				companyId -> _db.runSQL(
					"DROP_TABLE_IF_EXISTS(" + objectDefinitionDBTableName +
						")"));
			DBPartitionUtil.forEachCompanyId(
				companyId -> _db.runSQL(
					"DROP_TABLE_IF_EXISTS(" + objectDefinitionDBTableName +
						"_temp)"));
		}
	}

	@Test
	public void testUpgradeWithServiceComponentTable() throws Exception {
		DBInspector dbInspector = new DBInspector(_connection);

		String invalidColumnName = "testCOLUMN";
		String invalidTableName = "testTABLE";

		ServiceComponent serviceComponent =
			_serviceComponentLocalService.createServiceComponent(
				RandomTestUtil.nextLong());

		serviceComponent.setMvccVersion(0);
		serviceComponent.setBuildNamespace("com.liferay.test.service.impl");

		String testColumnName = "testColumn";
		String testTableName = "TestTable";

		serviceComponent.setData(
			StringBundler.concat(
				"<![CDATA[create table ", testTableName, " (	 \n",
				testColumnName, " LONG"));

		_serviceComponentLocalService.addServiceComponent(serviceComponent);

		try (Connection connection = DataAccess.getConnection();
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				DatabaseTableAndColumnCaseDataCleanupPreupgradeProcess.class.
					getName(),
				LoggerTestUtil.INFO)) {

			DBType dbType = DBManagerUtil.getDBType();

			if (dbType == DBType.SQLSERVER) {
				DBPartitionUtil.forEachCompanyId(
					companyId -> _db.runSQL(
						StringBundler.concat(
							"create table [", invalidTableName, "] ([",
							invalidColumnName, "] LONG)")));
			}
			else {
				DBPartitionUtil.forEachCompanyId(
					companyId -> _db.runSQL(
						StringBundler.concat(
							"create table `", invalidTableName, "` (`",
							invalidColumnName, "` LONG)")));
			}

			upgrade();

			List<String> messages = logCapture.getMessages();

			Assert.assertEquals(messages.toString(), 2, messages.size());

			Assert.assertTrue(
				messages.contains(
					StringBundler.concat(
						"Table ", testTableName,
						", altered because incorrect table name casing, was ",
						invalidTableName)));

			Assert.assertTrue(
				messages.contains(
					StringBundler.concat(
						"Table ", testTableName,
						", altered because incorrect column name casing, ",
						"column: ", invalidColumnName, " renamed to ",
						testColumnName)));

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			try (ResultSet resultSet = databaseMetaData.getColumns(
					dbInspector.getCatalog(), dbInspector.getSchema(),
					testTableName, "%")) {

				while (resultSet.next()) {
					String tableName = resultSet.getString("TABLE_NAME");
					String columnName = resultSet.getString("COLUMN_NAME");

					Assert.assertEquals(tableName, testTableName, tableName);
					Assert.assertEquals(columnName, testColumnName, columnName);
				}
			}
		}
		finally {
			_serviceComponentLocalService.deleteServiceComponent(
				serviceComponent);

			DBPartitionUtil.forEachCompanyId(
				companyId -> _db.runSQL(
					"DROP_TABLE_IF_EXISTS(" + invalidTableName + ")"));
			DBPartitionUtil.forEachCompanyId(
				companyId -> _db.runSQL(
					"DROP_TABLE_IF_EXISTS(" + testTableName + ")"));
			DBPartitionUtil.forEachCompanyId(
				companyId -> _db.runSQL(
					"DROP_TABLE_IF_EXISTS(" + testTableName + "_temp)"));
		}
	}

	private Connection _connection;
	private DB _db;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ServiceComponentLocalService _serviceComponentLocalService;

}