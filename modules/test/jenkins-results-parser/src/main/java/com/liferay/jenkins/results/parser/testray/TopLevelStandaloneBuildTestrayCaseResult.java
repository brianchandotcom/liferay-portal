/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuildReport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class TopLevelStandaloneBuildTestrayCaseResult
	extends BaseStandaloneBuildTestrayCaseResult {

	public TopLevelStandaloneBuildTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuildReport topLevelBuildReport) {

		super(testrayBuild, topLevelBuildReport);

		initBuildReport();
	}

	@Override
	public String getName() {
		return "Top Level Build";
	}

	@Override
	public List<TestrayAttachment> getTestrayAttachments() {
		List<TestrayAttachment> testrayAttachments = new ArrayList<>();

		testrayAttachments.add(getTopLevelBuildDatabaseTestrayAttachment());
		testrayAttachments.add(getTopLevelBuildReportTestrayAttachment());
		testrayAttachments.add(getTopLevelJenkinsConsoleTestrayAttachment());
		testrayAttachments.add(getTopLevelJenkinsReportTestrayAttachment());
		testrayAttachments.add(getTopLevelJobSummaryTestrayAttachment());

		testrayAttachments.removeAll(Collections.singleton(null));

		return testrayAttachments;
	}

	@Override
	protected String getBatchName() {
		return "top-level-build";
	}

	@Override
	protected String getFileName() {
		TopLevelBuildReport topLevelBuildReport = getTopLevelBuildReport();

		JenkinsMaster jenkinsMaster = topLevelBuildReport.getJenkinsMaster();

		return JenkinsResultsParserUtil.combine(
			"TESTS-", jenkinsMaster.getName(), "_",
			topLevelBuildReport.getJobName(), "_",
			String.valueOf(topLevelBuildReport.getBuildNumber()), "_",
			getBatchName(), ".xml");
	}

	@Override
	protected void initBuildReport() {
		setBuildReport(getTopLevelBuildReport());
	}

}