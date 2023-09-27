/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

/**
 * @author Matthew Tambara
 */
public class SQLStateAcceptor implements RetryAcceptor {

	public static final String SQLSTATE = "SQLSTATE";

	public static final String SQLSTATE_INTEGRITY_CONSTRAINT_VIOLATION = "23";

	public static final String SQLSTATE_TRANSACTION_ROLLBACK = "40";

	@Override
	public boolean acceptException(
		Throwable throwable, Map<String, String> propertyMap) {

		List<String> sqlStates = StringUtil.split(propertyMap.get(SQLSTATE));

		while (true) {
			if ((throwable instanceof SQLException) &&
				_scanForSQLState((SQLException)throwable, sqlStates)) {

				return true;
			}

			Throwable causeThrowable = throwable.getCause();

			if ((causeThrowable == null) || (causeThrowable == throwable)) {
				break;
			}

			throwable = causeThrowable;
		}

		return false;
	}

	@Override
	public boolean acceptResult(
		Object returnValue, Map<String, String> propertyMap) {

		return false;
	}

	private boolean _hasSQLState(
		String sqlState, List<String> expectedSQLStates) {

		if (Validator.isNull(sqlState)) {
			return false;
		}

		for (String expectedSQLState : expectedSQLStates) {
			if (sqlState.startsWith(expectedSQLState)) {
				return true;
			}
		}

		return false;
	}

	private boolean _scanForSQLState(
		SQLException sqlException1, List<String> expectedSQLStates) {

		while (true) {
			if (_hasSQLState(sqlException1.getSQLState(), expectedSQLStates)) {
				return true;
			}

			SQLException sqlException2 = sqlException1.getNextException();

			if ((sqlException2 == null) ||
				sqlException2.equals(sqlException1)) {

				return false;
			}

			sqlException1 = sqlException2;
		}
	}

}