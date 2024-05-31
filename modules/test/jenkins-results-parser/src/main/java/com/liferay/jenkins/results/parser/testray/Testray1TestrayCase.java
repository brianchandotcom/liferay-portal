/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class Testray1TestrayCase extends TestrayCase {

	@Override
	public String getComponent() {
		JSONObject jsonObject = getJSONObject();

		JSONObject mainComponentJSONObject = jsonObject.getJSONObject(
			"mainComponent");

		return mainComponentJSONObject.getString("name");
	}

	@Override
	public long getID() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getLong("testrayCaseId");
	}

	@Override
	public String getName() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.optString("name");
	}

	@Override
	public int getPriority() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getInt("priority");
	}

	@Override
	public String getTeamName() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getString("testrayTeamName");
	}

	@Override
	public String getType() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getString("testrayCaseTypeName");
	}

	protected Testray1TestrayCase(
		TestrayProject testrayProject, JSONObject jsonObject) {

		super(testrayProject, jsonObject);
	}

}