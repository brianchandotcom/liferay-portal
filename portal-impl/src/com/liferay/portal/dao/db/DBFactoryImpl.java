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

package com.liferay.portal.dao.db;

import com.liferay.portal.dao.orm.hibernate.DialectImpl;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactory;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.dialect.DB2Dialect;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.Oracle8iDialect;
import org.hibernate.dialect.Oracle9Dialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.Sybase11Dialect;
import org.hibernate.dialect.SybaseASE15Dialect;
import org.hibernate.dialect.SybaseAnywhereDialect;
import org.hibernate.dialect.SybaseDialect;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
@SuppressWarnings("deprecation")
public class DBFactoryImpl implements DBFactory {

	@Override
	public DB getDB() {
		if (_db == null) {
			try {
				if (_log.isInfoEnabled()) {
					_log.info("Using dialect " + PropsValues.HIBERNATE_DIALECT);
				}

				Dialect dialect = (Dialect)InstanceFactory.newInstance(
					PropsValues.HIBERNATE_DIALECT);

				setDB(dialect, InfrastructureUtil.getDataSource());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _db;
	}

	@Override
	public DB getDB(DBType dbType, DataSource dataSource) {
		int dbMajorVersion = 0;
		int dbMinorVersion = 0;

		if (dataSource != null) {
			try (Connection connection = dataSource.getConnection()) {
				DatabaseMetaData databaseMetaData = connection.getMetaData();

				dbMajorVersion = databaseMetaData.getDatabaseMajorVersion();
				dbMinorVersion = databaseMetaData.getDatabaseMinorVersion();
			}
			catch (SQLException sqle) {
				return ReflectionUtil.throwException(sqle);
			}
		}

		if (dbType == DBType.DB2) {
			return new DB2DB(dbMajorVersion, dbMinorVersion);
		}

		if (dbType == DBType.HYPERSONIC) {
			return new HypersonicDB(dbMajorVersion, dbMinorVersion);
		}

		if (dbType == DBType.MYSQL) {
			return new MySQLDB(dbMajorVersion, dbMinorVersion);
		}

		if (dbType == DBType.ORACLE) {
			return new OracleDB(dbMajorVersion, dbMinorVersion);
		}

		if (dbType == DBType.POSTGRESQL) {
			return new PostgreSQLDB(dbMajorVersion, dbMinorVersion);
		}

		if (dbType == DBType.SQLSERVER) {
			return new SQLServerDB(dbMajorVersion, dbMinorVersion);
		}

		if (dbType == DBType.SYBASE) {
			return new SybaseDB(dbMajorVersion, dbMinorVersion);
		}

		throw new IllegalArgumentException("Unknown database type " + dbType);
	}

	@Override
	public DB getDB(Object dialect, DataSource dataSource) {
		if (dialect instanceof DialectImpl) {
			DialectImpl dialectImpl = (DialectImpl)dialect;

			dialect = dialectImpl.getWrappedDialect();
		}

		if (dialect instanceof DB2Dialect) {
			return getDB(DBType.DB2, dataSource);
		}

		if (dialect instanceof HSQLDialect) {
			return getDB(DBType.HYPERSONIC, dataSource);
		}

		if (dialect instanceof MySQLDialect) {
			return getDB(DBType.MYSQL, dataSource);
		}

		if (dialect instanceof Oracle8iDialect ||
			dialect instanceof Oracle9Dialect) {

			return getDB(DBType.ORACLE, dataSource);
		}

		if (dialect instanceof PostgreSQLDialect) {
			return getDB(DBType.POSTGRESQL, dataSource);
		}

		if (dialect instanceof SQLServerDialect) {
			return getDB(DBType.SQLSERVER, dataSource);
		}

		if (dialect instanceof SybaseDialect ||
			dialect instanceof Sybase11Dialect ||
			dialect instanceof SybaseAnywhereDialect ||
			dialect instanceof SybaseASE15Dialect) {

			return getDB(DBType.SYBASE, dataSource);
		}

		throw new IllegalArgumentException("Unknown dialect type " + dialect);
	}

	@Override
	public void setDB(DBType dbType, DataSource dataSource) {
		_db = getDB(dbType, dataSource);

		if (_log.isDebugEnabled()) {
			Class<?> clazz = _db.getClass();

			_log.debug(
				"Using DB implementation " + clazz.getName() + " for " +
					dbType);
		}
	}

	@Override
	public void setDB(Object dialect, DataSource dataSource) {
		_db = getDB(dialect, dataSource);

		if (_log.isDebugEnabled()) {
			Class<?> dbClazz = _db.getClass();
			Class<?> dialectClazz = dialect.getClass();

			_log.debug(
				"Using DB implementation " + dbClazz.getName() + " for " +
					dialectClazz.getName());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(DBFactoryImpl.class);

	private static DB _db;

}