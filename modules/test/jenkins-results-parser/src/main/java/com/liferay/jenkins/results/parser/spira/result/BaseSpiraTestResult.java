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

package com.liferay.jenkins.results.parser.spira.result;

import com.google.common.collect.Lists;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalBranchInformationBuild;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.spira.BaseSpiraArtifact;
import com.liferay.jenkins.results.parser.spira.SpiraAutomationHost;
import com.liferay.jenkins.results.parser.spira.SpiraCustomPropertyValue;
import com.liferay.jenkins.results.parser.spira.SpiraRelease;
import com.liferay.jenkins.results.parser.spira.SpiraReleaseBuild;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseFolder;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseObject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;
import com.liferay.jenkins.results.parser.spira.SpiraTestSet;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseSpiraTestResult implements SpiraTestResult {

	@Override
	public Integer getDuration() {
		Build build = getBuild();

		if (build != null) {
			return (int)build.getDuration();
		}

		return 0;
	}

	@Override
	public String getPortalSHA() {
		String portalSHA = "";

		TopLevelBuild topLevelBuild = spiraBuildResult.getTopLevelBuild();

		if (topLevelBuild instanceof PortalBranchInformationBuild) {
			PortalBranchInformationBuild portalBranchInformationBuild =
				(PortalBranchInformationBuild)topLevelBuild;

			Build.BranchInformation portalBranchInformation =
				portalBranchInformationBuild.getPortalBranchInformation();

			portalSHA = portalBranchInformation.getSenderBranchSHA();
		}

		if (portalSHA.length() > 7) {
			portalSHA = portalSHA.substring(0, 7);
		}

		return portalSHA;
	}

	@Override
	public JSONObject getRequestJSONObject() {
		JSONObject requestJSONObject = new JSONObject();

		SpiraRelease spiraRelease = spiraBuildResult.getSpiraRelease();

		if (spiraRelease != null) {
			requestJSONObject.put(
				spiraRelease.getKeyID(), spiraRelease.getID());
		}

		SpiraReleaseBuild spiraReleaseBuild =
			spiraBuildResult.getSpiraReleaseBuild();

		if (spiraReleaseBuild != null) {
			requestJSONObject.put(
				spiraReleaseBuild.getKeyID(), spiraReleaseBuild.getID());
		}

		SpiraTestSet spiraTestSet = spiraBuildResult.getSpiraTestSet();

		if (spiraTestSet != null) {
			requestJSONObject.put(
				spiraTestSet.getKeyID(), spiraTestSet.getID());

			SpiraTestSet.SpiraTestSetTestCase spiraTestSetTestCase =
				spiraTestSet.assignSpiraTestCaseObject(
					getSpiraTestCaseObject());

			requestJSONObject.put(
				spiraTestSetTestCase.getKeyID(), spiraTestSetTestCase.getID());
		}

		SpiraAutomationHost spiraAutomationHost = getSpiraAutomationHost();

		if (spiraAutomationHost != null) {
			requestJSONObject.put(
				spiraAutomationHost.getKeyID(), spiraAutomationHost.getID());
		}

		SpiraTestCaseObject spiraTestCaseObject = getSpiraTestCaseObject();

		requestJSONObject.put(
			spiraTestCaseObject.getKeyID(), spiraTestCaseObject.getID());

		requestJSONObject.put(
			"CustomProperties",
			_spiraTestResultValues.getCustomPropertyValuesJSONArray());

		SpiraTestCaseRun.Status status = getSpiraTestCaseRunStatus();

		requestJSONObject.put("ExecutionStatusId", status.getID());

		requestJSONObject.put("RunnerMessage", spiraTestCaseObject.getName());
		requestJSONObject.put("RunnerName", "Liferay CI");
		requestJSONObject.put(
			"RunnerStackTrace", _spiraTestResultDetails.getDetails());
		requestJSONObject.put("RunnerTestName", getTestName());

		Build build = getBuild();

		requestJSONObject.put(
			"StartDate",
			BaseSpiraArtifact.toDateString(new Date(build.getStartTime())));

		SpiraTestCaseRun.RunnerFormat runnerFormat =
			getSpiraTestCaseRunRunnerFormat();

		requestJSONObject.put("TestRunFormatId", runnerFormat.getID());

		return requestJSONObject;
	}

	@Override
	public SpiraAutomationHost getSpiraAutomationHost() {
		Build build = getBuild();

		return SpiraAutomationHost.createSpiraAutomationHost(
			spiraBuildResult.getSpiraProject(), build.getJenkinsSlave());
	}

	@Override
	public SpiraBuildResult getSpiraBuildResult() {
		return spiraBuildResult;
	}

	@Override
	public SpiraTestCaseObject getSpiraTestCaseObject() {
		if (_spiraTestCaseObject != null) {
			return _spiraTestCaseObject;
		}

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		_spiraTestCaseObject =
			SpiraTestCaseObject.createSpiraTestCaseObjectByPath(
				spiraBuildResult.getSpiraProject(), getSpiraTestCasePath(),
				getSpiraTestCaseType(),
				Lists.<SpiraCustomPropertyValue>newArrayList(
					spiraBuildResult.getSpiraTestCaseProductVersion()));

		return _spiraTestCaseObject;
	}

	@Override
	public SpiraTestCaseRun.RunnerFormat getSpiraTestCaseRunRunnerFormat() {
		return SpiraTestCaseRun.RunnerFormat.HTML;
	}

	@Override
	public SpiraTestCaseRun.Status getSpiraTestCaseRunStatus() {
		Build build = getBuild();

		if (build == null) {
			return SpiraTestCaseRun.Status.FAILED;
		}

		String result = build.getResult();

		if (!result.equals("SUCCESS")) {
			return SpiraTestCaseRun.Status.FAILED;
		}

		return SpiraTestCaseRun.Status.PASSED;
	}

	@Override
	public SpiraTestResultDetails getSpiraTestResultDetails() {
		return _spiraTestResultDetails;
	}

	@Override
	public SpiraTestResultValues getSpiraTestResultValues() {
		return _spiraTestResultValues;
	}

	@Override
	public void record() {
		long startTestCase = System.currentTimeMillis();

		SpiraTestCaseObject spiraTestCaseObject = getSpiraTestCaseObject();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				getTestName(), " ", spiraTestCaseObject.getURL(), " in ",
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - startTestCase)));

		long startTestRun = System.currentTimeMillis();

		List<SpiraTestCaseRun> spiraTestCaseRuns =
			SpiraTestCaseRun.recordSpiraTestCaseRuns(
				spiraBuildResult.getSpiraProject(), this);

		SpiraTestCaseRun spiraTestCaseRun = spiraTestCaseRuns.get(0);

		System.out.println(
			JenkinsResultsParserUtil.combine(
				getTestName(), " ", spiraTestCaseRun.getURL(), " in ",
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - startTestRun)));

		System.out.println();
	}

	protected BaseSpiraTestResult(SpiraBuildResult spiraBuildResult) {
		this.spiraBuildResult = spiraBuildResult;

		_spiraTestResultDetails = SpiraResultFactory.newSpiraTestResultDetails(
			this);
		_spiraTestResultValues = SpiraResultFactory.newSpiraTestResultValues(
			this);
	}

	protected String getSpiraTestCasePath() {
		StringBuilder sb = new StringBuilder();

		SpiraTestCaseFolder spiraTestCaseFolder =
			spiraBuildResult.getSpiraTestCaseFolder();

		if (spiraTestCaseFolder != null) {
			sb.append(spiraTestCaseFolder.getPath());
		}

		sb.append("/");
		sb.append(BaseSpiraArtifact.fixStringForJSON(getTestName()));

		return sb.toString();
	}

	protected final SpiraBuildResult spiraBuildResult;

	private SpiraTestCaseObject _spiraTestCaseObject;
	private final SpiraTestResultDetails _spiraTestResultDetails;
	private final SpiraTestResultValues _spiraTestResultValues;

}