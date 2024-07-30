/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.exporter;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.db.schema.definition.internal.configuration.DBSchemaDefinitionExporterConfiguration;
import com.liferay.portal.db.schema.definition.internal.partition.DBSchemaPartitionUtil;
import com.liferay.portal.db.schema.definition.internal.sql.writer.SQLWriter;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.patcher.PatcherValues;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.sql.DataSource;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	configurationPid = "com.liferay.portal.db.schema.definition.internal.configuration.DBSchemaDefinitionExporterConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class DBSchemaDefinitionExporter {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_exportDBSchemaDefinition(properties);
	}

	private void _deleteConfiguration(String pid) {
		try {
			Path path = Paths.get(
				PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
				pid.concat(".config"));

			if (Files.exists(path)) {
				Files.delete(path);
			}
			else {
				Configuration[] configurations =
					_configurationAdmin.listConfigurations(
						StringBundler.concat(
							"(", Constants.SERVICE_PID, StringPool.EQUAL, pid,
							"*)"));

				if (configurations == null) {
					return;
				}

				for (Configuration configuration : configurations) {
					configuration.delete();
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _exportDBSchemaDefinition(Map<String, Object> properties) {
		if (_log.isInfoEnabled()) {
			_log.info("Start database schema definition export");
		}

		try {
			DBSchemaDefinitionExporterConfiguration
				dbSchemaDefinitionExporterConfiguration =
					ConfigurableUtil.createConfigurable(
						DBSchemaDefinitionExporterConfiguration.class,
						properties);

			DBType dbType = DBType.valueOf(
				StringUtil.toUpperCase(
					dbSchemaDefinitionExporterConfiguration.databaseType()));

			SQLWriter sqlWriter = new SQLWriter(dbType);

			File file = new File(
				dbSchemaDefinitionExporterConfiguration.path());

			sqlWriter.writeFiles(file);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Finished database schema definition export to " +
						file.getAbsolutePath());
			}

			_generateReport(
				dbSchemaDefinitionExporterConfiguration.path(), dbType);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to export database schema definition", exception);
		}
		finally {
			_deleteConfiguration((String)properties.get("service.pid"));
		}
	}

	private void _generateReport(String dirName, DBType exportDBType)
		throws Exception {

		String installedPatchNames = StringUtil.merge(
			PatcherValues.INSTALLED_PATCH_NAMES, StringPool.COMMA_AND_SPACE);
		Release release = _releaseLocalService.fetchRelease(
			ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

		FileUtil.write(
			new File(dirName, "db_schema_definition_export_report.info"),
			StringUtil.merge(
				new Object[] {
					"Export date: " + _toString(new Date()),
					"Portal build date: " + _toString(release.getBuildDate()),
					"Portal build number: " + release.getBuildNumber(),
					"Portal installed patches: " + installedPatchNames,
					"Portal schema version: " + release.getSchemaVersion(),
					StringPool.NEW_LINE,
					"Database type: " + DBManagerUtil.getDBType(),
					"Export database type: " + exportDBType,
					StringPool.NEW_LINE, _printCompanyTablesInfo(dirName)
				},
				StringPool.NEW_LINE));
	}

	private Set<String> _getDBTableNames(long companyId) throws Exception {
		return _getDBTableNamesByType(companyId, "TABLE");
	}

	private Set<String> _getDBTableNamesByType(long companyId, String type)
		throws Exception {

		Set<String> tableNames = new HashSet<>();

		DataSource dataSource = InfrastructureUtil.getDataSource();

		try (Connection connection = dataSource.getConnection()) {
			DBSchemaPartitionUtil.setPartition(connection, companyId);

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			try (ResultSet resultSet = databaseMetaData.getTables(
					connection.getCatalog(), connection.getSchema(), null,
					new String[] {type})) {

				while (resultSet.next()) {
					tableNames.add(
						StringUtil.toLowerCase(
							resultSet.getString("TABLE_NAME")));
				}
			}
		}

		return tableNames;
	}

	private Set<String> _getExportTableNames(String dirName, long companyId)
		throws Exception {

		return _getExportTableNames(
			dirName, companyId, "create table",
			line -> line.split(StringPool.SPACE)[2]);
	}

	private Set<String> _getExportTableNames(
			String dirName, long companyId, String filter,
			Function<String, String> function)
		throws Exception {

		Set<String> tableNames = new HashSet<>();

		String prefix = StringPool.BLANK;

		if (PortalInstancePool.getDefaultCompanyId() != companyId) {
			prefix = companyId + StringPool.UNDERLINE;
		}

		String fileContent = StringUtil.toLowerCase(
			FileUtil.read(new File(dirName, prefix + "tables.sql")));

		String[] lines = StringUtil.split(fileContent, StringPool.NEW_LINE);

		for (String line : lines) {
			if (StringUtil.startsWith(line, filter)) {
				tableNames.add(function.apply(line));
			}
		}

		return tableNames;
	}

	private String _printCompanyTablesInfo(String dirName) throws Exception {
		StringBundler sb = new StringBundler(
			_printDefaultCompanyTablesInfo(dirName));

		return sb.toString();
	}

	private String _printDefaultCompanyTablesInfo(String dirName)
		throws Exception {

		Set<String> dbTableNames = _getDBTableNames(
			PortalInstancePool.getDefaultCompanyId());
		Set<String> exportTableNames = _getExportTableNames(
			dirName, PortalInstancePool.getDefaultCompanyId());

		String missingTableNames = StringUtil.merge(
			SetUtil.asymmetricDifference(dbTableNames, exportTableNames),
			StringPool.COMMA_AND_SPACE);

		return StringUtil.merge(
			new Object[] {
				"Database tables: " + dbTableNames.size(),
				"Export tables: " + exportTableNames.size(),
				StringPool.NEW_LINE, "Missing tables: " + missingTableNames
			},
			StringPool.NEW_LINE);
	}

	private String _toString(Date date) {
		return Time.getSimpleDate(date, DateUtil.ISO_8601_PATTERN);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DBSchemaDefinitionExporter.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ReleaseLocalService _releaseLocalService;

}