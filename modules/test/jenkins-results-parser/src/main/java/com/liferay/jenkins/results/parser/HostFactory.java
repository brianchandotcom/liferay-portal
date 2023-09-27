/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class HostFactory {

	public static Host newHost(String name) {
		if (_hosts.containsKey(name)) {
			return _hosts.get(name);
		}

		_hosts.put(name, new DefaultHost(name));

		return _hosts.get(name);
	}

	private static final Map<String, Host> _hosts = new HashMap<>();

}