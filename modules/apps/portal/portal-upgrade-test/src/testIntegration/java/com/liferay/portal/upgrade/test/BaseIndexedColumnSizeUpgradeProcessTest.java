/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.BaseIndexedColumnSizeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class BaseIndexedColumnSizeUpgradeProcessTest
	extends BaseIndexedColumnSizeUpgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				_db.runSQL(
					StringBundler.concat(
						"create table ", _TABLE_NAME, " (id LONG not null ",
						"primary key, groupId LONG, column1 VARCHAR(10) null, ",
						"column2 VARCHAR(10) null)"));
				_db.runSQL(
					StringBundler.concat(
						"create unique index IX_TEST_COLUMN_SIZE on ",
						_TABLE_NAME, " (groupId, column1, column2)"));
			});
	}

	@After
	public void tearDown() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _db.runSQL("DROP_TABLE_IF_EXISTS(" + _TABLE_NAME + ")"));
	}

	@Test
	public void testUpgrade() throws Exception {
		String value1 = RandomTestUtil.randomString(10);
		String value2 = RandomTestUtil.randomString(10);

		_insert(1, value1, value2);

		upgrade();

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			Assert.assertTrue(
				dbInspector.hasColumnType(
					_TABLE_NAME, "column1", "VARCHAR(5) null"));
			Assert.assertTrue(
				dbInspector.hasColumnType(
					_TABLE_NAME, "column2", "VARCHAR(5) null"));
			Assert.assertTrue(
				dbInspector.hasIndex(_TABLE_NAME, "IX_TEST_COLUMN_SIZE"));
		}

		_assertValues(1, value1.substring(0, 5), value2.substring(0, 5));
	}

	@Test
	public void testUpgradeWithDuplicateUniqueIndexEntries() throws Exception {
		String value = RandomTestUtil.randomString(5);

		_insert(1, value + "1", value + "1");
		_insert(2, value + "2", value + "2");

		Assert.assertThrows(UpgradeException.class, this::upgrade);

		_assertValues(1, value + "1", value + "1");
	}

	@Override
	protected int getMaxColumnLength() {
		return 5;
	}

	@Override
	protected String[][] getTableAndColumnNames() {
		return new String[][] {
			{_TABLE_NAME, "column1"}, {_TABLE_NAME, "column2"}
		};
	}

	@Override
	protected String[][] getTableAndUniqueIndexColumnNames() {
		return new String[][] {{_TABLE_NAME, "groupId", "column1", "column2"}};
	}

	private void _assertValues(
			long id, String expectedValue1, String expectedValue2)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"select column1, column2 from " + _TABLE_NAME +
					" where id = ?")) {

			preparedStatement.setLong(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(
					expectedValue1, resultSet.getString("column1"));
				Assert.assertEquals(
					expectedValue2, resultSet.getString("column2"));
			}
		}
	}

	private void _insert(long id, String value1, String value2)
		throws Exception {

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME, " (id, groupId, column1, ",
				"column2) values (", id, ", 0, '", value1, "', '", value2,
				"')"));
	}

	private static final String _TABLE_NAME = "TestIndexedColumnSize";

	@Inject
	private CompanyLocalService _companyLocalService;

	private final DB _db = DBManagerUtil.getDB();

}