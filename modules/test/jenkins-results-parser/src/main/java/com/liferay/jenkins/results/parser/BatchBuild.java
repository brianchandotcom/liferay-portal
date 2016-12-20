/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public class BatchBuild extends BaseBuild {

	@Override
	public List<TestResult> getTestResults() {
		String status = getStatus();

		if (!status.equals("completed")) {
			return null;
		}

		List<TestResult> testResults = new ArrayList<>();

		JSONObject testReportJSONObject = getTestReportJSONObject();

		JSONArray childReportsJSONArray = testReportJSONObject.getJSONArray(
			"childReports");

		for (int i = 0; i < childReportsJSONArray.length(); i++) {
			JSONObject childReportJSONObject =
				childReportsJSONArray.getJSONObject(i);

			JSONObject resultJSONObject = childReportJSONObject.getJSONObject(
				"result");

			JSONArray suitesJSONArray = resultJSONObject.getJSONArray("suites");

			for (int j = 0; j < suitesJSONArray.length(); j++) {
				JSONObject suiteJSONObject = suitesJSONArray.getJSONObject(j);

				JSONArray casesJSONArray = suiteJSONObject.getJSONArray(
					"cases");

				for (int k = 0; k < casesJSONArray.length(); k++) {
					JSONObject caseJSONObject = casesJSONArray.getJSONObject(k);

					String testClassName = caseJSONObject.getString(
						"className");

					int x = testClassName.lastIndexOf(".");

					String testSimpleClassName = testClassName.substring(x + 1);

					String testPackageName = testClassName.substring(0, x);

					String testMethodName = caseJSONObject.getString("name");

					testMethodName = testMethodName.replace("[", "_");
					testMethodName = testMethodName.replace("]", "_");
					testMethodName = testMethodName.replace("#", "_");

					if (testPackageName.equals("junit.framework")) {
						testMethodName = testMethodName.replace(".", "_");
					}

					testResults.add(
						new TestResult(
							testSimpleClassName, null, testMethodName,
							caseJSONObject.getString("status")));
				}
			}
		}

		return testResults;
	}

	protected BatchBuild(String url) {
		this(url, null);
	}

	protected BatchBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	protected List<String> findDownstreamBuildsInConsoleText() {
		return Collections.emptyList();
	}

}