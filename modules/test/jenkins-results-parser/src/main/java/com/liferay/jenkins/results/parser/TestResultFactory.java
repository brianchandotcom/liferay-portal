/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class TestResultFactory {

	public static TestResult newTestResult(
		Build build, JSONObject caseJSONObject) {

		String className = caseJSONObject.getString("className");

		if (className.contains(
				"com.liferay.poshi.runner.ParallelPoshiRunner") ||
			className.contains("com.liferay.poshi.runner.PoshiRunner")) {

			return new PoshiJUnitTestResult(build, caseJSONObject);
		}

		return new JUnitTestResult(build, caseJSONObject);
	}

}