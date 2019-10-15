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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 */
public class PortalPullRequestTesterTopLevelBuild extends DefaultTopLevelBuild {

	public PortalPullRequestTesterTopLevelBuild(
		String url, TopLevelBuild topLevelBuild) {

		super(url, topLevelBuild);

		try {
			String testSuiteName = getTestSuiteName();

			if (testSuiteName.equals("relevant")) {
				_stableJob = JobFactory.newJob(jobName, "stable", branchName);
			}
		}
		catch (Exception e) {
			System.out.println("Unable to create stable job for " + jobName);

			e.printStackTrace();
		}
	}

	public String getStableResult() {
		if (_stableJob == null) {
			return null;
		}

		if (_stableResult != null) {
			return _stableResult;
		}

		List<Build> stableDownstreamBuilds = getStableDownstreamBuilds();

		int stableDownstreamBuildsSize = stableDownstreamBuilds.size();

		if (stableDownstreamBuildsSize == 0) {
			return null;
		}

		List<String> stableBatchNames = new ArrayList<>(
			_stableJob.getBatchNames());

		int completedCount = getJobVariantsDownstreamBuildCount(
			stableBatchNames, null, "completed");

		if (completedCount != stableDownstreamBuildsSize) {
			return null;
		}

		int successCount = getJobVariantsDownstreamBuildCount(
			stableBatchNames, "SUCCESS", null);

		if (successCount == stableDownstreamBuildsSize) {
			_stableResult = "SUCCESS";
		}
		else {
			_stableResult = "FAILURE";
		}

		return _stableResult;
	}

	protected Element getFailedStableJobSummaryElement() {
		List<String> stableBatchNames = new ArrayList<>(
			_stableJob.getBatchNames());

		Element jobSummaryListElement = getJobSummaryListElement(
			false, stableBatchNames);

		int successCount = getJobVariantsDownstreamBuildCount(
			stableBatchNames, "SUCCESS", null);

		int failCount =
			getJobVariantsDownstreamBuildCount(stableBatchNames, null, null) -
				successCount;

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"h4", null, String.valueOf(failCount), " Failed Jobs:"),
			jobSummaryListElement);
	}

	protected List<Build> getStableDownstreamBuilds() {
		if (_stableJob != null) {
			return getJobVariantsDownstreamBuilds(
				_stableJob.getBatchNames(), null, null);
		}

		return Collections.emptyList();
	}

	protected Element getStableJobSummaryElement() {
		List<String> stableBatchNames = new ArrayList<>(
			_stableJob.getBatchNames());

		int successCount = getJobVariantsDownstreamBuildCount(
			stableBatchNames, "SUCCESS", null);

		List<Build> stableDownstreamBuilds = getStableDownstreamBuilds();

		Element detailsElement = Dom4JUtil.getNewElement(
			"details", null,
			Dom4JUtil.getNewElement(
				"summary", null,
				Dom4JUtil.getNewElement(
					"strong", null, "ci:test:stable - ",
					String.valueOf(successCount), " out of ",
					String.valueOf(stableDownstreamBuilds.size()),
					" jobs PASSED")));

		int stableBuildCount = getJobVariantsDownstreamBuildCount(
			stableBatchNames, null, null);

		if (successCount < stableBuildCount) {
			Dom4JUtil.addToElement(
				detailsElement, getFailedStableJobSummaryElement());
		}

		if (successCount > 0) {
			Dom4JUtil.addToElement(
				detailsElement, getSuccessfulStableJobSummaryElement());
		}

		return detailsElement;
	}

	protected Element getStableResultElement() {
		if (_stableJob == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		List<String> stableBatchNames = new ArrayList<>(
			_stableJob.getBatchNames());

		int successCount = getJobVariantsDownstreamBuildCount(
			stableBatchNames, "SUCCESS", null);

		List<Build> stableDownstreamBuilds = getStableDownstreamBuilds();

		int stableDownstreamBuildsSize = stableDownstreamBuilds.size();

		if (successCount == stableDownstreamBuildsSize) {
			sb.append(":heavy_check_mark: ");
		}
		else {
			sb.append(":x: ");
		}

		sb.append("ci:test:stable - ");
		sb.append(String.valueOf(successCount));
		sb.append(" out of ");
		sb.append(String.valueOf(stableDownstreamBuildsSize));
		sb.append(" jobs passed");

		return Dom4JUtil.getNewElement("h3", null, sb.toString());
	}

	protected Element getSuccessfulStableJobSummaryElement() {
		List<String> stableBatchNames = new ArrayList<>(
			_stableJob.getBatchNames());

		Element jobSummaryListElement = getJobSummaryListElement(
			true, stableBatchNames);

		int successCount = getJobVariantsDownstreamBuildCount(
			stableBatchNames, "SUCCESS", null);

		return Dom4JUtil.getNewElement(
			"details", null,
			Dom4JUtil.getNewElement(
				"summary", null,
				Dom4JUtil.getNewElement(
					"strong", null, String.valueOf(successCount),
					" Successful Jobs:")),
			jobSummaryListElement);
	}

	@Override
	protected Element getTopGitHubMessageElement() {
		Element rootElement = super.getTopGitHubMessageElement();

		List<Build> stableDownstreamBuilds = new ArrayList<>();

		if (_stableJob != null) {
			stableDownstreamBuilds.addAll(getStableDownstreamBuilds());
		}

		if (!stableDownstreamBuilds.isEmpty()) {
			Dom4JUtil.insertElementAfter(
				rootElement, null, getStableResultElement());
		}

		Element detailsElement = rootElement.element("details");

		if (!stableDownstreamBuilds.isEmpty()) {
			Element jobSummaryElement = detailsElement.element("details");

			Dom4JUtil.insertElementBefore(
				detailsElement, jobSummaryElement,
				getStableJobSummaryElement());
		}

		return rootElement;
	}

	private Job _stableJob;
	private String _stableResult;

}