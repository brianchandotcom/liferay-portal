/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
		processConcurrently(
			getTableUpdaters(),
			tableUpdater -> {
				String tableName = tableUpdater.getTableName();

				try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
					if (!hasColumn(tableName, "companyId")) {
						if (_log.isInfoEnabled()) {
							_log.info(
								"Adding column companyId to table " +
									tableName);
						}

						runSQL(
							connection,
							"alter table " + tableName + " add companyId LONG");
					}
					else {
						if (_log.isInfoEnabled()) {
							_log.info(
								"Skipping the creation of companyId column " +
									"for table " + tableName);
						}
					}

					tableUpdater.update(connection);
				}
			},
			null);
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

		protected List<Long> getCompanyIds(Connection connection)
			throws SQLException {

			List<Long> companyIds = new ArrayList<>();

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select companyId from Company");
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					long companyId = resultSet.getLong(1);

					companyIds.add(companyId);
				}
			}

			return companyIds;
		}

		protected String getSelectSQL(
				Connection connection, String foreignTableName,
				String foreignColumnName)
			throws SQLException {

			List<Long> companyIds = getCompanyIds(connection);

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

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCompanyIdUpgradeProcess.class);

}