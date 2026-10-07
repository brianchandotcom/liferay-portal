/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

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

	@Test
	public void testUpgrade() throws Exception {
		for (String[] tableAndColumnName : getTableAndColumnNames()) {
			_testUpgrade(tableAndColumnName[1], tableAndColumnName[0]);
		}
	}

	@Test
	public void testUpgradeWithDuplicateUniqueIndexEntries() throws Exception {
		for (String[] tableAndColumnName : getTableAndColumnNames()) {
			_testUpgradeWithDuplicateUniqueIndexEntries(
				tableAndColumnName[1], tableAndColumnName[0]);
		}
	}

	protected String getInsertSQL(
		String columnName, String columnValue, long id,
		String primaryKeyColumnName, String tableName) {

		return StringBundler.concat(
			"insert into ", tableName, " (", primaryKeyColumnName, ", ",
			columnName, ") values (", id, ", '", columnValue, "')");
	}

	protected abstract int getNewColumnLength();

	protected abstract int getOldColumnLength();

	protected abstract String[][] getTableAndColumnNames();

	protected abstract String getUpgradeProcessClassName();

	protected abstract UpgradeStepRegistrator getUpgradeStepRegistrator();

	protected static DB db;

	private void _alterColumnType(
			int columnLength, String columnName, String tableName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			db.alterColumnType(
				connection, tableName, columnName,
				StringBundler.concat("VARCHAR(", columnLength, ") null"));
		}
	}

	private void _assertColumnValue(
			String columnName, String expectedValue, long id,
			String primaryKeyColumnName, String tableName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select ", columnName, " from ", tableName, " where ",
					primaryKeyColumnName, " = ?"))) {

			preparedStatement.setLong(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(
					expectedValue, resultSet.getString(columnName));
			}
		}
	}

	private List<IndexMetadata> _getIndexMetadatas(
			String columnName, String tableName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			return db.getIndexMetadatas(
				connection, tableName, columnName, false);
		}
	}

	private String _getPrimaryKeyColumnName(String tableName) throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			String[] primaryKeyColumnNames = db.getPrimaryKeyColumnNames(
				connection, tableName);

			return primaryKeyColumnNames[0];
		}
	}

	private void _restore(
			String columnName, long maxValueId, long oversizedValueId,
			String primaryKeyColumnName, String tableName)
		throws Exception {

		db.runSQL(
			StringBundler.concat(
				"delete from ", tableName, " where ", primaryKeyColumnName,
				" in (", maxValueId, ", ", oversizedValueId, ")"));

		_alterColumnType(getNewColumnLength(), columnName, tableName);
	}

	private void _testUpgrade(String columnName, String tableName)
		throws Exception {

		long maxValueId = RandomTestUtil.nextLong();
		long oversizedValueId = RandomTestUtil.nextLong();
		String primaryKeyColumnName = _getPrimaryKeyColumnName(tableName);

		try {
			List<IndexMetadata> indexMetadatas = _getIndexMetadatas(
				columnName, tableName);

			Assert.assertFalse(indexMetadatas.isEmpty());

			_alterColumnType(getOldColumnLength(), columnName, tableName);

			String maxValue = RandomTestUtil.randomString(getNewColumnLength());

			db.runSQL(
				getInsertSQL(
					columnName, maxValue, maxValueId, primaryKeyColumnName,
					tableName));

			String oversizedValue = RandomTestUtil.randomString(
				getNewColumnLength() + 1);

			db.runSQL(
				getInsertSQL(
					columnName, oversizedValue, oversizedValueId,
					primaryKeyColumnName, tableName));

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				getUpgradeStepRegistrator(), getUpgradeProcessClassName());

			upgradeProcess.upgrade();

			try (Connection connection = DataAccess.getConnection()) {
				DBInspector dbInspector = new DBInspector(connection);

				Assert.assertTrue(
					dbInspector.hasColumnType(
						tableName, columnName,
						StringBundler.concat(
							"VARCHAR(", getNewColumnLength(), ") null")));

				for (IndexMetadata indexMetadata : indexMetadatas) {
					Assert.assertTrue(
						dbInspector.hasIndex(
							tableName, indexMetadata.getIndexName()));
				}
			}

			_assertColumnValue(
				columnName, maxValue, maxValueId, primaryKeyColumnName,
				tableName);
			_assertColumnValue(
				columnName, oversizedValue.substring(0, getNewColumnLength()),
				oversizedValueId, primaryKeyColumnName, tableName);
		}
		finally {
			_restore(
				columnName, maxValueId, oversizedValueId, primaryKeyColumnName,
				tableName);
		}
	}

	private void _testUpgradeWithDuplicateUniqueIndexEntries(
			String columnName, String tableName)
		throws Exception {

		long maxValueId = RandomTestUtil.nextLong();
		long oversizedValueId = RandomTestUtil.nextLong();
		String primaryKeyColumnName = _getPrimaryKeyColumnName(tableName);

		try {
			_alterColumnType(getOldColumnLength(), columnName, tableName);

			String maxValue = RandomTestUtil.randomString(getNewColumnLength());

			db.runSQL(
				getInsertSQL(
					columnName, maxValue, maxValueId, primaryKeyColumnName,
					tableName));
			db.runSQL(
				getInsertSQL(
					columnName, maxValue + "x", oversizedValueId,
					primaryKeyColumnName, tableName));

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				getUpgradeStepRegistrator(), getUpgradeProcessClassName());

			Assert.assertThrows(
				UpgradeException.class, upgradeProcess::upgrade);
		}
		finally {
			_restore(
				columnName, maxValueId, oversizedValueId, primaryKeyColumnName,
				tableName);
		}
	}

}