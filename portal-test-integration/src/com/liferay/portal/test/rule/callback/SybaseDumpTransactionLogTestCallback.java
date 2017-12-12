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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class SybaseDumpTransactionLogTestCallback
	extends BaseTestCallback<Void, Void> {

	public static final SybaseDumpTransactionLogTestCallback INSTANCE =
		new SybaseDumpTransactionLogTestCallback();

	@Override
	public void afterMethod(Description description, Void v, Object target)
		throws Exception {

		if (_thread != null) {
			_thread.interrupt();
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

				_thread = new Thread() {

					public void run() {
						while (true) {
							try {
								_dumpTransactionLog();

								Thread.sleep(10000);
							}
							catch (Exception e) {
								String message = e.getMessage();

								if (!message.contains("sleep interrupted")) {
									throw new RuntimeException(e);
								}
							}
						}
					};

				};

				_thread.setName("Sybase-dumpTransactionLog-thread");

				_thread.start();
			}
		}

		return null;
	}

	private SybaseDumpTransactionLogTestCallback() {
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