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
import com.liferay.jenkins.results.parser.BuildReportFactory;
import com.liferay.jenkins.results.parser.CIJobSummaryReportUtil;
import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.TopLevelBuildReport;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TopLevelTestrayCaseResult extends BuildTestrayCaseResult {

	public TopLevelTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild) {

		super(testrayBuild, topLevelBuild);
	}

	@Override
	public Build getBuild() {
		return getTopLevelBuild();
	}

	@Override
	public String getComponentName() {
		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"testray.case.component", "top-level-build");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String getErrors() {
		return null;
	}

	@Override
	public String getName() {
		return "Top Level Build";
	}

	@Override
	public int getPriority() {
		try {
			String testrayCasePriority = JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"testray.case.priority", "top-level-build");

			if ((testrayCasePriority != null) &&
				testrayCasePriority.matches("\\d+")) {

				return Integer.parseInt(testrayCasePriority);
			}

			return 5;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String getTeamName() {
		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"testray.case.team", "top-level-build");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public List<TestrayAttachment> getTestrayAttachments() {
		List<TestrayAttachment> testrayAttachments = new ArrayList<>();

		testrayAttachments.add(getTopLevelBuildReportTestrayAttachment());
		testrayAttachments.add(getTopLevelJenkinsConsoleTestrayAttachment());
		testrayAttachments.add(getTopLevelJenkinsReportTestrayAttachment());
		testrayAttachments.add(getTopLevelJobSummaryTestrayAttachment());

		testrayAttachments.removeAll(Collections.singleton(null));

		return testrayAttachments;
	}

	@Override
	public String getType() {
		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"testray.case.type", "top-level-build");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String[] getWarnings() {
		return null;
	}

	@Override
	protected TestrayAttachment getTopLevelBuildReportTestrayAttachment() {
		String key = getTopLevelBuildReportKey();
		String name = getTopLevelBuildReportName();

		TestrayAttachment testrayAttachment = getTestrayAttachment(
			getTopLevelBuild(), name, key);

		if (testrayAttachment != null) {
			return testrayAttachment;
		}

		final TopLevelBuild topLevelBuild = getTopLevelBuild();

		return uploadTestrayAttachment(
			name, key,
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File file = new File(
						getTestrayUploadBaseDir(), "build-report.json");
					File gzipFile = new File(
						getTestrayUploadBaseDir(), "build-report.json.gz");

					TopLevelBuildReport topLevelBuildReport =
						BuildReportFactory.newTopLevelBuildReport(
							topLevelBuild);

					if (topLevelBuildReport == null) {
						return null;
					}

					JSONObject buildReportJSONObject =
						topLevelBuildReport.getBuildReportJSONObject();

					if (buildReportJSONObject == null) {
						return null;
					}

					try {
						JenkinsResultsParserUtil.write(
							file, buildReportJSONObject.toString());

						JenkinsResultsParserUtil.gzip(file, gzipFile);
					}
					catch (IOException ioException) {
						throw new RuntimeException(ioException);
					}
					finally {
						JenkinsResultsParserUtil.delete(file);
					}

					if (gzipFile.exists()) {
						return gzipFile;
					}

					return null;
				}

			});
	}

	@Override
	protected TestrayAttachment getTopLevelJenkinsConsoleTestrayAttachment() {
		String key = getTopLevelJenkinsConsoleKey();
		String name = getTopLevelJenkinsConsoleName();

		TestrayAttachment testrayAttachment = getTestrayAttachment(
			getTopLevelBuild(), name, key);

		if (testrayAttachment != null) {
			return testrayAttachment;
		}

		final TopLevelBuild topLevelBuild = getTopLevelBuild();

		return uploadTestrayAttachment(
			name, key,
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File file = new File(
						getTestrayUploadBaseDir(), "jenkins-console.txt");
					File gzipFile = new File(
						getTestrayUploadBaseDir(), "jenkins-console.txt.gz");

					try {
						JenkinsResultsParserUtil.write(
							file, topLevelBuild.getConsoleText());

						JenkinsResultsParserUtil.gzip(file, gzipFile);
					}
					catch (IOException ioException) {
						throw new RuntimeException(ioException);
					}
					finally {
						JenkinsResultsParserUtil.delete(file);
					}

					if (gzipFile.exists()) {
						return gzipFile;
					}

					return null;
				}

			});
	}

	@Override
	protected TestrayAttachment getTopLevelJenkinsReportTestrayAttachment() {
		String key = getTopLevelJenkinsReportKey();
		String name = getTopLevelJenkinsReportName();

		TestrayAttachment testrayAttachment = getTestrayAttachment(
			getTopLevelBuild(), name, key);

		if (testrayAttachment != null) {
			return testrayAttachment;
		}

		final TopLevelBuild topLevelBuild = getTopLevelBuild();

		return uploadTestrayAttachment(
			name, key,
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File file = new File(
						getTestrayUploadBaseDir(), "jenkins-report.html");
					File gzipFile = new File(
						getTestrayUploadBaseDir(), "jenkins-report.html.gz");

					Element jenkinsReportElement =
						topLevelBuild.getJenkinsReportElement();

					if (jenkinsReportElement == null) {
						return null;
					}

					try {
						JenkinsResultsParserUtil.write(
							file, Dom4JUtil.format(jenkinsReportElement));

						JenkinsResultsParserUtil.gzip(file, gzipFile);
					}
					catch (IOException ioException) {
						throw new RuntimeException(ioException);
					}
					finally {
						JenkinsResultsParserUtil.delete(file);
					}

					if (gzipFile.exists()) {
						return gzipFile;
					}

					return null;
				}

			});
	}

	@Override
	protected TestrayAttachment getTopLevelJobSummaryTestrayAttachment() {
		String key = getTopLevelJobSummaryKey();
		String name = getTopLevelJobSummaryName();

		TestrayAttachment testrayAttachment = getTestrayAttachment(
			getTopLevelBuild(), name, key);

		if (testrayAttachment != null) {
			return testrayAttachment;
		}

		final TopLevelBuild topLevelBuild = getTopLevelBuild();

		return uploadTestrayAttachment(
			name, key,
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File summaryDir = new File(
						getTestrayUploadBaseDir(), "job-summary");

					File file = new File(summaryDir, "index.html");
					File gzipFile = new File(summaryDir, "index.html.gz");

					Job job = topLevelBuild.getJob();

					if (job == null) {
						return null;
					}

					try {
						CIJobSummaryReportUtil.writeJobSummaryReport(
							summaryDir, job);

						JenkinsResultsParserUtil.gzip(file, gzipFile);
					}
					catch (IOException ioException) {
						throw new RuntimeException(ioException);
					}
					finally {
						JenkinsResultsParserUtil.delete(file);
					}

					if (gzipFile.exists()) {
						return gzipFile;
					}

					return null;
				}

			});
	}

}