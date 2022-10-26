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

import com.liferay.jenkins.results.parser.test.clazz.FunctionalTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class PoshiJUnitTestResult extends JUnitTestResult {

	@Override
	public String getDisplayName() {
		String testName = getTestName();

		if (testName.startsWith("test[")) {
			return testName.substring(5, testName.length() - 1);
		}

		return getSimpleClassName() + "." + testName;
	}

	@Override
	public Element getGitHubElement() {
		String testReportURL = getTestReportURL();

		Element downstreamBuildListItemElement = Dom4JUtil.getNewElement(
			"div", null);

		downstreamBuildListItemElement.add(
			Dom4JUtil.getNewAnchorElement(testReportURL, getDisplayName()));

		Dom4JUtil.addToElement(
			downstreamBuildListItemElement, " - ",
			Dom4JUtil.getNewAnchorElement(getPoshiReportURL(), "Poshi Report"),
			" - ",
			Dom4JUtil.getNewAnchorElement(
				getPoshiSummaryURL(), "Poshi Summary"),
			" - ",
			Dom4JUtil.getNewAnchorElement(
				getConsoleOutputURL(), "Console Output"));

		TestHistory testHistory = getTestHistory();

		if (testHistory != null) {
			downstreamBuildListItemElement.addText(" - ");

			downstreamBuildListItemElement.add(
				Dom4JUtil.getNewAnchorElement(
					testHistory.getTestrayCaseResultURL(),
					JenkinsResultsParserUtil.combine(
						"Failed ",
						String.valueOf(testHistory.getFailureCount()),
						" of last ",
						String.valueOf(testHistory.getTestCount()))));
		}

		String errorDetails = getErrorDetails();

		if ((errorDetails != null) && !errorDetails.isEmpty()) {
			Dom4JUtil.addToElement(
				Dom4JUtil.toCodeSnippetElement(errorDetails));
		}

		if (hasLiferayLog()) {
			Dom4JUtil.addToElement(
				downstreamBuildListItemElement, " - ",
				Dom4JUtil.getNewAnchorElement(
					getLiferayLogURL(), "Liferay Log"));
		}

		return downstreamBuildListItemElement;
	}

	@Override
	public TestClass getTestClass() {
		if (_testClass != null) {
			return _testClass;
		}

		Build build = getBuild();

		if (!(build instanceof DownstreamBuild)) {
			return null;
		}

		DownstreamBuild downstreamBuild = (DownstreamBuild)build;

		AxisTestClassGroup axisTestClassGroup =
			downstreamBuild.getAxisTestClassGroup();

		if (axisTestClassGroup == null) {
			return null;
		}

		String poshiTestName = _getPoshiTestName();

		for (TestClass testClass : axisTestClassGroup.getTestClasses()) {
			if (!(testClass instanceof FunctionalTestClass)) {
				continue;
			}

			FunctionalTestClass functionalTestClass =
				(FunctionalTestClass)testClass;

			if (Objects.equals(
					poshiTestName,
					functionalTestClass.getTestClassMethodName())) {

				_testClass = testClass;

				return _testClass;
			}
		}

		return null;
	}

	protected PoshiJUnitTestResult(Build build, JSONObject caseJSONObject) {
		super(build, caseJSONObject);
	}

	protected String getPoshiReportURL() {
		StringBuilder sb = new StringBuilder();

		String name = getDisplayName();

		sb.append(getTestrayLogsURL());
		sb.append("/");
		sb.append(name.replace('#', '_'));
		sb.append("/index.html.gz");

		return sb.toString();
	}

	protected String getPoshiSummaryURL() {
		StringBuilder sb = new StringBuilder();

		String name = getDisplayName();

		sb.append(getTestrayLogsURL());
		sb.append("/");
		sb.append(name.replace('#', '_'));
		sb.append("/summary.html.gz");

		return sb.toString();
	}

	private String _getPoshiTestName() {
		String testName = getTestName();

		Matcher matcher = _pattern.matcher(testName);

		if (!matcher.find()) {
			return testName;
		}

		return matcher.group("poshiTestName");
	}

	private static final Pattern _pattern = Pattern.compile(
		"test\\[(?<poshiTestName>[^\\]]+)\\]");

	private TestClass _testClass;

}