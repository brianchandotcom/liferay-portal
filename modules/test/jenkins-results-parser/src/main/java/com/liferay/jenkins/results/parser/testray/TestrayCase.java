/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayCase {

	public String getComponent() {
		return null;
	}

	public String getID() {
		return null;
	}

	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	public String getName() {
		return null;
	}

	public int getPriority() {
		return -1;
	}

	public String getTeamName() {
		return null;
	}

	public TestrayProject getTestrayProject() {
		return _testrayProject;
	}

	public String getType() {
		return null;
	}

	protected TestrayCase(
		TestrayProject testrayProject, JSONObject jsonObject) {

		_testrayProject = testrayProject;
		_jsonObject = jsonObject;
	}

	private final JSONObject _jsonObject;
	private final TestrayProject _testrayProject;

}