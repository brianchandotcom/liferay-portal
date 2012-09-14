/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.jpa;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import org.springframework.orm.jpa.vendor.Database;

/**
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
public class DatabaseDetector {

	public static Database determineDatabase(DataSource dataSource) {
		Database database = null;
		String type = null;

		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			String dbName = databaseMetaData.getDatabaseProductName();
			int dbMajorVersion = databaseMetaData.getDatabaseMajorVersion();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Determining DB type for " + dbName + " " + dbMajorVersion);
			}

			if (dbName.equals("Apache Derby")) {
				database = Database.DERBY;
				type = DB.TYPE_DERBY;
			}
			else if (dbName.startsWith("DB2/")) {
				database = Database.DB2;
				type = DB.TYPE_DB2;
			}
			else if (dbName.equals("HSQL Database Engine")) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(6);

					sb.append("Liferay is configured to use Hypersonic as ");
					sb.append("its database. Do NOT use Hypersonic in ");
					sb.append("production. Hypersonic is an embedded ");
					sb.append("database useful for development and demo'ing ");
					sb.append("purposes. The database settings can be ");
					sb.append("changed in portal-ext.properties.");

					_log.warn(sb.toString());
				}

				database = Database.HSQL;
				type = DB.TYPE_HYPERSONIC;
			}
			else if (dbName.equals("Informix Dynamic Server")) {
				database = Database.INFORMIX;
				type = DB.TYPE_INFORMIX;
			}
			else if (dbName.startsWith("Microsoft SQL Server")) {
				database = Database.SQL_SERVER;
				type = DB.TYPE_SQLSERVER;
			}
			else if (dbName.equals("MySQL")) {
				database = Database.MYSQL;
				type = DB.TYPE_MYSQL;
			}
			else if (dbName.equals("Oracle")) {
				database = Database.ORACLE;
				type = DB.TYPE_ORACLE;
			}
			else if (dbName.equals("PostgreSQL")) {
				database = Database.POSTGRESQL;
				type = DB.TYPE_POSTGRESQL;
			}
			else if (dbName.equals("Sybase SQL Server")) {
				database = Database.SYBASE;
				type = DB.TYPE_SYBASE;
			}

			if (dbName.equals("ASE") && (dbMajorVersion == 15)) {
				database = Database.SYBASE;
				type = DB.TYPE_SYBASE;
			}
		}
		catch (Exception e) {
			String msg = GetterUtil.getString(e.getMessage());

			if (msg.contains("explicitly set for database: DB2")) {
				database = Database.DB2;

				type = DB.TYPE_DB2;
			}
			else {
				_log.error(e, e);
			}
		}
		finally {
			DataAccess.cleanUp(connection);
		}

		if (database == null) {
			throw new RuntimeException("Unable to detect the database");
		}

		if (_log.isInfoEnabled()) {
			_log.info("Detected database " + database.toString());
		}

		if (Validator.isNotNull(PropsValues.JPA_DATABASE_TYPE)) {
			DBFactoryUtil.setDB(PropsValues.JPA_DATABASE_TYPE);
		}
		else {
			DBFactoryUtil.setDB(type);
		}

		return database;
	}

	private static Log _log = LogFactoryUtil.getLog(DatabaseDetector.class);

}