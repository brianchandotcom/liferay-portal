/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestrayFactor implements TestrayFactor {

	public Category getCategory() {
		return _category;
	}

	public Long getID() {
		return _jsonObject.getLong("id");
	}

	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	public Option getOption() {
		return _option;
	}

	public TestrayServer getTestrayServer() {
		return _testrayServer;
	}

	protected BaseTestrayFactor(
		TestrayServer testrayServer, JSONObject jsonObject) {

		_testrayServer = testrayServer;
		_jsonObject = jsonObject;

		JSONObject categoryJSONObject = jsonObject.getJSONObject(
			"factorCategoryToFactors");

		_category = new Category(categoryJSONObject);

		JSONObject optionJSONObject = jsonObject.optJSONObject(
			"factorOptionToFactors");

		if (optionJSONObject != null) {
			_option = new Option(optionJSONObject);
		}
		else {
			_option = null;
		}
	}

	private final Category _category;
	private final JSONObject _jsonObject;
	private final Option _option;
	private final TestrayServer _testrayServer;

}