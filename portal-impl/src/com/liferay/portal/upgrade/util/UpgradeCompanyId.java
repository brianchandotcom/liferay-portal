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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;

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

		public void update() throws IOException, SQLException {
			for (String[] foreignNames : _foreignNamesArray) {
				runSQL(getUpdateSQL(foreignNames[0], foreignNames[1]));
			}
		}

		protected String getSelectSQL(
			String foreignTableName, String foreignColumnName) {

			StringBundler sb = new StringBundler(10);

			sb.append("select max(companyId) from ");
			sb.append(foreignTableName);
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

		protected String getUpdateSQL(String selectSQL) {
			StringBundler sb = new StringBundler(5);

			sb.append("update ");
			sb.append(_tableName);
			sb.append(" set companyId = (");
			sb.append(selectSQL);
			sb.append(")");

			return sb.toString();
		}

		protected String getUpdateSQL(
			String foreignTableName, String foreignColumnName) {

			return getUpdateSQL(
				getSelectSQL(foreignTableName, foreignColumnName));
		}

		private final String _columnName;
		private final String[][] _foreignNamesArray;
		private final String _tableName;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeCompanyId.class);

}