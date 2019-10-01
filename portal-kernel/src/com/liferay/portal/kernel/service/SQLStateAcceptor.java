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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.util.Validator;

import java.sql.SQLException;

import java.util.Map;

/**
 * @author Matthew Tambara
 */
public class SQLStateAcceptor implements RetryAcceptor {

	public static final String SQLSTATE = "SQLSTATE";

	@Override
	public boolean acceptException(
		Throwable t, Map<String, String> propertyMap) {

		String sqlState = propertyMap.get(SQLSTATE);

		while (true) {
			Class<?> clazz = t.getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			if (classLoader == null) {
				classLoader = ClassLoader.getSystemClassLoader();
			}

			try {
				Class<?> exceptionClass = classLoader.loadClass(
					SQLException.class.getName());

				if (exceptionClass.isInstance(t) &&
					_scanForSQLState((SQLException)t, sqlState)) {

					return true;
				}
			}
			catch (ClassNotFoundException cnfe) {
			}

			Throwable cause = t.getCause();

			if ((t == cause) || (cause == null)) {
				break;
			}

			t = cause;
		}

		return false;
	}

	@Override
	public boolean acceptResult(
		Object returnValue, Map<String, String> propertyMap) {

		return false;
	}

	private boolean _scanForSQLState(
		SQLException sqle, String expectedSQLState) {

		while (true) {
			String sqlState = sqle.getSQLState();

			if (Validator.isNotNull(sqlState) &&
				sqlState.startsWith(expectedSQLState)) {

				return true;
			}

			SQLException next = sqle.getNextException();

			if ((next == null) || next.equals(sqle)) {
				break;
			}

			sqle = next;
		}

		return false;
	}

}