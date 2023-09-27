/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.BuildReportFactory;
import com.liferay.jenkins.results.parser.CIJobSummaryReportUtil;
import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.TopLevelBuildReport;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringEscapeUtils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TopLevelBuildTestrayCaseResult extends BuildTestrayCaseResult {

	public TopLevelBuildTestrayCaseResult(
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

	public void recordTestrayCaseResult(Job job) {
		TestrayBuild testrayBuild = getTestrayBuild();

		TestrayRun testrayRun = new TestrayRun(
			testrayBuild, "top-level-build", job.getJobPropertiesFiles());

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		Document document = DocumentHelper.createDocument();

		Element rootElement = document.addElement("testsuite");

		Element environmentsElement = rootElement.addElement("environments");

		for (TestrayRun.Factor factor : testrayRun.getFactors()) {
			Element environmentElement = environmentsElement.addElement(
				"environment");

			environmentElement.addAttribute("type", factor.getName());
			environmentElement.addAttribute("option", factor.getValue());
		}

		Map<String, String> propertiesMap = new HashMap<>();

		propertiesMap.put("testray.build.name", testrayBuild.getName());

		TestrayRoutine testrayRoutine = testrayBuild.getTestrayRoutine();

		propertiesMap.put("testray.build.type", testrayRoutine.getName());

		TestrayProductVersion testrayProductVersion =
			testrayBuild.getTestrayProductVersion();

		if (testrayProductVersion != null) {
			propertiesMap.put(
				"testray.product.version", testrayProductVersion.getName());
		}

		TestrayProject testrayProject = testrayBuild.getTestrayProject();

		propertiesMap.put("testray.project.name", testrayProject.getName());

		propertiesMap.put("testray.run.id", testrayRun.getRunIDString());

		_addPropertyElements(
			rootElement.addElement("properties"), propertiesMap);

		List<TestrayCaseResult> testrayCaseResults = new ArrayList<>();

		testrayCaseResults.add(this);

		for (TestrayCaseResult testrayCaseResult : testrayCaseResults) {
			Element testcaseElement = rootElement.addElement("testcase");

			Map<String, String> testcasePropertiesMap = new HashMap<>();

			testcasePropertiesMap.put(
				"testray.case.type.name", testrayCaseResult.getType());
			testcasePropertiesMap.put(
				"testray.component.names",
				testrayCaseResult.getSubcomponentNames());
			testcasePropertiesMap.put(
				"testray.main.component.name",
				testrayCaseResult.getComponentName());
			testcasePropertiesMap.put(
				"testray.team.name", testrayCaseResult.getTeamName());

			String testrayCaseName = testrayCaseResult.getName();

			if (testrayCaseName.length() > 150) {
				testrayCaseName = testrayCaseName.substring(0, 150);
			}

			testcasePropertiesMap.put("testray.testcase.name", testrayCaseName);

			testcasePropertiesMap.put(
				"testray.testcase.priority",
				String.valueOf(testrayCaseResult.getPriority()));

			TestrayCaseResult.Status testrayCaseStatus =
				testrayCaseResult.getStatus();

			testcasePropertiesMap.put(
				"testray.testcase.status", testrayCaseStatus.getName());

			Element propertiesElement = testcaseElement.addElement(
				"properties");

			_addPropertyElements(propertiesElement, testcasePropertiesMap);

			String[] warnings = testrayCaseResult.getWarnings();

			if ((warnings != null) && (warnings.length > 0)) {
				Element warningsPropertyElement = propertiesElement.addElement(
					"property");

				warningsPropertyElement.addAttribute(
					"name", "testray.testcase.warnings");
				warningsPropertyElement.addAttribute(
					"value", String.valueOf(warnings.length));

				for (String warning : warnings) {
					Element warningPropertyElement =
						warningsPropertyElement.addElement("value");

					warningPropertyElement.addText(
						StringEscapeUtils.escapeHtml(warning));
				}
			}

			Element attachmentsElement = testcaseElement.addElement(
				"attachments");

			for (TestrayAttachment testrayAttachment :
					testrayCaseResult.getTestrayAttachments()) {

				Element attachmentFileElement = attachmentsElement.addElement(
					"file");

				attachmentFileElement.addAttribute(
					"name", testrayAttachment.getName());
				attachmentFileElement.addAttribute(
					"url", testrayAttachment.getURL() + "?authuser=0");
				attachmentFileElement.addAttribute(
					"value", testrayAttachment.getKey() + "?authuser=0");
			}

			String errors = testrayCaseResult.getErrors();

			if (!JenkinsResultsParserUtil.isNullOrEmpty(errors)) {
				Element failureElement = testcaseElement.addElement("failure");

				failureElement.addAttribute("message", errors);
			}
		}

		TestrayServer testrayServer = testrayBuild.getTestrayServer();

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		JenkinsMaster jenkinsMaster = topLevelBuild.getJenkinsMaster();

		try {
			testrayServer.writeCaseResult(
				JenkinsResultsParserUtil.combine(
					"TESTS-", jenkinsMaster.getName(), "_",
					topLevelBuild.getJobName(), "_",
					String.valueOf(topLevelBuild.getBuildNumber()),
					"_top-level-build.xml"),
				Dom4JUtil.format(rootElement));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		long end = JenkinsResultsParserUtil.getCurrentTimeMillis();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Recorded ", String.valueOf(testrayCaseResults.size()),
				" case results for top-level-build in ",
				JenkinsResultsParserUtil.toDurationString(end - start)));
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

	private void _addPropertyElements(
		Element propertiesElement, Map<String, String> propertiesMap) {

		for (Map.Entry<String, String> propertyEntry :
				propertiesMap.entrySet()) {

			Element propertyElement = propertiesElement.addElement("property");

			String propertyName = propertyEntry.getKey();
			String propertyValue = propertyEntry.getValue();

			if (JenkinsResultsParserUtil.isNullOrEmpty(propertyName) ||
				JenkinsResultsParserUtil.isNullOrEmpty(propertyValue)) {

				continue;
			}

			propertyElement.addAttribute("name", propertyName);
			propertyElement.addAttribute("value", propertyValue);
		}
	}

}