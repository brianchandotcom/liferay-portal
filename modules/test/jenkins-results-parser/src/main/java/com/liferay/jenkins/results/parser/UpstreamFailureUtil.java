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

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class UpstreamFailureUtil {

	protected static List<String> getUpstreamJobFailures(String type) {
		List<String> upstreamFailures = new ArrayList<>();

		JSONArray failedBatchesJSONArray =
			upstreamFailuresJobJSONObject.getJSONArray("failedBatches");

		for (int i = 0; i < failedBatchesJSONArray.length(); i++) {
			JSONObject failedBatchJSONObject =
				failedBatchesJSONArray.getJSONObject(i);

			JSONArray failedTestsJSONArray = failedBatchJSONObject.getJSONArray(
				"failedTests");

			String jobVariant = failedBatchJSONObject.getString("jobVariant");

			if (type.equals("build")) {
				if (failedTestsJSONArray.length() == 0) {
					upstreamFailures.add(
						JenkinsResultsParserUtil.combine(
							jobVariant, ",",
							failedBatchJSONObject.getString("result")));
				}
			}
			else if (type.equals("test")) {
				for (int j = 0; j < failedTestsJSONArray.length(); j++) {
					Object object = failedTestsJSONArray.get(j);

					upstreamFailures.add(
						JenkinsResultsParserUtil.combine(
							object.toString(), ",", jobVariant));
				}
			}
		}

		return upstreamFailures;
	}

	protected static String getUpstreamJobFailuresSHA() {
		try {
			return upstreamFailuresJobJSONObject.getString("SHA");
		}
		catch (JSONException jsone) {
			System.out.println(
				"Unable to get upstream acceptance failure data");

			jsone.printStackTrace();

			return "";
		}
	}

	protected static boolean isBuildFailingInUpstreamJob(Build build) {
		try {
			List<TestResult> testResults = new ArrayList<>();

			testResults.addAll(build.getTestResults("FAILED"));
			testResults.addAll(build.getTestResults("REGRESSION"));

			if (testResults.isEmpty()) {
				String jobVariant = build.getJobVariant();
				String result = build.getResult();

				if (jobVariant.contains("/")) {
					int index = jobVariant.lastIndexOf("/");

					jobVariant = jobVariant.substring(0, index);
				}

				for (String upstreamJobFailure :
						getUpstreamJobFailures("build")) {

					if (upstreamJobFailure.contains(jobVariant) &&
						upstreamJobFailure.contains(result)) {

						return true;
					}
				}
			}

			return false;
		}
		catch (Exception e) {
			System.out.println(
				"Unable to get upstream acceptance failure data.");

			e.printStackTrace();

			return false;
		}
	}

	protected static boolean isTestFailingInUpstreamJob(TestResult testResult) {
		try {
			for (String failure : getUpstreamJobFailures("test")) {
				Build build = testResult.getBuild();

				String jobVariant = build.getJobVariant();

				if (jobVariant.contains("/")) {
					int index = jobVariant.lastIndexOf("/");

					jobVariant = jobVariant.substring(0, index);
				}

				if (failure.contains(jobVariant) &&
					failure.contains(testResult.getDisplayName())) {

					return true;
				}
			}

			return false;
		}
		catch (Exception e) {
			System.out.println(
				"Unable to get upstream acceptance failure data.");

			e.printStackTrace();

			return false;
		}
	}

	protected static void loadUpstreamJobFailuresJSONObject(Build build) {
		String jobName = build.getJobName();

		loadUpstreamJobFailuresJSONObject(jobName);
	}

	protected static void loadUpstreamJobFailuresJSONObject(String jobName) {
		try {
			if (jobName.contains("pullrequest")) {
				String upstreamJobName = jobName.replace(
					"pullrequest", "upstream");

				String url = JenkinsResultsParserUtil.getLocalURL(
					UPSTREAM_FAILURES_JOB_BASE_URL + upstreamJobName +
						"/builds/latest/test.results.json");

				upstreamFailuresJobJSONObject =
					JenkinsResultsParserUtil.toJSONObject(url);
			}
		}
		catch (IOException ioe) {
			System.out.println(
				"Unable to set upstream acceptance failure data.");

			ioe.printStackTrace();
		}
	}

	protected static final String UPSTREAM_FAILURES_JOB_BASE_URL =
		"https://test-1-0.liferay.com/userContent/testResults/";

	protected static JSONObject upstreamFailuresJobJSONObject = new JSONObject(
		"{\"SHA\":\"\",\"failedBatches\":[]}");

}