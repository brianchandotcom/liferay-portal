/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		DBInfo dbInfo = _dbInfos.get(dataSource);

		if (dbInfo == null) {
			dbInfo = _createDBInfo(dataSource);

			_dbInfos.put(dataSource, dbInfo);
		}

		return dbInfo;
	}

	private static DBInfo _createDBInfo(DataSource dataSource) {
		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			return new DBInfo(
				databaseMetaData.getDatabaseProductName(),
				databaseMetaData.getDatabaseMajorVersion(),
				databaseMetaData.getDatabaseMinorVersion());
		}
		catch (SQLException sqlException) {
			return ReflectionUtil.throwException(sqlException);
		}
	}

	private static final ConcurrentMap<DataSource, DBInfo> _dbInfos =
		new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}