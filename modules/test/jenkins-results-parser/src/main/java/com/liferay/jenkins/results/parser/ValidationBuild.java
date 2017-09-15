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

import java.util.Collections;
import java.util.List;

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Leslie Wong
 */
public class ValidationBuild extends BaseBuild {

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
			for (int i = 1; i < consoleSnippets.length; i++) {
				String consoleSnippet = consoleSnippets[i];

				if (consoleSnippet.contains("merge-test-results:")) {
					continue;
				}
			}
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

		return TestResult.getTestResults(
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
	protected Element getGitHubMessageJobResultsElement() {
		return null;
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

	private static final FailureMessageGenerator[] _failureMessageGenerators = {
		new RebaseFailureMessageGenerator(),
		new SubrepositorySourceFormatFailureMessageGenerator(),

		new GenericFailureMessageGenerator()
	};

}