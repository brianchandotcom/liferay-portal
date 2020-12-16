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
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseFolder;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseObject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;

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

		StringBuilder sb = new StringBuilder();

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		SpiraTestCaseFolder spiraTestCaseFolder =
			spiraBuildResult.getSpiraTestCaseFolder();

		if (spiraTestCaseFolder != null) {
			sb.append(spiraTestCaseFolder.getPath());
		}

		sb.append("/");
		sb.append(BaseSpiraArtifact.fixStringForJSON(getTestName()));

		_spiraTestCaseObject =
			SpiraTestCaseObject.createSpiraTestCaseObjectByPath(
				spiraBuildResult.getSpiraProject(), sb.toString(),
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
		long start = System.currentTimeMillis();

		SpiraTestCaseObject spiraTestCaseObject = getSpiraTestCaseObject();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				spiraTestCaseObject.getPath(), " ",
				spiraTestCaseObject.getURL(), " in ",
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start)));
	}

	protected BaseSpiraTestResult(SpiraBuildResult spiraBuildResult) {
		this.spiraBuildResult = spiraBuildResult;

		_spiraTestResultDetails = SpiraResultFactory.newSpiraTestResultDetails(
			this);
		_spiraTestResultValues = SpiraResultFactory.newSpiraTestResultValues(
			this);
	}

	protected final SpiraBuildResult spiraBuildResult;

	private SpiraTestCaseObject _spiraTestCaseObject;
	private final SpiraTestResultDetails _spiraTestResultDetails;
	private final SpiraTestResultValues _spiraTestResultValues;

}