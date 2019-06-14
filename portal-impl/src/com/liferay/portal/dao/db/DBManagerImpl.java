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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.jdbc.util.DBInfo;
import com.liferay.portal.dao.jdbc.util.DBInfoUtil;
import com.liferay.portal.dao.orm.hibernate.DialectImpl;
import com.liferay.portal.dao.orm.hibernate.MariaDBDialect;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactory;
import com.liferay.portal.kernel.dao.db.DBManager;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.util.PropsValues;

import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.ServiceLoader;
import java.util.Set;

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
@SuppressWarnings("deprecation")
public class DBManagerImpl implements DBManager {

	public DBManagerImpl() {
		ServiceLoader<DBFactory> serviceLoader = ServiceLoader.load(
			DBFactory.class, DBManagerImpl.class.getClassLoader());

		for (DBFactory dbFactory : serviceLoader) {
			_dbFactories.put(dbFactory.getDBType(), dbFactory);
		}
	}

	@Override
	public DB getDB() {
		if (_db == null) {
			try {
				if (_log.isInfoEnabled()) {
					_log.info("Using dialect " + PropsValues.HIBERNATE_DIALECT);
				}

				Dialect dialect = (Dialect)InstanceFactory.newInstance(
					PropsValues.HIBERNATE_DIALECT);

				setDB(
					getDB(
						getDBType(dialect),
						InfrastructureUtil.getDataSource()));
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _db;
	}

	@Override
	public DB getDB(DBType dbType, DataSource dataSource) {
		DBFactory dbCreator = _dbFactories.get(dbType);

		if (dbCreator == null) {
			throw new IllegalArgumentException(
				"Unsupported database type " + dbType);
		}

		if (dataSource == null) {
			return dbCreator.create(0, 0);
		}

		DBInfo dbInfo = DBInfoUtil.getDBInfo(dataSource);

		return dbCreator.create(
			dbInfo.getMajorVersion(), dbInfo.getMinorVersion());
	}

	@Override
	public DBType getDBType(Object dialect) {
		if (dialect instanceof DialectImpl) {
			DialectImpl dialectImpl = (DialectImpl)dialect;

			dialect = dialectImpl.getWrappedDialect();
		}

		if (dialect instanceof DB2Dialect) {
			return DBType.DB2;
		}

		if (dialect instanceof HSQLDialect) {
			return DBType.HYPERSONIC;
		}

		if (dialect instanceof MariaDBDialect) {
			return DBType.MARIADB;
		}

		if (dialect instanceof MySQLDialect) {
			return DBType.MYSQL;
		}

		if (dialect instanceof Oracle8iDialect ||
			dialect instanceof Oracle9Dialect) {

			return DBType.ORACLE;
		}

		if (dialect instanceof PostgreSQLDialect) {
			return DBType.POSTGRESQL;
		}

		if (dialect instanceof SQLServerDialect) {
			return DBType.SQLSERVER;
		}

		if (dialect instanceof Sybase11Dialect ||
			dialect instanceof SybaseAnywhereDialect ||
			dialect instanceof SybaseASE15Dialect ||
			dialect instanceof SybaseDialect) {

			return DBType.SYBASE;
		}

		throw new IllegalArgumentException("Unknown dialect type " + dialect);
	}

	@Override
	public Set<DBType> getDBTypes() {
		return new LinkedHashSet<>(_dbFactories.keySet());
	}

	@Override
	public void setDB(DB db) {
		_db = db;

		if (_log.isDebugEnabled()) {
			Class<?> clazz = _db.getClass();

			_log.debug(
				StringBundler.concat(
					"Using DB implementation ", clazz.getName(), " for ",
					db.getDBType()));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(DBManagerImpl.class);

	private DB _db;
	private final EnumMap<DBType, DBFactory> _dbFactories = new EnumMap<>(
		DBType.class);

}