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

package com.liferay.portal.convert;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.ServiceComponent;
import com.liferay.portal.service.ServiceComponentLocalServiceUtil;
import com.liferay.portal.spring.hibernate.DialectDetector;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.ShutdownUtil;

import java.lang.reflect.Field;

import java.sql.Connection;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import javax.sql.DataSource;

import org.hibernate.dialect.Dialect;

/**
 * @author Alexander Chow
 */
public class ConvertDatabase extends BaseConvertProcess {

	@Override
	public String getDescription() {
		return "migrate-data-from-one-database-to-another";
	}

	@Override
	public String getParameterDescription() {
		return "please-enter-jdbc-information-for-new-database";
	}

	@Override
	public String[] getParameterNames() {
		return new String[] {
			"jdbc-driver-class-name", "jdbc-url", "jdbc-user-name",
			"jdbc-password"
		};
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	protected void doConvert() throws Exception {
		DataSource dataSource = getDataSource();

		Dialect dialect = DialectDetector.getDialect(dataSource);

		DB db = DBManagerUtil.getDB(dialect, dataSource);

		List<String> modelNames = ModelHintsUtil.getModels();

		Map<String, Tuple> tableDetails = new LinkedHashMap<>();

		Connection connection = dataSource.getConnection();

		try {
			MaintenanceUtil.appendStatus(
				"Collecting information for database tables to migration");

			for (String modelName : modelNames) {
				if (!modelName.contains(".model.")) {
					continue;
				}

				String implClassName = modelName.replaceFirst(
					"(\\.model\\.)(\\p{Upper}.*)", "$1impl.$2Impl");

				if (_log.isDebugEnabled()) {
					_log.debug("Loading class " + implClassName);
				}

				Class<?> implClass = getImplClass(implClassName);

				if (implClass == null) {
					_log.error("Unable to load class " + implClassName);

					continue;
				}

				Field[] fields = implClass.getFields();

				for (Field field : fields) {
					Tuple tuple = null;

					String fieldName = field.getName();

					if (fieldName.equals("TABLE_NAME") ||
						(fieldName.startsWith("MAPPING_TABLE_") &&
						 fieldName.endsWith("_NAME"))) {

						tuple = getTableDetails(implClass, field, fieldName);
					}

					if (tuple != null) {
						String table = (String)tuple.getObject(0);

						tableDetails.put(table, tuple);
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Migrating database tables");
			}

			int i = 0;

			for (Tuple tuple : tableDetails.values()) {
				if ((i > 0) && (i % (tableDetails.size() / 4) == 0)) {
					MaintenanceUtil.appendStatus(
						(i * 100 / tableDetails.size()) + "%");
				}

				String table = (String)tuple.getObject(0);
				Object[][] columns = (Object[][])tuple.getObject(1);
				String sqlCreate = (String)tuple.getObject(2);

				migrateTable(db, connection, table, columns, sqlCreate);

				i++;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Migrating database indexes");
			}

			StartupHelperUtil.updateIndexes(db, connection, false);

			List<ServiceComponent> serviceComponents =
				ServiceComponentLocalServiceUtil.getLatestServiceComponents();

			Set<String> validIndexNames = new HashSet<>();

			for (ServiceComponent serviceComponent : serviceComponents) {
				String indexesSQL = serviceComponent.getIndexesSQL();

				db.addIndexes(connection, indexesSQL, validIndexNames);
			}
		}
		finally {
			DataAccess.cleanUp(connection);
		}

		MaintenanceUtil.appendStatus(
			"Please change your JDBC settings before restarting server");

		ShutdownUtil.shutdown(0);
	}

	protected DataSource getDataSource() throws Exception {
		String[] values = getParameterValues();

		String driverClassName = values[0];
		String url = values[1];
		String userName = values[2];
		String password = values[3];
		String jndiName = StringPool.BLANK;

		return DataSourceFactoryUtil.initDataSource(
			driverClassName, url, userName, password, jndiName);
	}

	protected Class<?> getImplClass(String implClassName) throws Exception {
		try {
			ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

			return classLoader.loadClass(implClassName);
		}
		catch (Exception e) {
		}

		for (String servletContextName : ServletContextPool.keySet()) {
			try {
				ServletContext servletContext = ServletContextPool.get(
					servletContextName);

				ClassLoader classLoader = servletContext.getClassLoader();

				return classLoader.loadClass(implClassName);
			}
			catch (Exception e) {
			}
		}

		return null;
	}

	protected Tuple getTableDetails(
		Class<?> implClass, Field tableField, String tableFieldVar) {

		try {
			String columnsFieldVar = StringUtil.replace(
				tableFieldVar, "_NAME", "_COLUMNS");
			String sqlCreateFieldVar = StringUtil.replace(
				tableFieldVar, "_NAME", "_SQL_CREATE");

			Field columnsField = implClass.getField(columnsFieldVar);
			Field sqlCreateField = implClass.getField(sqlCreateFieldVar);

			String table = (String)tableField.get(StringPool.BLANK);
			Object[][] columns = (Object[][])columnsField.get(new Object[0][0]);
			String sqlCreate = (String)sqlCreateField.get(StringPool.BLANK);

			return new Tuple(table, columns, sqlCreate);
		}
		catch (Exception e) {
		}

		return null;
	}

	protected void migrateTable(
			DB db, Connection connection, String tableName, Object[][] columns,
			String sqlCreate)
		throws Exception {

		Table table = new Table(tableName, columns);

		try {
			table.generateTempFile();

			db.runSQL(connection, sqlCreate);

			table.populateTable(connection);
		}
		catch (Exception e) {
			_log.error(e, e);

			MaintenanceUtil.appendStatus(e.getMessage());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConvertDatabase.class);

}