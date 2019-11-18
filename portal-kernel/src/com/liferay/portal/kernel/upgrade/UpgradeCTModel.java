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
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Preston Crary
 */
public class UpgradeCTModel extends UpgradeProcess {

	public UpgradeCTModel(String... tableNames) {
		if (tableNames.length == 0) {
			throw new IllegalArgumentException("Empty table names");
		}

		_tableNames = tableNames;
	}

	@Override
	protected void doUpgrade() throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		for (String tableName : _tableNames) {
			try (LoggingTimer loggingTimer = new LoggingTimer(
					UpgradeCTModel.class, tableName)) {

				_upgradeCTModel(databaseMetaData, dbInspector, tableName);
			}
		}
	}

	private void _upgradeCTModel(
			DatabaseMetaData databaseMetaData, DBInspector dbInspector,
			String tableName)
		throws Exception {

		String normalizedTableName = dbInspector.normalizeName(
			tableName, databaseMetaData);

		try (ResultSet rs = databaseMetaData.getColumns(
				dbInspector.getCatalog(), dbInspector.getSchema(),
				normalizedTableName,
				dbInspector.normalizeName(
					"ctCollectionId", databaseMetaData))) {

			if (rs.next()) {
				return;
			}
		}

		String primaryKeyColumnName = null;

		try (ResultSet rs = databaseMetaData.getPrimaryKeys(
				dbInspector.getCatalog(), dbInspector.getSchema(),
				normalizedTableName)) {

			if (rs.next()) {
				primaryKeyColumnName = rs.getString("COLUMN_NAME");
			}

			if (rs.next()) {
				throw new UpgradeException(
					"Single column primary key is required to upgrade " +
						normalizedTableName);
			}
		}

		if (primaryKeyColumnName == null) {
			throw new UpgradeException(
				"No primary key column found for " + normalizedTableName);
		}

		runSQL(
			StringBundler.concat(
				"alter table ", normalizedTableName,
				" add ctCollectionId LONG default 0 not null"));

		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == DBType.SYBASE) {
			String primaryKeyConstraintName = null;

			try (PreparedStatement ps = connection.prepareStatement(
					"sp_helpconstraint " + normalizedTableName);
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
					"No primary key constraint found for " +
						normalizedTableName);
			}

			runSQL(
				StringBundler.concat(
					"sp_dropkey primary ", normalizedTableName, " ",
					primaryKeyConstraintName));
		}
		else {
			runSQL(
				StringBundler.concat(
					"alter table ", normalizedTableName, " drop primary key"));
		}

		runSQL(
			StringBundler.concat(
				"alter table ", normalizedTableName, " add primary key (",
				primaryKeyColumnName, ", ctCollectionId)"));
	}

	private final String[] _tableNames;

}