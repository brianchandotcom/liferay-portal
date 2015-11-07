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
import java.net.URLDecoder;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.Project;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class GithubMessageUtilTest extends BaseJenkinsResultsParserTestCase {

	protected FailedJobMessageUtilTest jobMessageUtilTest;
	
	public GithubMessageUtilTest() {
		jobMessageUtilTest = new FailedJobMessageUtilTest();
	}
	
	@Before
	public void setUp() throws Exception {
		downloadSample(
			"generic-fail", "1609",
			"test-portal-acceptance-pullrequest(master)", "test-1-1",
			true);
	}

	@Test
	public void testGetFailedJobsMessage() throws Exception {
		assertSamples();
	}
	
	protected void processProgressiveText(
		File sampleDir, File progressiveTextFile, Properties properties)
			throws Exception {

		jobMessageUtilTest.dependenciesDir = sampleDir;

		String content = read(progressiveTextFile);
		Matcher progressiveTextMatcher = _PROGRESSIVE_TEXT_JOB_URL_PATTERN.matcher(content);
		
		StringBuffer progressiveTextBuffer = new StringBuffer();
		StringBuffer reportFilesBuffer = new StringBuffer();
		int passCount = 0;
		int jobCount = 0;
		while (progressiveTextMatcher.find()) {
			String urlString = progressiveTextMatcher.group("url");			
			Matcher urlMatcher = _URL_JOB_NAME_PATTERN.matcher(urlString);
			
			urlMatcher.find();
			
			jobMessageUtilTest.downloadSample(String.valueOf(jobCount), urlMatcher.group("buildNumber"), urlMatcher.group("jobName"), urlMatcher.group("hostName"), false);

			File jobDir = new File(sampleDir, jobCount + "-" + urlMatcher.group("jobName"));

			JSONObject jobJSONObject = 
				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(toURLString(
						new File(jobDir, "/api/json"))));
			
			File jobExpectedMessageFile = new File(jobDir, "expected_message.html");
			
			write(
				jobExpectedMessageFile,
				"<h5 job-result=\\\"" + jobJSONObject.getString("result") +
					"\\\"><a href=\"" + toURLString(jobDir) + "\">" +
					urlMatcher.group("jobName") + "</a></h5>" +
					read(jobExpectedMessageFile));
			
			
			if (reportFilesBuffer.length() > 0) {
				reportFilesBuffer.append(" ");
			}
			
			reportFilesBuffer.append(sampleDir.getPath());
			reportFilesBuffer.append("/");
			reportFilesBuffer.append(jobCount);
			reportFilesBuffer.append("-");
			reportFilesBuffer.append(urlMatcher.group("jobName"));
			reportFilesBuffer.append("/");
			reportFilesBuffer.append("expected_message.html");

			if ("SUCCESS".equals(jobJSONObject.getString("result"))) {
				passCount++;
			}

			progressiveTextMatcher.appendReplacement(
				progressiveTextBuffer, 
				Matcher.quoteReplacement(
					progressiveTextMatcher.group("prefix") +
					toURLString(new File(sampleDir, jobCount + "-" + urlMatcher.group("jobName"))) + 
					progressiveTextMatcher.group("suffix")));

			jobCount++;
		}
		properties.setProperty(
			"top.level.report.files", reportFilesBuffer.toString());
		properties.setProperty(
			"top.level.pass.count", String.valueOf(passCount));
		properties.setProperty(
			"top.level.fail.count", String.valueOf(jobCount - passCount));
		
		progressiveTextMatcher.appendTail(progressiveTextBuffer);
		
		write(progressiveTextFile, progressiveTextBuffer.toString());
	}
	
	private void _saveProperties(File file, Properties properties)
		throws Exception {

		try (FileOutputStream fos = new FileOutputStream(file)) {
			properties.store(fos, null);
		}
	}
	
	private Properties _loadProperties(File file) throws Exception {
		Properties properties = new Properties();
		
		try (FileInputStream fis = new FileInputStream(file)) {
			properties.load(fis);
		}
		
		return properties;
	}


	@Override
	protected void downloadSample(File sampleDir, URL url) throws Exception {
		downloadSampleURL(sampleDir, url, "/api/json");
		downloadSampleURL(sampleDir, url, "/logText/progressiveText");

		Properties properties = new Properties();
		
		processProgressiveText(
			sampleDir, new File(sampleDir, "/logText/progressiveText"),
			properties);
		
		JSONObject jsonObject =
			JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					toURLString(new File(sampleDir, "/api/json"))), false);

		properties.setProperty("env.BUILD_URL", toURLString(sampleDir));
		properties.setProperty(
			"top.level.result", jsonObject.getString("result"));

		_saveProperties(new File(sampleDir, "sample.properties"), properties);
		
	}

	protected void downloadSample(
			String sampleKey, String buildNumber, String jobName,
			String hostName, boolean generateJavacOutputFile)
		throws Exception {

		String urlString =
			"https://${hostName}.liferay.com/job/${jobName}/${buildNumber}/";

		urlString = replaceToken(urlString, "buildNumber", buildNumber);
		urlString = replaceToken(urlString, "hostName", hostName);
		urlString = replaceToken(urlString, "jobName", jobName);

		URL url = createURL(urlString);

		downloadSample(sampleKey, url);

		if (!generateJavacOutputFile) {
			File javacOutputFile = new File(
				dependenciesDir,
				sampleKey + "/" + _JAVAC_OUTPUT_FILE_NAME);

			if (javacOutputFile.exists()) {
				javacOutputFile.delete();
			}
		}
	}

	@Override
	protected String getMessage(String urlString) throws Exception {
		String localURLString = JenkinsResultsParserUtil.getLocalURL(urlString);
		
		File sampleDir = new File(localURLString.substring("file:".length()));
		
		Project project = _getProject(new File(sampleDir, "sample.properties"), sampleDir.getPath());

		GithubMessageUtil.getGithubMessage(project);

		return project.getProperty("github.post.comment.body");
	}

	private Project _getProject(File samplePropertiesFile,
		String topLevelSharedDir)
			throws Exception {
		

		Project project = new Project();
		
		Properties properties = _loadProperties(samplePropertiesFile);
		for (Entry<Object, Object> entry : properties.entrySet()) {
			project.setProperty(
				String.valueOf(entry.getKey()),
				String.valueOf(entry.getValue()));
		}
		
		project.setProperty("branch.name", "junit-branch-name");
		project.setProperty(
			"github.pull.request.head.branch", "junit-pr-head-branch");
		project.setProperty(
			"github.pull.request.head.username", "junit-pr-head-username");
		project.setProperty("plugins.branch.name", "junit-plugins-branch-name");
		project.setProperty("plugins.repository", "junit-plugins-repository");
		project.setProperty("portal.repository", "junit-portal-repository");
		project.setProperty("rebase.branch.git.commit", "rebase-branch-git-commit");
		project.setProperty("repository", "junit-repository");
		project.setProperty("top.level.build.name", "junit-top-level-build-name");
		project.setProperty("top.level.build.time", "junit-top-level-build-time");
		project.setProperty("top.level.result.message", "junit-top-level-result-message");
		project.setProperty("top.level.shared.dir", topLevelSharedDir);
		project.setProperty("top.level.shared.dir.url", "junit-top-level-shared-dir-url");

		return project;
	}

	private static final String _JAVAC_OUTPUT_FILE_NAME = "javac.output.txt";
	private static final Pattern _PROGRESSIVE_TEXT_JOB_URL_PATTERN =
		Pattern.compile("(?<prefix>\\[echo\\] \\'.*\\' completed at )(?<url>.+)(?<suffix>. )");
	private static final Pattern _URL_JOB_NAME_PATTERN =
		Pattern.compile(
			".+://(?<hostName>[^.]+).liferay.com/job/(?<jobName>[^/]+).*/(?<buildNumber>\\d+)/");

}