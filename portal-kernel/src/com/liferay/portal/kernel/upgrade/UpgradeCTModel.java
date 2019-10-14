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
import com.liferay.portal.kernel.dao.db.DBInspector;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.Collection;

/**
 * @author Preston Crary
 */
public class UpgradeCTModel extends UpgradeProcess {

	public UpgradeCTModel(Class<?> tableClass) {
		_tableClass = tableClass;
	}

	@Override
	protected void doUpgrade() throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		String tableName = dbInspector.normalizeName(
			getTableName(_tableClass), databaseMetaData);

		try (ResultSet rs = databaseMetaData.getColumns(
				dbInspector.getCatalog(), dbInspector.getSchema(), tableName,
				dbInspector.normalizeName(
					"ctCollectionId", databaseMetaData))) {

			if (rs.next()) {
				return;
			}
		}

		String primaryKeyColumnName = null;

		try (ResultSet rs = databaseMetaData.getPrimaryKeys(
				dbInspector.getCatalog(), dbInspector.getSchema(), tableName)) {

			if (rs.next()) {
				primaryKeyColumnName = rs.getString("COLUMN_NAME");
			}

			if (rs.next()) {
				throw new UpgradeException(
					"Single column primary key is required to upgrade " +
						tableName);
			}
		}

		if (primaryKeyColumnName == null) {
			throw new UpgradeException(
				"No primary key column found for " + tableName);
		}

		alter(
			_tableClass, new AlterTableAddCTCollectionIdColumn(),
			new DropPrimaryKeyAlterable(),
			new AddPrimaryKeyAlterable(primaryKeyColumnName));
	}

	private final Class<?> _tableClass;

	private static class AddPrimaryKeyAlterable
		extends BasePrimaryKeyAlterable {

		@Override
		public String getSQL(String tableName) {
			return StringBundler.concat(
				"alter table ", tableName, " add primary key (",
				_primaryKeyColumnName, ", ctCollectionId)");
		}

		private AddPrimaryKeyAlterable(String primaryKeyColumnName) {
			_primaryKeyColumnName = primaryKeyColumnName;
		}

		private final String _primaryKeyColumnName;

	}

	private abstract static class BasePrimaryKeyAlterable implements Alterable {

		@Override
		public boolean shouldAddIndex(Collection<String> columnNames) {
			return false;
		}

		@Override
		public boolean shouldDropIndex(Collection<String> columnNames) {
			return false;
		}

	}

	private static class DropPrimaryKeyAlterable
		extends BasePrimaryKeyAlterable {

		@Override
		public String getSQL(String tableName) {
			return StringBundler.concat(
				"alter table ", tableName, " drop primary key");
		}

	}

	private class AlterTableAddCTCollectionIdColumn
		extends AlterTableAddColumn {

		@Override
		public String getSQL(String tableName) {
			return StringBundler.concat(
				"alter table ", tableName,
				" add ctCollectionId LONG default 0 not null");
		}

		private AlterTableAddCTCollectionIdColumn() {
			super("ctCollectionId");
		}

	}

}