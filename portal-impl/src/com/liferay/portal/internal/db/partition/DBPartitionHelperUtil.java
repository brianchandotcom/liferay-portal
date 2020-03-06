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

package com.liferay.portal.internal.db.partition;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.spring.hibernate.DialectDetector;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Alberto Chaparro
 */
public class DBPartitionHelperUtil {

	public static boolean addPartition(long companyId) {
		return _dbPartitionHelper.addPartition(companyId);
	}

	public static boolean removePartition(long companyId) {
		return _dbPartitionHelper.removePartition(companyId);
	}

	public static void setDefaultCompanyId(long companyId) {
		_dbPartitionHelper.setDefaultCompanyId(companyId);
	}

	public static void usePartition(Connection connection) {
		_dbPartitionHelper.usePartition(connection);
	}

	public static DataSource wrapDataSource(DataSource dataSource)
		throws SQLException {

		boolean databasePartitionEnabled = GetterUtil.getBoolean(
			PropsUtil.get("database.partition.enabled"));

		if (databasePartitionEnabled) {
			DB db = DBManagerUtil.getDB(
				DBManagerUtil.getDBType(DialectDetector.getDialect(dataSource)),
				dataSource);

			if (db.getDBType() != DBType.MYSQL) {
				throw new Error("Database Partition requires MySQL");
			}

			try (Connection connection = dataSource.getConnection()) {
				_dbPartitionHelper = new DBPartitionHelperImpl(connection);
			}

			dataSource = new DBPartitionDataSource(dataSource);
		}
		else {
			_dbPartitionHelper = new DBPartitionHelper() {

				@Override
				public boolean addPartition(long companyId) {
					return false;
				}

				@Override
				public boolean removePartition(long companyId) {
					return false;
				}

				@Override
				public void setDefaultCompanyId(long companyId) {
				}

				@Override
				public void usePartition(Connection connection) {
				}

			};
		}

		return dataSource;
	}

	private static volatile DBPartitionHelper _dbPartitionHelper;

}