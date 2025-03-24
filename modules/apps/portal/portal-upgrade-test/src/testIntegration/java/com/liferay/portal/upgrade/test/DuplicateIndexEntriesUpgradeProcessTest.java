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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.DuplicateIndexEntriesUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class DuplicateIndexEntriesUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_db = DBManagerUtil.getDB();

		_oldPrimaryKey = RandomTestUtil.randomLong(10, 10);

		_newPrimaryKey = RandomTestUtil.randomLong(9000, 10000);

		_staticPrimaryKey = RandomTestUtil.randomLong(11, 8999);
	}

	@Before
	public void setUp() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				_db.runSQL(
					StringBundler.concat(
						"create table TestTable (primaryKeyColumn LONG not ",
						"null primary key, column1 LONG, column2 LONG, ",
						"column3 LONG)"));

				_db.runSQL(
					StringBundler.concat(
						"insert into TestTable values (", _oldPrimaryKey,
						", 1, 2, 3)"));

				_db.runSQL(
					StringBundler.concat(
						"insert into TestTable values (",
						RandomTestUtil.randomLong(10, 8999), ", 1, 2, 3)"));

				_db.runSQL(
					StringBundler.concat(
						"insert into TestTable values (", _staticPrimaryKey,
						", 5, 6, 7)"));

				_db.runSQL(
					StringBundler.concat(
						"insert into TestTable values (", _newPrimaryKey,
						", 1, 2, 3)"));
			});
	}

	@After
	public void tearDown() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _db.runSQL("drop table TestTable"));
	}

	@Test
	public void testDuplicateRemovalProcess()
		throws SQLException, UpgradeException {

		_assertDuplicates(false);

		DuplicateIndexEntriesUpgradeProcess upgradeProcess =
			new DuplicateIndexEntriesUpgradeProcess(
				"TestTable", new String[] {"column1", "column2", "column3"});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.kernel.upgrade." +
					"DuplicateIndexEntriesUpgradeProcess",
				LoggerTestUtil.OFF)) {

			upgradeProcess.upgrade();
		}

		_assertDuplicates(true);

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select primaryKeyColumn from TestTable where column1 = 1 " +
					"and column2 = 2 and column3 = 3");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(_oldPrimaryKey, resultSet.getLong(1));
		}
	}

	@Test
	public void testDuplicateRemovalProcessWithOrderBy()
		throws SQLException, UpgradeException {

		_assertDuplicates(false);

		DuplicateIndexEntriesUpgradeProcess upgradeProcess =
			new DuplicateIndexEntriesUpgradeProcess(
				"TestTable", new String[] {"column1", "column2", "column3"},
				"primaryKeyColumn asc");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.kernel.upgrade." +
					"DuplicateIndexEntriesUpgradeProcess",
				LoggerTestUtil.OFF)) {

			upgradeProcess.upgrade();
		}

		_assertDuplicates(true);

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select primaryKeyColumn from TestTable where column1 = 1 " +
					"and column2 = 2 and column3 = 3");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(_newPrimaryKey, resultSet.getLong(1));
		}
	}

	private void _assertDuplicates(boolean removed) throws SQLException {
		String countSQL =
			"select count(*) from TestTable group by column1, column2, " +
				"column3 having count(*) > 1";

		if (removed) {
			_companyLocalService.forEachCompany(
				company -> {
					try (PreparedStatement preparedStatement =
							_connection.prepareStatement(countSQL);
						ResultSet resultSet =
							preparedStatement.executeQuery()) {

						Assert.assertFalse(resultSet.next());
					}
				});

			_companyLocalService.forEachCompany(
				company -> {
					try (PreparedStatement preparedStatement =
							_connection.prepareStatement(
								"select primaryKeyColumn from TestTable " +
									"where primaryKeyColumn = " +
										_staticPrimaryKey);
						ResultSet resultSet =
							preparedStatement.executeQuery()) {

						Assert.assertTrue(resultSet.next());
					}
				});
		}
		else {
			_companyLocalService.forEachCompany(
				company -> {
					try (PreparedStatement preparedStatement =
							_connection.prepareStatement(countSQL);
						ResultSet resultSet =
							preparedStatement.executeQuery()) {

						Assert.assertTrue(resultSet.next());

						Assert.assertEquals(3, resultSet.getLong(1));
					}
				});
		}
	}

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static Connection _connection;
	private static DB _db;
	private static long _newPrimaryKey;
	private static long _oldPrimaryKey;
	private static long _staticPrimaryKey;

}