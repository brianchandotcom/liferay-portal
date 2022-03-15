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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestrayResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestrayServer implements TestrayServer {

	@Override
	public TestrayProject getTestrayProjectByID(int projectID) {
		_initTestrayProjects();

		return _testrayProjectsByID.get(projectID);
	}

	@Override
	public TestrayProject getTestrayProjectByName(String projectName) {
		_initTestrayProjects();

		return _testrayProjectsByName.get(projectName);
	}

	@Override
	public List<TestrayProject> getTestrayProjects() {
		_initTestrayProjects();

		return new ArrayList<>(_testrayProjectsByName.values());
	}

	@Override
	public URL getURL() {
		return _url;
	}

	@Override
	public void importCaseResults(TopLevelBuild topLevelBuild) {
		TestrayResultsParserUtil.processTestrayResultFiles(getResultsDir());

		if (JenkinsResultsParserUtil.isCINode()) {
			_importCaseResultsFromCI(topLevelBuild);
		}

		if (TestrayS3Bucket.googleCredentialsAvailable()) {
			_importCaseResultsToGCP(topLevelBuild);
		}
	}

	@Override
	public void writeCaseResult(String fileName, String fileContent) {
		if (JenkinsResultsParserUtil.isNullOrEmpty(fileName) ||
			JenkinsResultsParserUtil.isNullOrEmpty(fileContent)) {

			return;
		}

		try {
			JenkinsResultsParserUtil.write(
				new File(getResultsDir(), fileName), fileContent);
		}
		catch (IOException ioException) {
		}
	}

	protected BaseTestrayServer(String urlString) {
		try {
			_url = new URL(urlString);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(
				"Invalid Testray server URL " + urlString,
				malformedURLException);
		}
	}

	protected File getResultsDir() {
		String workspace = System.getenv("WORKSPACE");

		if (JenkinsResultsParserUtil.isNullOrEmpty(workspace)) {
			throw new RuntimeException("Please set WORKSPACE");
		}

		return new File(workspace, "testray/results");
	}

	private void _importCaseResultsFromCI(TopLevelBuild topLevelBuild) {
		if (!JenkinsResultsParserUtil.isCINode()) {
			return;
		}

		JenkinsMaster jenkinsMaster = topLevelBuild.getJenkinsMaster();

		String command = JenkinsResultsParserUtil.combine(
			"rsync -aqz --chmod=go=rx \"",
			JenkinsResultsParserUtil.getCanonicalPath(getResultsDir()),
			"\"/* \"", jenkinsMaster.getName(),
			"::testray-results/production/\"");

		try {
			JenkinsResultsParserUtil.executeBashCommands(command);
		}
		catch (IOException | TimeoutException exception) {
			throw new RuntimeException(exception);
		}

		for (File resultFile :
				JenkinsResultsParserUtil.findFiles(getResultsDir(), ".*.xml")) {

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Uploaded ",
					JenkinsResultsParserUtil.getCanonicalPath(resultFile),
					" by Rsync"));
		}
	}

	private void _importCaseResultsToGCP(TopLevelBuild topLevelBuild) {
		if (!TestrayS3Bucket.googleCredentialsAvailable()) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		JenkinsMaster jenkinsMaster = topLevelBuild.getJenkinsMaster();

		sb.append(jenkinsMaster.getName());

		sb.append("-");

		String jobName = topLevelBuild.getJobName();

		sb.append(jobName.replaceAll("[\\(\\)]", "_"));

		sb.append("-");
		sb.append(topLevelBuild.getBuildNumber());
		sb.append("-results.tar.gz");

		File resultsDir = getResultsDir();

		File resultsTarGzFile = new File(
			resultsDir.getParentFile(), sb.toString());

		JenkinsResultsParserUtil.tarGzip(resultsDir, resultsTarGzFile);

		TestrayS3Bucket testrayS3Bucket = TestrayS3Bucket.getInstance();

		testrayS3Bucket.createTestrayS3Object(
			"to-be-done/" + resultsTarGzFile.getName(), resultsTarGzFile);
	}

	private synchronized void _initTestrayProjects() {
		if ((_testrayProjectsByID != null) &&
			(_testrayProjectsByName != null)) {

			return;
		}

		_testrayProjectsByID = new HashMap<>();
		_testrayProjectsByName = new HashMap<>();

		int current = 1;

		while (true) {
			try {
				String projectAPIURL = JenkinsResultsParserUtil.combine(
					String.valueOf(getURL()),
					"/home/-/testray/projects.json?cur=",
					String.valueOf(current), "&delta=", String.valueOf(_DELTA),
					"&orderByCol=testrayProjectId");

				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					projectAPIURL, true);

				JSONArray dataJSONArray = jsonObject.getJSONArray("data");

				if (dataJSONArray.length() == 0) {
					break;
				}

				for (int i = 0; i < dataJSONArray.length(); i++) {
					JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

					TestrayProject testrayProject = new TestrayProject(
						this, dataJSONObject);

					_testrayProjectsByID.put(
						testrayProject.getID(), testrayProject);
					_testrayProjectsByName.put(
						testrayProject.getName(), testrayProject);
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
			finally {
				current++;
			}
		}
	}

	private static final int _DELTA = 50;

	private Map<Integer, TestrayProject> _testrayProjectsByID;
	private Map<String, TestrayProject> _testrayProjectsByName;
	private final URL _url;

}