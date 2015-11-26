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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.dao.orm.hibernate.DB2Dialect;
import com.liferay.portal.dao.orm.hibernate.HSQLDialect;
import com.liferay.portal.dao.orm.hibernate.SQLServer2005Dialect;
import com.liferay.portal.dao.orm.hibernate.SQLServer2008Dialect;
import com.liferay.portal.dao.orm.hibernate.SybaseASE157Dialect;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.hibernate.dialect.DB2400Dialect;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.resolver.DialectFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class DialectDetector {

	public static Dialect getDialect(DataSource dataSource) {
		String dialectKey = null;
		Dialect dialect = null;

		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			String dbName = databaseMetaData.getDatabaseProductName();
			int dbMajorVersion = databaseMetaData.getDatabaseMajorVersion();
			int dbMinorVersion = databaseMetaData.getDatabaseMinorVersion();

			StringBundler sb = new StringBundler(5);

			sb.append(dbName);
			sb.append(StringPool.COLON);
			sb.append(dbMajorVersion);
			sb.append(StringPool.COLON);
			sb.append(dbMinorVersion);

			dialectKey = sb.toString();

			dialect = _dialects.get(dialectKey);

			if (dialect != null) {
				return dialect;
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Determine dialect for " + dbName + " " + dbMajorVersion +
						"." + dbMinorVersion);
			}

			if (dbName.startsWith("HSQL")) {
				dialect = new HSQLDialect();

				if (_log.isWarnEnabled()) {
					sb = new StringBundler(6);

					sb.append("Liferay is configured to use Hypersonic as ");
					sb.append("its database. Do NOT use Hypersonic in ");
					sb.append("production. Hypersonic is an embedded ");
					sb.append("database useful for development and demo'ing ");
					sb.append("purposes. The database settings can be ");
					sb.append("changed in portal-ext.properties.");

					_log.warn(sb.toString());
				}
			}
			else if ((dbName.equals("ASE") ||
					  dbName.equals("Adaptive Server Enterprise")) &&
					 (dbMajorVersion >= 15)) {

				dialect = new SybaseASE157Dialect();
			}
			else if (dbName.startsWith("DB2") && (dbMajorVersion >= 9)) {
				dialect = new DB2Dialect();
			}
			else if (dbName.startsWith("Microsoft") && (dbMajorVersion == 9)) {
				dialect = new SQLServer2005Dialect();
			}
			else if (dbName.startsWith("Microsoft") && (dbMajorVersion == 10)) {
				dialect = new SQLServer2008Dialect();
			}
			else if (dbName.startsWith("Oracle") && (dbMajorVersion >= 10)) {
				dialect = new Oracle10gDialect();
			}
			else {
				dialect = DialectFactory.buildDialect(
					new Properties(), connection);
			}
		}
		catch (Exception e) {
			String msg = GetterUtil.getString(e.getMessage());

			if (msg.contains("explicitly set for database: DB2")) {
				dialect = new DB2400Dialect();

				if (_log.isWarnEnabled()) {
					_log.warn(
						"DB2400Dialect was dynamically chosen as the " +
							"Hibernate dialect for DB2. This can be " +
								"overriden in portal.properties");
				}
			}
			else {
				_log.error(e, e);
			}
		}

		if (dialect == null) {
			throw new RuntimeException("No dialect found");
		}
		else if (dialectKey != null) {
			if (_log.isInfoEnabled()) {
				_log.info("Found dialect " + dialect.getClass().getName());
			}

			_dialects.put(dialectKey, dialect);
		}

		return dialect;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DialectDetector.class);

	private static final Map<String, Dialect> _dialects =
		new ConcurrentHashMap<>();

}