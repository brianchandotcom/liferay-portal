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

	public TestrayCase(TestrayProject testrayProject, JSONObject jsonObject) {
		_testrayProject = testrayProject;
		_jsonObject = jsonObject;
	}

	public String getComponent() {
		JSONObject mainComponentJSONObject = _jsonObject.getJSONObject(
			"mainComponent");

		return mainComponentJSONObject.getString("name");
	}

	public String getID() {
		return _jsonObject.optString("testrayCaseId");
	}

	public String getName() {
		return _jsonObject.optString("name");
	}

	public int getPriority() {
		return _jsonObject.getInt("priority");
	}

	public String getTeamName() {
		return _jsonObject.getString("testrayTeamName");
	}

	public String getType() {
		return _jsonObject.getString("testrayCaseTypeName");
	}

	private final JSONObject _jsonObject;
	private final TestrayProject _testrayProject;

}