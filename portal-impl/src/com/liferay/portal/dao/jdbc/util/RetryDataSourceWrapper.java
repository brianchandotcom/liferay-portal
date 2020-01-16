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

import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Matthew Tambara
 */
public class RetryDataSourceWrapper extends DataSourceWrapper {

	public RetryDataSourceWrapper(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Connection getConnection() throws SQLException {
		int retries = PropsValues.RETRY_DATA_SOURCE_MAX_RETRIES;

		SQLException sqlException1 = null;

		while (retries-- >= 0) {
			try {
				return super.getConnection();
			}
			catch (SQLException sqlException2) {
				if (sqlException1 == null) {
					sqlException1 = sqlException2;
				}
			}
		}

		throw sqlException1;
	}

	@Override
	public Connection getConnection(String username, String password)
		throws SQLException {

		int retries = PropsValues.RETRY_DATA_SOURCE_MAX_RETRIES;

		SQLException sqlException1 = null;

		while (retries-- >= 0) {
			try {
				return super.getConnection(username, password);
			}
			catch (SQLException sqlException2) {
				if (sqlException1 == null) {
					sqlException1 = sqlException2;
				}
			}
		}

		throw sqlException1;
	}

}