/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.jethr0;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

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

		boolean opEnabled = false;

		try {
			opEnabled = Boolean.parseBoolean(
				JenkinsResultsParserUtil.getBuildProperty(
					"jethr0.1password.enabled"));
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}

		File opConnectFile = new File(
			System.getProperty("user.home") + "/.1password.connect");

		if (opEnabled && opConnectFile.exists()) {
			_jethr0Clients.put(key, new CIJethr0Client(jenkinsMaster));
		}
		else {
			_jethr0Clients.put(key, new LocalJethr0Client(jenkinsMaster));
		}

		return _jethr0Clients.get(key);
	}

	private static final Map<String, Jethr0Client> _jethr0Clients =
		new HashMap<>();

}