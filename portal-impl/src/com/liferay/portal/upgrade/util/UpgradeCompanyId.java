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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class UpgradeCompanyId extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (TableUpdater tableUpdater : getTableUpdaters()) {
			if (tableHasColumn(tableUpdater.getTableName(), "companyId")) {
				if (_log.isInfoEnabled()) {
					_log.info("Skipping table " + tableUpdater.getTableName());
				}

				continue;
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Adding column companyId to table " +
						tableUpdater.getTableName());
			}

			runSQL(
				"alter table " + tableUpdater.getTableName() +
					" add companyId LONG");

			tableUpdater.update();
		}
	}

	protected abstract TableUpdater[] getTableUpdaters();

	protected class TableUpdater {

		public TableUpdater(
			String tableName, String foreignTableName,
			String foreignColumnName) {

			_tableName = tableName;

			_columnName = foreignColumnName;

			_foreignNamesArray = new String[][] {
				new String[] {foreignTableName, foreignColumnName
				}};
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

		public void update() throws Exception {
			for (String[] foreignNames : _foreignNamesArray) {
				String select = getSelectSQL(foreignNames[0], foreignNames[1]);
				String update = getUpdateSQL();

				update(select, update);
			}
		}

		protected String getSelectSQL(
			String foreignTableName, String foreignColumnName) {

			StringBundler sb = new StringBundler(10);

			sb.append("select ");
			sb.append(foreignTableName);
			sb.append(".");
			sb.append("companyId, ");
			sb.append(_tableName);
			sb.append(".");
			sb.append(_columnName);
			sb.append(" from ");
			sb.append(foreignTableName);
			sb.append(", ");
			sb.append(_tableName);
			sb.append(" where ");
			sb.append(foreignTableName);
			sb.append(".");
			sb.append(foreignColumnName);
			sb.append(" = ");
			sb.append(_tableName);
			sb.append(".");
			sb.append(_columnName);

			return sb.toString();
		}

		protected String getUpdateSQL() {
			StringBundler sb = new StringBundler(5);

			sb.append("update ");
			sb.append(_tableName);
			sb.append(" set companyId = ?");
			sb.append(" where ");
			sb.append(_columnName);
			sb.append("=?");

			return sb.toString();
		}

		protected void update(String select, String update) throws Exception {
			Connection con = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			ResultSet rs = null;

			try {
				con = DataAccess.getUpgradeOptimizedConnection();

				DatabaseMetaData databaseMetaData = con.getMetaData();

				boolean supportsBatchUpdates =
					databaseMetaData.supportsBatchUpdates();

				ps = con.prepareStatement(select);

				rs = ps.executeQuery();

				ps2 = con.prepareStatement(update);

				int count = 0;

				while (rs.next()) {
					ps2.setLong(1, rs.getLong("companyId"));
					ps2.setLong(2, rs.getLong(_columnName));

					if (supportsBatchUpdates) {
						ps2.addBatch();

						int batchSize = GetterUtil.getInteger(
							PropsUtil.get(PropsKeys.HIBERNATE_JDBC_BATCH_SIZE));

						if (count == batchSize) {
							ps2.executeBatch();

							count = 0;
						}
						else {
							count++;
						}
					}
					else {
						ps2.executeUpdate();
					}

					if (supportsBatchUpdates && (count > 0)) {
						ps.executeBatch();
					}
				}
			}
			catch (SQLException sqle) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"The companyId was not updated in " + _tableName, sqle);
				}
			}
			finally {
				DataAccess.cleanUp(con, ps2);
				DataAccess.cleanUp(con, ps, rs);
			}
		}

		private final String _columnName;
		private final String[][] _foreignNamesArray;
		private final String _tableName;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeCompanyId.class);

}