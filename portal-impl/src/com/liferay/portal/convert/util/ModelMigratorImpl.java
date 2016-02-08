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

package com.liferay.portal.convert.util;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.service.ServiceComponentLocalServiceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.spring.hibernate.DialectDetector;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.util.MaintenanceUtil;

import java.io.IOException;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.hibernate.dialect.Dialect;

/**
 * @author Cristina González
 * @author Miguel Pastor
 */
public class ModelMigratorImpl implements ModelMigrator {

	@Override
	public void migrate(
			DataSource dataSource, List<Class<? extends BaseModel<?>>> models)
		throws IOException, SQLException {

		Dialect dialect = DialectDetector.getDialect(dataSource);

		Connection connection = dataSource.getConnection();

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Migrating database tables");
			}

			Map<String, Tuple> modelTableDetails = new HashMap<>();

			for (Class<? extends BaseModel<?>> model : models) {
				modelTableDetails.putAll(getModelTableDetails(model));
			}

			MaintenanceUtil.appendStatus(
				"Processing " + modelTableDetails.size() + " models");

			migrateModel(
				modelTableDetails, DBManagerUtil.getDB(dialect, dataSource),
				connection);
		}
		finally {
			DataAccess.cleanUp(connection);
		}
	}

	protected Map<String, Tuple> getModelTableDetails(Class<?> implClass) {
		Map<String, Tuple> modelTableDetails = new LinkedHashMap<>();

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

				modelTableDetails.put(table, tuple);
			}
		}

		return modelTableDetails;
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

	protected void migrateModel(
			Map<String, Tuple> modelTableDetails, DB db, Connection connection)
		throws IOException {

		MaintenanceUtil.appendStatus("<ul>");

		for (Tuple tuple : modelTableDetails.values()) {
			String table = (String)tuple.getObject(0);
			Object[][] columns = (Object[][])tuple.getObject(1);
			String sqlCreate = (String)tuple.getObject(2);

			MaintenanceUtil.appendStatus(
				"<li>Migrating table " + table + "</li>");

			migrateTable(db, connection, table, columns, sqlCreate);
		}

		MaintenanceUtil.appendStatus("</ul>");

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

	protected void migrateTable(
		DB db, Connection connection, String tableName, Object[][] columns,
		String sqlCreate) {

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
		ModelMigratorImpl.class);

}