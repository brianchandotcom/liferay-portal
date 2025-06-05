/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class JUnitTestReport extends BaseTestReport {

	@Override
	public String getTestClassName() {
		String testName = super.getTestName();

		Matcher matcher = _jUnitTestNamePattern.matcher(testName);

		if (!matcher.find()) {
			return testName;
		}

		return matcher.group("testClassName");
	}

	@Override
	public String getTestName() {
		String testName = super.getTestName();

		Matcher matcher = _jUnitTestNamePattern.matcher(testName);

		if (!matcher.find()) {
			return testName;
		}

		return matcher.group("testName");
	}

	protected JUnitTestReport(
		DownstreamBuildReport downstreamBuildReport, JSONObject jsonObject) {

		super(downstreamBuildReport, jsonObject);
	}

	private static final Pattern _jUnitTestNamePattern = Pattern.compile(
		"(?<testClassName>.*Test)\\.(?<testName>[^\\.]+)");

}