/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.report;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.patcher.PatcherValues;
import com.liferay.portal.kernel.service.ReleaseLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.File;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBSchemaDefinitionExporterReport {

	public static void generateReport(DBType exportDBType, String path)
		throws Exception {

		Release release = ReleaseLocalServiceUtil.fetchRelease(
			ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

		String buildDate = Time.getSimpleDate(
			release.getBuildDate(), DateUtil.ISO_8601_PATTERN);

		Set<String> dbTableNames = _getDBTableNames();
		String exportDate = Time.getSimpleDate(
			new Date(), DateUtil.ISO_8601_PATTERN);
		Set<String> exportTableNames = _getExportTableNames(path);
		String installedPatches = StringUtil.merge(
			PatcherValues.INSTALLED_PATCH_NAMES, StringPool.COMMA_AND_SPACE);
		String missingTableNames = StringUtil.merge(
			SetUtil.asymmetricDifference(dbTableNames, exportTableNames),
			StringPool.COMMA_AND_SPACE);

		FileUtil.write(
			new File(path, "db_schema_definition_export_report.info"),
			StringUtil.merge(
				new Object[] {
					"Export date: " + exportDate,
					"Portal schema version: " + release.getSchemaVersion(),
					"Portal build number: " + release.getBuildNumber(),
					"Portal build date: " + buildDate,
					"Installed patches: " + installedPatches,
					"Database type: " + DBManagerUtil.getDBType(),
					"Export database type: " + exportDBType,
					"Database tables: " + dbTableNames.size(),
					"Export tables: " + exportTableNames.size(),
					"Missing tables: " + missingTableNames
				},
				StringPool.NEW_LINE));
	}

	private static Set<String> _getDBTableNames() throws Exception {
		Set<String> tableNames = new HashSet<>();

		DataSource dataSource = InfrastructureUtil.getDataSource();

		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			try (ResultSet resultSet = databaseMetaData.getTables(
					connection.getCatalog(), connection.getSchema(), null,
					new String[] {"TABLE"})) {

				while (resultSet.next()) {
					tableNames.add(
						StringUtil.toLowerCase(
							resultSet.getString("TABLE_NAME")));
				}
			}
		}

		return tableNames;
	}

	private static Set<String> _getExportTableNames(String path)
		throws Exception {

		Set<String> tableNames = new HashSet<>();

		String[] lines = StringUtil.split(
			StringUtil.toLowerCase(FileUtil.read(new File(path, "tables.sql"))),
			StringPool.NEW_LINE);

		for (String line : lines) {
			if (StringUtil.startsWith(line, "create table")) {
				String[] parts = line.split(StringPool.SPACE);

				tableNames.add(parts[2]);
			}
		}

		return tableNames;
	}

}