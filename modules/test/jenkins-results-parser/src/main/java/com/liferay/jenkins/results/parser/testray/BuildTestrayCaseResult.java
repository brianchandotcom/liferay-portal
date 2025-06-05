/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.io.File;

import java.net.URL;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Michael Hashimoto
 */
public abstract class BuildTestrayCaseResult extends TestrayCaseResult {

	public BuildTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild) {

		super(testrayBuild, topLevelBuild);

		String workspace = System.getenv("WORKSPACE");

		if (JenkinsResultsParserUtil.isNullOrEmpty(workspace)) {
			throw new RuntimeException("Please set WORKSPACE");
		}

		_testrayUploadBaseDir = new File(
			workspace,
			"testray/" + JenkinsResultsParserUtil.getDistinctTimeStamp());
	}

	@Override
	public long getDuration() {
		Build build = getBuild();

		if (build == null) {
			return 0;
		}

		return build.getDuration();
	}

	@Override
	public Status getStatus() {
		Build build = getBuild();

		if (build == null) {
			return Status.UNTESTED;
		}

		if (build.isFailing()) {
			return Status.FAILED;
		}

		return Status.PASSED;
	}

	protected abstract Build getBuild();

	protected TestrayAttachment getTestrayAttachment(
		Build build, String name, String key) {

		if (_testrayAttachments.containsKey(key)) {
			return _testrayAttachments.get(key);
		}

		if ((build == null) || JenkinsResultsParserUtil.isNullOrEmpty(key) ||
			JenkinsResultsParserUtil.isNullOrEmpty(name) ||
			!TestrayS3Bucket.hasGoogleApplicationCredentials()) {

			return null;
		}

		for (URL testrayS3AttachmentURL : build.getTestrayAttachmentURLs()) {
			String testrayS3AttachmentURLString = String.valueOf(
				testrayS3AttachmentURL);

			if (!testrayS3AttachmentURLString.contains(key)) {
				continue;
			}

			TestrayAttachment testrayAttachment = new S3TestrayAttachment(
				this, name, key);

			_testrayAttachments.put(key, testrayAttachment);

			return _testrayAttachments.get(key);
		}

		return null;
	}

	protected File getTestrayUploadBaseDir() {
		return _testrayUploadBaseDir;
	}

	protected String getTopLevelBuildReportKey() {
		return getTopLevelBuildURLPath() + "/build-report.json.gz";
	}

	protected String getTopLevelBuildReportName() {
		return "Build Report (Top Level)";
	}

	protected TestrayAttachment getTopLevelBuildReportTestrayAttachment() {
		return getTestrayAttachment(
			getTopLevelBuild(), getTopLevelBuildReportName(),
			getTopLevelBuildReportKey());
	}

	protected String getTopLevelBuildURLPath() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (topLevelBuild == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		Date date = new Date(topLevelBuild.getStartTime());

		sb.append(
			JenkinsResultsParserUtil.toDateString(
				date, "yyyy-MM", "America/Los_Angeles"));

		sb.append("/");

		JenkinsMaster jenkinsMaster = topLevelBuild.getJenkinsMaster();

		sb.append(jenkinsMaster.getName());

		sb.append("/");
		sb.append(topLevelBuild.getJobName());
		sb.append("/");
		sb.append(topLevelBuild.getBuildNumber());

		return sb.toString();
	}

	protected String getTopLevelJenkinsConsoleKey() {
		return getTopLevelBuildURLPath() + "/jenkins-console.txt.gz";
	}

	protected String getTopLevelJenkinsConsoleName() {
		return "Jenkins Console (Top Level)";
	}

	protected TestrayAttachment getTopLevelJenkinsConsoleTestrayAttachment() {
		return getTestrayAttachment(
			getTopLevelBuild(), getTopLevelJenkinsConsoleName(),
			getTopLevelJenkinsConsoleKey());
	}

	protected String getTopLevelJenkinsReportKey() {
		return getTopLevelBuildURLPath() + "/jenkins-report.html.gz";
	}

	protected String getTopLevelJenkinsReportName() {
		return "Jenkins Report (Top Level)";
	}

	protected TestrayAttachment getTopLevelJenkinsReportTestrayAttachment() {
		return getTestrayAttachment(
			getTopLevelBuild(), getTopLevelJenkinsReportName(),
			getTopLevelJenkinsReportKey());
	}

	protected String getTopLevelJobSummaryKey() {
		return getTopLevelBuildURLPath() + "/job-summary/index.html.gz";
	}

	protected String getTopLevelJobSummaryName() {
		return "Job Summary (Top Level)";
	}

	protected TestrayAttachment getTopLevelJobSummaryTestrayAttachment() {
		return getTestrayAttachment(
			getTopLevelBuild(), getTopLevelJobSummaryName(),
			getTopLevelJobSummaryKey());
	}

	protected TestrayAttachment uploadTestrayAttachment(
		String name, String key, Callable<File> callable) {

		File file = null;

		try {
			file = callable.call();
		}
		catch (Exception exception) {
			return null;
		}

		if ((file == null) || !file.exists()) {
			return null;
		}

		TestrayAttachment testrayAttachment = _uploadS3TestrayAttachment(
			name, key, file);

		if (testrayAttachment == null) {
			return testrayAttachment;
		}

		_testrayAttachments.put(key, testrayAttachment);

		return testrayAttachment;
	}

	private TestrayAttachment _uploadS3TestrayAttachment(
		String name, String key, File file) {

		if (!file.exists()) {
			return null;
		}

		try {
			TestrayS3Bucket testrayS3Bucket = TestrayS3Bucket.getInstance();

			testrayS3Bucket.createTestrayS3Object(key, file);

			return new S3TestrayAttachment(this, name, key);
		}
		catch (Exception exception) {
			return null;
		}
	}

	private static final Map<String, TestrayAttachment> _testrayAttachments =
		new HashMap<>();

	private final File _testrayUploadBaseDir;

}