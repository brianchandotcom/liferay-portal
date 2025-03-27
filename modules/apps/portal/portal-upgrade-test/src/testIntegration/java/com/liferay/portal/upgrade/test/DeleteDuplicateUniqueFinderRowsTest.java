/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.DeleteDuplicateUniqueFinderRows;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

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
public class DeleteDuplicateUniqueFinderRowsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_db = DBManagerUtil.getDB();

		_oldDuplicateRowPrimaryKeyValue = 1;

		_newDuplicateRowPrimaryKeyValue = 3;

		_uniqueRowPrimaryKeyValue = 4;
	}

	@Before
	public void setUp() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				_db.runSQL(
					StringBundler.concat(
						"create table TestTable (primaryKeyColumn LONG not ",
						"null primary key, column1 BOOLEAN, column2 LONG, ",
						"column3 VARCHAR(75) null)"));

				_db.runSQL(
					StringBundler.concat(
						"insert into TestTable values (",
						_oldDuplicateRowPrimaryKeyValue,
						", [$TRUE$], 2, '3')"));

				_db.runSQL(
					"insert into TestTable values (2, [$TRUE$], 2, '3')");

				_db.runSQL(
					StringBundler.concat(
						"insert into TestTable values (",
						_newDuplicateRowPrimaryKeyValue,
						", [$TRUE$], 2, '3')"));

				_db.runSQL(
					StringBundler.concat(
						"insert into TestTable values (",
						_uniqueRowPrimaryKeyValue, ", [$FALSE$], 2, '3')"));
			});
	}

	@After
	public void tearDown() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _db.runSQL("drop table TestTable"));
	}

	@Test
	public void testUpgrade() throws SQLException, UpgradeException {
		_assert(false, null);

		DeleteDuplicateUniqueFinderRows upgradeProcess =
			new DeleteDuplicateUniqueFinderRows(
				"TestTable", new String[] {"column1", "column2", "column3"});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.kernel.upgrade." +
					"DeleteDuplicateUniqueFinderRows",
				LoggerTestUtil.OFF)) {

			upgradeProcess.upgrade();
		}

		_assert(true, _oldDuplicateRowPrimaryKeyValue);
	}

	@Test
	public void testUpgradeWithOrderByClause()
		throws SQLException, UpgradeException {

		_assert(false, null);

		DeleteDuplicateUniqueFinderRows upgradeProcess =
			new DeleteDuplicateUniqueFinderRows(
				"TestTable", new String[] {"column1", "column2", "column3"},
				"primaryKeyColumn asc");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.kernel.upgrade." +
					"DeleteDuplicateUniqueFinderRows",
				LoggerTestUtil.OFF)) {

			upgradeProcess.upgrade();
		}

		_assert(true, _newDuplicateRowPrimaryKeyValue);
	}

	private void _assert(
			boolean duplicatesRemoved, Long remainedRowPrimaryKeyValue)
		throws SQLException {

		_companyLocalService.forEachCompany(
			company -> {
				try (PreparedStatement preparedStatement =
						_connection.prepareStatement(
							"select count(*) from TestTable group by " +
								"column1, column2, column3 having count(*) > " +
									"1");
					ResultSet resultSet = preparedStatement.executeQuery()) {

					if (!duplicatesRemoved) {
						Assert.assertTrue(resultSet.next());

						Assert.assertEquals(3, resultSet.getInt(1));

						return;
					}

					Assert.assertFalse(resultSet.next());
				}

				try (PreparedStatement preparedStatement =
						_connection.prepareStatement(
							"select primaryKeyColumn from TestTable");
					ResultSet resultSet = preparedStatement.executeQuery()) {

					List<Long> primaryKeyValues = new ArrayList<>();

					while (resultSet.next()) {
						primaryKeyValues.add(resultSet.getLong(1));
					}

					Assert.assertEquals(
						primaryKeyValues.toString(), 2,
						primaryKeyValues.size());
					Assert.assertTrue(
						primaryKeyValues.contains(remainedRowPrimaryKeyValue));
					Assert.assertTrue(
						primaryKeyValues.contains(_uniqueRowPrimaryKeyValue));
				}
			});
	}

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static Connection _connection;
	private static DB _db;
	private static long _newDuplicateRowPrimaryKeyValue;
	private static long _oldDuplicateRowPrimaryKeyValue;
	private static long _uniqueRowPrimaryKeyValue;

}