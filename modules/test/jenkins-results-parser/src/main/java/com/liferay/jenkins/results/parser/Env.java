/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Map;

/**
 * @author Calum Ragan
 */
public class Env {

	public static String get(String name) {
		return _env.doGet(name);
	}

	public static Map<String, String> getAll() {
		return _env.doGetAll();
	}

	public static void setInstance(Env env) {
		_env = env;
	}

	protected String doGet(String name) {
		return System.getenv(name);
	}

	protected Map<String, String> doGetAll() {
		return System.getenv();
	}

	private static volatile Env _env = new Env();

}