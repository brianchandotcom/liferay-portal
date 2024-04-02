/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

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
		BaseDBPartitionTestCase.setUpClass();
	}

	@Before
	public void setUp() throws Exception {
		for (long companyId : COMPANY_IDS) {
			db.runSQL(
				dbPartitionDB.getCreatePartitionSQL(
					connection, getPartitionName(companyId)));
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
					"select 1 from " + getPartitionName(companyId) +
						".CompanyInfo");
			}
		}
		finally {
			removeDBPartitions();
		}
	}

	@Test
	public void testAddDefaultDBPartition() throws PortalException {
		Assert.assertFalse(
			DBPartitionUtil.addDBPartition(portal.getDefaultCompanyId()));
	}

	@Test
	public void testExtractAndInsertDBPartition() throws Exception {
		try {
			int companyCount = _getDefaultSchemaCount("Company");
			int virtualHostCount = _getDefaultSchemaCount("VirtualHost");

			addDBPartitions();
			insertPartitionRequiredData();

			HashMap<Long, List<String>> viewNames = new HashMap<>();
			HashMap<Long, Integer> tablesCount = new HashMap<>();

			for (long companyId : COMPANY_IDS) {
				viewNames.put(companyId, _getObjectNames("VIEW", companyId));
				tablesCount.put(companyId, _getTablesCount(companyId));
			}

			extractDBPartitions();

			Assert.assertEquals(
				companyCount, _getDefaultSchemaCount("Company"));
			Assert.assertEquals(
				virtualHostCount, _getDefaultSchemaCount("VirtualHost"));

			insertDBPartitions();

			Assert.assertEquals(
				companyCount + COMPANY_IDS.length,
				_getDefaultSchemaCount("Company"));
			Assert.assertEquals(
				virtualHostCount + COMPANY_IDS.length,
				_getDefaultSchemaCount("VirtualHost"));

			for (long companyId : COMPANY_IDS) {
				Assert.assertEquals(
					viewNames.get(companyId),
					_getObjectNames("VIEW", companyId));
				Assert.assertEquals(
					(int)tablesCount.get(companyId),
					_getTablesCount(companyId));
			}
		}
		finally {
			deletePartitionRequiredData();
			removeDBPartitions();
		}
	}

	@Test
	public void testExtractDBPartition() throws Exception {
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

			extractDBPartitions();

			for (long companyId : COMPANY_IDS) {
				List<String> views = viewNames.get(companyId);

				Assert.assertEquals(
					tablesCount.get(companyId) + views.size(),
					_getTablesCount(companyId));

				Assert.assertEquals(0, _getViewsCount(companyId));

				for (String viewName : viewNames.get(companyId)) {
					if (!isCopyableQuartzTable(viewName)) {
						Assert.assertEquals(
							viewName + " count",
							_getCount(viewName, true, companyId),
							_getCount(viewName, false, companyId));
					}
					else {
						Assert.assertEquals(
							viewName + " count", 0,
							_getCount(viewName, false, companyId));
					}
				}
			}
		}
		finally {
			removeDBPartitions();
		}
	}

	@Test
	public void testForEachCompanyId() throws Exception {
		try {
			addDBPartitions();

			insertPartitionRequiredData();

			Set<Long> companyIds = new ConcurrentSkipListSet<>();

			CompanyThreadLocal.setCompanyId(CompanyConstants.SYSTEM);

			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					Assert.assertEquals(
						companyId, CompanyThreadLocal.getCompanyId());

					Assert.assertTrue(CompanyThreadLocal.isLocked());

					companyIds.add(companyId);
				});

			Assert.assertEquals(companyIds.toString(), 3, companyIds.size());
		}
		finally {
			deletePartitionRequiredData();
			removeDBPartitions();
		}
	}

	@Test
	public void testRemoveDBPartition() throws Exception {
		addDBPartitions();

		removeDBPartitions();

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getCatalogs()) {
			while (resultSet.next()) {
				String catalogName = resultSet.getString("TABLE_CAT");

				for (long companyId : COMPANY_IDS) {
					Assert.assertNotEquals(
						getPartitionName(companyId), catalogName);
				}
			}
		}

		try (ResultSet resultSet = databaseMetaData.getSchemas()) {
			while (resultSet.next()) {
				String schemaName = resultSet.getString("TABLE_SCHEM");

				for (long companyId : COMPANY_IDS) {
					Assert.assertNotEquals(
						getPartitionName(companyId), schemaName);
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
				getPartitionName(companyId) + StringPool.PERIOD + tableName;
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

	private int _getDefaultSchemaCount(String tableName) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(1) from " + tableName);
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

		String partitionName = getPartitionName(companyId);

		try (ResultSet resultSet = databaseMetaData.getTables(
				dbPartitionDB.getCatalog(connection, partitionName),
				dbPartitionDB.getSchema(connection, partitionName), null,
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