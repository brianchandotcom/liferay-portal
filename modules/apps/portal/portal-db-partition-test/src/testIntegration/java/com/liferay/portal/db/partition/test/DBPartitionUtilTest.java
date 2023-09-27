/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class DBPartitionUtilTest extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		enableDBPartition();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		disableDBPartition();
	}

	@Before
	public void setUp() throws Exception {
		for (long companyId : COMPANY_IDS) {
			db.runSQL(
				"create schema if not exists " + getSchemaName(companyId) +
					" character set utf8");
		}
	}

	@After
	public void tearDown() throws Exception {
		dropSchemas();
	}

	@Test
	public void testAccessCompanyByCompanyThreadLocal() throws Exception {
		for (long companyId : COMPANY_IDS) {
			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.
						setInitializingCompanyIdWithSafeCloseable(companyId);
				Connection connection = DataAccess.getConnection();
				Statement statement = connection.createStatement()) {

				createAndPopulateTable(TEST_TABLE_NAME);

				statement.execute("select 1 from " + TEST_TABLE_NAME);
			}
		}
	}

	@Test
	public void testAccessDefaultCompanyByCompanyThreadLocal()
		throws SQLException {

		long currentCompanyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(portal.getDefaultCompanyId());

		try (Connection connection = DataAccess.getConnection();
			Statement statement = connection.createStatement()) {

			statement.execute("select 1 from CompanyInfo");
		}
		finally {
			CompanyThreadLocal.setCompanyId(currentCompanyId);
		}
	}

	@Test
	public void testAddDBPartition() throws Exception {
		addDBPartitions();

		try (Statement statement = connection.createStatement()) {
			for (long companyId : COMPANY_IDS) {
				statement.execute(
					"select 1 from " + getSchemaName(companyId) +
						".CompanyInfo");
			}
		}
		finally {
			removeDBPartitions(false);
		}
	}

	@Test
	public void testAddDBPartitionUsesDBCharacterSetEncoding()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.db.partition.DBPartitionUtil",
				LoggerTestUtil.INFO)) {

			addDBPartitions();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			String message = String.valueOf(logEntries.get(0));

			Assert.assertTrue(
				message,
				message.contains(
					"Obtained character set encoding from session with " +
						"value:"));
		}
		finally {
			removeDBPartitions(false);
		}
	}

	@Test
	public void testAddDefaultDBPartition() throws PortalException {
		Assert.assertFalse(
			DBPartitionUtil.addDBPartition(portal.getDefaultCompanyId()));
	}

	@Test
	public void testForEachCompanyId() throws Exception {
		try {
			addDBPartitions();

			insertPartitionRequiredData();

			Set<Long> companyIds = new ConcurrentSkipListSet<>();
			Set<Long> threadIds = new ConcurrentSkipListSet<>();

			CompanyThreadLocal.setCompanyId(CompanyConstants.SYSTEM);

			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					Assert.assertEquals(
						companyId, CompanyThreadLocal.getCompanyId());

					Assert.assertTrue(CompanyThreadLocal.isLocked());

					companyIds.add(companyId);

					Thread thread = Thread.currentThread();

					threadIds.add(thread.getId());
				});

			Assert.assertEquals(companyIds.toString(), 3, companyIds.size());
			Assert.assertEquals(threadIds.toString(), 3, threadIds.size());
		}
		finally {
			deletePartitionRequiredData();
			removeDBPartitions(false);
		}
	}

	@Test
	public void testMigrateDBPartition() throws Exception {
		addDBPartitions();

		try {
			HashMap<Long, List<String>> viewNames = new HashMap<>();
			HashMap<Long, Integer> tablesCount = new HashMap<>();

			for (long companyId : COMPANY_IDS) {
				List<String> views = _getObjectNames("VIEW", companyId);

				viewNames.put(companyId, views);

				Assert.assertNotEquals(0, views.size());

				tablesCount.put(companyId, _getTablesCount(companyId));
			}

			removeDBPartitions(true);

			for (long companyId : COMPANY_IDS) {
				List<String> views = viewNames.get(companyId);

				Assert.assertEquals(
					tablesCount.get(companyId) + views.size(),
					_getTablesCount(companyId));

				Assert.assertEquals(0, _getViewsCount(companyId));

				for (String viewName : viewNames.get(companyId)) {
					Assert.assertEquals(
						viewName + " count",
						_getCount(viewName, true, companyId),
						_getCount(viewName, false, companyId));
				}
			}
		}
		finally {
			removeDBPartitions(false);
		}
	}

	@Test
	public void testMigrateDBPartitionRollback() throws Exception {
		addDBPartitions();

		try {
			for (long companyId : COMPANY_IDS) {
				int tablesCount = _getTablesCount(companyId);
				int viewsCount = _getViewsCount(companyId);

				try {
					String fullTestTableName =
						getSchemaName(companyId) + "." +
							TEST_CONTROL_TABLE_NAME;

					createAndPopulateControlTable(TEST_CONTROL_TABLE_NAME);
					createAndPopulateControlTable(fullTestTableName);

					try {
						removeDBPartitions(true);

						Assert.fail("Should throw an exception");
					}
					catch (Exception exception) {
						Assert.assertEquals(
							tablesCount, _getTablesCount(companyId));
						Assert.assertEquals(
							viewsCount, _getViewsCount(companyId) - 1);
					}
				}
				finally {
					dropTable(TEST_CONTROL_TABLE_NAME);
				}
			}
		}
		finally {
			removeDBPartitions(false);
		}
	}

	@Test
	public void testRemoveDBPartition() throws Exception {
		addDBPartitions();

		removeDBPartitions(false);

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getCatalogs()) {
			while (resultSet.next()) {
				String schemaName = resultSet.getString("TABLE_CAT");

				for (long companyId : COMPANY_IDS) {
					Assert.assertNotEquals(
						getSchemaName(companyId), schemaName);
				}
			}
		}
	}

	private int _getCount(
			String tableName, boolean defaultSchema, long companyId)
		throws Exception {

		String whereClause = StringPool.BLANK;

		if (dbInspector.hasColumn(tableName, "companyId")) {
			whereClause = " where companyId = " + companyId;
		}

		String fullTableName = tableName;

		if (!defaultSchema) {
			fullTableName =
				getSchemaName(companyId) + StringPool.PERIOD + tableName;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(1) from " + fullTableName + whereClause);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		}

		throw new Exception("Table does not exist");
	}

	private List<String> _getObjectNames(String objectType, long companyId)
		throws Exception {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		List<String> objectNames = new ArrayList<>();

		try (ResultSet resultSet = databaseMetaData.getTables(
				getSchemaName(companyId), dbInspector.getSchema(), null,
				new String[] {objectType})) {

			while (resultSet.next()) {
				objectNames.add(resultSet.getString("TABLE_NAME"));
			}
		}

		return objectNames;
	}

	private int _getTablesCount(long companyId) throws Exception {
		List<String> tableNames = _getObjectNames("TABLE", companyId);

		return tableNames.size();
	}

	private int _getViewsCount(long companyId) throws Exception {
		List<String> viewNames = _getObjectNames("VIEW", companyId);

		return viewNames.size();
	}

}