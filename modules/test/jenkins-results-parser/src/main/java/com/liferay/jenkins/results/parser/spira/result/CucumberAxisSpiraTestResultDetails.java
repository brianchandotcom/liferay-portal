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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.CucumberFeatureResult;
import com.liferay.jenkins.results.parser.CucumberScenarioResult;
import com.liferay.jenkins.results.parser.CucumberTestResult;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.spira.BaseSpiraArtifact;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Michael Hashimoto
 */
public class CucumberAxisSpiraTestResultDetails
	extends BaseAxisSpiraTestResultDetails {

	protected CucumberAxisSpiraTestResultDetails(
		CucumberAxisSpiraTestResult cucumberAxisSpiraTestResult) {

		super(cucumberAxisSpiraTestResult);

		_cucumberAxisSpiraTestResult = cucumberAxisSpiraTestResult;
	}

	protected List<Callable<Map.Entry<String, String>>> getCallables() {
		List<Callable<Map.Entry<String, String>>> callables =
			super.getCallables();

		callables.add(
			new Callable() {

				@Override
				public Map.Entry<String, String> call() {
					return new AbstractMap.SimpleEntry<>(
						"CucumberSummary", _getCucumberSummary());
				}

			});

		return callables;
	}

	private String _getCucumberSummary() {
		StringBuilder sb = new StringBuilder();

		CucumberTestResult cucumberTestResult =
			_cucumberAxisSpiraTestResult.getCucumberTestResult();

		sb.append("<h4>Cucumber</h4><ul>");
		sb.append("<li>Cucumber Report: <a href=\"");

		CucumberFeatureResult cucumberFeatureResult =
			cucumberTestResult.getCucumberFeatureResult();

		sb.append(cucumberFeatureResult.getURL());

		sb.append("\" target=\"_blank\">");

		sb.append(
			BaseSpiraArtifact.fixStringForJSON(
				cucumberFeatureResult.getName()));

		sb.append("</a></li>");
		sb.append("<li>Cucumber Description: ");
		sb.append(cucumberFeatureResult.getDescription());
		sb.append("</li>");

		List<String> tags = cucumberFeatureResult.getTags();

		if (!tags.isEmpty()) {
			sb.append("<li>Cucumber Tags:");
			sb.append("<ul>");

			for (String tag : tags) {
				sb.append("<li>");
				sb.append(tag);
				sb.append("</li>");
			}

			sb.append("</ul></li>");
		}

		CucumberScenarioResult cucumberScenarioResult =
			cucumberTestResult.getCucumberScenarioResult();

		sb.append("<li>Cucumber Steps - ");

		sb.append(
			JenkinsResultsParserUtil.toDurationString(
				cucumberScenarioResult.getDuration()));

		sb.append(" - ");
		sb.append(cucumberScenarioResult.getStatus());
		sb.append("<ul>");

		List<CucumberScenarioResult.Step> backgroundSteps =
			cucumberScenarioResult.getBackgroundSteps();

		if (!backgroundSteps.isEmpty()) {
			sb.append("<li><details><summary>");

			sb.append(
				BaseSpiraArtifact.fixStringForJSON(
					cucumberScenarioResult.getBackgroundName()));

			sb.append(" - ");

			sb.append(
				JenkinsResultsParserUtil.toDurationString(
					cucumberScenarioResult.getBackgroundDuration()));

			sb.append(" - ");
			sb.append(cucumberScenarioResult.getBackgroundStatus());
			sb.append("</summary><ul>");

			for (CucumberScenarioResult.Step backgroundStep : backgroundSteps) {
				sb.append("<li>");

				sb.append(
					BaseSpiraArtifact.fixStringForJSON(
						backgroundStep.getName()));

				sb.append(" - ");

				sb.append(
					JenkinsResultsParserUtil.toDurationString(
						backgroundStep.getDuration()));

				sb.append(" - ");
				sb.append(backgroundStep.getStatus());
				sb.append("</li>");
			}

			sb.append("</ul></details></li>");
		}

		sb.append("<li><details><summary>");

		sb.append(
			BaseSpiraArtifact.fixStringForJSON(
				cucumberScenarioResult.getScenarioName()));

		sb.append(" - ");

		sb.append(
			JenkinsResultsParserUtil.toDurationString(
				cucumberScenarioResult.getScenarioDuration()));

		sb.append(" - ");
		sb.append(cucumberScenarioResult.getScenarioStatus());
		sb.append("</summary><ul>");

		for (CucumberScenarioResult.Step scenarioStep :
				cucumberScenarioResult.getScenarioSteps()) {

			sb.append("<li>");
			sb.append(
				BaseSpiraArtifact.fixStringForJSON(scenarioStep.getName()));

			sb.append(" - ");

			sb.append(
				JenkinsResultsParserUtil.toDurationString(
					scenarioStep.getDuration()));

			sb.append(" - ");
			sb.append(scenarioStep.getStatus());
			sb.append("</li>");
		}

		sb.append("</ul></details></li>");
		sb.append("</ul>");
		sb.append("</ul>");

		return sb.toString();
	}

	private final CucumberAxisSpiraTestResult _cucumberAxisSpiraTestResult;

}