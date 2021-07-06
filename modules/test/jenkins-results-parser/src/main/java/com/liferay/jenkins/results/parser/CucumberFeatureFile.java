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
import java.io.Serializable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author Michael Hashimoto
 */
public class CucumberFeatureFile implements Serializable {

	public CucumberFeatureFile(
		File faroDir, CucumberFeatureResult cucumberFeatureResult,
		CucumberScenarioResult cucumberScenarioResult) {

		_faroDir = faroDir;
		_cucumberFeatureResult = cucumberFeatureResult;
		_cucumberScenarioResult = cucumberScenarioResult;
	}

	public String getCategoryName() {
		Matcher matcher = _relativePathPattern.matcher(getRelativePath());

		if (!matcher.matches()) {
			return "";
		}

		String folderNames = matcher.group(1);

		folderNames = folderNames.replaceAll("\\.", " ");

		folderNames = StringUtils.capitalize(folderNames);

		folderNames = folderNames.replaceAll("\\s*(.+?)[/\\s]*", "$1");

		return folderNames;
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

		throw new RuntimeException("Unable to find a matching file");
	}

	private Set<String> _getFeaturePaths(String name) {
		Set<String> featurePaths = _featurePathsMap.get(name);

		if (featurePaths != null) {
			return featurePaths;
		}

		featurePaths = new HashSet<>();

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				false, _faroDir, 10000,
				JenkinsResultsParserUtil.combine(
					"git grep \"", name.replaceAll("\"", "\\\\\""), "$\""));

			try {
				String gitGrepResults =
					JenkinsResultsParserUtil.readInputStream(
						process.getInputStream());

				for (String gitGrepResult : gitGrepResults.split("\n")) {
					Matcher matcher = _gitGrepResultsPattern.matcher(
						gitGrepResult);

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

		_featurePathsMap.put(name, featurePaths);

		return _featurePathsMap.get(name);
	}

	private Set<String> _getFeaturePathsFromFeatureName() {
		String featureName = _cucumberFeatureResult.getName();

		return _getFeaturePaths(featureName.trim());
	}

	private Set<String> _getFeaturePathsFromScenarioName() {
		String scenarioName = _cucumberScenarioResult.getScenarioName();

		return _getFeaturePaths(scenarioName.trim());
	}

	private static final Pattern _gitGrepResultsPattern = Pattern.compile(
		"(.+\\.feature)\\:.*");
	private static final Pattern _relativePathPattern = Pattern.compile(
		".*/features/(.*)/[^/]+");

	private final CucumberFeatureResult _cucumberFeatureResult;
	private final CucumberScenarioResult _cucumberScenarioResult;
	private final File _faroDir;
	private final Map<String, Set<String>> _featurePathsMap = new HashMap<>();

}