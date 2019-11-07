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
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Preston Crary
 */
public class UpgradeCTModel extends UpgradeProcess {

	public UpgradeCTModel(String tableName) {
		_tableName = tableName;
	}

	@Override
	protected void doUpgrade() throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		String tableName = dbInspector.normalizeName(
			_tableName, databaseMetaData);

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

		runSQL(
			StringBundler.concat(
				"alter table ", tableName,
				" add ctCollectionId LONG default 0 not null"));

		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == DBType.SYBASE) {
			String primaryKeyConstraintName = null;

			try (PreparedStatement ps = connection.prepareStatement(
					"sp_helpconstraint " + tableName);
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					String definition = rs.getString("definition");

					if (definition.startsWith("PRIMARY KEY INDEX")) {
						primaryKeyConstraintName = rs.getString("name");

						break;
					}
				}
			}

			if (primaryKeyConstraintName == null) {
				throw new UpgradeException(
					"No primary key constraint found for " + tableName);
			}

			runSQL(
				StringBundler.concat(
					"sp_dropkey primary ", tableName, " ",
					primaryKeyConstraintName));
		}
		else {
			runSQL(
				StringBundler.concat(
					"alter table ", tableName, " drop primary key"));
		}

		runSQL(
			StringBundler.concat(
				"alter table ", tableName, " add primary key (",
				primaryKeyColumnName, ", ctCollectionId)"));
	}

	private final String _tableName;

}