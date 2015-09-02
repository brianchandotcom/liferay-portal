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
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.test.rule.callback.HypersonicServerTestCallback;
import com.liferay.portal.util.PropsImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author William Newbury
 */
public class HypersonicServerTestRule extends BaseTestRule<Object, Object> {

	public HypersonicServerTestRule(String databaseName) {
		super(getTestCallback(databaseName));

		_databaseURL = "jdbc:hsqldb:hsql://localhost/" + databaseName;
	}

	public List<String> getJdbcProperties() {
		if (_HYPERSONIC) {
			return Arrays.asList(
				new String[] {
					"portal:jdbc.default.url=".concat(_databaseURL),
					"portal:jdbc.default.username=sa",
					"portal:jdbc.default.password="
				});
		}

		return Collections.emptyList();
	}

	private static BaseTestCallback getTestCallback(String databaseName) {
		Props props = new PropsImpl();

		String jdbcDriver = props.get("jdbc.default.driverClassName");

		_HYPERSONIC = jdbcDriver.contains("hsqldb");

		if (_HYPERSONIC) {
			return new HypersonicServerTestCallback(databaseName);
		}
		else {
			return new BaseTestCallback();
		}
	}

	private static boolean _HYPERSONIC;

	private final String _databaseURL;

}