/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import com.liferay.jenkins.results.parser.testray.TestrayRoutine;
import com.liferay.jenkins.results.parser.testray.TestrayServer;

import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class TestrayJobHistory extends BaseJobHistory {

	@Override
	public URL getTestrayURL() {
		if (_testrayRoutine == null) {
			return null;
		}

		TestrayServer testrayServer = _testrayRoutine.getTestrayServer();

		if (testrayServer == null) {
			return null;
		}

		return testrayServer.getURL();
	}

	protected TestrayJobHistory(
		String portalUpstreamBranchName, TestrayRoutine testrayRoutine) {

		super(portalUpstreamBranchName);

		_testrayRoutine = testrayRoutine;
	}

	private final TestrayRoutine _testrayRoutine;

}