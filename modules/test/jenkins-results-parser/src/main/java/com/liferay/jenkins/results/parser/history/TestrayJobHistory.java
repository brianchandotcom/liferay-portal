/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import com.liferay.jenkins.results.parser.DownstreamBuildReport;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassReport;
import com.liferay.jenkins.results.parser.TopLevelBuildReport;
import com.liferay.jenkins.results.parser.testray.TestrayBuild;
import com.liferay.jenkins.results.parser.testray.TestrayRoutine;
import com.liferay.jenkins.results.parser.testray.TestrayServer;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayJobHistory extends BaseJobHistory {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		JSONArray batchesJSONArray = new JSONArray();

		for (BatchHistory batchHistory : getBatchHistories()) {
			batchesJSONArray.put(batchHistory.getJSONObject());
		}

		jsonObject.put(
			"batches", batchesJSONArray
		).put(
			"testray_url", String.valueOf(getTestrayURL())
		).put(
			"upstream_branch_name", _latestTestrayBuild.getPortalBranch()
		);

		return jsonObject;
	}

	@Override
	public URL getTestrayURL() {
		if (_testrayURL != null) {
			return _testrayURL;
		}

		if ((_testrayRoutines == null) || _testrayRoutines.isEmpty()) {
			return null;
		}

		TestrayRoutine testrayRoutine = _testrayRoutines.get(0);

		TestrayServer testrayServer = testrayRoutine.getTestrayServer();

		if (testrayServer == null) {
			return null;
		}

		_testrayURL = testrayServer.getURL();

		return _testrayURL;
	}

	public void populate() {
		if (_populated) {
			return;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		if ((_testrayRoutines == null) || _testrayRoutines.isEmpty()) {
			return;
		}

		for (TestrayRoutine testrayRoutine : _testrayRoutines) {
			List<TestrayBuild> testrayBuilds = testrayRoutine.getTestrayBuilds(
				_maxBuildCount);

			if (testrayBuilds.size() > _maxBuildCount) {
				testrayBuilds = testrayBuilds.subList(0, _maxBuildCount);
			}

			for (TestrayBuild testrayBuild : testrayBuilds) {
				TopLevelBuildReport topLevelBuildReport =
					testrayBuild.getTopLevelBuildReport();

				if ((topLevelBuildReport == null) ||
					JenkinsResultsParserUtil.isNullOrEmpty(
						topLevelBuildReport.getResult())) {

					continue;
				}

				boolean latestBuild = false;

				if (_latestTestrayBuild == null) {
					_latestTestrayBuild = testrayBuild;

					latestBuild = true;
				}

				for (DownstreamBuildReport downstreamBuildReport :
						topLevelBuildReport.getDownstreamBuildReports()) {

					String batchName = downstreamBuildReport.getBatchName();

					BatchHistory batchHistory = getBatchHistory(batchName);

					if (batchHistory == null) {
						batchHistory = HistoryFactory.newBatchHistory(
							batchName, this, null);

						addBatchHistory(batchHistory);
					}

					if (!(batchHistory instanceof TestrayBatchHistory)) {
						continue;
					}

					TestrayBatchHistory testrayBatchHistory =
						(TestrayBatchHistory)batchHistory;

					testrayBatchHistory.addBuildReport(
						downstreamBuildReport, latestBuild);
				}
			}
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Test history map populated in ",
				JenkinsResultsParserUtil.toDurationString(
					JenkinsResultsParserUtil.getCurrentTimeMillis() - start)));

		_populated = true;
	}

	public void writeCIHistoryJSONObjectFile(String filePath)
		throws IOException {

		if (!_populated) {
			populate();
		}

		File file = new File(filePath);

		File tempFile = new File(
			file.getParentFile(),
			JenkinsResultsParserUtil.getDistinctTimeStamp());

		try {
			JenkinsResultsParserUtil.write(
				tempFile, String.valueOf(getJSONObject()));

			JenkinsResultsParserUtil.gzip(tempFile, file);
		}
		finally {
			if (tempFile.exists()) {
				JenkinsResultsParserUtil.delete(tempFile);
			}
		}
	}

	public void writeFlakyTestDataJavaScriptFile(String filePath)
		throws IOException {

		if (!_populated) {
			populate();
		}

		JSONArray flakyTestDataJSONArray = new JSONArray();

		flakyTestDataJSONArray.put(
			new String[] {"Name", "Batch Type", "Results", "Status Changes"});

		for (BatchHistory batchHistory : getBatchHistories()) {
			for (TestClassHistory testClassHistory :
					batchHistory.getTestClassHistories()) {

				if (!testClassHistory.isFlaky()) {
					continue;
				}

				JSONArray jsonArray = new JSONArray();

				jsonArray.put(testClassHistory.getTestClassName());

				jsonArray.put(testClassHistory.getBatchName());

				JSONArray statusesJSONArray = new JSONArray();

				if (testClassHistory instanceof TestrayTestClassHistory) {
					TestrayTestClassHistory testrayTestClassHistory =
						(TestrayTestClassHistory)testClassHistory;

					for (TestClassReport testClassReport :
							testrayTestClassHistory.getTestClassReports()) {

						JSONArray statusJSONArray = new JSONArray();

						statusJSONArray.put(
							_fixStatus(testClassReport.getStatus()));

						DownstreamBuildReport downstreamBuildReport =
							testClassReport.getDownstreamBuildReport();

						statusJSONArray.put(
							downstreamBuildReport.getBuildURL());

						statusesJSONArray.put(statusJSONArray);
					}
				}

				jsonArray.put(statusesJSONArray);

				jsonArray.put(testClassHistory.getStatusChanges());

				flakyTestDataJSONArray.put(jsonArray);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var flakyTestData = ");
		sb.append(flakyTestDataJSONArray);
		sb.append(";\nvar flakyTestDataGeneratedDate = new Date(");
		sb.append(JenkinsResultsParserUtil.getCurrentTimeMillis());
		sb.append(");\n");

		JSONArray testrayRoutinesJSONArray = new JSONArray();

		if ((_testrayRoutines != null) && !_testrayRoutines.isEmpty()) {
			for (TestrayRoutine testrayRoutine : _testrayRoutines) {
				JSONObject testrayRoutineJSONObject = new JSONObject();

				testrayRoutineJSONObject.put(
					"name", testrayRoutine.getName()
				).put(
					"url", testrayRoutine.getURL()
				);

				testrayRoutinesJSONArray.put(testrayRoutineJSONObject);
			}
		}

		sb.append("var testrayRoutines = ");
		sb.append(testrayRoutinesJSONArray);
		sb.append(";");

		JenkinsResultsParserUtil.write(filePath, sb.toString());
	}

	protected TestrayJobHistory(
		int maxBuildCount, String portalUpstreamBranchName,
		List<TestrayRoutine> testrayRoutines) {

		super(portalUpstreamBranchName);

		_maxBuildCount = maxBuildCount;
		_testrayRoutines = testrayRoutines;
	}

	private String _fixStatus(String status) {
		status = status.replace("FIXED", "PASSED");
		status = status.replace("REGRESSION", "FAILED");

		return status;
	}

	private TestrayBuild _latestTestrayBuild;
	private final int _maxBuildCount;
	private boolean _populated;
	private final List<TestrayRoutine> _testrayRoutines;
	private URL _testrayURL;

}