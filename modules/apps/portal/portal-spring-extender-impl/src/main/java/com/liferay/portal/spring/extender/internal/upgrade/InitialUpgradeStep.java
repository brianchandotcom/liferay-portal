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

package com.liferay.portal.spring.extender.internal.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBContext;
import com.liferay.portal.kernel.dao.db.DBManager;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.hibernate.DialectDetector;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Dictionary;

import javax.sql.DataSource;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Shuyang Zhou
 */
public class InitialUpgradeStep implements UpgradeStep {

	public InitialUpgradeStep(Bundle bundle, DataSource dataSource) {
		_bundle = bundle;
		_dataSource = dataSource;
	}

	public Dictionary<String, Object> buildServiceProperties() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		Configuration configuration = ConfigurationFactoryUtil.getConfiguration(
			bundleWiring.getClassLoader(), "service");

		if (configuration != null) {
			String buildNumber = configuration.get("build.number");

			if (buildNumber != null) {
				properties.put("build.number", buildNumber);
			}
		}

		properties.put("upgrade.initial.database.creation", "true");

		properties.put(
			"upgrade.bundle.symbolic.name", _bundle.getSymbolicName());
		properties.put("upgrade.from.schema.version", "0.0.0");

		Dictionary<String, String> headers = _bundle.getHeaders(
			StringPool.BLANK);

		String upgradeToSchemaVersion = GetterUtil.getString(
			headers.get("Liferay-Require-SchemaVersion"),
			headers.get("Bundle-Version"));

		properties.put("upgrade.to.schema.version", upgradeToSchemaVersion);

		return properties;
	}

	@Override
	public String toString() {
		return "Initial Database Creation";
	}

	@Override
	public void upgrade(DBProcessContext dbProcessContext)
		throws UpgradeException {

		DBContext dbContext = dbProcessContext.getDBContext();

		DBManager dbManager = dbContext.getDBManager();

		_db = dbManager.getDB(
			dbManager.getDBType(DialectDetector.getDialect(_dataSource)),
			_dataSource);

		try {
			_db.process(
				companyId -> {
					if (_log.isInfoEnabled() &&
						Validator.isNotNull(companyId)) {

						_log.info(
							StringBundler.concat(
								toString(), StringPool.SPACE,
								_bundle.getSymbolicName(), "#", companyId));
					}

					_upgrade();
				});
		}
		catch (Exception exception) {
			throw new UpgradeException(exception);
		}
	}

	private void _upgrade() throws UpgradeException {
		String indexesSQL = DBResourceUtil.getModuleIndexesSQL(_bundle);
		String sequencesSQL = DBResourceUtil.getModuleSequencesSQL(_bundle);
		String tablesSQL = DBResourceUtil.getModuleTablesSQL(_bundle);

		try (Connection connection = _dataSource.getConnection()) {
			if (tablesSQL != null) {
				try {
					_db.runSQLTemplateString(connection, tablesSQL, true);
				}
				catch (Exception exception) {
					throw new UpgradeException(
						StringBundler.concat(
							"Bundle ", _bundle,
							" has invalid content in tables.sql:\n", tablesSQL),
						exception);
				}
			}

			if (sequencesSQL != null) {
				try {
					_db.runSQLTemplateString(connection, sequencesSQL, true);
				}
				catch (Exception exception) {
					throw new UpgradeException(
						StringBundler.concat(
							"Bundle ", _bundle,
							" has invalid content in sequences.sql:\n",
							sequencesSQL),
						exception);
				}
			}

			if (indexesSQL != null) {
				try {
					_db.runSQLTemplateString(connection, indexesSQL, true);
				}
				catch (Exception exception) {
					throw new UpgradeException(
						StringBundler.concat(
							"Bundle ", _bundle,
							" has invalid content in indexes.sql:\n",
							indexesSQL),
						exception);
				}
			}
		}
		catch (SQLException sqlException) {
			throw new UpgradeException(sqlException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InitialUpgradeStep.class);

	private final Bundle _bundle;
	private final DataSource _dataSource;
	private DB _db;

}