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

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.lang.StringEscapeUtils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class DownstreamBuild extends BaseBuild {

	@Override
	public void addTimelineData(BaseBuild.TimelineData timelineData) {
		timelineData.addTimelineData(this);
	}

	@Override
	public URL getArtifactsBaseURL() {
		return null;
	}

	public String getAxisVariable() {
		return getParameterValue("AXIS_VARIABLE");
	}

	@Override
	public String getBaseGitRepositoryName() {
		if (!JenkinsResultsParserUtil.isNullOrEmpty(gitRepositoryName)) {
			return gitRepositoryName;
		}

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		gitRepositoryName = topLevelBuild.getParameterValue("REPOSITORY_NAME");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(gitRepositoryName)) {
			return gitRepositoryName;
		}

		String branchName = getBranchName();

		gitRepositoryName = "liferay-portal-ee";

		if (branchName.equals("master")) {
			gitRepositoryName = "liferay-portal";
		}

		return gitRepositoryName;
	}

	public String getBatchName() {
		String jobVariant = getJobVariant();

		return jobVariant.replaceAll("([^/]+)/.*", "$1");
	}

	@Override
	public String getDisplayName() {
		StringBuilder sb = new StringBuilder();

		sb.append(getParameterValue("JOB_VARIANT"));

		String axisVariable = getParameterValue("AXIS_VARIABLE");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(axisVariable)) {
			sb.append("/");
			sb.append(axisVariable);
		}

		return sb.toString();
	}

	@Override
	public List<TestResult> getTestResults(String testStatus) {
		if (JenkinsResultsParserUtil.isNullOrEmpty(testStatus)) {
			return getTestResults();
		}

		List<TestResult> testResults = new ArrayList<>();

		for (TestResult testResult : getTestResults()) {
			if (testStatus.equals(testResult.getStatus())) {
				testResults.add(testResult);
			}
		}

		return testResults;
	}

	@Override
	public List<TestResult> getUpstreamJobFailureTestResults() {
		List<TestResult> upstreamFailureTestResults = new ArrayList<>();

		for (TestResult testResult : getTestResults(null)) {
			if (!testResult.isFailing()) {
				continue;
			}

			if (!testResult.isUniqueFailure()) {
				upstreamFailureTestResults.add(testResult);
			}
		}

		return upstreamFailureTestResults;
	}

	public List<String> getWarningMessages() {
		List<String> warningMessages = new ArrayList<>();

		URL poshiWarningsURL = null;

		try {
			poshiWarningsURL = new URL(
				getArtifactsBaseURL() + "/poshi-warnings.xml.gz");
		}
		catch (IOException ioException) {
			return warningMessages;
		}

		StringBuilder sb = new StringBuilder();

		try (InputStream inputStream = poshiWarningsURL.openStream();
			GZIPInputStream gzipInputStream = new GZIPInputStream(
				inputStream)) {

			int i = 0;

			while ((i = gzipInputStream.read()) > 0) {
				sb.append((char)i);
			}
		}
		catch (IOException ioException) {
			return warningMessages;
		}

		try {
			Document document = Dom4JUtil.parse(sb.toString());

			Element rootElement = document.getRootElement();

			for (Element valueElement : rootElement.elements("value")) {
				String liferayErrorText = "LIFERAY_ERROR: ";

				String valueElementText = StringEscapeUtils.escapeHtml(
					valueElement.getText());

				if (valueElementText.startsWith(liferayErrorText)) {
					valueElementText = valueElementText.substring(
						liferayErrorText.length());
				}

				warningMessages.add(valueElementText);
			}
		}
		catch (DocumentException documentException) {
			warningMessages.add("Unable to parse Poshi warnings");
		}

		return warningMessages;
	}

	protected DownstreamBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	protected Element getGitHubMessageJobResultsElement() {
		return null;
	}

}