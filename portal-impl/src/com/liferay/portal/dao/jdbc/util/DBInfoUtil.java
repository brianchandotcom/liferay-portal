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

package com.liferay.portal.dao.jdbc.util;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.reflect.ReflectionUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DelegatingDataSource;

/**
 * @author Shuyang Zhou
 */
public class DBInfoUtil {

	public static DBInfo getDBInfo(DataSource dataSource) {
		if (dataSource instanceof DelegatingDataSource) {
			DelegatingDataSource delegatingDataSource =
				(DelegatingDataSource)dataSource;

			dataSource = delegatingDataSource.getTargetDataSource();
		}

		return _dbInfos.computeIfAbsent(
			dataSource,
			keyDataSource -> {
				try (Connection connection = keyDataSource.getConnection()) {
					DatabaseMetaData databaseMetaData =
						connection.getMetaData();

					return new DBInfo(
						databaseMetaData.getDatabaseProductName(),
						databaseMetaData.getDriverName(),
						databaseMetaData.getDatabaseMajorVersion(),
						databaseMetaData.getDatabaseMinorVersion());
				}
				catch (SQLException sqle) {
					return ReflectionUtil.throwException(sqle);
				}
			});
	}

	private static final ConcurrentMap<DataSource, DBInfo> _dbInfos =
		new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}