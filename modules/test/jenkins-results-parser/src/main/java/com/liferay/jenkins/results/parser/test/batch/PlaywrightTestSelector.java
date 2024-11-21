/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.suite.RelevantRuleConfigurationException;

import java.io.File;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Kenji Heigel
 */
public class PlaywrightTestSelector extends BaseTestSelector {

	public PlaywrightTestSelector(
			File propertiesFile, Properties properties, String batchName,
			String relevantRuleName, String testSuiteName)
		throws RelevantRuleConfigurationException {

		super(
			propertiesFile, properties, batchName, relevantRuleName,
			testSuiteName);

		validate();

		JobProperty jobProperty = getJobProperty(
			"playwright.test.project", JobProperty.Type.MODULE_TEST_DIR);

		if (jobProperty.getValue() == null) {
			jobProperty = getJobProperty(
				"playwright.projects.includes",
				JobProperty.Type.MODULE_TEST_DIR);
		}

		_playwrightJobProperties.add(jobProperty);
	}

	public JobProperty getPlaywrightExcludesJobProperty() {
		JobProperty excludesJobProperty = getJobProperty(
			"playwright.projects.excludes", JobProperty.Type.MODULE_TEST_DIR);

		if (excludesJobProperty.getValue() != null) {
			return excludesJobProperty;
		}

		return null;
	}

	public Set<JobProperty> getPlaywrightJobProperties() {
		return _playwrightJobProperties;
	}

	@Override
	public void merge(TestSelector testSelector) {
		if (!(testSelector instanceof PlaywrightTestSelector)) {
			throw new RuntimeException("Unable to merge test selectors");
		}

		PlaywrightTestSelector playwrightTestSelector =
			(PlaywrightTestSelector)testSelector;

		_playwrightJobProperties.addAll(
			playwrightTestSelector.getPlaywrightJobProperties());
	}

	@Override
	public void validate() throws RelevantRuleConfigurationException {
		if ((getProperty("playwright.projects.includes") == null) &&
			(getProperty("playwright.test.project") == null)) {

			StringBuilder sb = new StringBuilder();

			sb.append("Unable to create batch ");
			sb.append(getBatchName());
			sb.append(" since playwright.projects.includes[");
			sb.append(getRelevantRuleName());
			sb.append("][");
			sb.append(getTestSuiteName());
			sb.append("][");
			sb.append(getBatchName());
			sb.append("] or playwright.test.project[");
			sb.append(getRelevantRuleName());
			sb.append("][");
			sb.append(getTestSuiteName());
			sb.append("][");
			sb.append(getBatchName());
			sb.append("] is not set in ");
			sb.append(getPropertiesFile());

			throw new RelevantRuleConfigurationException(sb.toString());
		}
	}

	private final Set<JobProperty> _playwrightJobProperties = new HashSet<>();

}