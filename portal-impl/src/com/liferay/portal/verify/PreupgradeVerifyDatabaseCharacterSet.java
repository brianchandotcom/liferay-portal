/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

		Set<String> tableNames = DBResourceUtil.getLiferayTableNames(
			connection);

		CompanyLocalServiceUtil.forEachCompanyId(
			companyId -> {
				try {
					tableNames.addAll(
						DBResourceUtil.getNonserviceBuilderTableNames(
							companyId));
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to get table names for company " + companyId,
						portalException);
				}
			});

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

				if (!tableNames.contains(tableName)) {
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

	private static final Log _log = LogFactoryUtil.getLog(
		PreupgradeVerifyDatabaseCharacterSet.class);

}