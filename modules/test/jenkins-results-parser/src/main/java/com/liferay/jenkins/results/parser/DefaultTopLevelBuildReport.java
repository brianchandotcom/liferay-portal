/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class DefaultTopLevelBuildReport extends BaseTopLevelBuildReport {

	@Override
	public JSONObject getBuildReportJSONObject() {
		if (buildReportJSONObject != null) {
			return buildReportJSONObject;
		}

		buildReportJSONObject = new JSONObject();

		buildReportJSONObject.put(
			"buildParameters", _topLevelBuild.getParameters());
		buildReportJSONObject.put("buildURL", _topLevelBuild.getBuildURL());
		buildReportJSONObject.put("duration", _topLevelBuild.getDuration());
		buildReportJSONObject.put("result", _topLevelBuild.getResult());
		buildReportJSONObject.put("startTime", _topLevelBuild.getStartTime());

		StopWatchRecordsGroup stopWatchRecordsGroup =
			_topLevelBuild.getStopWatchRecordsGroup();

		if (stopWatchRecordsGroup != null) {
			buildReportJSONObject.put(
				"stopWatchRecords", stopWatchRecordsGroup.getJSONArray());
		}

		List<Callable<JSONObject>> callables = new ArrayList<>();

		ParallelExecutor<JSONObject> parallelExecutor = new ParallelExecutor<>(
			callables, _executorService, "getBuildReportJSONObject");

		for (final Build build : _topLevelBuild.getDownstreamBuilds(null)) {
			if (build instanceof BatchBuild) {
				BatchBuild batchBuild = (BatchBuild)build;

				for (final AxisBuild axisBuild :
						batchBuild.getDownstreamAxisBuilds()) {

					JenkinsMaster jenkinsMaster = axisBuild.getJenkinsMaster();

					callables.add(
						new ParallelExecutor.SequentialCallable<JSONObject>(
							jenkinsMaster.getName()) {

							@Override
							public JSONObject call() throws Exception {
								return _getDownstreamBuildJSONObject(axisBuild);
							}

						});
				}
			}
			else {
				JenkinsMaster jenkinsMaster = build.getJenkinsMaster();

				callables.add(
					new ParallelExecutor.SequentialCallable<JSONObject>(
						jenkinsMaster.getName()) {

						@Override
						public JSONObject call() throws Exception {
							return _getDownstreamBuildJSONObject(build);
						}

					});
			}
		}

		Map<String, List<JSONObject>> downstreamBuildMap = new HashMap<>();

		try {
			for (JSONObject jsonObject : parallelExecutor.execute(_TIMEOUT)) {
				String batchName = "default";

				Matcher matcher = _axisNamePattern.matcher(
					jsonObject.optString("axisName", ""));

				if (matcher.find()) {
					batchName = matcher.group("batchName");
				}

				List<JSONObject> downstreamBuildJSONObjects =
					downstreamBuildMap.getOrDefault(
						batchName, new ArrayList<JSONObject>());

				downstreamBuildJSONObjects.add(jsonObject);

				downstreamBuildMap.put(batchName, downstreamBuildJSONObjects);
			}
		}
		catch (TimeoutException timeoutException) {
			throw new RuntimeException(timeoutException);
		}

		JSONArray batchesJSONArray = new JSONArray();

		for (Map.Entry<String, List<JSONObject>> downstreamBuildEntry :
				downstreamBuildMap.entrySet()) {

			JSONObject batchJSONObject = new JSONObject();

			batchJSONObject.put(
				"batchName", downstreamBuildEntry.getKey()
			).put(
				"builds", downstreamBuildEntry.getValue()
			);

			batchesJSONArray.put(batchJSONObject);
		}

		buildReportJSONObject.put("batches", batchesJSONArray);

		buildReportJSONObject.put(
			"testSuiteName", _topLevelBuild.getTestSuiteName());

		return buildReportJSONObject;
	}

	protected DefaultTopLevelBuildReport(TopLevelBuild topLevelBuild) {
		super(topLevelBuild);

		_topLevelBuild = topLevelBuild;

		_jenkinsConsoleLocalFile = new File(
			System.getenv("WORKSPACE"),
			JenkinsResultsParserUtil.getDistinctTimeStamp());

		try {
			JenkinsResultsParserUtil.write(
				_jenkinsConsoleLocalFile, topLevelBuild.getConsoleText());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	protected JSONObject getBuildJSONObject() {
		return _topLevelBuild.getBuildJSONObject();
	}

	@Override
	protected File getJenkinsConsoleLocalFile() {
		return _jenkinsConsoleLocalFile;
	}

	private JSONObject _getDownstreamBuildJSONObject(Build build) {
		JSONObject downstreamBuildJSONObject = new JSONObject();

		if (build instanceof AxisBuild) {
			AxisBuild axisBuild = (AxisBuild)build;

			downstreamBuildJSONObject.put("axisName", axisBuild.getAxisName());
		}
		else if (build instanceof DownstreamBuild) {
			DownstreamBuild downstreamBuild = (DownstreamBuild)build;

			downstreamBuildJSONObject.put(
				"axisName", downstreamBuild.getAxisName());
		}

		downstreamBuildJSONObject.put(
			"buildURL", build.getBuildURL()
		).put(
			"duration", build.getDuration()
		).put(
			"result", build.getResult()
		).put(
			"startTime", build.getStartTime()
		);

		StopWatchRecordsGroup stopWatchRecordsGroup =
			build.getStopWatchRecordsGroup();

		if (stopWatchRecordsGroup != null) {
			downstreamBuildJSONObject.put(
				"stopWatchRecords", stopWatchRecordsGroup.getJSONArray());
		}

		JSONArray testResultsJSONArray = new JSONArray();

		for (TestResult testResult : build.getTestResults(null)) {
			testResultsJSONArray.put(_getTestResultJSONObject(testResult));
		}

		downstreamBuildJSONObject.put("testResults", testResultsJSONArray);

		return downstreamBuildJSONObject;
	}

	private JSONObject _getTestResultJSONObject(TestResult testResult) {
		JSONObject testResultJSONObject = new JSONObject();

		testResultJSONObject.put("duration", testResult.getDuration());

		String errorDetails = testResult.getErrorDetails();

		if (errorDetails != null) {
			if (errorDetails.contains("\n")) {
				int index = errorDetails.indexOf("\n");

				errorDetails = errorDetails.substring(0, index);
			}

			if (errorDetails.length() > 200) {
				errorDetails = errorDetails.substring(0, 200);
			}

			testResultJSONObject.put("errorDetails", errorDetails);
		}

		testResultJSONObject.put(
			"name", testResult.getDisplayName()
		).put(
			"status", testResult.getStatus()
		).put(
			"testTaskName", _getTestTaskName(testResult)
		);

		return testResultJSONObject;
	}

	private String _getTestTaskName(TestResult testResult) {
		if (!(testResult instanceof JUnitTestResult)) {
			return null;
		}

		TestClassResult testClassResult = testResult.getTestClassResult();

		if (testClassResult == null) {
			return null;
		}

		TestClass testClass = testClassResult.getTestClass();

		if (testClass == null) {
			return null;
		}

		Matcher matcher = _testClassFilePathPattern.matcher(
			String.valueOf(testClass.getTestClassFile()));

		if (!matcher.find()) {
			return null;
		}

		String relativePath = matcher.group("relativePath");

		return JenkinsResultsParserUtil.combine(
			relativePath.replaceAll("\\/", ":"), ":", matcher.group("type"));
	}

	private static final long _TIMEOUT = 60L * 60L * 6L;

	private static final Pattern _axisNamePattern = Pattern.compile(
		"(?<batchName>[^/]+)/[^/]+/[^/]+");
	private static final ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(10, true);
	private static final Pattern _testClassFilePathPattern = Pattern.compile(
		".+/modules(?<relativePath>/.+)/src/(?<type>test|testIntegration)/.*");

	private final File _jenkinsConsoleLocalFile;
	private final TopLevelBuild _topLevelBuild;

}