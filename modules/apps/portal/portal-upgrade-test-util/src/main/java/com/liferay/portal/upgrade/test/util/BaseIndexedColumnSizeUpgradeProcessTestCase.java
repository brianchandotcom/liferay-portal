/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcela Cunha
 */
public abstract class BaseIndexedColumnSizeUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		db = DBManagerUtil.getDB();
	}

	@After
	public void tearDown() throws Exception {
		if (_maxValueRowId != 0) {
			db.runSQL(
				StringBundler.concat(
					"delete from ", getTableName(), " where ",
					getPrimaryKeyColumnName(), " in (", _maxValueRowId, ", ",
					_oversizedValueRowId, ")"));
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			db.alterColumnType(
				connection, getTableName(), getColumnName(),
				"VARCHAR(" + getOldColumnLength() + ") null");
		}

		String maxValue = RandomTestUtil.randomString(getNewColumnLength());

		_maxValueRowId = RandomTestUtil.nextLong();

		db.runSQL(_getInsertSQL(maxValue, _maxValueRowId));

		String oversizedValue = RandomTestUtil.randomString(
			getNewColumnLength() + 1);

		_oversizedValueRowId = RandomTestUtil.nextLong();

		db.runSQL(_getInsertSQL(oversizedValue, _oversizedValueRowId));

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			getUpgradeStepRegistrator(), getUpgradeProcessClassName());

		upgradeProcess.upgrade();

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			Assert.assertTrue(
				dbInspector.hasColumnType(
					getTableName(), getColumnName(),
					StringBundler.concat(
						"VARCHAR(", getNewColumnLength(), ") null")));
			Assert.assertTrue(
				dbInspector.hasIndex(getTableName(), getIndexName()));
		}

		_assertColumnValue(maxValue, _maxValueRowId);
		_assertColumnValue(
			oversizedValue.substring(0, getNewColumnLength()),
			_oversizedValueRowId);
	}

	@Test
	public void testUpgradeWithDuplicateUniqueIndexEntries() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			db.alterColumnType(
				connection, getTableName(), getColumnName(),
				"VARCHAR(" + getOldColumnLength() + ") null");
		}

		String maxValue = RandomTestUtil.randomString(getNewColumnLength());

		_maxValueRowId = RandomTestUtil.nextLong();

		db.runSQL(_getInsertSQL(maxValue, _maxValueRowId));

		String oversizedValue = maxValue + "x";

		_oversizedValueRowId = RandomTestUtil.nextLong();

		db.runSQL(_getInsertSQL(oversizedValue, _oversizedValueRowId));

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			getUpgradeStepRegistrator(), getUpgradeProcessClassName());

		Assert.assertThrows(UpgradeException.class, upgradeProcess::upgrade);
	}

	protected abstract String getColumnName();

	protected abstract String getIndexName();

	protected abstract String getInsertSQL(
		String columnName, String columnValue, long id, String tableName);

	protected abstract int getNewColumnLength();

	protected abstract int getOldColumnLength();

	protected abstract String getPrimaryKeyColumnName();

	protected abstract String getTableName();

	protected abstract String getUpgradeProcessClassName();

	protected abstract UpgradeStepRegistrator getUpgradeStepRegistrator();

	protected static DB db;

	private void _assertColumnValue(String expectedValue, long id)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select ", getColumnName(), " from ", getTableName(),
					" where ", getPrimaryKeyColumnName(), " = ?"))) {

			preparedStatement.setLong(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(
					expectedValue, resultSet.getString(getColumnName()));
			}
		}
	}

	private String _getInsertSQL(String columnValue, long id) {
		return getInsertSQL(getColumnName(), columnValue, id, getTableName());
	}

	private long _maxValueRowId;
	private long _oversizedValueRowId;

}