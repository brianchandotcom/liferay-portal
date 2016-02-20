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
import java.io.FileOutputStream;
import java.io.StringReader;

import java.net.URL;
import java.net.URLDecoder;

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
			"generic-1", "1792", "test-portal-acceptance-pullrequest(master)",
			"test-1-3");
		downloadSample(
			"jspc-1", "1799", "test-portal-acceptance-pullrequest(master)",
			"test-1-4");
		downloadSample(
			"max-fails-1", "672", "test-portal-acceptance-pullrequest(master)",
			"test-1-9");
		downloadSample(
			"rebase-1", "330", "test-portal-acceptance-pullrequest(ee-6.2.x)",
			"test-1-1");
		downloadSample(
			"unstable-1", "1790", "test-portal-acceptance-pullrequest(master)",
			"test-1-4");
	}

	@Test
	public void testGetGitHubMessage() throws Exception {
		assertSamples();
	}

	protected void downloadJobSamples(
			String progressiveTextURL, Properties properties, File sampleDir)
		throws Exception {

		gitHubJobMessageUtilTest.dependenciesDir = dependenciesDir;

		int jobTotalCount = 0;
		int jobSuccessCount = 0;
		StringBuilder sb = new StringBuilder();

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

			gitHubJobMessageUtilTest.downloadSample(
				sampleDir.getName() + "/" + "job-" + jobTotalCount,
				jobNameMatcher.group("buildNumber"),
				jobNameMatcher.group("jobName"),
				jobNameMatcher.group("hostName"));

			File jobExpectedMessageFile = getJobExpectedMessageFile(
				new File(
					sampleDir,
					"job-" + jobTotalCount + "-" +
						jobNameMatcher.group("jobName")));

			if (sb.length() > 0) {
				sb.append(" ");
			}

			sb.append(jobExpectedMessageFile.getPath());

			String result = jsonObject.getString("result");

			if (result.equals("SUCCESS")) {
				jobSuccessCount++;
			}

			jobTotalCount++;
		}

		properties.setProperty(
			"top.level.fail.count",
			String.valueOf(jobTotalCount - jobSuccessCount));
		properties.setProperty(
			"top.level.pass.count", String.valueOf(jobSuccessCount));
		properties.setProperty("top.level.report.files", sb.toString());
	}

	@Override
	protected void downloadSample(File sampleDir, URL url) throws Exception {
		Properties properties = new Properties();

		downloadJobSamples(
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

		URL url = JenkinsResultsParserUtil.createURL(urlString);

		downloadSample(sampleKey, url);
	}

	@Override
	protected void downloadSample(String sampleKey, URL url) throws Exception {
		String sampleDirName = dependenciesDir.getPath() + "/" + sampleKey;

		File sampleDir = new File(sampleDirName);

		File expectedMessageFile = new File(sampleDir, "expected_message.html");

		if (sampleDir.exists()) {
			if (!expectedMessageFile.exists()) {
				for (File jobDir : sampleDir.listFiles()) {
					String jobDirName = jobDir.getName();

					if (jobDir.isDirectory() && jobDirName.startsWith("job-")) {
						File jobExpectedMessageFile = new File(
							jobDir, "expected_message.html");

						jobExpectedMessageFile.delete();
					}
				}
			}

			for (File jobDir : sampleDir.listFiles()) {
				String jobDirName = jobDir.getName();

				if (jobDir.isDirectory() && jobDirName.startsWith("job-")) {
					File jobExpectedMessageFile = new File(
						jobDir, "expected_message.html");

					if (!jobExpectedMessageFile.exists()) {
						getJobExpectedMessageFile(jobDir);
					}
				}
			}
		}

		super.downloadSample(sampleKey, url);
	}

	protected File getJobExpectedMessageFile(File jobDir) throws Exception {
		File jsonFile = new File(jobDir, "api/json");

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(toURLString(jsonFile)));

		String urlString = URLDecoder.decode(
			jsonObject.getString("url"), "UTF-8");

		Matcher jobNameMatcher = _jobNamePattern.matcher(urlString);

		jobNameMatcher.find();

		File jobExpectedMessageFile = new File(jobDir, "expected_message.html");

		if (!jobExpectedMessageFile.exists()) {
			gitHubJobMessageUtilTest.dependenciesDir = dependenciesDir;
			gitHubJobMessageUtilTest.writeExpectedMessage(jobDir);
		}

		JenkinsResultsParserUtil.write(
			jobExpectedMessageFile,
			formatXML(
				"<div><h5 job-result=\"" + jsonObject.getString("result") +
				"\"><a href=\"" + urlString + "\">" +
				jobNameMatcher.group("jobName") + "</a></h5>" +
				JenkinsResultsParserUtil.read(
					jobExpectedMessageFile) + "</div>"));

		return jobExpectedMessageFile;
	}

	@Override
	protected String getMessage(String urlString) throws Exception {
		while (urlString.endsWith("/")) {
			urlString = urlString.substring(0, urlString.length() - 1);
		}

		File sampleDir = new File(
			dependenciesDir,
			urlString.substring(urlString.lastIndexOf("/") + 1));

		Project project = getProject(
			sampleDir.getName(), "", sampleDir.getPath());

		GitHubMessageUtil.getGitHubMessage(project);

		return formatXML(
			"<html>" + project.getProperty("github.post.comment.body") +
				"</html>");
	}

	protected Project getProject(
			String sampleName, String buildURLString,
			String topLevelSharedDirName)
		throws Exception {

		Project project = new Project();

		if ((sampleName != null) && (sampleName.length() > 0)) {
			Properties properties = loadProperties(sampleName);

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

	protected Properties loadProperties(String sampleName) throws Exception {
		Properties properties = new Properties();

		File propertiesFile = new File(
			dependenciesDir, sampleName + "/sample.properties");

		String content = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(toURLString(propertiesFile)));

		properties.load(new StringReader(content));

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