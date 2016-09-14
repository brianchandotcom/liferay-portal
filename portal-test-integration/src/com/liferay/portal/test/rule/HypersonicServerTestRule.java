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

import com.liferay.portal.kernel.test.rule.BaseTestRule;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.test.rule.callback.HypersonicServerTestCallback;
import com.liferay.portal.util.PropsImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hsqldb.jdbc.JDBCDriver;
import org.hsqldb.server.Server;

/**
 * @author William Newbury
 * @author Shuyang Zhou
 */
public class HypersonicServerTestRule extends BaseTestRule<Server, Object> {

	public static final HypersonicServerTestRule INSTANCE;

	public HypersonicServerTestRule() {
		super(_getTestCallback());
	}

	public List<String> getJdbcProperties() {
		if (_HYPERSONIC) {
			return Arrays.asList(
				new String[] {
					"portal:jdbc.default.url=" + _DATABASE_URL,
					"portal:jdbc.default.username=sa",
					"portal:jdbc.default.password="
				});
		}

		return Collections.emptyList();
	}

	private static BaseTestCallback<Server, Object> _getTestCallback() {
		if (_HYPERSONIC) {
			return new HypersonicServerTestCallback(_DATABASE_NAME);
		}

		return new BaseTestCallback<>();
	}

	private static final String _DATABASE_NAME;

	private static final String _DATABASE_URL;

	private static final boolean _HYPERSONIC;

	static {
		Props props = new PropsImpl();

		String className = props.get("jdbc.default.driverClassName");

		_HYPERSONIC = className.equals(JDBCDriver.class.getName());

		if (_HYPERSONIC) {
			String jdbcURL = props.get("jdbc.default.url");

			int index = jdbcURL.lastIndexOf(CharPool.SLASH);

			if (index < 0) {
				throw new ExceptionInInitializerError(
					"Invalid Hypersonic JDBC URL " + jdbcURL);
			}

			String databaseName = jdbcURL.substring(index + 1);

			index = databaseName.indexOf(CharPool.SEMICOLON);

			if (index >= 0) {
				databaseName = databaseName.substring(0, index);
			}

			_DATABASE_NAME = databaseName;

			_DATABASE_URL =
				HypersonicServerTestCallback.DATABASE_URL_BASE + _DATABASE_NAME;
		}
		else {
			_DATABASE_NAME = null;
			_DATABASE_URL = null;
		}

		INSTANCE = new HypersonicServerTestRule();
	}

}