/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.jethr0;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class Jethr0ClientFactory {

	public static Jethr0Client newJethr0Client(JenkinsMaster jenkinsMaster) {
		String key = jenkinsMaster.getName();

		if (_jethr0Clients.containsKey(key)) {
			return _jethr0Clients.get(key);
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				System.getenv("CACHE_DIR"))) {

			_jethr0Clients.put(key, new LocalJethr0Client(jenkinsMaster));
		}
		else {
			_jethr0Clients.put(key, new CIJethr0Client(jenkinsMaster));
		}

		return _jethr0Clients.get(key);
	}

	private static final Map<String, Jethr0Client> _jethr0Clients =
		new HashMap<>();

}