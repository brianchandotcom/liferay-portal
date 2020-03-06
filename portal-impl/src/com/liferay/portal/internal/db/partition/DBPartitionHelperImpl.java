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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.InfrastructureUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Alberto Chaparro
 */
public class DBPartitionHelperImpl implements DBPartitionHelper {

	@Override
	public boolean addPartition(long companyId) {
		if (companyId == _defaultCompanyId) {
			return false;
		}

		Connection connection = CurrentConnectionUtil.getConnection(
			InfrastructureUtil.getDataSource());

		String schemaName = "company" + companyId;

		try {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"create schema if not exists " + schemaName +
							" character set utf8")) {

				preparedStatement.executeUpdate();
			}

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBInspector dbInspector = new DBInspector(connection);

			try (ResultSet resultSet = databaseMetaData.getTables(
					dbInspector.getCatalog(), dbInspector.getSchema(), null,
					new String[] {"TABLE"});
				Statement statement = connection.createStatement()) {

				while (resultSet.next()) {
					String tableName = resultSet.getString("TABLE_NAME");

					if (_isControlTable(dbInspector, tableName)) {
						statement.executeUpdate(
							StringBundler.concat(
								"create view ", schemaName, StringPool.PERIOD,
								tableName, " as select * from companyDefault.",
								tableName));
					}
					else {
						statement.executeUpdate(
							StringBundler.concat(
								"create table ", schemaName, StringPool.PERIOD,
								tableName, " like companyDefault.", tableName));
					}
				}
			}
		}
		catch (Exception exception) {
			throw new ORMException(exception);
		}

		return true;
	}

	@Override
	public boolean removePartition(long companyId) {
		return true;
	}

	@Override
	public void setDefaultCompanyId(long companyId) {
		_defaultCompanyId = companyId;
	}

	@Override
	public void usePartition(Connection connection) {
		try {
			if (connection.isReadOnly()) {
				return;
			}

			long companyId = CompanyThreadLocal.getCompanyId();

			try (Statement statement = connection.createStatement()) {
				if ((companyId == CompanyConstants.SYSTEM) ||
					(companyId == _defaultCompanyId)) {

					statement.execute("USE companyDefault");
				}
				else {
					statement.execute("USE company" + companyId);
				}
			}
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}
	}

	private boolean _isControlTable(DBInspector dbInspector, String tableName)
		throws Exception {

		if (!dbInspector.hasColumn(tableName, "companyId") ||
			tableName.equals("Portlet") || tableName.equals("Company") ||
			tableName.equals("VirtualHost")) {

			return true;
		}

		return false;
	}

	private volatile long _defaultCompanyId;

}