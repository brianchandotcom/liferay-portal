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

import java.io.File;
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

	public static int getUpstreamJobFailuresBuildNumber(
		TopLevelBuild topLevelBuild) {

		try {
			JSONObject upstreamJobFailuresJSONObject =
				getUpstreamJobFailuresJSONObject(topLevelBuild);

			return upstreamJobFailuresJSONObject.getInt("buildNumber");
		}
		catch (JSONException jsonException) {
			System.out.println(
				"Unable to get upstream acceptance failure data");

			jsonException.printStackTrace();

			return 0;
		}
	}

	public static int getUpstreamJobFailuresBuildNumber(
		TopLevelBuild topLevelBuild, String sha) {

		int buildNumber = _getLastUpstreamBuildNumber(topLevelBuild);

		int oldestBuildNumber = Math.max(0, buildNumber - 10);

		String jobURL = getUpstreamJobFailuresJobURL(topLevelBuild);

		while (buildNumber > oldestBuildNumber) {
			String upstreamBranchSHA =
				JenkinsResultsParserUtil.getBuildParameter(
					jobURL + "/" + buildNumber, "PORTAL_GIT_COMMIT");

			if (upstreamBranchSHA.equals(sha)) {
				return buildNumber;
			}

			buildNumber--;
		}

		return 0;
	}

	public static String getUpstreamJobFailuresJobURL(
		TopLevelBuild topLevelBuild) {

		try {
			JSONObject upstreamJobFailuresJSONObject =
				getUpstreamJobFailuresJSONObject(topLevelBuild);

			return upstreamJobFailuresJSONObject.getString("jobURL");
		}
		catch (JSONException jsonException) {
			System.out.println(
				"Unable to get upstream acceptance failure data");

			jsonException.printStackTrace();

			return "";
		}
	}

	public static JSONObject getUpstreamJobFailuresJSONObject(
		TopLevelBuild topLevelBuild) {

		if (_upstreamFailuresJobJSONObject == null) {
			initUpstreamJobFailuresJSONObject(topLevelBuild);
		}

		return _upstreamFailuresJobJSONObject;
	}

	public static String getUpstreamJobFailuresSHA(
		TopLevelBuild topLevelBuild) {

		try {
			JSONObject upstreamJobFailuresJSONObject =
				getUpstreamJobFailuresJSONObject(topLevelBuild);

			return upstreamJobFailuresJSONObject.getString("SHA");
		}
		catch (JSONException jsonException) {
			System.out.println(
				"Unable to get upstream acceptance failure data");

			jsonException.printStackTrace();

			return "";
		}
	}

	public static void initUpstreamJobFailuresJSONObject(
		TopLevelBuild topLevelBuild) {

		_upstreamFailuresJobJSONObject = _defaultUpstreamFailuresJSONObject;

		if (!topLevelBuild.isCompareToUpstream()) {
			return;
		}

		try {
			if (_upstreamJobFailuresJSONFile.exists()) {
				String fileContent = JenkinsResultsParserUtil.read(
					_upstreamJobFailuresJSONFile);

				_upstreamFailuresJobJSONObject = new JSONObject(fileContent);
			}
			else {
				_upstreamFailuresJobJSONObject =
					_getUpstreamJobFailuresJSONObject(topLevelBuild);

				System.out.println(
					"Caching upstream test results in: " +
						_upstreamJobFailuresJSONFile);

				JenkinsResultsParserUtil.write(
					_upstreamJobFailuresJSONFile,
					_upstreamFailuresJobJSONObject.toString());
			}

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Comparing with test results from ",
					topLevelBuild.getAcceptanceUpstreamJobURL(), "/",
					String.valueOf(
						_upstreamFailuresJobJSONObject.getInt("buildNumber")),
					" at SHA ",
					_upstreamFailuresJobJSONObject.getString("SHA")));
		}
		catch (Exception exception) {
			System.out.println(exception.getMessage());

			System.out.println(
				"Unable to load upstream acceptance failure data");

			_upstreamComparisonAvailable = false;
		}
	}

	public static boolean isBuildFailingInUpstreamJob(Build build) {
		if (!_upstreamComparisonAvailable || !build.isCompareToUpstream()) {
			return false;
		}

		try {
			List<TestResult> testResults = new ArrayList<>();

			testResults.addAll(build.getTestResults("FAILED"));
			testResults.addAll(build.getTestResults("REGRESSION"));

			if (testResults.isEmpty()) {
				return _isBuildFailingInUpstreamJob(build);
			}

			for (TestResult testResult : testResults) {
				if (testResult.isUniqueFailure()) {
					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			System.out.println(
				"Unable to get upstream acceptance failure data.");

			exception.printStackTrace();

			return false;
		}
	}

	public static boolean isTestFailingInUpstreamJob(TestResult testResult) {
		Build build = testResult.getBuild();

		if (!_upstreamComparisonAvailable || !build.isCompareToUpstream()) {
			return false;
		}

		TopLevelBuild topLevelBuild = build.getTopLevelBuild();

		try {
			String jobVariant = build.getJobVariant();

			jobVariant = _formatJobVariant(jobVariant);

			for (String failure :
					_getUpstreamJobFailures("test", topLevelBuild)) {

				if (failure.equals(
						_formatUpstreamTestFailure(
							jobVariant, testResult.getDisplayName()))) {

					return true;
				}
			}

			return false;
		}
		catch (Exception exception) {
			System.out.println(
				"Unable to get upstream acceptance failure data.");

			exception.printStackTrace();

			return false;
		}
	}

	public static boolean isUpstreamComparisonAvailable(
		TopLevelBuild topLevelBuild) {

		initUpstreamJobFailuresJSONObject(topLevelBuild);

		return _upstreamComparisonAvailable;
	}

	public static void resetUpstreamJobFailuresJSONObject() {
		if (_upstreamJobFailuresJSONFile.exists()) {
			_upstreamJobFailuresJSONFile.delete();
		}

		_upstreamFailuresJobJSONObject = null;
	}

	private static String _formatJobVariant(String jobVariant) {
		jobVariant = jobVariant.replaceAll("(.*)/.*", "$1");

		return jobVariant.replaceAll("_stable$", "");
	}

	private static String _formatUpstreamBuildFailure(
		String jobVariant, String testResult) {

		return JenkinsResultsParserUtil.combine(jobVariant, ",", testResult);
	}

	private static String _formatUpstreamTestFailure(
		String jobVariant, String testName) {

		return JenkinsResultsParserUtil.combine(testName, ",", jobVariant);
	}

	private static int _getLastCompletedUpstreamBuildNumber(
		TopLevelBuild topLevelBuild) {

		try {
			JSONObject lastCompletedBuildJSONObject =
				JenkinsResultsParserUtil.toJSONObject(
					topLevelBuild.getAcceptanceUpstreamJobURL() +
						"/lastCompletedBuild/api/json?tree=number",
					false);

			return lastCompletedBuildJSONObject.getInt("number");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static int _getLastUpstreamBuildNumber(
		TopLevelBuild topLevelBuild) {

		try {
			JSONObject lastBuildJSONObject =
				JenkinsResultsParserUtil.toJSONObject(
					topLevelBuild.getAcceptanceUpstreamJobURL() +
						"/lastBuild/api/json?tree=number",
					false);

			return lastBuildJSONObject.getInt("number");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static List<String> _getUpstreamJobFailures(
		String type, TopLevelBuild topLevelBuild) {

		List<String> upstreamFailures = new ArrayList<>();

		JSONObject upstreamJobFailuresJSONObject =
			getUpstreamJobFailuresJSONObject(topLevelBuild);

		JSONArray failedBatchesJSONArray =
			upstreamJobFailuresJSONObject.optJSONArray("failedBatches");

		if (failedBatchesJSONArray == null) {
			return upstreamFailures;
		}

		for (int i = 0; i < failedBatchesJSONArray.length(); i++) {
			JSONObject failedBatchJSONObject =
				failedBatchesJSONArray.getJSONObject(i);

			String jobVariant = failedBatchJSONObject.getString("jobVariant");

			jobVariant = _formatJobVariant(jobVariant);

			if (type.equals("build")) {
				upstreamFailures.add(
					_formatUpstreamBuildFailure(
						jobVariant, failedBatchJSONObject.getString("result")));
			}
			else if (type.equals("test")) {
				JSONArray failedTestsJSONArray =
					failedBatchJSONObject.getJSONArray("failedTests");

				for (int j = 0; j < failedTestsJSONArray.length(); j++) {
					Object object = failedTestsJSONArray.get(j);

					upstreamFailures.add(
						_formatUpstreamTestFailure(
							jobVariant, object.toString()));
				}
			}
		}

		return upstreamFailures;
	}

	private static JSONObject _getUpstreamJobFailuresJSONObject(
			String jobName, String buildNumber)
		throws IOException {

		return JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(
				JenkinsResultsParserUtil.combine(
					_URL_BASE_UPSTREAM_FAILURES_JOB, jobName, "/builds/",
					buildNumber, "/test.results.json")),
			false, 5000);
	}

	private static JSONObject _getUpstreamJobFailuresJSONObject(
			TopLevelBuild topLevelBuild)
		throws IllegalStateException, IOException {

		int buildNumber = _getLastCompletedUpstreamBuildNumber(topLevelBuild);

		int oldestBuildNumber = Math.max(0, buildNumber - 20);

		String acceptanceUpstreamJobName =
			topLevelBuild.getAcceptanceUpstreamJobName();

		while (buildNumber > oldestBuildNumber) {
			JSONObject jsonObject = _getUpstreamJobFailuresJSONObject(
				acceptanceUpstreamJobName, String.valueOf(buildNumber));

			String sha = jsonObject.getString("SHA");

			GitWorkingDirectory gitWorkingDirectory =
				GitWorkingDirectoryFactory.newGitWorkingDirectory(
					topLevelBuild.getBranchName(), (File)null,
					topLevelBuild.getBaseGitRepositoryName());

			if (gitWorkingDirectory.refContainsSHA("HEAD", sha)) {
				return jsonObject;
			}

			buildNumber--;
		}

		throw new IllegalStateException(
			"Unable to find comparable upstream test results");
	}

	private static boolean _isBuildFailingInUpstreamJob(Build build) {
		String jobVariant = build.getJobVariant();

		if (jobVariant == null) {
			return false;
		}

		String result = build.getResult();

		if (result == null) {
			return false;
		}

		jobVariant = _formatJobVariant(jobVariant);

		TopLevelBuild topLevelBuild = build.getTopLevelBuild();

		for (String upstreamJobFailure :
				_getUpstreamJobFailures("build", topLevelBuild)) {

			if (upstreamJobFailure.equals(
					_formatUpstreamBuildFailure(jobVariant, result))) {

				return true;
			}
		}

		return false;
	}

	private static final String _URL_BASE_UPSTREAM_FAILURES_JOB =
		"https://test-1-0.liferay.com/userContent/testResults/";

	private static final JSONObject _defaultUpstreamFailuresJSONObject =
		new JSONObject("{\"SHA\":\"\",\"failedBatches\":[]}");
	private static boolean _upstreamComparisonAvailable = true;
	private static JSONObject _upstreamFailuresJobJSONObject;
	private static final File _upstreamJobFailuresJSONFile = new File(
		System.getenv("WORKSPACE"), "test.results.json");

}