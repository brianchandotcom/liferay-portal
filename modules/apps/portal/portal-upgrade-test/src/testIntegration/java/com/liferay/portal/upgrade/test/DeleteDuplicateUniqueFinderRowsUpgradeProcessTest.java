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
import com.liferay.portal.kernel.upgrade.DeleteDuplicateUniqueFinderRowsUpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
public class DeleteDuplicateUniqueFinderRowsUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();
		_db = DBManagerUtil.getDB();
	}

	@Before
	public void setUp() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				_db.runSQL(
					StringBundler.concat(
						"create table TestTable (primaryKey LONG not null ",
						"primary key, column1 BOOLEAN, column2 LONG, column3 ",
						"VARCHAR(75) null)"));

				_db.runSQL(
					"insert into TestTable values (1, [$TRUE$], 2, '3')");
				_db.runSQL(
					"insert into TestTable values (2, [$TRUE$], 2, '3')");
				_db.runSQL(
					"insert into TestTable values (3, [$TRUE$], 2, '3')");
				_db.runSQL(
					"insert into TestTable values (4, [$FALSE$], 2, '3')");
			});
	}

	@After
	public void tearDown() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _db.runSQL("drop table TestTable"));
	}

	@Test
	public void testUpgrade() throws Exception {
		_assert(false, null);

		DeleteDuplicateUniqueFinderRowsUpgradeProcess upgradeProcess =
			new DeleteDuplicateUniqueFinderRowsUpgradeProcess(
				"TestTable", new String[] {"column1", "column2", "column3"});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.kernel.upgrade." +
					"DeleteDuplicateUniqueFinderRowsUpgradeProcess",
				LoggerTestUtil.OFF)) {

			upgradeProcess.upgrade();
		}

		_assert(true, 1L);
	}

	@Test
	public void testUpgradeWithOrderByClause() throws Exception {
		_assert(false, null);

		DeleteDuplicateUniqueFinderRowsUpgradeProcess upgradeProcess =
			new DeleteDuplicateUniqueFinderRowsUpgradeProcess(
				"TestTable", new String[] {"column1", "column2", "column3"},
				"primaryKey asc");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.kernel.upgrade." +
					"DeleteDuplicateUniqueFinderRowsUpgradeProcess",
				LoggerTestUtil.OFF)) {

			upgradeProcess.upgrade();
		}

		_assert(true, 3L);
	}

	private void _assert(boolean duplicateRowsRemoved, Long uniqueRowPrimaryKey)
		throws Exception {

		_companyLocalService.forEachCompany(
			company -> {
				try (PreparedStatement preparedStatement =
						_connection.prepareStatement(
							"select count(*) from TestTable group by " +
								"column1, column2, column3 having count(*) > " +
									"1");
					ResultSet resultSet = preparedStatement.executeQuery()) {

					if (!duplicateRowsRemoved) {
						Assert.assertTrue(resultSet.next());

						Assert.assertEquals(3, resultSet.getInt(1));

						return;
					}

					Assert.assertFalse(resultSet.next());
				}

				try (PreparedStatement preparedStatement =
						_connection.prepareStatement(
							"select primaryKey from TestTable");
					ResultSet resultSet = preparedStatement.executeQuery()) {

					List<Long> primaryKeys = new ArrayList<>();

					while (resultSet.next()) {
						primaryKeys.add(resultSet.getLong(1));
					}

					Assert.assertEquals(
						primaryKeys.toString(), 2, primaryKeys.size());
					Assert.assertTrue(
						primaryKeys.contains(uniqueRowPrimaryKey));
					Assert.assertTrue(primaryKeys.contains(4L));
				}
			});
	}

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static Connection _connection;
	private static DB _db;

}