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
import com.liferay.jenkins.results.parser.failure.message.generator.SourceFormatFailureMessageGenerator;

import org.dom4j.Element;

/**
 * @author Cesar Polanco
 */
public class SourceFormatBuild extends TopLevelBuild {

	@Override
	public String getBaseRepositoryName() {
		return _pullRequest.getRepositoryName();
	}

	@Override
	public String getBaseRepositorySHA(String repositoryName) {
		return _pullRequest.getUpstreamBranchSHA();
	}

	@Override
	public String getBranchName() {
		return _pullRequest.getUpstreamBranchName();
	}

	@Override
	public Element[] getBuildFailureElements() {
		return new Element[] {getFailureMessageElement()};
	}

	@Override
	public Element getGitHubMessageElement() {
		update();

		Element rootElement = Dom4JUtil.getNewElement(
			"html", null, getResultElement());

		Element detailsElement = Dom4JUtil.getNewElement(
			"details", rootElement,
			Dom4JUtil.getNewElement(
				"summary", null, "Click here for more details."),
			Dom4JUtil.getNewElement("h4", null, "Base Branch:"),
			getBaseBranchDetailsElement(),
			Dom4JUtil.getNewElement("h4", null, "Sender Branch:"),
			getSenderBranchDetailsElement());

		String result = getResult();

		int successCount = 0;

		if ((result != null) && result.equals("SUCCESS")) {
			successCount++;
		}

		Dom4JUtil.addToElement(
			detailsElement, String.valueOf(successCount),
			" out of 1 jobs PASSED");

		if (!result.equals("SUCCESS")) {
			Dom4JUtil.addToElement(
				detailsElement, getFailedJobSummaryElement());
		}
		else if (result.equals("SUCCESS")) {
			Dom4JUtil.addToElement(
				detailsElement, getSuccessfulJobSummaryElement());
		}

		Dom4JUtil.addToElement(detailsElement, getMoreDetailsElement());

		if (!result.equals("SUCCESS")) {
			Dom4JUtil.addToElement(
				detailsElement, (Object[])getBuildFailureElements());
		}

		return rootElement;
	}

	public PullRequest getPullRequest() {
		return _pullRequest;
	}

	protected SourceFormatBuild(String url) {
		this(url, null);
	}

	protected SourceFormatBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);

		_pullRequest = new PullRequest(getParameterValue("PULL_REQUEST_URL"));
	}

	@Override
	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		return new FailureMessageGenerator[] {
			new RebaseFailureMessageGenerator(),
			new SourceFormatFailureMessageGenerator(),

			new GenericFailureMessageGenerator()
		};
	}

	protected Element getSenderBranchDetailsElement() {
		String repositoryName = _pullRequest.getRepositoryName();
		String senderBranchName = _pullRequest.getSenderBranchName();
		String senderUsername = _pullRequest.getSenderUsername();

		String senderBranchURL =
			"https://github.com/" + senderUsername + "/" + repositoryName +
				"/tree/" + senderBranchName;

		String senderSHA = _pullRequest.getSenderSHA();

		String senderCommitURL =
			"https://github.com/" + senderUsername + "/" + repositoryName +
				"/commit/" + senderSHA;

		Element senderBranchDetailsElement = Dom4JUtil.getNewElement(
			"p", null, "Branch Name: ",
			Dom4JUtil.getNewAnchorElement(senderBranchURL, senderBranchName));

		Dom4JUtil.addToElement(
			senderBranchDetailsElement, Dom4JUtil.getNewElement("br"),
			"Branch GIT ID: ",
			Dom4JUtil.getNewAnchorElement(senderCommitURL, senderSHA));

		return senderBranchDetailsElement;
	}

	@Override
	protected String getTestSuiteReportString() {
		return "ci:test:sf";
	}

	private PullRequest _pullRequest;

}