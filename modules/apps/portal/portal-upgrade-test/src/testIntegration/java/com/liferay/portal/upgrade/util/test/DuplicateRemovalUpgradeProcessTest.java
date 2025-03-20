/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.DuplicateRemovalUpgradeProcess;
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
public class DuplicateRemovalUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_maxPrimaryKey = RandomTestUtil.randomLong(9000, 10000);

		_minPrimaryKey = RandomTestUtil.randomLong(10, 10);
	}

	@Before
	public void setUp() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				_db.runSQL(
					StringBundler.concat(
						"create table TestTable (mvccVersion LONG default 0 ",
						"not null, uuid_ VARCHAR(75) null, primaryKeyColumn ",
						"LONG not null primary key, column1 LONG, column2 LONG",
						", column3 LONG, column4 LONG, companyId LONG)"));

				_db.runSQL(
					StringBundler.concat(
						"insert into TestTable values (0, '",
						RandomTestUtil.randomString(10), "', ", _minPrimaryKey,
						", 1, 2, 3, 4, 1)"));

				_db.runSQL(
					StringBundler.concat(
						"insert into TestTable values (0, '",
						RandomTestUtil.randomString(10), "', ",
						RandomTestUtil.randomLong(10, 8999),
						", 1, 2, 3, 4, 1)"));

				_db.runSQL(
					StringBundler.concat(
						"insert into TestTable values (0, '",
						RandomTestUtil.randomString(10), "', ",
						RandomTestUtil.randomLong(10, 8999),
						", 1, 2, 3, 4, 1)"));

				_db.runSQL(
					StringBundler.concat(
						"insert into TestTable values (0, '",
						RandomTestUtil.randomString(10), "', ", _maxPrimaryKey,
						", 1, 2, 3, 4, 1)"));
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

		DuplicateRemovalUpgradeProcess upgradeProcess =
			new DuplicateRemovalUpgradeProcess(
				"TestTable", "column1, column2, column3, column4");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.kernel.upgrade.util." +
					"BaseDuplicateRemovalProcess",
				LoggerTestUtil.OFF)) {

			upgradeProcess.upgrade();
		}

		_assertDuplicates(true);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"SELECT primaryKeyColumn FROM TestTable");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(_minPrimaryKey, resultSet.getLong(1));
		}
	}

	@Test
	public void testDuplicateRemovalProcessWithOrderBy()
		throws SQLException, UpgradeException {

		_assertDuplicates(false);

		DuplicateRemovalUpgradeProcess upgradeProcess =
			new DuplicateRemovalUpgradeProcess(
				"TestTable", "column1, column2, column3, column4",
				"primaryKeyColumn", "asc");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.kernel.upgrade.util." +
					"BaseDuplicateRemovalProcess",
				LoggerTestUtil.OFF)) {

			upgradeProcess.upgrade();
		}

		_assertDuplicates(true);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"SELECT primaryKeyColumn FROM TestTable");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(_maxPrimaryKey, resultSet.getLong(1));
		}
	}

	private void _assertDuplicates(boolean removed) throws SQLException {
		String countSQL =
			"SELECT COUNT(*) FROM TestTable GROUP BY column1, column2, " +
				"column3, column4 HAVING COUNT(*) > 1";

		if (removed) {
			_companyLocalService.forEachCompany(
				company -> {
					try (Connection connection = DataAccess.getConnection();
						PreparedStatement preparedStatement =
							connection.prepareStatement(countSQL);
						ResultSet resultSet =
							preparedStatement.executeQuery()) {

						Assert.assertFalse(resultSet.next());
					}
				});
		}
		else {
			_companyLocalService.forEachCompany(
				company -> {
					try (Connection connection = DataAccess.getConnection();
						PreparedStatement preparedStatement =
							connection.prepareStatement(countSQL);
						ResultSet resultSet =
							preparedStatement.executeQuery()) {

						Assert.assertTrue(resultSet.next());

						Assert.assertEquals(4, resultSet.getLong(1));
					}
				});
		}
	}

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static DB _db;
	private static long _maxPrimaryKey;
	private static long _minPrimaryKey;

}