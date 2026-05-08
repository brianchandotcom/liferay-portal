/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class RunTestrayFactor extends BaseTestrayFactor {

	public TestrayRun getTestrayRun() {
		return _testrayRun;
	}

	protected RunTestrayFactor(JSONObject jsonObject, TestrayRun testrayRun) {
		super(testrayRun.getTestrayServer(), jsonObject);

		_testrayRun = testrayRun;
	}

	private final TestrayRun _testrayRun;

}