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

package com.liferay.portal.test.jdbc;

import com.liferay.portal.dao.jdbc.DataSourceFactoryImpl;
import com.liferay.portal.dao.jdbc.util.DataSourceWrapper;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Shuyang Zhou
 */
public class ResetDatabaseUtilDataSource extends DataSourceWrapper {

	public static void initialize() {
		try {
			if (_paclField.get(null) != _pacl) {
				_paclField.set(null, _pacl);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ResetDatabaseUtilDataSource(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return (Connection)ProxyUtil.newProxyInstance(
			ResetDatabaseUtilDataSource.class.getClassLoader(),
			new Class<?>[] {Connection.class},
			new ResetDatabaseConnectionHandler(super.getConnection()));
	}

	private static final DataSourceFactoryImpl.PACL _pacl =
		new DataSourceFactoryImpl.PACL() {

			@Override
			public DataSource getDataSource(DataSource dataSource) {
				return new ResetDatabaseUtilDataSource(dataSource);
			}

		};

	private static final Field _paclField;

	static {
		try {
			_paclField = ReflectionUtil.getDeclaredField(
				DataSourceFactoryImpl.class, "_pacl");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}