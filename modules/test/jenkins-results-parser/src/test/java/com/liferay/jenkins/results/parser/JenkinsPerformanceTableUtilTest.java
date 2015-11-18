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

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.Project;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class JenkinsPerformanceTableUtilTest
	extends BaseJenkinsResultsParserTestCase {

	@Before
	public void setUp() throws Exception {
		downloadSample(
			"master-success-1", "1682",
			"test-portal-acceptance-pullrequest(master)", "test-1-1");
		downloadSample(
			"master-failure-1", "1697",
			"test-portal-acceptance-pullrequest(master)", "test-1-1");
		downloadSample(
			"6.2.x-success-1", "317",
			"test-portal-acceptance-pullrequest(ee-6.2.x)", "test-1-1");
		downloadSample(
			"6.2.x-failure-1", "313",
			"test-portal-acceptance-pullrequest(ee-6.2.x)", "test-1-1");
	}

	@Test
	public void testGetHTML() throws Exception {
		assertSamples();
	}

	@Override
	protected void downloadSample(File sampleDir, URL url) throws Exception {
		downloadSampleJobMessages(
			url.toString() + "/logText/progressiveText", sampleDir);
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
			String progressiveTextURL, File sampleDir)
		throws Exception {

		int jobCount = 0;

		String content = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(progressiveTextURL));

		Matcher progressiveTextMatcher = _progressiveTextPattern.matcher(
			content);

		StringBuilder sb = new StringBuilder();

		while (progressiveTextMatcher.find()) {
			String fileSuffix = null;
			String urlString = progressiveTextMatcher.group("url");
			String urlSuffix = null;

			if (urlString.contains("-source")) {
				fileSuffix = "source-" + jobCount;

				urlSuffix = "/api/json";
			}
			else {
				fileSuffix = Integer.toString(jobCount);

				urlSuffix = "/testReport/api/json";
			}

			write(
				new File(sampleDir, "job-" + fileSuffix + urlSuffix),
				JenkinsResultsParserUtil.toString(
					JenkinsResultsParserUtil.getLocalURL(
						urlString + urlSuffix + "?pretty")));

			if (sb.length() > 0) {
				sb.append("|");
			}

			sb.append(toURLString(new File(sampleDir, "job-" + fileSuffix)));

			jobCount++;
		}

		write(new File(sampleDir, "urls.txt"), sb.toString());
	}

	@Override
	protected String getMessage(String urlString) throws Exception {
		String localURL = JenkinsResultsParserUtil.getLocalURL(
			urlString + "/urls.txt");
		String urlText = JenkinsResultsParserUtil.toString(localURL);
		String[] urlArray = urlText.split("\\|");

		for (String url : urlArray) {
			if (url.length() == 0) {
				continue;
			}

			JenkinsPerformanceDataUtil.processPerformanceData(
				"build", url.trim(), 100);
		}

		return JenkinsPerformanceTableUtil.generateHTML();
	}

	protected Project getProject(
			File samplePropertiesFile, String buildURLString,
			String topLevelSharedDirName)
		throws Exception {

		Project project = new Project();

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

	private static final Pattern _progressiveTextPattern = Pattern.compile(
		"\\[echo\\] \\'.*\\' completed at (?<url>.+)\\.");

}