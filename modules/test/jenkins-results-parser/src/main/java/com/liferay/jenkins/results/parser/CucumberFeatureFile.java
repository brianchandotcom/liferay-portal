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

import java.io.File;
import java.io.IOException;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class CucumberFeatureFile {

	public CucumberFeatureFile(
		File faroDir, CucumberFeatureResult cucumberFeatureResult,
		CucumberScenarioResult cucumberScenarioResult) {

		_faroDir = faroDir;
		_cucumberFeatureResult = cucumberFeatureResult;
		_cucumberScenarioResult = cucumberScenarioResult;
	}

	public File getFile() {
		return new File(_faroDir, getRelativePath());
	}

	public String getRelativePath() {
		for (String featurePath : _getFeaturePathsFromFeatureName()) {
			Set<String> featurePaths = _getFeaturePathsFromScenarioName();

			if (featurePaths.contains(featurePath)) {
				return featurePath;
			}
		}

		throw new RuntimeException("Could not find a matching file");
	}

	private Set<String> _getFeaturePaths(String name) {
		Set<String> featurePaths = new HashSet<>();

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				false, _faroDir, 10000,
				JenkinsResultsParserUtil.combine(
					"git grep \"", name.replaceAll("\"", "\\\\\""), "\""));

			try {
				String gitGrepResults =
					JenkinsResultsParserUtil.readInputStream(
						process.getInputStream());

				for (String gitGrepResult : gitGrepResults.split("\n")) {
					Matcher matcher = _pattern.matcher(gitGrepResult);

					if (!matcher.find()) {
						continue;
					}

					featurePaths.add(matcher.group(1));
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(
					"Unable to read process input stream", ioException);
			}
		}
		catch (IOException | TimeoutException exception) {
			throw new RuntimeException(exception);
		}

		return featurePaths;
	}

	private Set<String> _getFeaturePathsFromFeatureName() {
		return _getFeaturePaths(_cucumberFeatureResult.getName());
	}

	private Set<String> _getFeaturePathsFromScenarioName() {
		return _getFeaturePaths(_cucumberScenarioResult.getScenarioName());
	}

	private static final Pattern _pattern = Pattern.compile(
		"([^\\.]+\\.feature)\\:.*");

	private final CucumberFeatureResult _cucumberFeatureResult;
	private final CucumberScenarioResult _cucumberScenarioResult;
	private final File _faroDir;

}