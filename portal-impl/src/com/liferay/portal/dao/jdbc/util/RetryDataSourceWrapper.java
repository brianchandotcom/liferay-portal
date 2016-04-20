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
		int retries = PropsValues.RETRY_DATA_SOURCE_WRAPPER_MAX_RETRIES + 1;

		SQLException sqlException = null;

		while (retries-- > 0) {
			try {
				return super.getConnection();
			}
			catch (SQLException sqle) {
				if (sqlException == null) {
					sqlException = sqle;
				}
			}
		}

		throw sqlException;
	}

	@Override
	public Connection getConnection(String username, String password)
		throws SQLException {

		int retries = PropsValues.RETRY_DATA_SOURCE_WRAPPER_MAX_RETRIES + 1;

		SQLException sqlException = null;

		while (retries-- > 0) {
			try {
				return super.getConnection(username, password);
			}
			catch (SQLException sqle) {
				if (sqlException == null) {
					sqlException = sqle;
				}
			}
		}

		throw sqlException;
	}

}