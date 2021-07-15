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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.StopWatchRecord;
import com.liferay.jenkins.results.parser.StopWatchRecordsGroup;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayBuild {

	public TestrayBuild(TestrayRoutine testrayRoutine, JSONObject jsonObject) {
		_testrayRoutine = testrayRoutine;
		_jsonObject = jsonObject;

		_testrayProject = _testrayRoutine.getTestrayProject();
		_testrayServer = _testrayRoutine.getTestrayServer();

		_testrayProductVersion = _testrayProject.getTestrayProductVersionByID(
			_jsonObject.getInt("testrayProductVersionId"));
	}

	public String getDescription() {
		return _jsonObject.getString("description");
	}

	public List<Long> getDownstreamBuildDurations() {
		_initBuildResultData();

		return _downstreamBuildDurations;
	}

	public int getID() {
		return _jsonObject.getInt("testrayBuildId");
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	public String getResult() {
		_initBuildResultData();

		return _result;
	}

	public List<TestrayCaseResult> getTestrayCaseResults() {
		List<TestrayCaseResult> testrayCaseResults = new ArrayList<>();

		String urlString = String.valueOf(getURL());

		String caseResultsAPIURLString = urlString.replace(
			"runs", "case_results.json");

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				caseResultsAPIURLString);

			JSONArray dataJSONArray = jsonObject.getJSONArray("data");

			for (int i = 0; i < dataJSONArray.length(); i++) {
				JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

				TestrayCaseResult testrayCaseResult = new TestrayCaseResult(
					this, dataJSONObject);

				testrayCaseResults.add(testrayCaseResult);
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}

		return testrayCaseResults;
	}

	public TestrayProductVersion getTestrayProductVersion() {
		return _testrayProductVersion;
	}

	public TestrayProject getTestrayProject() {
		return _testrayProject;
	}

	public TestrayRoutine getTestrayRoutine() {
		return _testrayRoutine;
	}

	public TestrayServer getTestrayServer() {
		return _testrayServer;
	}

	public Long getTopLevelActiveBuildDuration() {
		_initBuildResultData();

		return _topLevelActiveBuildDuration;
	}

	public Long getTopLevelBuildDuration() {
		_initBuildResultData();

		return _topLevelBuildDuration;
	}

	public URL getURL() {
		try {
			return new URL(_jsonObject.getString("htmlURL"));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private void _addChildStopWatchRecords(
		StopWatchRecordsGroup stopWatchRecordsGroup,
		StopWatchRecord stopWatchRecord, JSONObject stopWatchRecordJSONObject) {

		if (!stopWatchRecordJSONObject.has("childStopWatchRecords")) {
			return;
		}

		JSONArray childStopWatchRecordsJSONArray =
			stopWatchRecordJSONObject.getJSONArray("childStopWatchRecords");

		for (int i = 0; i < childStopWatchRecordsJSONArray.length(); i++) {
			JSONObject childStopWatchRecordJSONObject =
				childStopWatchRecordsJSONArray.getJSONObject(i);

			childStopWatchRecordJSONObject.put(
				"startTimestamp", _startTimestamp);

			_startTimestamp++;

			StopWatchRecord childStopWatchRecord = new StopWatchRecord(
				childStopWatchRecordJSONObject);

			stopWatchRecordsGroup.add(childStopWatchRecord);

			stopWatchRecord.addChildStopWatchRecord(childStopWatchRecord);

			_addChildStopWatchRecords(
				stopWatchRecordsGroup, childStopWatchRecord,
				childStopWatchRecordJSONObject);
		}
	}

	private JSONObject _getBuildResultJSONObject() {
		if (_buildResultJSONObject != null) {
			return _buildResultJSONObject;
		}

		URL buildResultURL = _getBuildResultURL();

		if (buildResultURL == null) {
			return null;
		}

		File jsonFile = new File(getID() + ".json");
		File jsonGzFile = new File(getID() + ".json.gz");

		try {
			JenkinsResultsParserUtil.toFile(buildResultURL, jsonGzFile);

			JenkinsResultsParserUtil.unGzip(jsonGzFile, jsonFile);

			_buildResultJSONObject = JenkinsResultsParserUtil.createJSONObject(
				JenkinsResultsParserUtil.read(jsonFile));

			_buildResultJSONObject.put("name", getName());

			return _buildResultJSONObject;
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		finally {
			if (jsonFile.exists()) {
				JenkinsResultsParserUtil.delete(jsonFile);
			}

			if (jsonGzFile.exists()) {
				JenkinsResultsParserUtil.delete(jsonGzFile);
			}
		}

		return null;
	}

	private URL _getBuildResultURL() {
		if (_buildResultURL != null) {
			return _buildResultURL;
		}

		List<TestrayCaseResult> testrayCaseResults = getTestrayCaseResults();

		if (testrayCaseResults.isEmpty()) {
			return null;
		}

		TestrayCaseResult testrayCaseResult = testrayCaseResults.get(0);

		TestrayAttachment buildResultTestrayAttachment =
			testrayCaseResult.getBuildResultTestrayAttachment();

		if (buildResultTestrayAttachment == null) {
			return null;
		}

		_buildResultURL = buildResultTestrayAttachment.getURL();

		return _buildResultURL;
	}

	private List<Long> _getDownstreamBuildDurations(
		JSONObject buildResultJSONObject) {

		if ((buildResultJSONObject == null) ||
			!buildResultJSONObject.has("batchResults")) {

			return new ArrayList<>();
		}

		JSONArray batchResultsJSONArray = buildResultJSONObject.getJSONArray(
			"batchResults");

		List<Long> downstreamBuildDurations = new ArrayList<>();

		for (int i = 0; i < batchResultsJSONArray.length(); i++) {
			JSONObject batchResultJSONObject =
				batchResultsJSONArray.getJSONObject(i);

			JSONArray buildResultsJSONArray =
				batchResultJSONObject.getJSONArray("buildResults");

			for (int j = 0; j < buildResultsJSONArray.length(); j++) {
				JSONObject buildResultsJSONObject =
					buildResultsJSONArray.getJSONObject(j);

				if (!buildResultsJSONObject.has("duration")) {
					continue;
				}

				downstreamBuildDurations.add(
					buildResultsJSONObject.getLong("duration"));
			}
		}

		return downstreamBuildDurations;
	}

	private String _getResult(JSONObject buildResultJSONObject) {
		if ((buildResultJSONObject != null) &&
			buildResultJSONObject.has("result")) {

			return buildResultJSONObject.getString("result");
		}

		StringBuilder sb = new StringBuilder();

		String urlString = String.valueOf(getURL());

		sb.append(urlString.replace("runs", "case_results.json"));

		sb.append("&statuses=");

		for (TestrayCaseResult.Status failedStatus :
				TestrayCaseResult.Status.getFailedStatuses()) {

			sb.append(failedStatus.getID());
			sb.append(",");
		}

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				sb.toString());

			JSONArray dataJSONArray = jsonObject.getJSONArray("data");

			if ((dataJSONArray != null) && (dataJSONArray.length() > 0)) {
				return "FAILURE";
			}
		}
		catch (Exception exception) {
			return "FAILURE";
		}

		return "SUCCESS";
	}

	private StopWatchRecordsGroup _getStopWatchRecordsGroup(
		JSONObject buildResultJSONObject) {

		Long topLevelBuildDuration = _getTopLevelBuildDuration(
			buildResultJSONObject);

		if (topLevelBuildDuration == null) {
			return null;
		}

		StopWatchRecordsGroup stopWatchRecordsGroup =
			new StopWatchRecordsGroup();

		StopWatchRecord stopWatchRecord = new StopWatchRecord(
			"top.level.build", _startTimestamp);

		_startTimestamp++;

		stopWatchRecord.setDuration(topLevelBuildDuration);

		stopWatchRecordsGroup.add(stopWatchRecord);

		if ((buildResultJSONObject == null) ||
			!buildResultJSONObject.has("stopWatchRecords")) {

			return stopWatchRecordsGroup;
		}

		JSONArray stopWatchRecordsJSONArray =
			buildResultJSONObject.getJSONArray("stopWatchRecords");

		for (int i = 0; i < stopWatchRecordsJSONArray.length(); i++) {
			JSONObject childStopWatchRecordJSONObject =
				stopWatchRecordsJSONArray.getJSONObject(i);

			childStopWatchRecordJSONObject.put(
				"startTimestamp", _startTimestamp);

			if (!childStopWatchRecordJSONObject.has("duration") ||
				!childStopWatchRecordJSONObject.has("name")) {

				continue;
			}

			_startTimestamp++;

			StopWatchRecord childStopWatchRecord = new StopWatchRecord(
				childStopWatchRecordJSONObject);

			stopWatchRecord.addChildStopWatchRecord(childStopWatchRecord);

			stopWatchRecordsGroup.add(childStopWatchRecord);

			_addChildStopWatchRecords(
				stopWatchRecordsGroup, childStopWatchRecord,
				childStopWatchRecordJSONObject);
		}

		return stopWatchRecordsGroup;
	}

	private Long _getTopLevelActiveBuildDuration(
		JSONObject buildResultJSONObject) {

		Long topLevelBuildDuration = _getTopLevelBuildDuration(
			buildResultJSONObject);
		Long topLevelPassiveBuildDuration = _getTopLevelPassiveBuildDuration(
			buildResultJSONObject);

		if ((topLevelBuildDuration == null) ||
			(topLevelPassiveBuildDuration == null)) {

			return null;
		}

		return topLevelBuildDuration - topLevelPassiveBuildDuration;
	}

	private Long _getTopLevelBuildDuration(JSONObject buildResultJSONObject) {
		if ((buildResultJSONObject == null) ||
			!buildResultJSONObject.has("duration")) {

			return null;
		}

		return buildResultJSONObject.getLong("duration");
	}

	private Long _getTopLevelPassiveBuildDuration(
		JSONObject buildResultJSONObject) {

		StopWatchRecordsGroup stopWatchRecordsGroup = _getStopWatchRecordsGroup(
			buildResultJSONObject);

		if (stopWatchRecordsGroup == null) {
			return null;
		}

		StopWatchRecord waitForInvokedJobsStopWatchRecord =
			stopWatchRecordsGroup.get("wait.for.invoked.jobs");
		StopWatchRecord waitForInvokedSmokeJobsStopWatchRecord =
			stopWatchRecordsGroup.get("wait.for.invoked.smoke.jobs");

		if ((waitForInvokedJobsStopWatchRecord != null) ||
			(waitForInvokedSmokeJobsStopWatchRecord != null)) {

			long topLevelPassiveBuildDuration = 0L;

			if (waitForInvokedJobsStopWatchRecord != null) {
				topLevelPassiveBuildDuration +=
					waitForInvokedJobsStopWatchRecord.getDuration();
			}

			if (waitForInvokedSmokeJobsStopWatchRecord != null) {
				topLevelPassiveBuildDuration +=
					waitForInvokedSmokeJobsStopWatchRecord.getDuration();
			}

			return topLevelPassiveBuildDuration;
		}

		StopWatchRecord invokeDownstreamBuildsStopWatchRecord =
			stopWatchRecordsGroup.get("invoke.downstream.builds");

		if (invokeDownstreamBuildsStopWatchRecord != null) {
			return invokeDownstreamBuildsStopWatchRecord.getDuration();
		}

		return null;
	}

	private synchronized void _initBuildResultData() {
		if (_buildResultDataPopulated) {
			return;
		}

		_buildResultDataPopulated = true;

		JSONObject buildResultJSONObject = _getBuildResultJSONObject();

		_downstreamBuildDurations = _getDownstreamBuildDurations(
			buildResultJSONObject);
		_result = _getResult(buildResultJSONObject);
		_topLevelActiveBuildDuration = _getTopLevelActiveBuildDuration(
			buildResultJSONObject);
		_topLevelBuildDuration = _getTopLevelBuildDuration(
			buildResultJSONObject);
	}

	private boolean _buildResultDataPopulated;
	private JSONObject _buildResultJSONObject;
	private URL _buildResultURL;
	private List<Long> _downstreamBuildDurations;
	private final JSONObject _jsonObject;
	private String _result;
	private int _startTimestamp;
	private final TestrayProductVersion _testrayProductVersion;
	private final TestrayProject _testrayProject;
	private final TestrayRoutine _testrayRoutine;
	private final TestrayServer _testrayServer;
	private Long _topLevelActiveBuildDuration;
	private Long _topLevelBuildDuration;

}