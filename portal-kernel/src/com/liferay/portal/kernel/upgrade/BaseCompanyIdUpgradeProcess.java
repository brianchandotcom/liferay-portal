/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseCompanyIdUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == DBType.SQLSERVER) {
			for (TableUpdater tableUpdater : getTableUpdaters()) {
				_addCompanyIdColumn(tableUpdater);
			}
		}
		else {
			processConcurrently(
				getTableUpdaters(),
				tableUpdater -> _addCompanyIdColumn(tableUpdater), null);
		}
	}

	protected abstract TableUpdater[] getTableUpdaters();

	protected class TableUpdater {

		public TableUpdater(
			String tableName, String foreignTableName, String columnName) {

			_tableName = tableName;
			_columnName = columnName;

			_foreignNamesArray = new String[][] {
				{foreignTableName, columnName}
			};
		}

		public TableUpdater(
			String tableName, String columnName, String[][] foreignNamesArray) {

			_tableName = tableName;
			_columnName = columnName;
			_foreignNamesArray = foreignNamesArray;
		}

		public String getTableName() {
			return _tableName;
		}

		public void setCreateCompanyIdColumn(boolean createCompanyIdColumn) {
			_createCompanyIdColumn = createCompanyIdColumn;
		}

		public void update(Connection connection) throws Exception {
			for (String[] foreignNames : _foreignNamesArray) {
				runSQL(
					connection,
					getUpdateSQL(connection, foreignNames[0], foreignNames[1]));
			}
		}

		protected String getSelectSQL(
				Connection connection, String foreignTableName,
				String foreignColumnName)
			throws SQLException {

			List<Long> companyIds = new ArrayList<>();

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select companyId from Company");
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					companyIds.add(resultSet.getLong(1));
				}
			}

			if (companyIds.size() == 1) {
				return String.valueOf(companyIds.get(0));
			}

			return StringBundler.concat(
				"select max(companyId) from ", foreignTableName, " where ",
				foreignTableName, ".", foreignColumnName, " > 0 and ",
				foreignTableName, ".", foreignColumnName, " = ", _tableName,
				".", _columnName);
		}

		protected String getUpdateSQL(
				Connection connection, String foreignTableName,
				String foreignColumnName)
			throws SQLException {

			return getUpdateSQL(
				getSelectSQL(connection, foreignTableName, foreignColumnName));
		}

		protected String getUpdateSQL(String selectSQL) {
			return StringBundler.concat(
				"update ", _tableName, " set companyId = (", selectSQL, ")");
		}

		private final String _columnName;
		private boolean _createCompanyIdColumn;
		private final String[][] _foreignNamesArray;
		private final String _tableName;

	}

	private void _addCompanyIdColumn(TableUpdater tableUpdater)
		throws Exception {

		String tableName = tableUpdater.getTableName();

		try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
			alterTableAddColumn(tableName, "companyId", "LONG");

			tableUpdater.update(connection);
		}
	}

}