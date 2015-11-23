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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.net.URL;

import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.Project;

import org.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class GitHubMessageUtilTest extends BaseJenkinsResultsParserTestCase {

	@Before
	public void setUp() throws Exception {
		downloadSample(
			"generic-1", "1609", "test-portal-acceptance-pullrequest(master)",
			"test-1-1");
		downloadSample(
			"rebase-1", "58", "test-portal-acceptance-pullrequest(ee-6.2.x)",
			"test-1-19");
		downloadSample(
			"jspc-1", "1672", "test-portal-acceptance-pullrequest(master)",
			"test-1-5");
	}

	@Test
	public void testGetFailedJobsMessage() throws Exception {
		assertSamples();
	}

	@Override
	protected void downloadSample(File sampleDir, URL url) throws Exception {
		Properties properties = new Properties();

		downloadSampleJobMessages(
			url.toString() + "/logText/progressiveText", properties, sampleDir);

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(url.toString() + "/api/json"));

		properties.setProperty("env.BUILD_URL", toURLString(sampleDir));
		properties.setProperty(
			"top.level.result", jsonObject.getString("result"));

		saveProperties(new File(sampleDir, "sample.properties"), properties);
	}

	protected void downloadSample(
			String sampleKey, String buildNumber, String jobName,
			String hostName)
		throws Exception {

		String urlString =
			"https://${hostName}.liferay.com/job/${jobName}/${buildNumber}/";

		urlString = replaceToken(urlString, "buildNumber", buildNumber);
		urlString = replaceToken(urlString, "hostName", hostName);
		urlString = replaceToken(urlString, "jobName", jobName);

		URL url = createURL(urlString);

		downloadSample(sampleKey, url);
	}

	protected void downloadSampleJobMessages(
			String progressiveTextURL, Properties properties, File sampleDir)
		throws Exception {

		gitHubJobMessageUtilTest.dependenciesDir = sampleDir;

		int jobCount = 0;
		int passCount = 0;
		StringBuilder reportFilesSB = new StringBuilder();

		String content = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(progressiveTextURL));

		Matcher progressiveTextMatcher = _progressiveTextPattern.matcher(
			content);

		while (progressiveTextMatcher.find()) {
			String urlString = progressiveTextMatcher.group("url");

			Matcher jobNameMatcher = _jobNamePattern.matcher(urlString);

			jobNameMatcher.find();

			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(urlString + "/api/json"));

			Project project = getProject(null, urlString, sampleDir.getPath());

			GitHubJobMessageUtil.getGitHubJobMessage(project);

			File reportFile = new File(sampleDir, jobCount + "-report.html");

			write(
				reportFile,
				"<h5 job-result=\\\"" + jsonObject.getString("result") +
					"\\\"><a href=\"" + urlString + "\">" +
						jobNameMatcher.group("jobName") + "</a></h5>" +
				project.getProperty("report.html.content"));

			if (reportFilesSB.length() > 0) {
				reportFilesSB.append(" ");
			}

			reportFilesSB.append(reportFile.getPath());

			String result = jsonObject.getString("result");

			if (result.equals("SUCCESS")) {
				passCount++;
			}

			jobCount++;
		}

		properties.setProperty(
			"top.level.fail.count", String.valueOf(jobCount - passCount));
		properties.setProperty(
			"top.level.pass.count", String.valueOf(passCount));
		properties.setProperty(
			"top.level.report.files", reportFilesSB.toString());
	}

	@Override
	protected String getMessage(String urlString) throws Exception {
		String localURLString = JenkinsResultsParserUtil.getLocalURL(urlString);

		File sampleDir = new File(localURLString.substring("file:".length()));

		Project project = getProject(
			new File(sampleDir, "sample.properties"), "", sampleDir.getPath());

		GitHubMessageUtil.getGitHubMessage(project);

		return project.getProperty("github.post.comment.body");
	}

	protected Project getProject(
			File samplePropertiesFile, String buildURLString,
			String topLevelSharedDirName)
		throws Exception {

		Project project = new Project();

		if (samplePropertiesFile != null) {
			Properties properties = loadProperties(samplePropertiesFile);

			for (Entry<Object, Object> entry : properties.entrySet()) {
				project.setProperty(
					String.valueOf(entry.getKey()),
					String.valueOf(entry.getValue()));
			}
		}

		project.setProperty("branch.name", "junit-branch-name");
		project.setProperty("build.url", buildURLString);
		project.setProperty(
			"github.pull.request.head.branch", "junit-pr-head-branch");
		project.setProperty(
			"github.pull.request.head.username", "junit-pr-head-username");
		project.setProperty("plugins.branch.name", "junit-plugins-branch-name");
		project.setProperty("plugins.repository", "junit-plugins-repository");
		project.setProperty("portal.repository", "junit-portal-repository");
		project.setProperty(
			"rebase.branch.git.commit", "rebase-branch-git-commit");
		project.setProperty("repository", "junit-repository");
		project.setProperty(
			"top.level.build.name", "junit-top-level-build-name");
		project.setProperty(
			"top.level.build.time", "junit-top-level-build-time");
		project.setProperty(
			"top.level.result.message", "junit-top-level-result-message");
		project.setProperty("top.level.shared.dir", topLevelSharedDirName);
		project.setProperty(
			"top.level.shared.dir.url", "junit-top-level-shared-dir-url");

		return project;
	}

	protected Properties loadProperties(File file) throws Exception {
		Properties properties = new Properties();

		try (FileInputStream fis = new FileInputStream(file)) {
			properties.load(fis);
		}

		return properties;
	}

	protected void saveProperties(File file, Properties properties)
		throws Exception {

		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			properties.store(fileOutputStream, null);
		}
	}

	protected GitHubJobMessageUtilTest gitHubJobMessageUtilTest =
		new GitHubJobMessageUtilTest();

	private static final Pattern _jobNamePattern = Pattern.compile(
		".+://(?<hostName>[^.]+).liferay.com/job/(?<jobName>[^/]+).*/" +
			"(?<buildNumber>\\d+)/");
	private static final Pattern _progressiveTextPattern = Pattern.compile(
		"\\[echo\\] \\'.*\\' completed at (?<url>.+)\\.");

}