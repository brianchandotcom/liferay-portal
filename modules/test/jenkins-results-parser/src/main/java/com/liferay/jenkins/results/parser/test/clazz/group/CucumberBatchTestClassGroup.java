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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author Michael Hashimoto
 */
public class CucumberBatchTestClassGroup extends BatchTestClassGroup {

	public File getFaroDir() {
		File faroDir = new File(
			JenkinsResultsParserUtil.getProperty(
				getJobProperties(), "analytics.cloud.faro.dir"));

		try {
			return new File(faroDir.getCanonicalPath());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static class CucumberTestClass extends BaseTestClass {

		public List<String> getCategoryNames() {
			String folderNames = JenkinsResultsParserUtil.getPathRelativeTo(
				getTestClassFile(),
				_cucumberBatchTestClassGroup._getFeatureBaseDir());

			folderNames = folderNames.replaceAll("(.*)/[^/]+.feature", "$1");

			folderNames = folderNames.replaceAll("\\.", " ");

			folderNames = StringUtils.capitalize(folderNames);

			folderNames = folderNames.replaceAll("\\s*(.+?)[/\\s]*", "$1");

			return Arrays.asList(folderNames.split("/"));
		}

		public CucumberBatchTestClassGroup getCucumberBatchTestClassGroup() {
			return _cucumberBatchTestClassGroup;
		}

		public String getFeatureDescription() {
			return _featureDescription;
		}

		public String getFeatureName() {
			return _featureName;
		}

		public List<String> getFeatureTags() {
			return _featureTags;
		}

		public String getScenarioName() {
			return _scenarioName;
		}

		protected CucumberTestClass(
			CucumberBatchTestClassGroup cucumberBatchTestClassGroup,
			File featureFile, String featureName, String featureDescription,
			List<String> featureTags, String scenarioName) {

			super(featureFile);

			addTestClassMethod(
				JenkinsResultsParserUtil.combine(
					featureName, " > ", scenarioName));

			_cucumberBatchTestClassGroup = cucumberBatchTestClassGroup;
			_featureName = featureName;
			_featureDescription = featureDescription;
			_featureTags = featureTags;
			_scenarioName = scenarioName;
		}

		protected boolean matchesTagExpressions(List<String> tagExpresions) {
			for (String tagExpresion : tagExpresions) {
				if (!_matchesTagExpression(tagExpresion)) {
					return false;
				}
			}

			return true;
		}

		private boolean _matchesTagExpression(String tagExpression) {
			if (tagExpression.contains(",")) {
				for (String tagExpressionItem : tagExpression.split(",")) {
					if (_matchesTagExpression(tagExpressionItem)) {
						return true;
					}
				}

				return false;
			}

			if (tagExpression.startsWith("~")) {
				for (String featureTag : _featureTags) {
					if (featureTag.equals(tagExpression.substring(1))) {
						return false;
					}
				}

				return true;
			}

			for (String featureTag : _featureTags) {
				if (featureTag.equals(tagExpression)) {
					return true;
				}
			}

			return false;
		}

		private final CucumberBatchTestClassGroup _cucumberBatchTestClassGroup;
		private final String _featureDescription;
		private final String _featureName;
		private final List<String> _featureTags;
		private final String _scenarioName;

	}

	protected CucumberBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		_setTestClasses();

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
	}

	private String _getCucumberOptions() {
		String cucumberOptions = System.getenv("CUCUMBER_OPTIONS");

		if (JenkinsResultsParserUtil.isNullOrEmpty(cucumberOptions)) {
			cucumberOptions = getBuildStartProperty("CUCUMBER_OPTIONS");
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(cucumberOptions)) {
			cucumberOptions = JenkinsResultsParserUtil.getProperty(
				getJobProperties(), "test.batch.cucumber.options",
				getTestSuiteName(), getBatchName());
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(cucumberOptions)) {
			cucumberOptions = JenkinsResultsParserUtil.getProperty(
				getJobProperties(), "analytics.cloud.cucumber.options");
		}

		return cucumberOptions;
	}

	private File _getFeatureBaseDir() {
		String cucumberOptions = _getCucumberOptions();

		return new File(
			getFaroDir(),
			JenkinsResultsParserUtil.combine(
				"osb-faro-functional-test/",
				cucumberOptions.replaceAll(".* ([^ ]+)", "$1")));
	}

	private List<String> _getTagExpressions() {
		Matcher matcher = _tagExpressionPattern.matcher(_getCucumberOptions());

		List<String> tagExpressions = new ArrayList<>();

		while (matcher.find()) {
			tagExpressions.add(matcher.group("tagExpression"));
		}

		return tagExpressions;
	}

	private void _setTestClasses() {
		List<File> featureFiles = JenkinsResultsParserUtil.findFiles(
			_getFeatureBaseDir(), ".*\\.feature");

		for (File featureFile : featureFiles) {
			String featureContent;

			try {
				featureContent = JenkinsResultsParserUtil.read(featureFile);
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			Matcher featureFileMatcher = _featureFilePattern.matcher(
				featureContent);

			if (!featureFileMatcher.find()) {
				throw new RuntimeException(
					"Invalid feature file " + featureFile);
			}

			Matcher featureTagMatcher = _featureTagPattern.matcher(
				featureFileMatcher.group("featureTags"));

			List<String> featureTags = new ArrayList<>();

			while (featureTagMatcher.find()) {
				featureTags.add(featureTagMatcher.group());
			}

			Matcher scenarioMatcher = _scenarioPattern.matcher(
				featureFileMatcher.group("featureBody"));

			while (scenarioMatcher.find()) {
				CucumberTestClass cucumberTestClass = new CucumberTestClass(
					this, featureFile, featureFileMatcher.group("featureName"),
					featureFileMatcher.group("featureDescription"), featureTags,
					scenarioMatcher.group("scenarioName"));

				if (!cucumberTestClass.matchesTagExpressions(
						_getTagExpressions())) {

					continue;
				}

				testClasses.add(cucumberTestClass);
			}
		}

		Collections.sort(testClasses);
	}

	private static final Pattern _featureFilePattern = Pattern.compile(
		"(?<featureTags>[\\S\\s]+)(?<featureName>Feature\\: [^\\n]*)\\n" +
			"\\t*(?<featureDescription>[^\\n]*)\\n(?<featureBody>[\\S\\s]+)");
	private static final Pattern _featureTagPattern = Pattern.compile(
		"@[^\\s]+");
	private static final Pattern _scenarioPattern = Pattern.compile(
		"\\s*(?<scenarioName>Scenario: [^\\n]+)\\n");
	private static final Pattern _tagExpressionPattern = Pattern.compile(
		"--tags (?<tagExpression>[^ ]+)");

}