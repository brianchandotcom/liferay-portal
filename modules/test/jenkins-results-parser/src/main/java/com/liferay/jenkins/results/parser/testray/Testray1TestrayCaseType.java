/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class Testray1TestrayCaseType extends TestrayCaseType {

	@Override
	public Long getID() {
		return _jsonObject.getLong("testrayCaseTypeId");
	}

	@Override
	public String getName() {
		return _jsonObject.getString("name");
	}

	protected Testray1TestrayCaseType(
		TestrayServer testrayServer, JSONObject jsonObject) {

		super(testrayServer, jsonObject);

		_jsonObject = jsonObject;
	}

	private final JSONObject _jsonObject;

}