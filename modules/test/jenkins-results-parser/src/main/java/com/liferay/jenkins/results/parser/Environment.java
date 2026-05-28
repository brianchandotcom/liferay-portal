/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Map;

/**
 * @author Calum Ragan
 */
public class Environment {

	public static String get(String name) {
		return _environment.doGet(name);
	}

	public static Map<String, String> getAll() {
		return _environment.doGetAll();
	}

	public static void setInstance(Environment environment) {
		_environment = environment;
	}

	protected String doGet(String name) {
		return System.getenv(name);
	}

	protected Map<String, String> doGetAll() {
		return System.getenv();
	}

	private static volatile Environment _environment = new Environment();

}