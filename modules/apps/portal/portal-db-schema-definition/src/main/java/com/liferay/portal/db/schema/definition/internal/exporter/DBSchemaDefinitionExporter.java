/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.exporter;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.db.schema.definition.internal.configuration.DBSchemaDefinitionExporterConfiguration;
import com.liferay.portal.db.schema.definition.internal.processor.DBSchemaToFilesProcessor;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;

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
		_generateSchema(properties);
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

	private void _generateSchema(Map<String, Object> properties) {
		if (_log.isInfoEnabled()) {
			_log.info("Starting schema generation");
		}

		try {
			DBSchemaDefinitionExporterConfiguration
				dbSchemaDefinitionExporterConfiguration =
					ConfigurableUtil.createConfigurable(
						DBSchemaDefinitionExporterConfiguration.class,
						properties);

			new DBSchemaToFilesProcessor(
				DBType.valueOf(
					StringUtil.toUpperCase(
						dbSchemaDefinitionExporterConfiguration.databaseType()))
			).processTo(
				dbSchemaDefinitionExporterConfiguration.path()
			);

			if (_log.isInfoEnabled()) {
				_log.info("Schema generation finished");
			}
		}
		catch (Exception exception) {
			_log.error("Unable to perform schema generation", exception);
		}
		finally {
			_deleteConfiguration((String)properties.get("service.pid"));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DBSchemaDefinitionExporter.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}