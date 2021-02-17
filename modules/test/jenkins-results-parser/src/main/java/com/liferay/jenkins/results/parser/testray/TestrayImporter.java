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

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.TopLevelBuild;

/**
 * @author Michael Hashimoto
 */
public class TestrayImporter {

	public TestrayImporter(Build build) {
		if (build == null) {
			throw new RuntimeException("Please provide a valid build");
		}

		_topLevelBuild = build.getTopLevelBuild();

		if (_topLevelBuild == null) {
			throw new RuntimeException(
				"Please provide a build with a top level build");
		}
	}

	public Job getJob() {
		if (_job != null) {
			return _job;
		}

		_job = _topLevelBuild.getJob();

		return _job;
	}

	public TestrayProject getTestrayProject() {
		if (_testrayProject != null) {
			return _testrayProject;
		}

		long start = System.currentTimeMillis();

		Job job = getJob();

		String testrayProjectID = System.getProperty("TESTRAY_PROJECT_ID");

		TestrayProject testrayProject = null;

		if ((testrayProjectID != null) && testrayProjectID.matches("\\d+")) {
			testrayProject = _testrayServer.getTestrayProjectByID(
				Integer.parseInt(testrayProjectID));
		}

		String testrayProjectName = System.getProperty("TESTRAY_PROJECT_NAME");

		if ((testrayProject == null) &&
			!JenkinsResultsParserUtil.isNullOrEmpty(testrayProjectName)) {

			testrayProject = _testrayServer.getTestrayProjectByName(
				testrayProjectName);
		}

		testrayProjectID = JenkinsResultsParserUtil.getProperty(
			job.getJobProperties(), "testray.project.id", job.getJobName(),
			_topLevelBuild.getTestSuiteName());

		if ((testrayProject == null) && (testrayProjectID != null) &&
			testrayProjectID.matches("\\d+")) {

			testrayProject = _testrayServer.getTestrayProjectByID(
				Integer.parseInt(testrayProjectID));
		}

		testrayProjectName = JenkinsResultsParserUtil.getProperty(
			job.getJobProperties(), "testray.project.name", job.getJobName(),
			_topLevelBuild.getTestSuiteName());

		if ((testrayProject == null) &&
			!JenkinsResultsParserUtil.isNullOrEmpty(testrayProjectName)) {

			testrayProject = _testrayServer.getTestrayProjectByName(
				testrayProjectName);
		}

		if (testrayProject != null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Testray Project ", String.valueOf(testrayProject.getURL()),
					" created in ",
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start)));

			_testrayProject = testrayProject;

			return _testrayProject;
		}

		return null;
	}

	public TestrayServer getTestrayServer() {
		long start = System.currentTimeMillis();

		String testrayServerURL = System.getProperty("TESTRAY_SERVER_URL");

		TestrayServer testrayServer = null;

		if ((testrayServerURL != null) &&
			testrayServerURL.matches("https?://.*")) {

			testrayServer = new TestrayServer(testrayServerURL);
		}

		Job job = getJob();

		testrayServerURL = JenkinsResultsParserUtil.getProperty(
			job.getJobProperties(), "testray.server.url", job.getJobName(),
			_topLevelBuild.getTestSuiteName());

		if ((testrayServer == null) && (testrayServerURL != null) &&
			testrayServerURL.matches("https?://.*")) {

			testrayServer = new TestrayServer(testrayServerURL);
		}

		if (testrayServer != null) {
			_testrayServer = testrayServer;

			_testrayServer.setAdminUserName(
				JenkinsResultsParserUtil.getProperty(
					job.getJobProperties(), "testray.admin.user.name",
					job.getJobName(), _topLevelBuild.getTestSuiteName()));

			_testrayServer.setAdminUserPassword(
				JenkinsResultsParserUtil.getProperty(
					job.getJobProperties(), "testray.admin.user.password",
					job.getJobName(), _topLevelBuild.getTestSuiteName()));

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Testray Server ", String.valueOf(_testrayServer.getURL()),
					" created in ",
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start)));

			return _testrayServer;
		}

		return null;
	}

	public TopLevelBuild getTopLevelBuild() {
		return _topLevelBuild;
	}

	private Job _job;
	private TestrayProject _testrayProject;
	private TestrayServer _testrayServer;
	private final TopLevelBuild _topLevelBuild;

}