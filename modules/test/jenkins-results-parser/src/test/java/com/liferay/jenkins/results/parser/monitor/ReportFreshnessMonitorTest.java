/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.monitor;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsMasterTestUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.ReflectionTestUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Brittney Nguyen
 */
public class ReportFreshnessMonitorTest
	extends com.liferay.jenkins.results.parser.Test {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		mockEnvironment(Collections.<String, String>emptyMap());

		JenkinsMasterTestUtil.getJenkinsCohortProperties(_COHORT_NAME, 2);
	}

	@After
	@Override
	public void tearDown() {
		super.tearDown();

		JenkinsMasterTestUtil.resetCaches();

		JenkinsResultsParserUtil.setBuildProperties(new Properties());
	}

	@Test
	public void testExecuteAtOverdueBoundary() throws Exception {
		mockURLReaders();

		setURLReaderOutput(
			_newReportData(
				JenkinsResultsParserUtil.getCurrentTimeMillis() - (5400 * 1000),
				"dataGeneratedDate"),
			_URL);

		MonitorResult monitorResult = _execute(_newMonitorProperties());

		testEquals(MonitorResult.Status.OK, monitorResult.getStatus());

		Map<String, String> metrics = monitorResult.getMetrics();

		testEquals("5400", metrics.get("output.age.seconds"));
	}

	@Test
	public void testExecuteBlacklistedJenkinsMasters() throws Exception {
		mockURLReaders();

		_setStaleReportData();

		_setBuildJSONObjects(
			_MASTER_NAME_1,
			_newBuildJSONObject(
				false, _MASTER_NAME_1, _REPORT_NAME,
				RandomTestUtil.randomString(), 2000));

		JSONObject buildJSONObject = _newBuildJSONObject(
			false, _MASTER_NAME_2, _REPORT_NAME, "FAILURE", 1000);

		_setBuildJSONObjects(_MASTER_NAME_2, buildJSONObject);

		ReflectionTestUtil.setFieldValue(
			JenkinsMaster.getInstance(_MASTER_NAME_1), "_blacklisted", true);

		_testExecute(
			Collections.singletonList(_getRemoteBuildURL(buildJSONObject)),
			"The latest \"generate-reports\" build to run it ended in FAILURE");

		verifyURLReaderAttemptsCount(
			0,
			JenkinsResultsParserUtil.combine(
				"http://", _MASTER_NAME_1, "/job/generate-reports/api/json"));

		ReflectionTestUtil.setFieldValue(
			JenkinsMaster.getInstance(_MASTER_NAME_2), "_blacklisted", true);

		_testExecute(
			null,
			JenkinsResultsParserUtil.combine(
				"Every master in cohort ", _COHORT_NAME, " is blacklisted"));
	}

	@Test
	public void testExecuteFileURL() throws Exception {
		long generatedTimestamp =
			JenkinsResultsParserUtil.getCurrentTimeMillis() - (600 * 1000);

		File reportDataFile = File.createTempFile("jenkins-data", ".js");

		reportDataFile.deleteOnExit();

		JenkinsResultsParserUtil.write(
			reportDataFile,
			_newReportData(generatedTimestamp, "jenkinsDataGeneratedDate"));

		Properties monitorProperties = _newMonitorProperties();

		monitorProperties.setProperty(
			"monitor[a].parameter[url]",
			"file://" + reportDataFile.getAbsolutePath());

		MonitorResult monitorResult = _execute(monitorProperties);

		testEquals(MonitorResult.Status.OK, monitorResult.getStatus());

		Map<String, String> metrics = monitorResult.getMetrics();

		testEquals(
			String.valueOf(generatedTimestamp),
			metrics.get("generated.timestamp"));
	}

	@Test
	public void testExecuteGeneratedDateFuture() throws Exception {
		mockURLReaders();

		setURLReaderOutput(
			_newReportData(4102444800000L, "dataGeneratedDate"), _URL);

		MonitorResult monitorResult = _execute(_newMonitorProperties());

		testEquals(
			JenkinsResultsParserUtil.combine(
				"Report ", _REPORT_NAME, " was generated at Dec 31, 2099 ",
				"4:00:00 PM PST, which is in the future"),
			monitorResult.getMessage());
		testEquals(MonitorResult.Status.CRITICAL, monitorResult.getStatus());

		Map<String, String> metrics = monitorResult.getMetrics();

		Assert.assertTrue(metrics.isEmpty());
	}

	@Test
	public void testExecuteGeneratedDateOldestRules() throws Exception {
		mockURLReaders();

		long currentTimeMillis =
			JenkinsResultsParserUtil.getCurrentTimeMillis();

		long oldestTimestamp = currentTimeMillis - (7200 * 1000);

		setURLReaderOutput(
			JenkinsResultsParserUtil.combine(
				_newReportData(currentTimeMillis, "dataGeneratedDate"), "\n",
				_newReportData(oldestTimestamp, "dataGeneratedDate"), "\n",
				_newReportData(
					currentTimeMillis - (60 * 1000), "dataGeneratedDate")),
			_URL);

		_setBuildJSONObjects(_MASTER_NAME_1);
		_setBuildJSONObjects(_MASTER_NAME_2);

		MonitorResult monitorResult = _execute(_newMonitorProperties());

		testEquals(MonitorResult.Status.WARN, monitorResult.getStatus());

		Map<String, String> metrics = monitorResult.getMetrics();

		testEquals(
			String.valueOf(oldestTimestamp),
			metrics.get("generated.timestamp"));
	}

	@Test
	public void testExecuteGeneratedDateVariant() throws Exception {
		_testExecuteGeneratedDateVariant("dataGeneratedDate");
		_testExecuteGeneratedDateVariant("flakyTestDataGeneratedDate");
		_testExecuteGeneratedDateVariant("jenkinsDataGeneratedDate");
		_testExecuteGeneratedDateVariant("testrayDataGeneratedDate");
	}

	@Test
	public void testExecuteLatestBuild() throws Exception {
		mockURLReaders();

		_setStaleReportData();

		JSONObject buildJSONObject1 = _newBuildJSONObject(
			false, _MASTER_NAME_1, _REPORT_NAME, "SUCCESS", 2000);

		_setBuildJSONObjects(
			_MASTER_NAME_1,
			_newBuildJSONObject(
				false, _MASTER_NAME_1, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 4000),
			buildJSONObject1);

		_setBuildJSONObjects(
			_MASTER_NAME_2,
			_newBuildJSONObject(
				false, _MASTER_NAME_2, _REPORT_NAME,
				RandomTestUtil.randomString(), 1000));

		_testExecute(
			Collections.singletonList(_getRemoteBuildURL(buildJSONObject1)),
			"The latest \"generate-reports\" build to run it ended in SUCCESS");

		JSONObject buildJSONObject2 = _newBuildJSONObject(
			false, _MASTER_NAME_2, _REPORT_NAME, "ABORTED", 3000);

		_setBuildJSONObjects(_MASTER_NAME_2, buildJSONObject2);

		_testExecute(
			Collections.singletonList(_getRemoteBuildURL(buildJSONObject2)),
			"The latest \"generate-reports\" build to run it ended in ABORTED");

		buildJSONObject2 = _newBuildJSONObject(
			true, _MASTER_NAME_2, _REPORT_NAME, null, 3000);

		_setBuildJSONObjects(_MASTER_NAME_2, buildJSONObject2);

		_testExecute(
			Collections.singletonList(_getRemoteBuildURL(buildJSONObject2)),
			"The latest \"generate-reports\" build to run it is still running");
	}

	@Test
	public void testExecuteLatestBuildLinkURLs() throws Exception {
		mockURLReaders();

		_setStaleReportData();

		JSONObject buildJSONObject = _newBuildJSONObject(
			false, _MASTER_NAME_1, _REPORT_NAME, RandomTestUtil.randomString(),
			RandomTestUtil.randomLong());

		_setBuildJSONObjects(_MASTER_NAME_1, buildJSONObject);

		_setBuildJSONObjects(_MASTER_NAME_2);

		Properties monitorProperties = _newMonitorProperties();

		String linkURL = "https://" + RandomTestUtil.randomString();

		monitorProperties.setProperty("monitor[a].parameter[links]", linkURL);

		MonitorResult monitorResult = _execute(monitorProperties);

		testEquals(
			Arrays.asList(linkURL, _getRemoteBuildURL(buildJSONObject)),
			monitorResult.getLinkURLs());
	}

	@Test
	public void testExecuteLookupFailure() throws Exception {
		mockURLReaders();

		_setStaleReportData();

		JSONObject buildJSONObject = _newBuildJSONObject(
			false, _MASTER_NAME_1, _REPORT_NAME, RandomTestUtil.randomString(),
			RandomTestUtil.randomLong());

		buildJSONObject.remove("url");

		_setBuildJSONObjects(_MASTER_NAME_1, buildJSONObject);

		_setBuildJSONObjects(_MASTER_NAME_2);

		MonitorResult monitorResult = _execute(_newMonitorProperties());

		Assert.assertNull(monitorResult.getLinkURLs());

		String message = monitorResult.getMessage();

		Assert.assertTrue(
			message.startsWith(
				_getStaleMessage(
					JenkinsResultsParserUtil.combine(
						"Unable to look up the latest \"generate-reports\" ",
						"build in cohort ", _COHORT_NAME, ": "))));

		Map<String, String> metrics = monitorResult.getMetrics();

		testEquals("7200", metrics.get("output.age.seconds"));

		testEquals(MonitorResult.Status.WARN, monitorResult.getStatus());
	}

	@Test
	public void testExecuteMissingFailureMessage() throws Exception {
		mockURLReaders();

		setURLReaderException(new IOException(), _URL);

		MonitorResult monitorResult = _execute(_newMonitorProperties());

		testEquals(
			JenkinsResultsParserUtil.combine(
				"Unable to read ", _URL, ": java.io.IOException"),
			monitorResult.getMessage());
		testEquals(MonitorResult.Status.CRITICAL, monitorResult.getStatus());

		Map<String, String> metrics = monitorResult.getMetrics();

		Assert.assertTrue(metrics.isEmpty());
	}

	@Test
	public void testExecuteMissingGeneratedDate() throws Exception {
		mockURLReaders();

		setURLReaderOutput(RandomTestUtil.randomString(), _URL);

		MonitorResult monitorResult = _execute(_newMonitorProperties());

		testEquals(
			JenkinsResultsParserUtil.combine(
				"Unable to determine the generated date for report ",
				_REPORT_NAME, " from ", _URL),
			monitorResult.getMessage());
		testEquals(MonitorResult.Status.CRITICAL, monitorResult.getStatus());

		Map<String, String> metrics = monitorResult.getMetrics();

		Assert.assertTrue(metrics.isEmpty());
	}

	@Test
	public void testExecuteMissingJob() throws Exception {
		mockURLReaders();

		_setStaleReportData();

		setURLReaderException(
			new FileNotFoundException(),
			JenkinsResultsParserUtil.combine(
				"http://", _MASTER_NAME_1, "/job/generate-reports/api/json"));

		_setBuildJSONObjects(_MASTER_NAME_2);

		_testExecute(
			null,
			JenkinsResultsParserUtil.combine(
				"No \"generate-reports\" build in cohort ", _COHORT_NAME,
				" ran it among the newest 100 on each master"));
	}

	@Test
	public void testExecuteModificationDate() throws Exception {
		mockURLReaders();

		long generatedTimestamp =
			JenkinsResultsParserUtil.getCurrentTimeMillis() - (600 * 1000);

		setURLReaderOutput(
			JenkinsResultsParserUtil.combine(
				"var allDurations = {\"id\":\"a\",\"modification_date\":",
				String.valueOf(generatedTimestamp), ",\"title\":\"b\"};"),
			_URL);

		MonitorResult monitorResult = _execute(_newMonitorProperties());

		testEquals(MonitorResult.Status.OK, monitorResult.getStatus());

		Map<String, String> metrics = monitorResult.getMetrics();

		testEquals(
			String.valueOf(generatedTimestamp),
			metrics.get("generated.timestamp"));
	}

	@Test
	public void testExecuteOK() throws Exception {
		mockURLReaders();

		long generatedTimestamp =
			JenkinsResultsParserUtil.getCurrentTimeMillis() - (600 * 1000);

		setURLReaderOutput(
			_newReportData(generatedTimestamp, "dataGeneratedDate"), _URL);

		MonitorResult monitorResult = _execute(_newMonitorProperties());

		testEquals(
			JenkinsResultsParserUtil.combine("Report ", _REPORT_NAME, " is OK"),
			monitorResult.getMessage());
		testEquals(MonitorResult.Status.OK, monitorResult.getStatus());

		Map<String, String> metrics = monitorResult.getMetrics();

		testEquals(
			String.valueOf(generatedTimestamp),
			metrics.get("generated.timestamp"));

		Assert.assertNotNull(metrics.get("output.age.seconds"));

		verifyURLReaderAttemptsCount(0, "/job/generate-reports/api/json");
		verifyURLReaderRead(false, 0, 27000);
	}

	@Test
	public void testExecuteOverdueGraceBelowFloor() throws Exception {
		mockURLReaders();

		setURLReaderOutput(
			_newReportData(
				JenkinsResultsParserUtil.getCurrentTimeMillis() - (3700 * 1000),
				"dataGeneratedDate"),
			_URL);

		_setBuildJSONObjects(_MASTER_NAME_1);
		_setBuildJSONObjects(_MASTER_NAME_2);

		Properties monitorProperties = _newMonitorProperties();

		monitorProperties.setProperty(
			"monitor[a].threshold[overdue.grace]", "60");

		MonitorResult monitorResult = _execute(monitorProperties);

		testEquals(MonitorResult.Status.WARN, monitorResult.getStatus());
	}

	@Test
	public void testExecuteReportNames() throws Exception {
		mockURLReaders();

		_setStaleReportData();

		_setBuildJSONObjects(_MASTER_NAME_2);

		_testExecuteReportNames(
			false, _REPORT_NAME + RandomTestUtil.randomString());
		_testExecuteReportNames(false, _REPORT_NAME.substring(0, 9));
		_testExecuteReportNames(false, null);
		_testExecuteReportNames(
			true,
			JenkinsResultsParserUtil.combine(
				RandomTestUtil.randomString(), " , ", _REPORT_NAME, ",",
				RandomTestUtil.randomString()));
		_testExecuteReportNames(true, _REPORT_NAME);
	}

	@Test
	public void testExecuteStale() throws Exception {
		mockURLReaders();

		setURLReaderOutput(
			_newReportData(
				JenkinsResultsParserUtil.getCurrentTimeMillis() - (7200 * 1000),
				"dataGeneratedDate"),
			_URL);

		_setBuildJSONObjects(_MASTER_NAME_1);
		_setBuildJSONObjects(_MASTER_NAME_2);

		MonitorResult monitorResult = _execute(_newMonitorProperties());

		testEquals(
			JenkinsResultsParserUtil.combine(
				"Report ", _REPORT_NAME, " output was generated 2 hours ago, ",
				"exceeding its cadence of 1 hour plus a grace period of 30 ",
				"minutes. No \"generate-reports\" build in cohort ",
				_COHORT_NAME, " ran it among the newest 100 on each master"),
			monitorResult.getMessage());
		testEquals(MonitorResult.Status.WARN, monitorResult.getStatus());

		Map<String, String> metrics = monitorResult.getMetrics();

		testEquals("7200", metrics.get("output.age.seconds"));
	}

	@Test
	public void testExecuteStaleReads() throws Exception {
		mockURLReaders();

		_setStaleReportData();

		_setBuildJSONObjects(_MASTER_NAME_1);
		_setBuildJSONObjects(_MASTER_NAME_2);

		_execute(_newMonitorProperties());

		verifyURLReaderRead(false, 3, 0, 27000);
	}

	@Test
	public void testExecuteStaleWithOverdueGrace() throws Exception {
		mockURLReaders();

		setURLReaderOutput(
			_newReportData(
				JenkinsResultsParserUtil.getCurrentTimeMillis() -
					(10800 * 1000),
				"dataGeneratedDate"),
			_URL);

		_setBuildJSONObjects(_MASTER_NAME_1);
		_setBuildJSONObjects(_MASTER_NAME_2);

		Properties monitorProperties = _newMonitorProperties();

		monitorProperties.setProperty(
			"monitor[a].threshold[overdue.grace]", "3600");

		MonitorResult monitorResult = _execute(monitorProperties);

		testEquals(
			JenkinsResultsParserUtil.combine(
				"Report ", _REPORT_NAME, " output was generated 3 hours ago, ",
				"exceeding its cadence of 1 hour plus a grace period of 1 ",
				"hour. No \"generate-reports\" build in cohort ", _COHORT_NAME,
				" ran it among the newest 100 on each master"),
			monitorResult.getMessage());
		testEquals(MonitorResult.Status.WARN, monitorResult.getStatus());
	}

	@Test
	public void testExecuteUnreadableJenkinsMasters() throws Exception {
		mockURLReaders();

		_setStaleReportData();

		setURLReaderException(
			new IOException(RandomTestUtil.randomString()),
			JenkinsResultsParserUtil.combine(
				"http://", _MASTER_NAME_1, "/job/generate-reports/api/json"));

		JSONObject buildJSONObject = _newBuildJSONObject(
			false, _MASTER_NAME_2, _REPORT_NAME, "FAILURE",
			RandomTestUtil.randomLong());

		_setBuildJSONObjects(_MASTER_NAME_2, buildJSONObject);

		_testExecute(
			Collections.singletonList(_getRemoteBuildURL(buildJSONObject)),
			JenkinsResultsParserUtil.combine(
				"The latest \"generate-reports\" build to run it ended in ",
				"FAILURE, but \"generate-reports\" could not be read on ",
				_MASTER_NAME_1, ", so a newer build may exist"));

		_setBuildJSONObjects(_MASTER_NAME_2);

		_testExecute(
			null,
			JenkinsResultsParserUtil.combine(
				"No \"generate-reports\" build in cohort ", _COHORT_NAME,
				" ran it, and \"generate-reports\" could not be read on ",
				_MASTER_NAME_1));

		setURLReaderException(
			new IOException(RandomTestUtil.randomString()),
			JenkinsResultsParserUtil.combine(
				"http://", _MASTER_NAME_2, "/job/generate-reports/api/json"));

		_testExecute(
			null,
			JenkinsResultsParserUtil.combine(
				"No \"generate-reports\" build in cohort ", _COHORT_NAME,
				" ran it, and \"generate-reports\" could not be read on ",
				_MASTER_NAME_1, ", ", _MASTER_NAME_2));
	}

	@Test
	public void testGetLinkURLs() {
		ReportFreshnessMonitor reportFreshnessMonitor =
			_newReportFreshnessMonitor(_newMonitorProperties());

		testEquals(
			Collections.emptyList(), reportFreshnessMonitor.getLinkURLs());
	}

	@Test
	public void testReportFreshnessMonitor() {
		_testReportFreshnessMonitorInvalidProperty(
			"monitor[a].parameter[cadence]", "-1");
		_testReportFreshnessMonitorInvalidProperty(
			"monitor[a].parameter[cadence]", "0");
		_testReportFreshnessMonitorInvalidProperty(
			"monitor[a].parameter[cadence]", "not-a-number");
		_testReportFreshnessMonitorInvalidProperty(
			"monitor[a].parameter[url]",
			"file:" + RandomTestUtil.randomString());
		_testReportFreshnessMonitorInvalidProperty(
			"monitor[a].parameter[url]",
			"https://" + RandomTestUtil.randomString());
		_testReportFreshnessMonitorInvalidProperty(
			"monitor[a].parameter[url]", RandomTestUtil.randomString());
		_testReportFreshnessMonitorInvalidProperty(
			"monitor[a].threshold[overdue.grace]", "-1");
		_testReportFreshnessMonitorInvalidProperty(
			"monitor[a].threshold[overdue.grace]", "not-a-number");

		_testReportFreshnessMonitorMissingProperty(
			"monitor[a].parameter[cadence]");
		_testReportFreshnessMonitorMissingProperty(
			"monitor[a].parameter[report.name]");
		_testReportFreshnessMonitorMissingProperty("monitor[a].parameter[url]");
	}

	@Test
	public void testReportFreshnessMonitorCohortName() throws Exception {
		mockURLReaders();

		_setStaleReportData();

		_testReportFreshnessMonitorCohortName("");
		_testReportFreshnessMonitorCohortName(null);
	}

	@Test
	public void testReportFreshnessMonitorUserInfo() {
		Properties monitorProperties = _newMonitorProperties();

		String password = RandomTestUtil.randomString();

		monitorProperties.setProperty(
			"monitor[a].parameter[url]",
			JenkinsResultsParserUtil.combine(
				"file://", RandomTestUtil.randomString(), ":", password, "@",
				RandomTestUtil.randomString()));

		try {
			_newReportFreshnessMonitor(monitorProperties);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			String message = illegalArgumentException.getMessage();

			Assert.assertFalse(message.contains(password));
			Assert.assertTrue(message.contains("[REDACTED]"));
		}
	}

	private MonitorResult _execute(Properties monitorProperties) {
		ReportFreshnessMonitor reportFreshnessMonitor =
			_newReportFreshnessMonitor(monitorProperties);

		return reportFreshnessMonitor.execute();
	}

	private String _getRemoteBuildURL(JSONObject buildJSONObject) {
		String buildURL = buildJSONObject.getString("url");

		return buildURL.replaceFirst(
			"^http://([^/]+)/", "https://$1.liferay.com/");
	}

	private String _getStaleMessage(String lookupMessage) {
		return JenkinsResultsParserUtil.combine(
			"Report ", _REPORT_NAME, " output was generated 2 hours ago, ",
			"exceeding its cadence of 1 hour plus a grace period of 30 ",
			"minutes. ", lookupMessage);
	}

	private JSONObject _newBuildJSONObject(
		boolean building, String masterName, String reportNames, String result,
		long timestamp) {

		JSONArray parametersJSONArray = new JSONArray();

		if (reportNames != null) {
			parametersJSONArray.put(
				new JSONObject(
				).put(
					"name", "REPORT_NAMES"
				).put(
					"value", reportNames
				));
		}

		return new JSONObject(
		).put(
			"actions",
			new JSONArray(
			).put(
				new JSONObject(
				).put(
					"parameters", parametersJSONArray
				)
			)
		).put(
			"building", building
		).put(
			"result", result
		).put(
			"timestamp", timestamp
		).put(
			"url",
			JenkinsResultsParserUtil.combine(
				"http://", masterName, "/job/generate-reports/",
				String.valueOf(RandomTestUtil.randomInt()), "/")
		);
	}

	private Properties _newMonitorProperties() {
		Properties monitorProperties = new Properties();

		monitorProperties.setProperty("monitor[a].parameter[cadence]", "3600");
		monitorProperties.setProperty(
			"monitor[a].parameter[cohort.name]", _COHORT_NAME);
		monitorProperties.setProperty(
			"monitor[a].parameter[report.name]", _REPORT_NAME);
		monitorProperties.setProperty("monitor[a].parameter[url]", _URL);
		monitorProperties.setProperty("monitor[a].type", "report-freshness");

		return monitorProperties;
	}

	private String _newReportData(
		long generatedTimestamp, String variableName) {

		return JenkinsResultsParserUtil.combine(
			"var ", variableName, " = new Date(",
			String.valueOf(generatedTimestamp), ");");
	}

	private ReportFreshnessMonitor _newReportFreshnessMonitor(
		Properties monitorProperties) {

		List<MonitorConfig> monitorConfigs =
			MonitorConfigLoader.getMonitorConfigs(monitorProperties);

		return new ReportFreshnessMonitor(monitorConfigs.get(0));
	}

	private void _setBuildJSONObjects(
			String masterName, JSONObject... buildJSONObjects)
		throws Exception {

		setURLReaderOutput(
			String.valueOf(
				new JSONObject(
				).put(
					"builds", new JSONArray(buildJSONObjects)
				)),
			JenkinsResultsParserUtil.combine(
				"http://", masterName, "/job/generate-reports/api/json?tree=",
				"builds%5Bactions%5Bparameters%5Bname%2Cvalue%5D%5D%2C",
				"building%2Cresult%2Ctimestamp%2Curl%5D%7B0%2C100%7D"));
	}

	private void _setStaleReportData() throws Exception {
		setURLReaderOutput(
			_newReportData(
				JenkinsResultsParserUtil.getCurrentTimeMillis() - (7200 * 1000),
				"dataGeneratedDate"),
			_URL);
	}

	private void _testExecute(
		List<String> expectedLinkURLs, String expectedLookupMessage) {

		MonitorResult monitorResult = _execute(_newMonitorProperties());

		testEquals(expectedLinkURLs, monitorResult.getLinkURLs());
		testEquals(
			_getStaleMessage(expectedLookupMessage),
			monitorResult.getMessage());
		testEquals(MonitorResult.Status.WARN, monitorResult.getStatus());
	}

	private void _testExecuteGeneratedDateVariant(String variableName)
		throws Exception {

		mockURLReaders();

		long generatedTimestamp =
			JenkinsResultsParserUtil.getCurrentTimeMillis() - (600 * 1000);

		setURLReaderOutput(
			_newReportData(generatedTimestamp, variableName), _URL);

		MonitorResult monitorResult = _execute(_newMonitorProperties());

		testEquals(MonitorResult.Status.OK, monitorResult.getStatus());

		Map<String, String> metrics = monitorResult.getMetrics();

		testEquals(
			String.valueOf(generatedTimestamp),
			metrics.get("generated.timestamp"));
	}

	private void _testExecuteReportNames(
			boolean expectedFound, String reportNames)
		throws Exception {

		JSONObject buildJSONObject = _newBuildJSONObject(
			false, _MASTER_NAME_1, reportNames, "FAILURE",
			RandomTestUtil.randomLong());

		_setBuildJSONObjects(_MASTER_NAME_1, buildJSONObject);

		if (expectedFound) {
			_testExecute(
				Collections.singletonList(_getRemoteBuildURL(buildJSONObject)),
				JenkinsResultsParserUtil.combine(
					"The latest \"generate-reports\" build to run it ended in ",
					"FAILURE"));

			return;
		}

		_testExecute(
			null,
			JenkinsResultsParserUtil.combine(
				"No \"generate-reports\" build in cohort ", _COHORT_NAME,
				" ran it among the newest 100 on each master"));
	}

	private void _testReportFreshnessMonitorCohortName(String cohortName) {
		Properties monitorProperties = _newMonitorProperties();

		if (cohortName == null) {
			monitorProperties.remove("monitor[a].parameter[cohort.name]");
		}
		else {
			monitorProperties.setProperty(
				"monitor[a].parameter[cohort.name]", cohortName);
		}

		MonitorResult monitorResult = _execute(monitorProperties);

		testEquals(
			_getStaleMessage("Unable to find a master in cohort test-1"),
			monitorResult.getMessage());
	}

	private void _testReportFreshnessMonitorInvalidProperty(
		String name, String value) {

		Properties monitorProperties = _newMonitorProperties();

		monitorProperties.setProperty(name, value);

		try {
			_newReportFreshnessMonitor(monitorProperties);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}
	}

	private void _testReportFreshnessMonitorMissingProperty(String name) {
		Properties monitorProperties = _newMonitorProperties();

		monitorProperties.remove(name);

		try {
			_newReportFreshnessMonitor(monitorProperties);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}
	}

	private static final String _COHORT_NAME = "test-9";

	private static final String _MASTER_NAME_1 = _COHORT_NAME + "-1";

	private static final String _MASTER_NAME_2 = _COHORT_NAME + "-2";

	private static final String _REPORT_NAME = "CI System Status";

	private static final String _URL =
		"file:///" + RandomTestUtil.randomString();

}