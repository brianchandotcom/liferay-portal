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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Preston Crary
 * @author Alberto Chaparro
 */
public class UpgradePrimaryKey extends UpgradeProcess {

	public UpgradePrimaryKey(
		String newPKColumnDefinition, String... tableNames) {

		_newPKColumnDefinition = newPKColumnDefinition;

		if (tableNames.length == 0) {
			throw new IllegalArgumentException("Table names is empty");
		}

		_tableNames = tableNames;

		_newPKColumnName = StringUtil.extractFirst(
			newPKColumnDefinition, CharPool.SPACE);
	}

	@Override
	protected void doUpgrade() throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		for (String tableName : _tableNames) {
			try (LoggingTimer loggingTimer = new LoggingTimer(
					UpgradePrimaryKey.class, tableName)) {

				_upgradePrimaryKey(databaseMetaData, dbInspector, tableName);
			}
		}
	}

	protected String getAlterPrimaryKeySQL(
		String tableName, List<String> primaryKeyColumnNames) {

		boolean addCompanyId = false;

		String alterSQL = "alter table " + tableName + " add primary key (";

		for (String primaryKeyColumnName : primaryKeyColumnNames) {
			if (primaryKeyColumnName.equals("companyId")) {
				addCompanyId = true;

				continue;
			}

			alterSQL += primaryKeyColumnName + ", ";
		}

		alterSQL += _newPKColumnName;

		if (addCompanyId) {
			alterSQL += ", companyId";
		}

		return alterSQL += ")";
	}

	private void _upgradePrimaryKey(
			DatabaseMetaData databaseMetaData, DBInspector dbInspector,
			String tableName)
		throws Exception {

		String normalizedTableName = dbInspector.normalizeName(
			tableName, databaseMetaData);

		List<String> primaryKeyColumnNames = new ArrayList<>();

		try (ResultSet rs = databaseMetaData.getPrimaryKeys(
				dbInspector.getCatalog(), dbInspector.getSchema(),
				normalizedTableName)) {

			while (rs.next()) {
				primaryKeyColumnNames.add(rs.getString("COLUMN_NAME"));
			}
		}

		if (primaryKeyColumnNames.isEmpty()) {
			throw new UpgradeException(
				"No primary key column found for " + normalizedTableName);
		}

		if (primaryKeyColumnNames.contains(
				dbInspector.normalizeName(
					_newPKColumnName, databaseMetaData))) {

			return;
		}

		if (!hasColumn(normalizedTableName, _newPKColumnName)) {
			runSQL(
				StringBundler.concat(
					"alter table ", normalizedTableName, " add ",
					_newPKColumnDefinition));
		}

		DB db = DBManagerUtil.getDB();

		DBType dbType = db.getDBType();

		if ((dbType == DBType.SQLSERVER) || (dbType == DBType.SYBASE)) {
			String primaryKeyConstraintName = null;

			if (dbType == DBType.SQLSERVER) {
				try (PreparedStatement ps = connection.prepareStatement(
						StringBundler.concat(
							"select name from sys.key_constraints where type ",
							"= 'PK' and OBJECT_NAME(parent_object_id) = '",
							normalizedTableName, "'"));
					ResultSet rs = ps.executeQuery()) {

					if (rs.next()) {
						primaryKeyConstraintName = rs.getString("name");
					}
				}
			}
			else {
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
			}

			if (primaryKeyConstraintName == null) {
				throw new UpgradeException(
					"No primary key constraint found for " +
						normalizedTableName);
			}

			runSQL(
				StringBundler.concat(
					"alter table ", normalizedTableName, " drop constraint ",
					primaryKeyConstraintName));
		}
		else {
			runSQL(
				StringBundler.concat(
					"alter table ", normalizedTableName, " drop primary key"));
		}

		runSQL(
			getAlterPrimaryKeySQL(normalizedTableName, primaryKeyColumnNames));
	}

	private final String _newPKColumnDefinition;
	private final String _newPKColumnName;
	private final String[] _tableNames;

}