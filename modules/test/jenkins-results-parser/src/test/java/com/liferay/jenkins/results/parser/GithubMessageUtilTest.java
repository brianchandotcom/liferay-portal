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
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.Project;
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
			"generic-1", "1609",
			"test-portal-acceptance-pullrequest(master)", "test-1-1");
		downloadSample(
			"rebase-1", "58",
			"test-portal-acceptance-pullrequest(ee-6.2.x)", "test-1-19");
	}

	@Test
	public void testGetFailedJobsMessage() throws Exception {
		assertSamples();
	}

	protected void processProgressiveText(
		File sampleDir, String progressiveTextURL, Properties properties)
			throws Exception {

		jobMessageUtilTest.dependenciesDir = sampleDir;

		String content = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(progressiveTextURL));
		Matcher progressiveTextMatcher = _PROGRESSIVE_TEXT_JOB_URL_PATTERN.matcher(content);
		
		int jobCount = 0;
		
		ExecutorService executor = Executors.newFixedThreadPool(_MAX_THREADS);
		List<FutureTask<FailedJobMessageResult>> taskList = new ArrayList<>();		
		while (progressiveTextMatcher.find()) {
			FailedJobMessageCallable callable = new FailedJobMessageCallable(jobCount, sampleDir, progressiveTextMatcher.group("url"));

			FutureTask<FailedJobMessageResult> futureTask = new FutureTask<>(callable);

			executor.execute(futureTask);
			taskList.add(futureTask);
			jobCount++;
		}
		
		executor.shutdown();

		int passCount = 0;
		StringBuilder reportFilesStringBuilder = new StringBuilder();
		for (FutureTask<FailedJobMessageResult> task : taskList) {
			FailedJobMessageResult result = task.get();

			if ("SUCCESS".equals(result.getResult())) {
				passCount++;
			}
			
			if (reportFilesStringBuilder.length() > 0) {
				reportFilesStringBuilder.append(" ");
			}
			
			reportFilesStringBuilder.append(result.getJobReportFilePath());
		}
		
		properties.setProperty(
			"top.level.report.files", reportFilesStringBuilder.toString());
		properties.setProperty(
			"top.level.pass.count", String.valueOf(passCount));
		properties.setProperty(
			"top.level.fail.count", String.valueOf(jobCount - passCount));
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

		Properties properties = new Properties();
		
		processProgressiveText(
			sampleDir, url.toString() + "/logText/progressiveText",	properties);
		
		JSONObject jsonObject =
			JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					url.toString() + "/api/json"));

		properties.setProperty("env.BUILD_URL", toURLString(sampleDir));
		properties.setProperty(
			"top.level.result", jsonObject.getString("result"));

		_saveProperties(new File(sampleDir, "sample.properties"), properties);	
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

	@Override
	protected String getMessage(String urlString) throws Exception {
		String localURLString = JenkinsResultsParserUtil.getLocalURL(urlString);
		
		File sampleDir = new File(localURLString.substring("file:".length()));
		
		Project project = _getProject(new File(sampleDir, "sample.properties"), "", sampleDir.getPath());

		GithubMessageUtil.getGithubMessage(project);

		return project.getProperty("github.post.comment.body");
	}

	private Project _getProject(File samplePropertiesFile,
		String buildURLString, String topLevelSharedDir)
			throws Exception {

		Project project = new Project();
		
		if (samplePropertiesFile != null) {
			Properties properties = _loadProperties(samplePropertiesFile);
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
		project.setProperty("rebase.branch.git.commit", "rebase-branch-git-commit");
		project.setProperty("repository", "junit-repository");
		project.setProperty("top.level.build.name", "junit-top-level-build-name");
		project.setProperty("top.level.build.time", "junit-top-level-build-time");
		project.setProperty("top.level.result.message", "junit-top-level-result-message");
		project.setProperty("top.level.shared.dir", topLevelSharedDir);
		project.setProperty("top.level.shared.dir.url", "junit-top-level-shared-dir-url");

		return project;
	}

	private static final int _MAX_THREADS = 1;
	private static final Pattern _PROGRESSIVE_TEXT_JOB_URL_PATTERN =
		Pattern.compile("(?<prefix>\\[echo\\] \\'.*\\' completed at )(?<url>.+)(?<suffix>. )");
	private static final Pattern _URL_JOB_NAME_PATTERN =
		Pattern.compile(
			".+://(?<hostName>[^.]+).liferay.com/job/(?<jobName>[^/]+).*/(?<buildNumber>\\d+)/");
	
	private class FailedJobMessageResult {

		public FailedJobMessageResult(String jobReportFilePath, String result) {
			_jobReportFilePath = jobReportFilePath;
			_result = result;
		}

		public String getJobReportFilePath() {
			return _jobReportFilePath;
		}

		public String getResult() {
			return _result;
		}
		
		private String _jobReportFilePath;
		private String _result;
		
	}

	private class FailedJobMessageCallable implements Callable<FailedJobMessageResult> {
		
		public FailedJobMessageCallable(int jobCount, File sampleDir, String url) {
			_jobCount = jobCount;
			_sampleDir = sampleDir;
			_url = url;
		}

		@Override
		public FailedJobMessageResult call() throws Exception {
			Matcher urlMatcher = _URL_JOB_NAME_PATTERN.matcher(_url);
			
			urlMatcher.find();
			
			File jobReportFile = new File(_sampleDir, _jobCount + "-" + urlMatcher.group("jobName") + "-report.html");
			
			Project project = _getProject(null, _url, _sampleDir.getPath());

			FailedJobMessageUtil.getFailedJobMessage(project);

			String reportString = project.getProperty("report.html.content");

			JSONObject jobJSONObject = 
				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(_url + "/api/json"));

			write(
				jobReportFile,
				"<h5 job-result=\\\"" + jobJSONObject.getString("result") +
					"\\\"><a href=\"" + _url + "\">" +
					urlMatcher.group("jobName") + "</a></h5>" +
					reportString);
			
			return new FailedJobMessageResult(jobReportFile.getPath(), jobJSONObject.getString("result"));
		}
		
		private int _jobCount;
		private File _sampleDir;
		private String _url;
		
	}
	
	

}