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

import com.liferay.jenkins.results.parser.failure.message.generator.FailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.GenericFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.RebaseFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.SubrepositorySourceFormatFailureMessageGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Leslie Wong
 */
public class ValidationBuild extends BaseBuild {

	@Override
	public void addTimelineData(BaseBuild.TimelineData timelineData) {
		timelineData.addTimelineData(this);
	}

	@Override
	public Element getGitHubMessageElement() {
		update();

		Element rootElement = Dom4JUtil.getNewElement(
			"html", null, getResultMessageElement(), getBuildTimeElement(),
			Dom4JUtil.getNewElement("h4", null, "Base Branch:"),
			getBaseBranchDetailsElement());

		String consoleText = getConsoleText();

		String[] consoleSnippets = consoleText.split(
			"Executing subrepository task ");

		if (consoleSnippets.length > 1) {
			Dom4JUtil.addToElement(
				rootElement,
				Dom4JUtil.getNewElement("h6", null, "Task Summary:"));

			Element taskSummaryListElement = Dom4JUtil.getNewElement(
				"ul", rootElement);

			for (int i = 1; i < consoleSnippets.length; i++) {
				String consoleSnippet = consoleSnippets[i];

				if (consoleSnippet.contains("merge-test-results:")) {
					continue;
				}

				Dom4JUtil.addToElement(
					taskSummaryListElement,
					getTaskSummaryIndexElement(consoleSnippet));
			}

			Dom4JUtil.addToElement(
				rootElement,
				Dom4JUtil.getNewElement(
					"h5", null, "For full console, click ",
					Dom4JUtil.getNewAnchorElement(
						getBuildURL() + "/consoleText", "here"),
					"."));

			Dom4JUtil.addToElement(
				rootElement, Dom4JUtil.getNewElement("hr"),
				getTestSummaryElement());
		}
		else {
			Dom4JUtil.addToElement(rootElement, getFailureMessageElement());
		}

		return rootElement;
	}

	@Override
	public List<TestResult> getTestResults(String testStatus) {
		String status = getStatus();

		if (!status.equals("completed")) {
			return Collections.emptyList();
		}

		JSONObject testReportJSONObject = getTestReportJSONObject();

		return getTestResults(
			this, testReportJSONObject.getJSONArray("suites"), testStatus);
	}

	protected ValidationBuild(String url) {
		this(url, null);
	}

	protected ValidationBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	protected Element getBaseBranchDetailsElement() {
		String baseBranchURL =
			"https://github.com/liferay/" + getBaseRepositoryName() + "/tree/" +
				getBranchName();

		String baseRepositoryName = getBaseRepositoryName();

		String baseRepositorySHA = null;

		if (!baseRepositoryName.equals("liferay-jenkins-ee") &&
			baseRepositoryName.endsWith("-ee")) {

			baseRepositorySHA = getBaseRepositorySHA(
				baseRepositoryName.substring(
					0, baseRepositoryName.length() - 3));
		}
		else {
			baseRepositorySHA = getBaseRepositorySHA(baseRepositoryName);
		}

		String baseRepositoryCommitURL =
			"https://github.com/liferay/" + baseRepositoryName + "/commit/" +
				baseRepositorySHA;

		Element baseBranchDetailsElement = Dom4JUtil.getNewElement(
			"p", null, "Branch Name: ",
			Dom4JUtil.getNewAnchorElement(baseBranchURL, getBranchName()));

		if (baseRepositorySHA != null) {
			Dom4JUtil.addToElement(
				baseBranchDetailsElement, Dom4JUtil.getNewElement("br"),
				"Branch GIT ID: ",
				Dom4JUtil.getNewAnchorElement(
					baseRepositoryCommitURL, baseRepositorySHA));
		}

		return baseBranchDetailsElement;
	}

	@Override
	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		return _FAILURE_MESSAGE_GENERATORS;
	}

	@Override
	protected Element getGitHubMessageJobResultsElement() {
		return null;
	}

	protected Element getGitHubMessageTestResultsElement() {
		int failCount = getTestCountByStatus("FAILURE");
		int successCount = getTestCountByStatus("SUCCESS");

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, Integer.toString(successCount),
				JenkinsResultsParserUtil.getNounForm(
					successCount, " Tests", " Test"),
				" Passed.", Dom4JUtil.getNewElement("br"),
				Integer.toString(failCount),
				JenkinsResultsParserUtil.getNounForm(
					failCount, " Tests", " Test"),
				" Failed."));
	}

	protected Element getResultMessageElement() {
		Element resultMessageElement = Dom4JUtil.getNewElement("h1");

		String result = getResult();

		if (!result.equals("SUCCESS")) {
			resultMessageElement.addText("Validation FAILED.");
		}
		else {
			resultMessageElement.addText(
				"Validation PASSED. Running batch tests.");
		}

		return resultMessageElement;
	}

	protected String getTaskResultIcon(String result) {
		if (result.equals("FAILED")) {
			return " :x:";
		}

		if (result.equals("SUCCESSFUL")) {
			return " :white_check_mark:";
		}

		return "";
	}

	protected Element getTaskSummaryIndexElement(String console) {
		String taskName = console.substring(0, console.indexOf("\n"));

		Matcher matcher = _consoleResultPattern.matcher(console);

		String taskResult = "FAILED";

		if (matcher.find()) {
			taskResult = matcher.group(1);
		}

		Element taskSummaryIndexElement = Dom4JUtil.getNewElement("li", null);

		Dom4JUtil.addToElement(
			taskSummaryIndexElement, taskName, " - ",
			getTaskResultIcon(taskResult));

		if (taskResult.equals("FAILED")) {
			GenericFailureMessageGenerator genericFailureMessageGenerator =
				new GenericFailureMessageGenerator();

			Dom4JUtil.addToElement(
				taskSummaryIndexElement,
				genericFailureMessageGenerator.getMessageElement(console));
		}

		return taskSummaryIndexElement;
	}

	protected Element getTestSummaryElement() {
		Element testSummaryElement = Dom4JUtil.getNewElement(
			"div", null, Dom4JUtil.getNewElement("h6", null, "Test Results:"));

		List<TestResult> testResults = getTestResults(null);

		boolean noTestResults = testResults.isEmpty();

		if (testResults.size() == 1) {
			TestResult testResult = testResults.get(0);

			String className = testResult.getClassName();

			noTestResults = className.equals("com.liferay.jenkins.Jenkins");
		}

		if (noTestResults) {
			Dom4JUtil.addToElement(
				testSummaryElement,
				Dom4JUtil.getNewElement("h5", null, "No tests were run."));
		}
		else {
			Dom4JUtil.addToElement(
				testSummaryElement,
				Dom4JUtil.getNewElement(
					"div", null, getGitHubMessageTestResultsElement()));

			List<Element> failureElements = new ArrayList<>();

			for (TestResult testResult : getTestResults(null)) {
				String testStatus = testResult.getStatus();

				if (testStatus.equals("FIXED") || testStatus.equals("PASSED") ||
					testStatus.equals("SKIPPED")) {

					continue;
				}

				failureElements.add(testResult.getGitHubElement());
			}

			if (!failureElements.isEmpty()) {
				Dom4JUtil.getOrderedListElement(
					failureElements, testSummaryElement, 5);
			}

			Element testReportElement = Dom4JUtil.getNewElement(
				"h5", null, "For all test results, click ",
				Dom4JUtil.getNewAnchorElement(
					getBuildURL() + "/testReport", "here"),
				".");

			Dom4JUtil.addToElement(testSummaryElement, testReportElement);
		}

		return testSummaryElement;
	}

	private static final FailureMessageGenerator[] _FAILURE_MESSAGE_GENERATORS =
		{
			new RebaseFailureMessageGenerator(),
			new SubrepositorySourceFormatFailureMessageGenerator(),

			new GenericFailureMessageGenerator()
		};

	private static final Pattern _consoleResultPattern = Pattern.compile(
		"Subrepository task (FAILED|SUCCESSFUL)");

}