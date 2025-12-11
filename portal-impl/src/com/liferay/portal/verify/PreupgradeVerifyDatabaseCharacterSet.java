/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Jorge Avalos
 */
public class PreupgradeVerifyDatabaseCharacterSet
	extends PreupgradeVerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		DB db = DBManagerUtil.getDB();

		if (!db.isSupportsCharacterSet(connection)) {
			throw new VerifyException(
				"Unsupported database character set: " +
					db.getCharacterSet(connection));
		}

		if ((db.getDBType() != DBType.MARIADB) &&
			(db.getDBType() != DBType.MYSQL)) {

			return;
		}

		Set<String> tableNames =
			DBResourceUtil.getServiceComponentModuleTableNames(connection);

		tableNames.addAll(
			DBResourceUtil.getServiceComponentPortalTableNames(connection));
		tableNames.addAll(DBResourceUtil.getModuleTableNames(connection));
		tableNames.addAll(DBResourceUtil.getPortalTableNames(connection));

		DBInspector dbInspector = new DBInspector(connection);

		tableNames.addAll(_getObjectDefinitionDBTableNames(dbInspector));
		tableNames.addAll(_getObjectRelationshipDBTableNames(dbInspector));

		String sql = StringBundler.concat(
			"select distinct character_set_name, collation_name, table_name, ",
			"default_character_set_name, default_collation_name from ",
			"information_schema.columns join information_schema.schemata on ",
			"information_schema.columns.table_schema = ",
			"information_schema.schemata.schema_name where ",
			"information_schema.columns.table_schema = database() and ",
			"information_schema.columns.collation_name is not null and",
			"(information_schema.columns.character_set_name != ",
			"information_schema.schemata.default_character_set_name or ",
			"information_schema.columns.collation_name != ",
			"information_schema.schemata.default_collation_name)");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String tableName = resultSet.getString("table_name");

				if (!tableNames.contains(
						dbInspector.normalizeName(tableName))) {

					continue;
				}

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Mixed character set and collation: ", tableName,
							" has ", resultSet.getString("character_set_name"),
							" character set and ",
							resultSet.getString("collation_name"),
							" collation, but database has ",
							resultSet.getString("default_character_set_name"),
							" character set and ",
							resultSet.getString("default_collation_name"),
							" collation. Recommended character set is utf8mb4 ",
							"and recommended collation is ",
							"utf8mb4_unicode_ci."));
				}
			}
		}
	}

	@Override
	protected boolean isSkipDBPartitions() {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.MYSQL) {
			return true;
		}

		return false;
	}

	private List<String> _getObjectDefinitionDBTableNames(
			DBInspector dbInspector)
		throws Exception {

		if (!dbInspector.hasTable("ObjectDefinition")) {
			return Collections.emptyList();
		}

		boolean hasModifiableColumn = dbInspector.hasColumn(
			"ObjectDefinition", "modifiable");
		boolean hasSystemColumn = dbInspector.hasColumn(
			"ObjectDefinition", "system_");

		List<String> objectDefinitionDBTableNames = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select companyId, dbTableName",
					hasModifiableColumn ? ", modifiable" : "",
					hasSystemColumn ? ", system_" : "",
					" from ObjectDefinition where status = ?"))) {

			preparedStatement.setInt(1, WorkflowConstants.STATUS_APPROVED);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String dbTableName = resultSet.getString("dbTableName");

					objectDefinitionDBTableNames.add(
						dbInspector.normalizeName(dbTableName));

					objectDefinitionDBTableNames.add(
						dbInspector.normalizeName(dbTableName + "_l"));

					String extensionDBTableName = dbTableName + "_x";

					if ((!hasModifiableColumn ||
						 !resultSet.getBoolean("modifiable")) &&
						hasSystemColumn && resultSet.getBoolean("system_")) {

						if (dbTableName.endsWith("_")) {
							extensionDBTableName = dbTableName + "x_";
						}
						else {
							extensionDBTableName = dbTableName + "_x_";
						}

						extensionDBTableName += resultSet.getLong("companyId");
					}

					objectDefinitionDBTableNames.add(
						dbInspector.normalizeName(extensionDBTableName));
				}
			}
		}

		return objectDefinitionDBTableNames;
	}

	private List<String> _getObjectRelationshipDBTableNames(
			DBInspector dbInspector)
		throws Exception {

		if (!dbInspector.hasTable("ObjectRelationship")) {
			return Collections.emptyList();
		}

		List<String> objectRelationshipDBTableNames = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select dbTableName from ObjectRelationship where type_ = ?")) {

			preparedStatement.setString(1, "manyToMany");

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					objectRelationshipDBTableNames.add(
						dbInspector.normalizeName(
							resultSet.getString("dbTableName")));
				}
			}
		}

		return objectRelationshipDBTableNames;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PreupgradeVerifyDatabaseCharacterSet.class);

}