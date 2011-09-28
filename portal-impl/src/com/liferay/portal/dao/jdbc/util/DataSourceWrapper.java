/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * @author Shuyang Zhou
 */
public class DataSourceWrapper implements DataSource {

	public DataSourceWrapper(DataSource wrappedDataSource) {
		_wrappedDataSource = wrappedDataSource;
	}

	public Connection getConnection() throws SQLException {
		return _wrappedDataSource.getConnection();
	}

	public Connection getConnection(String username, String password)
		throws SQLException {

		return _wrappedDataSource.getConnection(username, password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return _wrappedDataSource.getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return _wrappedDataSource.getLoginTimeout();
	}

	public Logger getParentLogger() {

		// JDK 7

		throw new UnsupportedOperationException();
	}

	public DataSource getWrappedDataSource() {
		return _wrappedDataSource;
	}

	public boolean isWrapperFor(Class<?> clazz) {

		// JDK 6

		return DataSource.class.equals(clazz);
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		_wrappedDataSource.setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		_wrappedDataSource.setLoginTimeout(seconds);
	}

	public void setWrappedDataSource(DataSource wrappedDataSource) {
		_wrappedDataSource = wrappedDataSource;
	}

	public <T> T unwrap(Class<T> clazz) throws SQLException {

		// JDK 6

		if (!DataSource.class.equals(clazz)) {
			throw new SQLException("Invalid class " + clazz);
		}

		return (T)this;
	}

	private volatile DataSource _wrappedDataSource;

}