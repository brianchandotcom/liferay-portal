/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class Testray1TestrayRun extends TestrayRun {

	@Override
	public long getID() {
		return _id;
	}

	public String getRunIDString() {
		List<String> factorValues = new ArrayList<>();

		for (Factor factor : getFactors()) {
			factorValues.add(factor.getValue());
		}

		return JenkinsResultsParserUtil.join("|", factorValues);
	}

	protected Testray1TestrayRun(
		TestrayBuild testrayBuild, JSONObject jsonObject) {

		super(testrayBuild, jsonObject);

		_id = jsonObject.getLong("testrayRunId");
	}

	protected Testray1TestrayRun(
		TestrayBuild testrayBuild, String batchName,
		List<File> propertiesFiles) {

		super(testrayBuild, batchName, propertiesFiles);

		JSONObject jsonObject = getJSONObject();

		_id = jsonObject.getLong("testrayRunId");
	}

	@Override
	protected void initializeFactorsByJSONObject(JSONObject jsonObject) {
		factors = new ArrayList<>();

		if (jsonObject == null) {
			return;
		}

		JSONArray testrayFactorsJSONArray = jsonObject.optJSONArray(
			"testrayFactors");

		if (testrayFactorsJSONArray == null) {
			return;
		}

		factors = new ArrayList<>();

		for (int i = 0; i < testrayFactorsJSONArray.length(); i++) {
			JSONObject testrayFactorJSONObject =
				testrayFactorsJSONArray.getJSONObject(i);

			factors.add(
				new Factor(
					testrayFactorJSONObject.getString(
						"testrayFactorCategoryName"),
					testrayFactorJSONObject.getString(
						"testrayFactorOptionName")));
		}
	}

	private final long _id;

}