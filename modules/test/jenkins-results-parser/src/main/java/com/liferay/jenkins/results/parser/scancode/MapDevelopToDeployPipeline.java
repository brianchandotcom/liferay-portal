/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.scancode;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.json.JSONObject;

/**
 * @author Brittney Nguyen
 */
public class MapDevelopToDeployPipeline extends ScanCodePipeline {

	@Override
	public void execute() throws IOException, TimeoutException {
		invokeScan(getJSONObject());

		waitForScan(_pipelineName);

		checkComplianceAlerts("ERROR");

		checkComplianceAlerts("WARNING");

		downloadResultFiles();

		sendSlackNotification(getS3URL());
	}

	public JSONObject getJSONObject() throws IOException {
		JSONObject jsonObject = new JSONObject();

		List<String> inputURLS = new ArrayList<>();

		String tomcatURL = JenkinsResultsParserUtil.getBuildParameter(
			_buildURL, "TEST_PORTAL_RELEASE_TOMCAT_URL");

		inputURLS.add(tomcatURL + "#to");

		inputURLS.add(getReleaseTarballLink());
		inputURLS.add(
			JenkinsResultsParserUtil.getBuildProperty("scancode.tar.gz.url"));
		inputURLS.add(
			JenkinsResultsParserUtil.getBuildProperty(
				"scancode.config.file.url"));

		String portalReleaseVersion =
			JenkinsResultsParserUtil.getBuildParameter(
				_buildURL, "TEST_PORTAL_RELEASE_VERSION");

		SimpleDateFormat simpleDateFormat = getSimpleDateFormat();

		jsonObject.put(
			"execute_now", true
		).put(
			"input_urls", inputURLS
		).put(
			"labels", getLabels(new String[] {portalReleaseVersion})
		).put(
			"name",
			JenkinsResultsParserUtil.combine(
				portalReleaseVersion, " Scan-",
				simpleDateFormat.format(new Date()))
		).put(
			"pipeline", "map_deploy_to_develop:Java,Javascript"
		);

		return jsonObject;
	}

	public String getReleaseTarballLink() {
		String portalBranchUsername =
			JenkinsResultsParserUtil.getBuildParameter(
				_buildURL, "TEST_PORTAL_USER_NAME");

		String portalSHA = JenkinsResultsParserUtil.getBuildParameter(
			_buildURL, "TEST_PORTAL_RELEASE_GIT_ID");

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/");
		sb.append(portalBranchUsername);
		sb.append("/liferay-portal-ee/archive/");
		sb.append(portalSHA);
		sb.append(".tar.gz");
		sb.append("#from");

		return sb.toString();
	}

	protected MapDevelopToDeployPipeline(String buildURL, String pipelineName) {
		super(buildURL, pipelineName);

		_buildURL = buildURL;
		_pipelineName = pipelineName;
	}

	private final String _buildURL;
	private final String _pipelineName;

}