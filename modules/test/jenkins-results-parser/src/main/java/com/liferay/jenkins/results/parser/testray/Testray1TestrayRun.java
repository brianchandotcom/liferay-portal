/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class Testray1TestrayRun extends TestrayRun {

	@Override
	public String getRunID() {
		TestrayBuild testrayBuild = getTestrayBuild();

		JSONObject runsJSONObject = testrayBuild.getRunsJSONObject();

		if (runsJSONObject == null) {
			return null;
		}

		JSONArray dataJSONArray = runsJSONObject.optJSONArray("data");

		if ((dataJSONArray == JSONObject.NULL) || dataJSONArray.isEmpty()) {
			return null;
		}

		Map<String, String> factorValues = new HashMap<>();

		for (Factor factor : getFactors()) {
			String factorValue = factor.getValue();

			factorValues.put(factor.getName(), factorValue.toLowerCase());
		}

		for (int i = 0; i < dataJSONArray.length(); i++) {
			JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

			JSONArray testrayFactorsJSONArray = dataJSONObject.optJSONArray(
				"testrayFactors");

			if ((testrayFactorsJSONArray == JSONObject.NULL) ||
				testrayFactorsJSONArray.isEmpty()) {

				continue;
			}

			Map<String, String> factorOptionNames = new HashMap<>();

			for (int j = 0; j < testrayFactorsJSONArray.length(); j++) {
				JSONObject testrayFactorJSONObject =
					testrayFactorsJSONArray.getJSONObject(j);

				String factorCategoryName = testrayFactorJSONObject.optString(
					"testrayFactorCategoryName");
				String factorOptionName = testrayFactorJSONObject.optString(
					"testrayFactorOptionName");

				if (JenkinsResultsParserUtil.isNullOrEmpty(
						factorCategoryName) ||
					JenkinsResultsParserUtil.isNullOrEmpty(factorOptionName)) {

					continue;
				}

				factorOptionNames.put(
					factorCategoryName, factorOptionName.toLowerCase());
			}

			if (factorValues.equals(factorOptionNames)) {
				return dataJSONObject.getString("testrayRunId");
			}
		}

		return null;
	}

	protected Testray1TestrayRun(
		TestrayBuild testrayBuild, String batchName,
		List<File> propertiesFiles) {

		super(testrayBuild, batchName, propertiesFiles);
	}

}