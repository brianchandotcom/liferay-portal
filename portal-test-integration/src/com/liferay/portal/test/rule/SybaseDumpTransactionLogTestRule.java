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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AbstractTestRule;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class SybaseDumpTransactionLogTestRule
	extends AbstractTestRule<Void, Void> {

	public static final SybaseDumpTransactionLogTestRule INSTANCE =
		new SybaseDumpTransactionLogTestRule();

	@Override
	public void afterMethod(Description description, Void v, Object target)
		throws Exception {

		if (_thread != null) {
			_thread.interrupt();

			_thread.join();
		}
	}

	@Override
	public Void beforeClass(Description description) throws SQLException {
		SybaseDumpTransactionLog sybaseDumpTransactionLog =
			description.getAnnotation(SybaseDumpTransactionLog.class);

		if (sybaseDumpTransactionLog != null) {
			SybaseDump[] sybaseDumps = sybaseDumpTransactionLog.dumpBefore();

			if (!ArrayUtil.contains(sybaseDumps, SybaseDump.CLASS)) {
				return null;
			}
		}

		_dumpTransactionLog();

		return null;
	}

	@Override
	public Void beforeMethod(Description description, Object target)
		throws SQLException {

		Class<?> testClass = description.getTestClass();

		SybaseDumpTransactionLog sybaseDumpTransactionLog =
			testClass.getAnnotation(SybaseDumpTransactionLog.class);

		if (sybaseDumpTransactionLog != null) {
			SybaseDump[] sybaseDumps = sybaseDumpTransactionLog.dumpBefore();

			if (ArrayUtil.contains(sybaseDumps, SybaseDump.METHOD)) {
				_dumpTransactionLog();

				_thread = new Thread(
					() -> {
						while (true) {
							try {
								Thread.sleep(10000);

								_dumpTransactionLog();
							}
							catch (InterruptedException ie) {
								break;
							}
							catch (SQLException sqle) {
								throw new RuntimeException(sqle);
							}
						}
					},
					SybaseDumpTransactionLogTestRule.class.getName());

				_thread.setDaemon(true);

				_thread.start();
			}
		}

		return null;
	}

	@Override
	protected void afterClass(Description description, Void v) {
	}

	private SybaseDumpTransactionLogTestRule() {
	}

	private void _dumpTransactionLog() throws SQLException {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.SYBASE) {
			return;
		}

		try (Connection connection = DataAccess.getConnection();
			Statement statement = connection.createStatement()) {

			statement.execute(
				"dump transaction " + connection.getCatalog() + " with no_log");
		}
	}

	private Thread _thread;

}