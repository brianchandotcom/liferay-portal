/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PlaywrightTestClassMethod extends TestClassMethod {

	@Override
	public String getName() {
		TestClass testClass = getTestClass();

		return JenkinsResultsParserUtil.combine(
			testClass.getName(), " > ", super.getName());
	}

	public String getTestName() {
		return super.getName();
	}

	protected PlaywrightTestClassMethod(
		boolean ignored, String name, TestClass testClass) {

		super(ignored, name, testClass);
	}

	protected PlaywrightTestClassMethod(
		JSONObject jsonObject, TestClass testClass) {

		super(jsonObject, testClass);
	}

}