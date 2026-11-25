/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.monitor;

import com.liferay.jenkins.results.parser.JenkinsAPIUtil;
import com.liferay.jenkins.results.parser.JenkinsCohort;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.ParallelExecutor;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeoutException;

import org.apache.http.client.utils.URIBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Brittney Nguyen
 */
public class ReportFreshnessMonitor extends BaseMonitor {

	public ReportFreshnessMonitor(MonitorConfig monitorConfig) {
		super(monitorConfig);

		Map<String, String> parameters = monitorConfig.getParameters();

		_cadenceSeconds = getLongValue("parameter", 0, "cadence", parameters);

		if (_cadenceSeconds <= 0) {
			throw new IllegalArgumentException(
				getInvalidValueMessage(
					"parameter", "cadence", parameters.get("cadence")));
		}

		String cohortName = parameters.get("cohort.name");

		if (JenkinsResultsParserUtil.isNullOrEmpty(cohortName)) {
			cohortName = "test-1";
		}

		_cohortName = cohortName;

		_reportDataURL = getRequiredURLParameter("url", parameters, "file:///");
		_reportName = getRequiredParameter("report.name", parameters);
		_overdueGraceSeconds = getOverdueGraceSeconds(
			_cadenceSeconds, monitorConfig.getThresholds());
	}

	@Override
	public MonitorResult execute() {
		String reportData = null;

		long currentTimeMillis =
			JenkinsResultsParserUtil.getCurrentTimeMillis();

		try {
			reportData = JenkinsResultsParserUtil.toString(
				_reportDataURL, false, 0, 0, getAttemptTimeoutMillis(0));
		}
		catch (Exception exception) {
			return new MonitorResult(
				_getReadFailureMessage(exception), null,
				MonitorResult.Status.CRITICAL, currentTimeMillis);
		}

		long generatedTimestamp = JenkinsResultsParserUtil.getGeneratedTime(
			reportData);

		if (generatedTimestamp <= 0) {
			return new MonitorResult(
				JenkinsResultsParserUtil.combine(
					"Unable to determine the generated date for report ",
					_reportName, " from ", _reportDataURL),
				null, MonitorResult.Status.CRITICAL, currentTimeMillis);
		}

		if (generatedTimestamp > currentTimeMillis) {
			return new MonitorResult(
				JenkinsResultsParserUtil.combine(
					"Report ", _reportName, " was generated at ",
					JenkinsResultsParserUtil.toDateString(
						new Date(generatedTimestamp)),
					", which is in the future"),
				null, MonitorResult.Status.CRITICAL, currentTimeMillis);
		}

		long outputAgeSeconds = (currentTimeMillis - generatedTimestamp) / 1000;

		Map<String, String> metrics = new LinkedHashMap<>();

		metrics.put("generated.timestamp", String.valueOf(generatedTimestamp));
		metrics.put("output.age.seconds", String.valueOf(outputAgeSeconds));

		if (outputAgeSeconds > (_cadenceSeconds + _overdueGraceSeconds)) {
			return _getStaleMonitorResult(
				currentTimeMillis, metrics, outputAgeSeconds);
		}

		return new MonitorResult(
			JenkinsResultsParserUtil.combine("Report ", _reportName, " is OK"),
			metrics, MonitorResult.Status.OK, currentTimeMillis);
	}

	private List<JenkinsMaster> _getJenkinsMasters() {
		Map<String, JenkinsMaster> jenkinsMastersMap = new TreeMap<>();

		JenkinsCohort jenkinsCohort = JenkinsCohort.getInstance(_cohortName);

		for (JenkinsMaster jenkinsMaster : jenkinsCohort.getJenkinsMasters()) {
			jenkinsMastersMap.put(jenkinsMaster.getName(), jenkinsMaster);
		}

		for (JenkinsMaster jenkinsMaster :
				jenkinsCohort.getBlacklistedJenkinsMasters()) {

			jenkinsMastersMap.put(jenkinsMaster.getName(), jenkinsMaster);
		}

		return new ArrayList<>(jenkinsMastersMap.values());
	}

	private JSONObject _getLatestBuildJSONObject(JenkinsMaster jenkinsMaster)
		throws IOException {

		URIBuilder uriBuilder = JenkinsResultsParserUtil.newURIBuilder(
			jenkinsMaster.getURL() + "/job/generate-reports/api/json");

		uriBuilder.addParameter(
			"tree",
			JenkinsResultsParserUtil.combine(
				"builds[actions[parameters[name,value]],building,result,",
				"timestamp,url]{0,", String.valueOf(_BUILDS_SIZE_MAX), "}"));

		JSONObject jobJSONObject = JenkinsResultsParserUtil.toJSONObject(
			uriBuilder.toString(), false, 0, null, null, 0,
			getAttemptTimeoutMillis(0), null);

		JSONArray buildsJSONArray = jobJSONObject.getJSONArray("builds");

		for (int i = 0; i < buildsJSONArray.length(); i++) {
			JSONObject buildJSONObject = buildsJSONArray.getJSONObject(i);

			Map<String, String> buildParameters =
				JenkinsAPIUtil.getBuildParameters(buildJSONObject);

			String reportNames = buildParameters.get("REPORT_NAMES");

			if (reportNames == null) {
				continue;
			}

			for (String reportName : reportNames.split("\\s*,\\s*")) {
				if (reportName.equals(_reportName)) {
					return buildJSONObject;
				}
			}
		}

		return null;
	}

	private JSONObject _getLatestBuildJSONObject(
			List<JenkinsMaster> jenkinsMasters,
			Set<String> unreadableJenkinsMasterNames)
		throws TimeoutException {

		List<Callable<JSONObject>> callables = new ArrayList<>();

		for (JenkinsMaster jenkinsMaster : jenkinsMasters) {
			callables.add(
				new Callable<JSONObject>() {

					@Override
					public JSONObject call() {
						try {
							return _getLatestBuildJSONObject(jenkinsMaster);
						}
						catch (FileNotFoundException fileNotFoundException) {
							return null;
						}
						catch (Exception exception) {
							unreadableJenkinsMasterNames.add(
								jenkinsMaster.getName());

							return null;
						}
					}

				});
		}

		ParallelExecutor<JSONObject> parallelExecutor = new ParallelExecutor<>(
			callables,
			JenkinsResultsParserUtil.getNewThreadPoolExecutor(
				callables.size(), true),
			"readGenerateReportsBuilds");

		List<JSONObject> buildJSONObjects = parallelExecutor.execute();

		JSONObject latestBuildJSONObject = null;

		for (JSONObject buildJSONObject : buildJSONObjects) {
			if ((buildJSONObject == null) ||
				((latestBuildJSONObject != null) &&
				 (buildJSONObject.getLong("timestamp") <=
					 latestBuildJSONObject.getLong("timestamp")))) {

				continue;
			}

			latestBuildJSONObject = buildJSONObject;
		}

		return latestBuildJSONObject;
	}

	private String _getLatestBuildMessage(
		JSONObject buildJSONObject, Set<String> unreadableJenkinsMasterNames) {

		String latestBuildMessage =
			"The latest \"generate-reports\" build to run it is still running";

		if (!buildJSONObject.optBoolean("building")) {
			latestBuildMessage =
				"The latest \"generate-reports\" build to run it ended in " +
					buildJSONObject.optString("result");
		}

		if (unreadableJenkinsMasterNames.isEmpty()) {
			return latestBuildMessage;
		}

		return JenkinsResultsParserUtil.combine(
			latestBuildMessage,
			", but \"generate-reports\" could not be read on ",
			JenkinsResultsParserUtil.join(
				", ", new ArrayList<>(unreadableJenkinsMasterNames)),
			", so a newer build may exist");
	}

	private String _getNotFoundMessage(
		Set<String> unreadableJenkinsMasterNames) {

		if (unreadableJenkinsMasterNames.isEmpty()) {
			return JenkinsResultsParserUtil.combine(
				"No \"generate-reports\" build in cohort ", _cohortName,
				" ran it among the newest ", String.valueOf(_BUILDS_SIZE_MAX),
				" on each master");
		}

		return JenkinsResultsParserUtil.combine(
			"No \"generate-reports\" build in cohort ", _cohortName,
			" ran it, and \"generate-reports\" could not be read on ",
			JenkinsResultsParserUtil.join(
				", ", new ArrayList<>(unreadableJenkinsMasterNames)));
	}

	private String _getReadFailureMessage(Exception exception) {
		return JenkinsResultsParserUtil.combine(
			"Unable to read ", _reportDataURL, ": ",
			JenkinsResultsParserUtil.getMessage(exception));
	}

	private String _getStaleMessage(long outputAgeSeconds) {
		return JenkinsResultsParserUtil.combine(
			"Report ", _reportName, " output was generated ",
			JenkinsResultsParserUtil.toDurationString(outputAgeSeconds * 1000),
			" ago, exceeding its cadence of ",
			JenkinsResultsParserUtil.toDurationString(_cadenceSeconds * 1000),
			" plus a grace period of ",
			JenkinsResultsParserUtil.toDurationString(
				_overdueGraceSeconds * 1000));
	}

	private MonitorResult _getStaleMonitorResult(
		long currentTimeMillis, Map<String, String> metrics,
		long outputAgeSeconds) {

		List<String> linkURLs = null;
		String lookupMessage = null;

		try {
			List<JenkinsMaster> activeJenkinsMasters = new ArrayList<>();

			List<JenkinsMaster> jenkinsMasters = _getJenkinsMasters();

			for (JenkinsMaster jenkinsMaster : jenkinsMasters) {
				if (jenkinsMaster.isBlacklisted()) {
					continue;
				}

				activeJenkinsMasters.add(jenkinsMaster);
			}

			if (jenkinsMasters.isEmpty()) {
				lookupMessage =
					"Unable to find a master in cohort " + _cohortName;
			}
			else if (activeJenkinsMasters.isEmpty()) {
				lookupMessage = JenkinsResultsParserUtil.combine(
					"Every master in cohort ", _cohortName, " is blacklisted");
			}
			else {
				Set<String> unreadableJenkinsMasterNames =
					new ConcurrentSkipListSet<>();

				JSONObject latestBuildJSONObject = _getLatestBuildJSONObject(
					activeJenkinsMasters, unreadableJenkinsMasterNames);

				if (latestBuildJSONObject != null) {
					linkURLs = new ArrayList<>(getConfiguredLinkURLs());

					linkURLs.add(
						JenkinsResultsParserUtil.getRemoteURL(
							latestBuildJSONObject.getString("url")));

					lookupMessage = _getLatestBuildMessage(
						latestBuildJSONObject, unreadableJenkinsMasterNames);
				}
				else {
					lookupMessage = _getNotFoundMessage(
						unreadableJenkinsMasterNames);
				}
			}
		}
		catch (Exception exception) {
			linkURLs = null;

			lookupMessage = JenkinsResultsParserUtil.combine(
				"Unable to look up the latest \"generate-reports\" build in ",
				"cohort ", _cohortName, ": ",
				JenkinsResultsParserUtil.getMessage(exception));
		}

		return new MonitorResult(
			linkURLs,
			JenkinsResultsParserUtil.combine(
				_getStaleMessage(outputAgeSeconds), ". ", lookupMessage),
			metrics, MonitorResult.Status.WARN, currentTimeMillis);
	}

	private static final int _BUILDS_SIZE_MAX = 100;

	private final long _cadenceSeconds;
	private final String _cohortName;
	private final long _overdueGraceSeconds;
	private final String _reportDataURL;
	private final String _reportName;

}