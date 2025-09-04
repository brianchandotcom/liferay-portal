/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class TestrayRunComparison {

	public URL getComparisonURL() {
		StringBuilder sb = new StringBuilder();

		sb.append("https://testray.liferay.com/#/compare-runs/");
		sb.append(_testrayRunA.getID());
		sb.append("/");
		sb.append(_testrayRunB.getID());
		sb.append("/teams");

		try {
			return new URL(sb.toString());
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	protected TestrayRunComparison(
		TestrayRun testrayRunA, TestrayRun testrayRunB) {

		if ((testrayRunA == null) || (testrayRunB == null)) {
			throw new IllegalArgumentException(
				"One or more Testray run objects is null");
		}

		_testrayRunA = testrayRunA;
		_testrayRunB = testrayRunB;
	}

	private JSONObject _getRunComparisonsJSONObject() {
		if (_jsonObject != null) {
			return _jsonObject;
		}

		try {
			StringBuilder sb = new StringBuilder();

			TestrayBuild testrayBuild = _testrayRunA.getTestrayBuild();

			TestrayServer testrayServer = testrayBuild.getTestrayServer();

			sb.append(testrayServer.getURL());

			sb.append("/o/testray-rest/v1.0/testray-run-comparisons/");
			sb.append(_testrayRunA.getID());
			sb.append("/");
			sb.append(_testrayRunB.getID());
			sb.append("/runs");

			_jsonObject = JenkinsResultsParserUtil.toJSONObject(sb.toString());

			return _jsonObject;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private boolean _isFailureStatus(String status) {
		if (status.equals(_BLOCKED) || status.equals(_FAILED) ||
			status.equals(_TESTFIX)) {

			return true;
		}

		return false;
	}

	private boolean _isUntestedStatus(String status) {
		if (status.equals(_DNR) || status.equals(_UNTESTED)) {
			return true;
		}

		return false;
	}

	private static final String _BLOCKED = "BLOCKED";

	private static final String _DNR = "DIDNOTRUN";

	private static final String _FAILED = "FAILED";

	private static final String _PASSED = "PASSED";

	private static final String[] _STATUSES;

	private static final String _TESTFIX = "TESTFIX";

	private static final String _UNTESTED = "UNTESTED";

	static {
		_STATUSES = new String[] {
			_BLOCKED, _DNR, _FAILED, _PASSED, _TESTFIX, _UNTESTED
		};
	}

	private JSONObject _jsonObject;
	private final TestrayRun _testrayRunA;
	private final TestrayRun _testrayRunB;

}