/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class Testray1TestrayProductVersion extends TestrayProductVersion {

	@Override
	public long getID() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getLong("testrayProductVersionId");
	}

	@Override
	public String getName() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getString("name");
	}

	protected Testray1TestrayProductVersion(
		TestrayProject testrayProject, JSONObject jsonObject) {

		super(testrayProject, jsonObject);
	}

}